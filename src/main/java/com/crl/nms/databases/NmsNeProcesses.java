package  com.crl.nms.databases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ashwinimehta
 */
@Entity
@Table(name = "NMS_NE_PROCESSES")
@XmlRootElement

public class NmsNeProcesses implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NmsNeProcessesPK nmsNeProcessesPK;
    @Column(name = "PROCNO")
    private Integer procno=0;
    @Column(name = "PROCSTATUS")
    private Short procstatus=0;
    @Column(name = "PROCPATH")
    private String procpath="NA";
    @Column(name = "PROCSIZE")
    private String procsize="NA";
    @Column(name = "PROCCKSUM")
    private String proccksum="NA";
    @Column(name = "RUN_DURATION")
    private String runDuration="NA";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CPU_PERCENT")
    private BigDecimal cpuPercent=BigDecimal.valueOf(0);
    @Column(name = "MEM_PERCENT")
    private BigDecimal memPercent=BigDecimal.valueOf(0);
    @Column(name = "IOREAD_BYTES")
    private String ioreadBytes="NA";
    @Column(name = "IOWRITE_BYTES")
    private String iowriteBytes="NA";
    @Column(name = "PROCSTATE")
    private Short procstate=0;
    @JoinColumn(name = "NEKEY", referencedColumnName = "NEKEY", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NmsNeDetail nmsNeDetail;
    @JoinColumn(name = "NODE_KEY", referencedColumnName = "NODE_KEY")
    @ManyToOne
    private NodeDefinition nodeKey;

    @Column(name = "UPDATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;
    public NmsNeProcesses() {
    }

    public NmsNeProcesses(NmsNeProcessesPK nmsNeProcessesPK) {
        this.nmsNeProcessesPK = nmsNeProcessesPK;
    }

    public NmsNeProcesses(String nekey, String procname) {
        this.nmsNeProcessesPK = new NmsNeProcessesPK(nekey, procname);
    }

    public NmsNeProcessesPK getNmsNeProcessesPK() {
        return nmsNeProcessesPK;
    }

    public void setNmsNeProcessesPK(NmsNeProcessesPK nmsNeProcessesPK) {
        this.nmsNeProcessesPK = nmsNeProcessesPK;
    }

    public Integer getProcno() {
        return procno;
    }

    public void setProcno(Integer procno) {
        this.procno = procno;
    }

    public Short getProcstatus() {
        return procstatus;
    }

    public void setProcstatus(Short procstatus) {
        this.procstatus = procstatus;
    }

    public String getProcpath() {
        return procpath;
    }

    public void setProcpath(String procpath) {
        this.procpath = procpath;
    }

    public String getProcsize() {
        return procsize;
    }

    public void setProcsize(String procsize) {
        this.procsize = procsize;
    }

    public String getProccksum() {
        return proccksum;
    }

    public void setProccksum(String proccksum) {
        this.proccksum = proccksum;
    }

    public String getRunDuration() {
        return runDuration;
    }

    public void setRunDuration(String runDuration) {
        this.runDuration = runDuration;
    }

    public BigDecimal getCpuPercent() {
        return cpuPercent;
    }

    public void setCpuPercent(BigDecimal cpuPercent) {
        this.cpuPercent = cpuPercent;
    }

    public BigDecimal getMemPercent() {
        return memPercent;
    }

    public void setMemPercent(BigDecimal memPercent) {
        this.memPercent = memPercent;
    }

    public String getIoreadBytes() {
        return ioreadBytes;
    }

    public void setIoreadBytes(String ioreadBytes) {
        this.ioreadBytes = ioreadBytes;
    }

    public String getIowriteBytes() {
        return iowriteBytes;
    }

    public void setIowriteBytes(String iowriteBytes) {
        this.iowriteBytes = iowriteBytes;
    }

    public Short getProcstate() {
        return procstate;
    }

    public void setProcstate(Short procstate) {
        this.procstate = procstate;
    }

    public NmsNeDetail getNmsNeDetail() {
        return nmsNeDetail;
    }

    public void setNmsNeDetail(NmsNeDetail nmsNeDetail) {
        this.nmsNeDetail = nmsNeDetail;
    }

    public NodeDefinition getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(NodeDefinition nodeKey) {
        this.nodeKey = nodeKey;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nmsNeProcessesPK != null ? nmsNeProcessesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NmsNeProcesses)) {
            return false;
        }
        NmsNeProcesses other = (NmsNeProcesses) object;
        if ((this.nmsNeProcessesPK == null && other.nmsNeProcessesPK != null) || (this.nmsNeProcessesPK != null && !this.nmsNeProcessesPK.equals(other.nmsNeProcessesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shm_bo.NmsNeProcesses[ nmsNeProcessesPK=" + nmsNeProcessesPK + " ]";
    }
    
}
