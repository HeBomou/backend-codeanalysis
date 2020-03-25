package local.happysixplus.backendcodeanalysis.po;

import java.util.List;

import javax.persistence.Convert;
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

    @Convert(converter = LongListToStringConverter.class)
    List<Long> vertexIds;

    @Convert(converter = LongListToStringConverter.class)
    List<Long> edgeIds;

    String anotation;
}