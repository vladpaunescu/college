/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import actions.UseTile;
import actions.Action;
import board.AgentImpl;
import board.Coordinates;
import board.Environment;

/**
 *
 * @author Vlad
 */
public class UseTileState implements AgentState {

    public UseTileState() {
    }

    @Override
    public Action chooseAction(AgentImpl a, Environment env) {
        Coordinates hole = a.pathToHole.remove(0);
        a.state = new MoveToTileState();
        return new UseTile(a, hole, a.tileColor);
    }

}
