package tankgame;

public class Bullet implements Runnable {
    private int x;
    private int y;
    private int direction;
    private int speed = 5;
    private boolean isLive = true;
    public Bullet(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    @Override
    public void run() {
        while (this.isLive) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            move();
        }
    }
    public void move() {
        switch (direction) {
            case 0:
                y -= speed;
                break;
            case 1:
                x += speed;
                break;
            case 2:
                y += speed;
                break;
            case 3:
                x -= speed;
                break;
        }
        //边界碰撞检测
        if (x < 0 || x > MyPanel.winWIDTH || y < 0 || y > MyPanel.winHEIGHT) {
            isLive = false;
        }
        //System.out.println("子弹当前位置:x = " + x + ", y = " + y);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getDirection() {
        return direction;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public boolean isLive() {
        return isLive;
    }
    public void setLive(boolean live) {
        isLive = live;
    }
}
