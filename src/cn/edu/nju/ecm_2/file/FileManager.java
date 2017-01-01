package cn.edu.nju.ecm_2.file;

import cn.edu.nju.ecm_2.models.version.Model;
import cn.edu.nju.ecm_2.models.version.WrongModelException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * Created by simengzhao on 16/12/10.
 * 文件管理模块，主要的功能是实现从文件读取 生成model
 */
public class FileManager {
    public static Model readModelFromFile(File file){
        SAXReader reader = new SAXReader();
        reader.setEncoding("utf-8");
        Model model = new Model();
        try{

            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            String name = root.element("name").getText();
            String description = root.element("description").getText();
            int count = Integer.parseInt(root.element("count").getText());
            List<Element> bodys = root.elements("Body");
            List<Element> heads = root.elements("Head");
            List<Element> linknodes = root.elements("LinkNode");
            List<Element> relations = root.elements("Relation");
            for(Element body:bodys)
            {
                String Bname = body.element("name").getText();
                String Bmessage = body.elementText("message");
                double posx = Double.parseDouble(body.elementText("posX"));
                double posy = Double.parseDouble(body.elementText("posY"));
                int id = Integer.parseInt(body.attribute("id").getText());
                model.addBody(posx,posy,Bname,Bmessage,id);
            }
            for (Element head:heads)
            {
                double posx = Double.parseDouble(head.elementText("posX"));
                double posy = Double.parseDouble(head.elementText("posY"));
                int id = Integer.parseInt(head.attribute("id").getText());
                model.addHead(posx,posy,id);

            }
            for(Element linknode:linknodes)
            {
                double posx = Double.parseDouble(linknode.elementText("posX"));
                double posy = Double.parseDouble(linknode.elementText("posY"));
                int id = Integer.parseInt(linknode.attribute("id").getText());
                model.addLinkNode(posx,posy,id);
            }
            for (Element relation:relations)
            {
                int elementA = Integer.parseInt(relation.element("elementA").getText());
                int elementB = Integer.parseInt(relation.element("elementB").getText());
                int id = Integer.parseInt(relation.attribute("id").getText());
                try {
                    model.addRelation(elementA,elementB);
                }catch (WrongModelException e)
                {
                    e.printStackTrace();
                }
            }
        }catch (DocumentException e)
        {
            e.printStackTrace();
        }
    return model;
    }
}
