

import java.util.Arrays;
import java.util.List;
//Clase llamada Application
public class Application {
    //cadenas de direcciones de los endpoints para dos servidores 

    private static final String WORKER_ADDRESS_1 = "http://localhost:8080/task"; 



    public static void main(String[] args) {
        //Instancia del Objeto Aggregator 
        Aggregator aggregator = new Aggregator();
        

        Demo task1 = new Demo(1757600,"IPN");
   
        //El método sentToWorkers correspondiente a la clase aggregator envia las tareas , posee dos
        //listas una para los trabajadores y otra para las tareas  
        List<String> results = aggregator.sendTasksToWorkers(

                Arrays.asList(WORKER_ADDRESS_1),
                Arrays.asList(task1)
                
                );
         //Hace un recorrido de las valores almacenados en resultado y los imprime 
        /*for (String result : results) {
            System.out.println(result);
        }*/
    }
}