package local.happysixplus.backendcodeanalysis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVo {

    Long id;

    Long senderId;

    Long receiverId;

    String content;

    String time;

    int isRead;

}