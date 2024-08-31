package  com.crl.nms.databases;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

/**
 *
 * @author ashwinimehta
 */
@Entity
@Table(name = "NMS_NE_CPU")
@XmlRootElement
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class NmsNeCpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "CPU_PERCENTUSAGE")
    private BigDecimal cpuPercentusage;

    @Column(name = "USERSPACE")
    private String userspace;

    @Column(name = "SYSTEM")
    private String system;

    @Column(name = "NICE")
    private String nice;

    @Column(name = "WAIT")
    private String wait;

    @Column(name = "HW_INTERRUPT")
    private String hwInterrupt;

    @Column(name = "SW_INTERRUPT")
    private String swInterrupt;

    @Column(name = "STEAL")
    private String steal;

    @Column(name = "IDLE")
    private String idle;

    @Basic(optional = false)
    @Column(name = "TRENDCOUNT")
    private short trendcount;

    @EmbeddedId
    NmsNeCpuPK nmsNeCpuPK;

    @JoinColumn(name = "NEKEY", referencedColumnName = "NEKEY") //, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NmsNeDetail nekey;

    @JoinColumn(name = "NODE_KEY", referencedColumnName = "NODE_KEY")
    @ManyToOne
    private NodeDefinition nodeKey;

}
