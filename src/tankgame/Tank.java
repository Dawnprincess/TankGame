package tankgame;

public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed = 1;

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

    public void moveUp(){
        //边界碰撞检测
        if (y - 10 * speed < 0) return;
        y -= 10 * speed;
    }
    public void moveDown(){
        //边界碰撞检测
        if (y + 10 * speed + HEIGHT > MyPanel.winHEIGHT) return;
        y += 10 * speed;
    }
    public void moveLeft(){
        //边界碰撞检测
        if (x - 10 * speed < 0) return;
        x -= 10 * speed;
    }
    public void moveRight(){
        //边界碰撞检测
        if (x + 10 * speed + WIDTH > MyPanel.winHEIGHT) return;
        x += 10 * speed;
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
