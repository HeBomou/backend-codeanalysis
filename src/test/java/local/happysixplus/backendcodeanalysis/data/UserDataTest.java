package local.happysixplus.backendcodeanalysis.data;

import local.happysixplus.backendcodeanalysis.po.UserPo;

import lombok.var;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserDataTest {

    @Autowired
    UserData data;

    UserPo po;

    @BeforeEach
    void setUp() {
        data.deleteAll();
        po = new UserPo(null, "SKTFaker", "123faker");
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        po = data.save(po);
        UserPo res = data.findById(po.getId()).get();
        assertEquals(po.getId(), res.getId());
        assertEquals(po.getUsername(), res.getUsername());
        assertEquals(po.getPwdMd5(), res.getPwdMd5());
    }

    @Test
    public void testUpdate() {
        po = data.save(po);
        po = new UserPo(po.getId(), "RNGUzi", "sfzjhnfkzgjjuzi");
        po = data.save(po);
        UserPo res = data.findById(po.getId()).get();
        assertEquals(po.getId(), res.getId());
        assertEquals(po.getUsername(), res.getUsername());
        assertEquals(po.getPwdMd5(), res.getPwdMd5());
    }

    @Test
    public void testRemove() {
        po = data.save(po);
        data.deleteById(po.getId());
        var res = data.findById(po.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByUsername() {
        po = data.save(po);
        UserPo res = data.findByUsername(po.getUsername());
        assertEquals(po.getId(), res.getId());
        assertEquals(po.getUsername(), res.getUsername());
        assertEquals(po.getPwdMd5(), res.getPwdMd5());
    }
}