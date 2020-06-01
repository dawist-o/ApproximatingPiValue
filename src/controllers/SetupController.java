package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SetupController {
    @FXML
    private TextField dotsCountTextField;

    private final int MAX_DOTS_COUNT=50000;
    private final int MIN_DOTS_COUNT=2000;

    @FXML
    void decreaseDotsCount() {
        int currentDotsCount=Integer.parseInt(dotsCountTextField.getText());
        if(currentDotsCount>MIN_DOTS_COUNT)
            currentDotsCount-=2000;
        dotsCountTextField.setText(Integer.toString(currentDotsCount));
    }

    @FXML
    void increaseDotsCount() {
        int currentDotsCount=Integer.parseInt(dotsCountTextField.getText());
        if(currentDotsCount<MAX_DOTS_COUNT)
            currentDotsCount+=2000;
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
