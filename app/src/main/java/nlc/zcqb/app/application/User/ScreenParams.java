package nlc.zcqb.app.application.User;


/**
 * Created by little_beauty on 2016/7/3.
 */
public class ScreenParams {
    int width;
    int height;
    float density;

    public float getScale() {
        if (width!=0&&height!=0)
        {
            return  ((float)width/1080 ) ;
        }else
            return 1;
    }

    public ScreenParams(int width, int height,float density) {
        this.width = width;
        this.density=density;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }
}
