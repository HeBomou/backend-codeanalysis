package local.happysixplus.backendcodeanalysis.service;

import local.happysixplus.backendcodeanalysis.data.AdminUserData;
import local.happysixplus.backendcodeanalysis.po.AdminUserPo;
import local.happysixplus.backendcodeanalysis.vo.AdminUserVo;
import lombok.var;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AdminUserServiceTest {

    @MockBean
    AdminUserData data;

    @Autowired
    AdminUserService service;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addAdmin() throws Exception {
        // 执行
        var adminVo = new AdminUserVo(null, "manager1", "password1", "xxxx-xxxx-xxxx");
        service.addAdmin(adminVo);

        // 验证
        var adminPo = new AdminUserPo(null, "manager1", "password1");
        Mockito.verify(data).save(adminPo);
    }

    @Test
    void removeAdmin() {
        var id = 12L;
        Mockito.when(data.existsById(id)).thenReturn(true);
        service.removeAdmin(id);
        Mockito.verify(data).deleteById(id);
    }

    @Test
    void updateAdmin() {
        // 执行
        var adminVo = new AdminUserVo(2L, "manager2", "password2", "xxxx");
        service.updateAdmin(adminVo);

        // 验证
        var adminPo = new AdminUserPo(2L, "manager2", "password2");
        Mockito.verify(data).save(adminPo);
    }

    @Test
    void getAllAdmin() {
        // 打桩
        var ap1 = new AdminUserPo(2L, "manager3", "password3");
        var ap2 = new AdminUserPo(5L, "manager4", "password4");
        var ap3 = new AdminUserPo(7L, "manager5", "password5");
        var aps = Arrays.asList(ap1, ap2, ap3);
        Mockito.when(data.findAll()).thenReturn(aps);

        // 执行
        var res = service.getAllAdmin();

        // 验证
        var uv1 = new AdminUserVo(2L, "manager3", null, null);
        var uv2 = new AdminUserVo(5L, "manager4", null, null);
        var uv3 = new AdminUserVo(7L, "manager5", null, null);
        var uvs = Arrays.asList(uv2, uv1, uv3);

        assertEquals(new HashSet<>(uvs), new HashSet<>(res));
    }

    @Test
    void getOneAdmin() {
        var aup = new AdminUserPo(15L, "manager10086", "password25");
        Mockito.when(data.findById(15L)).thenReturn(Optional.of(aup));

        var res = service.getOneAdmin(15L);
        var auv = new AdminUserVo(15L, "manager10086", null, null);
        assertEquals(auv, res);
    }
}