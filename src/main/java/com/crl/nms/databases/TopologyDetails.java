package  com.crl.nms.databases;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "NMS_NODE_HIERARCHY")
@XmlRootElement
@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopologyDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NODE_ID")
    private Integer nodeId;

    @Column(name = "NODE_LEVEL")
    private Short nodeLevel;

    @Column(name = "NODE_NAME")
    private String nodeName;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "LOC_CODE")
    private String locCode;

    @Column(name = "RESERVE1")
    private Short reserve1;

    @Column(name = "LAT")
    private String lat;

    @Column(name = "LAT_DIR")
    private String latDir;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "LONG_DIR")
    private String longDir;

    @Column(name = "PARENT_NODE_ID")
    private Integer parentNodeId;

    @Column(name = "RESERVE2")
    private Short reserve2;

    @Column(name = "NO_OF_CHILD")
    private Short noOfChild;

    @Column(name = "LOC_UI")
    private String locUi;

    @Column(name = "NMS_IP")
    private String nmsIp;

    @Column(name = "NODE_STATUS")
    private Short nodeStatus;

    @Column(name = "LINK_STATUS")
    private Short linkStatus;

    @Column(name = "POLL_INTERVAL")
    private Long pollInterval;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "DB_IP")
    private String dbIp;

    @Column(name = "X_POS")
    private String xPos;

    @Column(name = "Y_POS")
    private String yPos;

    @Column(name = "CRITICAL_NODE")
    private Short criticalNode;

    @Column(name = "GEOMAP_TYPE")
    private Short geomapType;

    @Column(name = "NODE_STATE")
    private Short nodeState;

    @JoinColumn(name = "NODE_KEY", referencedColumnName = "NODE_KEY")
    @ManyToOne
    private NodeDefinition nodeKey;
}

