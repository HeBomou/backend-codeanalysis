package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactVo {
    
    Long id;

    Long userId;

    String username;

    Integer read;
}