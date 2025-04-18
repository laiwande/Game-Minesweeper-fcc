package com.sxt;

import java.awt.*;

/***
 * 难度选择类
 */
public class GameSelect {

    //判断是否点击到
    boolean hard(){
        if(GameUtil.MOUSE_X>100 && GameUtil.MOUSE_X<400){
            if(GameUtil.MOUSE_Y>50 && GameUtil.MOUSE_Y<150){
                GameUtil.level = 1;
                GameUtil.state = 0;
                return true;
            }
            if(GameUtil.MOUSE_Y>200 && GameUtil.MOUSE_Y<300){
                GameUtil.level = 2;
                GameUtil.state = 0;
                return true;
            }
            if(GameUtil.MOUSE_Y>350 && GameUtil.MOUSE_Y<450){
                GameUtil.level = 3;
                GameUtil.state = 0;
                return true;
            }
        }
        return false;
    }
    void paintSelf(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(100,50,300,100);
        GameUtil.drawWord(g,"简单",220,100,30,Color.black);

        g.drawRect(100,200,300,100);
        GameUtil.drawWord(g,"普通",220,250,30,Color.black);

        g.drawRect(100,350,300,100);
        GameUtil.drawWord(g,"困难",220,400,30,Color.black);
    }

    void hard(int level){
        switch (level){
            case 1:
                GameUtil.RAY_MAX = 10;
                GameUtil.Map_W = 9;
                GameUtil.Map_H= 9;
                break;
            case 2:
                GameUtil.RAY_MAX = 40;
                GameUtil.Map_W = 16;
                GameUtil.Map_H= 16;
                break;
            case 3:
                GameUtil.RAY_MAX = 99;
                GameUtil.Map_W = 30;
                GameUtil.Map_H= 16;
                break;
            default:
        }
    }
}
