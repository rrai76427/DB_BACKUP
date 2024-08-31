package com.crl.nms.Repository;

import com.crl.nms.databases.NmsAlarms;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author acer
 */
@Repository
public interface NmsAlarmsRepository extends JpaRepository<NmsAlarms,BigDecimal>{

    List<NmsAlarms> findByNekeyAndAlarmIdAlarmIdAndAlarmDescOrderByReceivingDateTimeDesc(String nekey,short alarmid, String alarmDesc);

}
