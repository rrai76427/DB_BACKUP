package com.crl.nms.actors;

import akka.actor.AbstractActor;
import akka.actor.Cancellable;
import akka.actor.Props;
import com.crl.nms.common.utilities.Global;
import com.crl.nms.databases.*;
import com.crl.nms.messages.*;
import akka.actor.ActorRef;
import com.crl.nms.pojo.CPUConfigPojo;
import com.crl.nms.pojo.RAMConfigPojo;
import com.crl.nms.service.DbHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DeviceActor extends AbstractActor {

    private static final Logger logger = LoggerFactory.getLogger(DeviceActor.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    String deviceIp;

    String neKey;

    int nodeId;

    public  int ramConfigCurrentValue;

    public  int cpuConfigCurrentValue;

    Integer nodeKey;

    NmsNeDetail nmsNeDetail;

    List<NmsNeProcesses> nmsNeProcessesList = new ArrayList<>();

    DbHandlerService dbHandlerService;

    ProcessDownCount[] arrProcDownCnt = null;

    String[] arrProcName = null;

    public com.crl.nms.pojo.NmsNeRam nmsNeRamPOJO = null;

    public  com.crl.nms.pojo.NmsNeCpu NmsNeCpuPOJO = null;

    public   com.crl.nms.pojo.NmsNeHdd NmsNeHddPOJO = null;

    public static Props props(String deviceIp,String neKey,Integer nodeKey,int nodeId,List<NmsNeProcesses> nmsNeProcesses,DbHandlerService dbHandlerService, NmsNeDetail nmsNeDetail) {

        return Props.create(DeviceActor.class,
                () -> new DeviceActor(deviceIp,neKey,nodeKey,nodeId,nmsNeProcesses,dbHandlerService,nmsNeDetail));
    }

    private DeviceActor(String deviceIp,String neKey,Integer nodeKey,int nodeId,List<NmsNeProcesses> nmsNeProcesses,DbHandlerService dbHandlerService, NmsNeDetail nmsNeDetail) {

        this.deviceIp=deviceIp;
        this.neKey=neKey;
        this.dbHandlerService=dbHandlerService;
        this.nodeId=nodeId;
        this.nmsNeDetail=nmsNeDetail;
        this.nodeKey=nodeKey;
        this.nmsNeProcessesList.addAll(nmsNeProcesses);
        getProcessCount(nmsNeProcessesList);
        logger.info("Device Actor Spawn Successfully !!!"+this.nmsNeProcessesList);
        if(this.nmsNeProcessesList.size()>0){
            dbHandlerService.sendProcNames(this.neKey,this.deviceIp.replace(".","_"));
        }
    }

    private void updateTagNo(List<NmsNeProcesses> nmsNeProcessesList,int index) {

        for(int i=0;i<nmsNeProcessesList.size();i++){
            ProcessDownCount processDwnCnt=dbHandlerService.getProcessDownCnt(neKey,Global.PROCESS_DOWN_ALARM_ID,nmsNeProcessesList.get(i).getNmsNeProcessesPK().getProcname());
            arrProcName[i]=nmsNeProcessesList.get(i).getNmsNeProcessesPK().getProcname();
            processDwnCnt.setSendFlag(false);
            arrProcDownCnt[i]=processDwnCnt;
        }
    }

    private void getProcessCount(List<NmsNeProcesses> nmsNeProcessesList) {

        arrProcDownCnt=new ProcessDownCount[nmsNeProcessesList.size()];
        arrProcName=new String[nmsNeProcessesList.size()];
        for(int i=0;i<nmsNeProcessesList.size();i++){
            ProcessDownCount processDwnCnt=dbHandlerService.getProcessDownCnt(neKey,Global.PROCESS_DOWN_ALARM_ID,nmsNeProcessesList.get(i).getNmsNeProcessesPK().getProcname());
            arrProcName[i]=nmsNeProcessesList.get(i).getNmsNeProcessesPK().getProcname();
            arrProcDownCnt[i]=processDwnCnt;
        }
    }

    @Override
    public Receive createReceive() {

        return receiveBuilder()
                .match(Ping.class, this::handlePing)
                .match(SystemInfo.class, this::handleSystemInfo)
                .match(EndPointInterfaceDetailInfo.class, this::handleEndPointInterfaceDetailsInfo)
                .match(ProcessInfo.class,this::handleProcessInfo)
                .match(UpdateTagInfo.class,this::handleUpdateTagInfo)
                .match(EndPointClientConfigMsg.class,this::handleEndPointClientConfig)
                .match(RAMConfigPojo.class,this::handleRamConfig)
                .match(CPUConfigPojo.class,this::handleCpuConfig)
                .build();
    }

    @Override
    public void postStop(){
        System.out.println("Actor already killed its instance"+this.deviceIp);
    }


    private void handleEndPointInterfaceDetailsInfo(EndPointInterfaceDetailInfo message){

        try{
            for(InterfaceDetails interfaceDetails : message.getInterface_detail()){
                NmsNeDeviceInterfaceDetail nmsNeDeviceInterfaceDetail = new NmsNeDeviceInterfaceDetail();
                NmsNeDeviceInterfaceDetailPK nmsNeDeviceInterfaceDetailPK = new NmsNeDeviceInterfaceDetailPK();
                nmsNeDeviceInterfaceDetailPK.setInterfaceId(interfaceDetails.getInterfaceId());
                nmsNeDeviceInterfaceDetailPK.setNekey(this.neKey);
                nmsNeDeviceInterfaceDetail.setNmsNeDeviceInterfaceDetailPK(nmsNeDeviceInterfaceDetailPK);
                nmsNeDeviceInterfaceDetail.setTx(interfaceDetails.getTx());
                nmsNeDeviceInterfaceDetail.setRx(interfaceDetails.getRx());
                nmsNeDeviceInterfaceDetail.setStatus(Short.valueOf(interfaceDetails.getStatus()));
                nmsNeDeviceInterfaceDetail.setMac(interfaceDetails.getMAC());
                nmsNeDeviceInterfaceDetail.setTimestamp(new Date());
                dbHandlerService.insertEndpointInterfaceInfo(nmsNeDeviceInterfaceDetail);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void handleCpuConfig(CPUConfigPojo cpuConfigPojo) {

        try {
            CPUConfigPojo cpuConfigPojo1=CPUConfigPojo.builder().currentValue(cpuConfigPojo.getCurrentValue()).updatingTime(new Date()).build();
            dbHandlerService.sendConfigMsg(this.neKey,this.deviceIp.replace(".","_"), cpuConfigPojo1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleRamConfig(RAMConfigPojo ramConfigPojo) {

        try {
            RAMConfigPojo ramConfigPojo1=ramConfigPojo.builder().currentValue(ramConfigPojo.getCurrentValue()).updatingTime(new Date()).build();
            dbHandlerService.sendConfigMsg(this.neKey,this.deviceIp.replace(".","_"), ramConfigPojo1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Sending EndPointClientConfigMsg
    private void handleEndPointClientConfig(EndPointClientConfigMsg endPointClientConfig) {

        if (endPointClientConfig.getMessage_type().equals("GET_ENDPOINT_CONFIG")) {
            try {
                dbHandlerService.sendEndPointConfigMsg(this.neKey, this.deviceIp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSystemInfo(SystemInfo systemInfoMessage) throws JsonProcessingException {

        nmsNeRamPOJO=systemInfoMessage.getRamInfo();
        NmsNeRamPK nmsNeRamPK=new NmsNeRamPK();
        NmsNeRam nmsNeRam=new NmsNeRam();
        nmsNeRamPK.setNekey(this.neKey);
        nmsNeRamPK.setUpdatetime(new Date());
        nmsNeRam.setNmsNeRamPK(nmsNeRamPK);
        nmsNeRam.setNodeKey(this.nmsNeDetail.getNodeKey());
        nmsNeRam.setRamPercentusage(nmsNeRamPOJO.getRamPercentusage());
        nmsNeRam.setRamusage(nmsNeRamPOJO.getRamusage());
        dbHandlerService.insertRAMInfo(nmsNeRam);
        if(nmsNeRamPOJO.getRamPercentusage().compareTo(BigDecimal.valueOf(ramConfigCurrentValue) )>0 &&ramConfigCurrentValue!=0){
            NmsAlarms nmsAlarms= NmsAlarms.builder()
                    .receivingDateTime(new Date())
                    .severity(Global.CRITICAL)
                    .alarmDesc("CPU Threshold Breach")
                    .nekey(this.neKey).nodeKey(nodeKey).build();
            dbHandlerService.sendMsgToKAFKA(neKey, nmsAlarms, "cpu_config_info_alarm");
        }
        NmsNeCpuPOJO=systemInfoMessage.getCpuInfo();
        NmsNeCpuPK nmsNeCpuPK=new NmsNeCpuPK();
        nmsNeCpuPK.setTrendkey(this.neKey+"_"+ systemInfoMessage.trendcount);
        nmsNeCpuPK.setUpdatetime(new Date());
        NmsNeCpu nmsNeCpu1=new NmsNeCpu();
        nmsNeCpu1.setNmsNeCpuPK(nmsNeCpuPK);
        nmsNeCpu1.setNekey(this.nmsNeDetail);
        nmsNeCpu1.setTrendcount(systemInfoMessage.trendcount);
        nmsNeCpu1.setNodeKey(this.nmsNeDetail.getNodeKey());
        nmsNeCpu1.setCpuPercentusage(NmsNeCpuPOJO.getCpuPercentusage());
        nmsNeCpu1.setSteal(NmsNeCpuPOJO.getSteal());
        nmsNeCpu1.setHwInterrupt(NmsNeCpuPOJO.getHwInterrupt());
        nmsNeCpu1.setIdle(NmsNeCpuPOJO.getIdle());
        nmsNeCpu1.setSwInterrupt(NmsNeCpuPOJO.getSwInterrupt());
        nmsNeCpu1.setNice(NmsNeCpuPOJO.getNice());
        nmsNeCpu1.setSystem(NmsNeCpuPOJO.getSystem());
        nmsNeCpu1.setUserspace(NmsNeCpuPOJO.getUserspace());
        nmsNeCpu1.setWait(NmsNeCpuPOJO.getWait());
        dbHandlerService.insertCPUInfo(nmsNeCpu1);
        if(NmsNeCpuPOJO.getCpuPercentusage().compareTo(BigDecimal.valueOf(cpuConfigCurrentValue))>0 && cpuConfigCurrentValue!=0){
            NmsAlarms nmsAlarms=   NmsAlarms.builder()
                    .receivingDateTime(new Date())
                    .severity(Global.CRITICAL)
                    .alarmDesc("Ram Threshold Breach")
                    .nekey(this.neKey).nodeKey(nodeKey).build();
            dbHandlerService.sendMsgToKAFKA(neKey, nmsAlarms, "ram_config_info_alarm");
        }
        for(int i=0;i<systemInfoMessage.getDiskInfo().size();i++){
            NmsNeHddPOJO=systemInfoMessage.diskInfo.get(i);
            NmsNeHdd nmsNeHdd1=new NmsNeHdd();
            NmsNeHddPK nmsNeHddPK=new NmsNeHddPK();
            nmsNeHddPK.setHddkey(this.neKey+"_"+(i+1));
            nmsNeHddPK.setUpdatetime(new Date());
            nmsNeHdd1.setNmsNeHddPK(nmsNeHddPK);
            nmsNeHdd1.setNekey(this.nmsNeDetail);
            nmsNeHdd1.setNodeKey(this.nmsNeDetail.getNodeKey());
            nmsNeHdd1.setDiskutil(NmsNeHddPOJO.getDiskutil());
            nmsNeHdd1.setTotalsize(NmsNeHddPOJO.getTotalsize());
            nmsNeHdd1.setUsagePercent(NmsNeHddPOJO.getUsagePercent());
            nmsNeHdd1.setMounton(NmsNeHddPOJO.getMounton());
            dbHandlerService.insertHDDInfo(nmsNeHdd1);
        }
        var nmsDeviceStatPK = new NmsNeDeviceStatsPK();
        nmsDeviceStatPK.setNekey(this.neKey);
        nmsDeviceStatPK.setNodeKey(this.nodeKey);

        Optional<NmsNeDeviceStats> nmsNeDeviceStats = dbHandlerService.getDeviceStatusField(nmsDeviceStatPK);
        NmsNeDeviceStats nmsDeviceStats = null;
        if(nmsNeDeviceStats.isPresent()){
            nmsDeviceStats = nmsNeDeviceStats.get();
        } else {
            nmsDeviceStats = new NmsNeDeviceStats();
            nmsDeviceStats.setNmsNeDeviceStatsPK(nmsDeviceStatPK);
        }

        // CPU
        nmsDeviceStats.setCpuTrendCount(systemInfoMessage.trendcount);
        nmsDeviceStats.setCpuPercentUsage(NmsNeCpuPOJO.getCpuPercentusage());
        nmsDeviceStats.setCpuSteal(NmsNeCpuPOJO.getSteal());
        nmsDeviceStats.setCpuHwInterrupt(NmsNeCpuPOJO.getHwInterrupt());
        nmsDeviceStats.setCpuIdle(NmsNeCpuPOJO.getIdle());
        nmsDeviceStats.setCpuNice(NmsNeCpuPOJO.getNice());
        nmsDeviceStats.setCpuSystem(NmsNeCpuPOJO.getSystem());
        nmsDeviceStats.setCpuUserspace(NmsNeCpuPOJO.getUserspace());
        nmsDeviceStats.setCpuWait(NmsNeCpuPOJO.getWait());

        // RAM
        nmsDeviceStats.setRamPercentUsage(nmsNeRamPOJO.getRamPercentusage());
        nmsDeviceStats.setRamUsage(nmsNeRamPOJO.getRamusage());

        // HDD
        nmsDeviceStats.setHddTotalSize(systemInfoMessage.getHddTotalSize());
        nmsDeviceStats.setHddUsagePercent(systemInfoMessage.getHddUsagePercentage());
        nmsDeviceStats.setHddMountOn(objectMapper.readTree(objectMapper.writeValueAsString(getHddMountOnJSON(systemInfoMessage.getDiskInfo()))));

        dbHandlerService.insertSystemInfo(nmsDeviceStats);
    }

    private NmsNeDeviceStats getNmsNeDeviceStats(NmsNeDeviceStats nmsDeviceStats, SystemInfo systemInfoMessage, NmsNeDeviceStatsPK nmsDeviceStatPK) throws JsonProcessingException {

        // CPU
        nmsDeviceStats.setCpuTrendCount(systemInfoMessage.trendcount);
        nmsDeviceStats.setCpuPercentUsage(NmsNeCpuPOJO.getCpuPercentusage());
        nmsDeviceStats.setCpuSteal(NmsNeCpuPOJO.getSteal());
        nmsDeviceStats.setCpuHwInterrupt(NmsNeCpuPOJO.getHwInterrupt());
        nmsDeviceStats.setCpuIdle(NmsNeCpuPOJO.getIdle());
        nmsDeviceStats.setCpuNice(NmsNeCpuPOJO.getNice());
        nmsDeviceStats.setCpuSystem(NmsNeCpuPOJO.getSystem());
        nmsDeviceStats.setCpuUserspace(NmsNeCpuPOJO.getUserspace());
        nmsDeviceStats.setCpuWait(NmsNeCpuPOJO.getWait());

        // RAM
        nmsDeviceStats.setRamPercentUsage(nmsNeRamPOJO.getRamPercentusage());
        nmsDeviceStats.setRamUsage(nmsNeRamPOJO.getRamusage());

        // HDD
        nmsDeviceStats.setHddTotalSize(systemInfoMessage.getHddTotalSize());
        nmsDeviceStats.setHddUsagePercent(systemInfoMessage.getHddUsagePercentage());
        nmsDeviceStats.setHddMountOn(objectMapper.readTree(objectMapper.writeValueAsString(getHddMountOnJSON(systemInfoMessage.getDiskInfo()))));
        return nmsDeviceStats;
    }

    private List<HddMountOnModel> getHddMountOnJSON(List<com.crl.nms.pojo.NmsNeHdd> message){

        List<HddMountOnModel> hddMountOnModelList = new ArrayList<>();
        for(com.crl.nms.pojo.NmsNeHdd nmsNeHdd : message){
            HddMountOnModel hddMountOnModel = new HddMountOnModel();
            hddMountOnModel.setMountOn(nmsNeHdd.getMounton());
            hddMountOnModel.setUsed(nmsNeHdd.getUsagePercent());
            hddMountOnModel.setTotal(nmsNeHdd.getTotalsize());
            hddMountOnModelList.add(hddMountOnModel);
        }
        return hddMountOnModelList;
    }

    public void handleProcessInfo(ProcessInfo processInfo){

        switch (processInfo.getMessage_type()){
            case "GET_PROCNAME":
                try {
                    dbHandlerService.sendProcNames(this.neKey,this.deviceIp.replace(".","_"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "PROC_DETAILS":
                NmsAlarms nmsAlarms=null;
                try{
                    for (NmsNeProcesses nmsNeProcesses: processInfo.getProcesses_list()) {
                    nmsNeProcesses.setNmsNeDetail(this.nmsNeDetail);
                    nmsNeProcesses.setNodeKey(this.nmsNeDetail.getNodeKey());
                    if(nmsNeProcesses.getProcstate()== Global.PROCESS_RUNNING){
                        List<String> tempProcList=Arrays.asList(arrProcName);
                        int index=tempProcList.indexOf(nmsNeProcesses.getNmsNeProcessesPK().getProcname());
                        ProcessDownCount processDownCount = arrProcDownCnt[index];
                        if(!processDownCount.isSendFlag()){
                            processDownCount.setSendFlag(true);
                            arrProcDownCnt[index]=processDownCount;
                            dbHandlerService.updateClearingTime(neKey,Global.PROCESS_DOWN_ALARM_ID,nmsNeProcesses.getNmsNeProcessesPK().getProcname(),Global.FAULT_AUTO_CLOSE);
                        }
                    }
                    else{
                        List<String> tempProcList=Arrays.asList(arrProcName);
                        int index=tempProcList.indexOf(nmsNeProcesses.getNmsNeProcessesPK().getProcname());
                        ProcessDownCount processDownCount = arrProcDownCnt[index];
                        if(processDownCount.isSendFlag()){
                            BigInteger counter = processDownCount.getProcessDownCount().add(BigInteger.ONE);
                            processDownCount.setProcessDownCount(counter);
                            nmsAlarms=  NmsAlarms.builder()
                                    .tagno(processDownCount.getTagNo())
                                    .receivingDateTime(new Date())
                                    .severity(Global.CRITICAL)
                                    .alarmDesc(nmsNeProcesses.getNmsNeProcessesPK().getProcname()+Global.PROCESS_DOWN_STR)
                                    .alarmCount(processDownCount.getProcessDownCount())
                                    .nekey(this.neKey).nodeKey(nodeKey).build();
                            dbHandlerService.sendMsgToKAFKA(neKey,nmsAlarms,Global.PROCESS_INFO_ALARM);
                            processDownCount.setSendFlag(false);
                            if(processDownCount.getTagNo().equals(BigDecimal.ZERO)){
                                Cancellable scheduleOnce = this.getContext().getSystem().scheduler().scheduleOnce(
                                        Duration.create(1, TimeUnit.SECONDS), this.getSelf(), UpdateTagInfo.builder().index(index).processName(nmsNeProcesses.getNmsNeProcessesPK().getProcname()).build(), this.getContext().dispatcher(),
                                        ActorRef.noSender());
                            }
                            arrProcDownCnt[index]=processDownCount;
                        }
                        dbHandlerService.updateClearingTime(neKey,Global.PROCESS_DOWN_ALARM_ID,nmsNeProcesses.getNmsNeProcessesPK().getProcname(),Global.FAULT_OPEN);
                    }
                    dbHandlerService.insertProcessInfo(nmsNeProcesses);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "ADD_PROCESS":
                try {
                    this.nmsNeProcessesList.add(processInfo.getProcesses_list().get(0));
                    getProcessCount(nmsNeProcessesList);
                    ProcessInfo processInfo1=ProcessInfo.builder().message_type("ADD_PROCESS").processes_list( processInfo.getProcesses_list()).build();
                    dbHandlerService.sendProcessMsg(this.neKey,this.deviceIp.replace(".","_"), processInfo1);
                }
                catch (Exception e){

                    e.printStackTrace();
                }
                break;
            case "DELETE_PROCESS":
                try {
                    this.nmsNeProcessesList.remove(processInfo.getProcesses_list().get(0));
                    getProcessCount(nmsNeProcessesList);
                    ProcessInfo processInfo2=ProcessInfo.builder().message_type("DELETE_PROCESS").processes_list( processInfo.getProcesses_list()).build();
                    dbHandlerService.sendProcessMsg(this.neKey,this.deviceIp.replace(".","_"), processInfo2);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "EDIT_PROCESS":
                try {
                    this.nmsNeProcessesList.add(processInfo.getProcesses_list().get(0));
                    getProcessCount(nmsNeProcessesList);
                    ProcessInfo processInfo3=ProcessInfo.builder().message_type("EDIT_PROCESS").processes_list( processInfo.getProcesses_list()).build();
                    dbHandlerService.sendProcessMsg(this.neKey,this.deviceIp.replace(".","_"), processInfo3);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "PROCESS_INFO_DOWN":
                try {
                    dbHandlerService.setProcessDownByNeIp(processInfo.getIpAddress());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }



    public void handleUpdateTagInfo(UpdateTagInfo message){

        try {
            ProcessDownCount processDwnCnt=dbHandlerService.getProcessDownCnt(neKey,Global.PROCESS_DOWN_ALARM_ID,message.getProcessName());
            arrProcDownCnt[message.getIndex()].setTagNo(processDwnCnt.getTagNo());
            dbHandlerService.updateClearingTime(neKey,Global.PROCESS_DOWN_ALARM_ID,message.getProcessName(),Global.FAULT_OPEN);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void handlePing(Ping ping){

        switch (ping.getMessage()){
            case "START_PROCESS_INFO":
                try {
                    ProcessInfo processInfo1=ProcessInfo.builder().message_type("START_PROCESS_INFO").build();
                    dbHandlerService.sendProcessMsg(this.neKey,this.deviceIp.replace(".","_"), processInfo1);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "STOP_PROCESS_INFO":
                try {
                    ProcessInfo processInfo2=ProcessInfo.builder().message_type("STOP_PROCESS_INFO").build();
                    dbHandlerService.sendProcessMsg(this.neKey,this.deviceIp.replace(".","_"), processInfo2);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "START_SYSTEM_INFO":
                try{
                    ProcessInfo processInfo3=ProcessInfo.builder().message_type("START_SYSTEM_INFO").build();
                    dbHandlerService.sendProcessMsg(this.neKey,this.deviceIp.replace(".","_"), processInfo3);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "STOP_SYSTEM_INFO":
                try {
                    ProcessInfo processInfo4=ProcessInfo.builder().message_type("STOP_SYSTEM_INFO").build();
                    dbHandlerService.sendProcessMsg(this.neKey,this.deviceIp.replace(".","_"), processInfo4);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }
}
