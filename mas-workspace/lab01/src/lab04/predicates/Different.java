package lab04.predicates;

import java.util.Map;

import lab04.Block;

public class Different implements Predicate {

	public Block first;
	public Block second;
	
	public Different(Block x, Block y) {
		first = x;
		second = y;
	}

	@Override
	public boolean isValid() {
		return !first.equals(second);
	}

	@Override
	public String printInfo() {
		if (isValid()) return "Different(" + first.name + ", " + second.name + ")";
		return "NOT Different(" + first.name + ", " + second.name + ")";
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTrue() {
		// TODO Auto-generated method stub

	}

	@Override
	public Predicate clone(Map<String, Block> blockMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
