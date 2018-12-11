package nlc.zcqb.baselibrary.util;

/**
 * Created by lvqiu on 2017/6/7.
 */

/**
 *
 */
public class URL {

    public final static String homeIp="http://192.168.1.107:";
    public final static String labIp="http://192.168.1.106:";
    public final static String roomip="http://192.168.43.136:";
    public  static String ip=roomip;
    public final static String port="8080";
    public  static String downservice="/ssm/File/";
    public  static String uploadservice="/ssm/user/mobile/uploadfile";
    public  static String mutixuploadservice="/ssm/user/mobile/uploadmutixfiles";



    public  static String name2="black.jpg";
    public  static String name3="video.mp4";
    public  static String name4="video1.mp4";

    public  static String Login_url= port+ "/ssm/Login/";
    public  static String Register_url= port+ "/ssm/Register/";
    public  static String Usericon_url= port+ "/ssm/getUsericon/";
    public  static String breakpointdownservice="/ssm/breakpointdown/";

    public  static String fillDetail_url= port+ "/ssm/user/filldetail";
    public  static String getUserDetail_url= port+ "/ssm/user/getUser";
    public  static String TestToken_url= port+ "/ssm/user/testToken";
    public  static String Fixpassword_url= port+ "/ssm/user/Fix/";
    public  static String Logout_url= port+ "/ssm/user/Logout/";
    public  static String sendFriendZone= port+ "/ssm/user/sendFriendZone/";
    public  static String getZoneByusername= port+ "/ssm/user/getZoneByusername/";

    public  static String breakpointdownByPath= port+ "/ssm/breakpointdownByPath/";
    public  static String getCloudList= port+ "/ssm/GetCloudPicList/";

    public  static String image_url=  port+downservice+"black.jpg";
    public  static String image_url2=  port+downservice+"generated.jpg";

    public  static String download_url=  port+breakpointdownservice;
    public  static String upload_url=  port+uploadservice;
    public  static String uploadmutix_url=  port+mutixuploadservice;

}
