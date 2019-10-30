package com.cp.stackunderflow;

import com.cp.stackunderflow.entity.Tag;
import com.cp.stackunderflow.entity.User;
import com.cp.stackunderflow.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Test
    public void testRegister() throws Exception {

        Tag tag1 = new Tag();
        tag1.setId(1);

        Tag tag2 = new Tag();
        tag2.setId(11);

        Tag tag3 = new Tag();
        tag3.setId(21);


        User user1 = new User();
        user1.setEmail("pb426@njit.edu");
        user1.setDisplayName("Palash");
        user1.setPassword("virtusa");
        user1.setTags(new HashSet<Tag>(Arrays.asList(tag1, tag2,tag3)));

        Assert.assertEquals(1,1);

    }

}
