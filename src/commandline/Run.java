package commandline;

public class Run {
    public static DictionaryCommandLine testing = new DictionaryCommandLine();
    public static void main(String[] args) {
        testing.insertFromDatabase();;
        String word = "app";
        word = testing.dictionarySearcher(word);
        System.out.println(word);
    }
}
