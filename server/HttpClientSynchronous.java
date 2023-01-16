import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientSynchronous{

    //este es el codigo para declarar un cliente
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    public static void main(String [] args) throws IOException, InterruptedException{

        //objeto para construir la cabecera
        // tipo de metodo,url, el header 17576000


        /*HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString("IPN,17576"))
                .uri(URI.create("http://192.168.1.82:8080/searchtoken"))
                .setHeader("X-Debug", "true") // add request header
                .build();*/

    HttpRequest request2 = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString("1757600,ZZZ"))
                .uri(URI.create("http://localhost:8081/searchtoken"))
                .setHeader("X-Debug", "false") // add request header
                .build();
        //HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());

        // print response headers
        //HttpHeaders headers = response.headers();
        //headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
         //System.out.println(response.statusCode());
        HttpHeaders headers2 = response2.headers();
        headers2.map().forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println(response2.body().toString());
        System.out.println(response2.statusCode());
        // print status code
       
       
        

    }
}