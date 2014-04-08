package lab04.operators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lab04.State;
import lab04.predicates.Predicate;

public abstract class Operator {
	

	public List<Predicate> preconditions;
	public List<Predicate> additions;
	public List<Predicate> eliminations;
	
	public Operator(){
		preconditions = new ArrayList<>();
		additions = new ArrayList<>();
		eliminations = new ArrayList<>();
	}
	
	public boolean canApply(){
		for(Predicate precondition : preconditions){
			if (!precondition.isValid()) return false;
		}
		return true;
	}
	
	public void apply() {
		
		for(Predicate elimination : eliminations){
			elimination.invalidate();
		}
		
		for(Predicate addition : additions){
			addition.makeTrue();
		}
	}
}
