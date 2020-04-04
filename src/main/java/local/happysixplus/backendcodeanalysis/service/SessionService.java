package local.happysixplus.backendcodeanalysis.service;

import local.happysixplus.backendcodeanalysis.vo.SessionVo;

public interface SessionService {

    /**
     * 用户登陆获取一个Session
     * 
     * @param vo
     */
    Long addSession(SessionVo vo) throws Exception;

    /**
     * 用户登出移除对应的Session
     */
    void removeSession(String id) throws Exception;
}