package cn.tycoding.common.utils.vcode;

import java.util.Random;

public class Randoms {

    Randoms() {

    }

    private static final Random RANDOM = new Random();
    // 定义验证码字符.去除了 O 和 I 等容易混淆的字母
    private static final char[] ALPHA = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'G', 'K', 'M', 'N', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 产生两个数之间的随机数
     *
     * @param min 小数
     * @param max 比 min大的数
     * @return int 随机数字
     */
    private static int num(int min, int max) {
        return min + RANDOM.nextInt(max - min);
    }

    /**
     * 产生 0--num 的随机数,不包括num
     *
     * @param num 数字
     * @return int 随机数字
     */
    static int num(int num) {
        return RANDOM.nextInt(num);
    }

    public static char alpha() {
        return ALPHA[num(0, ALPHA.length)];
    }
}