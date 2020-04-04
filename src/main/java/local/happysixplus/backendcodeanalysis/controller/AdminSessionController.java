package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.AdminSessionService;
import local.happysixplus.backendcodeanalysis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/adminSession")
public class AdminSessionController {
    @Autowired
    AdminSessionService service;

    @PostMapping
    public Long postAdmin(@RequestBody AdminSessionVo vo) {
        return service.addSession(vo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAdmin(@RequestParam String id) {
        service.removeSession(id);
    }

}
