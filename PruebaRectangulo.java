import java.util.*;
public class PruebaRectangulo {

    public static void main (String[] args) {

        PoligonoIrreg p = new PoligonoIrreg(10000000);
        double aux1 = 0;
        double aux2 = 0;

        long nano_startTime = System.nanoTime();
        for(int i = 0; i < 10000000; i++){
            aux1 = Math.floor(Math.random() * 20);
            aux2 = Math.floor(Math.random() * 20);
            p.anadeVertice(new Coordenada(aux1,aux2));
        }
        long nano_endTime = System.nanoTime();
         long elapsedTime = (nano_endTime - nano_startTime);
         double elapsedTimeInSecondX = (double) elapsedTime * 000000000.1;
         System.out.println(elapsedTimeInSecondX + " segundos");

     
    }

}