package cn.edu.nju.ecm_2.models.version;

/**
 * Created by simengzhao on 16/12/5.
 */
public class WrongModelException extends Exception {
    private  String  wrongMessage;
    public  enum WrongType{SAME,RELATION,CANNOTLINK};
    private  WrongType wrongType;

    public String getWrongMessage() {
        return wrongMessage;
    }

    public WrongType getWrongType() {
        return wrongType;
    }

    public  WrongModelException(WrongType wrongType)
    {
        super();
        this.wrongType = wrongType;
        this.wrongMessage = wrongType.toString();
    }

}
