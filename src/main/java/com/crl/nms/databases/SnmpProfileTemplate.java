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
@Table(name = "SNMP_PROFILE_TEMPLATE")
@XmlRootElement
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SnmpProfileTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "SNMP_VERSION")
    private Short snmpVersion;

    @Column(name = "SNMP_PORT")
    private Integer snmpPort;

    @Column(name = "SNMP_RETRIES")
    private Short snmpRetries;

    @Column(name = "SNMP_TIMEOUT")
    private Short snmpTimeout;

    @Column(name = "SNMP_COMMUNITY")
    private String snmpCommunity;

    @Column(name = "SNMP_V3_USER")
    private String snmpV3User;

    @Column(name = "SNMP_V3_PASS")
    private String snmpV3Pass;

    @Column(name = "SNMP_V3_PRIVACYPASS")
    private String snmpV3Privacypass;

    @Column(name = "SNMP_V3_AUTHPROTOCOL")
    private String snmpV3Authprotocol;

    @Column(name = "SNMP_V3_PRIVACYPROTOCOL")
    private String snmpV3Privacyprotocol;

    @Column(name = "SNMP_V3_SECURITY")
    private Short snmpV3Security;

    @Column(name = "UPDATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "CONTEXT_NAME")
    private String contextName;

    @Column(name = "PROFILE_NAME")
    private String profileName;

    @Column(name = "AUTO_DISCOVERY_DEFAULT_FLAG")
    private Integer autoDiscoveryDefaultFlag;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NMS_SNM_PROFILE_ID_SEQ_NO_P")
    @SequenceGenerator(sequenceName = "NMS_SNM_PROFILE_ID_SEQ_NO_P", initialValue = 1, allocationSize = 1, name = "NMS_SNM_PROFILE_ID_SEQ_NO_P")
    @Id
    @Basic(optional = false)
    @Column(name = "PROFILE_ID")
    private Short profileId;

    @Column(name = "NE_TYPE")
    private Short neType;

}
