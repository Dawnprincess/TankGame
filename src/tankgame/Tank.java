package tankgame;

public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed = 1;

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
        y -= 10 * speed;
    }
    public void moveDown(){
        y += 10 * speed;
    }
    public void moveLeft(){
        x -= 10 * speed;
    }
    public void moveRight(){
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
