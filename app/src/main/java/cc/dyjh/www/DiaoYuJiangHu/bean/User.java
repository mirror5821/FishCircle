package cc.dyjh.www.DiaoYuJiangHu.bean;

/**
 * Created by dongqian on 16/3/20.
 */
public class User  {
    private String id;
    private String token;

    private String pic;//":"/upload/appupload/20160324/H15/20160324154808996.jpeg",
    private String name;//":"有名人士",
    private String phone;//"

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
