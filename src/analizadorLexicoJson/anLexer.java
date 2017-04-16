/*****************************************/
/*Alumno: Moises Osmar Avalos Torres     */
/*Cedula: 4641375                        */
/*****************************************/


package analizadorLexicoJson;

import java.util.ArrayList;


public class anLexer {
    
        lectorArchivo mi_archivo = new lectorArchivo("fuente.txt");
        
        //variable para almacenar la linea leida
        int linea = 0;
        String aux = "";
        String pre_fuente = "";
        String fuente = "";
        int estado = 0;
        boolean aceptacion;
        ArrayList<String> tokens = new ArrayList<String>();
        ArrayList<String> mensaje_error = new ArrayList<String>();
        
    public anLexer(){
        
        //para ser procesado como un solo String
        while((fuente = mi_archivo.getLinea()) != null){
            fuente += '\n';
            //contador de cantidad de lineas
            linea++;
        
        int x = 0;
        
        while(x < fuente.length()){
            //espacios
            if(fuente.charAt(x)== ' '){
               //no procesar los espacios
               x++;
            }
            
            //salto de linea
            else if(fuente.charAt(x)== '\n'){
                tokens.add("\n");
                x++;
            }
            
            
            //tokens validos
            else if('{' == fuente.charAt(x)){
                tokens.add("L_LLAVE");
                x++;
            }
            
            else if('}' == fuente.charAt(x)){
                tokens.add("R_LLAVE");
                x++;
            }
            
            else if('[' == fuente.charAt(x)){
                tokens.add("L_CORCHETE");
                x++;
            }
            
            else if(']' == fuente.charAt(x)){
                tokens.add("R_CORCHETE");
                x++;
            }
            
            
            else if(',' == fuente.charAt(x)){
                tokens.add("COMA");
                x++;
            }
            
            else if(':' == fuente.charAt(x)){
                tokens.add("DOS_PUNTOS");
                x++;
            }
            
            else if('\"' == fuente.charAt(x)){
                x++;
                //consumir todo el string sin considerar su construccion
                while(!('\"' == fuente.charAt(x)) ){
                    if(Character.isLetter(fuente.charAt(x))){
                        aux += fuente.charAt(x);
                    }
                    
                    x++;
                }
                
                //por el valor de x queda en la ultima comilla
                x++;
                tokens.add("LITERAL_CADENA");
               
            }
            
            //booleanos y null
            else if(Character.isLetter(fuente.charAt(x))){
                //consumir todo el string sin considerar su construccion
                aux = "";
                while(Character.isLetter(fuente.charAt(x)) ){
                    aux += fuente.charAt(x);
                    x++;
                }

                if("TRUE".equals(aux.toUpperCase())){
                    tokens.add("TRUE");
                }else if("FALSE".equals(aux.toUpperCase())){
                    tokens.add("FALSE");
                }else if("NULL".equals(aux.toUpperCase())){
                    tokens.add("NULL");
                }else{
                    mensaje_error.add(aux + "es una construccion no valida. Linea: " + linea);
                }
                
               
            }
            
            //digitos en notacion cientifica sin signo inicial
            else if( Character.isDigit(fuente.charAt(x))){
                aux = "";
                
                estado = 0;
                //si el estado de aceptacion es falso saldra del ciclo
                aceptacion = true;
                while(aceptacion){
                    switch(estado){
                        case 0: //una secuencia de digitos
                            if(Character.isDigit(fuente.charAt(x))){
                                aux += fuente.charAt(x);
                                estado = 0;
                            }else if('.' == fuente.charAt(x)){
                                aux += fuente.charAt(x);
                                estado = 1;
                            }else if(('e' == fuente.charAt(x)) || ('E' == fuente.charAt(x))){
                                aux += fuente.charAt(x);
                                estado = 3;
                            }else{
                                estado = -1;//error
                            }
                            break;
                        case 1://un punto, debe seguir un digito  o simbolo + -
                            if(Character.isDigit(fuente.charAt(x))){
                                aux+=fuente.charAt(x);
                                estado = 2;
                            }else if(('+' == fuente.charAt(x)) || ('-' == fuente.charAt(x))){
                                aux+=fuente.charAt(x);
                                estado = 2;
                            }else{
                                estado = -1; //error
                            }
                            break;
                        case 2:
                            if(Character.isDigit(fuente.charAt(x))){
                                aux+=fuente.charAt(x);
                                estado = 2;
                            }else if(('e' == fuente.charAt(x)) || ('E' == fuente.charAt(x))){
                                aux += fuente.charAt(x);
                                estado = 3;
                            }else{
                                estado = -1; //error
                            }
                            break;
                        case 3:
                            if(('+' == fuente.charAt(x)) || ('-' == fuente.charAt(x))){
                                aux += fuente.charAt(x);
                                estado = 4;
                            }else if(Character.isDigit(fuente.charAt(x))){
                                aux += fuente.charAt(x);
                                estado = 5;
                            }else{
                                estado = -1; //error
                            }
                            break;
                        
                        case 4:
                            if(Character.isDigit(fuente.charAt(x))){
                                aux += fuente.charAt(x);
                                estado = 5;
                            }else {
                                estado = -1; //error
                            }
                            break;
                        case 5:
                            if(Character.isDigit(fuente.charAt(x))){
                                aux += fuente.charAt(x);
                                estado = 5;
                            }else{
                                aceptacion = false;//terminara el ciclo
                            }
                            break;
                        default:
                                aceptacion = false;
                    }//fin switch
                    
                    x++;//para leer el siguiente digito
                    
                }//fin while
                x++;
                tokens.add("LITERAL_NUM");
            }//fin si ..es digito..
            else{
                mensaje_error.add("Error. no se reconoce el caracter: "+ fuente.charAt(x)+ "Linea: "+linea);
            }
            
        }//fin while que consume caracter a caracter
    }//fin while leer linea
    
    //generar un archivo
    new Archivo(tokens);
        
    for(int k = 0 ; k < tokens.size() ; k++){
        System.out.print(' '+tokens.get(k));
    }
    
    for(int k = 0 ; k < mensaje_error.size() ; k++){
        System.out.print(mensaje_error.get(k));
    }
    
    }
    public static void main(String[] args) {
        new anLexer();
    }
}

