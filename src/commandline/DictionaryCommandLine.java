package commandline;

/**
 * code data base
 *https://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */

public class DictionaryCommandLine extends DictionaryManagement {

    public DictionaryCommandLine() {

    }

    /**
     * duyệt chiều sâu.
     *
     * @param idx id của node
     */
    public void dfs(int idx) {
        for (int i = 0; i < numchild; i++)
            if (trie.get(idx).child[i] != -1) {
                if (i == 26) t = t + ' ';
                else if (i == 27) t = t + '\'';
                else if (i == 28) t = t + '-';
                else if (i == 29) t = t + '.';
                else t = t + (char) (i + 'a');
                int id = trie.get(idx).child[i];
                if (!trie.get(id).html.isEmpty()) s = s + t + ",";
                dfs(id);
            }
        t = t.substring(0, t.length() - 1);
    }

    /**
     * search những từ bắt đầu bằng startLookup.
     *
     * @param startLookup đoạn đầu từ
     * @return những từ bắt đầu bằng startLookup, phân cách bởi dấu phẩy ","
     */
    public String dictionarySearcher(String startLookup) {
        s = "";
        t = startLookup;
        int idx = wordToId(startLookup);
        if (idx != 0) {
            if (!trie.get(idx).html.isEmpty()) {
                s = startLookup + ",";
                System.out.println(startLookup);
            }
            dfs(idx);
        }

        return s;

    }
}

/*
public class DictionaryCommandLine {

    DictionaryManagement Dictmana = new DictionaryManagement();
    public void dictionaryBasic() {
        Dictmana.insertFromDatabase();
    }

    public void showAllWords() {

        for(int i=1; i<= Dictmana.Dict.getLength(); i++) {
            System.out.println(Dictmana.Dict.ListWord[i].getWord() + " "
                    + Dictmana.Dict.ListWord[i].getPronounce());

        }
    }
}

 */
