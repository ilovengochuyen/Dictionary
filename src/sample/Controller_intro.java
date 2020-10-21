package sample;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller_intro {
    @FXML
    private WebView webView;
    private WebEngine webEngine;

    private String text = "<br>\n" +
            "<h1> Bài Tập lớn số 1 TỪ ĐIỂN </h1>\n" +
            "\n" +
            "<br>\n" +
            "\n" +
            "<ul>\n" +
            "\n" +
            "\t<li>  Lập trình bằng JavaFX trên intellij. </li>\n" +
            "\t<li>  sử dụng API goole speech để phát âm. </li>\n" +
            "\t<li>  sử dụng database sqlite có trên 100000 từ. </li>\n" +
            "\t<li>  Dùng trie quản lý từ. </li>\n" +
            "\t<li>  Thêm/sửa/xóa. </li>\n" +
            "\t<li>  Có thể dùng googletranslate web. </li>\n" +
            "\t<li>  ... </li>\n" +
            "<li> -Trần Việt Tùng - 19020067 </li>\n" +
            "\t\t<ul>\n" +
            "</ul>\n";



    @FXML
    public void initialize() {
        webEngine = webView.getEngine();
        webEngine.loadContent(text, "text/html");
    }
}
