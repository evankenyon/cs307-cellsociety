package cellsociety.location;

public class CornerLocation {
    private double x_pos;
    private double y_pos;

    public CornerLocation(double x_pos, double y_pos)
    {
        this.x_pos=x_pos;
        this.y_pos=y_pos;
    }

    public void setX_pos(double x_pos) {
        this.x_pos = x_pos;
    }

    public void setY_pos(double y_pos) {
        this.y_pos = y_pos;
    }

    public double getX_pos() {
        return x_pos;
    }

    public double getY_pos() {
        return y_pos;
    }

    @Override
    public String toString() {
        return this.x_pos + ", " + this.y_pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CornerLocation otherLocation = (CornerLocation) o;
        return this.x_pos == otherLocation.x_pos && this.y_pos == otherLocation.y_pos;
    }
}
