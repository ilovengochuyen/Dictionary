package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Optional;

public class Controller_Dictionary {

    @FXML
    ObservableList<String> list = FXCollections.observableArrayList(); //list quản lí listview

    @FXML
    private ListView<String> listView; //danh sách từ gợi ý

    @FXML
    private TextField textField; //text nhập từ cần tra

    @FXML
    private WebView webView; //hiện text định dạng web (html)
    private WebEngine engine; //quản lí dữ liệu do webView

    @FXML
    private ImageView addImage; // biểu tượng thêm từ mới vào BOOK

    /**
     *
     * @param event
     */
    @FXML
    public void searchOnAction(ActionEvent event) {
        String wordSelect = textField.getText();
        wordSelect = wordSelect.toLowerCase();
        //wordSpeaker = wordSelect;
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
        //speaker.setVisible(true);
        addImage.setVisible(true);
        //speaker1.setVisible(true);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void clickOnLookup(MouseEvent event) {
        String wordSelect = textField.getText();
        wordSelect = wordSelect.toLowerCase();
        //wordSpeaker = wordSelect;
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
    }

    /**
     *
     * @param event
     */
    @FXML
    public void clickOnAddImage(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add to BOOK");
        dialog.setHeaderText("Enter your book");
        dialog.setContentText("Name of BOOK");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(bookName ->{
            DatabaseForBook.addWordtoBook(bookName, wordSpeaker);
        });
    }

    /**
     *
     * @param words
     */
    @FXML
    public void loadList(String words) {
        list.clear();
        words = words.toLowerCase();
        words = Main.testing.dictionarySearcher(words);
        if (words.isEmpty()) return;
        String[] ss = words.split(",");
        for (int i = 0; i < ss.length; ++i) {
            list.add(ss[i]);
        }
    }

    /**
     *
     */
    @FXML
    public void initialize() {
        engine = webView.getEngine();
        listView.setItems(list);
        //speaker.setVisible(false);
        addImage.setVisible(false);
        //speaker1.setVisible(false);

        //Bắt sự kiện text thay đổi cho textField.
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                loadList(newValue);
                listView.getSelectionModel().selectFirst();
            } else {
                list.clear();
                listView.getSelectionModel().clearSelection();
            }
        });

        //Bắt sự kiện selected item change.
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!listView.getSelectionModel().isEmpty()) {
                String wordSelect = listView.getSelectionModel().getSelectedItem();
                if (!wordSelect.isEmpty()) {
                    //wordSpeaker = wordSelect;
                    wordSelect = Main.testing.dictionaryLookup(wordSelect);
                    engine.loadContent(wordSelect, "text/html");
                    //speaker.setVisible(true);
                    addImage.setVisible(true);
                   // speaker1.setVisible(true);
                }
            }
        });

        final Tooltip tooltip1 = new Tooltip("Speaker");
       // speaker.setPickOnBounds(true);
       // Tooltip.install(speaker, tooltip1);
       // speaker1.setPickOnBounds(true);
       // Tooltip.install(speaker1, tooltip1);
        final Tooltip tooltip2 = new Tooltip("Add this word to BOOK");
        addImage.setPickOnBounds(true);
        Tooltip.install(addImage, tooltip2);
    }

}
