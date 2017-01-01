package cn.edu.nju.ecm_2.views.shape_elements;

import cn.edu.nju.ecm_2.models.version.Modifyable;
import cn.edu.nju.ecm_2.views.DrawPlace;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.List;

/**
 * Created by chugare on 2016/11/19.
 */
public class EBody_P extends Group implements IdAvaiable,Modifyable{

    HashSet<BHLine> connectList;
    int mid;
    String name;
    String message;
    Text text ;
    double x;
    double y;
    double width;
    double height;
    double dx;
    double dy;
    Rectangle rec_body;
    //Group rec_text;//整个Ebody对象包装在一个group中
    EBody_P me = this;
    public EBody_P(){

    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

//    public void addMe(DrawPlace drawPlace)
//    {
//        drawPlace.getChildren().add(me);
//    }
    public void moveTo(double x,double y)
    {
        text.setX(x);
        rec_body.setX(x);
        text.setY(y+rec_body.getHeight());
        rec_body.setY(y);
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void setFocus() {
        rec_body.setFill(Color.color(0.9,0.9,1,0.8));
    }

    @Override
    public void resetFocus() {
        rec_body.setFill(Color.WHITE);
    }

    @Override
    public void setRadius(double radius) {

    }

    @Override
    public double getRadius() {
        return -1;
    }

    public Rectangle getRec_body() {
        return rec_body;
    }

    public  void  setStroke(Paint paint) {rec_body.setStroke(paint);}
    public void setRec_body(Rectangle rec_body) {
        this.rec_body = rec_body;
    }

    public EBody_P(double x, double y, double width, double height)
    {
        super();
        rec_body = new Rectangle(x,y,width,height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        //rec_text = new Group();
        getChildren().add(rec_body);
        text = new Text(x,y+height,"");
        getChildren().add(text);
        rec_body.setFill(Color.WHITE);
        rec_body.setStroke(Color.DARKGREEN);
        rec_body.setStrokeWidth(1);
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EBody_P.this.dx = event.getX()- EBody_P.this.getX();
                EBody_P.this.dy = event.getY() - EBody_P.this.getY();
//                System.out.println("Dx: "+dx+"Dy: "+dy);
                DrawPlace p  = (DrawPlace)getParent();
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
                moveTo(x,y);
                DrawPlace p = (DrawPlace)getParent();
                p.updateConnect();
                //System.out.println("x:"+x+"  y:"+y +"pos:"+getX()+"--"+getY());
            }
        });
    }
    public void  setNameMessage(String name,String message)
    {
        this.name = name;
        this.message = message;
        text.setText(name);
    }




}
