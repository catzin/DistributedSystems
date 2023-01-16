import java.util.*;
public class PruebaRectangulo {

    public static void main (String[] args) {

        PoligonoIrreg p = new PoligonoIrreg();
        Coordenada aux = new Coordenada(0.0,0.0);
        double aux1 = 0;
        double aux2 = 0;

        long nano_startTime = System.nanoTime();
        for(int i = 0; i < 10; i++){
            aux1 = Math.floor(Math.random() * (100 - (-100))+(-100));
            aux2 = Math.floor(Math.random() * (100 - (-100))+(-100));
            aux = new Coordenada(aux1,aux2);
            p.anadeVertice(aux);
      
        }

        
        System.out.println("sin ordenar");
        System.out.println(p.toString());
        p.ordenaVertices();
        System.out.println("ordenado");
        System.out.println(p.toString());
        
     
    }

}