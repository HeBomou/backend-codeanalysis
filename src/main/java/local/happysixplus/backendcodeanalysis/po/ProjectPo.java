package local.happysixplus.backendcodeanalysis.po;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目包括源代码，初始图的结构等静态信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = { @Index(columnList = "userId"), @Index(columnList = "groupId") })

public class ProjectPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long userId;

    @Column(columnDefinition = "MEDIUMTEXT")
    String packageStructure;

    Long groupId;

}