package states;

import actions.Action;
import board.AgentImpl;
import board.Environment;

/**
 *
 * @author Vlad
 */
public interface AgentState {
    Action chooseAction(AgentImpl a, Environment env);
}
