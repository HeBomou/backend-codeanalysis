package local.happysixplus.backendcodeanalysis.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import local.happysixplus.backendcodeanalysis.service.AdminUserService;
import local.happysixplus.backendcodeanalysis.vo.AdminUserVo;

@Service
public class AdminUserServiceImpl implements AdminUserService{

    @Override
    public void addAdmin(AdminUserVo vo){
        System.out.println("add admin");
    };

    @Override
    public void removeAdmin(Long id){
        System.out.println("remove admin");
    };

    @Override
    public void updateAdmin(AdminUserVo vo){
        System.out.println("update admin");
    };

    @Override
    public List<AdminUserVo> getAllAdmin(){
        System.out.println("get all admin");
        return new ArrayList<>();
    };

    @Override
    public AdminUserVo getOneAdmin(Long id){
        System.out.println("get one admin");
        return new AdminUserVo();
    };
}