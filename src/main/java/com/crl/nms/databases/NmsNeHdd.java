package  com.crl.nms.databases;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

/**
 *
 * @author ashwinimehta
 */
@Entity
@Table(name = "NMS_NE_HDD")
@XmlRootElement
@Data
@Builder
@AllArgsConstructor
public class NmsNeHdd implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected NmsNeHddPK nmsNeHddPK;

    @Basic(optional = false)
    @Column(name = "HDDNO")
    private int hddno;

    @Column(name = "FILESYSNAME")
    private String filesysname;

    @Column(name = "TOTALSIZE")
    private String totalsize;

    @Basic(optional = false)
    @Column(name = "USAGE_PERCENT")
    private String usagePercent;

    @Column(name = "DISKUTIL")
    private String diskutil;

    @Column(name = "MOUNTON")
    private String mounton;

    @JoinColumn(name = "NEKEY", referencedColumnName = "NEKEY") //, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NmsNeDetail nekey;

    @JoinColumn(name = "NODE_KEY", referencedColumnName = "NODE_KEY")
    @ManyToOne
    private NodeDefinition nodeKey;

    public NmsNeHdd() {
    }

  /*  public NmsNeHdd(String hddkey) {
        this.hddkey = hddkey;
    }

    public NmsNeHdd(String hddkey, int hddno, String usagePercent) {
        this.hddkey = hddkey;
        this.hddno = hddno;
        this.usagePercent = usagePercent;
    }

    public int getHddno() {
        return hddno;
    }

    public void setHddno(int hddno) {
        this.hddno = hddno;
    }

    public String getFilesysname() {
        return filesysname;
    }

    public void setFilesysname(String filesysname) {
        this.filesysname = filesysname;
    }

    public String getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(String totalsize) {
        this.totalsize = totalsize;
    }

    public String getUsagePercent() {
        return usagePercent;
    }

    public void setUsagePercent(String usagePercent) {
        this.usagePercent = usagePercent;
    }

    public String getDiskutil() {
        return diskutil;
    }

    public void setDiskutil(String diskutil) {
        this.diskutil = diskutil;
    }

    public String getMounton() {
        return mounton;
    }

    public void setMounton(String mounton) {
        this.mounton = mounton;
    }

    public String getHddkey() {
        return hddkey;
    }

    public void setHddkey(String hddkey) {
        this.hddkey = hddkey;
    }

    public NmsNeDetail getNekey() {
        return nekey;
    }

    public void setNekey(NmsNeDetail nekey) {
        this.nekey = nekey;
    }

    public NodeDefinition getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(NodeDefinition nodeKey) {
        this.nodeKey = nodeKey;
    }*/

   /* @Override
    public int hashCode() {
        int hash = 0;
        hash += (hddkey != null ? hddkey.hashCode() : 0);
        return hash;
    }*/

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NmsNeHdd)) {
            return false;
        }
        NmsNeHdd other = (NmsNeHdd) object;
        /*if ((this.hddkey == null && other.hddkey != null) || (this.hddkey != null && !this.hddkey.equals(other.hddkey))) {
            return false;
        }*/
        return true;
    }

/*    @Override
    public String toString() {
        return "shm_bo.NmsNeHdd[ hddkey=" + hddkey + " ]";
    }*/
    
}
