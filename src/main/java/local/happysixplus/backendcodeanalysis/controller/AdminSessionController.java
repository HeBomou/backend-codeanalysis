package local.happysixplus.backendcodeanalysis.controller;


import local.happysixplus.backendcodeanalysis.service.AdminSessionService;
import local.happysixplus.backendcodeanalysis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/adminSession")
public class AdminSessionController {
    @Autowired
    AdminSessionService service;

    @PostMapping(value="")
    public void postAdmin(@RequestBody AdminSessionVo vo){
        service.addSession(vo);
    }

    @DeleteMapping(value="/{id}")
    public void deleteAdmin(@RequestParam String id){
        service.removeSession();
    }

}
