package board;

/**
 *
 * @author Vlad
 */
public class Coordinates {
    public int x, y;
    
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Coordinates(Coordinates c){
        x = c.x;
        y = c.y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.x;
        hash = 29 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinates other = (Coordinates) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    
}
