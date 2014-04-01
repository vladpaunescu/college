package lab04.operators;

import java.util.LinkedList;
import java.util.List;

import lab04.predicates.Predicate;

public abstract class Operator {
	

	public List<Predicate> preconditions;
	public List<Predicate> additions;
	public List<Predicate> eliminations;
	
	public Operator(){
		preconditions = new LinkedList<>();
		additions = new LinkedList<>();
		eliminations = new LinkedList<>();
	}

}
