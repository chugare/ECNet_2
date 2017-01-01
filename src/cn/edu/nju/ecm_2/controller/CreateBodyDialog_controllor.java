package cn.edu.nju.ecm_2.controller;

import cn.edu.nju.ecm_2.views.DrawPlace;
import cn.edu.nju.ecm_2.views.shape_elements.EBody_P;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by chugare on 2016/11/30.
 */
public class CreateBodyDialog_controllor implements Controller{
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
    void submit_click(ActionEvent e)
    {
        if(DrawPlace.instance!=null)
        {

            EBody_P eBody_p = new EBody_P(0,0,100,30);

            String ns = name_input.getText();
            String ms = message_input.getText();
            if(ns.isEmpty())
            {
                warnMessage.setText("链体名不为空");
            }
            else if(ms.isEmpty())
            {
                warnMessage.setText("链体信息不为空");
            }

            eBody_p.setNameMessage(ns,ms);
            myDrawPlace.enterDrawShapeMode(eBody_p);
            myStage.close();
        }
    }
    @Override
    public void setStage(Stage stage) {
        myStage = stage;
    }

    @Override
    public void setDrawplace(DrawPlace drawplace) {
        myDrawPlace = drawplace ;
    }

    @Override
    public void setParentController(Controller controller) {
        parentController = controller;
    }

    @Override
    public Controller getParentController() {
        return parentController;
    }

    @FXML
    void cancel_click(ActionEvent e)
    {
        if(myStage!=null)
        {
            myStage.close();
        }
    }
}
