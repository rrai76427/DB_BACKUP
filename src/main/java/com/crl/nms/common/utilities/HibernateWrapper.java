///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.crl.nms.common.utilities;
//
//
//import com.crl.nms.databases.hibernateUtil;
//import java.util.List;
//
////import org.apache.log4j.Logger;
//import jakarta.persistence.Query;
//import org.hibernate.HibernateException;
//import org.hibernate.JDBCException;
////import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.exception.ConstraintViolationException;
//import org.hibernate.exception.DataException;
//import org.hibernate.exception.GenericJDBCException;
//import org.hibernate.exception.JDBCConnectionException;
//import org.hibernate.exception.SQLGrammarException;
//
///**
// *
// * @author Munish Khatana
// */
//public class HibernateWrapper {
//
////  private final Logger //m_logger;
//    private SessionFactory sessionFac;
//    private Session session;
//   // private static final Logger logger = Logger.getLogger(HibernateWrapper.class);
//
//    /**
//     * Default constructor, Opens a new hibernate session for database
//     * transactions.
//     */
//    public HibernateWrapper() {
//
//        if (Global.s_dbDriver == null) {
//            Global.s_dbDriver = new Configuration().configure().getProperty("hibernate.connection.driver_class").toLowerCase();
//            if (Global.s_dbDriver.contains("mysql")) {
//                Global.s_dbSchema = new Configuration().configure().getProperty("hibernate.connection.url").split("mysql://")[1];
//
//                Global.s_dbSchema = Global.s_dbSchema.replace("/", "::");
//                Global.s_dbSchema = Global.s_dbSchema.replace("?", "::");
//                Global.s_dbSchema = Global.s_dbSchema.split("::")[1];
//
//            }
//
//        }
//        setDBProperties();
//        sessionFac = hibernateUtil.getSessionFactory();
//        session = sessionFac.openSession();
//
//    }
//
//    public void setDBProperties() {
//
//        Configuration cfg = new Configuration();
//        Global.dbUrl = new StringBuilder("jdbc:oracle:thin:@").append(Global.dbIp).append(":").
//                append(Global.dbPort).append(":").append(Global.dbSID).toString();
//
//    }
//
//    /**
//     * @return the sessionFac
//     */
//    public SessionFactory getSessionFac() {
//        return sessionFac;
//    }
//
//    /**
//     * @return the session
//     */
//    public Session getSession() {
//        return session;
//    }
//
//    /**
//     * Destroys the database hibernate session once the operation is complete.
//     */
//    public void destroy() {
//        try {
//            getSession().close();
//
//            //    getSession().clear();
//            //    getSession().flush();
//        } catch (NullPointerException e) {
//
//            // logger.info("------------NullPointerException : " + e.getMessage());
//        } catch (HibernateException e) {
//
//        }
//
//    }
//
//    /**
//     * Inserts the database table object into the corresponding DB table.
//     *
//     * @param obj
//     * @return
//     */
//    public boolean insertHqlObject(Object obj) {
//        try {
//            getSession().clear();
//            getSession().beginTransaction();
//            getSession().saveOrUpdate(obj);
//            getSession().getTransaction().commit();
//            getSession().clear();
//        } catch (DataException e) {
//            //logger.info("------------DataException : " + e.getMessage());
//
//            return false;
//        } catch (SQLGrammarException e) {
//            //logger.info("------------SQLGrammarException : " + e.getMessage());
//
//            return false;
//        } catch (ConstraintViolationException e) {
//            //logger.info("------------ConstraintViolationException : " + e.getMessage());
//            return false;
//        } catch (GenericJDBCException e) {
//            //logger.info("------------GenericJDBCException : " + e.getMessage());
//            if (e.getErrorCode() == 1194 && e.getSQLState().equals("HY000")) {
//                //  repair Table
//            }
//
//            return false;
//        } catch (JDBCConnectionException e) {
//            //logger.info("------------JDBCConnectionException : " + e.getMessage());
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return insertHqlObject(obj);
//        } catch (JDBCException e) {
//            //logger.info("------------JDBCException : " + e.getMessage());
//
//            return false;
//        } catch (HibernateException e) {
//
//            //logger.info("------------HibernateException : " + e.getMessage());
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return insertHqlObject(obj);
//        }
//        return true;
//    }
//
//    /**
//     *
//     * Executes the HQL query for insertion, deletion or updation of the DB
//     * table.
//     *
//     * @param query
//     * @return
//     */
//    public boolean executeHqlQuery(Query query) {
//        try {
//            getSession().clear();
//            getSession().beginTransaction();
//           // query.executeUpdate();
//            getSession().getTransaction().commit();
//            getSession().clear();
//        } catch (DataException e) {
//            //logger.info("------------DataException : " + e.getMessage());
//
//            return false;
//        } catch (SQLGrammarException e) {
//            //logger.info("------------SQLGrammarException : " + e.getMessage());
//
//            return false;
//        } catch (ConstraintViolationException e) {
//            //logger.info("------------ConstraintViolationException : " + e.getMessage());
//            return false;
//        } catch (GenericJDBCException e) {
//            //logger.info("------------GenericJDBCException : " + e.getMessage());
//
//            return false;
//        } catch (JDBCConnectionException e) {
//            //logger.info("------------JDBCConnectionException : " + e.getMessage());
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return executeHqlQuery(query);
//        } catch (JDBCException e) {
//            //logger.info("------------JDBCException : " + e.getMessage());
//
//            return false;
//        } catch (HibernateException e) {
//            //logger.info("------------HibernateException : " + e.getMessage());
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return executeHqlQuery(query);
//        }
//        return true;
//    }
//
//    /**
//     *
//     * Executes the SQL query for insertion, deletion or updation of the DB
//     * table.
//     *
//     * @param query
//     * @return
//     */
//    public boolean executeSqlQuery(String query) {
//        try {
//            getSession().clear();
//            getSession().beginTransaction();
//           // getSession().createSQLQuery(query).executeUpdate();
//            getSession().getTransaction().commit();
//            getSession().clear();
//        } catch (DataException e) {
//            //logger.info("------------DataException : " + e.getMessage());
//
//            return false;
//        } catch (SQLGrammarException e) {
//            //logger.info("------------SQLGrammarException : " + e.getMessage());
//
//            return false;
//        } catch (ConstraintViolationException e) {
//            //logger.info("------------ConstraintViolationException : " + e.getMessage());
//            return false;
//        } catch (GenericJDBCException e) {
//            //logger.info("------------GenericJDBCException : " + e.getMessage());
//
//            return false;
//        } catch (JDBCConnectionException e) {
//            //logger.info("------------JDBCConnectionException : " + e.getMessage());
//            System.exit(0);
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return executeSqlQuery(query);
//        } catch (JDBCException e) {
//            //logger.info("------------JDBCException : " + e.getMessage());
//
//            return false;
//        } catch (HibernateException e) {
//           // logger.info("------------HibernateException : " + e.getMessage());
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return executeSqlQuery(query);
//
//        }
//        return true;
//    }
//
//    /**
//     *
//     * Executes the HQL query to get the result set from the database.
//     *
//     * @param query
//     * @return
//     */
//    public List selectHQLQuery(String query) {
//        List resultList = null;
//        try {
//            getSession().clear();
//            getSession().beginTransaction();
//          // Query q = getSession().createQuery(query);
//            //resultList = q.list();
//            getSession().getTransaction().commit();
//            getSession().clear();
//        } catch (SQLGrammarException e) {
//           // logger.info("------------SQLGrammarException : " + e.getMessage());
//
//        } catch (ConstraintViolationException e) {
//            //logger.info("------------ConstraintViolationException : " + e.getMessage());
//        } catch (GenericJDBCException e) {
//            //logger.info("------------GenericJDBCException : " + e.getMessage());
//
//        } catch (JDBCConnectionException e) {
//            //logger.info("------------JDBCConnectionException : " + e.getMessage());
//            System.exit(0);
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return selectHQLQuery(query);
//        } catch (JDBCException e) {
//           // logger.info("------------JDBCException : " + e.getMessage());
//
//        } catch (HibernateException e) {
//            //logger.info("------------HibernateException : " + e.getMessage());
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return selectHQLQuery(query);
//        }
//        return resultList;
//    }
//
//    /**
//     *
//     * Executes the SQL query to get the result set from the database.
//     *
//     * @param query
//     * @return
//     */
//    public List selectSqlQuery(String query) {
//        List resultList = null;
//        try {
//            getSession().clear();
//            getSession().beginTransaction();
//            //Query q = getSession().createSQLQuery(query);
//           // resultList = q.list();
//            getSession().getTransaction().commit();
//            getSession().clear();
//        } catch (SQLGrammarException e) {
//           // logger.info("------------SQLGrammarException : " + e.getMessage());
//            System.out.println("------------SQLGrammarException : " + e.getMessage());
//
//        } catch (ConstraintViolationException | GenericJDBCException e) {
//           // logger.info("------------ConstraintViolationException : " + e.getMessage());
//
//        } catch (JDBCConnectionException e) {
//           // logger.info("------------JDBCConnectionException : " + e.getMessage());
//            //   System.exit(0);
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return selectSqlQuery(query);
//        } catch (JDBCException e) {
//            //logger.info("------------JDBCException : " + e.getMessage());
//
//        } catch (HibernateException e) {
//            //logger.info("------------HibernateException : " + e.getMessage());
//            destroy();
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//            sessionFac = hibernateUtil.getSessionFactory();
//            session = getSessionFac().openSession();
//            return selectSqlQuery(query);
//        }
//        return resultList;
//    }
//}
