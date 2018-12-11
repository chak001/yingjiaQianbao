package nlc.zcqb.baselibrary.util;

/**
 * Created by zwzd-01 on 2016/5/24.
 */
public class CheckValue {

        private boolean isright=false;
        private String error="";
        public CheckValue(boolean flag, String err)
        {
            this.isright=flag;
            this.error=err;
        }

        public String getError() {
            return error;
        }

        public boolean isright() {
            return isright;
        }


}
