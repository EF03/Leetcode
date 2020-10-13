package em.slot;

/**
 * @author Ron
 * @date 2020/8/19 上午 10:37
 */
public class MatrixLocation {

    private int x;
    private int y;

    public MatrixLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MatrixLocation() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "MatrixLocation{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
