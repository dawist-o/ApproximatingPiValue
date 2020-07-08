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
    public TextField totalDotsCountTextField;
    @FXML
    public TextField circleDotsCountTextField;
    @FXML
    private TextField piValueTextField;
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
    private int sleepTime_Millis = 100;

    public void setupDrawScene(MainApp mainApp, AnchorPane drawAnchorPane, int dotsCount) {
        DrawController.mainApp = mainApp;
        DrawController.drawAnchorPane = drawAnchorPane;
        this.dotsCountToApproximating = dotsCount;
    }

    @FXML
    void backToSetup(ActionEvent event) {
        mainApp.showSetup();
    }

    @FXML
    void on_x1_SpeedPressed(ActionEvent event) {
        sleepTime_Millis = 50;
    }

    @FXML
    void on_x5_SpeedPressed(ActionEvent event) {
        sleepTime_Millis = 10;
    }

    @FXML
    void on_x10_SpeedPressed(ActionEvent event) {
        sleepTime_Millis = 5;
    }

    @FXML
    void on_max_SpeedPressed(ActionEvent event) {
        sleepTime_Millis = 1;
    }

    private void drawDots() {
        //executorService  ~~ new Thread.start(()->{.....})
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            while (totalDots < dotsCountToApproximating && isDrawing) {
                double x = ThreadLocalRandom.current().nextInt(-R, R);
                double y = ThreadLocalRandom.current().nextInt(-R, R);
                totalDots++;

                Circle dot = new Circle(paneCircle.getCenterX() + paneCircle.getLayoutX() + x,
                        paneCircle.getCenterY() + paneCircle.getLayoutY() + y, 1, null);

                if (Math.sqrt(x * x + y * y) < R) {
                    circleDots++;
                    dot.setFill(Color.rgb(0, 191, 255));
                } else
                    dot.setFill(Color.rgb(255, 97, 223));

                float pi = (float) 4.0 * circleDots / totalDots;
                try {
                    Thread.sleep(sleepTime_Millis);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                Platform.runLater(() -> {
                    drawAnchorPane.getChildren().add(dot);
                    totalDotsCountTextField.setText(String.valueOf(totalDots));
                    circleDotsCountTextField.setText(String.valueOf(circleDots));
                    piValueTextField.setText(String.format("%.6f", pi));
                    if (totalDots == dotsCountToApproximating)
                        start_stop_button.setText("restart");
                });
            }
        });
        executorService.shutdown();
    }

    @FXML
    void onStartButtonPressed() {
        if (totalDots == dotsCountToApproximating) {
            sleepTime_Millis = 100;
            isDrawing = true;
            clearFields();
        }
        if (isDrawing) {
            start_stop_button.setText("start");
            isDrawing = false;
        } else {
            isDrawing = true;
            start_stop_button.setText("stop");
        }
        drawDots();
    }

    @FXML
    void onClearButtonPressed(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        isDrawing = false;
        circleDots = 0;
        totalDots = 0;
        circleDotsCountTextField.setText("");
        totalDotsCountTextField.setText("");
        List<Node> toRemove = new ArrayList<>();
        for (Node shape : drawAnchorPane.getChildren())
            if (shape.getClass() == Circle.class && shape != paneCircle)
                toRemove.add(shape);
        drawAnchorPane.getChildren().removeAll(toRemove);
        piValueTextField.setText("");
    }
}
