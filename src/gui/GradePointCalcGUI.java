package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GradePointModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class GradePointCalcGUI extends Application implements Observer{

    // Initial number of rows
    private final int ROWS = 5;

    // Grade Point Model
    GradePointModel model;

    // Grid ArrayList
    private ArrayList<TextField> textarea;

    private ArrayList<ChoiceBox> choicebox;

    // GPA Display
    Text display;

    @Override
    public void init(){

        model = new GradePointModel();

        textarea = makeTextArea();

        choicebox = makeChoiceBox();

        display = new Text();

        display.setFont(Font.font("Helvetica"));

        model.addObserver(this);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane borderPane = new BorderPane();

        borderPane.setPadding( new Insets(40, 40, 40, 40));

        HBox topBox = new HBox();

        Text titleText = new Text("GPA Calculator");

        titleText.setFont(Font.font("Arial"));

        topBox.setAlignment(Pos.CENTER);

        topBox.getChildren().addAll(titleText);

        //borderPane.setTop(topBox);

        GridPane gridPane = makeGridPane();

        borderPane.setCenter(gridPane);

        // Set Bottom

        HBox bottomBox = new HBox();

        Button buttonCalc = makeCalculate();

        Button buttonClear = makeClear();

        bottomBox.setSpacing(50.0);

        bottomBox.setPadding(new Insets(20, 20, 20, 20));

        bottomBox.setAlignment(Pos.CENTER_LEFT);

        bottomBox.getChildren().addAll(buttonCalc, buttonClear, display);

        borderPane.setBottom(bottomBox);

        Scene scene = new Scene(borderPane);

        //Set Title
        primaryStage.setTitle("GPA Calculator");

        //Set Scene
        primaryStage.setScene(scene);

        primaryStage.show();


    }


    private GridPane makeGridPane(){

        final int gap = 20;

        int count = 0;

        //Create BorderPane
        GridPane gridPane = new GridPane();

        gridPane.setVgap(gap); // gap between grid cells
        gridPane.setHgap(gap);
        gridPane.setPadding(new Insets(5));
        gridPane.setGridLinesVisible( false ); // makes it hard to see active item



        for ( int row = 0; row < ROWS + 1; row++){
            for ( int col = 0; col < 3; col++){
                if ( row == 0 ){
                    if ( col == 0){
                        gridPane.add(new Text("Courses"), col, row);
                    }
                    else if ( col == 1){
                        gridPane.add(new Text("Credit Hours"), col, row);
                    }
                    else if (col == 2) {
                        gridPane.add(new Text("Letter Grade"), col, row);
                    }
                }
                else if ( col == 2){
                    gridPane.add(choicebox.get(row - 1), col, row);
                }
                else{
                    gridPane.add(textarea.get(count), col, row);
                    count++;
                }
            }
        }

        return gridPane;
    }

    private ArrayList makeTextArea(){

        ArrayList nodes = new ArrayList();

        for (int row = 0; row < ROWS; row++){
            nodes.add(getCourseField());
            nodes.add(getCreditField());

        }

        return nodes;
    }

    private ArrayList makeChoiceBox(){
        ArrayList nodes = new ArrayList();

        for (int row = 0; row < ROWS; row++){
            nodes.add(getGradeBox());

        }

        return nodes;
    }

    private TextField getCourseField(){

        TextField courseField = new TextField();

        return courseField;
    }

    private TextField getCreditField(){

        TextField gradeField = new TextField();

        gradeField.setPrefWidth(50);

        return gradeField;
    }

    private ChoiceBox getGradeBox(){

        ChoiceBox gradeBox = new ChoiceBox();

        gradeBox.getItems().addAll(this.model.gradePoint.keySet());

        return gradeBox;
    }

    private Button makeCalculate() {
        Button btn = new Button("Calculate");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.clear();
                Calculate();
                model.getGradePoint();
            }
        });
        return btn;
    }

    private Button makeClear(){

        Button btn = new Button("Clear");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.clear();
                textarea.stream().forEach(
                        node -> node.setText("")
                );
                choicebox.stream().forEach(
                        node -> node.setValue(null)
                );
            }
        });
        return btn;
    }

    private void Calculate(){
        String courseName = "";
        int count = 0;
        for (TextField text: textarea){
            if (!((textarea.indexOf(text) % 2) == 0)){
                if (!(choicebox.get(count).getValue() == null)){
                    this.model.addCourse(courseName, choicebox.get(count).getValue().toString(), Double.parseDouble(text.getText()));
                }
                count++;
            }
            else{
                courseName = text.getText();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {

        display.setText("GPA: " + Double.toString(this.model.gradePointAverage));

    }
}
