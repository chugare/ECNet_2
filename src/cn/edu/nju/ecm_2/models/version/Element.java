package cn.edu.nju.ecm_2.models.version;

public class Element {
	int ID;
	double posX;
	double posY;

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public enum MyType{
		Body,Head,LinkNode,BBrelation,BHrelation,HLrelation,WRONG
	}
	MyType myType; 
	public MyType getMyType() {
		return myType;
	}
	public void setMyType(MyType myType) {
		this.myType = myType;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Element(int id) {
		ID = id;
		// TODO Auto-generated constructor stub
	}
	public Element(int id,MyType type) {
		ID  =id;
		myType  = type; 
		// TODO Auto-generated constructor stub
	}
}
