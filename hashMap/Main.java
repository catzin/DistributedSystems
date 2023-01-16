import java.io.*;
import java.util.*;

public class Main{

    public static void main(String [ ] args) throws Exception{
        File file = new File("/Users/catzin/Documents/distribuidos/hashMap/labiblia.txt");
        Scanner sc = new Scanner(file);
        sc.useDelimiter(":|\\n");

        Map<Character,Integer> map = new HashMap<>();
        ArrayList<Currency> dataList = new ArrayList<Currency>();

    
    
        while(sc.hasNextLine()){
            
            char [] line = sc.nextLine().toCharArray();
            System.out.println(line);

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

        Collections.sort(dataList, new sortByCurrency());

        for(Currency c : dataList){
            System.out.println(c.toString());
        }


    }
}