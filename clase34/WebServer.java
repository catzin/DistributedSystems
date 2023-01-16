import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.nio.charset.StandardCharsets;
public class WebServer {
    //End point
    private static final String TASK_ENDPOINT = "/task";
    private static final String STATUS_ENDPOINT = "/status";
    private static final String ISPRIME_ENDPOINT = "/isprime";
    private static final String ISPRIME2_ENDPOINT = "/isprime2";
    private static final String SEARCHTOKEN_ENDPOINT = "/searchtoken";
    private final int port; //Puerto
    private HttpServer server; //Tipo http
    public static void main(String[] args) {
        int serverPort = 8080;
        if (args.length == 1) { //Puerto especifico
            serverPort = Integer.parseInt(args[0]);
        }
        WebServer webServer = new WebServer(serverPort); //Instancia un webServer
        webServer.startServer();//Ejecuta el servidor
        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }
    public WebServer(int port) { //Contructor
        this.port = port;
    }

    static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);//Crea una intancia de TCP, la cola esta en 0(lo decide el sistema)
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //Asocia un contexto a cada ruta
        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);
        HttpContext searchtokenContext = server.createContext(SEARCHTOKEN_ENDPOINT);
        HttpContext isprimeContext = server.createContext(ISPRIME_ENDPOINT);
        HttpContext isprime2Context = server.createContext(ISPRIME2_ENDPOINT);
        statusContext.setHandler(this::handleStatusCheckRequest);
        taskContext.setHandler(this::handleTaskRequest);
        searchtokenContext.setHandler(this::handleSearchTokenRequest);
        isprimeContext.setHandler(this::handleIsprimeRequest);
        isprime2Context.setHandler(this::handleIsprime2Request);
        //Asigna un ejecutor al servidor con un pool de 8 hilos
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();//Inicia la ejecución de un hilo
    }
    private void handleIsprimeRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }
        String query = exchange.getRequestURI().getQuery();
        String [] params = query.split("&");
  
        String numero = "0";
        int nucleos = 0;
        for(String param: params) {
            String[] vals = param.split("=");
            if(vals[0].equals("numero"))
                numero = vals[1];
            else if(vals[0].equals("nucleos"))
                nucleos = Integer.parseInt(vals[1]);
        }
        System.out.println("\nNumero recibido: " + numero);
        System.out.println("Nucleos recibidos: " + nucleos);
        WebClient webClient = new WebClient();
        CompletableFuture<String> futures = new CompletableFuture();
        futures = webClient.sendTask("http://localhost:8081/isprime2",numero.getBytes());
        String responseMessage = futures.join();
        String aux = new String(responseMessage);
        System.out.println("Respuesta recibida: " + aux);
        sendResponse(String.format("%s",responseMessage).getBytes(), exchange); //Envia la repuesta
    }
    
    
    private void handleIsprime2Request(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {//Verifica que el metodo sea post
            exchange.close();
            return;
        }
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();//Obtiene la información del body
        String numero = new String(requestBytes);
        System.out.println("\nNumero recibido: " + numero);
        byte[] responseBytes = calculateIsPrime(Integer.parseInt(numero));//Función para calcular la respuesta
        sendResponse(responseBytes, exchange);//Envia la respuesta
    }
    private byte[] calculateIsPrime(int num) {
        String primo = "TRUE";
        if(num < 1){
            return  String.format("¿EL numero es primo?: %s\n", primo).getBytes();
        }
        
        for(int i = 2; i <= num; i++){
            
            if(num%i == 0){
                System.out.println(num + " es divisible por "+i);
                primo = "TRUE";
                break;
            }
            else{
                System.out.println(num + " no es divisible por "+i);
                primo = "FALSE";
            }
            
        }    
        System.out.println("Se envia respuesta: " + primo);
        return String.format("¿EL numero es primo?: %s\n", primo).getBytes();
    }

    ///Manejador para la ruta de task
    private void handleTaskRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {//Verifica que el metodo sea post
            exchange.close();
            return;
        }
        Headers headers = exchange.getRequestHeaders();//Obtiene las cabeceras
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {//Verifica que este activada la cebecera X-Test
            String dummyResponse = "123\n";// asigna una respuesta
            sendResponse(dummyResponse.getBytes(), exchange);//Envía la respuesta
            return;
        }
        boolean isDebugMode = false; //Activa una bandera si se activa la cabecera de Debug
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }
        long startTime = System.nanoTime(); //Tiempo inicial del proces
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();//Obtiene la información del body
        byte[] responseBytes = calculateResponse(requestBytes);//Función para calcular la respuesta
        long finishTime = System.nanoTime(); //Tiempo final del proces
        String mensaje = "La operación tomó " + (finishTime - startTime) + " nanosegundos = " + Math.floor((finishTime - startTime)/1000000000) + " segundos con " + ((finishTime - startTime)%1000000000)/1000000 + " milisegundos";
        if (isDebugMode) {
            //String debugMessage = String.format();
            String debugMessage = String.format(mensaje);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));//Agrega el mensaje a la cabecera de Debug
        }
        sendResponse(responseBytes, exchange);//Envia la respuesta
    }
    private byte[] calculateResponse(byte[] requestBytes) { ///Metodo para calcular multiplicación
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");
        BigInteger result = BigInteger.ONE;
        for (String number : stringNumbers) {
            BigInteger bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }
        return String.format("El resultado de la multiplicación es %s\n", result).getBytes();
    }
    ///Metodo para ruta status
    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        System.out.println("a");
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) { //Revisa que sea metodo get
            exchange.close();
            return;
        }
        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange); //Envia la repuesta
    }
    
    private void handleSearchTokenRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {//Verifica que el metodo sea post
            exchange.close();
            return;
        }
        Headers headers = exchange.getRequestHeaders();//Obtiene las cabeceras
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {//Verifica que este activada la cebecera X-Test
            String dummyResponse = "123\n";// asigna una respuesta
            sendResponse(dummyResponse.getBytes(), exchange);//Envía la respuesta
            return;
        }
        boolean isDebugMode = false; //Activa una bandera si se activa la cabecera de Debug
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }
        long startTime = System.nanoTime(); //Tiempo inicial del proces
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();//Obtiene la información del body
        byte[] responseBytes = searchResponse(requestBytes);//Función para calcular la respuesta
        long finishTime = System.nanoTime(); //Tiempo final del proces
        String mensaje = "La operación tomó " + (finishTime - startTime) + " nanosegundos = " + Math.floor((finishTime - startTime)/1000000000) + " segundos con " + ((finishTime - startTime)%1000000000)/1000000 + " milisegundos";
        if (isDebugMode) {
            //String debugMessage = String.format();
            String debugMessage = String.format(mensaje);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));//Agrega el mensaje a la cabecera de Debug
        }
        sendResponse(responseBytes, exchange);//Envia la respuesta
    }
    private byte[] searchResponse(byte[] requestBytes) { ///Metodo para calcular multiplicación
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");
        int n = Integer.parseInt(stringNumbers[0]);
        String subcadena = stringNumbers[1].toUpperCase();
        int lon = subcadena.length();
        StringBuilder cadenota = new StringBuilder(n*4);
        int i,x,ran,contador=0;
        for(i=1;i<=n*lon+1;i++){
            if(i%(lon+1)==0){
                cadenota.append(' ');
                continue;
            }
            ran = (int)(Math.random()*(26)+65);
            cadenota.append((char)ran);
        }
        for(i=0;i<n*(lon+1)-1;i++){
            x = cadenota.indexOf(subcadena,i);
            if(x !=-1){
                i=x+1;
                //System.out.println("Ocurrencia en "+x);
                contador++;
            }
            else{
                break;
            }               
        }
        return String.format("Apariciones: %s", contador).getBytes();
    }
    ///Metodo paarr responder
    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length); //Envia status OK
        OutputStream outputStream = exchange.getResponseBody(); //Se escribe el cuerpo del mensaje
        outputStream.write(responseBytes);//Se escribe en el stream
        outputStream.flush();//Se limpia y cierra el stream
        outputStream.close();
        exchange.close();//
    }
}