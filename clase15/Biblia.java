import java.io.*;
import java.util.*;

/**la lectura debe dividirse 7346 */
public class Biblia{

    public static void main(String [ ] args) throws Exception{

        File file = new File("/Users/catzin/Desktop/texto.txt");
        Scanner sc = new Scanner(file);

        Map<Character,Integer> map = new HashMap<>();
        ArrayList<Currency> dataList = new ArrayList<Currency>();

        int countLines = 0;
    
        while(sc.hasNextLine()){
            countLines++;
            char [] line = sc.nextLine().toCharArray();

            for(char caract : line){
                if(map.containsKey(caract)){

                    map.put(caract,map.get(caract)+1);
                }
                else{
                    map.put(caract,1);
                }
            }
        }

        for(Map.Entry entry:  map.entrySet()){
            String auxChar = String.valueOf(entry.getKey());
            String auxInt = String.valueOf(entry.getValue());
            dataList.add(new Currency(auxChar.charAt(0),Integer.parseInt(auxInt)));
        }

        Collections.sort(dataList, new SortByCurrency());

        for(Currency c : dataList){
            System.out.println(c.toString());
        }
        System.out.println(countLines);
    
    }
}