/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import actions.Action;
import actions.Move;
import actions.PickUp;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import states.AgentState;
import states.MoveToTileState;
import states.PickUpState;

/**
 *
 * @author Vlad
 */
public class AgentImpl extends Thread implements Agent {

    public Environment env;
    public Color color;
    public int x, y;
    public boolean hasTile;
    public Color tileColor;
    public AgentState state;
    public int points;
    public JLabel pointsLabel;

    CyclicBarrier barrier;

    public List<Coordinates> pathToTile;
    public List<Coordinates> pathToHole;
    public List<List<Coordinates>> tilePaths;
    public List<Color> nearestTiles;
    public int id;
    private boolean done;

    public AgentImpl(Color c, int id) {
        color = c;
        pathToHole = new LinkedList<>();
        pathToTile = new LinkedList<>();
        this.id = id;
        state = new MoveToTileState();
    }

    @Override
    public void play(Environment e) {
        env = e;
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(AgentImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("hello");
            if (done) {
                System.out.println(this.getName() + " done. Points: " + points);
                break;
            }
            if (pathToTile.isEmpty() && pathToHole.isEmpty()) {
                System.out.println("Empty path. Finding path");
                findBestPath();
                if (pathToTile.isEmpty()) {
                    System.out.println("Path tot tile " + pathToTile.size());
                    state = new PickUpState();
                }
                System.out.println(id + ": tile paths " + tilePaths.size());
            }
            Action act = state.chooseAction(this, env);
            if (act != null) {
                synchronized (env.actions) {
                    env.actions.add(act);
                }
            }

            try {
                barrier.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(AgentImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(AgentImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void findBestPath() {
        tilePaths = new ArrayList<>();
        nearestTiles = new ArrayList<>();
        int marked[][] = new int[env.width][env.height];
        Coordinates parents[][] = new Coordinates[env.width][env.height];
        int dx[] = {0, 1, 0, -1};
        int dy[] = {-1, 0, 1, 0};
        Coordinates position = new Coordinates(x, y);
        Queue<Coordinates> q = new LinkedList<>();
        q.add(position);
        marked[position.y][position.x] = 1;
        parents[position.y][position.x] = null;
        while (!q.isEmpty()) {
            Coordinates c = q.poll();
            int distance = marked[c.y][c.x];
            if (env.board[c.y][c.x] != Board.OBSTACLE && env.board[c.y][c.x] > 0) {
                System.out.println(id + ": Found a new path to tile at " + c.x + " " + c.y);
                tilePaths.add(getPath(position, c, parents));
                nearestTiles.add(env.boardColors[c.y][c.x]);
            }

            for (int i = 0; i < 4; i++) {
                int x1 = c.x + dx[i];
                int y1 = c.y + dy[i];
                if (x1 >= 0 && x1 < env.width && y1 >= 0 && y1 < env.height && marked[y1][x1] == 0) {
                    Coordinates neigh = new Coordinates(x1, y1);
                    int type = env.board[neigh.y][neigh.x];
                    if (type != Board.OBSTACLE && type >= 0) {
                        marked[y1][x1] = distance + 1;
                        parents[y1][x1] = c;
                        q.add(neigh);
                    }
                }
            }
        }
        int best = Integer.MAX_VALUE;
        for (List<Coordinates> path : tilePaths) {
            Coordinates end;
            if (path.size() > 0) {
                end = path.get(path.size() - 1);
            } else {
                end = position;
            }
            if (env.boardColors[end.y][end.x] == this.color) {
                List<Coordinates> holePath = computePathToHole(end);
                printPath(holePath);
                System.out.println("hole path " + holePath.size());
                if (path.size() + holePath.size() < best) {
                    best = path.size();
                    pathToTile = path;
                    System.out.println("Path to tile");
                    printPath(path);
                    System.out.println("Path to hole");
                    printPath(holePath);
                    pathToHole = holePath;
                }
            }
        }

    }

    private List<Coordinates> getPath(Coordinates position, Coordinates dest, Coordinates parents[][]) {

        List<Coordinates> path = new LinkedList<>();
        while (dest != null && dest != position) {
            path.add(0, dest);
            dest = parents[dest.y][dest.x];
        }
        System.out.println("Path len " + path.size());
        return path;
    }

    private List<Coordinates> computePathToHole(Coordinates start) {
        List<Coordinates> path = new LinkedList<>();
        int marked[][] = new int[env.width][env.height];
        Coordinates parents[][] = new Coordinates[env.width][env.height];
        int dx[] = {0, 1, 0, -1};
        int dy[] = {-1, 0, 1, 0};
        Queue<Coordinates> q = new LinkedList<>();
        q.add(start);
        marked[start.y][start.x] = 1;
        parents[start.y][start.x] = null;
        while (!q.isEmpty()) {
            Coordinates c = q.poll();
            int distance = marked[c.y][c.x];
            if (env.board[c.y][c.x] < 0 && env.boardColors[c.y][c.x] == this.color) {
                System.out.println(id + ": Found a new path to hole at " + c.x + " " + c.y);
                path = getPath(start, c, parents);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int x1 = c.x + dx[i];
                int y1 = c.y + dy[i];
                if (x1 >= 0 && x1 < env.width && y1 >= 0 && y1 < env.height && marked[y1][x1] == 0) {
                    Coordinates neigh = new Coordinates(x1, y1);
                    int type = env.board[neigh.y][neigh.x];
                    if (type != Board.OBSTACLE) {
                        if (type >= 0 || (type < 0 && env.boardColors[neigh.y][neigh.x] == color)) {
                            marked[y1][x1] = distance + 1;
                            parents[y1][x1] = c;
                            q.add(neigh);
                        }
                    }
                }
            }
        }

        return path;
    }

    private void printPath(List<Coordinates> holePath) {
        for (int yy = 0; yy < env.height; yy++) {
            for (int xx = 0; xx < env.width; xx++) {
                if (this.x == xx && this.y == yy) {
                    System.out.print("x ");
                } else if (holePath.contains(new Coordinates(xx, yy))) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }

    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int p) {
        points = p;
        pointsLabel.setText(points + "");
    }

    void notifyFinish() {
        done = true;
    }
}
