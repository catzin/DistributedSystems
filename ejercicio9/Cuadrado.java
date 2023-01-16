public class Cuadrado extends Figura implements Perimetro{

    private Coordenada superiorIzq, inferiorDer;
    private double area;


    public Cuadrado(){

        superiorIzq = new Coordenada(0,0);

        inferiorDer = new Coordenada(0,0);

    }

    public double imprimePerimetro(){
        return this.PI;
    }

    

    public Cuadrado(double xSupIzq, double ySupIzq, double xInfDer, double yInfDer){

        superiorIzq = new Coordenada(xSupIzq, ySupIzq);

        inferiorDer = new Coordenada(xInfDer, yInfDer);        

    }

    



    public Coordenada superiorIzquierda( ) { return superiorIzq; }

 

    //Metodo getter de la coordenada inferior derecha

    public Coordenada inferiorDerecha( ) { return inferiorDer; }

    

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )

    @Override

    public String toString( ) {

        return "Esquina superior izquierda: " + superiorIzq + "\tEsquina superior derecha:" + inferiorDer + "\n";
    }

    public double area(){
        double ancho = this.superiorIzquierda().ordenada() * this.inferiorDerecha().ordenada();
        //double alto = this.inferiorDerecha().abcisa() + this.superiorIzquierda().abcisa();
        return  ancho;
    
    }

}