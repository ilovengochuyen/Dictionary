package sample;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_ggweb implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private TextField addressBar;
    private WebEngine engine;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = webView.getEngine();
        engine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED){
                addressBar.setText(engine.getLocation());
            }
        });
        String address = "https://translate.google.com/?hl=vi";
        engine.load(address);
    }

    public void keyHandle(KeyEvent ke){
        if (ke.getCode().equals(KeyCode.ENTER)){
            String address = addressBar.getText();
            engine.load(address);
        }
    }

    public void backHandle(ActionEvent e){
            String address = "https://translate.google.com/?hl=vi";
            engine.load(address);
        }


    public void forwardHandle(ActionEvent e){
             String address = "https://dictionary.cambridge.org/vi/dictionary/english-vietnamese/";
             engine.load(address);
    }


}
