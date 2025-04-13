package com.sxt;

import com.sxt.BottomNum;
import com.sxt.BottomRay;
import com.sxt.GameUtil;

import java.awt.*;

/***
 * 底层地图
 * 绘制游戏相关组件
 */

public class MapBottom {
    //底层雷
    BottomRay bottomRay = new BottomRay();
    //底层数字
    BottomNum bottomNum = new BottomNum();
    {
        bottomRay.newRay();
        bottomNum.newNum();
    }

    //重置游戏
    void reGame(){
        for (int i = 1; i <= GameUtil.Map_W ; i++) {
            for (int j = 1; j <= GameUtil.Map_H; j++) {
                GameUtil.DATA_BOTTOM[i][j] = 0;
            }
        }
        bottomRay.newRay();
        bottomNum.newNum();
    }

    //底层画面
    //Graphics是画笔
    void paintSelf(Graphics g) {

        g.setColor(Color.gray);

        //画大致布局
        //画竖线
        for (int i = 0; i <= GameUtil.Map_W; i++) {
            g.drawLine(
                    GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    3 * GameUtil.OFFSET,
                    GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    3 * GameUtil.OFFSET + GameUtil.Map_H * GameUtil.SQUARE_LENGTH);
        }
        //画横线
        for (int i = 0; i <= GameUtil.Map_H; i++) {
            g.drawLine(
                    GameUtil.OFFSET,
                    3 * GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    GameUtil.OFFSET + GameUtil.Map_W * GameUtil.SQUARE_LENGTH,
                    3 * GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH
            );
        }

        //填底层图片,1-11保护壳
        for (int i = 1; i <= GameUtil.Map_W; i++) {
            for (int j = 1; j <= GameUtil.Map_H; j++) {
                //雷
                if (GameUtil.DATA_BOTTOM[i][j] == -1) {
                    g.drawImage(
                            //选图片
                            GameUtil.boom,
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
                //数字
                if (GameUtil.DATA_BOTTOM[i][j] >= 0) {
                    g.drawImage(
                            //选图片
                            GameUtil.images[GameUtil.DATA_BOTTOM[i][j]],
                            //起始坐标，末尾+1是为了让它居中，且不挡住线
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 6,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 5,
                            //定义图片长宽
                            GameUtil.SQUARE_LENGTH - 10,
                            GameUtil.SQUARE_LENGTH - 10,
                                //观察图像是否加载完成的监听器，用于异步加载图像的场景,无需加载的可以直接填null
                            null
                        );

                }
            }
        }

        //绘制数字，剩余雷数,倒计时
        GameUtil.drawWord(g,""+(GameUtil.RAY_MAX-GameUtil.FLAG_NUM),GameUtil.OFFSET,2*GameUtil.OFFSET,30,Color.black);
        GameUtil.drawWord(g,""+((GameUtil.END_TIME-GameUtil.START_TIME)/1000),GameUtil.OFFSET+GameUtil.Map_W*GameUtil.SQUARE_LENGTH-20,2*GameUtil.OFFSET,30,Color.black);


        //上中
        switch (GameUtil.state){
            case 0:
                GameUtil.END_TIME = System.currentTimeMillis(); //游戏进行中实时记录
                g.drawImage(
                        GameUtil.face,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.Map_W/2),
                        GameUtil.OFFSET*3/2,
                        GameUtil.SQUARE_LENGTH +10,
                        GameUtil.SQUARE_LENGTH +10,
                        null
                );
                break;
            case 1:
                g.drawImage(
                        GameUtil.win,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.Map_W/2)-20,
                        GameUtil.OFFSET*3/2-20,
                        GameUtil.SQUARE_LENGTH*3,
                        GameUtil.SQUARE_LENGTH*3,
                        null
                );
                GameUtil.drawWord(g,"你宁愿玩这破扫雷都不学英语",GameUtil.OFFSET+(GameUtil.Map_W-9)*GameUtil.SQUARE_LENGTH-10,3*GameUtil.OFFSET+GameUtil.Map_H*GameUtil.SQUARE_LENGTH+20,20,Color.RED);
                break;
            case 2:
                g.drawImage(
                        GameUtil.over,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.Map_W/2)-20,
                        GameUtil.OFFSET*3/2-20,
                        GameUtil.SQUARE_LENGTH*3,
                        GameUtil.SQUARE_LENGTH*3,
                        null
                );
                GameUtil.drawWord(g,"回家吧孩子，回家吧。。",GameUtil.OFFSET+(GameUtil.Map_W-8)*GameUtil.SQUARE_LENGTH,3*GameUtil.OFFSET+GameUtil.Map_H*GameUtil.SQUARE_LENGTH+20,20,Color.RED);
                break;

        }
    }
}