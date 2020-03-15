package local.happysixplus.backendcodeanalysis.vo;

import lombok.Data;

@Data
public class UserVo {
    Integer id;
    String username;
    String pwdMd5;
}