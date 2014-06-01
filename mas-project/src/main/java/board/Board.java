package board;

import actions.Action;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class Board extends JPanel {

    public static int OBSTACLE = 1000;
    public static int FREE = 0;

    public static int MAX_TILE_COUNT = 4;
    public static int MAX_DEPTH = 3;
    public static int FREE_PROB = 3; // 0...3 free
    public static int OBSTACLE_PROB = 4;
    public static int DEPTH_PROB = 6; // 5.. 6 DEPTH
    public static int TILE_PROB = 8; // 7... 8 TILE

    public int maxTilesCount = 20;
    public int maxDepthCount = 10;
    public int maxObstacleCount = 5;
    public int[][] board;
    public Color[][] boardColors;

    Color[] colors = {Color.BLUE, Color.GREEN};

    public int width, height;
    public double cellHeight, cellWidth;
    public Map<Color, Integer> depthCount;

    List<AgentImpl> agents;
    Random rand;
    CyclicBarrier barrier;

    public List<Action> actions;

    public Board() {
        rand = new Random();
        agents = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {

                double left = c * cellWidth;
                double top = r * cellHeight;

                if (board[r][c] == OBSTACLE) {
                    g2.setColor(Color.GRAY);
                    g2.fill(new Rectangle2D.Double(left, top, cellWidth, cellHeight));
                } else if (board[r][c] != FREE) {
                    g2.setColor(boardColors[r][c]);
                    g2.setFont(new Font("TimesRoman", Font.BOLD, 20));
                    g2.drawString(board[r][c] + "", (int) (left + cellWidth / 2 - 10), (int) (top + cellHeight / 2 - 2));
                }

            }

        }

        // draw agents
        for (AgentImpl a : agents) {
            g2.setColor(a.color);
            double left = a.x * cellWidth + cellWidth / 10;
            double top = a.y * cellHeight + cellHeight / 10;
            g2.fillOval((int) left, (int) top, (int) (cellWidth - cellWidth / 5), (int) (cellHeight - cellHeight / 5));

            if (a.hasTile) {
                g2.setColor(a.color);
                g2.drawString("1", (int) left, (int) top);
            }
        }

        // draw lines 
        g2.setColor(Color.BLACK);
        for (int r = 0; r < height; r++) {
            int y = (int) Math.round(r * cellHeight);
            g2.drawLine(0, y, (int) Math.round(getWidth()), y);
        }
        for (int c = 0; c < width; c++) {
            int x = (int) Math.round(c * cellWidth);
            g2.drawLine(x, 0, x, (int) Math.round(getHeight()));
        }
    }

    public void generate(int w, int h) {
        width = w;
        height = h;

        cellHeight = 1.0 * getWidth() / width;
        cellWidth = 1.0 * getHeight() / height;

        board = new int[h][w];
        boardColors = new Color[h][w];
        depthCount = new HashMap<>();

        int type;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                type = rand.nextInt(9);
                if (type <= FREE_PROB) {
                    board[i][j] = FREE;
                } else if (type <= OBSTACLE_PROB) {
                    board[i][j] = OBSTACLE;
                } else if (type <= DEPTH_PROB) {
                    board[i][j] = -rand.nextInt(MAX_DEPTH) - 1;
                    int result = rand.nextInt(colors.length);
                    boardColors[i][j] = colors[result];
                    if (depthCount.get(colors[result]) == null) {
                        depthCount.put(colors[result], 0);
                    }
                    depthCount.put(colors[result], depthCount.get(colors[result]) + 1);
                } else {
                    board[i][j] = rand.nextInt(MAX_TILE_COUNT) + 1;
                    boardColors[i][j] = colors[rand.nextInt(colors.length)];
                }

            }
        }

        actions = new LinkedList<>();

        barrier = new CyclicBarrier(agents.size() + 1);

        for (AgentImpl a : agents) {
            int r, c;
            do {
                r = rand.nextInt(height);
                c = rand.nextInt(width);

            } while (board[r][c] != 0);
            a.x = c;
            a.y = r;
            a.barrier = barrier;
        }

        this.repaint();

        for (Color key : depthCount.keySet()) {
            if (depthCount.get(key) == 0) {
                System.out.println("Agent of color " + key + " has finished");
                for (AgentImpl a : agents) {
                    if (a.color == key) {
                        a.notifyFinish();
                    }
                }
            }
        }

    }

    void play() {
        while (true) {
            this.repaint();
            barrier.reset();
            System.out.println("Calling agents");
            for (Agent a : agents) {
                a.play(new Environment(this));
            }
            System.out.println("Waiting for responses");
            try {
                barrier.await();
                System.out.println("Received responses");

                for (Action act : actions) {
                    act.execute(this);
                }

                actions.clear();
                Thread.sleep(1000);

            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
