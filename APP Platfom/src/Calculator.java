import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {
    // Create the calculator's text field
    TextField textField = new TextField();

    // Define the buttons for the calculator
    Button btn0 = new Button("0");
    Button btn1 = new Button("1");
    Button btn2 = new Button("2");
    Button btn3 = new Button("3");
    Button btn4 = new Button("4");
    Button btn5 = new Button("5");
    Button btn6 = new Button("6");
    Button btn7 = new Button("7");
    Button btn8 = new Button("8");
    Button btn9 = new Button("9");
    Button btnAdd = new Button("+");
    Button btnSubtract = new Button("-");
    Button btnMultiply = new Button("*");
    Button btnDivide = new Button("/");
    Button btnEquals = new Button("=");
    Button btnClear = new Button("C");

    @Override
    public void start(Stage primaryStage) {
        // Set the window title
        primaryStage.setTitle("Calculator");

        // Create the grid pane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add the text field to the grid pane
        gridPane.add(textField, 0, 0, 4, 1);

        // Add the buttons to the grid pane
        gridPane.add(btn7, 0, 1);
        gridPane.add(btn8, 1, 1);
        gridPane.add(btn9, 2, 1);
        gridPane.add(btnAdd, 3, 1);

        gridPane.add(btn4, 0, 2);
        gridPane.add(btn5, 1, 2);
        gridPane.add(btn6, 2, 2);
        gridPane.add(btnSubtract, 3, 2);

        gridPane.add(btn1, 0, 3);
        gridPane.add(btn2, 1, 3);
        gridPane.add(btn3, 2, 3);
        gridPane.add(btnMultiply, 3, 3);

        gridPane.add(btn0, 0, 4);
        gridPane.add(btnEquals, 1, 4);
        gridPane.add(btnClear, 2, 4);
        gridPane.add(btnDivide, 3, 4);

        // Add event handlers to the buttons
        btn0.setOnAction(e -> textField.appendText("0"));
        btn1.setOnAction(e -> textField.appendText("1"));
        btn2.setOnAction(e -> textField.appendText("2"));
        btn3.setOnAction(e -> textField.appendText("3"));
        btn4.setOnAction(e -> textField.appendText("4"));
        btn5.setOnAction(e -> textField.appendText("5"));
        btn6.setOnAction(e -> textField.appendText("6"));
        btn7.setOnAction(e -> textField.appendText("7"));
        btn8.setOnAction(e -> textField.appendText("8"));
        btn9.setOnAction(e -> textField.appendText("9"));
        btnAdd.setOnAction(e -> textField.appendText("+"));
        btnSubtract.setOnAction(e -> textField.appendText("-"));
        btnMultiply.setOnAction(e -> textField.appendText("*"));
        btnDivide.setOnAction(e -> textField.appendText("/"));
        btnClear.setOnAction(e -> textField.clear());
        

        btnEquals.setOnAction(e -> {
            String expression = textField.getText();
            Double result = evaluateExpression(expression);
            textField.setText(result.toString());
        });

        // Set the scene
        Scene scene = new Scene(gridPane, 300, 250);
        primaryStage.setScene(scene);

        // Show the window
        primaryStage.show();
    }

    // Evaluate the expression and return the result
    public Double evaluateExpression(String expression) {
        try {
            String[] parts = expression.split("(?<=op)|(?=op)".replace("op", "[+\\-*/]"));
            Double leftOperand = Double.parseDouble(parts[0]);
            Double rightOperand = Double.parseDouble(parts[2]);
            String operator = parts[1];
            switch (operator) {
                case "+":
                    return leftOperand + rightOperand;
                case "-":
                    return leftOperand - rightOperand;
                case "*":
                    return leftOperand * rightOperand;
                case "/":
                    return leftOperand / rightOperand;
                default:
                    return 0.0;
            }
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}