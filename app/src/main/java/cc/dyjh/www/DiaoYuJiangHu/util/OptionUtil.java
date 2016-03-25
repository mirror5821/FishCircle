package cc.dyjh.www.DiaoYuJiangHu.util;

import java.util.List;

import cc.dyjh.www.DiaoYuJiangHu.bean.YuChang;

/**
 * Created by 王沛栋 on 2016/3/25.
 */
public class OptionUtil {

    /**
     * 类型选择方法 因为公用Yu这个实体类
     * @param yu
     * @param ids
     * @return
     */
    public static String  getYu(List<YuChang.Yu> yu,String ids){
        StringBuilder sb = new StringBuilder();
        String [] str = ids.split(" ");
        for(String s:str){
            for(YuChang.Yu y:yu){
                if(y.getId() == Integer.valueOf(s)){
                    sb.append(y.getName()+" ");
                }
            }

        }
        return sb.toString();
    }
}
