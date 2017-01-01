package cn.edu.nju.ecm_2.views.information_column;

import cn.edu.nju.ecm_2.views.DrawPlace;
import cn.edu.nju.ecm_2.views.shape_elements.EBody_P;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * Created by simengzhao on 16/12/15.
 */
public class InformationColumnManager {
    static public void updateInformationColumn(InformationColumnManager ICM) {
        Node focus = ICM.drawPlace.getFocus();
        if(focus.getClass() == EBody_P.class)
        {
            EBody_P body = (EBody_P)focus;
            ICM.namebox .setText(body.getName());
            ICM.describe.setText(body.getMessage());
        }else
        {
            ICM.namebox.setText("");
            ICM.describe.setText("");
        }
    }

    static public void updateEBody(InformationColumnManager ICM)
    {
        Node focus = ICM.drawPlace.getFocus();
        if(focus.getClass()==EBody_P.class)
        {
            ((EBody_P)focus).setNameMessage(ICM.namebox.getText(),ICM.describe.getText());
        }
    }
    TextArea namebox;
    TextArea describe;
    DrawPlace drawPlace;
    public InformationColumnManager(TextArea name,TextArea des,DrawPlace drawPlace)
    {
        this.drawPlace = drawPlace;
        namebox = name;
        describe = des;
    }
}
