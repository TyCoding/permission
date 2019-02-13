package cn.tycoding.shiro;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tycoding
 * @date 2019-02-05
 */
public class PathTest {

    public static void main(String[] args) {
        System.out.println(PathTest.class.getResource("/"));

        List list = new ArrayList();
        list.add("ss");
        System.out.println(check(list));
    }

    private static boolean check(List list) {
        if (list.size() > 0) {
            System.out.println("---true");
            return false;
        }
        System.out.println("---false");
        return true;
    }

}
