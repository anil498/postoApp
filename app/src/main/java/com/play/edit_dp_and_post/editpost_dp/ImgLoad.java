package com.play.edit_dp_and_post.editpost_dp;

import java.io.Serializable;

public class ImgLoad implements Serializable
{
    String name;
    String imgpath;
    long time;

    public ImgLoad() {
    }

    public ImgLoad(String name, String imgpath, long time) {
        this.name = name;
        this.imgpath = imgpath;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
///////////////

/*public class ImgLoad {
    String name;
    String imgpath;
    long time;

    public ImgLoad() {
    }

    public ImgLoad(String name, String imgpath, long time) {
        this.name = name;
        this.imgpath = imgpath;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
 */

