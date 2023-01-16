public class ProcessOne implements Runnable{
    private int data;

    public ProcessOne(int d){
        this.data = d;
    }
    @Override
    public void run(){
        int hilo =  Integer.parseInt(Thread.currentThread().getName().split("-")[1]);
        for(int i = 0; i < this.data; i++){
            modifica(hilo);
        }
    }

    public void modifica(int  ThreadId){
        if(ThreadId == 0){

        }
        else{

        }
    }


  


}