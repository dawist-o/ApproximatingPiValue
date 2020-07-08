package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class DrawController {
    @FXML
    private TextField dotsCountTextField;
    @FXML
    private TextField piValueTextField;

    @FXML
    void backToSetup(ActionEvent event) {
        mainApp.showSetup();
    }

    @FXML
    private Circle paneCircle;
    @FXML
    private Button start_stop_button;

    private static MainApp mainApp;
    private static AnchorPane drawAnchorPane;
    private int dotsCountToApproximating;
    private final int R = 100;
    private boolean isDrawing;
    private int circleDots = 0;
    private int totalDots = 0;

    public void setupDrawScene(MainApp mainApp, AnchorPane drawAnchorPane, int dotsCount) {
        DrawController.mainApp = mainApp;
        DrawController.drawAnchorPane = drawAnchorPane;
        this.dotsCountToApproximating = dotsCount;
    }

    private int play_pause_iterator = 0;

    @FXML
    void onStartButtonPressed() {
        if (isDrawing) {
            start_stop_button.setText("start");
            isDrawing = false;
        } else {
            isDrawing = true;
            start_stop_button.setText("stop");
        }
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            while (totalDots <= dotsCountToApproximating && isDrawing) {
                double x = ThreadLocalRandom.current().nextInt(-R, R);
                double y = ThreadLocalRandom.current().nextInt(-R, R);
                totalDots++;

                Circle dot = new Circle(paneCircle.getCenterX() + x, paneCircle.getCenterY() + y, 1, null);
                if (Math.sqrt(x * x + y * y) < R) {
                    circleDots++;
                    dot.setFill(Color.rgb(0, 191, 255));
                } else
                    dot.setFill(Color.rgb(255, 97, 223));

                float pi = (float) 4.0 * circleDots / totalDots;

                try {
                    Thread.sleep(200);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                Platform.runLater(() -> {
                    drawAnchorPane.getChildren().add(dot);
                    dotsCountTextField.setText("all dots " + totalDots + " circle dots " + circleDots);//.valueOf(play_pause_iterator));
                    piValueTextField.setText(Double.toString(pi));
                });
            }
        });
        executorService.shutdown();
    }

    @FXML
    void onClearButtonPressed(ActionEvent event) {
        isDrawing = false;
        dotsCountTextField.setText("");
        piValueTextField.setText("");
        circleDots = 0;
        totalDots = 0;
        List<Node> toRemove = new ArrayList<>();
        for (Node shape : drawAnchorPane.getChildren())
            if (shape.getClass() == Circle.class && shape != paneCircle)
                toRemove.add(shape);
        drawAnchorPane.getChildren().removeAll(toRemove);

    }
}
