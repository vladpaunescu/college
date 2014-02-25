package lab02;

public class Coordinates {
	public int x, y;
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public Coordinates getNext(Orientation or){
		switch (or) {
		case NORTH:
			return new Coordinates(x, y - 1);
		case EAST:
			return new Coordinates(x + 1, y);
		case SOUTH:
			return new Coordinates(x, y + 1);
		case WEST:
			return new Coordinates(x - 1, y);
		}
		
		return this;
	}
}
