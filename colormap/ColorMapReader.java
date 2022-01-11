package colormap;
import java.util.ArrayList;
import java.util.*;  
import java.io.File;  
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ColorMapReader{
    private ArrayList<ColorMap> colormap;
    private String path;

    public ColorMapReader(String path){
        this.path = path;
        colormap = new ArrayList <ColorMap> ();
    }
    public ArrayList<ColorMap> ColorMap(){
        return colormap;
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