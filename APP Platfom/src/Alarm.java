import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;

public class Alarm extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set up the UI
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);

        // Set up the alarm controls
        HBox alarmControls = new HBox(10);
        alarmControls.setAlignment(Pos.CENTER);
        Label alarmLabel = new Label("Set alarm for: ");
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 0);
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
        alarmControls.getChildren().addAll(alarmLabel, hourSpinner, new Label(":"), minuteSpinner);

        // Set up the alarm status label
        Label statusLabel = new Label("");

        // Set up the snooze button
        Button snoozeButton = new Button("Snooze");
        snoozeButton.setDisable(true);

        // Add the controls to the root pane
        VBox vbox = new VBox(10, alarmControls, statusLabel, snoozeButton);
        vbox.setAlignment(Pos.CENTER);
        root.setCenter(vbox);

        // Start the clock
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalTime currentTime = LocalTime.now();
            if (hourSpinner.getValue() == currentTime.getHour() &&
                    minuteSpinner.getValue() == currentTime.getMinute()) {
                statusLabel.setText("Alarm triggered!");
                snoozeButton.setDisable(false);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Set up the snooze button action
        snoozeButton.setOnAction(event -> {
            snoozeButton.setDisable(true);
            statusLabel.setText("");
            timeline.playFrom(Duration.seconds(LocalTime.now().getSecond() + 5));
        });

        // Show the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

