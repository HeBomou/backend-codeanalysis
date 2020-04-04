package local.happysixplus.backendcodeanalysis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import local.happysixplus.backendcodeanalysis.data.AdminUserData;
import local.happysixplus.backendcodeanalysis.exception.MyRuntimeException;
import local.happysixplus.backendcodeanalysis.service.AdminSessionService;
import local.happysixplus.backendcodeanalysis.vo.AdminSessionVo;
import lombok.var;

@Service
public class AdminSessionServiceImpl implements AdminSessionService {

    @Autowired
    AdminUserData adminUserData;

    @Override
    public Long addSession(AdminSessionVo vo) {
        var po = adminUserData.findByUsername(vo.getUsername());
        if (po == null) {
            throw new MyRuntimeException("该账号不存在");
        }
        if (!po.getPwdMd5().equals(vo.getPwdMd5())) {
            throw new MyRuntimeException("密码错误");
        }
        return po.getId();
    };

    @Override
    public void removeSession(String id) {

    };
}