package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.Queue;
import java.util.LinkedList;

public class Main extends Application {
    static int targerX,targerY;
    static int[][] map =  {{0,0,0,0,0,0,0,0,0,0},
                            {1,0,1,1,1,0,1,1,1,0},
                            {0,0,1,0,0,0,1,0,0,0},
                            {0,1,1,0,1,1,1,1,0,1},
                            {0,0,0,0,1,0,0,0,0,0},
                            {1,1,1,0,1,0,1,1,1,0},
                            {0,0,0,0,0,0,1,0,0,0},
                            {0,1,1,0,1,0,1,0,1,1},
                            {0,1,1,0,1,0,1,0,0,0},
                            {0,0,0,0,1,0,0,0,1,0}};
    static int[][] status = {{0,0,0,0,0,0,0,0,0,0},
                            {2,0,2,2,2,0,2,2,2,0},
                            {0,0,2,0,0,0,2,0,0,0},
                            {0,2,2,0,2,2,2,2,0,2},
                            {0,0,0,0,2,0,0,0,0,0},
                            {2,2,2,0,2,0,2,2,2,0},
                            {0,0,0,0,0,0,2,0,0,0},
                            {0,2,2,0,2,0,2,0,2,2},
                            {0,2,2,0,2,0,2,0,0,0},
                            {0,0,0,0,2,0,0,0,2,0}};
    static int[][] distance = {{0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0}};
    static String[][] predecessor = new String[10][10];
    static String[] plan = new String[20];
    static Queue queue = new LinkedList();
    static int k=0,xChange=0,yChange=0;
    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            run();
        }
    };
    //  起點
    static String start = "0,0";
    static boolean flag = false;
    static int[] sta = new int[2];
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("自動循路模擬");
        primaryStage.setScene(new Scene(root, 490, 500));
        primaryStage.show();
//      安裝滑鼠聆聽器
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setFinalLocation((int)event.getX(),(int)event.getY());
//              先將起始點放入堆疊裡
                queue.offer(start);
                while (flag==false){
                    sta=translation1((String)queue.peek());
                    search(sta);
                }
                showData(distance);
                showData2(predecessor);
                disindRoud();
//                animationTimer.start();
                run();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    //    印出地圖
    public static void showData(int[][] data){
        System.out.println();
        System.out.println();
        for(int i = 0;i<data.length;i++){
            for(int k = 0;k<data.length;k++){
                System.out.print(data[i][k] + "\t");
            }
            System.out.println("");
        }
    }

//    印出繼承圖
    public static void showData2(String[][] data){
        System.out.println();
        System.out.println();
        for(int i = 0;i<data.length;i++){
            for(int k = 0;k<data.length;k++){
                System.out.print(data[i][k] + "\t\t\t\t\t\t");
            }
            System.out.println("");
        }
    }

    //    將滑鼠座標轉換成地圖座標
    public static void setFinalLocation(int mouseClickX,int mouseClickY){
        targerX = mouseClickX;
        targerY = mouseClickY;
        targerX = targerX/50;
        targerY = targerY/50;
//        int[] tem = {targerX,targerY};
//        queue2.offer(translation2(tem));
        System.out.println("X: " + targerX + "\n" + "Y: " + targerY);
    }

    //    將座標字串轉換成整數陣列
    public static int[] translation1(String location){
        int[] point = new int[2];
        String[] qqq = location.split(",");
        for(int i = 0;i < 2;i++){
            point[i] = Integer.parseInt(qqq[i]);
        }
        return point;
    }

    //    將座標陣列轉換成座標字串
    public static String translation2(int[] point){
        String var = Integer.toString(point[0]) + "," + Integer.toString(point[1]);
        return var;
    }

    //    搜尋可行走的點(順序上,下,左,右)
    public static void search(int[] point){
        int x = point[0];
        int y = point[1];
//        暫存用
        int[] var = new int[2];
        int[] var2 = new int[2];
//        上
        if(y>0){
            if(status[y-1][x] == 0){
                if(x == targerX && y-1 == targerY){flag=true;}
                var[0] = x;
                var[1] = y-1;
                queue.offer(translation2(var));
                status[y-1][x] = 1;
                distance[y-1][x] = distance[y][x]+1;
                predecessor[y-1][x] = Integer.toString(x) + "," + Integer.toString(y);
            }
        }
//        下
        if(y<9){
            if(status[y+1][x] == 0){
                if(x == targerX && y+1 == targerY){flag=true;}
                var[0] = x;
                var[1] = y+1;
                queue.offer(translation2(var));
                status[y+1][x] = 1;
                distance[y+1][x] = distance[y][x]+1;
                predecessor[y+1][x] = Integer.toString(x) + "," + Integer.toString(y);
            }
        }
//        左
        if(x>0){
            if(status[y][x-1] == 0){
                if(x-1 == targerX && y == targerY){flag=true;}
                var[0] = x-1;
                var[1] = y;
                queue.offer(translation2(var));
                status[y][x-1] = 1;
                distance[y][x-1] = distance[y][x]+1;
                predecessor[y][x-1] = Integer.toString(x) + "," + Integer.toString(y);
            }
        }
//        右
        if(x<9){
            if(status[y][x+1] == 0){
                if(x+1 == targerX && y == targerY){flag=true;}
                var[0] = x+1;
                var[1] = y;
                queue.offer(translation2(var));
                status[y][x+1] = 1;
                distance[y][x+1] = distance[y][x]+1;
                predecessor[y][x+1] = Integer.toString(x) + "," + Integer.toString(y);
            }
        }
        var2 = translation1((String)queue.poll());
        status[var2[1]][var2[0]] = 2;
    }

//    規劃路徑
    public static void disindRoud(){
        System.out.println("start plan...");
        String now = Integer.toString(targerX) + "," +Integer.toString(targerY);
        int x,y;
        int[] loca;
        plan[k] = now;
        while(!now.equals(start)){
            k++;
            loca = translation1(now);
            x = loca[0];
            y = loca[1];
            plan[k] = predecessor[y][x];
            now = predecessor[y][x];
        }
        for(int i=k;i>=0;i--){
            System.out.println(plan[i]);
        }
    }

//    走走走~
    public static void setTarger(){
    k--;
//    Controller.ha();
    System.out.print(Controller.nini.getX());
    System.out.println(plan[k]);
    int[] stac = new int[2];
    int xx,yy;
    stac=translation1(plan[k]);
    xx = stac[0]*50+25;
    System.out.println("xx= " + xx);
    yy = stac[1]*50+25;
    System.out.println("yy= " + yy);
    if(xx>Controller.nini.getX()){
        xChange = 5;
    }else if(xx<Controller.nini.getX()){
        xChange = -5;
    }else if(yy>Controller.nini.getY()){
        yChange = 5;
    }else if(yy<Controller.nini.getY()){
        yChange = -5;
    }
}
    public static void run(){
//        Controller.ha();
        setTarger();
        Controller.nini.setX(Controller.nini.getX() + xChange);
        Controller.nini.setY(Controller.nini.getY() + yChange);
    }
}
