
public class Threads implements Runnable{
        //esta va a ser nuestra variable compartida
        static int dato = 0;
        private int n;
    
        public Threads(int n){
            this.n = n;
        }

        @Override
        public void run(){
            String hilo = Thread.currentThread().getName();
            for(int i = 0; i < this.n; i++){
                modifica(hilo);

            }
        }
        //este es el metodo que modifica,entonces agregamos el syncro
        public synchronized static void modifica(String  ThreadId){
            int id = Integer.parseInt(ThreadId.split("-")[1]);
            if(id == 0){
                dato++;
            }
            else{
                dato--;
            }
        }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Thread hilo1 = new Thread(new Threads(n));
        Thread hilo2 = new Thread(new Threads(n));

        hilo1.start();
        
        hilo2.start();

        try
        {
          
            hilo1.join();
            hilo2.join();
            System.out.println("Despues de: "+n+" iteraciones");
            System.out.println(dato);
        }
  
        catch(Exception ex)
        {
            System.out.println("Exception has " +
                                "been caught" + ex);
        }    
    }

   
}