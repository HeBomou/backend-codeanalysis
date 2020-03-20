package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.service.UserService;
import local.happysixplus.backendcodeanalysis.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void addUser(UserVo vo) {
        System.out.println("Add user");
    }

    @Override
    public void removeUser(Long id) {
        System.out.println("Remove user");
    }

    @Override
    public void updateUser(UserVo vo) {
        System.out.println("Update user");
    }

    @Override
    public List<UserVo> getAllUsers() {
        System.out.println("Get all user");
        return new ArrayList<>();
    }

    @Override
    public UserVo getOneUser(Long id) {
        System.out.println("Get one user");
        return new UserVo();
    }
}