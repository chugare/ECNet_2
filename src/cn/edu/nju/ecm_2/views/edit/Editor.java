package cn.edu.nju.ecm_2.views.edit;

import cn.edu.nju.ecm_2.models.version.Model;
import cn.edu.nju.ecm_2.views.DrawPlace;

import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by simengzhao on 16/12/26.
 */
public class Editor {

    Stack<Model> undoStack;
    int index;
    Stack<Model> redoStack;
    public Editor()
    {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    public void doOperation(DrawPlace now)
    {
        undoStack.push(now.getModel());
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
        Model temp = undoStack.pop();

        return  temp;
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
