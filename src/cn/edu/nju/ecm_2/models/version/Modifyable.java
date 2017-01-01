package cn.edu.nju.ecm_2.models.version;

import javafx.scene.paint.Paint;

/**
 * Created by simengzhao on 16/12/7.
 */
public interface Modifyable {
    void setX(double x);
    double getX();

    void setY(double y);
    double getY();
    void setStroke(Paint paint);
    void setWidth(double width);
    void setHeight(double height);
    double getWidth();
    double getHeight();
    void setRadius(double radius);
    double getRadius();
}
