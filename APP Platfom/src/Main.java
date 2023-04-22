import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private Alarm alarm = new Alarm();
    private Calculator calculator = new Calculator();
    private DrawingBoard drawingBoard = new DrawingBoard();
    private Timer timer = new Timer();

    @Override
    public void start(Stage primaryStage) {
        // Set up the header
        Label headerLabel = new Label("Application Platform");
        headerLabel.setFont(Font.font("Arial", 36));
        StackPane headerPane = new StackPane(headerLabel);
        headerPane.setPadding(new Insets(30));
        headerPane.setStyle("-fx-background-color: #0099cc; -fx-text-fill: white;");

        // Set up the buttons
        Button alarmButton = new Button("Alarm");
        alarmButton.setPrefWidth(150);
        alarmButton.setOnAction(e -> alarm.start(new Stage()));

        Button calculatorButton = new Button("Calculator");
        calculatorButton.setPrefWidth(150);
        calculatorButton.setOnAction(e -> calculator.start(new Stage()));

        Button drawingBoardButton = new Button("Drawing Board");
        drawingBoardButton.setPrefWidth(150);
        drawingBoardButton.setOnAction(e -> drawingBoard.start(new Stage()));

        Button timerButton = new Button("Timer");
        timerButton.setPrefWidth(150);
        timerButton.setOnAction(e -> timer.start(new Stage()));

        // Set up the button boxes
        HBox topButtonBox = new HBox(alarmButton, calculatorButton);
        topButtonBox.setSpacing(20);
        topButtonBox.setAlignment(Pos.CENTER);
        HBox bottomButtonBox = new HBox(drawingBoardButton, timerButton);
        bottomButtonBox.setSpacing(20);
        bottomButtonBox.setAlignment(Pos.CENTER);

        // Set up the main content
        Label mainLabel = new Label("Welcome to the Application Platform");
        mainLabel.setFont(Font.font("Arial", 24));
        StackPane mainPane = new StackPane(mainLabel);
        mainPane.setPadding(new Insets(50));

        // Set up the layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(headerPane);
        borderPane.setCenter(mainPane);
        borderPane.setPadding(new Insets(20));
        borderPane.setPrefSize(600, 400);
        borderPane.setStyle("-fx-background-color: #ffffff;");

        VBox buttonBox = new VBox(topButtonBox, bottomButtonBox);
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(buttonBox);

        // Set up the scene
        Scene scene = new Scene(borderPane);

        // Set up the stage
        primaryStage.setTitle("Application Platform");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

