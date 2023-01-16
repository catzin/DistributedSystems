import java.util.Arrays;

public class Main{

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
        char [] cadenota = new char[n * 4];
        Main.initArr(cadenota);
        int siguiente = 0;
        int j = -1;
        int cont = 0;
       
        for(int k = 0; k < n; k++){
            char [] aux = Main.generaCadena(3);             
            siguiente = Main.nextDat(cadenota);
            for(int i = siguiente; i <= siguiente + 3; i++){
                j++;
                cadenota[i] = aux[j];
            }
            j = -1;
        }
        long nano_startTime = System.nanoTime();
        for(int x = 0; x < n; x++){
            if(cadenota[x*4] == 'i' && cadenota[x*4+1] == 'p' && cadenota[x*4+2] == 'n'){
                cont++;
            }
        }
        long nano_endTime = System.nanoTime();
        long elapsedTime = (nano_endTime - nano_startTime);
        double elapsedTimeInSecondX = (double) elapsedTime * 000000000.1;

        System.out.println(cadenota);
        System.out.println("IPN aparece : "+cont);
        System.out.println(elapsedTimeInSecondX + " seconds");

       
        


  
    }
}