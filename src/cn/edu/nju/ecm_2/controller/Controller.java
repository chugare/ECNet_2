package cn.edu.nju.ecm_2.controller;

import cn.edu.nju.ecm_2.views.DrawPlace;
import javafx.scene.control.Control;
import javafx.stage.Stage;

/**
 * Created by simengzhao on 16/12/3.
 */
public interface Controller {

    void setStage(Stage stage);
    void setDrawplace(DrawPlace drawplace);
    void setParentController(Controller controller);
    Controller getParentController();
}
