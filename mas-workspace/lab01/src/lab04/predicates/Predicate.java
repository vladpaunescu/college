package lab04.predicates;

import java.util.Map;

import lab04.Block;

public interface Predicate {
	
	boolean isValid();

	String printInfo();

	void invalidate();

	void makeTrue();

	Predicate clone(Map<String, Block> blockMap);

}
