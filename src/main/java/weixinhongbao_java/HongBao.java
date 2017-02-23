package weixinhongbao_java;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class HongBao {
    //1.红包金额限制，最小红包额度1分,最大金额为200
    private static final int MINMONEY = 1;
    private static final int MAXMONEY = 200 * 100;
    private static final double TIMES = 2.1;

    //2.判断红包是否合法。主要判断分配红包的金额是否合法+判断剩余红包金额是否合法，
    private boolean isRight(int money, int count) {
        double avg = money / count;
        if (avg < MINMONEY || avg > MAXMONEY) {
            return false;
        } else
            return true;
    }

    //3.随机产生红包。
    private int random(int money, int count, int minS, int maxS) {
        if (count == 1) {
            return money;
        }
        if (minS == maxS) {
            return minS;
        }
        int max = maxS > money ? money : maxS;
        //随机产生红包,Math.rint()方法产生最接近随机数的整数
        int one = ((int) Math.rint(Math.random() * (max - minS) + minS)) % max + 1;
        int money1 = money - one;
        if (isRight(money1, count - 1)) {
            return one;
        } else {
            double avg = money1 / (count - 1);
            if (avg < MINMONEY) {
                //递归调用，修改红包最大金额
                return random(money, count, minS, one);
            } else if (avg > MAXMONEY) {
                return random(money, count, one, maxS);
            }
        }
        return one;
    }
    //3.随机分配红包优化
    private int random2(int money,int minS,int maxS,int count){
        //红包数量为1，直接返回金额。
        if (count == 1){
            return money;
        }
        if (maxS == minS){
            return minS;
        }
        int max = maxS > money ? money : maxS;
        int maxY = money - (count-1)*minS;
        int minY = money - (count-1)*maxS;
        int min = minY > minS ? minY : minS;
        max = maxY < max ? maxY : max;
        return  (int)Math.rint(Math.random()*(max-min)+min);
    }
    //4.红包分配
    private List<Integer> splitRedPackets(int money,int count){
        if (!isRight(money, count)) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        int max = (int) (money * TIMES / count);
        max = max > MAXMONEY ? MAXMONEY : max;
        for (int i = 0; i < count; i++) {
            int one = random(money, MINMONEY, max, count - i);
            list.add(one);
            money -= one;
        }
        return list;
    }
    public static void main( String[] args )
    {
        HongBao hongbao = new HongBao();
        System.out.println(hongbao.splitRedPackets(20,10));
    }
}
