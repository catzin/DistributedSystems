import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;

class Main2{

    public static void main(String[] args){
        ArrayList<String> arrCurps = new ArrayList<String>();
        Scanner entrada = new Scanner(System.in);
        int n = 0;
   
        System.out.println("Ingresa numero de curps a generar");
        n = entrada.nextInt();

        insertCurps(arrCurps,n);

        showArr(arrCurps);
       

    }

    static void insertCurps(ArrayList<String> arrCurps, int curps){
        Iterator<String> aux = arrCurps.iterator();
        int ini, tope = 0;
        String curp = "";
        int i = 0;
        for(i = 0; i < curps; i++){
            ini = 0;
            tope = 0;
            aux = arrCurps.iterator();
            curp = getCURP();
            if(!aux.hasNext()){
                arrCurps.add(curp);
            } else {
                while (aux.hasNext()) {
                    String encolaCurp = aux.next();
                    if(curp.compareTo(encolaCurp) < 0) {
                        arrCurps.add(ini, curp);
                        tope = 1;
                        break;
                    }
                    ini++;
                }
                if (tope == 0) {
                    arrCurps.add(curp);
                }
            }
        }

    }

    static void showArr(ArrayList<String> arr){
        for(String i : arr){
            System.out.println(i);
        }
    }

    // Función para generar una CURP aleatoria
    static String getCURP(){

        String Letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String Numero = "0123456789";

        String Sexo = "HM";

        String Entidad[] = {"AS", "BC", "BS", "CC", "CS", "CH", "CL", "CM", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TL", "TS", "VZ", "YN", "ZS"};

        int indice;

        

        StringBuilder sb = new StringBuilder(18);

        

        for (int i = 1; i < 5; i++) {

            indice = (int) (Letra.length()* Math.random());

            sb.append(Letra.charAt(indice));        

        }

        

        for (int i = 5; i < 11; i++) {

            indice = (int) (Numero.length()* Math.random());

            sb.append(Numero.charAt(indice));        

        }

 

        indice = (int) (Sexo.length()* Math.random());

        sb.append(Sexo.charAt(indice));        

        

        sb.append(Entidad[(int)(Math.random()*32)]);

 

        for (int i = 14; i < 17; i++) {

            indice = (int) (Letra.length()* Math.random());

            sb.append(Letra.charAt(indice));        

        }

 

        for (int i = 17; i < 19; i++) {

            indice = (int) (Numero.length()* Math.random());

            sb.append(Numero.charAt(indice));        

        }

    
        return sb.toString();

    }           

}