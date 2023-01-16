public class Rectangulo extends Figura implements Perimetro{

    private Coordenada superiorIzq, inferiorDer;
    private double area;


    public Rectangulo(){

        superiorIzq = new Coordenada(0,0);

        inferiorDer = new Coordenada(0,0);

    }

    

    public Rectangulo(double xSupIzq, double ySupIzq, double xInfDer, double yInfDer){

        superiorIzq = new Coordenada(xSupIzq, ySupIzq);

        inferiorDer = new Coordenada(xInfDer, yInfDer);        

    }

    public double imprimePerimetro(){
        return this.PI;
    }

    

    //Metodo getter de la coordenada superior izquierda

    public Coordenada superiorIzquierda( ) { return superiorIzq; }

 

    //Metodo getter de la coordenada inferior derecha

    public Coordenada inferiorDerecha( ) { return inferiorDer; }

    

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )

    @Override

    public String toString( ) {

        return "Esquina superior izquierda: " + superiorIzq + "\tEsquina superior derecha:" + inferiorDer + "\n";
    }

    public double area(){
        double ancho = this.superiorIzquierda().ordenada() - this.inferiorDerecha().ordenada();
        double alto = this.inferiorDerecha().abcisa() - this.superiorIzquierda().abcisa();
        return  ancho*alto;
    
    }

}