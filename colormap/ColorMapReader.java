package colormap;
import java.util.ArrayList;
import java.util.*;  
import java.io.File;  
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ColorMapReader{
    private ArrayList<ColorMap> colormap;
    private String path;
    private double[] isovaules;

    public ColorMapReader(String path, double[] isovaules){
        this.path = path;
        colormap = new ArrayList <ColorMap> ();
        this.isovaules = isovaules;
    }
    public ArrayList<ColorMap> ColorMap(){
        return colormap;
    }
    public double[] isovaules(){
        return isovaules;
    }
    public void LoadColorMapReader(){
        try{
            File Obj = new File(path);
            Scanner Reader = new Scanner(Obj);
            Reader.nextLine();
            while (Reader.hasNextLine()){
                 String data = Reader.nextLine();
                double isovalue;
                double r;
                double g ;
                double b ;
                ArrayList<String> numbers = new ArrayList<>(Arrays.asList(data.split(",")));
                isovalue = Double.parseDouble(numbers.get(0));
                r = Double.parseDouble(numbers.get(1));
                g = Double.parseDouble(numbers.get(2));
                b = Double.parseDouble(numbers.get(3));
            
                //Rescale color from [0-1] t0 [0-255]
                int red, blue, green;
                red = (int)(r*255 );
                blue = (int)(b*255 );
                green = (int)(g*255 );
                
                colormap.add(new ColorMap(isovalue,red,green,blue));
            }
            Reader.close();
        } catch (FileNotFoundException e){
            System.out.println(path + ": Not found");
            e.printStackTrace();
        }
        
    }


}