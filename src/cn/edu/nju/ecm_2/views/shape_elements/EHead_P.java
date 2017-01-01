package cn.edu.nju.ecm_2.views.shape_elements;

import cn.edu.nju.ecm_2.models.version.Modifyable;
import cn.edu.nju.ecm_2.views.DrawPlace;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by chugare on 2016/11/19.
 */
public class EHead_P extends Circle implements IdAvaiable,Modifyable{
    EHead_P me = this;
    double dx,dy;
    int mid;

    @Override
    public void setX(double x) {
        setCenterX(x);
    }

    @Override
    public void setY(double y) {
        setCenterY(y);
    }

    @Override
    public double getX() {
        return getCenterX();
    }

    @Override
    public double getY() {
        return getCenterY();
    }

    @Override
    public void setWidth(double width) {

    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public double getWidth() {
        return -1;
    }

    @Override
    public double getHeight() {
        return -1;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Override
    public void setFocus() {
        setFill(Color.color(0.9,0.9,1,0.8));
    }

    @Override
    public void resetFocus() {
        setFill(Color.WHITE);
    }
    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
    public EHead_P()
    {

    }
    public EHead_P(double x, double y, double r)
    {
        super(x,y,r);
        setStroke(Color.BLUE);
        setFill(Color.WHITE);
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                me.dx = event.getX()- me.getCenterX();
                me.dy = event.getY() - me.getCenterY();
                DrawPlace p  = (DrawPlace) getParent();
                p.updateTop(me);
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                    double x = event.getX()-me.dx;
                    double y = (event.getY()-me.dy);
                    if(x<0)
                        x=0;
                    if(y<0)
                        y=0;
                    me.setCenterX(x);
                    me.setCenterY(y);
                DrawPlace p = (DrawPlace) getParent();
                p.updateConnect();
                    //System.out.println("x:"+x+"  y:"+y +"pos:"+getX()+"--"+getY());
            }
        });

    }
}
