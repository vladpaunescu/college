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
class CarryTileState implements AgentState {

    public CarryTileState() {
    }

    @Override
    public Action chooseAction(AgentImpl a, Environment env) {
        System.out.println("Inside carry tile");
        Coordinates next = a.pathToHole.remove(0);
        if (a.pathToHole.size() == 1) {
            a.state = new UseTileState();
        }
        return new Move(a, next);
    }
    
}
