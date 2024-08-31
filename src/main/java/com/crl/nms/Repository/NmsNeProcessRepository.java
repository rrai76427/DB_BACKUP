package com.crl.nms.Repository;

import com.crl.nms.databases.NmsNeProcesses;
import com.crl.nms.databases.NmsNeProcessesPK;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface NmsNeProcessRepository extends JpaRepository<NmsNeProcesses, NmsNeProcessesPK> {
    public List<NmsNeProcesses> findByNmsNeProcessesPKNekey(String nekey);
    Optional<NmsNeProcesses>  findByNmsNeProcessesPK(NmsNeProcessesPK nmsNeProcessesPK);

    @Transactional
    @Modifying
    @Query(value = "update public.NMS_NE_PROCESSES " +
            "set cpu_percent=?1 , mem_percent= ?2 , procstate= ?3 , procstatus= ?4 \n" +
            "where procname= ?5 ", nativeQuery = true)
    void updateProcessInfo(BigDecimal cpuPercent, BigDecimal memPercent, Short procState, Short procStatus, String procName);


    @Query(value = "UPDATE nms_ne_processes as nnp\n" +
            "SET procstate= :procState , procstatus= :procStatus \n" +
            "FROM nms_ne_processes\n" +
            "WHERE nnp.nekey= :nekey ",
            nativeQuery = true)
    @Transactional
    @Modifying
    void updateProcessByNekey(Short procState, Short procStatus, String nekey);
}
