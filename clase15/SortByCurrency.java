import java.util.Comparator;
public class SortByCurrency implements Comparator<Currency>{
    @Override
    public int compare(Currency a , Currency b){
        return a.getTimes() - b.getTimes();
    }

}