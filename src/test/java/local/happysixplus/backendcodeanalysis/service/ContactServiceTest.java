package local.happysixplus.backendcodeanalysis.service;

import local.happysixplus.backendcodeanalysis.data.ContactData;
import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.po.ContactPo;
import local.happysixplus.backendcodeanalysis.vo.ContactVo;
import local.happysixplus.backendcodeanalysis.po.UserPo;
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
class ContactServiceTest {

    @MockBean
    ContactData data;

    @MockBean
    UserData userData;


    @Autowired
    ContactService service;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getContactByUserId(){
        var vo1=new ContactVo(7L,4L,"SKTFaker",0);
        var vo2=new ContactVo(8L,5L,"SKTotto",1);
        var vo3=new ContactVo(9L,6L,"SKTEasyhoon",2);
        var vos=Arrays.asList(vo1,vo2,vo3);

        var po1=new ContactPo(7L,3L,4L,0);
        var po2=new ContactPo(8L,3L,5L,1);
        var po3=new ContactPo(9L,3L,6L,2);
        var pos=Arrays.asList(po1,po2,po3);

        Mockito.when(data.findByUserId(3L)).thenReturn(pos);
        Mockito.when(userData.findById(4L)).thenReturn(Optional.of(new UserPo(4L,"SKTFaker","1234")));
        Mockito.when(userData.findById(5L)).thenReturn(Optional.of(new UserPo(5L,"SKTotto","12346")));
        Mockito.when(userData.findById(6L)).thenReturn(Optional.of(new UserPo(6L,"SKTEasyhoon","12343")));
        var res=service.getContactsByUserId(3L);
        assertEquals(new HashSet<>(vos),new HashSet<>(res));
    }

    @Test
    void getContact(){
        var vo=new ContactVo(7L,4L,"SKTFaker",0);
        var po=new ContactPo(7L,3L,4L,0);
        Mockito.when(data.findByUserIdAndContactUserId(3L, 4L)).thenReturn(po);
        Mockito.when(userData.findById(4L)).thenReturn(Optional.of(new UserPo(4L,"SKTFaker","123123132")));
        var res=service.getContact(3L, 4L);
        assertEquals(res,vo);
    }

    @Test
    void updateContactIsRead(){
        var po=new ContactPo(7L,3L,4L,0);
        Mockito.when(data.findByUserIdAndContactUserId(3L, 4L)).thenReturn(po);
        service.updateContactRead(3L, 4L, 2);
        var po1=new ContactPo(7L,3L,4L,2);
        Mockito.verify(data).save(po1);
    }

    @Test
    void existsContact(){
        Mockito.when(data.existsByUserIdAndContactUserId(3L,4L)).thenReturn(true);
        var res=service.existContact(3L, 4L);
        assertEquals(res,true);
        Mockito.verify(data).existsByUserIdAndContactUserId(3L,4L);
    }

    @Test
    void addContact(){
        var po=new ContactPo(null,2L,3L,0);
        service.addContact(2L,3L,0);
        Mockito.verify(data).save(po);
    }

    @Test
    void removeContact(){
        service.removeContact(99L,999L);
        Mockito.verify(data).deleteByUserIdAndContactUserId(99L, 999L);
    }
}