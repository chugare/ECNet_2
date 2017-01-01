package cn.edu.nju.ecm_2.views.edit;

import cn.edu.nju.ecm_2.views.DrawPlace;

/**
 * Created by simengzhao on 16/12/23.
 */
public class EditOperation {
    DrawPlace shot;
    enum EditType{
        CUT,COPY,PASTE,DELETE,MODIFY,MOVE
    }
    EditType editType;
    public EditOperation(DrawPlace drawPlace,EditType type)
    {
        shot = drawPlace;
        editType = type;
    }


}
