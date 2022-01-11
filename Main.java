import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;

import colormap.*;
import drawpanel.MeshPanel;
import vtkload.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class Main{
   
    public static void main(String args[]){
      JFrame sf = new JFrame("Simple");
      int width = 1000;
      int height = 1000;

      String path;
      String localDir = System.getProperty("user.dir");
      if (args.length != 1) {
        path = localDir + "/riderr.vtk"; 
        System.out.println("Haven't detect file name, automatically import vtk file: " + path);
        System.out.println("Please input number:\n2 Triangle Mesh:0; Contour Line: 1; Color Render: 2");
      }
      else path =localDir + "/" + args[0];
      
      //Read type 
      Scanner sc=new Scanner(System.in);  
      int type= sc.nextInt();  
      sc.close();
      
      vtkUnstructuredGridReader vtk = new vtkUnstructuredGridReader(path);
      ColorMapReader colormap = new ColorMapReader(localDir + "/octave_colormap.csv");
      //load data

      vtk.LoadReader();
      colormap.LoadColorMapReader();
      MeshPanel drawPanel = new MeshPanel(width, height, vtk.Points(),  vtk.Faces(), colormap.ColorMap(), type);
  
      sf.setTitle("Plot");
      sf.add(drawPanel, BorderLayout.CENTER);
      sf.pack();
      sf.setSize(width, height);
      sf.setVisible(true);

      //Exit project
      sf.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            System.exit(0);
          }
      });
  }
  
  
}

