package local.happysixplus.backendcodeanalysis.service;

import local.happysixplus.backendcodeanalysis.service.impl.ProjectServiceImpl;
import local.happysixplus.backendcodeanalysis.vo.ProjectAllVo;
import local.happysixplus.backendcodeanalysis.vo.ProjectDynamicVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectServiceTest {
    @Autowired
    ProjectService service;

    @Test
    public void testProjectService(){
        ProjectAllVo vo=service.addProject("Linux","https://gitee.com/forsakenspirit/Linux",1);
        System.out.println('s');


    }


}