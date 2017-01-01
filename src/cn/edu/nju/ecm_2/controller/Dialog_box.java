package cn.edu.nju.ecm_2.controller;

import cn.edu.nju.ecm_2.views.DrawPlace;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by simengzhao on 16/12/6.
 */
public class Dialog_box extends DialogPane implements Controller{
    private Stage mystage;
    private Controller parentController;
    private Callbackable confirmfun;
    private Callbackable cancelfun;
    public static enum BoxType{

    }
    @FXML
    private Label message;
    @FXML
    private Button confirm;
    @FXML
    void confirmEvent(ActionEvent e)
    {
        confirmfun.doCallBackFunction();
        mystage.close();
    }
    @FXML
    void cancelEvent(ActionEvent e)
    {
        cancelfun.doCallBackFunction();
        mystage.close();
    }
    //implement

    @Override
    public void setStage(Stage stage) {
        mystage = stage;
    }

    @Override
    public void setDrawplace(DrawPlace drawplace) {
        return;
    }

    @Override
    public void setParentController(Controller controller) {
        parentController = controller;
    }

    @Override
    public Controller getParentController() {
        return parentController;
    }

    static public void CreateBox(String message,Callbackable confirm,Callbackable cancel)
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Dialog_box dialog =  new Dialog_box();
        fxmlLoader.setLocation(dialog.getClass().getResource("../views/Box_dialog.fxml"));
        try {
            Parent p = fxmlLoader.load();
            dialog = fxmlLoader.getController();
            dialog.confirmfun = confirm;
            dialog.cancelfun = cancel;
            dialog.setStage(stage);
            dialog.message.setText(message);
            dialog.setFocusTraversable(false);
            stage.setScene(new Scene(p));
            stage.show();
            stage.setResizable(false);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
