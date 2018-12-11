package nlc.zcqb.app.application.User;

import java.io.Serializable;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.fourth.bean.PersonBean;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.UserPreferencesUtil;

/**
 * Created by lvqiu on 2017/11/7.
 */

public class User implements Serializable {


    //微易聊部分
    String username;
    String password;
    String newpassword;
    String deviceId;
    String longToken;
    String time;
    boolean isLogin=true;
    long deadline;
    String icon;
    String thumb;
    String discribtion;
    //公共部分
    String nickname;
    //贷超部分
    String user_id;
    String pic;
    String tel;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String newpassword) {
        this.username = username;
        this.password = password;
        this.newpassword = newpassword;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDiscribtion() {
        return discribtion;
    }

    public void setDiscribtion(String discribtion) {
        this.discribtion = discribtion;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        if (deviceId==null){
            return "";
        }
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLongToken() {
        return longToken;
    }

    public void setLongToken(String longToken) {
        this.longToken = longToken;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public void clear(){
        username="";
        password="";
        isLogin=false;
        longToken="";
    }

    public void saveUser(PersonBean personBean){
        User user= MyApplication.mUser;
        user.setUser_id(personBean.getUser_id());
        user.setLogin(true);
        user.setTel(personBean.getTel());
        user.setNickname(personBean.getNickname());
        user.setIcon(personBean.getPic());
        user.setPassword(personBean.getPass());
        user.setName(personBean.getName());
        UserPreferencesUtil.save(user);
    }


    public  void clearUser(){
        this.setName("");
        this.setNickname("");
        this.setPassword("");
        this.setPic("");
        this.setLogin(false);
        this.setUser_id("");
        UserPreferencesUtil.save(this);
    }
}
