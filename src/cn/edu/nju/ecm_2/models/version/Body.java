package cn.edu.nju.ecm_2.models.version;

import java.util.HashSet;

public class Body extends Element{
	private HashSet<Body> frontBody;
	private HashSet<Body> backBody;
	private HashSet<Head> Head;
//	保存相连单元的集合
//	储存当前的位置信息和长宽信息，用于下次读取时使用
	String name;
	String message;
	double width = 100;
	double height = 30;
//	保存内容信息

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public HashSet<Body> getFrontBody() {
		return frontBody;
	}
	public HashSet<Body> getBackBody() {
		return backBody;
	}
	public HashSet<Head> getHead() {
		return Head;
	}

	
	
	public Body(int id) {
		super(id);
		this.setMyType(MyType.Body);
		frontBody = new HashSet<Body>();
		backBody = new HashSet<>();
		Head = new HashSet<>();
		// TODO Auto-generated constructor stub
	}
	public void addBodyF(Body otherBody){
		frontBody.add(otherBody);
	};
	public void addBodyB(Body otherBody){
		backBody.add(otherBody);
	};
	public void addHead(Head head)
	{
		Head.add(head);
	}
	
	
}
