import java.util.*;
import java.util.Comparator;
import java.util.Collections;
public class PoligonoIrreg{

    //private Coordenada [] vertices;
    private ArrayList<Coordenada> vertices;
    public Coordenada coor;
    private int next;
    private double magnitud;
    
    public PoligonoIrreg(){
        //this.vertices = new Coordenada[vertices];
        this.next = 0;
        this.coor = new Coordenada(0.0,0.0);
        this.magnitud = 0.0;
        this.vertices = new ArrayList<Coordenada>();
     
    }
    public void anadeVertice(Coordenada vertice){
        vertice.calcularMagnitud();
        this.vertices.add(vertice);
        /* this.vertices[next] = vertice;   
        this.next++; */
    } 

    public void setCoor(double x , double y){
        this.coor.setAbcisa(x);
        this.coor.setOrdenada(y);
    }
    
    @Override
    public String toString( ) {
        String aux = "";
        for(Coordenada i : this.vertices){
            //aux+= i.toString();
            System.out.println(i.toString()+"magnitud:"+i.getMagnitud());
        
                
        }

        return aux;

    }

    public void ordenaVertices(){
        Collections.sort(this.vertices, new OrdenarVertices());
    } 
}   