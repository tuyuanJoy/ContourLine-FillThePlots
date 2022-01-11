package vtkLoad;
import java.util.*;  
import java.io.File;  
import java.io.FileNotFoundException;
import java.util.Scanner;

public class vtkUnstructuredGridReader{
    private ArrayList <Point> points;
    private ArrayList <int[]> faces;
    private int numberOfPoints;
    private int numberOfFaces;
    private String path;
    private boolean startLoadingPoint;
    private boolean startLoadingFace;
    private boolean startScalarField;

    public vtkUnstructuredGridReader(String path){
        this.path = path;
        numberOfFaces = 0;
        numberOfPoints = 0;
        startLoadingFace = false;
        startLoadingPoint = false;
        startScalarField = false;
        points = new ArrayList <Point>();
        faces = new ArrayList <int[]> ();
    }
    public ArrayList <Point> Points(){
        return points;
    }
    public int NumberOfPoints(){
        return numberOfPoints;
    }
    public int NumberOfFaces(){
        return numberOfFaces;
    }
    public ArrayList<int[]> Faces(){
        return faces;
    }
 
    public void LoadReader(){
        try{
            File Obj = new File(path);
            Scanner Reader = new Scanner(Obj);
            while (Reader.hasNextLine()){
                String data = Reader.nextLine();
                LoadPoints(data, Reader);
                LoadFaces(data, Reader);
                LoadScalarField(data, Reader);
            }
            Reader.close();
        } catch (FileNotFoundException e){
            System.out.println(path + ": Not found");
            e.printStackTrace();
        }
    }
 
    private void LoadPoints(String data,  Scanner Reader){
        startLoadingPoint = data.indexOf("POINTS ") !=-1? true: false;
        if(startLoadingPoint){ 
            String [] words = data. split(" ", 3);
            for (String word : words){
                if(isNumeric(word)) numberOfPoints = Integer.valueOf(word);
            }
            
            double x, y, z;
            for(int i = 0; i <numberOfPoints; i++){
                while(Reader.hasNextDouble()){
                    x = Reader.hasNextDouble()? Reader.nextDouble(): 0;
                    y = Reader.hasNextDouble()? Reader.nextDouble(): 0;
                    z = Reader.hasNextDouble()? Reader.nextDouble(): 0;
                    points.add(new Point(x,y));
                }
            }
        }
        
    }

    private void LoadFaces(String data, Scanner Reader){
        startLoadingFace = data.indexOf("CELLS ") !=-1? true: false;
        if(startLoadingFace){
            
            if(Reader.hasNextInt()) numberOfFaces = Reader.nextInt();
            int a, b, c;
            for(int i = 0; i < numberOfFaces; i++){
                while(Reader.hasNextInt()){
                    
                    a = Reader.hasNextInt()? Reader.nextInt(): 0;
                    b = Reader.hasNextInt()? Reader.nextInt(): 0;
                    c = Reader.hasNextInt()? Reader.nextInt(): 0;
                    int[] face = {a, b, c};
                    faces.add(face);
                    if(Reader.hasNextInt())Reader.nextInt();
                }
            }
        }
        
    }
  
    private void LoadScalarField(String data, Scanner Reader){
        double maxd = Double.MIN_VALUE;
        startScalarField = data.indexOf("LOOKUP_TABLE ") !=-1? true: false;
        if(startScalarField){
            int count = 0;
            for(count=0; count < numberOfPoints;count++){
               if(Reader.hasNextDouble()) points.get(count).setValue(Reader.nextDouble());
               if(maxd < points.get(count).Field()) maxd =  points.get(count).Field();
            }
            int maxin = (int)Math.round(maxd);
            //normalize scalar to range 0-1
            for(int i=0; i < numberOfPoints;i++){
                double value =  points.get(i).Field();
                points.get(i).setValue(value/maxin);
             }
        }


    }
 
    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }


}