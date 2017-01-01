package cn.edu.nju.ecm_2.models.version;

import cn.edu.nju.ecm_2.views.DrawPlace;
import cn.edu.nju.ecm_2.views.shape_elements.EBody_P;
import cn.edu.nju.ecm_2.views.shape_elements.EHead_P;
import cn.edu.nju.ecm_2.views.shape_elements.ELinkNode_P;
import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
/*
 *model 的作用就是将画板上的元素抽象化，形成方便进行转换的静态模型
 *
 */
public class Model {
	private HashSet<Body> bodys;
	private HashSet<Head> heads;
	private HashSet<LinkNode> linkNodes;
	private HashSet<Relation> relations;
	private HashMap<Integer,Element> eleMap;
	int ids;
	int count;
	String name ;
	String description;
	public Model() {
		ids = 0;
		count = 0;
		bodys = new HashSet<>();
		heads = new HashSet<>();
		linkNodes = new HashSet<>();
		relations = new HashSet<>();
		eleMap = new HashMap<>();
		// / TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int addBody(double x, double y, String name, String attr,int id){
		Body body = new Body(ids);
		body.setPosX(x);
		body.setPosY(y);
		body.setName(name);
		body.setMessage(attr);

		if(id<0)
		{
			id = ids;
		}else
		{
			if(ids<id)
			{
				ids = id;
			}
		}
		eleMap.put(id,body);
		ids++;
		count++;
		bodys.add(body);

		System.out.println("model.addbody "+ids);
		return ids-1;


	}
	public int addHead(double cx,double cy,int id)
	{
		Head head = new Head(ids);
		head.setPosX(cx);
		head.setPosY(cy);
		if(id<0)
		{
			id = ids;
		}else
		{
			if(ids<id)
			{
				ids = id;
			}
		}
		eleMap.put(id,head);
		ids++;
		count++;
		heads.add(head);

		System.out.println("model.addhead "+ids);
		return  ids-1;
	}
	public int addLinkNode(double x,double y,int id) {

		LinkNode linkNode = new LinkNode(ids);
		linkNode.setPosX(x);
		linkNode.setPosY(y);
		if(id<0)
		{
			id = ids;
		}else
		{
			if(ids<id)
			{
				ids = id;
			}
		}
		eleMap.put(id,linkNode);
		ids++;
		count++;
		linkNodes.add(linkNode);
		return ids-1;
	}
	public  void addRelation  (int Fid,int Bid) throws WrongModelException
	{

		try {
			Relation relation = new Relation(ids,eleMap.get(Fid),eleMap.get(Bid));
			relations.add(relation);
			eleMap.put(ids,relation);
			ids++;
			count++;
		}catch (WrongModelException e)
		{
			throw e;
		}


	}
	public static DrawPlace setFromModel(Model model)
	{
		DrawPlace drawPlace = new DrawPlace();
		for(Body body:model.bodys)
		{
			drawPlace.addBody(body.posX,body.posY,body.width,body.height,body.name,body.message,body.ID);
		}
		for(Head head:model.heads)
		{
			drawPlace.addHead(head.posX,head.posY,head.radius,head.ID);
		}
		for (LinkNode linkNode:model.linkNodes)
		{

			drawPlace.addLinkNode(linkNode.posX,linkNode.posY,linkNode.width,linkNode.height,linkNode.ID);
		}
		for(Relation relation:model.relations)
		{
			int id1 = relation.e1.getID();
			int id2 = relation.e2.getID();
			drawPlace.addRelation(id1,id2);
		}

		return drawPlace;
	};
	public void toXML(BufferedWriter writer)
	{
		try
		{
			writer.append("<Model>\n");
			writer.append("\t<count>"+count+"</count>\n");
			writer.append("\t<name>"+name+"</name>\n");
			writer.append("\t<description>"+description+"</description>\n");
			for(Body body:bodys)
			{

				writer.append("\t\t<Body id = '"+body.getID()+"'>\n");
				writer.append("\t\t\t<posX>"+body.getPosX()+"</posX>\n");
				writer.append("\t\t\t<posY>"+body.getPosY()+"</posY>\n");
				writer.append("\t\t\t<name>"+body.getName()+"</name>\n");
				writer.append("\t\t\t<message>"+body.getMessage()+"</message>\n");
				writer.append("\t\t</Body>\n");
			}
			for(Head head:heads)
			{
				writer.append("\t\t<Head id='"+head.getID() +"'>\n");
				writer.append("\t\t\t<posX>"+head.getPosX() +"</posX>\n");
				writer.append("\t\t\t<posY>"+head.getPosY() +"</posY>\n");
				writer.append("\t\t</Head >\n ");
			}
			for(LinkNode linkNode:linkNodes)
			{

				writer.append("\t\t<LinkNode id='"+ linkNode.getID()+"'>\n");
				writer.append("\t\t\t<posX>"+ linkNode.getPosX()+"</posX>\n");
				writer.append("\t\t\t<posY>"+ linkNode.getPosY()+"</posY>\n");
				writer.append("\t\t</LinkNode >\n ");
			}
			for(Relation relation:relations)
			{
				writer.append("\t\t<Relation id='"+relation.getID()+"'>\n");

				writer.append("\t\t\t<elementA>"+relation.e1.getID()+"</elementA>\n");

				writer.append("\t\t\t<elementB>"+relation.e2.getID()+"</elementB>\n");

				writer.append("\t\t</Relation>\n");
			}
			writer.append("</Model>\n");
			writer.flush();
			writer.close();
		}catch (IOException e)
		{
			e.printStackTrace();
		}

	}


//	向模型中添加元素，包括关系以及元素
	@Override
	public String toString() {
		System.out.println("translate to XML");
		return "Model [bodys=" + bodys + ", heads=" + heads + ", linkNodes=" + linkNodes + ", relations=" + relations
				+ ", ids=" + ids + ", count=" + count + "]";
	}
//	用于实现xml读写
}
