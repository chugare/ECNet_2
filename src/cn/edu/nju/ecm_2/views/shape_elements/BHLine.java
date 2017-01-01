package cn.edu.nju.ecm_2.views.shape_elements;

import cn.edu.nju.ecm_2.models.version.Modifyable;
import cn.edu.nju.ecm_2.views.DrawPlace;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * Created by chugare on 2016/11/19.
 */
public class BHLine extends Group {
    private Circle checkpoint1,checkpoint2;
    private Line link;
    private Polygon arrow;
    double dx,dy;
    double k ;
    Node startE;
    Node endE;
    private BHLine me;

    public Node getStartE() {
        return  startE;
    }

    public Node getEndE() {
        return endE;
    }

    boolean calcLegalition()
    {
        if(startE==null||endE==null||startE.getClass()==endE.getClass())
        {
            link.setStroke(Color.PINK);
            return false;
        }
        link.setStroke(Color.BLACK);
        return true;
    }
    boolean generteArrow()
    {
        if(arrow!=null)
            arrow.setVisible(false);
        getChildren().remove(arrow);
        double size = 9;
        double width = 5;
        if(startE!=null&&startE.getClass()==ELinkNode_P.class)
        {
            arrow = new Polygon();
            getChildren().add(arrow);
            double length = Math.pow((link.getEndY()-link.getStartY()),2)+Math.pow((link.getStartX()-link.getEndX()),2);
            length = Math.sqrt(length);
            double rad = Math.atan(k);

            double dirVectorX  = (link.getStartX()-link.getEndX())/length;
            double dirVectorY = (link.getStartY()-link.getEndY())/length;
            ELinkNode_P eLinkNode_p = (ELinkNode_P)startE;
            double crossPointX = link.getStartX() - dirVectorX*size;
            double crossPointY = link.getStartY() - dirVectorY*size;
            double p1X = crossPointX - dirVectorY*width;
            double p1Y = crossPointY + dirVectorX*width;
            double p2X = crossPointX + dirVectorY*width;
            double p2Y = crossPointY - dirVectorX*width;
            arrow.getPoints().addAll(new Double[]{
                    link.getStartX(),link.getStartY(),p1X,p1Y,p2X,p2Y
            });
        }
        else if(endE!=null&&endE.getClass() == ELinkNode_P.class)
        {

            arrow = new Polygon();
            getChildren().add(arrow);

            double length = Math.pow((link.getEndY()-link.getStartY()),2)+Math.pow((link.getStartX()-link.getEndX()),2);
            length = Math.sqrt(length);
            double rad = Math.atan(k);
            double dirVectorX  = -(link.getStartX()-link.getEndX())/length;
            double dirVectorY = -(link.getStartY()-link.getEndY())/length;
            ELinkNode_P eLinkNode_p = (ELinkNode_P)endE;
            double crossPointX = link.getEndX() - dirVectorX*size;
            double crossPointY = link.getEndY() - dirVectorY*size;
            double p1X = crossPointX - dirVectorY*width;
            double p1Y = crossPointY + dirVectorX*width;
            double p2X = crossPointX + dirVectorY*width;
            double p2Y = crossPointY - dirVectorX*width;
            arrow.getPoints().addAll(new Double[]{
                    link.getEndX(),link.getEndY(),p1X,p1Y,p2X,p2Y
            });
        }else
            return false;
        return  true;
    }
    public BHLine(Node e1,Node e2)
    {
        this(0,0,0,0);
        startE = e1;
        endE = e2;
        updatePosition();
    }
    public BHLine(double x1,double y1,double x2,double y2)
    {
        link = new Line(x1,y1,x2,y2);
        k = (y2-y1)/(x2-x1);
        link.setStrokeWidth(3);
        checkpoint1 = new Circle(x1,y1,5);
        me = this;

        checkpoint2 = new Circle(x2,y2,5);

        this.getChildren().add(link);
        this.getChildren().add(checkpoint1);
        this.getChildren().add(checkpoint2);
        checkpoint1.setFill(Color.WHITE);
        checkpoint2.setFill(Color.WHITE);
        checkpoint1.setStroke(Color.BLACK);
        checkpoint2.setStroke(Color.BLACK);
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DrawPlace p  = (DrawPlace) getParent();
                p.updateTop(me);
            }
        });
        link.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dx = event.getX();
                dy = event.getY();
                System.out.println("Dxy:"+dx+"  "+dy);
            }
        });
        //移动整个线条
        link.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                double x = link.getStartX();
                double y = link.getStartY();
                //System.out.println("Mxy:"+event.getX()+" "+event.getY());
                //System.out.println("Sxy:"+x+" "+y);
                moveStart(x+event.getX()-dx,y+event.getY()-dy);
                x = link.getEndX();
                y = link.getEndY();
                //System.out.println("Exy:"+x+" "+y);
                moveEnd(x+event.getX()-dx,y+event.getY()-dy);
                dx = event.getX();
                dy = event.getY();
            }
        });
        checkpoint1.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                moveStart(x,y);
                setPoint(x,y);
            }
        });

        checkpoint2.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                moveEnd(x, y);
                setPoint(x, y);
            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                catchPointElement();
                updatePosition();
            }
        });
    }
    public  void catchPointElement()
    {
        DrawPlace p = (DrawPlace)getParent();
        Node sObject = p.pointOnWhich(link.getStartX(),link.getStartY());
        Node eObject = p.pointOnWhich(link.getEndX(),link.getEndY());
        p.clearColor();
        startE = sObject;
        endE = eObject;
    }
    public void updatePosition()
    {

        double startCenterX,startCenterY,endCenterX,endCenterY;
        startCenterX = link.getStartX();
        startCenterY = link.getStartY();
        endCenterX = link.getEndX();
        endCenterY = link.getEndY();

        int startType = 0;//0:null,1:body,2:head
        int endType = 0;
        if(startE!=null)
        {
            if(startE.getClass() == EBody_P.class)
            {
                EBody_P tempB = (EBody_P)startE;
                startType = 1;
                startCenterX = tempB.getX()+tempB.getWidth()/2;
                startCenterY = tempB.getY()+tempB.getHeight()/2;
            }
            if(startE.getClass() == EHead_P.class)
            {
                startType =2;
                EHead_P tempB = (EHead_P)startE;
                startCenterX = tempB.getCenterX();
                startCenterY = tempB.getCenterY();
            }
            if(startE.getClass() == ELinkNode_P.class)
            {
                ELinkNode_P tempB = (ELinkNode_P)startE;
                startType = 3;
                startCenterX = tempB.getX()+tempB.getWidth()/2;
                startCenterY = tempB.getY()+tempB.getHeight()/2;
            }
//            处理当目标是连接点的情况 if(startE.getClass() == LinkNode)....
        }
        if(endE!=null)
        {
            if(endE.getClass() == EBody_P.class)
            {
                EBody_P tempB = (EBody_P)endE;
                endType = 1;
                endCenterX = tempB.getX()+tempB.getWidth()/2;
                endCenterY = tempB.getY()+tempB.getHeight()/2;
            }
            if(endE.getClass() == EHead_P.class)
            {
                endType =2;
                EHead_P tempB = (EHead_P)endE;
                endCenterX = tempB.getCenterX();
                endCenterY = tempB.getCenterY();
            }
            if(endE.getClass() == ELinkNode_P.class)
            {
                endType = 3;
                ELinkNode_P tempB = (ELinkNode_P)endE;
                endCenterX = tempB.getX()+tempB.getWidth()/2;
                endCenterY = tempB.getY()+tempB.getHeight()/2;
            }
        }
        k = (endCenterY-startCenterY)/(endCenterX-startCenterX);
//      斜率
        boolean LRflag = endCenterX>startCenterX;//start = left:false, right:true
        boolean UDflag = endCenterY>startCenterY;//down:false ,up:true;
//判断象限的辅助标志位
//调整附着点的位置－>>>>
        if(startType == 0)
        {
//            首先进行类型的判断，如果没有物体则保持原位
            moveStart(startCenterX,startCenterY);
        }else if(startType ==1||startType==3)
        {
//起始点是长方形的情况,可以处理连接点和连体两种情况。
            Modifyable eb = (Modifyable)startE;
            double t_w = eb.getWidth();
            double t_h = eb.getHeight();
            double t_dx =(t_h/k/2);
            double t_dy =(t_w*k/2);

            if(t_dx<t_w/2&&t_dx>(t_w/-2))
            {

                if(LRflag)
                    startCenterX += Math.abs(t_dx);
                else
                    startCenterX -= Math.abs(t_dx);
                if(UDflag)
                    startCenterY +=t_h/2;
                else
                    startCenterY -=t_h/2;
            }else
            {
                if(LRflag)
                    startCenterX += t_w/2;
                else
                    startCenterX -= t_w/2;
                if(UDflag)
                    startCenterY +=Math.abs(t_dy);
                else
                    startCenterY -=Math.abs(t_dy);
            }

        }else if(startType == 2)
        {
            // 圆形的情况；
            //System.out.println("cxy: "+startCenterX+" "+startCenterY);
            EHead_P eHead_p = (EHead_P)startE;
            double r = eHead_p.getRadius();
            double rad = Math.atan(k);
            if(LRflag)
                startCenterX += Math.abs(r*Math.cos(rad));
            else
                startCenterX -= Math.abs(r*Math.cos(rad));
            if(UDflag)
                startCenterY += Math.abs(r*Math.sin(rad));
            else
                startCenterY -=Math.abs(r*Math.sin(rad));
        }
        moveStart(startCenterX,startCenterY);
        if(endType == 0)
        {
//            首先进行类型的判断，如果没有物体则保持原位
            //moveStart(startCenterX,startCenterY);
        }else if(endType ==1||endType==3)
        {
//结束点是长方形的情况
            Modifyable eb = (Modifyable)endE;
            double t_w = eb.getWidth();
            double t_h = eb.getHeight();
            double t_dx =(t_h/k/2);
            double t_dy =(t_w*k/2);

            if(t_dx<t_w/2&&t_dx>(t_w/-2))
            {

                if(LRflag)
                    endCenterX -= Math.abs(t_dx);
                else
                    endCenterX += Math.abs(t_dx);
                if(UDflag)
                    endCenterY -=t_h/2;
                else
                    endCenterY +=t_h/2;
            }else
            {
                if(LRflag)
                    endCenterX -= t_w/2;
                else
                    endCenterX += t_w/2;
                if(UDflag)
                    endCenterY -=Math.abs(t_dy);
                else
                    endCenterY +=Math.abs(t_dy);
            }

        }else if(endType == 2)
        {
            // 圆形的情况；
            EHead_P eHead_p = (EHead_P)endE;
            double r = eHead_p.getRadius();
            double rad = Math.atan(k);
            if(LRflag)
                endCenterX -= Math.abs(r*Math.cos(rad));
            else
                endCenterX += Math.abs(r*Math.cos(rad));
            if(UDflag)
                endCenterY -= Math.abs(r*Math.sin(rad));
            else
                endCenterY += Math.abs(r*Math.sin(rad));
        }
        moveEnd(endCenterX,endCenterY);
        calcLegalition();
        if(generteArrow())
        {
            arrow.setVisible(true);
        };

    }
    public void removeElement(Node node)
    {
        if(node==startE)
        {
            startE=null;
        }else if(node==endE)
        {
            endE = null;
        }
    }
    public void moveStart(double x,double y){
        checkpoint1.setCenterX(x);
        checkpoint1.setCenterY(y);
        link.setStartX(x);
        link.setStartY(y);
        k = (link.getEndY()-link.getStartY())/(link.getEndX()-link.getStartX());
    }
    public void moveEnd(double x,double y){
        checkpoint2.setCenterX(x);
        checkpoint2.setCenterY(y);
        link.setEndX(x);
        link.setEndY(y);
        k = (link.getEndY()-link.getStartY())/(link.getEndX()-link.getStartX());
    }
    void setPoint(double x,double y)
    {
        DrawPlace p  = (DrawPlace) getParent();
        p.pointOnWhich(x,y);
    }
}
