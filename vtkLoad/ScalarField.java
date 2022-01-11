package vtkLoad;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class ScalarField extends JFrame{
   
    private vtkUnstructuredGridReader vtk;
    
    public ScalarField(String path){
        super();
        vtk = new vtkUnstructuredGridReader(path);
    }


    public void paint(Graphics grf){
        
        Graphics2D g2d = (Graphics2D)grf;
        vtk.LoadReader();
        System.out.println("Print" + vtk.NumberOfPoints());
        
        for(int i=0; i<vtk.NumberOfPoints();i++){
            
           // if(vtk.Points().get(i).Field() <0.3 && vtk.Points().get(i).Field() > 0.1) {
                double xp = vtk.Points().get(i).Coor().getX();
                double yp = vtk.Points().get(i).Coor().getY();
               // points.add(new Point2D.Double(xp,yp));
                if(i>1){
                
                double x1 = vtk.Points().get(i).Coor().getX() * 10 + 100;
                double y1 = -vtk.Points().get(i).Coor().getY() * 10 +400 ;
                double x2 = vtk.Points().get(i-1).Coor().getX() *10 + 100;
                double y2 = -vtk.Points().get(i-1).Coor().getY() *10 +400;
                
                g2d.draw(new Line2D.Double(x1, y1, x1-5, y1-5));
                //g2d.drawOval(x1,x2,2,2);
                System.out.println(x1+" "+ y1+ " " +x2+ " " + y2);
           //    }
            }
           //while(){

        //}
            
        }
    }
    private void colloectPoint(vtkUnstructuredGridReader vtk, double min, double max, Graphics2D g2d, Color color){
        
        ArrayList<Point2D> points = new ArrayList <Point2D> ();
        for(int i=0; i<vtk.NumberOfPoints();i++){
            
            if(vtk.Points().get(i).Field() <max && vtk.Points().get(i).Field() > min) {
                double xp = vtk.Points().get(i).Coor().getX();
                double yp = vtk.Points().get(i).Coor().getY();
                points.add(new Point2D.Double(xp,yp));
                if(i>1){
                    
                    double x1 = vtk.Points().get(i).Coor().getX() * 10 + 100;
                    double y1 = -vtk.Points().get(i).Coor().getY() * 10 +400 ;
                    double x2 = vtk.Points().get(i-1).Coor().getX() *10 + 100;
                    double y2 = -vtk.Points().get(i-1).Coor().getY() *10 +400;
                    
                    g2d.setColor(color);
                    g2d.draw(new Line2D.Double(x1, y1, x1-5, y1-5));
                }
            }

        }
    }
    void drawLines(Graphics g, Point2D a, Point2D b) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(new Line2D.Double(a.getX(), b.getX(), a.getX(), b.getY()));
    }
    
    public static void main(String args[]){
        ScalarField sf = new ScalarField("C:/JoysStuff/Classes/2021-Q4/Java2D3D/task_2d_data/task_2d_data_2016/riderr.vtk");
        sf.setTitle("Plot");
        sf.setSize(800, 800);
        sf.setVisible(true);
    }
    
    
}
