
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class WebServer {

  // Cadenas que corresponden a los 2 endpoint del Server
  private static final String TASK_ENDPOINT = "/task";
  private static final String STATUS_ENDPOINT = "/status";
  private static final String SEARCH_ENDPOINT = "/searchtoken";
  private static final String POLIG_ENDPOINT = "/poligono";
  private final int port; // Puerto que escucha
  private HttpServer server; // Incluye un servidor HTTP básico

  public static void main(String[] args) {
    // Se define un puerto default para el servidor
    int serverPort = 8080;
    // Mediante la terminal se puede escribir como parámetro otro puerto.
    // Si se da el nuevo puerto, se le asigna a la variable serverPort.
    if (args.length == 1) {
      serverPort = Integer.parseInt(args[0]);
    }
    // Se instancia un objeto WebServer en el puerto asociado
    WebServer webServer = new WebServer(serverPort);
    // Se inicia el método principal para iniciar la configuración del Server
    webServer.startServer();
    // Se imprime el puerto en donde está escuchando el servidor
    System.out.println("Servidor escuchando en el puerto " + serverPort);
  }

  // Constructor del WebServer
  public WebServer(int port) {
    // Recibe e inicializa la variable puerto
    this.port = port;
  }

  // Método principal de la clase WebServer
  public void startServer() {
    try {
      // Objeto HTTPServer con instancia de socket TCP vinculada a una IP y un puerto.
      this.server = HttpServer.create(new InetSocketAddress(port), 0);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    /* Representa un mapeo para el identificador uniforme de recursos, la aplicación, y un
            HTTPHandler. */
    /* createContext crea objetos HTTPContext y HTTPHandler que se asocia con la ruta
            relativa asignada. */
    HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
    HttpContext taskContext = server.createContext(TASK_ENDPOINT);
    HttpContext searchContext = server.createContext(SEARCH_ENDPOINT);
    HttpContext taskPolig = server.createContext(POLIG_ENDPOINT);
    /* setHandler recibe como parámetro el método que implementa el manejador y vincula el
            Handler para dicho contexto si no se ha inicializado ya. */
    statusContext.setHandler(this::handleStatusCheckRequest);
    taskContext.setHandler(this::handleTaskRequest);
    searchContext.setHandler(this::handleSearchRequest);
    taskPolig.setHandler(this::handlePoligRequest);
    // El método setExecutor establece un objeto ejecutor al servido, este es necesario antes de iniciar.
    // Se agrega un ThreadPool de 8 hilos y se deja al ejecutor la labor de iniciarlos y asignarles
    // tareas.
    server.setExecutor(Executors.newFixedThreadPool(8));
    // Se inicia la ejecución del servidor en un hilo nuevo en segundo plano.
    server.start();
  }

  public double generateCoor(){
        Random rnd = new Random();
        return rnd.nextInt();
    }

  /**TASK POLIGONO*/

  private void handlePoligRequest(HttpExchange exchange) throws IOException {
    // Se obtiene el método y se verifica si el método corresponde al método POST.
    PoligonoIrreg objeto = null;
  
   
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      // Si no cumple, cierra el exchange y regresa del método.
      exchange.close();
      return;
    }
    // getRequestHeaders recupera del exchange todos los headers con ayuda de un map.
    Headers headers = exchange.getRequestHeaders();
    // Si hay un header X-Test con valor asociado de true.
    if (
      headers.containsKey("X-Test") &&
      headers.get("X-Test").get(0).equalsIgnoreCase("true")
    ) {
      // Se genera la respuesta dummy 123 conocida y se envía mediante sendResponse.
      String dummyResponse = "123\n";
      sendResponse(dummyResponse.getBytes(), exchange);
      return;
    }
    boolean isDebugMode = false; // Si no, se queda isDebugMode con valor false.
    // Si hay un header X-Debug con valor asociado de true.
    if (
      headers.containsKey("X-Debug") &&
      headers.get("X-Debug").get(0).equalsIgnoreCase("true")
    ) {
      // Se inicializa isDebugMode, valor = true.
      isDebugMode = true;
      // Si es true se conecta una serie de información de depuración que se puede mandar de regreso.
    }
    // Se devuelve la cantidad de tiempo que tomó el procesamiento como información de depuración.
    // Toma el tiempo de inicio del tiempo de procesamiento.
    long startTime = System.nanoTime();
    /* 
            getRequestBody recupera toda la información del cuerpo del mensaje de la transacción HTTP.
            Se almacena esa información como bytes, se almacenan los 2 números que se quieren multiplicar.
            calculateResponse realiza las operaciones de multiplicación de esos números y se almacena en
            responseBytes. 
        */

    //System.out.println("recibo:"+ objeto.showInfo());
    byte[] requestBytes = exchange.getRequestBody().readAllBytes();

    //byte[] responseBytes = calculateResponse(requestBytes);

  
    // Toma el tiempo final del tiempo de procesamiento.
    long finishTime = System.nanoTime();
    long elapsedTime = finishTime - startTime;
    double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
    double elapsedTimeInMilis = (double) elapsedTime / 1_000_000;
    
    

    objeto = (PoligonoIrreg)SerializationUtils.deserialize(requestBytes);

    System.out.println("server recibe: "+ objeto.toString());


    Coordenada aux = new Coordenada(generateCoor(),generateCoor());

    objeto.anadeVertice(aux);

   System.out.println("server añade:"+objeto.toString());

    byte[] serializado = SerializationUtils.serialize(objeto);

    
    //System.out.println(new String(responseBytes, StandardCharsets.UTF_8));
    // Si isDebugMode se calcula el tiempo que tomó restando el tiempo final - el tiempo inicial.
    /*if (isDebugMode) {
      String debugMessage = String.format(
        "La operacion tomo %d nanosegundos, %f segundos, %f milisegundos",
        elapsedTime,
        elapsedTimeInSecond,
        elapsedTimeInMilis
      );
      //String debugMessage = String.format(res);
      // En debugMessage almacena el tiempo, lo convierte a cadena y se introduce en los headers de respuesta de
      // X-Debug-Info que se agrega del método put.
      exchange
        .getResponseHeaders()
        .put("X-Debug-Info", Arrays.asList(debugMessage));
    }*/
    // sendResponse envía una respuesta; con la respuesta a través de la transacción.
    sendResponse(serializado, exchange);
  }


  /**END METHOD POLIGONO */

  // Método del manejador del endpoint task.
  // HTTPExchange encapsula todo lo relacionado de la transacción HTTP actual entre el cliente y el servidor.
  private void handleTaskRequest(HttpExchange exchange) throws IOException {
    // Se obtiene el método y se verifica si el método corresponde al método POST.

    Demo objeto = null; 
   
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      // Si no cumple, cierra el exchange y regresa del método.
      exchange.close();
      return;
    }
    // getRequestHeaders recupera del exchange todos los headers con ayuda de un map.
    Headers headers = exchange.getRequestHeaders();
    // Si hay un header X-Test con valor asociado de true.
    if (
      headers.containsKey("X-Test") &&
      headers.get("X-Test").get(0).equalsIgnoreCase("true")
    ) {
      // Se genera la respuesta dummy 123 conocida y se envía mediante sendResponse.
      String dummyResponse = "123\n";
      sendResponse(dummyResponse.getBytes(), exchange);
      return;
    }
    boolean isDebugMode = false; // Si no, se queda isDebugMode con valor false.
    // Si hay un header X-Debug con valor asociado de true.
    if (
      headers.containsKey("X-Debug") &&
      headers.get("X-Debug").get(0).equalsIgnoreCase("true")
    ) {
      // Se inicializa isDebugMode, valor = true.
      isDebugMode = true;
      // Si es true se conecta una serie de información de depuración que se puede mandar de regreso.
    }
    // Se devuelve la cantidad de tiempo que tomó el procesamiento como información de depuración.
    // Toma el tiempo de inicio del tiempo de procesamiento.
    long startTime = System.nanoTime();
    /* 
            getRequestBody recupera toda la información del cuerpo del mensaje de la transacción HTTP.
            Se almacena esa información como bytes, se almacenan los 2 números que se quieren multiplicar.
            calculateResponse realiza las operaciones de multiplicación de esos números y se almacena en
            responseBytes. 
        */



    //System.out.println("recibo:"+ objeto.showInfo());
    byte[] requestBytes = exchange.getRequestBody().readAllBytes();

    byte[] responseBytes = calculateResponse(requestBytes);
    // Toma el tiempo final del tiempo de procesamiento.
    long finishTime = System.nanoTime();
    long elapsedTime = finishTime - startTime;
    double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
    double elapsedTimeInMilis = (double) elapsedTime / 1_000_000;

    objeto = (Demo)SerializationUtils.deserialize(requestBytes);


    //System.out.println(new String(responseBytes, StandardCharsets.UTF_8));
    // Si isDebugMode se calcula el tiempo que tomó restando el tiempo final - el tiempo inicial.
    if (isDebugMode) {
      String debugMessage = String.format(
        "La operacion tomo %d nanosegundos, %f segundos, %f milisegundos",
        elapsedTime,
        elapsedTimeInSecond,
        elapsedTimeInMilis
      );
      //String debugMessage = String.format(res);
      // En debugMessage almacena el tiempo, lo convierte a cadena y se introduce en los headers de respuesta de
      // X-Debug-Info que se agrega del método put.
      exchange
        .getResponseHeaders()
        .put("X-Debug-Info", Arrays.asList(debugMessage));
    }
    // sendResponse envía una respuesta; con la respuesta a través de la transacción.
    sendResponse(responseBytes, exchange);
  }

  // Método que multiplica los 2 números recibidos como BigInteger.
  private byte[] calculateResponse(byte[] requestBytes) {

    PoligonoIrreg objeto = null;
    objeto = (PoligonoIrreg)SerializationUtils.deserialize(requestBytes);
    System.out.println();
    //String[] stringNumbers = bodyString.split(",");
    /*BigInteger result = BigInteger.ONE;
    for (String number : stringNumbers) {
      BigInteger bigInteger = new BigInteger(number);
      result = result.multiply(bigInteger);
    }*/
    //System.out.println("objeto deserializado: "+objeto.toString());
    return String
      .format("servidor recibe:")
      .getBytes();
  }


  public static void initArr(char[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = '-';
    }
  }

  public static int nextDat(char[] arr) {
    int sig = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] != '-') {
        sig++;
      }
    }
    return sig;
  }

  // handleStatusCheckRequest busca si el método en la transacción(exchange) es el método GET.
  private void handleStatusCheckRequest(HttpExchange exchange)
    throws IOException {
    if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
      exchange.close();
      return;
    }
    // Si es el método GET, devuelve el mensaje de respuesta del servidor.
    String responseMessage = "El servidor está vivo\n";
    // sendResponse envía la respuesta con la respuesta a través de la transacción.
    sendResponse(responseMessage.getBytes(), exchange);
  }

  private void sendResponse(byte[] responseBytes, HttpExchange exchange)
    throws IOException {
    // Método que agrega el status éxitoso 200 y la longitud de la respuesta.
    exchange.sendResponseHeaders(200, responseBytes.length);
    // Se escribe como el cuerpo de mensaje.
    OutputStream outputStream = exchange.getResponseBody();
    outputStream.write(responseBytes); // Se escribe en el Stream.
    outputStream.flush(); // Limpieza al Stream.
    outputStream.close(); // Cierra el Stream
    exchange.close(); // Cierra la transacción
  }

  // - - - - -- - metodo extra

  private int contarCaracteres(String cadena, String caracter) {
    int posicion, contador = 0;
    posicion = cadena.indexOf(caracter);
    while (posicion != -1) {
      contador++;

      posicion = cadena.indexOf(caracter, posicion + 1);
    }

    return contador;
  }



  private byte[] countTimesWord(byte[] requestBytes) {

    String bodyString = new String(requestBytes);
    String[] body = bodyString.split(",");
    int n = Integer.parseInt(body[0]);

    char[] inicial = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    StringBuilder cadenota = new StringBuilder();

    int siguiente = 0;
    int j = -1;
    int posicion, contador = 0;

    for (int k = 0; k < n; k++) {
           
            cadenota.append(this.generaCadena(3));
        }

    posicion = cadenota.indexOf(body[1]);

    while (posicion != -1) {
      contador++;
      posicion = cadenota.indexOf(body[1], posicion + 1);
    }
  
    return String.format("%s aparece %d veces\n", body[1], contador).getBytes();

   
  }



  public static char[] generaCadena(int longitud) {
        char[] palabra = new char[4];

        for (int i = 0; i < longitud; i++) {
            int codigoAscii = (int) Math.floor(Math.random() * (122 - 97) + 97);
            palabra[i] = Character.toUpperCase((char) codigoAscii);
        }
        palabra[3] = ' ';

        return (palabra);

    }

  private void handleSearchRequest(HttpExchange exchange) throws IOException {
    // Se obtiene el método y se verifica si el método corresponde al método POST.
    if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
      // Si no cumple, cierra el exchange y regresa del método.
      exchange.close();
      return;
    }
    // getRequestHeaders recupera del exchange todos los headers con ayuda de un map.
    Headers headers = exchange.getRequestHeaders();
    // Si hay un header X-Test con valor asociado de true.
    if (
      headers.containsKey("X-Test") &&
      headers.get("X-Test").get(0).equalsIgnoreCase("true")
    ) {
      // Se genera la respuesta dummy 123 conocida y se envía mediante sendResponse.
      String dummyResponse = "123\n";
      sendResponse(dummyResponse.getBytes(), exchange);
      return;
    }
    boolean isDebugMode = false; // Si no, se queda isDebugMode con valor false.
    // Si hay un header X-Debug con valor asociado de true.
    if (
      headers.containsKey("X-Debug") &&
      headers.get("X-Debug").get(0).equalsIgnoreCase("true")
    ) {
      // Se inicializa isDebugMode, valor = true.
      isDebugMode = true;
      // Si es true se conecta una serie de información de depuración que se puede mandar de regreso.
    }
    // Se devuelve la cantidad de tiempo que tomó el procesamiento como información de depuración.
    // Toma el tiempo de inicio del tiempo de procesamiento.
    long startTime = System.nanoTime();
    /* 
            getRequestBody recupera toda la información del cuerpo del mensaje de la transacción HTTP.
            Se almacena esa información como bytes, se almacenan los 2 números que se quieren multiplicar.
            calculateResponse realiza las operaciones de multiplicación de esos números y se almacena en
            responseBytes. 
        */
    byte[] requestBytes = exchange.getRequestBody().readAllBytes();
    byte[] responseBytes = countTimesWord(requestBytes);
    // Toma el tiempo final del tiempo de procesamiento.
    long finishTime = System.nanoTime();
    long elapsedTime = finishTime - startTime;
    double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
    double elapsedTimeInMilis = (double) elapsedTime / 1_000_0000;
    // Si isDebugMode se calcula el tiempo que tomó restando el tiempo final - el tiempo inicial.
    if (isDebugMode) {
      String debugMessage = String.format(
        "La operacion tomo  %d segundos, con %d milisegundos",
        (long) elapsedTimeInSecond,
        (long) elapsedTimeInMilis
      );
      //String debugMessage = String.format(res);
      // En debugMessage almacena el tiempo, lo convierte a cadena y se introduce en los headers de respuesta de
      // X-Debug-Info que se agrega del método put.
      exchange
        .getResponseHeaders()
        .put("X-Debug-Info", Arrays.asList(debugMessage));
    }
    // sendResponse envía una respuesta; con la respuesta a través de la transacción.
    sendResponse(responseBytes, exchange);
  }
}
