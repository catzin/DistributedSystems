import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.text.SimpleDateFormat; 
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class HttpClientSynchronous implements Runnable {
    public static final int MAX_T = 4;
    public static int m;
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(100))
            .build();
    public void run() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString("1757600,IPN"))
                .uri(URI.create("http://192.168.5.171:8080/searchtoken"))
                .setHeader("X-Debug", "true")
                .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HttpHeaders headers = response.headers();
            headers.map().forEach((x, y) -> System.out.println(x + ":" + y));
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        m = Integer.parseInt(args[0]);
        Runnable [] threads = new Runnable[m]; //hilos 4,8,
        for(int i = 0; i < m; i++){
            threads[i] = new HttpClientSynchronous();
        }
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
        for(int j = 0; j < m; j++){
            System.out.println("Ejecutando hilo"+ (j+1));
            pool.execute(threads[j]);
        }

        pool.shutdown();

    }
}
