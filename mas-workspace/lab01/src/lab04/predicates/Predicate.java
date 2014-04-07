package lab04.predicates;

public interface Predicate {
	
	boolean isValid();

	String printInfo();

	void invalidate();

	void makeTrue();

}
