package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener,Runnable{
    public static final int winWIDTH = 1600;
    public static final int winHEIGHT = 900;
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemySize = 3;

    public MyPanel(){
        //初始化坦克坐标
        hero = new Hero(100, 100);
        //初始化敌人tank
        for(int i = 0; i < enemySize; i++){
            enemyTanks.add(new EnemyTank(100 * i, 0));
        }
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        //绘制窗口大小，默认为黑色
        g.fillRect(0, 0, winWIDTH, winHEIGHT);

        //画tank
        drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 0);
        //画敌方tank
        for (EnemyTank enemyTank : enemyTanks) {
            drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
        }
        //绘制玩家子弹
        if(hero.getBullet() != null && hero.getBullet().isLive()){
            g.draw3DRect(hero.getBullet().getX(), hero.getBullet().getY(), 5, 5, false);
        }
    }

    /**
     * @param x 坦克左上角的x坐标
     * @param y 坦克左上角的y坐标
     * @param g 画笔
     * @param direction 坦克方向
     * 0:上 1:右 2:下 3:左
     * @param type 坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type){
        switch (type){
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.red);
                break;
        }

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        //坦克中心点
        int cx = x + 20;
        int cy = y + 30;
        //0:上 1:右 2:下 3:左，对应顺时针旋转0/90/180/270度
        //因为后续的rotate是绕原点旋转而不是坦克中心，所以需要先将坐标系移动到坦克中心
        g2d.translate(cx, cy);
        //把坐标系绕中心旋转90°整数倍
        g2d.rotate(Math.toRadians(direction * 90.0));
        //把坐标系原点再放回去
        g2d.translate(-cx, -cy);

        //只需绘制"朝上"的坦克，其他方向由旋转实现
        g.fill3DRect(x, y, 10, 60, false);           //左履带
        g.fill3DRect(x + 10, y + 10, 20, 40, false); //车身
        g.fill3DRect(x + 30, y, 10, 60, false);      //右履带
        g.fillOval(x + 10, y + 20, 20, 20);          //炮塔
        g.drawLine(x + 20, y + 30, x + 20, y);       //炮筒

        g2d.setTransform(old);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            //改变坦克方向
            hero.setDirection(0);
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.moveRight();
            hero.setDirection(1);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(2);
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(3);
            hero.moveLeft();
        }

        if(e.getKeyCode() == KeyEvent.VK_J)
            hero.shotBullet();

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
}
