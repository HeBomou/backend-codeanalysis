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

    @PostMapping
    public Long postSession(@RequestBody SessionVo vo, HttpServletRequest request) throws Exception {
        return service.addSession(vo, request);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteSession(@RequestParam String id, HttpServletRequest request) throws Exception {
        service.removeSession(id, request);
    }

}
