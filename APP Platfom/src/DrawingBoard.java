import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class DrawingBoard extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private Stack<WritableImage> undoStack;
    private Stack<WritableImage> redoStack;

    private double lastX = -1, lastY = -1;

    @Override
    public void start(Stage primaryStage) {
        // Set up the canvas and graphics context
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(5);

        // Set up the undo and redo stacks
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        // Set up the brush size slider
        Slider brushSizeSlider = new Slider(1, 20, 5);
        brushSizeSlider.setShowTickLabels(true);
        brushSizeSlider.setShowTickMarks(true);
        brushSizeSlider.setMajorTickUnit(5);
        brushSizeSlider.setMinorTickCount(4);
        brushSizeSlider.setBlockIncrement(1);

        // Set up the brush color picker
        ColorPicker brushColorPicker = new ColorPicker(Color.BLACK);

        // Set up the eraser button
        Button eraserButton = new Button("Eraser");
        eraserButton.setOnAction(e -> {
            brushColorPicker.setValue(Color.WHITE);
        });

        // Set up the clear button
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            undoStack.clear();
            redoStack.clear();
        });

        // Set up the undo button
        Button undoButton = new Button("Undo");
        undoButton.setOnAction(e -> {
            if (!undoStack.isEmpty()) {
                WritableImage currentImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, currentImage);
                redoStack.push(currentImage);
                WritableImage prevImage = undoStack.pop();
                gc.drawImage(prevImage, 0, 0);
            }
        });

        // Set up the redo button
        Button redoButton = new Button("Redo");
        redoButton.setOnAction(e -> {
            if (!redoStack.isEmpty()) {
                WritableImage currentImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, currentImage);
                undoStack.push(currentImage);
                WritableImage nextImage = redoStack.pop();
                gc.drawImage(nextImage, 0, 0);
            }
        });

        // Set up the brush size and color boxes
        HBox brushSizeBox = new HBox(10);
        brushSizeBox.getChildren().addAll(brushSizeSlider);
        brushSizeBox.setAlignment(Pos.CENTER);

        HBox brushColorBox = new HBox(10);
        brushColorBox.getChildren().addAll(brushColorPicker);
        brushColorBox.setAlignment(Pos.CENTER);

        // Set up the save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Open a file chooser dialog to choose where to save the image
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                    canvas.snapshot(null, writableImage);
                    ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Set up the tool box
        VBox toolBox = new VBox(10);
        toolBox.setPadding(new Insets(10));
        toolBox.getChildren().addAll(brushSizeBox, brushColorBox, eraserButton, clearButton, undoButton, redoButton, saveButton);
        toolBox.setAlignment(Pos.TOP_CENTER);

        // Set up the drawing area
        BorderPane drawingArea = new BorderPane();
        drawingArea.setCenter(canvas);

        // Set up the root node
        BorderPane root = new BorderPane();
        root.setLeft(toolBox);
        root.setCenter(drawingArea);

        // Set up the scene
        Scene scene = new Scene(root, 800, 600);

        // Add event handlers for mouse events
        canvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.setStroke(brushColorPicker.getValue());
            gc.setLineWidth(brushSizeSlider.getValue());
            gc.moveTo(e.getX(), e.getY());
            gc.stroke();

            lastX = e.getX();
            lastY = e.getY();

            // Save the current image to the undo stack
            WritableImage currentImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, currentImage);
            undoStack.push(currentImage);

            // Clear the redo stack
            redoStack.clear();
        });

        canvas.setOnMouseDragged(e -> {
            gc.setStroke(brushColorPicker.getValue());
            gc.setLineWidth(brushSizeSlider.getValue());
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });

        // Set the stage
        primaryStage.setTitle("Drawing Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




