/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import networking.WebClient;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Aggregator {
    //Objeto WebClient 
    private WebClient webClient;
    //Se instancia el objeto WebClient por medio de su constructor 
    public Aggregator() {
        this.webClient = new WebClient();
    }
    //Metodo de la clase Aggregator que recibe una lista de los trabajadores y lista de las tareas 
    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<Demo> tasks) {

        //Manejo de la comunicación asincrona 
        CompletableFuture<String>[] futures = new CompletableFuture[tasks.size()];
         //Iteracion sobre todos los elementos en la lista para obtener las direcciones de cada uno de los trabajadores y tareas
        System.out.println("En el método sendTasksToWorkers se asignaron las siguientes tareas a los servidores:");
        for (int i = 0; i < 1; i++) {
            String workerAddress = workersAddresses.get(i);
     
            Demo task = tasks.get(i);
            
            byte[] serializado = SerializationUtils.serialize(task);
            //Almacenamiento de las tareas en formato byte
            //byte[] requestPayload = task.getBytes();
            //Envio de las tareas asincornas
            futures[i] = webClient.sendTask(workerAddress, serializado);
            System.out.println("Servidor: " + workersAddresses + " -> Tarea: " + task.showInfo());
        }

        
        // Evalúa continuamente si uno de los servidores ha terminado.
        boolean bandera = true;
        while (bandera) {
            for (int j = 0; j < 1; j++) {
                if (true == futures[j].isDone()) {
                    bandera = false;
                    Demo task = tasks.get(j); //obtiene las tareas
                    //byte[] requestPayload = task.getBytes();
                    byte[] serializado = SerializationUtils.serialize(task);
                    //futures[j] = webClient.sendTask(workersAddresses.get(j), serializado);
                    System.out.println("El primer servidor en terminar fue: "+workersAddresses.get(j)+". Y se le asigna la tarea: "+task.showInfo());
                }
            }
        }

        //Declaracion de lista de resultados 
        List<String> results = new ArrayList();
        //Se van agragando los resultados a la lista 
        for (int i = 0; i < 1; i++) {
            results.add("Para la tarea " + tasks.get(i) + " El " +futures[i].join());
        }
        //regreso de respuestas asincronas 
        return results;
    }
}
