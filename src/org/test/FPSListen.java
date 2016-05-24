package org.test;

import java.text.DecimalFormat;

/**
 * <p>Title: LoonFramework</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: LoonFramework</p>
 * @author zhangjianxin  
 * @email：zhangjianxinnet@gmail.com
 * @version 0.1
 */
public class FPSListen {
    //设定动画的FPS桢数，此数值越高,动画速度越快。
    public static final int FPS = 10000;  

    // 换算为运行周期
    public static final long PERIOD = (long) (1.0 / FPS * 1000000000); // 单位: ns(纳秒)

    // FPS最大间隔时间，换算为1s = 10^9ns
    public static long FPS_MAX_INTERVAL = 1000000000L; // 单位: ns
    
    // 实际的FPS数值
    private double nowFPS = 0.0;
    
    // FPS累计用间距时间
    private long interval = 0L; // in ns
    private long time;

    //运行桢累计
    private long frameCount = 0;
    
    //格式化小数位数
    private DecimalFormat df = new DecimalFormat("0.0");
 
    //开启opengl
    public void opengl(){
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.translaccel", "True");
    }
    

    
    /**
     * 制造FPS数据
     * 
     */
    public void makeFPS() {
        frameCount++;
        interval += PERIOD;

        //当实际间隔符合时间时。
        if (interval >= FPS_MAX_INTERVAL) {
            //nanoTime()返回最准确的可用系统计时器的当前值，以毫微秒为单位
            long timeNow = System.nanoTime();
            // 获得到目前为止的时间距离
            long realTime = timeNow - time; // 单位: ns

            //换算为实际的fps数值
            nowFPS = ((double) frameCount / realTime) * FPS_MAX_INTERVAL;
            //变更数值
            frameCount = 0L;
            interval = 0L;
            time = timeNow;
        }
    }
    public long getFrameCount() {
        return frameCount;
    }
    public void setFrameCount(long frameCount) {
        this.frameCount = frameCount;
    }
    public long getInterval() {
        return interval;
    }
    public void setInterval(long interval) {
        this.interval = interval;
    }
    public double getNowFPS() {
        return nowFPS;
    }
    public void setNowFPS(double nowFPS) {
        this.nowFPS = nowFPS;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getFPS(){
        return df.format(nowFPS);
    }

}