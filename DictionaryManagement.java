import java.util.List;

import java.util.Scanner ;

public class DictionaryManagement {
    public Dictionary Dict = new Dictionary();

    public void insertFromCommandline() {
        Dict.setup();
        int n;
        String s="11";
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        //Dict.setLength(n);
        //System.out.println(Dict.getLength());
        for (int i = 1; i<= n; i++) {

            Word New= new Word();
            System.out.println("nhap_tu");
            s = sc.next();
            New.setWord_target(s);
            System.out.println("nhap_tu");
            s = sc.next();
            New.setWord_explain(s);
            Dict.newWord(New);

        }

    }



}
