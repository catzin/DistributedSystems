public class PoligonoIrreg{

    private Coordenada [] vertices;
    public Coordenada coor;
    private int next;

    public PoligonoIrreg(int vertices){
        this.vertices = new Coordenada[vertices];
        this.next = 0;
        this.coor = new Coordenada(0.0,0.0);
    }
    public void anadeVertice(Coordenada vertice){
        System.out.println(vertice.toString());
        this.vertices[next] = vertice;   
        this.next++;
    }

 

    public void setCoor(double x , double y){
        this.coor.setAbcisa(x);
        this.coor.setOrdenada(y);
    }
    
    @Override
    public String toString( ) {
        String aux = "";
        for(int i = 0; i < this.vertices.length; i++){
            aux += this.vertices[i].toString();
        }
        return aux;

    }
}   