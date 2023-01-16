public class Currency{
    private char character;
    private int times;


    public Currency(char c , int t){
        this.character = c;
        this.times = t;
    }

    public int getTimes(){
        return this.times;
    }

    public char getCharacter(){
        return this.character;
    }

    @Override
    public String toString(){
       return this.character+":"+this.times;
    }
}