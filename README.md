# ContourLine-FillThePlots
It's a project from course Java 2D3D. It requires students to implement algorithm to draw contour lines and fill the contour plots with JAVA 2D API for give vtk data.

## Read Data 
In this project, data-set format is [vtk](https://vtk.org/). However, vtk java API is not used. 
In package vtkLoad, vertexs(points), faces(triangle cells) and scalar value are read and stored through vtkUnstructuredGridReader.java.
To draw contour line and fill color, colormap are read through ColorMapReader in colormap Package.

## Triangle Mesh
To draw the triangle mesh according to the data, it is necessary to center and rescale the shape into the window first,
then according to the faces data, draw polygon(triangle) of the 3 points of the face. This is implemented in package colormap - MeshPanel.java.

## Contour Lines
Since every points have its scalar value, it is possible to draw contour lines according to these values. Here [Marching squares -Isolines](https://en.wikipedia.org/wiki/Marching_squares) are used
 to draw coutour line. This is also implemented in package colormap - MeshPanel.java.
 
 ## Fill Contour Plots
 Instead of draw lines through Triangle, we fill colors into those triangles to show the scalar field. This is also implemented in package colormap - MeshPanel.java.
