public class PruebaRectangulo {

    public static void main (String[] args) {

        Rectangulo rect1 = new Rectangulo(2,3,5,1);
        Cuadrado c1 = new Cuadrado(0,6,0,6);
        System.out.println("El area del rectangulo es:");
        System.out.println(rect1.area());
        System.out.println("Perimetro desde el rectangulo:");
        System.out.println(rect1.imprimePerimetro());
        System.out.println("El area del cuadrado es:");
        System.out.println(c1.area());
        System.out.println("Perimetro desde el cuadrado:");
        System.out.println(c1.imprimePerimetro());

    }

}