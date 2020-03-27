package local.happysixplus.backendcodeanalysis.po;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    Set<VertexPo> vertexs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    Set<EdgePo> edges;

    String anotation;

    String color;
    
}