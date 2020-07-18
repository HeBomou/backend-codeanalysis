package local.happysixplus.backendcodeanalysis.controller;

import local.happysixplus.backendcodeanalysis.service.ContactService;
import local.happysixplus.backendcodeanalysis.vo.ContactVo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping(value="/{userId}/new")
    public Boolean getContactNew(@PathVariable Long userId) {
        return service.getContactNew(userId);
    }
    
    @GetMapping(value="/{userId}/{contactUserId}")
    public ContactVo getContact(@PathVariable Long userId,@PathVariable Long contactUserId){
        return service.getContact(userId, contactUserId);
    }

    @PutMapping(value="/{userId}/{contactUserId}")
    public void putContactRead(@PathVariable Long userId,@PathVariable Long contactUserId, @RequestParam int read){
        service.updateContactRead(userId, contactUserId, read);
    }

    @GetMapping(value="/exist/{userId}/{contactUserId}")
    public Boolean getExistsContact(@PathVariable Long userId,@PathVariable Long contactUserId) {
        return service.existContact(userId, contactUserId);
    }

    @PostMapping(value="/{userId}/{contactUserId}")
    public void addContact(@PathVariable Long userId,@PathVariable Long contactUserId,@RequestParam int read){
        service.addContact(userId, contactUserId, read);
    }

    @DeleteMapping(value="/{userId}/{contactUserId}")
    public void removceContact(@PathVariable Long userId,@PathVariable Long contactUserId){
        service.removeContact(userId, contactUserId);
    }
    

}