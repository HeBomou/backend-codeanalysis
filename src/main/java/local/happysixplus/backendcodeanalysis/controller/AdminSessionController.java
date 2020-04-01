package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.AdminSessionService;
import local.happysixplus.backendcodeanalysis.vo.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/adminSession")
public class AdminSessionController {
    @Autowired
    AdminSessionService service;

    @PostMapping
    public Long postAdmin(@RequestBody AdminSessionVo vo, HttpServletRequest request) {
        return service.addSession(vo, request);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAdmin(@RequestParam String id, HttpServletRequest request) {
        service.removeSession(id, request);
    }

}
