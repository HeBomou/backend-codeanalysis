package local.happysixplus.backendcodeanalysis.po;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设置了阈值的子图，包括子图的各个联通域的点等静态信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubgraphPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double threshold;

    String name;

    /**
     * 联通域的点与边等信息
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    Set<ConnectiveDomainPo> connectiveDomains;
}