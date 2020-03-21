package local.happysixplus.backendcodeanalysis.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConnectiveDomainPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long subgraphId;

    // TODO: 需要使用@Convert注解实现Long数组
    // @Convert
    // List<Long> vertexIds;

    // @Convert
    // List<Long> edgeIds;

    String anotation;
}