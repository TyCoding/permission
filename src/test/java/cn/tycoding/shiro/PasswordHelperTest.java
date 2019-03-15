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
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        helper.encryptPassword(user);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getSalt());
        User user2 = new User();
        user2.setUsername("tycoding");
        user2.setPassword("123456");
        helper.encryptPassword(user2);
        System.out.println(user2.getUsername());
        System.out.println(user2.getPassword());
        System.out.println(user2.getSalt());
        User user3 = new User();
        user3.setUsername("tumo");
        user3.setPassword("123456");
        helper.encryptPassword(user3);
        System.out.println(user3.getUsername());
        System.out.println(user3.getPassword());
        System.out.println(user3.getSalt());
    }
}
