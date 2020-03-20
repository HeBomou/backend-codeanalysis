package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.vo.AdminUserVo;
import org.springframework.web.bind.annotation.RestController;

import local.happysixplus.backendcodeanalysis.service.AdminUserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(value = "/admin")
public class AdminUserController {
    @Autowired
    AdminUserService service;

    @PostMapping
    public void postAdmin(@RequestBody AdminUserVo vo) {
        service.addAdmin(vo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.removeAdmin(id);
    }

    @PutMapping(value = "/{id}")
    public void putUser(@PathVariable Long id, @RequestBody AdminUserVo vo) {
        service.updateAdmin(vo);
    }

    @GetMapping
    public List<AdminUserVo> getAllUsers() {
        return service.getAllAdmin();
    }
}