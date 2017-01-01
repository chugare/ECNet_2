package cn.edu.nju.ecm_2.views.shape_elements;

import cn.edu.nju.ecm_2.models.version.Modifyable;
import cn.edu.nju.ecm_2.views.DrawPlace;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by chugare on 2016/11/19.
 */
public class ELinkNode_P extends Rectangle implements IdAvaiable,Modifyable{
    double dx;
    double dy;
    int mid;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Override
    public void setRadius(double radius) {

    }

    @Override
    public double getRadius() {
        return -1;
    }
    @Override
    public void setFocus() {
        setFill(Color.color(0.9,0.9,1,0.8));
    }

    @Override
    public void resetFocus() {
        setFill(Color.WHITE);
    }
    ELinkNode_P me = this;
    public ELinkNode_P(double x,double y,double width,double height)
    {
        super(x,y,width,height);


        this.setFill(Color.WHITE);
        this.setStroke(Color.DARKORANGE);
        this.setStrokeWidth(1);
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                me.dx = event.getX()- me.getX();
                me.dy = event.getY() - me.getY();
                DrawPlace p  = (DrawPlace) getParent();
                p.updateTop(me);

            }
        });
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                double x = event.getX()-me.dx;
                double y = (event.getY()-me.dy);
                if(x<0)
                    x=0;
                if(y<0)
                    y=0;
                me.setX(x);
                me.setY(y);
                DrawPlace p = (DrawPlace) getParent();
                p.updateConnect();
                //System.out.println("x:"+x+"  y:"+y +"pos:"+getX()+"--"+getY());
            }
        });
    }
}
