package tankgame;

import java.awt.Rectangle;
import java.util.Vector;

public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed = 1;
    private boolean isLive = true;

    public static final int WIDTH = 40;
    public static final int HEIGHT = 60;
    public Tank(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    //两参构造器：不指定speed时，默认speed为1
    public Tank(int x, int y) {
        this(x, y, 1);
    }

    //others：除自己以外的所有存活坦克，由游戏层传入
    public boolean moveUp(Vector<Tank> others){
        int targetY = y - 10 * speed;
        //窗口边界检测
        if (targetY < 0) return false;
        //坦克碰撞检测：检查目标位置是否与其他坦克重叠
        for (Tank other : others) {
            if (new Rectangle(x, targetY, WIDTH, HEIGHT).intersects(other.getRect())) {
                return false;
            }
        }
        y = targetY;
        return true;
    }
    public boolean moveDown(Vector<Tank> others){
        int targetY = y + 10 * speed;
        //窗口边界检测
        if (targetY + HEIGHT > MyPanel.winHEIGHT) return false;
        //坦克碰撞检测
        for (Tank other : others) {
            if (new Rectangle(x, targetY, WIDTH, HEIGHT).intersects(other.getRect())) {
                return false;
            }
        }
        y = targetY;
        return true;
    }
    public boolean moveLeft(Vector<Tank> others){
        int targetX = x - 10 * speed;
        //窗口边界检测
        if (targetX < 0) return false;
        //坦克碰撞检测
        for (Tank other : others) {
            if (new Rectangle(targetX, y, WIDTH, HEIGHT).intersects(other.getRect())) {
                return false;
            }
        }
        x = targetX;
        return true;
    }
    public boolean moveRight(Vector<Tank> others){
        int targetX = x + 10 * speed;
        //窗口边界检测
        if (targetX + WIDTH > MyPanel.winWIDTH) return false;
        //坦克碰撞检测
        for (Tank other : others) {
            if (new Rectangle(targetX, y, WIDTH, HEIGHT).intersects(other.getRect())) {
                return false;
            }
        }
        x = targetX;
        return true;
    }
    public Rectangle getRect() {
        if (direction == 1 || direction == 3) {
            return new Rectangle(x - 10, y + 10, 60, 40); //旋转后的实际范围
        }
        return new Rectangle(x, y, WIDTH, HEIGHT);
       }
    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getY() {
        return y;
    }

    //炮口x坐标：坦克中心沿炮筒方向偏移30
    public int getMuzzleX() {
        int cx = x + 20; //坦克中心x
        switch (direction) {
            case 1:  return cx + 30; //右
            case 3:  return cx - 30; //左
            default: return cx;      //上、下方向x不变
        }
    }

    //炮口y坐标
    public int getMuzzleY() {
        int cy = y + 30; //坦克中心y
        switch (direction) {
            case 0:  return cy - 30; //上
            case 2:  return cy + 30; //下
            default: return cy;      //左、右方向y不变
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
