




public class DictionaryCommandLine {

    DictionaryManagement Dictmana = new DictionaryManagement();
    public void dictionaryBasic() {
        Dictmana.insertFromCommandline();
    }

    public void showAllWords() {

        for(int i=1; i<= Dictmana.Dict.getLength(); i++) {
            System.out.println(Dictmana.Dict.ListWord[i].getWord_target() + " "
                                    + Dictmana.Dict.ListWord[i].getWord_explain());

        }
    }
}
