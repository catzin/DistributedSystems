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

//Librerias para construir un servidor en java
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
public class WebServer {
  // se definen los dos posibles paths que puede recirbir el servidor
  private static final String TASK_ENDPOINT = "/task";
  private static final String STATUS_ENDPOINT = "/status";
  private static final String SEARCHTOKEN_ENDPOINT = "/searchtoken";
  private final int port; //Variable para el puerto
  private HttpServer server; //Implementa un servidor HTTP sencillo
  public static void main(String[] args) {
    int serverPort = 8080; //puerto default del servidor
    if (args.length == 1) {
      serverPort = Integer.parseInt(args[0]); //Si se escribe un puerto acá recibe los parámetros
    }
    WebServer webServer = new WebServer(serverPort); //se instancia el objeto webserver
    webServer.startServer(); //se llama el método startServer
    System.out.println("Servidor escuchando en el puerto " + serverPort);
  }
  public WebServer(int port) {
    this.port = port; //constructor que recibe el puerto e inicia la variable privada port
  }
  public void startServer() {
    try {
      this.server = HttpServer.create(new InetSocketAddress(port), 0);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    HttpContext statusContext = server.createContext(STATUS_ENDPOINT); //representa un mapeo entree el identificador uniforme de recursos, una aplicación y un http handler
    HttpContext taskContext = server.createContext(TASK_ENDPOINT); //crea un objeto HTTP context si un HTTP handler asociado pero con la ruta relativa asignada
    HttpContext searchContext = server.createContext(SEARCHTOKEN_ENDPOINT); //representa un mapeo entree el identificador uniforme de recursos, una aplicación y un http handler
    statusContext.setHandler(this::handleStatusCheckRequest); //Recibe el método que implementa el manejador y vincula el handler para dicho contexto si no se ha inicializado
    taskContext.setHandler(this::handleTaskRequest);
    searchContext.setHandler(this::handleSearchCheckRequest);
    server.setExecutor(Executors.newFixedThreadPool(8)); //es necesario esta instrucción antes de iniciar el servidor con un total de 8 hilos
    server.start(); // se ocupa un hilo para crear el servidor
  }
  private void handleTaskRequest(HttpExchange exchange) throws IOException { //encapsula todo lo relacionado entre la comunicación del servidor y el cliente
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) { //recibe el método y se verifica si corresponde al método POST
      exchange.close(); //si no corresponde lo cierra
      return;
    }
    Headers headers = exchange.getRequestHeaders(); // Recupera Todos los headers del exchange
    if (
      headers.containsKey("X-Test") &&
      headers.get("X-Test").get(0).equalsIgnoreCase("true") //si los header coinciden con estos parametros y con el valor true
    ) {
      String dummyResponse = "123\n"; // se imprime la cadena 123
      sendResponse(dummyResponse.getBytes(), exchange); //
      return;
    }
    boolean isDebugMode = false;
    if (
      headers.containsKey("X-Debug") &&
      headers.get("X-Debug").get(0).equalsIgnoreCase("true") //si los header coinciden con estos parametros y con el valor true
    ) {
      isDebugMode = true;
    }
    long startTime = System.nanoTime(); //se inicia el conteo del tiempo de ejecución
    byte[] requestBytes = exchange.getRequestBody().readAllBytes(); //se recuppera el mensaje del body y lo almacena en una cadena de bytes
    byte[] responseBytes = calculateResponse(requestBytes); //se envain los numeros a la función calculateResponse
    long finishTime = System.nanoTime(); //se finaliza el conteo del tiempo de ejecución
    if (isDebugMode) {
      String debugMessage = String.format(
        "La operación tomó %d nanosegundos",
        finishTime - startTime
      );
      exchange
        .getResponseHeaders()
        .put("X-Debug-Info", Arrays.asList(debugMessage));
    }
    sendResponse(responseBytes, exchange);
  }
  private byte[] calculateResponse(byte[] requestBytes) {
    String bodyString = new String(requestBytes);
    String[] stringNumbers = bodyString.split(",");
    BigInteger result = BigInteger.ONE;
    for (String number : stringNumbers) {
      BigInteger bigInteger = new BigInteger(number);
      result = result.multiply(bigInteger);
    }
    return String
      .format("El resultado de la multiplicación es %s\n", result)
      .getBytes();
  }
  private void handleStatusCheckRequest(HttpExchange exchange)
    throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }
    String responseMessage = "El servidor está vivo\n";
    sendResponse(responseMessage.getBytes(), exchange);
  }
  private void handleSearchCheckRequest(HttpExchange exchange)
    throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      exchange.close();
      return;
    }
    Headers headers = exchange.getRequestHeaders(); // Recupera Todos los headers del exchange
    boolean isDebugMode = false;
    if (
      headers.containsKey("X-Debug") &&
      headers.get("X-Debug").get(0).equalsIgnoreCase("true") //si los header coinciden con estos parametros y con el valor true
    ) {
      isDebugMode = true;
    }
    long startTime = System.nanoTime();
    byte[] requestBytes = exchange.getRequestBody().readAllBytes(); //se recuppera el mensaje del body y lo almacena en una cadena de bytes
    String bodyString = new String(requestBytes);
    String[] stringNumbers = bodyString.split(",");
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    String cad, palabra = stringNumbers[1].toUpperCase();
    long cant = Long.parseLong(stringNumbers[0]);
    int count = 0, x = 0;
    for (int i = 0; i <= cant; i++) {
      if (i == cant) {
        sb.append(
          (char) (random.nextInt(21) + 'A') +
          "" +
          (char) (random.nextInt(21) + 'A') +
          "" +
          (char) (random.nextInt(21) + 'A')
        );
      } else {
        sb.append(
          (char) (random.nextInt(21) + 'A') +
          "" +
          (char) (random.nextInt(21) + 'A') +
          "" +
          (char) (random.nextInt(21) + 'A') +
          " "
        );
      }
    }
    cad = sb.toString();
    for (int i = 0; i < cad.length(); i++) {
      x = cad.indexOf(palabra, i);
      if (x != -1) {
        i = x + 1;
        count++;
      } else {
        break;
      }
    }
    long finishTime = System.nanoTime();
    if (isDebugMode) {
      String debugMessage = String.format("La operación tomo: "+(finishTime - startTime) + " nanosegundos = " + 
      Math.floor((finishTime - startTime)/1000000000) + " segundos con " + ((finishTime - startTime)%1000000000)/1000000 + 
      " milisegundos"); //se restan los tiempos final e inicial de ejecucion y devolvemos esto mas adelante en el header
      exchange
        .getResponseHeaders()
        .put("X-Debug-Info", Arrays.asList(debugMessage)); // anexamos el tiempo de ejecucion en el header
    }
    byte[] responseBytes = String
      .format(
        "numero de apariciones es %s: %d",
        stringNumbers[1].toUpperCase(),
        count
      )
      .getBytes();
    sendResponse(responseBytes, exchange);
  }
  private void sendResponse(byte[] responseBytes, HttpExchange exchange)
    throws IOException {
    exchange.sendResponseHeaders(200, responseBytes.length);
    OutputStream outputStream = exchange.getResponseBody();
    outputStream.write(responseBytes);
    outputStream.flush();
    outputStream.close();
    exchange.close();
  }
}
