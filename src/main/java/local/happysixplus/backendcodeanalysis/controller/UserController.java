package local.happysixplus.backendcodeanalysis.controller;

import org.springframework.web.bind.annotation.RestController;

import local.happysixplus.backendcodeanalysis.service.UserService;
import local.happysixplus.backendcodeanalysis.vo.UserVo;

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
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping
    public void postUser(@RequestBody UserVo vo) throws Exception {
        service.addUser(vo);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.removeUser(id);
    }

    @PutMapping(value = "/{id}")
    public void putUser(@PathVariable Long id, @RequestBody UserVo vo) {
        vo.setId(id);
        service.updateUser(vo);
    }

    @GetMapping
    public List<UserVo> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public UserVo getMethodName(@PathVariable Long id) {
        return service.getUser(id);
    }

}