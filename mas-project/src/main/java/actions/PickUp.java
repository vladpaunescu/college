package actions;

import board.AgentImpl;
import board.Board;
import board.Coordinates;
import java.awt.Color;

/**
 *
 * @author Vlad
 */
public class PickUp implements Action {

    public Coordinates coords;
    public AgentImpl agent;
    public Color tileColor;

    public PickUp(AgentImpl a, Coordinates coord, Color tileClr) {
        coords = coord;
        agent = a;
        tileColor = tileClr;
    }

    @Override
    public void execute(Board b) {
        if (coords.x != agent.x || coords.y != agent.y) {
            System.out.println("Pickup coordinates mismatch");
            return;
        }
        
        if (b.board[coords.y][coords.x] != Board.OBSTACLE &&
                b.board[coords.y][coords.x] > 0 && b.boardColors[coords.y][coords.x] == tileColor) {
            System.out.println("Executing pickup");
            agent.hasTile = true;
            agent.tileColor = tileColor;
            System.out.println("Tile count " +  b.board[coords.y][coords.x]);
            b.board[coords.y][coords.x]--;
            if ( b.board[coords.y][coords.x] == Board.FREE){
                b.boardColors[coords.y][coords.x] = null;
            }
        }
    }

}
