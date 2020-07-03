package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.ContactVo;

public interface ContactService {

    List<ContactVo> getContactsByUserId(Long userId);

    ContactVo getContact(Long userId, Long contactUserId);

    void updateContactRead(Long userId, Long contactUserId, Integer read);

    boolean existContact(Long userId, Long contactUserId);

    void addContact(Long userId, Long contactUserId, Integer read);

    void removeContact(Long userId, Long contactUserId);

}