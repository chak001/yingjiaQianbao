package nlc.zcqb.baselibrary.basemodel.bean;


import java.io.Serializable;

public class FileResult implements Serializable{

    public String pic;
    public String code;
    public String message;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCode() {
        return code;  
    }  
    public void setCode(String code) {  
        this.code = code;  
    }  
    public void setMessage(String message) {  
        this.message = message;  
    }  
    public String getMessage() {  
        return message;  
    }  
  
}
