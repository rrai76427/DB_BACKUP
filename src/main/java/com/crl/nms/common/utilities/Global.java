<<<<<<< HEAD
package com.crl.nms.common.utilities;

import akka.actor.ActorRef;
import java.util.concurrent.ConcurrentHashMap;

public class Global {

  public static String dbIp;

  public static String dbPort;

  public static String dbSID;

  public static String dbUrl;

  public static String l_dbUser;

  public static String l_dbPasswd;

  public static String l_driverClass;

  public static String l_dbUrl;

  public static String l_dialect;

  public static String s_dbDriver;

  public static String s_dbSchema;

  public static final String process = "process";

  public static final String SYSTEM_INFO="system_info";

  public static final String PROCESS_INFO="process_info";

  public static final String GET_ENDPOINT_CLIENT_CONFIG="get_endpoint_client_config";

  public static final String ADD_RAM_CONFIG="add_ram_config";

  public static final String  ADD_CPU_CONFIG="add_cpu_config";

  public static final String PROCESS_INFO_ALARM="process_info_alarm";

  public static final  String ADD_PROCESS="add_process";

  public static final  String DELETE_PROCESS="delete_process";

  public static final  String EDIT_PROCESS="edit_process";

  public static final String ADD_DEVICE ="add_device" ;

  public static final String EDIT_DEVICE ="edit_device" ;

  public static final String DELETE_DEVICE ="delete_device" ;

  public static final String END_POINT_INTERFACE_DETAILS = "endpoint_interface_details";

  public static final String process_health_status = "process_health_status";

  public  static final int PROCESS_RUNNING=1;

  public static final short PROCESS_DOWN_ALARM_ID=3;

  public static final String PROCESS_DOWN_STR=": Process is Down";

  public static final int PROCESS_STOP=2;

  public static ConcurrentHashMap<String, ActorRef> deviceip_actoraddress = new ConcurrentHashMap<>();

  public static final short FAULT_OPEN=1;

  public static final short FAULT_AUTO_CLOSE=4;

  public static final short FAULT_MANUAL_CLOSE=5;

  public static final short CRITICAL=1;

  public static final short NON_CRITICAL=2;

  public static final short INFORMATIVE=3;

  public static final short PROCESS_UP = 1;

  public static final short PROCESS_DOWN = 2;

  public static final String PROCESS_INFO_DOWN = "PROCESS_INFO_DOWN";
}
=======
package com.crl.nms.common.utilities;

import akka.actor.ActorRef;
import java.util.concurrent.ConcurrentHashMap;

public class Global {


  public static final String CREATE_BACKP="create_backup";

  public static final String GET_DBBACKUP_LIST="db_backup_list";
}
>>>>>>> ce7b643 (CBTC_DB_BACKUP)
