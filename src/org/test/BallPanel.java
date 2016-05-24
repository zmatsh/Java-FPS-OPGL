package org.test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * <p>
 * Title: LoonFramework
 * </p>
 * <p>
 * Description:以JAVA获取FPS用演示程序及随机生成乱数球体。(更优化代码内置于loonframework-game框架中)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: LoonFramework
 * </p>
 * 
 * @author zhangjianxin
 * @email：zhangjianxinnet@gmail.com
 * @version 0.1
 */
public class BallPanel extends Panel implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 1920;

    public static final int HEIGHT = 1080;

    // 设定最大球体数量
    private static final int NUM_BALLS = 100000;

    // 定义球体数组
    private Ball[] ball;

    // 运行状态
    private volatile boolean running = false;

    private Thread gameLoop;

    // 缓存用图形
    private Graphics bg;

    private Image screen = null;

    // 生成随机数
    private Random rand;

    // fps监听
    private FPSListen fps = null;

    public BallPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        screen = new BufferedImage(WIDTH, HEIGHT, 1);
        bg = screen.getGraphics();
        fps = new FPSListen();
        //fps.opengl();
        // 以当前毫秒生成随机数
        rand = new Random(System.currentTimeMillis());
        ball = new Ball[NUM_BALLS];
        // 初始化球体参数
        for (int i = 0; i < NUM_BALLS; i++) {
            int x = rand.nextInt(WIDTH);
            int y = rand.nextInt(HEIGHT);
            int vx = rand.nextInt(30);
            int vy = rand.nextInt(30);
            ball[i] = new Ball(x, y, vx, vy);
        }
    }

    // 加入Notify
    public void addNotify() {
        super.addNotify();
        // 判断循环条件是否成立
        if (gameLoop == null || !running) {
            gameLoop = new Thread(this);
            gameLoop.start();
        }
    }

    /**
     * 进行线程运作。
     */
    public void run() {
        long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;
        // 获得精确纳秒时间
        beforeTime = System.nanoTime();
        fps.setTime(beforeTime);
        running = true;
        while (running) {
            gameUpdate();
            repaint();
            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            // 换算间隔时间
            sleepTime = (FPSListen.PERIOD - timeDiff) - overSleepTime;
            if (sleepTime > 0) {
                // 制造延迟
                try {
                    Thread.sleep(sleepTime / 1000000L); // nano->ms
                } catch (InterruptedException e) {
                }
                // 获得延迟时间
                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            } else {
                // 重新计算
                overSleepTime = 0L;
                // 判断noDelays值
                if (++noDelays >= 16) {
                    Thread.yield(); // 令线程让步
                    noDelays = 0;
                }
            }

            // 重新获得beforeTime
            beforeTime = System.nanoTime();

            // 制造FPS结果
            fps.makeFPS();
        }

    }

    /**
     * 变更球体轨迹
     * 
     */
    private void gameUpdate() {
        for (int i = 0; i < NUM_BALLS; i++) {
            ball[i].move();
        }
    }

    /**
     * 变更图形
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * 显示图形
     */
    public void paint(Graphics g) {

        // 设定背景为白色，并清空图形
        bg.setColor(Color.WHITE);
        bg.fillRect(0, 0, WIDTH, HEIGHT);

        // FPS数值显示
        bg.setColor(Color.BLACK);
        bg.drawString("FPS: " + fps.getFPS(), 4, 16);
        System.out.println("FPS: " + fps.getFPS());
        // 分别绘制相应球体
        for (int i = 0; i < NUM_BALLS; i++) {
            ball[i].draw(bg);
        }
        g.drawImage(screen, 0, 0, this);
        g.dispose();
    }

    public static void main(String[] args) {

        Frame frm = new Frame();
        frm.setTitle("Java FPS速度测试(由Loonframework框架提供)");
        frm.setSize(WIDTH, HEIGHT+20);
        frm.setResizable(false);
        frm.add(new BallPanel());
        frm.setVisible(true);
        frm.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }

}