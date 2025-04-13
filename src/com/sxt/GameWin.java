package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame {
    int width = 2* GameUtil.OFFSET+GameUtil.Map_W*GameUtil.SQUARE_LENGTH;
    int height = 4*GameUtil.OFFSET+GameUtil.Map_H*GameUtil.SQUARE_LENGTH;
    Image offScreenImage = null;
    MapBottom mapBottom = new MapBottom();
    MapTop mapTop = new MapTop();
    GameSelect gameSelect = new GameSelect();

    boolean begin = false;
    void launch(){
        GameUtil.START_TIME = System.currentTimeMillis();
        this.setVisible(true);  //设置窗口可见
        if(GameUtil.state == 3){
            this.setSize(500,500);
        }else{
            this.setSize(width,height);
        }
        this.setLocationRelativeTo(null);  //this.setLocation(x, y);设置坐标   null为居中
        this.setTitle("刘晓艳的扫雷");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  //点击X退出

        //鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (GameUtil.state){
                    case 0:
                        //左键点击
                        if(e.getButton()==1){
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.LEFT = true;
                        }
                        //右键点击
                        if(e.getButton()==3){
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.RIGHT = true;
                        }
                    case 1:
                    case 2:
                        if(e.getButton()==1){
                            if(
                                    e.getX()>GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.Map_W/2)
                                    && e.getX()<GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.Map_W/2)+GameUtil.SQUARE_LENGTH
                                    && e.getY()>GameUtil.OFFSET*3/2
                                    && e.getY()<GameUtil.OFFSET*3/2+ GameUtil.SQUARE_LENGTH
                            ){
                                mapBottom.reGame();
                                mapTop.reGame();
                                GameUtil.FLAG_NUM=0;
                                GameUtil.START_TIME = System.currentTimeMillis();
                                GameUtil.state = 0;
                            }
                        }
                        break;
                    case 3:
                        if(e.getButton()==1){
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            begin = gameSelect.hard();
                        }
                        break;
                }
            }
        });

        while (true){
            repaint();
            begin();
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void begin(){
        if(begin){
            begin = false;
            gameSelect.hard(GameUtil.level);
            dispose();
            GameWin gameWin= new GameWin();
            GameUtil.START_TIME = System.currentTimeMillis();
            GameUtil.FLAG_NUM=0;
            mapBottom.reGame();
            mapTop.reGame();
            gameWin.launch();

        }
    }

    //你不需要在main()里手动调用 paint() 是因为：
    //setVisible(true)，自动调用 paint()
    @Override
    public void paint(Graphics g) {
        if(GameUtil.state==3){
            offScreenImage = this.createImage(width, height);
            Graphics gImage = offScreenImage.getGraphics();
            gImage.setColor(Color.WHITE);
            gImage.fillRect(0,0,500,500);
            gameSelect.paintSelf(gImage);
            g.drawImage(offScreenImage, 0, 0, null);
        }else {
            //初始化画布
            offScreenImage = this.createImage(width, height);
            //让接下来画画内容都在画布上
            Graphics gImage = offScreenImage.getGraphics();
            //调用这个画笔，将顶层元素，底层元素都在这个画布中进行
            mapBottom.paintSelf(gImage);
            mapTop.paintSelf(gImage);
            //最后将这个画布和元素打包绘制到窗口中
            g.drawImage(offScreenImage, 0, 0, null);
        }
    }

    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
