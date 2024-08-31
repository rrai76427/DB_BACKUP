package com.crl.nms.messages;

import lombok.*;

@Data
@AllArgsConstructor
@Setter
@Getter
public class ProcessHealthStatusModel {
    String service;
    String status;
    String deviceIp;
}
