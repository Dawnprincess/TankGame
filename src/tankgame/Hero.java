package tankgame;

import java.util.Vector;

public class Hero extends Tank{
    public Vector<Bullet> bullets = new Vector<>();
    public Hero(int x, int y){
        super(x, y);
    }

    //监听按键，当用户按下J的时候，发射子弹
    public void shotBullet(){
        //创建子弹对象，子弹从炮口射出
        Bullet bullet = new Bullet(this.getMuzzleX(), this.getMuzzleY(), this.getDirection());
        bullets.add(bullet);
        new Thread(bullet).start();
    }
}
