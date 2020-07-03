package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.ContactService;
import local.happysixplus.backendcodeanalysis.vo.ContactVo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/contact")
public class ContactController {
    @Autowired
    ContactService service;

    // 获取所有联系人
    @GetMapping(value = "/{userId}")
    public List<ContactVo> getContacts(@PathVariable Long userId) {
        return service.getContactsByUserId(userId);
    }

}