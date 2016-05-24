package org.test;

import java.awt.Color;
import java.awt.Graphics;
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
 * <p>Title: LoonFramework</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: LoonFramework</p>
 * @author zhangjianxin  
 * @email： zhangjianxinnet@gmail.com
 * @version 0.1
 */
public class Ball {
        private static final int SIZE = 10;
        private int x, y;
        protected int vx, vy;

        public Ball(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public void move() {
            x += vx;
            y += vy;
            if (x < 0 || x > BallPanel.WIDTH - SIZE) {
                vx = -vx;
            }
            if (y < 0 || y > BallPanel.HEIGHT - SIZE) {
                vy = -vy;
            }
        }

        public void draw(Graphics g) {
           // g.setColor(Color.RED);
            g.setColor(new Color(
              (new Double(Math.random() * 128)).intValue() + 128,   
	          (new Double(Math.random() * 128)).intValue() + 128,   
	          (new Double(Math.random() * 128)).intValue() + 128));
            g.fillOval(x, y, SIZE, SIZE);
        }
    

}