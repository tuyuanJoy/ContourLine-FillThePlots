package vtkload;
import java.awt.geom.Point2D;

public class Point {
    private double scalarVaule;
    private Point2D coordinate;
    private boolean ishigh;

    public Point(Double x, Double y) {
        this.coordinate = new Point2D.Double(x, y);
        scalarVaule = 0.0;
        ishigh = true;
    }

    public void setValue(double value) {
        this.scalarVaule = value;
    }

    public void SetisHigh(boolean t) {
        ishigh = t;
    }

    public Point2D Coor() {
        return coordinate;
    }

    public double Field() {
        return scalarVaule;
    }

    public boolean Ishigh() {
        return ishigh;
    }

}