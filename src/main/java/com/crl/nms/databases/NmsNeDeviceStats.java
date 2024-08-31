package com.crl.nms.databases;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author satwikanmol
 */
@Entity
@Table(name = "NMS_NE_DEVICE_STATS")
@XmlRootElement
@Data
@AllArgsConstructor
@Setter
@Getter
@Builder
@NoArgsConstructor
public class NmsNeDeviceStats implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    NmsNeDeviceStatsPK nmsNeDeviceStatsPK;


    @Basic(optional = false)
    @Column(name = "CPU_PERCENT_USAGE")
    private BigDecimal cpuPercentUsage;
    @Column(name = "CPU_USERSPACE")
    private String cpuUserspace;
    @Column(name = "CPU_SYSTEM")
    private String cpuSystem;
    @Column(name = "CPU_NICE")
    private String cpuNice;
    @Column(name = "CPU_WAIT")
    private String cpuWait;
    @Column(name = "CPU_HW_INTERRUPT")
    private String cpuHwInterrupt;
    @Column(name = "CPU_SW_INTERRUPT")
    private String swInterrupt;
    @Column(name = "CPU_STEAL")
    private String cpuSteal;
    @Column(name = "CPU_IDLE")
    private String cpuIdle;
    @Basic(optional = false)
    @Column(name = "CPU_TREND_COUNT")
    private short cpuTrendCount;

    @Column(name = "RAM_USAGE")
    private BigDecimal ramUsage;
    @Column(name = "RAM_PERCENT_USAGE")
    private BigDecimal ramPercentUsage;

    @Column(name = "HDD_TOTAL_SIZE")
    private String hddTotalSize;
    @Basic(optional = false)
    @Column(name = "HDD_USAGE_PERCENT")
    private String hddUsagePercent;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "HDD_MOUNT_ON")
    private JsonNode hddMountOn;

    @JoinColumn(name = "NEKEY", referencedColumnName = "NEKEY", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NmsNeDetail nmsNeDetail;









}
