package cn.edu.nju.ecm_2.controller;

import cn.edu.nju.ecm_2.views.DrawPlace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by simengzhao on 16/11/29.
 */
public class CreateCanvas_Diolog implements Controller {
    Stage myStage;
    DrawPlace myDrawPlace;
    Controller parentController;
    @FXML
    private GridPane New_dialog;
    @FXML
    private TextField name_input;
    @FXML
    private TextArea message_input;
    @FXML
    private Label warnMessage;

    @FXML
    public void submit_click(ActionEvent e)
    {
        if(name_input.getText().isEmpty())
        {
           warnMessage.setText("请输入名称");
        }else if(message_input.getText().isEmpty())
        {
            warnMessage.setText("请输入描述信息");
        }else
        {
            //
            String name = name_input.getText();
            String description = message_input.getText();
            ((MainPage)parentController).newCanvas(name,description);
            myStage.close();
            //

        }
    }

    @Override
    public void setStage(Stage stage) {
        myStage = stage;
    }

    @Override
    public void setDrawplace(DrawPlace drawplace) {
        myDrawPlace = drawplace;
    }

    @Override
    public void setParentController(Controller controller) {
        parentController = controller;
    }

    @Override
    public Controller getParentController() {
        return parentController;
    }
}
