/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import actions.Action;
import actions.Move;
import board.AgentImpl;
import board.Coordinates;
import board.Environment;

/**
 *
 * @author Vlad
 */
public class MoveToTileState implements AgentState {

    @Override
    public Action chooseAction(AgentImpl a, Environment env) {
        Coordinates next = a.pathToTile.remove(0);
        if (a.pathToTile.isEmpty()) {
            System.out.println("State change pickup state" );
            a.state = new PickUpState();
        }
        return new Move(a, next);
    }

}
