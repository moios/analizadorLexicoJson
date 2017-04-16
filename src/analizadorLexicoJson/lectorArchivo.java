
package analizadorLexicoJson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class lectorArchivo {
   private String linea_leida;
   private File archivo;
   private FileReader fr;
   private BufferedReader br;
   
   //constructor que recibe una ruta de archivo
   public lectorArchivo(String ruta_archivo){
   
       archivo = new File(ruta_archivo);
       
       try{
       fr = new FileReader(archivo);
       
       }catch(FileNotFoundException e){
           System.out.println("No se pudo leer el archivo :( ");
           System.exit(0);
       }
       //en caso de encontrar el archivo
       //se lee la primera linea del archivo
       br = new BufferedReader(fr);
   }   
       public String getLinea(){
       try {
           while((linea_leida=br.readLine())!= null){
               //se retorna una linea cada vez que se invoca
               return linea_leida;
           }
           return null;
       } catch (IOException ex) {
           System.out.println("Error al intentar leer");
           
       }
       return null;
       }
   }
