package com.play.edit_dp_and_post.editpost_dp;

public class NewApp
{
    int sno;
    String newappimgname;
    String textshow;
    String appuri;

    public NewApp() {
    }

    public NewApp(int sno, String newappimgname, String textshow,String appuri) {
        this.sno = sno;
        this.newappimgname = newappimgname;
        this.textshow = textshow;
        this.appuri=appuri;
    }


    public String getAppuri() {
        return appuri;
    }

    public void setAppuri(String appuri) {
        this.appuri = appuri;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getNewappimgname() {
        return newappimgname;
    }

    public void setNewappimgname(String newappimgname) {
        this.newappimgname = newappimgname;
    }

    public String getTextshow() {
        return textshow;
    }

    public void setTextshow(String textshow) {
        this.textshow = textshow;
    }
}
