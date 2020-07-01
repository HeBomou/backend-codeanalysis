package local.happysixplus.backendcodeanalysis.service;

import java.util.List;

import local.happysixplus.backendcodeanalysis.vo.UserVo;

public interface ContactService {

    List<UserVo> getContactsByUserId(Long userId);

    boolean existContact(Long userId, Long contactUserId);

    void addContact(Long userId, Long contactUserId);

    void removeContact(Long userId, Long contactUserId);

}