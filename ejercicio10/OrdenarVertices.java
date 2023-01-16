import java.util.Comparator;

public class OrdenarVertices implements Comparator<Coordenada>{
    @Override
    public int compare(Coordenada a, Coordenada b){
        return (int)(a.getMagnitud() - b.getMagnitud());
    }
}