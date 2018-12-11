package com.example.customview.refreshview.view.headInterface;

/**
 * Created by lvqiu on 2017/11/2.
 */

public interface GestureAction {
    public void pulldown(int position);
    public void pushup(int position);
    public void free(int duration);
    public void refreshing(int duration);
    public void backto(int position,int destination);
    public void idle();
}
