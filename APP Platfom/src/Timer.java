import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Timer extends Application {
    private Timeline timeline;
    private long timeInMilliseconds = 0;
    private Label timeLabel;
    private Label recordLabel;
    private Button startButton, pauseButton, resetButton, recordButton;
    private List<String> recordList;

    @Override
    public void start(Stage primaryStage) {
        // Create GUI components
        timeLabel = new Label("00:00:00.000");
        recordLabel = new Label();
        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        resetButton = new Button("Reset");
        recordButton = new Button("Record");
        recordList = new ArrayList<>();

        // Set event handlers
        startButton.setOnAction(event -> startTimer());
        pauseButton.setOnAction(event -> pauseTimer());
        resetButton.setOnAction(event -> resetTimer());
        recordButton.setOnAction(event -> recordTime());

        // Set layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(timeLabel, 0, 0, 4, 1);
        gridPane.add(startButton, 0, 1);
        gridPane.add(pauseButton, 1, 1);
        gridPane.add(resetButton, 2, 1);
        gridPane.add(recordButton, 3, 1);
        gridPane.add(recordLabel, 0, 2, 4, 1);

        // Set the scene
        Scene scene = new Scene(gridPane, 400, 200);
        primaryStage.setTitle("Timer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Start the timer
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            timeInMilliseconds++;
            updateLabel();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Pause the timer
    private void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    // Reset the timer
    private void resetTimer() {
        timeInMilliseconds = 0;
        updateLabel();
        recordList.clear();
        recordLabel.setText("");
    }

    // Record the current time point
    private void recordTime() {
        if (timeline != null) {
            String time = timeLabel.getText();
            if (recordList.size() >= 5) {
                recordList.remove(0);
            }
            recordList.add(time);
            StringBuilder recordText = new StringBuilder();
            for (String record : recordList) {
                recordText.append(record).append("\n");
            }
            recordLabel.setText(recordText.toString());
        }
    }

    // Update the label with the current time
    private void updateLabel() {
        int hours = (int) (timeInMilliseconds / (1000 * 60 * 60));
        int minutes = (int) ((timeInMilliseconds / (1000 * 60)) % 60);
        int seconds = (int) ((timeInMilliseconds / 1000) % 60);
        int milliseconds = (int) (timeInMilliseconds % 1000);

        String time = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
        timeLabel.setText(time);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
