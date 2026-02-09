
package to.pkgdo.list;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Region;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ButtonBar;
import javafx.scene.text.Text;


public class ToDoList extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage){
        Label title = new Label("To do list");
        TextField addItem = new TextField();
        addItem.setPrefWidth(150);
        addItem.setPromptText("Enter an item to do");
        ButtonBar btnBar = new ButtonBar();
        Button add = new Button("Add item");
        Button delete = new Button("Delete");
        btnBar.setButtonData(add, ButtonBar.ButtonData.OK_DONE);
        btnBar.setButtonData(delete, ButtonBar.ButtonData.CANCEL_CLOSE);
        btnBar.getButtons().addAll(add, delete);
        
        VBox top = new VBox();
        top.getChildren().addAll(title,addItem,btnBar);
        top.setAlignment(Pos.CENTER);
        top.setMaxWidth(250);
        top.setSpacing(20);
        
        VBox list = new VBox();
        list.setMaxWidth(250);
        list.setAlignment(Pos.CENTER);
        ScrollPane scroll = new ScrollPane(list);
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPrefHeight(100);
        VBox setup = new VBox();
        setup.getChildren().addAll(top,scroll);
        setup.setAlignment(Pos.CENTER);
        setup.setMaxWidth(250);
        setup.setSpacing(20);
        
        BorderPane root = new BorderPane();
        root.setCenter(setup);
        Scene scene = new Scene(root,600,600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        HBox[] activeItem = new HBox[1];
        ArrayList<String> arrayItems = new ArrayList<>();
        
        add.setOnAction(e->{
            String item = addItem.getText();
            if (arrayItems.contains(item)){
                    Alert alert1 = new Alert(AlertType.WARNING);
                    alert1.setHeaderText("Item cannot be added");
                    alert1.setContentText("Item already exists in list.");
                    alert1.showAndWait();
            }else{
            
            arrayItems.add(item);
            Text itemText = new Text(item);
            itemText.setStrikethrough(false);
            CheckBox check = new CheckBox();
            HBox itemBox = new HBox();
            itemBox.getChildren().addAll(check, itemText);
            itemBox.setSpacing(20);
            list.getChildren().add(itemBox);
            addItem.clear();
            
            itemBox.setOnMouseClicked(clk ->{
                activeItem[0] = itemBox;
                itemBox.setStyle("-fx-background-color: lightblue");
                for (Node node: list.getChildren()){
                    if (node != itemBox && node instanceof HBox){
                        node.setStyle("");
                    }
                }
            });
            
            check.setOnAction(clicked -> itemText.setStrikethrough(check.isSelected()));
            }
        });
        
        delete.setOnAction(e->{
            if (activeItem[0] != null){
                for (Node node: activeItem[0].getChildren()){
                    if (node instanceof Text){
                        String deleteText = ((Text) node).getText();
                        arrayItems.remove(deleteText);
                    }
                }
                list.getChildren().remove(activeItem[0]);
                activeItem[0] = null;
            }
        });
    }
}
