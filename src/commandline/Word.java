package commandline;

public class Word {
    static int numchild = 31;
    public int id;
    public int num;
    public int endword;
    public String description;
    public String pronounce;
    public String html = "";
    public int[] child = new int[numchild];
}


/*
public class Word {
    private String word;
    private String description;
    private String pronounce;
    private String html;

    public Word() {
    }
    public Word (String word, String description, String pronounce, String html) {
        this.word = word;
        this.description = description;
        this.pronounce = pronounce;
        this.html = html;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    public String getPronounce() {
        return description;
    }

    public String getHtml() {
        return html;
    }
}

 */
