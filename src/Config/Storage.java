/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Illuminate
 */
public class Storage {
    /*
    * String type = "image|video|text"
    */
    public File open(String type) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        
        switch(type) {
            case "image":
                    fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
                    );
                break;
            case "video":
                    fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv")
                    );
                break;
            case "text":
                    fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text Files", "*.txt")
                    );
                break;
            default:
                break;
        }
        
        return fileChooser.showOpenDialog(stage);
    }
}
