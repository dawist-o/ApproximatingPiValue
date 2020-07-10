package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class SetupController {
    @FXML
    private TextField dotsCountTextField;

    private final int MAX_DOTS_COUNT=50000;
    private final int MIN_DOTS_COUNT=5000;



    @FXML
    void decreaseDotsCountBy_5k() {
        int currentDotsCount=Integer.parseInt(dotsCountTextField.getText());
        if(currentDotsCount>MIN_DOTS_COUNT)
            currentDotsCount-=5000;
        dotsCountTextField.setText(Integer.toString(currentDotsCount));
    }

    @FXML
    void increaseDotsCountBy_5k() {
        int currentDotsCount=Integer.parseInt(dotsCountTextField.getText());
        if(currentDotsCount<MAX_DOTS_COUNT)
            currentDotsCount+=5000;
        dotsCountTextField.setText(Integer.toString(currentDotsCount));
    }

    @FXML
    void decreaseDotsCountBy_10k() {
        int currentDotsCount=Integer.parseInt(dotsCountTextField.getText());
        if(currentDotsCount>MIN_DOTS_COUNT)
            currentDotsCount-=10000;
        dotsCountTextField.setText(Integer.toString(currentDotsCount));
    }

    @FXML
    void increaseDotsCountBy_10k() {
        int currentDotsCount=Integer.parseInt(dotsCountTextField.getText());
        if(currentDotsCount<MAX_DOTS_COUNT)
            currentDotsCount+=10000;
        dotsCountTextField.setText(Integer.toString(currentDotsCount));
    }

    @FXML
    void onCountButtonPressed(ActionEvent event) {
        mainApp.showApproximatingPi(Integer.parseInt(dotsCountTextField.getText()));
    }

    private static MainApp mainApp;
    public void setMain(MainApp mainApp) {
        SetupController.mainApp =mainApp;
    }
}
