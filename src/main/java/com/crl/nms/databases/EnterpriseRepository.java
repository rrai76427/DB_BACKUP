package  com.crl.nms.databases;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Constraint;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

/**
 *
 * @author meenuchhindra
 */
@Entity
@Table(name = "ENTERPRISE_REPOSITORY")
@XmlRootElement
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected EnterpriseRepositoryPK enterpriseRepositoryPK;

    @Column(name = "PARAM_NAME")
    private String paramName;

    @Column(name = "THRESHOLD")
    private Short threshold;

    @Column(name = "MIB_DATA_TYPE")
    private String mibDataType;

    @Column(name = "CONFIGURABLE")
    private Short configurable;

    @Column(name = "DISPLAY_FLAG")
    private Short displayFlag;

    @Column(name = "TRIGGER1_VALUE")
    private String trigger1Value;

    @Column(name = "TRIGGER2_VALUE")
    private String trigger2Value;

    @Column(name = "TRIGGER3_VALUE")
    private String trigger3Value;

    @Column(name = "TRIGGER1_DESCRIPTION")
    private String trigger1Description;

    @Column(name = "TRIGGER2_DESCRIPTION")
    private String trigger2Description;

    @Column(name = "TRIGGER3_DESCRIPTION")
    private String trigger3Description;

    @Column(name = "DESCR")
    private String descr;

    @Column(name = "syntax")
    private String syntax;

    @Column(name = "INTERPRETATION")
    private String interpretation;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "AUTO_DISCOVERY_DEFAULT_FLAG")
    private Integer autoDiscoveryDefaultFlag;

    @Column(name = "NE_TYPE")
    private Short neType;
}
