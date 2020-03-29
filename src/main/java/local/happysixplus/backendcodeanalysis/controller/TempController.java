package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.callgraph.ProjectInfo;
import local.happysixplus.backendcodeanalysis.callgraph.stat.JCallGraph;
import local.happysixplus.backendcodeanalysis.service.SessionService;
import local.happysixplus.backendcodeanalysis.vo.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/faker")
public class TempController {
    @Autowired
    SessionService service;

    @GetMapping
    public String[] getP(){
        return JCallGraph.getGraphFromJar("/Users/tianduyingcai/Desktop/GIT/SE3/backend-codeanalysis/temp/Linux/target/Hello-1.0-SNAPSHOT.jar","Linux");
    }

}
