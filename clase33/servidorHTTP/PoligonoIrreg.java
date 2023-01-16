import java.io.*;
import java.util.*;
public class PoligonoIrreg implements java.io.Serializable{
     
    private ArrayList<Coordenada> vertices;
    private int next;
    
    public PoligonoIrreg(){
        this.vertices = new ArrayList<Coordenada>();
       
    }
    public void anadeVertice(Coordenada vertice){
        this.vertices.add(vertice);
    }
    
    @Override
    public String toString( ) {
        String aux = "";
        for(int i = 0; i < this.vertices.size(); i++){
            aux += this.vertices.get(i).toString();
        }
        return aux;
    }
}