package local.happysixplus.backendcodeanalysis.service.impl;

import org.springframework.stereotype.Service;

import local.happysixplus.backendcodeanalysis.service.SessionService;
import local.happysixplus.backendcodeanalysis.vo.SessionVo;

@Service
public class SessionServiceImpl implements SessionService {
    
    @Override
    public void addSession(SessionVo vo){
        System.out.println("add Session");
    };

    @Override
    public void removeSession(){
        System.out.println("remove Session");
    };
}