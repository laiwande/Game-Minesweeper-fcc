package com.sxt;

/**
 * 底层数字类
 */

public class BottomNum {
    void newNum(){
        for (int i = 1; i <= GameUtil.Map_W; i++) {
            for (int j = 1; j <= GameUtil.Map_H; j++) {
                if(GameUtil.DATA_BOTTOM[i][j]==-1){
                    for (int k = i-1; k <= i+1; k++) {
                        for (int l = j-1; l <= j+1; l++) {
                            if(GameUtil.DATA_BOTTOM[k][l]>=0){
                                GameUtil.DATA_BOTTOM[k][l]++;
                            }
                        }
                    }
                }
            }
        }
    }
}
