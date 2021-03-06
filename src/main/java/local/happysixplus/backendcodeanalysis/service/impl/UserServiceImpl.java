package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.UserData;
import local.happysixplus.backendcodeanalysis.exception.MyRuntimeException;
import local.happysixplus.backendcodeanalysis.po.UserPo;
import local.happysixplus.backendcodeanalysis.service.UserService;
import local.happysixplus.backendcodeanalysis.vo.UserVo;
import lombok.var;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserData userData;

    @Override
    public void addUser(UserVo vo) throws Exception {
        if (userData.findByUsername(vo.getUsername()) != null)
            throw new MyRuntimeException("该用户名已被注册");
        userData.save(new UserPo(null, vo.getUsername(), vo.getPwdMd5()));
    }

    @Override
    public void removeUser(Long id) {
        if (userData.existsById(id))
            userData.deleteById(id);
        else
            throw new MyRuntimeException("该用户不存在");
    }

    @Override
    public void updateUser(UserVo vo) {
        userData.save(new UserPo(vo.getId(), vo.getUsername(), vo.getPwdMd5()));
    }

    @Override
    public List<UserVo> getAllUsers() {
        var pos = userData.findAll();
        var vos = new ArrayList<UserVo>(pos.size());
        for (var po : pos)
            vos.add(new UserVo(po.getId(), po.getUsername(), null));
        return vos;
    }

    @Override
    public UserVo getUser(Long id) {
        var po = userData.findById(id).orElse(null);
        if (po == null)
            throw new MyRuntimeException("该用户不存在");
        return new UserVo(po.getId(), po.getUsername(), null);
    }

    @Override
    public boolean existUser(Long id) {
        return userData.existsById(id);
    }
}