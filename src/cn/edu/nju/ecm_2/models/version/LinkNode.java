package cn.edu.nju.ecm_2.models.version;

import java.util.HashSet;


public class LinkNode extends Element{
	private HashSet<Head> heads;
//	private HashSet<E>
	double width;
	double height;
	public LinkNode(int id) {
		super(id);
		width=30;
		height = 30;
		this.setMyType(MyType.LinkNode);
		// TODO Auto-generated constructor stub
	}
	public void addHead(Head head){
		heads.add(head);
	}
}
