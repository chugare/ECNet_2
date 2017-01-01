package cn.edu.nju.ecm_2.models.version;

public class Relation extends Element {
	Element e1;
	Element e2;
	int elementA;
	int elementB;
	public Relation(int id,Element e1,Element e2) throws WrongModelException{
		super(id);
		this.e1 = e1;
		this.e2 = e2;
		//实现顺序理清，Body>Head>linknode
		if(e1.getMyType()==e2.getMyType())
		{
			WrongModelException wrongModelException = new WrongModelException(WrongModelException.WrongType.SAME);
			throw  wrongModelException;
		}
		if(e1.getMyType()==MyType.Body)
		{
			if(e2.getMyType()==MyType.LinkNode)
			{
				throw new WrongModelException(WrongModelException.WrongType.CANNOTLINK);
			}
		}

		if(e2.getMyType()==MyType.Body)
		{
			if(e1.getMyType()==MyType.Head)
			{
				this.e1 = e2;
				this.e2 = e1;
			}
			else {
				throw new WrongModelException(WrongModelException.WrongType.CANNOTLINK);
			}
		}
		if(e2.getMyType()==MyType.Head)
		{
			if(e1.getMyType()==MyType.LinkNode)
			{
				this.e1 = e2;
				this.e2 = e1;
			}
		}

		// TODO Auto-generated constructor stub
	}
}
