package lab04;

import lab04.operators.Operator;
import lab04.predicates.Predicate;

public class ActionState {
	public Operator operator;
	public State state;
	
	public ActionState(Operator op, State state){
		operator = op;
		this.state = state;
	}

	public State applyAction() {
		operator.apply();
		for (Predicate elimination : operator.eliminations){
			state.removePredicate(elimination);
		}
		for(Predicate addition: operator.additions){
			state.addPredicate(addition);
		}
		
		return state;
	}
}
