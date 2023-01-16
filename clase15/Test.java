// Java program to illustrate
// ThreadPool
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Task class to be executed (Step 1)
class Task implements Runnable {

  //7346
  private String name;

  private String ruta = "/Users/catzin/Documents/distribuidos/clase15";
  Map<Character, Integer> map = new HashMap<>();

  public Task(String s) {
    name = s;
  }

  // Prints task name and sleeps for 1s
  // This Whole process is repeated 5 times
  synchronized public void run() {
    String auxRoute = "";
    int i = 0;

    try {
      for (i = 1; i <= 5; i++) {
        switch (i) {
          case 1:
       

            try {
              File file = new File(
                "/Users/catzin/Documents/distribuidos/clase15/1.txt"
              );
              Scanner sc = new Scanner(file);

              while (sc.hasNextLine()) {
                char[] line = sc.nextLine().toCharArray();

                for (char caract : line) {
                  if (map.containsKey(caract)) {
                    map.put(caract, map.get(caract) + 1);
                  } else {
                    map.put(caract, 1);
                  }
                }
              }
            } catch (IOException e) {
              System.out.println(e);
            }
			System.out.println(map);
            break;
			

			//inicio map2
          case 2:
		  
            try {
              File file1 = new File(
                "/Users/catzin/Documents/distribuidos/clase15/2.txt"
              );
              Scanner sc1 = new Scanner(file1);

              while (sc1.hasNextLine()) {
                char[] line = sc1.nextLine().toCharArray();

                for (char caract : line) {
                  if (map.containsKey(caract)) {
                    map.put(caract, map.get(caract) + 1);
                  } else {
                    map.put(caract, 1);
                  }
                }
              }
            } catch (IOException e) {
              System.out.println(e);
            }

			System.out.println(map);
            break;
		
			//fin map 2


			//inicia map3
			case 3:
            try {
              File file2 = new File(
                "/Users/catzin/Documents/distribuidos/clase15/3.txt"
              );
              Scanner sc2 = new Scanner(file2);

              while (sc2.hasNextLine()) {
                char[] line = sc2.nextLine().toCharArray();

                for (char caract : line) {
                  if (map.containsKey(caract)) {
                    map.put(caract, map.get(caract) + 1);
                  } else {
                    map.put(caract, 1);
                  }
                }
              }
            } catch (IOException e) {
              System.out.println(e);
            }

            break;
      
			//fin map3

			//inicio map4

			case 4:
            try {
              File file3 = new File(
                "/Users/catzin/Documents/distribuidos/clase15/4.txt"
              );
              Scanner sc3 = new Scanner(file3);

              while (sc3.hasNextLine()) {
                char[] line = sc3.nextLine().toCharArray();

                for (char caract : line) {
                  if (map.containsKey(caract)) {
                    map.put(caract, map.get(caract) + 1);
                  } else {
                    map.put(caract, 1);
                  }
                }
              }
            } catch (IOException e) {
              System.out.println(e);
            }

            break;
        
			//fin map4

			//inicio map5

			case 5:
            try {
              File file4 = new File(
                "/Users/catzin/Documents/distribuidos/clase15/5.txt"
              );
              Scanner sc4 = new Scanner(file4);

              while (sc4.hasNextLine()) {
                char[] line = sc4.nextLine().toCharArray();

                for (char caract : line) {
                  if (map.containsKey(caract)) {
                    map.put(caract, map.get(caract) + 1);
                  } else {
                    map.put(caract, 1);
                  }
                }
              }
            } catch (IOException e) {
              System.out.println(e);
            }

            break;
		

			//fin map5


        
        }
	  
	  //Thread.sleep(1000);  
		
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

public class Test {

  // Maximum number of threads in thread pool
  static final int MAX_T = 3;

  public static void main(String[] args) throws IOException {
    // creates five tasks
    Runnable r1 = new Task("task 1");
    Runnable r2 = new Task("task 2");
    Runnable r3 = new Task("task 3");
    Runnable r4 = new Task("task 4");
    Runnable r5 = new Task("task 5");

    // creates a thread pool with MAX_T no. of
    // threads as the fixed pool size(Step 2)
    ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

    // passes the Task objects to the pool to execute (Step 3)
    pool.execute(r1);
    pool.execute(r2);
    pool.execute(r3);
    pool.execute(r4);
    pool.execute(r5);

    pool.shutdown();
	}
}