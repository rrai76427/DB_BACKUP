package com.crl.nms.messages;

import lombok.*;

/**
 *
 * @author Sneha Prajapati
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddDeviceModel {

    public String deviceName = "";

    public String deviceType = "";

    public String deviceIP = "";

    public String deviceStatus;

    public String nodeId;

    public String neKey;   //ne element of agency

    public String neGroupNo;  // Ne Group

    public String snmpVersion;

    public String port;

    public String retries;

    public String timeout;

    public String usernameSNMP;

    public String snmpContext;

    public String authPassphrase;

    public String privacyPassPhrase;

    public String securityType;

    public String authProtocol;

    public String privacyProtocol;

    public String community;

    public String snmpProfileStatus;

    public String profileId;

    public String profileName;

    private short neId;

    private int isSnmpFlag;

    private short groupNo;

    private String userName;

    private short isIPChange;

    private String threshLimit;

    public String templateName;

    private String url;

    public boolean templateFlag;

    private boolean autoDisc = false;

    public int isSnmpFlag() {
        return isSnmpFlag;
    }
}
