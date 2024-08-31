package  com.crl.nms.databases;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

/**
 *
 * @author ashwinimehta
 */
@Entity
@Table(name = "ENTERPRISE_PARAM_TEMPLATE")
@XmlRootElement
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseParamTemplate implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EnterpriseParamTemplatePK enterpriseParamTemplatePK;

    @Column(name = "UPDATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

}
