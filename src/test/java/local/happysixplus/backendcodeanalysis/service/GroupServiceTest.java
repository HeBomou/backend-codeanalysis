package local.happysixplus.backendcodeanalysis.service;

import local.happysixplus.backendcodeanalysis.data.GroupData;
import local.happysixplus.backendcodeanalysis.data.GroupUserRelData;
import local.happysixplus.backendcodeanalysis.data.GroupTaskData;
import local.happysixplus.backendcodeanalysis.data.GroupNoticeData;
import local.happysixplus.backendcodeanalysis.data.TaskUserRelData;
import local.happysixplus.backendcodeanalysis.data.UserData;

import local.happysixplus.backendcodeanalysis.vo.GroupVo;
import local.happysixplus.backendcodeanalysis.vo.GroupMemberVo;
import local.happysixplus.backendcodeanalysis.vo.GroupNoticeVo;

import local.happysixplus.backendcodeanalysis.po.GroupPo;
import local.happysixplus.backendcodeanalysis.po.GroupUserRelPo;
import local.happysixplus.backendcodeanalysis.po.UserPo;
import local.happysixplus.backendcodeanalysis.po.GroupNoticePo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import lombok.var;

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
class GroupServiceTest {

    @MockBean
    GroupData data;

    @MockBean
    GroupUserRelData relData;

    @MockBean
    GroupNoticeData noticeData;

    @MockBean
    GroupTaskData taskData;
    
    @MockBean
    TaskUserRelData taskRelData;

    @MockBean
    UserData userData;

    @Autowired
    GroupService service;


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void removeGroup() {
        var id = 12L;
        Mockito.when(data.existsById(id)).thenReturn(true);
        Mockito.when(taskData.existsById(id)).thenReturn(true);
        Mockito.when(noticeData.existsById(id)).thenReturn(true);
        Mockito.when(taskRelData.existsById(id)).thenReturn(true);
        Mockito.when(relData.existsById(id)).thenReturn(true);
        service.removeGroup(id);
        Mockito.verify(data).deleteById(id);
    }

    @Test
    void updateGroup() {
        // 执行
        var userVo = new GroupVo(2L, 3L,"tester3", "hello");
        service.updateGroup(userVo);

        // 验证
        var userPo = new GroupPo(2L,3L, "tester3", "hello");
        Mockito.verify(data).save(userPo);
    }

    @Test
    void getAllGroup() {
        // 打桩
        var up1 = new GroupPo(2L,4396L, "tester3", "hello");
        var up2 = new GroupPo(4L,4396L, "tester5", "hellooo");
        var up3 = new GroupPo(8L,4396L,"zyq", "nb!");

        var rel1= new GroupUserRelPo(1L,2L,1234L,"1");
        var rel2= new GroupUserRelPo(2L,4L,1234L,"2");
        var rel3= new GroupUserRelPo(3L,8L,1234L,"3");
        var ups = Arrays.asList(up1, up2, up3);
        var ids = Arrays.asList(2L,4L,8L);
        var rels=Arrays.asList(rel1,rel2,rel3);
        Mockito.when(relData.findByUserId(1234L)).thenReturn(rels);
        Mockito.when(data.findByIdIn(ids)).thenReturn(ups);

        // 执行
        var res = service.getAllGroup(1234L);

        // 验证
        var uv1 = new GroupVo(2L, 4396L,"tester3", "hello");
        var uv2 = new GroupVo(4L, 4396L,"tester5", "hellooo");
        var uv3 = new GroupVo(8L, 4396L,"zyq", "nb!");
        var uvs = Arrays.asList(uv1, uv3, uv2);

        assertEquals(new HashSet<>(uvs), new HashSet<>(res));
    }

    @Test
    void testAddMember(){
        var gp=new GroupPo(3L,4L,"HSP","123456");
        Mockito.when(data.findById(3L)).thenReturn(Optional.of(gp));
        service.addMember(3L, "123456",9L);
        Mockito.verify(relData).save(new GroupUserRelPo(null,3L,9L,"member"));
    }

    @Test
    void testRemoveMember(){
        service.removeMember(3L, 4L);
        Mockito.verify(relData).deleteByGroupIdAndUserId(3L, 4L);
        Mockito.verify(taskRelData).deleteByUserIdAndGroupId(4L,3L);
    }

    @Test
    void testUpdateMember(){
        var grp=new GroupUserRelPo(1L,2L,3L,"faker");
        Mockito.when(relData.findByGroupIdAndUserId(2L,3L)).thenReturn(grp);
        service.updateMember(2L, 3L, "faker");
        Mockito.verify(relData).save(grp);
    }

    @Test
    void testGetMembers(){
        var gm1=new GroupMemberVo(1L,"hbm","mbh");
        var gm2=new GroupMemberVo(2L,"hdd","ddh");
        var gm3=new GroupMemberVo(3L,"zyq","qyz");
        var gms=Arrays.asList(gm1,gm2,gm3);
        
        var gr1=new GroupUserRelPo(1L,10L,1L,"mbh");
        var gr2=new GroupUserRelPo(2L,10L,2L,"ddh");
        var gr3=new GroupUserRelPo(3L,10L,3L,"qyz");
        var grs=Arrays.asList(gr1,gr2,gr3);

        var u1=new UserPo(1L,"hbm","sss");
        var u2=new UserPo(2L,"hdd","ssss");
        var u3=new UserPo(3L,"zyq","sssss");
        var usrs=Arrays.asList(u1,u2,u3);

        var ids=Arrays.asList(1L,2L,3L);
        Mockito.when(relData.findByGroupId(10L)).thenReturn(grs);
        Mockito.when(userData.findByIdIn(ids)).thenReturn(usrs);
        assertEquals(new HashSet<>(gms),new HashSet<>(service.getMembers(10L)));
    }

    @Test
    void testRemoveNotice(){
        service.removeNotice(3L);
        Mockito.verify(noticeData).deleteById(3L);
    }

    @Test
    void testUpdateNotice(){
        var nv=new GroupNoticeVo(1L,3L,"title","Contnet","sada");
        var vp=new GroupNoticePo(1L,3L,"title","Contnet","sada");
        var vp1=new GroupNoticePo(1L,3L,"tie","Cet","sada");
        Mockito.when(noticeData.findById(1L)).thenReturn(Optional.of(vp1));
        service.updateNotice(nv);
        Mockito.verify(noticeData).save(vp);
    }

    @Test
    void testGetNotice(){
        var nv1=new GroupNoticeVo(1L,3L,"title","Contnet","sada");
        var nv2=new GroupNoticeVo(2L,3L,"title1","Co1ntnet","sa1da");
        var nv3=new GroupNoticeVo(3L,3L,"213","sad","sa");
        var nvs=Arrays.asList(nv1,nv2,nv3);

        var np1=new GroupNoticePo(1L,3L,"title","Contnet","sada");
        var np2=new GroupNoticePo(2L,3L,"title1","Co1ntnet","sa1da");
        var np3=new GroupNoticePo(3L,3L,"213","sad","sa");
        var nps=Arrays.asList(np1,np2,np3);

        Mockito.when(noticeData.findAllByGroupId(3L)).thenReturn(nps);
        assertEquals(new HashSet<>(nvs),new HashSet<>(service.getNotice(3L)));
    }

}