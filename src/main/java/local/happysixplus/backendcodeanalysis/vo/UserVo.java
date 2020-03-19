package local.happysixplus.backendcodeanalysis.vo;

import lombok.Data;

@Data
public class UserVo {
    Long id;
    String username;
    String pwdMd5;
}