package tankgame;

import javax.swing.*;

public class Game01 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args){
        Game01 game01 = new Game01();
    }

    public Game01(){
        //创建一个画好的panel
        mp = new MyPanel();
        //放在当前这个窗口上
        this.add(mp);
        //增加监听
        this.addKeyListener(mp);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
