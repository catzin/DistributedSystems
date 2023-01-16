
/** PROYECTO 2 PARTE 1 - Ehecatzin Vallejo Serrano - 4CM14*/
import java.io.File;
import java.util.*;

public class UltraCorrect{
    private String folderPath;
    private ArrayList <String> files = new ArrayList<String>();
    private Map<String,Integer> map = new HashMap<>();
    private ArrayList<String> badWords = new ArrayList<String>();

    public UltraCorrect(String path){
        this.folderPath = path;
    }
    public void getFiles(){

        File folder = new File(this.folderPath);
        File [] auxFiles = folder.listFiles();

        for(File file : auxFiles){
            if(file.isFile()){
                this.files.add(file.getName());
            }
        }
    }

    public void readBook(String pathFile){

        String fullPath = this.folderPath+"/"+pathFile;
        try {

            File book = new File(fullPath);
            Scanner sc = new Scanner(book);

        while(sc.hasNextLine()){
            Scanner line = new Scanner(sc.nextLine());
            while(line.hasNext()){
                String word = line.next();
                
                if(this.map.containsKey(word)){

                    this.map.put(word,map.get(word)+1);
                }
                else{
                    this.map.put(word,1);
                }
            }
        }

        }

        catch (Exception err) {
            System.out.println(err);
        }
    }


    public void buildDictionary(){
        int i = 0;
        for(String book : this.files){
            System.out.println("Leyendo libro"+" "+(i+1)+": "+book);
            this.readBook(book);
            i++;
        }
      
    }

    public void resetBadWords(){
        this.badWords.clear();
    }

    public void showDictionaryInfo(){
        System.out.println("Numero de palabras en el diccionario: "+this.map.size());
    }

    public boolean existWord(String word){
        return this.map.containsKey(word);
    }
    public void analyzeParraf(String parraf){
        String [] words = parraf.split(" ");

        for(String word : words){
            if(existWord(word) == false){
                this.badWords.add(word);
            }
        }

    }

    public ArrayList<String> getBadWords(){ 
        return this.badWords;
    }

    public static void main(String [] args)throws Exception{
    
        Scanner lector = new Scanner(System.in);
        int flag = 1;
        boolean flagWhile = true;
        String parraf  = "";
        UltraCorrect corrector = new UltraCorrect("/Users/catzin/Documents/distribuidos/proyecto2/libros");
        corrector.getFiles();
        corrector.buildDictionary();
        corrector.showDictionaryInfo();

        System.out.println("Ingresa una linea");
        parraf = lector.nextLine();
        corrector.analyzeParraf(parraf);
            
        System.out.println("Palabras potencialmente erroneas:");
        for(String word : corrector.getBadWords()){
                System.out.println(word);
            }

    
    }
} 