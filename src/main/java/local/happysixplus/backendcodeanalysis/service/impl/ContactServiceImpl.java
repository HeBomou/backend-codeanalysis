package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.ContactData;
import local.happysixplus.backendcodeanalysis.po.ContactPo;
import local.happysixplus.backendcodeanalysis.service.ContactService;
import local.happysixplus.backendcodeanalysis.service.UserService;
import local.happysixplus.backendcodeanalysis.vo.ContactVo;
import lombok.var;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    UserService userService;

    @Autowired
    ContactData data;

    @Override
    public List<ContactVo> getContactsByUserId(Long userId) {
        var contacts = data.findByUserId(userId);
        var res = new ArrayList<ContactVo>();
        for (ContactPo c : contacts) {
            var user = userService.getUser(c.getContactUserId());
            res.add(new ContactVo(c.getId(), user.getId(), user.getUsername(), c.getIsRead()));
        }
        return res;
    }

    @Override
    public ContactVo getContact(Long userId, Long contactUserId) {
        var contact = data.findByUserIdAndContactUserId(userId, contactUserId);
        var user = userService.getUser(contactUserId);
        return new ContactVo(contact.getId(), user.getId(), user.getUsername(), contact.getIsRead());
    }

    @Override
    public void updateContactRead(Long userId, Long contactUserId, Integer read) {
        var po = data.findByUserIdAndContactUserId(userId, contactUserId);
        po.setIsRead(read);
        data.save(po);
    }

    @Override
    public boolean existContact(Long userId, Long contactUserId) {
        return data.existsByUserIdAndContactUserId(userId, contactUserId);
    }

    @Override
    public void addContact(Long userId, Long contactUserId, Integer read) {
        var contact = new ContactPo(null, userId, contactUserId, read);
        data.save(contact);
    }

    @Override
    public void removeContact(Long userId, Long contactUserId) {
        data.deleteByUserIdAndContactUserId(userId, contactUserId);
    }

}