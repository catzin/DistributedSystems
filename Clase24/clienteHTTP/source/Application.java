

import java.util.Arrays;
import java.util.List;
//Clase llamada Application
public class Application {
    //cadenas de direcciones de los endpoints para dos servidores 
    /*private static final String WORKER_ADDRESS_1 = "http://34.125.123.233:80/searchtoken"; 
    private static final String WORKER_ADDRESS_2 = "http://34.125.41.97:80/searchtoken"; 
    private static final String WORKER_ADDRESS_3 = "http://34.125.244.48:80/searchtoken"; 
    private static final String WORKER_ADDRESS_4 = "http://34.125.92.127:80/searchtoken"; */

    private static final String WORKER_ADDRESS_1 = "http://34.125.99.181:80/searchtoken"; 
    private static final String WORKER_ADDRESS_2 = "http://34.125.144.211:80/searchtoken"; 
    private static final String WORKER_ADDRESS_3 = "http://34.125.41.97:80/searchtoken"; 
    private static final String WORKER_ADDRESS_4 = "http://34.125.192.202:80/searchtoken";

    public static void main(String[] args) {
        //Instancia del Objeto Aggregator 
        Aggregator aggregator = new Aggregator();
        //Inicialización de las cadenas de los facotes que se multiplicaran en cada servidor 
        String task1 = "1757600,IPN";
        String task2 = "1757600,SAL";
        String task3 = "1757600,MAS";
        String task4 = "1757600,ELE";
             //El método sentToWorkers correspondiente a la clase aggregator envia las tareas , posee dos
             //listas una para los trabajadores y otra para las tareas  
        List<String> results = aggregator.sendTasksToWorkers(

                Arrays.asList(WORKER_ADDRESS_1,WORKER_ADDRESS_2,WORKER_ADDRESS_3,WORKER_ADDRESS_4),
                Arrays.asList(task1, task2, task3, task4)
                
                );
         //Hace un recorrido de las valores almacenados en resultado y los imprime 
        for (String result : results) {
            System.out.println(result);
        }
    }
}