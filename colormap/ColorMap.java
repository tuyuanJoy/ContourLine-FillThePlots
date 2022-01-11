package colormap;
import java.awt.Color;

public class ColorMap {
    private double isovalue;
    private Color color;
    public ColorMap(double isovalue, int r, int b, int g){
        this.isovalue = isovalue;
        this.color = new Color( r,g,b,255);
    }
    public double Isovalue(){
        return isovalue;
    }
    public Color LineColor(){
        return color;
    }
    
}
