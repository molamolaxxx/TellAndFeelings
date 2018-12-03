package com.mola.tellandfeelings.pojo;

public class Setting {
    private static Setting setting;
    /**
     * @Param percent 随机比 指顺序展示和随机展示的比例，0为全部顺序展示，100为全随机展示
     * 顺序展示完毕之后全部为随机展示
     */
    private int percent;//0---100
    /**
     * @Param showSpeed 展示速度,单位为秒
     *
     */
    private int showSpeed;
    /**
     * @Param showTranslateTime 动画的速度
     * 0----200ms
     * 1----500ms
     * 2----1000ms
     */
    private int showTranslateTime;
    public static int TRAN_SLOW=100;
    public static int TRAN_MID=400;
    public static int TRAN_FAST=800;
    /**
     * @Param MaxTellNum tell的最大数
     */
    private int maxTellNum;
    //单例setting
    public static Setting getSettingInstance(){
        if(setting==null)
            setting=new Setting();
        return setting;
    }
    //初始化变量
    public Setting(){
        //设置初始比例为50%
        percent=50;
        //设置初始speed为三秒一动
        showSpeed=3000;
        showTranslateTime=TRAN_MID;
        //最大数设为10，无法改变
        maxTellNum=10;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getShowSpeed() {
        return showSpeed;
    }

    public void setShowSpeed(int showSpeed) {
        this.showSpeed = showSpeed;
    }

    public int getShowTranslateTime() {
        return showTranslateTime;
    }

    public void setShowTranslateTime(int showTranslateTime) {
        this.showTranslateTime = showTranslateTime;
    }

    public int getMaxTellNum() {
        return maxTellNum;
    }

    public void setMaxTellNum(int maxTellNum) {
        this.maxTellNum = maxTellNum;
    }
}
