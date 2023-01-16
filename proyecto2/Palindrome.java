/** PROYECTO 2 PARTE 2 - Ehecatzin Vallejo Serrano - 4CM14*/
import java.io.File;
import java.util.*;
public class Palindrome {

    private String folderPath;

    public Palindrome(String path){
        this.folderPath = path;
    }

    public  boolean isPalindrome(String str){
        str = str.toLowerCase();
        int i = 0, j = str.length() - 1;
        while (i < j) {

            if (str.charAt(i) != str.charAt(j))
                return false;
            i++;
            j--;
        }
 
        return true;
    }

    public void findPalindrome(String nameText){

        try{
            String absolutePath = this.folderPath+"/"+ nameText;
            File file = new File(absolutePath);
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()){
            Scanner line = new Scanner(sc.nextLine());
            while(line.hasNext()){
                String word = line.next();
                System.out.println(word);
                if(word.length() > 1){

                    if(this.isPalindrome(word)){
                    System.out.println("Palindromo encontrado: "+word);
                }

                }
                
                
            }
        }

        }catch(Exception err){
            System.out.println(err);
        }

    }

    public static void main(String [] args){
        String file = args[0];
        Palindrome pal = new Palindrome("/Users/catzin/Documents/distribuidos/proyecto2/libros");
        pal.findPalindrome(file);
    }
    
}