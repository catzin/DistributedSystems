public class Coordenada {

    private double x, y;
    private double magnitud;

    public  Coordenada(double x, double y) {

        this.x = x;
        this.magnitud = 0.0;
        this.y = y;

    }

    //Metodo getter de x

    public double abcisa( ) { return x; }

    public void setAbcisa(double a){
        this.x = a;
    }

    //Metodo getter de y

    public double ordenada( ) { return y; }
    public void setOrdenada(double b){
        this.y = b;
    }

    public void calcularMagnitud(){
        this.magnitud = Math.sqrt(Math.pow((0.0-this.abcisa()),2) + Math.pow((0.0 - this.ordenada()),2));
    }

    public double getMagnitud(){
        return  this.magnitud;
    }
    

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )

    @Override

    public String toString( ) {

        return "[" + x + "," + y + "]";

    }

}

 