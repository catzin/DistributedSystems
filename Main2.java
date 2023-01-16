import java.util.Arrays;

public class Main2{

    int contarCaracteres(String cadena, String caracter) {
      
        int posicion, contador = 0;
        posicion = cadena.indexOf(caracter);
        while (posicion != -1){
            contador++;          
                                         
            posicion = cadena.indexOf(caracter, posicion + 1);
        }


        return contador;
}

    public static char[] generaCadena(int longitud){
        char [] palabra = new char[4];

        for(int i=0; i < longitud; i++){
			int codigoAscii = (int)Math.floor(Math.random()*(122 - 97)+97);
		    palabra[i] = (char)codigoAscii;
		}
        palabra[3] = ' ';

        return palabra;
      
    }

    public static void initArr(char [] arr){
        for(int i = 0; i < arr.length; i++){
            arr[i] = '-';
        }
    }

    public static int nextDat(char [] arr){
        int sig = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] != '-'){
                sig++;
            }
        }

        return sig;
    }

    public static void main(String [] args){
        
        int n = Integer.parseInt(args[0]);
        char [] inicial = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder cadenota = new StringBuilder();
        int siguiente = 0;
        int j = -1;
        int posicion, contador = 0;

        
        for(int k = 0; k < n; k++){
            cadenota.append(Main.generaCadena(3));
        }
        long nano_startTime = System.nanoTime();
        posicion = cadenota.indexOf("ipn");
        while (posicion != -1){
            contador++;          
                                         
            posicion = cadenota.indexOf("ipn", posicion + 1);
        }
        long nano_endTime = System.nanoTime();
        long elapsedTime = (nano_endTime - nano_startTime);
        double elapsedTimeInSecondX = (double) elapsedTime * 000000000.1;
    

        System.out.println(cadenota);
        System.out.println("IPN aparece : "+contador);
        System.out.println(elapsedTimeInSecondX + " seconds");

       
        


  
    }
}