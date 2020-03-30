package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.data.AdminUserData;
import local.happysixplus.backendcodeanalysis.exception.MyRuntimeException;
import local.happysixplus.backendcodeanalysis.po.AdminUserPo;
import local.happysixplus.backendcodeanalysis.service.AdminUserService;
import local.happysixplus.backendcodeanalysis.vo.AdminUserVo;
import lombok.var;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    AdminUserData adminUserData;

    @Override
    public void addAdmin(AdminUserVo vo) {
        if (adminUserData.findByUsername(vo.getUsername()) != null)
            throw new MyRuntimeException("该用户名已被注册");
        adminUserData.save(new AdminUserPo(null, vo.getUsername(), vo.getPwdMd5()));
    };

    @Override
    public void removeAdmin(Long id) {
        if (adminUserData.existsById(id))
            adminUserData.deleteById(id);
        else
            throw new MyRuntimeException("该管理员不存在");
    };

    @Override
    public void updateAdmin(AdminUserVo vo) {

        adminUserData.save(new AdminUserPo(vo.getId(), vo.getUsername(), vo.getPwdMd5()));
    };

    @Override
    public List<AdminUserVo> getAllAdmin() {
        var pos = adminUserData.findAll();
        var vos = new ArrayList<AdminUserVo>(pos.size());
        for (var po : pos)
            vos.add(new AdminUserVo(po.getId(), po.getUsername(), po.getPwdMd5(), null));
        return vos;
    };

    @Override
    public AdminUserVo getOneAdmin(Long id) {
        var po = adminUserData.findById(id).orElse(null);
        if (po == null)
            throw new MyRuntimeException("该管理员不存在");
        return new AdminUserVo(po.getId(), po.getUsername(), po.getPwdMd5(), null);
    };
}