package sample;

import savedb.saveDB;
import googletexttospeech.JavaGoogleTextToSpeech;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Optional;

//Controller scene4
public class Controller_saved {

    private String wordSpeaker; //từ phát âm
    private String recentBookname; //tên book name hiện tại

    @FXML
    ObservableList<String> list = FXCollections.observableArrayList();
    //list quản lí ListView

    @FXML
    ObservableList<String> listChoice = FXCollections.observableArrayList();
    //list các BOOK

    @FXML
    private ListView<String> listView;
    //list các từ trong BOOK

    @FXML
    private ImageView speaker; //image phát âm

    @FXML
    private ImageView removeImage; //image remove word



    @FXML
    private WebView webView; //web view hiển thị text/html nghĩa
    private WebEngine engine; //quản lí webView

    @FXML
    private ChoiceBox choiceBox; //Box lựa chọn BOOK


    /**
     * Bắt sự kiện Mouse Clicked cho biểu tượng phát âm -> phát âm từ = source github.
     * @param event sự kiện chuột
     */
    @FXML
    public void clickOnSpeaker(MouseEvent event) {
        JavaGoogleTextToSpeech.speak(wordSpeaker);
    }


    /**
     * Bắt sự kiện Mouse Clicked cho Image, xóa từ khỏi book.
     * @param event sự kiện chuột.
     */
    @FXML
    public void clickOnRemove(MouseEvent event) {
        saveDB.removeWordFromBook(recentBookname, wordSpeaker);
        list.remove(wordSpeaker);
    }

    /**
     * Load dữ liệu của Book.
     */
    public void loadList() {
        String s = saveDB.loadBookName();
        if (!s.isEmpty()) {
            String[] ss = s.split(",");
            listChoice.addAll(ss);
        }
    }

    /**
     * Set tooltip cho các Image.
     * Bắt sự kiện cho choiceBox.
     */
    @FXML
    public void initialize() {
        engine = webView.getEngine();
        loadList();
        listView.setItems(list);
        speaker.setVisible(false);
        removeImage.setVisible(false);

        final Tooltip tooltip1 = new Tooltip("Speaker");
        speaker.setPickOnBounds(true);
        Tooltip.install(speaker, tooltip1);        final Tooltip tooltip2 = new Tooltip("Remove this word from BOOK");
        removeImage.setPickOnBounds(true);
        Tooltip.install(removeImage, tooltip2);
        final Tooltip tooltip3 = new Tooltip("Refresh BOOK");

        //Bắt sự kiện selected list BOOK thay đổi.
        choiceBox.setItems(listChoice);
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            list.clear();
            listView.getSelectionModel().clearSelection();
            recentBookname = newValue.toString();
            String s = saveDB.loadBook(newValue.toString());
            if (!s.isEmpty()) {
                String[] ss = s.split(",");
                for (int i = 0; i < ss.length; ++i) {
                    list.add(ss[i]);
                }
            }
        });

        //Bắt sự kiện selected item change.
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!listView.getSelectionModel().isEmpty()) {
                String wordSelect = listView.getSelectionModel().getSelectedItem();
                if (!wordSelect.isEmpty()) {
                    wordSpeaker = wordSelect;
                    wordSelect = Main.testing.dictionaryLookup(wordSelect);
                    engine.loadContent(wordSelect, "text/html");
                    speaker.setVisible(true);
                    removeImage.setVisible(true);

                }
            }
        });
    }
}
