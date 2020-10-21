package commandline;

import java.util.ArrayList;

public class Dictionary {

    protected ArrayList <Word> trie = new ArrayList<Word>();
    protected static int numchild = 31;

    public String t;
    public String s;

    public void New_node()
    {
        Word root = new Word();
        root.num = 0;
        for (int i = 0; i < numchild; i++)
            root.child[i] = -1;
        root.id = trie.size();
        trie.add(root);
    }

    public Dictionary()
    {
        New_node();
    }

    public int tranfer(Character c) {
        if (c == ' ') return 26;
        else if (c == '-') return 27;
        else if (c == '\'') return 28;
        else if (c == '.') return 29;
        else return c - 'a';
    }


    /**
     * @param wordx từ tiếng anh cần tìm.
     * @return id trong cây trie.
     */
    public int wordToId(String wordx)
    {
        int idx = 0;
        int charecter;
        for (int i = 0; i < wordx.length(); i++)
        {
            charecter = tranfer(wordx.charAt(i));
            if (trie.get(idx).child[charecter] == -1) return 0;
            idx = trie.get(idx).child[charecter];
        }
        return idx;
    }

}


/*
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
*/