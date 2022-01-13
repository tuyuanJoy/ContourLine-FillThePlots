package drawpanel;

import vtkload.*;
import java.util.ArrayList;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import colormap.ColorMap;

import java.awt.Polygon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class MeshPanel extends JPanel {
    ArrayList<Point> lines = new ArrayList<Point>();

    ArrayList<vtkload.Point> vex;
    ArrayList<Point2D> scaledCoord;
    ArrayList<int[]> faces;
    ArrayList<ColorMap> colormap;
    ArrayList<Color> colors;
    int type;
    double[] isovalues;

    public MeshPanel(int width, int height, ArrayList<vtkload.Point> points, ArrayList<int[]> faces,
            ArrayList<ColorMap> colorMap, int type, double[] isovaules) {
        setBackground(Color.white);

        this.vex = points;
        this.faces = faces;
        this.colormap = colorMap;
        this.type = type;
        this.isovalues = isovaules;
        colors = new ArrayList<Color>();

        colors.add(Color.MAGENTA);
        colors.add(Color.red);
        colors.add(Color.pink);
        colors.add(Color.orange);
        colors.add(Color.yellow);
        colors.add(Color.green);
        colors.add(Color.CYAN);
        colors.add(Color.blue);

        Point2D minPoint = new Point2D.Double();
        Point2D maxPoint = new Point2D.Double();

        LocatePanel(minPoint, maxPoint);
        scaledCoord = new ArrayList<Point2D>();
        scaleToFitWindow(minPoint, maxPoint, width, height);

    }

    public void paint(Graphics g) {
        switch (type){
            case 0: paintMeshPolygons(g); break;
            case 1: paintContourLine(g); break;
            case 2: fillMeshPolygons(g); break;
            case 3: renderColorToShapeByReplace(g); break;
            default: System.out.println("Please input integer number between 0-3");
        }
    }

    // rescale shape and center the graph
    private void LocatePanel(Point2D minPoint, Point2D maxPoint) {
        minPoint.setLocation(Double.MAX_VALUE, Double.MAX_VALUE);
        maxPoint.setLocation(Double.MIN_VALUE, Double.MIN_VALUE);
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        for (int i = 0; i < vex.size(); i++) {
            Point p = vex.get(i);
            minX = Math.min(minX, p.Coor().getX());
            minY = Math.min(minY, p.Coor().getY());
            maxX = Math.max(maxX, p.Coor().getX());
            maxY = Math.max(maxY, p.Coor().getY());
        }
        minPoint.setLocation(minX, minY);
        maxPoint.setLocation(maxX, maxY);

    }
    private void scaleToFitWindow(Point2D minPoint, Point2D maxPoint, int width, int height) {

        double xdistance = maxPoint.getX() - minPoint.getX();
        double ydistance = maxPoint.getY() - minPoint.getY();
        for (int i = 0; i < vex.size(); i++) {
            double x = vex.get(i).Coor().getX();
            double y = vex.get(i).Coor().getY();

            x = x - minPoint.getX();
            y = y - minPoint.getY();

            x = x * width / xdistance;
            y = -y * height / ydistance + height;

            scaledCoord.add(new Point2D.Double(x, y));

        }
    }
 
    //Triangle mesh
    private Polygon getPolygon(int i){
        Polygon p = new Polygon();
        int[] face = faces.get(i);
        int[] x = new int[3];
        int[] y = new int[3];
        x[0] = (int) scaledCoord.get(face[0]).getX();
        x[1] = (int) scaledCoord.get(face[1]).getX();
        x[2] = (int) scaledCoord.get(face[2]).getX();
        y[0] = (int) scaledCoord.get(face[0]).getY();
        y[1] = (int) scaledCoord.get(face[1]).getY();
        y[2] = (int) scaledCoord.get(face[2]).getY();
       
        p.addPoint(x[0], y[0]);
        p.addPoint(x[1], y[1]);
        p.addPoint(x[2], y[2]);
        return p;
    }
    private void paintMeshPolygons(Graphics g) {
        g.setColor(Color.black);
        for (int i = 0; i < faces.size(); i++) {
            Polygon p = new Polygon();
            p = getPolygon(i);
            g.drawPolygon(p);
        }
    }

    //Paint user requires contour line
    private void paintContourLine(Graphics g) {
        for (int i = 0; i < isovalues.length; i++) {
            // to determine if the scalar value higher than sandard or not
            //checkIsovalueOnEachPoint(i);
            checkIsovalueOnEachPointWithInput(i);
            // for every triangle, connect to the middle of their edge(if 0-1 or 1-0) to
            // draw a line
            drawContourLineInTriangle(i, g);
        }
    }
    private void drawContourLineInTriangle(int indexOfColorMap, Graphics g) {
        boolean v1, v2, v3;
        // set line color
        g.setColor(colormap.get(indexOfColorMap).LineColor());
        // g.setColor(colors.get(indexOfColorMap));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i < faces.size(); i++) {
            int[] face = faces.get(i);
            int[] x = new int[3];
            int[] y = new int[3];

            x[0] = (int) scaledCoord.get(face[0]).getX();
            x[1] = (int) scaledCoord.get(face[1]).getX();
            x[2] = (int) scaledCoord.get(face[2]).getX();
            y[0] = (int) scaledCoord.get(face[0]).getY();
            y[1] = (int) scaledCoord.get(face[1]).getY();
            y[2] = (int) scaledCoord.get(face[2]).getY();

            v1 = vex.get(face[0]).Ishigh();
            v2 = vex.get(face[1]).Ishigh();
            v3 = vex.get(face[2]).Ishigh();

            if (v1 != v2 && v2 != v3) {
                int x1 = (int) (x[0] + x[1]) / 2;
                int y1 = (int) (y[0] + y[1]) / 2;
                int x2 = (int) (x[2] + x[1]) / 2;
                int y2 = (int) (y[2] + y[1]) / 2;
                g.drawLine(x1, y1, x2, y2);
            }
            if (v1 != v2 && v1 != v3) {
                int x1 = (int) (x[0] + x[1]) / 2;
                int y1 = (int) (y[0] + y[1]) / 2;
                int x2 = (int) (x[2] + x[0]) / 2;
                int y2 = (int) (y[2] + y[0]) / 2;
                g.drawLine(x1, y1, x2, y2);
            }
            if (v1 != v3 && v2 != v3) {
                int x1 = (int) (x[0] + x[2]) / 2;
                int y1 = (int) (y[0] + y[2]) / 2;
                int x2 = (int) (x[2] + x[1]) / 2;
                int y2 = (int) (y[2] + y[1]) / 2;
                g.drawLine(x1, y1, x2, y2);
            }

        }
    }
    private void checkIsovalueOnEachPointWithInput(int i) {
        for (int j = 0; j < vex.size(); j++) {
            vex.get(j).SetisHigh(true);
            // if(vex.get(j).Field() <= i * 0.125) {
            if (isovalues[i] <= vex.get(j).Field()) {
                vex.get(j).SetisHigh(false);
            }
        }
        
    }

    //Fill color by replacing drawing lines in triangle
    private void renderColorToShapeByReplace(Graphics g) {
        for (int i = 0; i < colormap.size(); i++) {
            checkIsovalueOnEachPoint(i);
            fillShapeByReplace(i, g);
        }
    }
    private void checkIsovalueOnEachPoint(int i) {
        for (int j = 0; j < vex.size(); j++) {
            vex.get(j).SetisHigh(true);
            // if(vex.get(j).Field() <= i * 0.125) {
            if (vex.get(j).Field() <= colormap.get(i).Isovalue()) {
                vex.get(j).SetisHigh(false);
            }
        }
    }
    private void fillShapeByReplace(int indexOfColorMap, Graphics g) {
        boolean v1, v2, v3;
        for (int i = 0; i < faces.size(); i++) {
            int[] face = faces.get(i);
            int[] x = new int[3];
            int[] y = new int[3];

            x[0] = (int) scaledCoord.get(face[0]).getX();
            x[1] = (int) scaledCoord.get(face[1]).getX();
            x[2] = (int) scaledCoord.get(face[2]).getX();
            y[0] = (int) scaledCoord.get(face[0]).getY();
            y[1] = (int) scaledCoord.get(face[1]).getY();
            y[2] = (int) scaledCoord.get(face[2]).getY();

            Polygon pl = new Polygon();
            pl.addPoint(x[0], y[0]);
            pl.addPoint(x[1], y[1]);
            pl.addPoint(x[2], y[2]);

            Color tempColor = colormap.get(indexOfColorMap).LineColor();
            g.setColor(tempColor);

            v1 = vex.get(face[0]).Ishigh();
            v2 = vex.get(face[1]).Ishigh();
            v3 = vex.get(face[2]).Ishigh();

            if (v1 != v2 && v2 != v3 || v1 != v2 && v1 != v3 || v1 != v3 && v2 != v3) {
                g.fillPolygon(pl);
            }
            if (v1 == v2 == v3) {
                g.fillPolygon(pl);
            }
            
        }
    }

    //Fill color by calculating average scalar vaule
    private void fillMeshPolygons(Graphics g) {

        for (int i = 0; i < faces.size(); i++) {
            Polygon p = new Polygon();
            int[] face = faces.get(i);
            double averageScalar;
            Color averageColor;
            p = getPolygon(i);
            double v1, v2, v3;
            v1 = vex.get(face[0]).Field();
            v2 = vex.get(face[1]).Field();
            v3 = vex.get(face[2]).Field();
            averageScalar = (v1 + v2 + v3) /3;
            averageColor = findAverageColor(averageScalar);
            g.setColor(averageColor);
            g.fillPolygon(p);
        }
    }
    private Color findAverageColor(double averageScalar){
        Color averageColor;
        int id=0;
        double minDifference = Double.MAX_VALUE;  
        for (int i = 0; i < colormap.size(); i++) {
            double isovalue = colormap.get(i).Isovalue();
            if(Math.abs(averageScalar - isovalue) < minDifference) {
                minDifference = Math.abs(averageScalar - isovalue);
                id = i;
            }
        }    
        averageColor = colormap.get(id).LineColor();
        return averageColor;
    }

    
  
}