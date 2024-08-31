/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crl.nms.Repository;


import com.crl.nms.databases.NmsNeDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author acer
 */
@Repository
public interface NmsNeDetailRepository extends JpaRepository<NmsNeDetail,String>{
    List<NmsNeDetail> findByNodeIdAndNeTypeIn(short nodeId,List<String> neTypes);
    
    
//    @Modifying
//    @Query("UPDATE NmsNeDetail SET NeStatus =:neStatus,neDowntime =:current_timestamp WHERE neKey = :neKey")
//    void updateNeStatusAndDowntime(@Param("neStatus")byte neStatus,@Param("neKey")String neKey);
//
//
//    @Modifying
//    @Query("UPDATE NmsNeDetail SET NeStatus = :neStatus,Uptime = current_timestamp WHERE neKey = :neKey")
//    void updateNeStatusAndUptime(@Param("neStatus")byte neStatus,@Param("neKey")String neKey);
//



     List<NmsNeDetail>findByNekey(String nekey);
     
     List<NmsNeDetail> findByNeIp(String deviceIP);

     void deleteByNekey(String nekey);
}
