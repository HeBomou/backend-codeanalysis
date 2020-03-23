package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.SessionService;
import local.happysixplus.backendcodeanalysis.vo.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/session")
public class SessionController {
    @Autowired
    SessionService service;

    @PostMapping(value = "")
    public void postSession(@RequestBody SessionVo vo, HttpServletRequest request) {
        service.addSession(vo, request);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteSession(@RequestParam String id,HttpServletRequest request) {
        service.removeSession(id,request);
    }

}
