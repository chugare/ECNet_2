package cn.edu.nju.ecm_2.controller;

import cn.edu.nju.ecm_2.file.FileManager;
import cn.edu.nju.ecm_2.models.version.Model;
import cn.edu.nju.ecm_2.views.DrawPlace;
import cn.edu.nju.ecm_2.views.edit.Editor;
import cn.edu.nju.ecm_2.views.information_column.InformationColumnManager;
import cn.edu.nju.ecm_2.views.shape_elements.BHLine;
import cn.edu.nju.ecm_2.views.shape_elements.EBody_P;
import cn.edu.nju.ecm_2.views.shape_elements.EHead_P;
import cn.edu.nju.ecm_2.views.shape_elements.ELinkNode_P;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.io.*;

/**
 * Created by chugare on 2016/11/18.
 */
public class MainPage  implements Controller{
    Editor editor;
    Stage myStage;
    DrawPlace myDrawPlace;
    Controller parentController;
    InformationColumnManager infoManager;
    @FXML
    private MenuItem create;
    @FXML
    private MenuItem open;
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem save_as;
    @FXML
    private MenuItem quit;
    @FXML
    private MenuItem add;
    @FXML
    private MenuItem copy;
    @FXML
    private MenuItem paste;
    @FXML
    private MenuItem delete;
    @FXML
    private TextArea body_name_box;
    @FXML
    private TextArea body_describe_box;
//    menu单位
    @FXML
    private GridPane gridPane_2;
    @FXML
    private Button body;
    @FXML
    Button head;
    @FXML
    Button linkNode;
    @FXML
    Button connect;
    @FXML
    Label mouseX;
    @FXML
    Label mouseY;
    @FXML
    Label messageField;

    void setMessage(String value)
    {
        messageField.setText(value);
    };

    boolean drawRectMode;
    boolean drawLineMode;

    boolean hasCanvas;

    boolean haveCreateFile = false;
    boolean fileModified = false;
    String filepath;

    //file menu
    @FXML
    protected void createOperation(ActionEvent e)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/FileCreate_Dialog.fxml"));
        try {

            Parent parent = loader.load();
            Controller controller = loader.getController();
            Stage s = new Stage();

            s.setResizable(false);
            s.setScene(new Scene(parent,500,400));
            controller.setStage(s);
            controller.setParentController(this);
            s.show();

        }catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    @FXML
    void openOperation(ActionEvent e)
    {
        if(hasCanvas)
        {
            Callbackable confirm = new Callbackable() {
                @Override
                public void doCallBackFunction() {
                    MainPage.this.saveFile();
                }
            };
            Callbackable cancel = new Callbackable() {
                @Override
                public void doCallBackFunction() {
                    gridPane_2.getChildren().remove(myDrawPlace);
                    MainPage.this.openFile();
                }
            };
            Dialog_box.CreateBox("是否保存当前画布?",confirm,cancel);
            //处理新建文件的保存问题
        }
        else
        {
            openFile();
        }
    }
    void openFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件" );
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("EvidenceChain2:.ecm2","*.ecm2"));
        File file = fileChooser.showOpenDialog(myStage);
        if(file==null)
            return;
        Model model = FileManager.readModelFromFile(file);
        DrawPlace drawPlace = Model.setFromModel(model);
        setDrawplace(drawPlace);
    }
    @FXML
    void saveOperation(ActionEvent e)
    {
        if(!hasCanvas)
        {
            return;
        }
        if(haveCreateFile)
        {
            try {
               writeIntoFile();
            }catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            return ;
        }
        saveFile();
    }
    void saveFile()
    {
        haveCreateFile =true;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("指定保存文件的位置");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("EvidenceChain2:.ecm2","*.ecm2"));
        File file = fileChooser.showSaveDialog(myStage);

        if(file==null)
            return;
        filepath = file.getPath();
        if(!file.getPath().endsWith(".ecm2"))
        {
            filepath =filepath+".ecm2";
        }
        System.out.println("save:"+file.getPath());
        try{
            writeIntoFile();
        }catch (IOException IOe)
        {
            IOe.printStackTrace();
        }

    }
    void writeIntoFile ()throws IOException
    {
        try {

            FileWriter fileWriter = new FileWriter(filepath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            Model model = myDrawPlace.getModel();
            model.toXML(bufferedWriter);

        }catch (IOException e)
        {
            throw  e;
        }
    }
    @FXML
    void saveASOperation(ActionEvent e)
    {
        saveFile();
    }

    //edit menu
    private Node buffer ;
    @FXML
    void redoOperation(ActionEvent e)
    {
        Model model  = editor.redoOpertation(myDrawPlace);
        setDrawplace(Model.setFromModel(model));
        setMessage("redo");
    }
    @FXML
    void undoOperation(ActionEvent e)
    {
        Model model = editor.undoOperation(myDrawPlace);
        setDrawplace(Model.setFromModel(model));
        setMessage("undo");
    }
    @FXML
    void deleteShape(ActionEvent e)

    {
        editor.doOperation(myDrawPlace);
        myDrawPlace.removeShape(myDrawPlace.getFocus());
        setMessage("删除");
    }
    @FXML
    void copyOperation(ActionEvent e)
    {

        buffer =  myDrawPlace.getFocus();
        setMessage("复制");
    }
    @FXML
    void pasteOperation(ActionEvent e)
    {

        if(buffer==null)
        {
            return;
        }else
        {
            if(buffer.getClass()==EBody_P.class)
            {
                editor.doOperation(myDrawPlace);
                EBody_P eBody_p = (EBody_P)buffer;
                myDrawPlace.addBody(eBody_p.getX(),eBody_p.getY(),eBody_p.getWidth(),eBody_p.getHeight(),eBody_p.getName(),eBody_p.getName(),eBody_p.getMid());

            }
            else if(buffer.getClass()==EHead_P.class)
            {
                editor.doOperation(myDrawPlace);
                EHead_P eHead_p = (EHead_P)buffer;
                myDrawPlace.addHead(eHead_p.getX(),eHead_p.getY(),eHead_p.getRadius(),eHead_p.getMid());
            }
            else if(buffer.getClass()==ELinkNode_P.class)
            {
                editor.doOperation(myDrawPlace);
                ELinkNode_P eLinkNode_p =(ELinkNode_P)buffer;
                myDrawPlace.addLinkNode(eLinkNode_p.getX(),eLinkNode_p.getY(),eLinkNode_p.getWidth(),eLinkNode_p.getHeight(),eLinkNode_p.getMid());
            }
            else if(buffer.getClass() == BHLine.class)
            {
                messageField.setText("线段不能复制");
            }
        }
        setMessage("粘贴");
    }

    //tools bar
    @FXML
    void bodyClick(ActionEvent e)
    {
        editor.doOperation(myDrawPlace);
        if(!hasCanvas)
        {
            messageField .setText("请先新建一个画布" );
            return;
        }
        newBodyDialog();
    }

    @FXML
    void headClick(ActionEvent e)
    {
        editor.doOperation(myDrawPlace);
        if(!hasCanvas)
        {
            messageField .setText("请先新建一个画布" );
            return;
        }
        if(myDrawPlace!=null)
        {
            EHead_P eHead_p= new EHead_P(0,0,20);
            myDrawPlace.enterDrawShapeMode(eHead_p);
        }

    }
    @FXML
    void linkNodeClick(ActionEvent e)
    {
        editor.doOperation(myDrawPlace);
        if(!hasCanvas)
        {
            messageField .setText("请先新建一个画布" );
            return;
        }
        if(myDrawPlace!=null)
        {
            ELinkNode_P eLinkNode_p = new ELinkNode_P(0,0,30,30);
            myDrawPlace.enterDrawShapeMode(eLinkNode_p);
        }

    }
    @FXML
    void connectClick(ActionEvent e)
    {
        editor.doOperation(myDrawPlace);
        if(!hasCanvas)
        {
            messageField .setText("请先新建一个画布" );
            return;
        }
        else
            {
            drawLineMode = !drawLineMode;
            System.out.println("drawmode:"+drawLineMode);
            myDrawPlace.enterDrawLineMode();

        }

    }
    void setXY(MouseEvent e)
    {
        //System.out.println("x:"+e.getX()+"  y:"+e.getY());
        mouseX.setText(Double.toString(e.getX()));
        mouseY.setText(Double.toString(e.getY()));
    }
    @FXML
    void textChangedAction(InputMethodEvent e)
    {
        InformationColumnManager.updateEBody(infoManager);
        System.out.println("body text changed");
    }
    @FXML
    void ICM_enterPressed(KeyEvent k)
    {
        if(k.getCode()== KeyCode.ENTER){
            if(body_name_box.getText().endsWith("\n"))
            {
            }
            System.out.print("enter");
        }
    }
    void newBodyDialog()
    {
        Stage new_body_dialog = new Stage();
        Parent fxml=null;
        FXMLLoader loader = new FXMLLoader();

        try {
            loader.setLocation(getClass().getResource("../views/NewBody_Dialog.fxml"));
            fxml=loader.load();
            CreateBodyDialog_controllor controllor = loader.getController();
            controllor.setStage(new_body_dialog);
            controllor.setDrawplace(myDrawPlace);
            controllor.setParentController(this);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if(fxml!=null)
        new_body_dialog.setScene(new Scene(fxml,500,400));

        new_body_dialog.show();
    }
    @Override
    public void setStage(Stage stage) {
        myStage = stage;
    }

    @Override
    public void setDrawplace(DrawPlace drawplace) {

        gridPane_2.getChildren().remove(myDrawPlace);
        myDrawPlace = drawplace ;
        gridPane_2.add(drawplace,0,0);
        setInfoManager();

    }

    @Override
    public void setParentController(Controller controller) {
        parentController = controller;
    }

    @Override
    public Controller getParentController() {
        return null;
    }

    public void setInfoManager()
    {
        infoManager = new InformationColumnManager(body_name_box,body_describe_box,myDrawPlace);
//        初始化自身信息栏管理器
        gridPane_2.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setXY(event);
            }
        });
        gridPane_2.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setXY(event);
            }
        });
        hasCanvas = true;
        myDrawPlace.addCallbackFun("UIB", new Callbackable() {
            @Override
            public void doCallBackFunction() {
                InformationColumnManager.updateInformationColumn(infoManager);//为更新信息栏添加事件
            }
        });
    }//初始化信息栏管理器




    public void newCanvas(String name, String message)
    {
        DrawPlace drawPlace = new DrawPlace();
        drawPlace.setName(name);
        drawPlace.setDescribe(message);
        myDrawPlace = drawPlace;
        gridPane_2.add(myDrawPlace,0,0);
        setInfoManager();
    }
    public MainPage(){
        editor = new Editor();
        drawRectMode = false;
        drawLineMode = false;
        hasCanvas = false;

    }

}
