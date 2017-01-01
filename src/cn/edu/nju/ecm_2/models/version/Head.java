package cn.edu.nju.ecm_2.models.version;

import java.util.HashSet;

public class Head extends Element {
	private HashSet<Body> bodys;

	private HashSet<Head> frontHead;
	private HashSet<Head> backHead;
	public HashSet<Body> getBodys() {
		return bodys;
	}

	public HashSet<Head> getFrontHead() {
		return frontHead;
	}
	public HashSet<Head> getBackHead() {
		return backHead;
	}
	
	double radius = 20;
	public Head(int id) {
		super(id);
		this.setMyType(MyType.Head);
		bodys = new HashSet<Body>();
		frontHead = new HashSet<>();
		backHead = new HashSet<>();
		// TODO Auto-generated constructor stub
	}
	public void addBody(Body body){
		bodys.add(body);
	};

	public void addHeadF(Head head)
	{
		frontHead.add(head);
	}
	public void addHeadB(Head head)
	{
		backHead.add(head);
	}
	
}
