package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.ThreadLocalRandom;

public class DrawController {
    private static MainApp mainApp;
    private static AnchorPane drawAnchorPane;
    private int dotsCount;
    private final int R = 100;

    @FXML
    private TextField dotsCountTextField;

    @FXML
    private TextField piValueTextField;

    public void setupDrawScene(MainApp mainApp, AnchorPane drawAnchorPane, int dotsCount) {
        DrawController.mainApp = mainApp;
        DrawController.drawAnchorPane = drawAnchorPane;
        this.dotsCount = dotsCount;
    }


    @FXML
    void backToSetup(ActionEvent event) {
        mainApp.showSetup();
    }

    @FXML
    private Rectangle square;

    @FXML
    private Circle paneCircle;

    @FXML
    void onStartButtonPressed() {
        int circlePoints = 0;
        int totalPoints = 0;
        
        for (int i = 0; i < dotsCount; i++) {
            double x = ThreadLocalRandom.current().nextInt(-R, R);
            double y = ThreadLocalRandom.current().nextInt(-R, R);
            totalPoints++;
            Circle dot = new Circle(paneCircle.getCenterX() + x, paneCircle.getCenterY() + y, 1, null);
            if (Math.sqrt(x * x + y * y) < R) {
                circlePoints++;
                dot.setFill(Color.rgb(0,191,255));
            } else
                dot.setFill(Color.rgb(255,97,223));
            drawAnchorPane.getChildren().add(dot);
            dotsCountTextField.setText(Integer.toString(i));
        }
        float pi = (float) 4.0 * circlePoints / totalPoints;
        piValueTextField.setText(Double.toString(pi));
    }
}