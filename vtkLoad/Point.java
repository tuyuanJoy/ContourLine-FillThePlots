package vtkload;
import colormap.*; 
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.awt.Color;

public class Point{
    private double scalarVaule;
    private Point2D coordinate;
    private boolean ishigh;
    private Color color;
   

    public Point(Double x, Double y){
        this.coordinate = new Point2D.Double(x, y);
        scalarVaule = 0.0;
        ishigh = true;
    }

    public void setValue(double value){
        this.scalarVaule = value;
    }
    public void SetisHigh(boolean t){
        ishigh = t;
    }
    public Point2D Coor(){
        return coordinate;
    }
    public double Field(){
        return scalarVaule;
    }
    public boolean Ishigh(){
        return ishigh;
    }
    public void setColorToPoint( ArrayList<ColorMap> map){
        for(int i=0; i<map.size();i++ ){
            if(map.get(i).Isovalue() == scalarVaule) {
                color = map.get(i).LineColor();
                break;
            }
        }
    }
    public Color GetColor(){
        return color;
    }

}