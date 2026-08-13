package tankgame;

public class Hero extends Tank{
    public Bullet bullet = null;
    public Hero(int x, int y){
        super(x, y);
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    //监听按键，当用户按下J的时候，发射子弹
    public void shotBullet(){
        //创建子弹对象，子弹从炮口射出
        bullet = new Bullet(this.getMuzzleX(), this.getMuzzleY(), this.getDirection());
        new Thread(bullet).start();
    }
}
