package com.sxt;

/***
 * 初始化地雷
 */

public class BottomRay {
    //存放雷坐标，用相邻两个数去存放雷坐标，所以*2
    static int[] rays = new int[GameUtil.RAY_MAX*2];

    //地雷坐标
    int x,y;
    //是否放置
    boolean isPlace= true;

    //写法1
    void newRay(){
        for (int i = 0; i < GameUtil.RAY_MAX*2; i+=2) {
            x = (int) (Math.random()*GameUtil.Map_W+1); //[1-12),强转成int后[1,11]，保护壳操作
            y = (int) (Math.random()*GameUtil.Map_H+1);

            for (int j = 0; j < i; j+=2) {
                if(x==rays[j] && y==rays[j+1]){
                    i= i-2;
                    isPlace = false;
                    break;
                }
            }
            if(isPlace){
                rays[i] = x;
                rays[i+1] = y;
            }
            isPlace = true;
        }

        //放到二维数组中去
        for (int i = 0; i < GameUtil.RAY_MAX*2; i+=2) {
            GameUtil.DATA_BOTTOM[rays[i]][rays[i + 1]] = -1;
        }
    }

    //写法1：使用 rays[] 存储所有坐标，便于后续使用，主要为了存x,y
    //写法2：只使用一次就丢弃，不可复用

    //写法2
//    {
//        for (int i = 0; i < GameUtil.RAY_MAX; i++) {
//            x = (int) (Math.random()*GameUtil.Map_W+1); //[1-12),强转成int后[1,11]，保护壳操作
//            y = (int) (Math.random()*GameUtil.Map_H+1);
//            GameUtil.DATA_BOTTOM[x][y] = -1;
//        }
//    }

}
