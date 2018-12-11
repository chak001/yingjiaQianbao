package nlc.zcqb.baselibrary.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by _SOLID
 * Date:2016/4/1
 * Time:16:11
 */
public class StringUtils {
    public static int NInt(Integer integer){
        if (integer==null){
            return 0;
        }
        return integer;
    }

    public static int NInt(String integer){
        if (integer==null || !isNumeric(integer)){
            return 0;
        }
        int i = Integer.valueOf(integer);
        return i;
    }

    /**
     * 验证身份证
     * @param IDNumber
     * @return
     */
    public static boolean isCard_ID(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }



    public static String toUtf(String str){
        String path=str;
        try {
            path= java.net.URLEncoder.encode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
    }



    public static ArrayList<String> StringToList(String temp, String splitword){
        ArrayList<String> list;
        if (temp!=null && !temp.equals("")){
            String[] words= temp.split(splitword);
            List<String> templist=Arrays.asList(words);
            //因为返回的是T类型的list，所以需要重新生成arraylist，要不然报错
            list= new ArrayList<>(templist);
        }else {
            list=null;
        }
        return list;
    }


    /**
     * 将字符串进行md5转换
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(str.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(str.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0 || getNullString(s).equals("");
    }

    /**
     * 清除文本里面的HTML标签
     *
     * @param htmlStr
     * @return
     */
    public static String clearHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        Log.v("htmlStr", htmlStr);
        try {
            Pattern p_script = Pattern.compile(regEx_script,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            Pattern p_style = Pattern.compile(regEx_style,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            Pattern p_html = Pattern.compile(regEx_html,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
        } catch (Exception e) {
            htmlStr = "clear error";

        }

        return htmlStr; // 返回文本字符串
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param str
     * @return 转换异常返回 0
     */
    public static int toInt(String str) {
        if (str == null)
            return 0;
        return toInt(str.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param str
     * @return 转换异常返回 0
     */
    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }


        public static final String REGISTER_SELECTED_TPYE_VALUE="REGISTER_SELECTED_TPYE_VALUE";
        public static final String REGISTER_SELECTED_TPYE_TEXT="REGISTER_SELECTED_TPYE_TEXT";
        public static final String REGISTER_USER_BASE_INFO="REGISTER_USER_BASE_INFO";


        public static String getNullString(String string) {
            if (null != string && !string.equals("") && !string.equals("null") && !string.equals("NULL")) {
                return string;
            } else {
                return "";
            }
        }

    public static String stringDefault(String string,String defaults) {
        if (null != string && !string.equals("") && !string.equals("null") && !string.equals("NULL")) {
            return string;
        } else {
            return defaults;
        }
    }

        public static boolean  NotNull(String string) {
            if (null != string && !string.equals("") && !string.equals("null") && !string.equals("NULL")) {
                return true;
            } else {
                return false;
            }
        }

        public static String ifGreaterThousand(int count) {
            if(count < 0){
                throw new IllegalArgumentException("this argument should greater than 1000");
            }else if(count >= 1000){
                return count/1000 +"k";
            }else{
                return count+"";
            }
        }

        /**
         *
         * @param string
         * @param replace  如果为空，代替字符串
         * @return
         */
        public static String getReplaceString(String string,String replace) {
            if (null != string && !string.equals("") && !string.equals("null")) {
                return string;
            } else {
                return replace;
            }
        }
        public static String get0String(String string) {
            if (null != string && !string.equals("") && !string.equals("null")) {
                return string;
            } else {
                return "0";
            }
        }


        /**
         * 判断mail是否合理
         *
         * @param email
         */
        public static boolean judgeEmail(String email) {

            if(isEmail(email))
            {
                return true;
            }

            return false;
        }
        public static boolean isEmail(String email){
            //正则表达式
            //不适用正则
            if(email==null||"".equals(email))
                return false ;

            if(!containsOneWord('@',email)||!containsOneWord('.',email))
                return false;
            String prefix = email.substring(0,email.indexOf("@"));
            String middle = email.substring(email.indexOf("@")+1,email.indexOf("."));
            String subfix = email.substring(email.indexOf(".")+1);
            System.out.println("prefix="+prefix +"  middle="+middle+"  subfix="+subfix);

            if(prefix==null||prefix.length()>40||prefix.length()==0)
                return false ;
            if(!isNumeric(prefix))
                return false ;
            if(middle==null||middle.length()>40||middle.length()==0)
                return false ;
            if(!isLetterorNum(middle))
                return false ;
            if(subfix==null||subfix.length()>3||subfix.length()<2)
                return false ;
            if(!isOnlyLetter(subfix))
                return false ;

            return true ;
        }
        //判断字符串只包含指定的一个字符c
        private static boolean containsOneWord(char c , String word){
            char[] array = word.toCharArray();
            int count = 0 ;
            for(Character ch : array){
                if(c == ch) {
                    count++;
                }
            }
            return count==1 ;
        }

        /**
         * 判断身份证号是否合理
         *
         * @param idcard
         */
        public static boolean judgeIdCard(String idcard) {

            CheckValue return_value=IDCardValidate(idcard);
            if(return_value.isright())
            {
                return true;
            }

            return false;
        }
        /**
         * 判断身份证号是否合理
         *
         * @param qq
         */
        public static boolean judgeQQ(String qq) {


            if(isNumeric(qq))
            {
                return true;
            }

            return false;
        }
        /**
         * 判断真实姓名是否合理
         *
         * @param pwd
         */
        public static boolean judgeRealName(String pwd) {


            if(isOnlyLetter(pwd)||isOnlyChinese(pwd))
                return true;

            return false;
        }

        /**
         * 判断手机密码是否合理
         *
         * @param pwd
         */
        public static boolean judgePwd(String pwd) {
            if (isMatchLength(pwd, 6,12))
                if(	isLetterorNum(pwd))
                    if(!isOnlyLetter(pwd))
                        if(!isNumeric(pwd))
                            return true;

            return false;
        }
        /**
         * 判断手机号码是否合理
         *
         * @param phoneNums
         */
        public static boolean judgePhoneNums(String phoneNums) {
            if (StringUtils.isMatchLength(phoneNums, 11)
                    && StringUtils.isMobileNO(phoneNums)) {
                return true;
            }

            return false;
        }
        /**
         * 判断证书编号是否合理
         *
         * @param
         */
        public static boolean judgeCertificateNo(String no) {
//		if (StringUtils.isMatchLength(no,18, 20))
            if(StringUtils.isNumeric(no))
            {
                return true;
            }

            return false;
        }
        /**
         * 判断昵称是否合理
         *
         * @param nick
         */
        public static boolean judgeNickName(String nick) {
            if (isMatchLength(nick, 4,16)) {
                if (isLetterDigitOrChinese(nick)) {
                    return true;
                }
            }

            return false;
        }
        /**
         * 功能：判断字符串是否为日期格式
         *
         * @param strDate
         * @return
         */
        public static boolean isDate(String strDate) {
            Pattern pattern = Pattern
                    .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
            Matcher m = pattern.matcher(strDate);
            if (m.matches()) {
                return true;
            } else {
                return false;
            }
        }
        /*
             ^ 与字符串开始的地方匹配，不匹配任何字符
             $ 与字符串结束的地方匹配，不匹配任何字符
             + 表达式至少出现1次，相当于 {1,}，比如："a+b"可以匹配 "ab","aab","aaab"...
         */
        private static boolean isLetterorNum(String str) {

            String regex = "^[a-z0-9A-Z]+$";
            return str.matches(regex);

        }

        //只有字母数字或者中文
        public static boolean isLetterDigitOrChinese(String str) {
            String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
            boolean flag= str.matches(regex);
            return flag;
        }
        /*判断字符串是否仅为数字:*/
        public static boolean isNumeric(String str){

            Pattern pattern = Pattern.compile("[0-9]*");
            return pattern.matcher(str).matches();
        }
        /*判断字符串是否仅为中文:*/
        public static boolean isOnlyChinese(String str){


            Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]*");

            return pattern.matcher(str).matches();
        }
        /*判断字符串是否仅为汉字:*/
        public static boolean isOnlyLetter(String str){

            Pattern pattern = Pattern.compile("[a-zA-Z]*");
            return pattern.matcher(str).matches();
        }

        /**
         * 判断一个字符串的位数
         * @param str
         * @param length
         * @return
         */
        public static boolean isMatchLength(String str, int length) {
            if (str.isEmpty()) {
                return false;
            } else
            {
                int valueLength = 0;
                String chinese = "[\u4e00-\u9fa5]";
                for (int i = 0; i < str.length(); i++) {
                    String temp = str.substring(i, i + 1);
                    if (temp.matches(chinese)) {
                        valueLength += 2;
                    } else {
                        valueLength += 1;
                    }
                }
                if (valueLength==length)
                    return true;

            }
            return  false;
        }
        /**
         * 判断一个字符串的位数是否在 输入的两个值之间
         * @param str
         * @param length_min
         * @param length_max
         * @return
         */
        public static boolean isMatchLength(String str,int length_min ,int length_max) {
            if(str.length()<=length_max && str.length()>=length_min)
                return true;
            return false;
//		if (str.isEmpty()) {
//			return false;
//		} else  {
//			int valueLength = 0;
//			String chinese = "[\u4e00-\u9fa5]";
//			for (int i = 0; i < str.length(); i++) {
//				String temp = str.substring(i, i + 1);
//				if (temp.matches(chinese)) {
//					valueLength += 2;
//				} else {
//					valueLength += 1;
//				}
//			}
//			if (valueLength>=length_min && valueLength<=length_max)
//			return true;
//		}
//		return false;
        }

        /**
         * 验证手机格式
         */
        public static boolean isMobileNO(String mobileNums) {
		 /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186
         * 电信：133、153、180、189、177（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
//		String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            String telRegex = "[1]\\d{10}";// "[1]"代表第1位为数字1 ，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            if (TextUtils.isEmpty(mobileNums))
                return false;
            else
                return mobileNums.matches(telRegex);
        }

        public static CheckValue IDCardValidate(String IDStr)  {
            String errorInfo = "";// 记录错误信息
            String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
                    "3", "2" };
            String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                    "9", "10", "5", "8", "4", "2" };
            String Ai = "";
            // ================ 号码的长度 15位或18位 ================
            if (IDStr.length() != 15 && IDStr.length() != 18) {
                errorInfo = "身份证号码长度应该为15位或18位。";

                return new CheckValue(false,errorInfo);
            }
            // =======================(end)========================

            // ================ 数字 除最后以为都为数字 ================
            if (IDStr.length() == 18) {
                Ai = IDStr.substring(0, 17);
            } else if (IDStr.length() == 15) {
                Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
            }
            if (StringUtils.isNumeric(Ai) == false) {
                errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
                return new CheckValue(false,errorInfo);
            }
            // =======================(end)========================

            // ================ 出生年月是否有效 ================
            String strYear = Ai.substring(6, 10);// 年份
            String strMonth = Ai.substring(10, 12);// 月份
            String strDay = Ai.substring(12, 14);// 月份
            if (StringUtils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
                errorInfo = "身份证生日无效。";
                return new CheckValue(false,errorInfo);
            }
            GregorianCalendar gc = new GregorianCalendar();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                        || (gc.getTime().getTime() - s.parse(
                        strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                    errorInfo = "身份证生日不在有效范围。";
                    return new CheckValue(false,errorInfo);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
                errorInfo = "身份证月份无效";
                return new CheckValue(false,errorInfo);
            }
            if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
                errorInfo = "身份证日期无效";
                return new CheckValue(false,errorInfo);
            }
            // =====================(end)=====================

            // ================ 地区码时候有效 ================
            Hashtable h = GetAreaCode();
            if (h.get(Ai.substring(0, 2)) == null) {
                errorInfo = "身份证地区编码错误。";
                return new CheckValue(false,errorInfo);
            }
            // ==============================================

            // ================ 判断最后一位的值 ================
            int TotalmulAiWi = 0;
            for (int i = 0; i < 17; i++) {
                TotalmulAiWi = TotalmulAiWi
                        + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                        * Integer.parseInt(Wi[i]);
            }
            int modValue = TotalmulAiWi % 11;
            String strVerifyCode = ValCodeArr[modValue];
            Ai = Ai + strVerifyCode;

            if (IDStr.length() == 18) {
                if (Ai.equals(IDStr) == false) {
                    errorInfo = "身份证无效，不是合法的身份证号码";
                    return new CheckValue(false,errorInfo);
                }
            } else {
                return new CheckValue(true,"");
            }
            // =====================(end)=====================
            return new CheckValue(true,"");
        }
        /**
         * 功能：设置地区编码
         *
         * @return Hashtable 对象
         */

        private static Hashtable GetAreaCode() {
            Hashtable hashtable = new Hashtable();
            hashtable.put("11", "北京");
            hashtable.put("12", "天津");
            hashtable.put("13", "河北");
            hashtable.put("14", "山西");
            hashtable.put("15", "内蒙古");
            hashtable.put("21", "辽宁");
            hashtable.put("22", "吉林");
            hashtable.put("23", "黑龙江");
            hashtable.put("31", "上海");
            hashtable.put("32", "江苏");
            hashtable.put("33", "浙江");
            hashtable.put("34", "安徽");
            hashtable.put("35", "福建");
            hashtable.put("36", "江西");
            hashtable.put("37", "山东");
            hashtable.put("41", "河南");
            hashtable.put("42", "湖北");
            hashtable.put("43", "湖南");
            hashtable.put("44", "广东");
            hashtable.put("45", "广西");
            hashtable.put("46", "海南");
            hashtable.put("50", "重庆");
            hashtable.put("51", "四川");
            hashtable.put("52", "贵州");
            hashtable.put("53", "云南");
            hashtable.put("54", "西藏");
            hashtable.put("61", "陕西");
            hashtable.put("62", "甘肃");
            hashtable.put("63", "青海");
            hashtable.put("64", "宁夏");
            hashtable.put("65", "新疆");
            hashtable.put("71", "台湾");
            hashtable.put("81", "香港");
            hashtable.put("82", "澳门");
            hashtable.put("91", "国外");
            return hashtable;
        }




}
