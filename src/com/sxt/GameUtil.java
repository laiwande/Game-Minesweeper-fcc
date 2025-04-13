package com.sxt;

import java.awt.*;

/***
 * 工具类
 * 存放静态参数
 * 工具方法
 */

public class GameUtil {
    //雷个数
    static int RAY_MAX = 100;
    //宽高，横竖格子数量
    static int Map_W = 36;
    static int Map_H = 17;
    //雷区偏移量，常量距离
    static int OFFSET = 40;
    //格子边长
    static int SQUARE_LENGTH = 25;
    //插旗数量
    static int FLAG_NUM = 0;

    //鼠标相关
    //坐标
    static int MOUSE_X;
    static int MOUSE_Y;
    //状态
    static boolean LEFT = false;
    static boolean RIGHT = false;

    //游戏状态 0 游戏中 1 胜利 2 失败 3 难度选择
    static int state = 3;

    //游戏难度
    static int level;

    //倒计时
    static long START_TIME;
    static long END_TIME;

    //底层元素 -1 雷  0 空  1-8 对应数字
    // +2 在地图四周加一圈“保护壳”，这样处理数字提示时更方便，不容易越界
    static int[][] DATA_BOTTOM = new int[Map_W+2][Map_H+2];

    //顶层元素 -1 无覆盖  0 覆盖  1 插旗 2 插错旗
    static int[][] DATA_TOP = new int[Map_W+2][Map_H+2];

    //载入图片
    static Image boom = Toolkit.getDefaultToolkit().getImage("C:\\Users\\haitai\\Documents\\2025\\Minesweeper\\src\\imgs\\boom.jpg");
    static Image top = Toolkit.getDefaultToolkit().getImage("C:\\Users\\haitai\\Documents\\2025\\Minesweeper\\src\\imgs\\top.gif");
    static Image flag = Toolkit.getDefaultToolkit().getImage("C:\\Users\\haitai\\Documents\\2025\\Minesweeper\\src\\imgs\\flag.gif");
    static Image noflag = Toolkit.getDefaultToolkit().getImage("C:\\Users\\haitai\\Documents\\2025\\Minesweeper\\src\\imgs\\noflag.png");

    static Image face = Toolkit.getDefaultToolkit().getImage("C:\\Users\\haitai\\Documents\\2025\\Minesweeper\\src\\imgs\\lei.png");
    static Image over = Toolkit.getDefaultToolkit().getImage("C:\\Users\\haitai\\Documents\\2025\\Minesweeper\\src\\imgs\\over.jpg");
    static Image win = Toolkit.getDefaultToolkit().getImage("C:\\Users\\haitai\\Documents\\2025\\Minesweeper\\src\\imgs\\win.jpg");

    static Image[] images = new Image[9];
    static{
        for (int i = 1; i < 9; i++) {
            images[i] = Toolkit.getDefaultToolkit().getImage("C:\\Users\\haitai\\Documents\\2025\\Minesweeper\\src\\imgs\\num\\"+i+".png");
        }
    }
    static void drawWord(Graphics g,String str ,int x,int y,int size,Color color){
        g.setColor(color);
        g.setFont(new Font("仿宋",Font.BOLD,size));
        g.drawString(str,x,y);
    }
}
