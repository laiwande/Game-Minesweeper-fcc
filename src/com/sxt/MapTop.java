package com.sxt;

import com.sxt.GameUtil;

import java.awt.*;

/***
 * 顶层地图类
 * 绘制顶层组件
 * 判断逻辑
 */

public class MapTop {

    //格子位置
    int temp_x;
    int temp_y;

    //重置游戏
    void reGame(){
        for (int i = 1; i <= GameUtil.Map_W ; i++) {
            for (int j = 1; j <= GameUtil.Map_H; j++) {
                GameUtil.DATA_TOP[i][j] = 0;
            }
        }
    }

    //判断逻辑
    void logic(){
        temp_x = temp_y = 0;
        if(GameUtil.MOUSE_X>GameUtil.OFFSET && GameUtil.MOUSE_Y>GameUtil.OFFSET*3) {
            //不加这个if会导致右边和上边会翻开格子,因为 -30/60+1 = 30/60+1 = 1
            temp_x = (GameUtil.MOUSE_X - GameUtil.OFFSET) / GameUtil.SQUARE_LENGTH + 1;
            temp_y = (GameUtil.MOUSE_Y - GameUtil.OFFSET * 3) / GameUtil.SQUARE_LENGTH + 1;
        }
        //在雷区才判断
        if(temp_x>=1&&temp_x<=GameUtil.Map_W && temp_y>=1&&temp_y<=GameUtil.Map_H) {
            if (GameUtil.LEFT) {
                //左击，覆盖变则翻开
                if (GameUtil.DATA_TOP[temp_x][temp_y] == 0) {
                    GameUtil.DATA_TOP[temp_x][temp_y] = -1;
                }
                spaceOpen(temp_x,temp_y);
                GameUtil.LEFT = false;
            }
            if (GameUtil.RIGHT) {
                //右击，覆盖则插旗
                if (GameUtil.DATA_TOP[temp_x][temp_y] == 0) {
                    GameUtil.DATA_TOP[temp_x][temp_y] = 1;
                    GameUtil.FLAG_NUM++;
                }
                //右击，插旗则恢复覆盖
                else if (GameUtil.DATA_TOP[temp_x][temp_y] == 1) {
                    GameUtil.DATA_TOP[temp_x][temp_y] = 0;
                    GameUtil.FLAG_NUM--;
                }
                //右击，数字区域
                else if(GameUtil.DATA_TOP[temp_x][temp_y]==-1){
                    numOpen(temp_x,temp_y);
                }
                GameUtil.RIGHT = false;
            }
        }
        boom();
        victory();
    }

    //数字右击
    void numOpen(int x,int y){
        int count=0;
        //下面主要判定数字与插旗数是否相等
        if(GameUtil.DATA_BOTTOM[x][y]>0){
            for (int i = x-1; i <=x+1 ; i++) {
                for (int j = y-1; j <=y+1 ; j++) {
                    if(GameUtil.DATA_TOP[i][j] == 1){
                        count++;
                    }
                }
            }
        }
        if(count==GameUtil.DATA_BOTTOM[x][y]){
            for (int i = x-1; i <=x+1 ; i++) {
                for (int j = y-1; j <=y+1 ; j++) {
                    if(GameUtil.DATA_TOP[i][j] != 1){
                        GameUtil.DATA_TOP[i][j]=-1;
                    }
                    //必须在雷区中，不在保护区，不然会数组越界
                    if(i>=1 && j>=1 && i<=GameUtil.Map_W && j<=GameUtil.Map_H) {
                        //递归调用翻开周围格子
                        spaceOpen(i, j);
                    }
                }
            }
        }
    }

    //失败判定
    boolean boom() {
        if(GameUtil.FLAG_NUM==GameUtil.RAY_MAX){
            for (int i = 1; i <= GameUtil.Map_W; i++) {
                for (int j = 1; j <= GameUtil.Map_H; j++) {
                    if (GameUtil.DATA_TOP[i][j] == 0) {
                        GameUtil.DATA_TOP[i][j] = -1;
                    }
                }
            }
        }
        for (int i = 1; i <= GameUtil.Map_W; i++) {
            for (int j = 1; j <= GameUtil.Map_H; j++) {
                if(GameUtil.DATA_BOTTOM[i][j]==-1 && GameUtil.DATA_TOP[i][j]==-1){
                    GameUtil.state = 2;
                    seeBoom();
                    return true;
                }
            }
        }
        return false;
    }

    //失败显示所有雷
    void seeBoom(){
        for (int i = 1; i <= GameUtil.Map_W; i++) {
            for (int j = 1; j <= GameUtil.Map_H; j++) {
                //底层是雷，顶层不是旗
                if (GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] != -1) {
                    GameUtil.DATA_TOP[i][j] = -1;
                }
                //底层不是雷，顶层是旗，显示插错旗
                if (GameUtil.DATA_BOTTOM[i][j] != -1 && GameUtil.DATA_TOP[i][j] == 1) {
                    GameUtil.DATA_TOP[i][j] = 2;
                }
            }
        }
    }

    //胜利判断
    boolean victory(){
        int count = 0;
        for (int i = 1; i <= GameUtil.Map_W; i++) {
            for (int j = 1; j <= GameUtil.Map_H; j++) {
                //统计未翻开数量
                if(GameUtil.DATA_TOP[i][j]!=-1){
                    count++;
                }
            }
        }
        if(count==GameUtil.RAY_MAX){
            GameUtil.state = 1;
            for (int i = 1; i <= GameUtil.Map_W; i++) {
                for (int j = 1; j <= GameUtil.Map_H; j++) {
                    //未翻开，变成旗
                    if (GameUtil.DATA_TOP[i][j] == 0) {
                        GameUtil.DATA_TOP[i][j] = 1;
                    }
                }
            }
            return true;
        }
        return false;
    }

    //打开空格
    void spaceOpen(int x,int y){
        if (GameUtil.DATA_BOTTOM[x][y]==0) {
            for (int i = x-1; i <=x+1 ; i++) {
                for (int j = y-1; j <=y+1 ; j++) {
                    if(GameUtil.DATA_TOP[i][j] != -1) {
                        //如果有旗子，则撤回旗子数
                        if(GameUtil.DATA_TOP[i][j]==1){
                            GameUtil.FLAG_NUM--;
                        }
                        GameUtil.DATA_TOP[i][j] = -1;
                        //必须在雷区中，不在保护区，不然会数组越界
                        if(i>=1 && j>=1 && i<=GameUtil.Map_W && j<=GameUtil.Map_H) {
                            //递归调用翻开周围格子
                            spaceOpen(i, j);
                        }
                    }
                }
            }
        }
    }

    //直接复制底层绘制方法，只取图片填充那块
    void paintSelf(Graphics g) {
        logic();
        //填底层图片,1-11保护壳
        for (int i = 1; i <= GameUtil.Map_W; i++) {
            for (int j = 1; j <= GameUtil.Map_H; j++) {
                //覆盖
                if (GameUtil.DATA_TOP[i][j] == 0) {
                    g.drawImage(
                            //选图片
                            GameUtil.top,
                            //起始坐标，末尾+1是为了让它居中，且不挡住线
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            //定义图片长宽
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            //观察图像是否加载完成的监听器，用于异步加载图像的场景,无需加载的可以直接填null
                            null
                    );
                }
                //插旗
                if (GameUtil.DATA_TOP[i][j] == 1) {
                    g.drawImage(
                            //选图片
                            GameUtil.flag,
                            //起始坐标，末尾+1是为了让它居中，且不挡住线
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            //定义图片长宽
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            //观察图像是否加载完成的监听器，用于异步加载图像的场景,无需加载的可以直接填null
                            null
                    );
                }
                //插错旗
                if (GameUtil.DATA_TOP[i][j] == 2) {
                    g.drawImage(
                            //选图片
                            GameUtil.noflag,
                            //起始坐标，末尾+1是为了让它居中，且不挡住线
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            //定义图片长宽
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            //观察图像是否加载完成的监听器，用于异步加载图像的场景,无需加载的可以直接填null
                            null
                    );
                }
            }
        }
    }
}

