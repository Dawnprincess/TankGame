package tankgame;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    Vector<Bullet> bullets = new Vector<>();
    private MyPanel panel; //面板引用：用于获取其他坦克列表做碰撞检测
    public EnemyTank(int x, int y, MyPanel panel) {
        super(x, y);
        this.panel = panel;
    }

    @Override
    public void run() {
        //敌人坦克随机移动
        while (isLive()) {
            Vector<Tank> others = panel.getOtherTanks(this);
            boolean moved = false;
            //一次只移动一步
            switch (getDirection()) {
                case 0:
                    moved = moveUp(others);
                    break;
                case 1:
                    moved = moveRight(others);
                    break;
                case 2:
                    moved = moveDown(others);
                    break;
                case 3:
                    moved = moveLeft(others);
                    break;
            }
            //随机射击
            if (moved && (int)(Math.random() * 100) < 2) {
                //没撞到，才小概率随机射击
                Bullet bullet = new Bullet(getMuzzleX(), getMuzzleY(), getDirection());
                bullets.add(bullet);
                new Thread(bullet).start();
            }
            if (!moved) {
                //撞到墙或坦克，换方向
                setDirection((int)(Math.random() * 4));
            } else if((int)(Math.random() * 100) < 5){
                //没撞到，才小概率随机转向
                setDirection((int)(Math.random() * 4));
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
