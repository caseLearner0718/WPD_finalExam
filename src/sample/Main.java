package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("自動循路模擬");
        primaryStage.setScene(new Scene(root, 490, 500));
        primaryStage.show();
        int[][] map =  {{0,0,0,0,0,0,0,0,0,0},
                        {1,0,1,1,1,0,1,1,1,0},
                        {0,0,1,0,0,0,1,0,0,0},
                        {0,1,1,0,1,1,1,1,0,1},
                        {0,0,0,0,1,0,0,0,0,0},
                        {1,1,1,0,1,0,1,1,1,0},
                        {0,0,0,0,0,0,1,0,0,0},
                        {0,1,1,0,1,0,1,0,1,1},
                        {0,1,1,0,1,0,1,0,0,0},
                        {0,0,0,0,1,0,0,0,1,0}};
        printMap(map);
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getScreenX());
                System.out.println(event.getScreenY());
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void printMap(int[][] map){
        for(int i = 0;i<map.length;i++){
            for(int k = 0;k<map.length;k++){
                System.out.print(map[i][k] + " ");
            }
            System.out.println("");
        }
    }
}
