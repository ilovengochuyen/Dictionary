import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    public Word[] ListWord = new Word[101];

    public int length = 0;

    public void setup() {
        for(int i=1; i<= 100; i++)
            ListWord[i] = new Word();
    }

    public int getLength() {
        return(length);
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void newWord(Word New) {
        length+=1;
        //System.out.println(length);
        ListWord[length] = New;
    }
}
