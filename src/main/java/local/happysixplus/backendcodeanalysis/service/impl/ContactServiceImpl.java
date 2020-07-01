package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.ContactData;
import local.happysixplus.backendcodeanalysis.po.ContactPo;
import local.happysixplus.backendcodeanalysis.service.ContactService;
import local.happysixplus.backendcodeanalysis.service.UserService;
import local.happysixplus.backendcodeanalysis.vo.UserVo;
import lombok.var;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    UserService userService;

    @Autowired
    ContactData data;

    @Override
    public List<UserVo> getContactsByUserId(Long userId) {
        var contacts = data.findByUserId(userId);
        return contacts.stream().map(c -> userService.getUser(c.getContactUserId())).collect(Collectors.toList());
    }

    @Override
    public boolean existContact(Long userId, Long contactUserId) {
        return data.existsByUserIdAndContactUserId(userId, contactUserId);
    }

    @Override
    public void addContact(Long userId, Long contactUserId) {
        var contact = new ContactPo(null, userId, contactUserId);
        data.save(contact);
    }

    @Override
    public void removeContact(Long userId, Long contactUserId) {
        data.deleteByUserIdAndContactUserId(userId, contactUserId);
    }

}