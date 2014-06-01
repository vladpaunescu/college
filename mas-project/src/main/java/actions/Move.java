package actions;

import board.AgentImpl;
import board.Board;
import board.Coordinates;

/**
 *
 * @author Vlad
 */
public class Move implements Action {

    public Coordinates coords;
    public AgentImpl agent;

    public Move(AgentImpl a, Coordinates coord) {
        coords = coord;
        agent = a;
    }

    @Override
    public void execute(Board b) {
        if (coords.x < 0 || coords.x >= b.width || coords.y < 0 || coords.y >= b.height) {
            System.out.println("Exception coords out of range " + coords.x + " " + coords.y);
            return;
        }
        if (Math.abs(coords.x - agent.x) > 1 || Math.abs(coords.y  - agent.y) > 1){
            System.out.println("Exception distance range ");
            return;
        }

        agent.x = coords.x;
        agent.y = coords.y;
    }

}
