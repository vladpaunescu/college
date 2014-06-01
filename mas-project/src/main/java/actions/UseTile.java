package actions;

import board.AgentImpl;
import board.Board;
import board.Coordinates;
import java.awt.Color;

/**
 *
 * @author Vlad
 */
public class UseTile implements Action {

    public Coordinates coords;
    public AgentImpl agent;
    public Color tileColor;

    public UseTile(AgentImpl a, Coordinates coord, Color tileClr) {
        agent = a;
        coords = coord;
        tileColor = tileClr;
    }

    @Override
    public void execute(Board b) {
        if (coords.x < 0 || coords.x >= b.width || coords.y < 0 || coords.y >= b.height) {
            System.out.println("Exception coords out of range " + coords.x + " " + coords.y);
            return;
        }
        if (Math.abs(coords.x - agent.x) > 1 || Math.abs(coords.y - agent.y) > 1) {
            System.out.println("Exception distance range ");
            return;
        }

        if (b.boardColors[coords.y][coords.x] != tileColor
                || b.board[coords.y][coords.x] >= 0) {
            System.out.println("Error different hole color");
            return;
        }

        System.out.println("Executing use tile");
        agent.hasTile = false;
        agent.tileColor = null;
        b.board[coords.y][coords.x]++;
        agent.setPoints(agent.getPoints() + 10);
        if (b.board[coords.y][coords.x] == Board.FREE) {
            b.boardColors[coords.y][coords.x] = null;
            agent.points += 40;
            b.depthCount.put(tileColor, b.depthCount.get(tileColor) - 1);
        }
    }
}
