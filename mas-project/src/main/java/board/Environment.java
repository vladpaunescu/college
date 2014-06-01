package board;

import actions.Action;
import java.awt.Color;
import java.util.List;

public class Environment {

    public int board[][];
    public Color[][] boardColors;
    public List<AgentImpl> agents;
    public int width, height;
    public List<Action> actions;

    public Environment(Board b) {
        this.board = b.board;
        agents = b.agents;
        width = b.width;
        height = b.height;
        boardColors = b.boardColors;
        actions = b.actions;
    }

}
