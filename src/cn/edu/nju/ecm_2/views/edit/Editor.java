package cn.edu.nju.ecm_2.views.edit;

import cn.edu.nju.ecm_2.models.version.Model;
import cn.edu.nju.ecm_2.views.DrawPlace;

import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by simengzhao on 16/12/26.
    editor类的作用是保存历史操作，这些操作包括新建，删除，撤销重做等，从而可以实现撤销和重做的操作
 实现的方式是通过当前画板的快照，也就是建立模型对象，并将模型对象存储在堆栈数据类型中，实现最多20层的撤销操作
 */
public class Editor {

    private Stack<Model> undoStack;
    private Stack<Model> redoStack;
    public Editor()
    {
        undoStack = new Stack<>();
//        撤销堆栈
        redoStack = new Stack<>();
//    重做堆栈
    }
    public void doOperation(DrawPlace now)
    {
        undoStack.push(now.getModel());
//        进行操作前将当前状态压入堆栈
        while (undoStack.capacity()>20)
        {
            undoStack.remove(undoStack.firstElement());
        }
        redoStack = new Stack<>();
    }
    public Model undoOperation(DrawPlace now)
    {
        if(redoStack.empty())
            redoStack.push(now.getModel());
        if(undoStack.empty())
            return null;
        return   undoStack.pop();


    }

    public Model redoOpertation(DrawPlace now)
    {
        if (redoStack.empty())
            return null;
        undoStack.push(now.getModel());

        Model temp = redoStack.pop();
        return temp;
    }

}
