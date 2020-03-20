package local.happysixplus.backendcodeanalysis.service.impl;

import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.service.AdminSessionService;
import local.happysixplus.backendcodeanalysis.vo.AdminSessionVo;

@Service
public class AdminSessionServiceImpl implements AdminSessionService{
    
    @Override
    public void addSession(AdminSessionVo vo){
        System.out.println("add admin Session");
    };

    @Override
    public void removeSession(){
        System.out.println("remove admin Session");
    };
}