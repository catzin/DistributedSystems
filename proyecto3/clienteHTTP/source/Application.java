/** PROYECTO 3 - Ehecatzin Vallejo Serrano - 4CM14*/
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList; 
import java.util.stream.*; 

public class Application {
    //cadenas de direcciones de los endpoints para dos servidores 
    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/searchtoken"; 
    private static final String WORKER_ADDRESS_2 = "http://localhost:8082/searchtoken"; 
    private static final String WORKER_ADDRESS_3 = "http://localhost:8083/searchtoken"; 


    private static  List<String> taskToDo = new ArrayList<String>();

    public static void main(String[] args) {
     
        List<String> tasks = new ArrayList<String>();
        Aggregator aggregator = new Aggregator();

        taskToDo = aggregator.buildPayloads();

        System.out.println("Tareas a ejecutar por el servidor:\n");
      
        for(String k : taskToDo){
            System.out.println(k);
        }
    
        //El método sentToWorkers correspondiente a la clase aggregator envia las tareas , posee dos
        //listas una para los trabajadores y otra para las tareas  
        List<String> results = aggregator.sendTasksToWorkers(
                Arrays.asList(WORKER_ADDRESS_1,WORKER_ADDRESS_2,WORKER_ADDRESS_3),
                taskToDo
                );


        int counter = 0;
        System.out.println("- - - - - Listadp: - - - - -");
        List<String> Final = results.stream().sorted().collect(Collectors.toList());    
        for (String result : results) {
            System.out.println(result);
    
        }

   
    }
}