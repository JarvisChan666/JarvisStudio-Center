package com.jarvis.usercenterbackend.service;
import java.util.Date;

import com.jarvis.usercenterbackend.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



/**
 * @Description: 用户服务测试
 *
 * @author JarvisChan
 */

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(0L);
        user.setUsername("jarvis2");
        user.setUserAccount("123");
        user.setAvatarUrl("https://www.bing.com/images/search?view=detailV2&ccid=TG3xVixj&id=6BBEEC7001701DE27D44D0D8D73390DBADFDCA18");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setEmail("123");
        user.setUserStatus(0);
        user.setPhone("456");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);


        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

  @Test
  void userRegister() {
        String userAccount= "rose";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount= "jarv";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "jarvis";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "jar vis";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "rose3";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result > 0);

  }
}