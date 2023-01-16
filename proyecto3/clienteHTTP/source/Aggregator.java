/** PROYECTO 3 - Ehecatzin Vallejo Serrano - 4CM14*/
import networking.WebClient;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Aggregator {

    private WebClient webClient;
    public Aggregator() {
        this.webClient = new WebClient();
    }

    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks) {
        System.out.println("Tasks size:"+tasks.size());
        CompletableFuture<String>[] futures = new CompletableFuture[tasks.size()];
        List<String> results = new ArrayList();
        String workerAddress = "";
        int j = 0;
        String task = "";
        String tarea = "";
        boolean bandera = false;

        for (int i = 0; i < tasks.size(); i++){

            task = tasks.get(i);
            byte [] requestPayload = task.getBytes();
        

            if(i < 3){

                workerAddress = chooseSever(workersAddresses,i)
                futures[i] = webClient.sendTask(workerAddress,requestPayload);
                System.out.println("Server:"+workerAddress+","+"tarea:"+task+"\n");

            }
            else{

                bandera = true;

                while(bandera){
                
                    if(futures[j].isDone()){
                        
                        String res = futures[j].join();
                        System.out.println(res);
                        results.add(res);
                        workerAddress = chooseSever(workersAddresses,j);
                        System.out.println("Asignando:"+tasks.get(i)+" a: "+workerAddress);
                        futures[j] = webClient.sendTask(workerAddress,requestPayload);
                        bandera = false;
                    }
                    else{
                        j++;
                        if(j >= workersAddresses.size()){
                            j = 0;
                        }
                    }
                }

            }
        }
    
        return results;
    }

   


    public int generateNTokens(int min , int max){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }

    public List<String> buildPayloads(){

        List<String> taskToDo = new ArrayList<String>();
        StringBuilder str = new StringBuilder();
       
        for(int i = 65; i <= 90; i++){
            String aux = String.valueOf(generateNTokens(1757600,17576000))+","+str.append((char) i).append((char)i).append((char)i).toString();
            str.setLength(0);
           
            taskToDo.add(aux);
        }

        return taskToDo;

    }

    public String chooseSever(List<String> workersAddresses, int k){
        return workersAddresses.get(k);
    }
}
