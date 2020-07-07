package local.happysixplus.backendcodeanalysis.data;

import lombok.var;
import local.happysixplus.backendcodeanalysis.po.ContactPo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ContactDataTest {

    @Autowired
    ContactData data;

    ContactPo contactPo = new ContactPo();

    @BeforeEach
    void init() {
        data.deleteAll();
        contactPo = new ContactPo(1234L, 1L, 2L,0);
    }

    @AfterEach
    void tearDown() {
        data.deleteAll();
    }

    @Test
    public void testInsert() {
        contactPo = data.save(contactPo);
        ContactPo resPo = data.findById(contactPo.getId()).get();
        assertEquals(resPo.getId(), contactPo.getId());
        assertEquals(resPo.getUserId(), contactPo.getUserId());
        assertEquals(resPo.getContactUserId(), contactPo.getContactUserId());
        assertEquals(resPo.getIsRead(),contactPo.getIsRead());
    }

    @Test
    public void testUpdate() {
        contactPo = data.save(contactPo);
        contactPo = new ContactPo(contactPo.getId(), 4396L,
                4397L,1);
        contactPo = data.save(contactPo);
        ContactPo resPo = data.findById(contactPo.getId()).get();
        assertEquals(resPo.getId(), contactPo.getId());
        assertEquals(resPo.getUserId(), contactPo.getUserId());
        assertEquals(resPo.getContactUserId(), contactPo.getContactUserId());
        assertEquals(resPo.getIsRead(),contactPo.getIsRead());
    }

    @Test
    public void testRemove() {
        contactPo = data.save(contactPo);
        data.deleteById(contactPo.getId());
        var res = data.findById(contactPo.getId()).orElse(null);
        assertEquals(res, null);
    }

    @Test
    public void testFindByUserId() {
        contactPo = data.save(contactPo);
        ContactPo contactPo1 = data
                .save(new ContactPo(12345L, 1L, 2L,0));
        ContactPo contactPo2 = data
                .save(new ContactPo(12346L, 1L, 3L,1));
        List<ContactPo> list = data.findByUserId(1L);
        list.sort((a, b) -> (int) (a.getId() - b.getId()));
        assertEquals(list.get(0).getId(), contactPo.getId());
        assertEquals(list.get(0).getUserId(), contactPo.getUserId());
        assertEquals(list.get(0).getContactUserId(), contactPo.getContactUserId());
        assertEquals(list.get(0).getIsRead(),contactPo.getIsRead());

        assertEquals(list.get(1).getId(), contactPo1.getId());
        assertEquals(list.get(1).getUserId(), contactPo1.getUserId());
        assertEquals(list.get(1).getContactUserId(), contactPo1.getContactUserId());
        assertEquals(list.get(1).getIsRead(),contactPo1.getIsRead());

        assertEquals(list.get(2).getId(), contactPo2.getId());
        assertEquals(list.get(2).getUserId(), contactPo2.getUserId());
        assertEquals(list.get(2).getContactUserId(), contactPo2.getContactUserId());
        assertEquals(list.get(2).getIsRead(),contactPo2.getIsRead());
    }

    @Test
    public void testFindByUserIdAndContactUserId() {
        contactPo = data.save(contactPo);
        // ContactPo contactPo1 = 
        data
                .save(new ContactPo(12345L, 1L, 2L,0));
        ContactPo contactPo2 = data
                .save(new ContactPo(12346L, 1L, 3L,1));
        ContactPo resPo= data.findByUserIdAndContactUserId(1L,3L);
        assertEquals(resPo.getId(), contactPo2.getId());
        assertEquals(resPo.getUserId(), contactPo2.getUserId());
        assertEquals(resPo.getContactUserId(), contactPo2.getContactUserId());
        assertEquals(resPo.getIsRead(),contactPo2.getIsRead());
    }

    @Test
    public void testExistsByUserIdAndContextUserId() {
        
            contactPo = data.save(contactPo);
            // ContactPo contactPo1 = 
            data.save(new ContactPo(12345L, 1L, 2L,0));
            // ContactPo contactPo2 = 
            data.save(new ContactPo(12346L, 1L, 3L,1));
            boolean res1= data.existsByUserIdAndContactUserId(1L,4L);
            boolean res2=data.existsByUserIdAndContactUserId(1L,3L);
            assertEquals(res1,false);
            assertEquals(res2,true);
    }

    @Test
    public void testDeleteByUserIdAndContactUserId() {
        contactPo = data.save(contactPo);
        // ContactPo contactPo1 = 
        data.save(new ContactPo(12345L, 1L, 2L,0));
        ContactPo contactPo2 = 
        data.save(new ContactPo(12346L, 1L, 3L,1));
        ContactPo resPo= data.findByUserIdAndContactUserId(1L,3L);
        assertEquals(resPo.getId(), contactPo2.getId());
        assertEquals(resPo.getUserId(), contactPo2.getUserId());
        assertEquals(resPo.getContactUserId(), contactPo2.getContactUserId());
        assertEquals(resPo.getIsRead(),contactPo2.getIsRead());
        data.deleteByUserIdAndContactUserId(1L, 3L);
        assertEquals(data.existsByUserIdAndContactUserId(1L, 3L),false);
    }
}
