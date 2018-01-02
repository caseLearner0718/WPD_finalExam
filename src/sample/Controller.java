package sample;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    ImageView img99;

    @FXML
    public int[] getLocation(){
        int[] location = {0,0};
        location[0] = ((int)img99.getLayoutX()/50)+1;
        location[1] = ((int)img99.getLayoutY()/50);
        System.out.print(location[0] + " " + location[1]);
        return location;
    }

}
