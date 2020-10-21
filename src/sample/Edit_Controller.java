package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import commandline.*;

//Controller scene2
public class Edit_Controller{
    @FXML
    private TextField text1, text2, text3, text4, data1, data2, data3; //các text trong ctrinh

    @FXML
    private TextArea data4;

    /**
     * Bắt sự kiện On Action cho button Add/Edit.
     * @param event sự kiện action (click chuột)
     */
    @FXML
    public void addButtonAction(ActionEvent event) {
        String english = data1.getText();
        english = english.toLowerCase();
        String[] ss = data4.getText().split("\\n");
        String vietnamese = "<h1>" + english + "</h1>"
                + "<h3><i>/" + data2.getText() + "/</i></h3>"
                + "<h2>" + data3.getText() + "</h2>"
                + "<ul>";
        for (int i = 0; i < ss.length; ++i) {
            vietnamese = vietnamese + "<li>" + ss[i] + "</li>";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Done");
        vietnamese = vietnamese + "</ul>";
        if (Main.testing.dictionaryLookup(english).equals("Can't find this word")) {
            Main.testing.addNewWord(english, vietnamese);
            Main.testing.addToDatabase(english, vietnamese);
            alert.setHeaderText("Add succesfully");
        } else {
            Main.testing.resetWord(english, vietnamese);
            Main.testing.updateDatabase(english, vietnamese);
            alert.setHeaderText("Edit successfully");
        }
        alert.show();
    }

    /**
     * Bắt sự kiện On Action cho button Remove.
     * @param event sự kiện action(click chuột)
     */
    @FXML
    public void removeButtonAction(ActionEvent event) {
        String english = data1.getText();
        english = english.toLowerCase();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("done!");
        System.out.println(english);
        if (!Main.testing.dictionaryLookup(english).equals("Can't find this word")) {
            Main.testing.removeFromDatabase(english);
            Main.testing.deleteWord(english);
            alert.setHeaderText("Remove " + "\"" + english + "\" successfully");
        } else {
            alert.setHeaderText("Don't have this word");
        }
        alert.show();
    }

    /**
     * Cấm nhập cho các textField hiển thị định nghĩa, đặt chế độ khung cho textArea.
     */
    @FXML
    public void initialize() {
        text1.setEditable(false);
        text2.setEditable(false);
        text3.setEditable(false);
        text4.setEditable(false);
        data4.setWrapText(true);
    }
}
