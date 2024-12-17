
package com.crl.nms.common.utilities;

import akka.actor.ActorRef;
import com.crl.nms.messages.CronMessage;

import java.util.concurrent.ConcurrentHashMap;

public class Global {


  public static final String CREATE_BACKP="create_backup";

  public static final String GET_DBBACKUP_LIST="get_dbbackup_list";

  public static final String GET_DB_IMPORT="getdbimport";
  public static CronMessage cronMessage;


}
