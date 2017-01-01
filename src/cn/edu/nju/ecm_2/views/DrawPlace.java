package cn.edu.nju.ecm_2.views;


import cn.edu.nju.ecm_2.controller.Callbackable;
import cn.edu.nju.ecm_2.models.version.*;
import cn.edu.nju.ecm_2.views.shape_elements.*;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.NativeUint8Array;

import java.util.HashMap;
import java.util.HashSet;


/**
 * Created by chugare on 2016/11/19.
 */


public class DrawPlace extends AnchorPane implements Cloneable {

    Node focus;
    HashSet<EBody_P> EBList ;
    HashSet<EHead_P> EHList ;
    HashSet<ELinkNode_P> LNList ;
    HashSet<BHLine> BHList;
    HashMap<Integer,Node> ShapeMap;
    HashMap<String,Callbackable> callbackList = new HashMap<>();
    //在从文件读取的过程中用于标记id和对应形状的关系
//    仅仅在读取的时候用到。写入的时候id重新分配
    Model model ;
    int ids;
    int count;
    static public DrawPlace instance = null;
    String name;
    String describe;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }



    public void addCallbackFun(String name,Callbackable fun)
    {
        callbackList.put(name,fun);
        System.out.println("mapsize:"+callbackList.size()
        );
    }
    public void removeCallbackFun(String name)
    {
        callbackList.remove(name);
    }
    public Node getFocus() {
        return focus;
    }
    private  void setFocus(Node focus)
    {
        this.focus = focus;
        System.out.print("number:"+callbackList.size());
        Callbackable fun = callbackList.get("UIB");
        for(Node node :getChildren())
        {
            ((Modifyable)node).resetFocus();
        }
        ((Modifyable)focus).setFocus();
        if(fun!=null)
            fun.doCallBackFunction();
    }
    public DrawPlace(){
        super();
        ids = 0;
        count = 0;
        this.setPrefSize(600,500);
        model= new Model();
        EBList = new HashSet<EBody_P>();
        EHList = new HashSet<EHead_P>();
        LNList = new HashSet<ELinkNode_P>();
        BHList = new HashSet<BHLine>();
        ShapeMap = new HashMap<>();
        if(instance!=null)
        {
            System.out.println("another Drawplace instance is replaced by a new one");

            instance = this;
        }
        else
        {
            instance = this;
        }
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode())
                {
                    case DELETE:

                }
            }
        });
//        设置快捷键


    }
    public  void updateTop(Node node)
    {
        if(getChildren().remove(node))
        {
            getChildren().add(node);
            setFocus(node);
        }
    }//当前使用的节点放置在最顶端


    public  void clearColor()
    {
        for(Node e :getChildren()){
            if(e.getClass() == EBody_P.class)
            {
                EBody_P eb = (EBody_P)e;
                eb.setStroke(Color.DARKGREEN);
            }else if(e.getClass() == EHead_P.class)
            {
                EHead_P eh = (EHead_P)e;
                eh.setStroke(Color.BLUE);
            }
        }
    }
    public void updateConnect()
    {
        for(BHLine bhLine:BHList)
        {
            bhLine.updatePosition();
        }
    }
    public  Node pointOnWhich(double x,double y){
        Node s = null;
        Rectangle r ;
        Circle c ;

        for(Node e :getChildren()){
            if(e.getClass()== EBody_P.class)
            {
                r = ((EBody_P)e).getRec_body();

                if(x>=r.getX()&&x<=r.getX()+r.getWidth())
                {
                    if(y>=r.getY()&&y<=r.getY()+r.getHeight())
                    {
                        s = e;
                    }
                };
            }
            if(e.getClass() == ELinkNode_P.class)
            {
                r = (Rectangle) e;

                if(x>=r.getX()&&x<=r.getX()+r.getWidth())
                {
                    if(y>=r.getY()&&y<=r.getY()+r.getHeight())
                    {
                        s = e;
                    }
                };
            }else if(e.getClass() == EHead_P.class)
            {
                c = (Circle) e;
                double d = Math.pow(c.getCenterX()-x,2)+Math.pow(c.getCenterY()-y,2);
                d = Math.sqrt(d);
                if (d<c.getRadius())
                {
                    s = e;
                }
            }
        }
        if(s!=null)
        {
            ((Modifyable)s).setStroke(Color.RED);
        }

            for(Node e :getChildren()){
                if(e.equals(s))
                {
                    ((Modifyable)s).setStroke(Color.RED);
                }else if(e.getClass() == EBody_P.class)
                {
                    Modifyable m = (Modifyable) e;
                    m.setStroke(Color.DARKGREEN);
                }else if(e.getClass() == EHead_P.class)
                {
                    Modifyable m = (Modifyable) e;
                    m.setStroke(Color.BLUE);
                }else  if(e.getClass()==ELinkNode_P.class)
                {
                    ((Modifyable)e).setStroke(Color.DARKORANGE);
                }
            }
        return s;

    }
//    在这个部分仅仅是查找 body，head，和linknode的实体，由于线条的实体不需要进行连线，所以不进行查找
//    而且查找一遍的过程也是将其他所有的实体focus标志清除的过程。
    public void addBody(double x,double y,double width,double height,String name,String message,int id){
        EBody_P eBody_p =new EBody_P(x,y,width,height);
        eBody_p.setNameMessage(name,message);
        EBList.add(eBody_p);
        if(id<0)
        {
            id = ids;
            ids++;
            count++;
        }else
        {
            if(ids<=id)
            {
                ids = id+1;
            }
            count++;
        }
//如果id为负，则是自己的
        eBody_p.setMid(id);
        ShapeMap.put(eBody_p.getMid(),eBody_p);
        getChildren().add(eBody_p);
    }
    public void addHead(double x,double y,double r,int id){
        EHead_P eHead_p = new EHead_P(x,y,r);
        if(id<0)
        {
            id = ids;
            ids++;
            count++;
        }
        else
        {
            if(ids<=id)
            {
                ids = id+1;
            }
            count++;
        }
        eHead_p.setMid(id);
        EHList.add(eHead_p);
        ShapeMap.put(eHead_p.getMid(),eHead_p);
        getChildren().add(eHead_p);
    }
    public void addLinkNode(double x,double y,double width,double height,int id)
    {
        ELinkNode_P eLinkNode_p = new ELinkNode_P(x,y,width,height);
        if(id<0)
        {
            id = ids;
            ids++;
            count++;
        }
        else
        {
            if(ids<=id)
            {
                ids = id+1;
            }
            count++;
        }
        eLinkNode_p.setMid(id);
        LNList.add(eLinkNode_p);
        ShapeMap.put(eLinkNode_p.getMid(),eLinkNode_p);
        getChildren().add(eLinkNode_p);
    }
    public void addRelation(int id1,int id2)
    {
        Node e1 = ShapeMap.get(id1);
        Node e2 = ShapeMap.get(id2);
        BHLine bhLine = new BHLine(e1,e2);
        BHList.add(bhLine);
        getChildren().add(bhLine);

    }
    private BHLine tempBHline;
    private EventHandler mouse_press_handler_temp;
    private  EventHandler mouse_drag_handler_temp;
    private  EventHandler mouse_release_handler_temp;

    public void enterDrawShapeMode(Node node)
    {

        node.setVisible(false);
        setFocus(node);
        if(node.getClass()==EBody_P.class)

        {
            getChildren().add(node);
            EBList.add((EBody_P)node);
            setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    node.setVisible(true);
                    ((EBody_P)node).moveTo(event.getX(),event.getY());

                }
            });
            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setOnMouseMoved(null);
                    setOnMouseClicked(null);
                }
            });
        }
        else if(node.getClass()==EHead_P.class)
        {
            getChildren().add(node);
            EHList.add((EHead_P)node);
            setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    node.setVisible(true);
                    EHead_P eHead_p = (EHead_P) node;
                    eHead_p.setCenterX(event.getX());
                    eHead_p.setCenterY(event.getY());
                }
            });
            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    setOnMouseMoved(null);
                    setOnMouseClicked(null);
                }
            });

        }else if(node.getClass() == ELinkNode_P.class)
        {
            getChildren().add(node);
            LNList.add((ELinkNode_P)node);
            //deal with the linknode;
            setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    node.setVisible(true);
                    ELinkNode_P eLinkNode_p = (ELinkNode_P) node;
                    eLinkNode_p.setX(event.getX());
                    eLinkNode_p.setY(event.getY());
                }
            });
            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    setOnMouseMoved(null);
                    setOnMouseClicked(null);
                }
            });


            //todo:
        }
    }
    public void enterDrawLineMode(){

        addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                BHLine bhLine = new BHLine(event.getX(),event.getY(),event.getX(),event.getY());
                tempBHline = bhLine;
                getChildren().add(bhLine);
                setFocus(bhLine);
                BHList.add(bhLine);
                pointOnWhich(event.getX(),event.getY());
                event.consume();
                mouse_press_handler_temp = this;
            }
        });
        addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tempBHline!=null){
                    pointOnWhich(event.getX(),event.getY());
                    tempBHline.moveEnd(event.getX(),event.getY());
                    event.consume();
                    mouse_drag_handler_temp = this;
                }
            }
        });
        addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(tempBHline!=null){
                    System.out.print("mouse released \n");
                    tempBHline.catchPointElement();
                    tempBHline.updatePosition();
                    updateConnect();
                    mouse_release_handler_temp = this;
                    DrawPlace.this.existDrawLineMode();
                }
            }
        });

//        setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                BHLine bhLine = new BHLine(event.getX(),event.getY(),event.getX(),event.getY());
//                tempBHline = bhLine;
//                getChildren().add(bhLine);
//                BHList.add(bhLine);
//                pointOnWhich(event.getX(),event.getY());
//            }
//        });
//        setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if(tempBHline!=null){
//                    pointOnWhich(event.getX(),event.getY());
//                    tempBHline.moveEnd(event.getX(),event.getY());
//                }
//            }
//        });
//        setOnMouseReleased(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//            }
//        });
    }
    public void existDrawLineMode(){
        if(mouse_press_handler_temp!=null)
        {
        removeEventFilter(MouseEvent.MOUSE_PRESSED,mouse_press_handler_temp);
        removeEventFilter(MouseEvent.MOUSE_DRAGGED,mouse_drag_handler_temp);
        removeEventFilter(MouseEvent.MOUSE_RELEASED,mouse_release_handler_temp);
        }
    }
    public void removeShape(Node element)
    {

        if (element.getClass()==BHLine.class)
        {
            BHList.remove(element);
            getChildren().remove(element);

        }else
        {
            for(BHLine bhLine:BHList)
            {
                bhLine.removeElement(element);
            }
            if(element.getClass() == EBody_P.class)
            {
                EBList.remove(element);
                getChildren().remove(element);
            }
            else if(element.getClass()==EHead_P.class)
            {
                EHList.remove(element);
                getChildren().remove(element);
            }else if(element.getClass() == ELinkNode_P.class)
            {
                LNList.remove(element);
                getChildren().remove(element);
            }
        }


    }

    public Model getModel()
    {
        Model model = new Model();
        model.setName(name);
        model.setDescription(describe);
        ShapeMap = new HashMap<>();
        for(EBody_P eBody_p:EBList)
        {
            double x = eBody_p.getX();
            double y = eBody_p.getY();
            String name = eBody_p.getName();
            String message = eBody_p.getMessage();
            int id = model.addBody(x,y,name,message,-1);
            eBody_p.setMid(id);
            ShapeMap.put(id,eBody_p);

            System.out.println("Body write:"+id);

        }
        for(EHead_P eHead_p: EHList)
        {
            double x = eHead_p.getCenterX();
            double y = eHead_p.getCenterY();
            int id = model.addHead(x,y,-1);
            ShapeMap.put(id,eHead_p);
            System.out.println("Head write:"+id);


            eHead_p.setMid(id);
        }
        for(ELinkNode_P eLinkNode_p:LNList)
        {
            double x = eLinkNode_p.getX();

            double y = eLinkNode_p.getY();
            int id = model.addLinkNode(x,y,-1);
            ShapeMap.put(id,eLinkNode_p);
            eLinkNode_p.setMid(id);
        }
        for(BHLine bhLine : BHList)
        {
            IdAvaiable e1 = (IdAvaiable)bhLine.getStartE();
            IdAvaiable e2 = (IdAvaiable)bhLine.getEndE();
            int id1 = e1.getMid();
            int id2 = e2.getMid();
            try {
                model.addRelation(id1,id2);
            }catch (WrongModelException e)
            {
                Stage wrongMessageBox = new Stage();
                Text message = new Text();
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(message);
                switch (e.getWrongType())
                {
                    case SAME:message.setText(e.getWrongMessage());
                        break;
                    case CANNOTLINK:message.setText(e.getWrongMessage());
                        break;
                    case RELATION:message.setText(e.getWrongMessage());
                        break;
                }
                wrongMessageBox.setScene(new Scene(stackPane,300,200));


            }
        }
        return model;

    }



}
