package com.dp.vvgram;

import com.dp.vvgram.dtos.UserDto;
import com.dp.vvgram.models.User;
import com.dp.vvgram.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class VvGramApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void getAllUsers() {
        List<User> users = userService.getUsers("devvi");
        List<UserDto> userDtos = new ArrayList<>();
        if (!users.isEmpty()) {
            for (User user : users) {
                userDtos.add(UserDto.from(user));
            }
        }

        System.out.println(userDtos);
    }

}
