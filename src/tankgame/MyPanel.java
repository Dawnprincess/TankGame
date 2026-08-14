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
    Vector<Boom> booms = new Vector<>();  //爆炸效果集合
    int enemySize = 3;

    public MyPanel(){
        //初始化坦克坐标
        hero = new Hero(800, 500);
        //初始化敌人tank
        for(int i = 0; i < enemySize; i++){
            EnemyTank enemyTank = new EnemyTank(100 * i, 0, this);
            enemyTank.setDirection(2);
            enemyTanks.add(enemyTank);
            new Thread(enemyTank).start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //绘制窗口大小，默认为黑色
        g.fillRect(0, 0, winWIDTH, winHEIGHT);

        //画tank
        if(hero.isLive())
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 0);
        //画敌方tank
        for (EnemyTank enemyTank : enemyTanks) {
            if(enemyTank.isLive())
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
        }
        //绘制玩家子弹
        for (int i = 0; i < hero.bullets.size(); i++){
            Bullet bullet = hero.bullets.get(i);
        if (bullet != null && bullet.isLive()) {
            g.draw3DRect(bullet.getX(), bullet.getY(), 5, 5, false);
        } else {
            hero.bullets.remove(bullet);
            }
        }
        //绘制敌人子弹
        for(int i = 0; i < enemyTanks.size(); i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            for(int j = 0; j < enemyTank.bullets.size(); j++){
                Bullet bullet = enemyTank.bullets.get(j);
                if(bullet != null && bullet.isLive()){
                    g.draw3DRect(bullet.getX(), bullet.getY(), 5, 5, false);
                }else{
                    enemyTank.bullets.remove(bullet);
                }
            }
        }
        //绘制爆炸效果
        for(int i = 0; i < booms.size(); i++){
            Boom boom = booms.get(i);
            if(boom.isLive()){
                drawBoom(boom, g);
            }else{
                booms.remove(boom);
            }
        }
        //移出已死亡敌方坦克
        for(int i = enemyTanks.size() - 1; i >= 0; i--){
            if(!enemyTanks.get(i).isLive()){
                enemyTanks.remove(i);
            }
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

    //根据生命周期绘制爆炸：life越大爆炸越大
    public void drawBoom(Boom boom, Graphics g){
        int x = boom.getX();
        int y = boom.getY();
        int size = boom.getLife() * 10; //最大90，最小10
        g.setColor(Color.orange);
        g.fillOval(x - size / 2, y - size / 2, size, size);
        g.setColor(Color.yellow);
        g.fillOval(x - size / 4, y - size / 4, size / 2, size / 2);
    }

    //检测子弹是否击中敌人坦克
    public void hitEnemyTank() {
        for(Bullet bullet : hero.bullets){
            if(bullet == null || !bullet.isLive()){
                continue;
            }
            for(EnemyTank enemyTank : enemyTanks){
                //检测子弹是否击中敌人坦克
                if(hitTank(bullet, enemyTank)){
                    bullet.setLive(false);
                    enemyTank.setLive(false);
                    //在坦克中心生成爆炸
                    booms.add(new Boom(enemyTank.getX() + 20, enemyTank.getY() + 30));
                }
            }
        }
    }
    public void hitHero(){
        for(EnemyTank enemyTank : enemyTanks){
            for(Bullet bullet : enemyTank.bullets){
                if(bullet == null || !bullet.isLive()){
                    continue;
                }
                if(hitTank(bullet, hero)){
                    bullet.setLive(false);
                    hero.setLive(false);
                    //在坦克中心生成爆炸
                    booms.add(new Boom(hero.getX() + 20, hero.getY() + 30));
                }
            }
        }
    }
    //子弹碰撞检测
    public boolean hitTank(Bullet bullet, Tank tank){
        Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), 5, 5);
        Rectangle tankRect = new Rectangle(tank.getX(), tank.getY(), 40, 60);
        return bulletRect.intersects(tankRect);
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(!hero.isLive()){
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            //改变坦克方向
            hero.setDirection(0);
            heroMove(0);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(1);
            heroMove(1);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(2);
            heroMove(2);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(3);
            heroMove(3);
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
            hitEnemyTank();
            hitHero();
            //爆炸效果每帧递减
            for(Boom boom : booms){
                boom.lifeDown();
            }
            this.repaint();
        }
    }

    //获取除me以外所有存活坦克的列表
    public Vector<Tank> getOtherTanks(Tank me) {
        Vector<Tank> others = new Vector<>();
        if (me != hero && hero.isLive()) {
            others.add(hero);
        }
        for (EnemyTank enemyTank : enemyTanks) {
            if (enemyTank != me && enemyTank.isLive()) {
                others.add(enemyTank);
            }
        }
        return others;
    }

    //英雄移动：撞墙或撞坦克则原地不动（move方法返回false）
    public void heroMove(int direction) {
        Vector<Tank> others = getOtherTanks(hero);
        switch (direction) {
            case 0: hero.moveUp(others); break;
            case 1: hero.moveRight(others); break;
            case 2: hero.moveDown(others); break;
            case 3: hero.moveLeft(others); break;
        }
    }
}
