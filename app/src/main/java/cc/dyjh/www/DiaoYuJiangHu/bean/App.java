package cc.dyjh.www.DiaoYuJiangHu.bean;

/**
 * Created by 王沛栋 on 2016/4/6.
 */
public class App {
    private int vcode;// private String 1.0private String ,
    private String vname;// private String 1.0private String ,
    private int isupdate;// private String 0private String ,
    private String updateurl;// private String http://m.dyjh.cc/app/dyjhyc.apkprivate String

    public int getVcode() {
        return vcode;
    }

    public void setVcode(int vcode) {
        this.vcode = vcode;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public int getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(int isupdate) {
        this.isupdate = isupdate;
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }
}
