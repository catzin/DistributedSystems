
public class EjerciciosSerie1{

    public static void main(String [] args){

    int n1 = 1;
    int n2 = 2;
    int n3 = 3;
    int suma = 0;


    for(int i = 0; i < 20; i++){

        suma = n1+n2+n3;
        n1 = n2;
        n2 = n3;
        n3 = suma;


        System.out.println(suma);
    }

   

    }

    





}