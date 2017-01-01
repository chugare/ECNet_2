package cn.edu.nju.ecm_2;

import cn.edu.nju.ecm_2.models.version.Model;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.xml.sax.*;
import org.dom4j.io.SAXReader;


import javax.xml.parsers.SAXParser;
import java.io.*;

/**
 * Created by simengzhao on 16/11/29.
 */
public class FileManager {

    static public String NEW_LINE = "\n";
    static public Model readModelFromFile(String filePath)
    {
        SAXReader saxReader;
        saxReader = new SAXReader();
        saxReader.setEncoding("gbk");
        try {
            Document document = saxReader.read(filePath);
        }catch (DocumentException e)
        {
            e.printStackTrace();
        }
        Model model = new Model();
        return  model;
    }
    static public void saveModelIntoFile(Model sourceModel,String filePath) throws IOException
    {
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = new FileOutputStream(filePath);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"gbk");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            sourceModel.toXML(bufferedWriter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public FileManager()
    {


    }
}
