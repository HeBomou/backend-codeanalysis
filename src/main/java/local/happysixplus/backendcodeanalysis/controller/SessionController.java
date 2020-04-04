package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.SessionService;
import local.happysixplus.backendcodeanalysis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/session")
public class SessionController {
    @Autowired
    SessionService service;

    @PostMapping
    public Long postSession(@RequestBody SessionVo vo) throws Exception {
        return service.addSession(vo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteSession(@RequestParam String id) throws Exception {
        service.removeSession(id);
    }

}
