import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;

import colormap.*;
import drawpanel.MeshPanel;
import vtkload.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class Main {

  public static void main(String args[]) {
    JFrame sf = new JFrame("Simple");
    int width = 500;
    int height = 500;

    String path;
    String localDir = System.getProperty("user.dir");

    //Read info from user
    if (args.length != 1) {
      path = localDir + "/riderr.vtk";
      System.out.println("Haven't detect file name, automatically import vtk file: " + path);
      System.out.println("Please input number:\n Triangle Mesh:0; \n Contour Line: 1; \n Color Render with Average Color: 2;\n Color Render Replace draw line: 3;\n");
    } else
    path = localDir + "/" + args[0];
    
    double[] isovalues = new double[0];
    Scanner sc = new Scanner(System.in);
    int type = sc.nextInt();

    if(type == 1){
      System.out.println("Please input how many contour lines you want?");
      int count = sc.nextInt();
  
      System.out.println("Please input "+ count+" isovalues between range [0,1]");
      isovalues = new double[count];
    
      for(int i=0; i<count; i++){
        isovalues[i] = sc.nextDouble();
      }
    }
    sc.close();

    //Load data
    vtkUnstructuredGridReader vtk = new vtkUnstructuredGridReader(path);
    ColorMapReader colormap = new ColorMapReader(localDir + "/octave_colormap.csv" , isovalues);
    vtk.LoadReader();
    colormap.LoadColorMapReader();

    //Draw
    MeshPanel drawPanel = new MeshPanel(width, height, vtk.Points(), vtk.Faces(), colormap.ColorMap(), type, colormap.isovaules());
    sf.setTitle("Plot");
    sf.add(drawPanel, BorderLayout.CENTER);
    sf.pack();
    sf.setSize(width, height);
    sf.setVisible(true);

    // Exit project
    sf.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }

}
