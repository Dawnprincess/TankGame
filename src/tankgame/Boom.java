package tankgame;

public class Boom {
    private int x;         //爆炸中心x
    private int y;         //爆炸中心y
    private int life = 9;  //生命周期：每帧递减，减到0爆炸结束
    private boolean isLive = true;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //生命周期递减
    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLife() {
        return life;
    }

    public boolean isLive() {
        return isLive;
    }
}
