package local.happysixplus.backendcodeanalysis.service;

import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.po.UserPo;
import local.happysixplus.backendcodeanalysis.vo.UserVo;
import lombok.var;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @MockBean
    UserData data;

    @Autowired
    UserService service;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addUser() {
        // 执行
        var userVo = new UserVo(null, "tester1", "xxxx");
        service.addUser(userVo);

        // 验证
        var userPo = new UserPo(null, "tester1", "xxxx");
        Mockito.verify(data).save(userPo);
    }

    @Test
    void removeUser() {
        var id = 12L;
        service.removeUser(id);

        Mockito.verify(data).deleteById(id);
    }

    @Test
    void updateUser() {
        // 执行
        var userVo = new UserVo(2L, "tester3", "hello");
        service.updateUser(userVo);

        // 验证
        var userPo = new UserPo(2L, "tester3", "hello");
        Mockito.verify(data).save(userPo);
    }

    @Test
    void getAllUsers() {
        // 打桩
        var up1 = new UserPo(2L, "tester3", "hello");
        var up2 = new UserPo(4L, "tester5", "hellooo");
        var up3 = new UserPo(8L, "zyq", "nb!");
        var ups = Arrays.asList(up3, up2, up1);
        Mockito.when(data.findAll()).thenReturn(ups);

        // 执行
        var res = service.getAllUsers();

        // 验证
        var uv1 = new UserVo(2L, "tester3", null);
        var uv2 = new UserVo(4L, "tester5", null);
        var uv3 = new UserVo(8L, "zyq", null);
        var uvs = Arrays.asList(uv1, uv3, uv2);

        assertEquals(new HashSet<>(uvs), new HashSet<>(res));
    }

    @Test
    void getUser() {
        var up = new UserPo(15L, "tester100", "fsfs");
        Mockito.when(data.findById(15L)).thenReturn(Optional.of(up));

        var res = service.getUser(15L);
        var uv = new UserVo(15L, "tester100", null);
        assertEquals(uv, res);
    }
}