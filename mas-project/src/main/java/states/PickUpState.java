/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import actions.PickUp;
import actions.Action;
import board.AgentImpl;
import board.Board;
import board.Coordinates;
import board.Environment;

/**
 *
 * @author Vlad
 */
public class PickUpState implements AgentState {

    public PickUpState() {
    }

    @Override
    public Action chooseAction(AgentImpl a, Environment env) {
        if (env.board[a.y][a.x] != Board.OBSTACLE
                && env.board[a.y][a.x] > 0 && env.boardColors[a.y][a.x] == a.color) {
            System.out.println("State change Carry tile state");
            if (a.pathToHole.size() > 1) {
                a.state = new CarryTileState();
            } else {
                a.state = new UseTileState();
            }
            return new PickUp(a, new Coordinates(a.x, a.y), a.color);
        }

        System.out.println("PickUp state error");
        return null;
    }
}
