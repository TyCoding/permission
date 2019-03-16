package cn.tycoding.shiro;

import cn.tycoding.common.utils.PasswordHelper;
import cn.tycoding.system.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tycoding
 * @date 2019-03-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordHelperTest {

    @Autowired
    private PasswordHelper helper;

    /**
     * 用于获取加密后的密码
     */
    @Test
    public void testPassword() {
        System.out.println("--------管理员-------");
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        helper.encryptPassword(user);
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("salt: " + user.getSalt());
        System.out.println("-------测试账号------");
        User user2 = new User();
        user2.setUsername("tycoding");
        user2.setPassword("123456");
        helper.encryptPassword(user2);
        System.out.println("username: " + user2.getUsername());
        System.out.println("password: " + user2.getPassword());
        System.out.println("salt: " + user2.getSalt());
        System.out.println("-------用户管理员------");
        User user3 = new User();
        user3.setUsername("tumo");
        user3.setPassword("123456");
        helper.encryptPassword(user3);
        System.out.println("username: " + user3.getUsername());
        System.out.println("password: " + user3.getPassword());
        System.out.println("salt: " + user3.getSalt());
        System.out.println("-------系统监控员------");
        User user4 = new User();
        user4.setUsername("monitor");
        user4.setPassword("123456");
        helper.encryptPassword(user4);
        System.out.println("username: " + user4.getUsername());
        System.out.println("password: " + user4.getPassword());
        System.out.println("salt: " + user4.getSalt());
        System.out.println("-------天气预报员------");
        User user5 = new User();
        user5.setUsername("synoptic");
        user5.setPassword("123456");
        helper.encryptPassword(user5);
        System.out.println("username: " + user5.getUsername());
        System.out.println("password: " + user5.getPassword());
        System.out.println("salt: " + user5.getSalt());
        System.out.println("-------用户查看------");
        User user6 = new User();
        user6.setUsername("user");
        user6.setPassword("123456");
        helper.encryptPassword(user6);
        System.out.println("username: " + user6.getUsername());
        System.out.println("password: " + user6.getPassword());
        System.out.println("salt: " + user6.getSalt());
    }
}
