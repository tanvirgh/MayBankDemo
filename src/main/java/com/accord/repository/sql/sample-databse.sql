/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.10 : Database - account-database
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`account_database` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `account_database`;

/*Table structure for table `accounts` */

DROP TABLE IF EXISTS `accounts`;

CREATE TABLE `accounts` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `version` bigint(20) DEFAULT NULL,
                            `account_number` varchar(255) DEFAULT NULL,
                            `customer_id` varchar(255) DEFAULT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            `tran_amount` decimal(38,2) DEFAULT NULL,
                            `tran_date` date DEFAULT NULL,
                            `tran_time` time DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `accounts` */

/*Table structure for table `batch_job_execution` */

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION`;

CREATE TABLE `BATCH_JOB_EXECUTION` (
                                       `JOB_EXECUTION_ID` bigint(20) NOT NULL,
                                       `JOB_INSTANCE_ID` bigint(20) NOT NULL,
                                       `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       `END_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
                                       `STATUS` varchar(10) NOT NULL,
                                       `EXIT_CODE` varchar(10) NOT NULL,
                                       `EXIT_MESSAGE` varchar(5000) DEFAULT NULL,
                                       `VERSION` bigint(20) NOT NULL,
                                       `CREATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
                                       `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
                                       PRIMARY KEY (`JOB_EXECUTION_ID`),
                                       KEY `JOB_INSTANCE_ID` (`JOB_INSTANCE_ID`),
                                       CONSTRAINT `BATCH_JOB_EXECUTION_ibfk_1` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Modify start time column to accept null value` */
ALTER TABLE BATCH_JOB_EXECUTION MODIFY COLUMN START_TIME DATETIME NULL;
ALTER TABLE BATCH_JOB_EXECUTION MODIFY COLUMN END_TIME DATETIME NULL;

/*Data for the table `batch_job_execution` */

/*Table structure for table `batch_job_execution_context` */

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_CONTEXT`;

CREATE TABLE `BATCH_JOB_EXECUTION_CONTEXT` (
                                               `JOB_EXECUTION_ID` bigint(20) NOT NULL,
                                               `SHORT_CONTEXT` varchar(255) DEFAULT NULL,
                                               `SERIALIZED_CONTEXT` blob,
                                               KEY `JOB_EXECUTION_ID` (`JOB_EXECUTION_ID`),
                                               CONSTRAINT `BATCH_JOB_EXECUTION_CONTEXT_ibfk_1` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `batch_job_execution_context` */

/*Table structure for table `batch_job_execution_params` */

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_PARAMS`;

CREATE TABLE `BATCH_JOB_EXECUTION_PARAMS` (
                                              `JOB_EXECUTION_ID` bigint(20) NOT NULL,
                                              `PARAMETER_NAME` varchar(100) NOT NULL,
                                              `PARAMETER_TYPE` varchar(100) NOT NULL,
                                              `PARAMETER_VALUE` varchar(250) DEFAULT NULL,
                                              `IDENTIFYING` char(1) NOT NULL,
                                              PRIMARY KEY (`JOB_EXECUTION_ID`,`PARAMETER_NAME`),
                                              CONSTRAINT `BATCH_JOB_EXECUTION_PARAMS_ibfk_1` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `batch_job_execution_params` */

insert  into `BATCH_JOB_EXECUTION_PARAMS`(`JOB_EXECUTION_ID`,`PARAMETER_NAME`,`PARAMETER_TYPE`,`PARAMETER_VALUE`,`IDENTIFYING`) values (2,'timestamp','java.lang.Long','1732102048556','Y'),(3,'timestamp','java.lang.Long','1732102053966','Y'),(4,'timestamp','java.lang.Long','1732102396617','Y'),(5,'timestamp','java.lang.Long','1732102499174','Y'),(6,'timestamp','java.lang.Long','1732102503828','Y'),(7,'timestamp','java.lang.Long','1732102570369','Y'),(8,'timestamp','java.lang.Long','1732102761802','Y'),(9,'timestamp','java.lang.Long','1732102856766','Y'),(10,'timestamp','java.lang.Long','1732103074295','Y'),(11,'timestamp','java.lang.Long','1732103241074','Y'),(12,'timestamp','java.lang.Long','1732104440180','Y'),(13,'timestamp','java.lang.Long','1732104729316','Y'),(14,'timestamp','java.lang.Long','1732105011139','Y'),(15,'timestamp','java.lang.Long','1732105382481','Y'),(16,'timestamp','java.lang.Long','1732105728284','Y'),(17,'timestamp','java.lang.Long','1732105797613','Y'),(18,'timestamp','java.lang.Long','1732160496529','Y'),(19,'timestamp','java.lang.Long','1732160830341','Y'),(20,'timestamp','java.lang.Long','1732160933870','Y'),(21,'timestamp','java.lang.Long','1732161107025','Y'),(22,'timestamp','java.lang.Long','1732162007301','Y'),(23,'timestamp','java.lang.Long','1732162327571','Y'),(24,'timestamp','java.lang.Long','1732162873388','Y'),(25,'timestamp','java.lang.Long','1732163190100','Y'),(26,'timestamp','java.lang.Long','1732163196811','Y'),(27,'timestamp','java.lang.Long','1732163540327','Y'),(28,'timestamp','java.lang.Long','1732163545814','Y'),(29,'timestamp','java.lang.Long','1732163955057','Y'),(30,'timestamp','java.lang.Long','1732163960349','Y'),(31,'timestamp','java.lang.Long','1732163997465','Y'),(32,'timestamp','java.lang.Long','1732164059405','Y'),(33,'timestamp','java.lang.Long','1732164099351','Y'),(34,'timestamp','java.lang.Long','1732164174603','Y'),(35,'timestamp','java.lang.Long','1732164730539','Y'),(36,'timestamp','java.lang.Long','1732164827300','Y'),(37,'timestamp','java.lang.Long','1732165286883','Y'),(38,'timestamp','java.lang.Long','1732165562625','Y'),(39,'timestamp','java.lang.Long','1732165589967','Y');

/*Table structure for table `batch_job_execution_seq` */

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_SEQ`;

CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
                                           `ID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `batch_job_execution_seq` */

insert  into `BATCH_JOB_EXECUTION_SEQ`(`ID`) values (39);

/*Table structure for table `batch_job_instance` */

DROP TABLE IF EXISTS `BATCH_JOB_INSTANCE`;

CREATE TABLE `BATCH_JOB_INSTANCE` (
                                      `JOB_INSTANCE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `JOB_NAME` varchar(100) NOT NULL,
                                      `JOB_KEY` varchar(100) NOT NULL,
                                      `VERSION` int(11) NOT NULL,
                                      PRIMARY KEY (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `batch_job_instance` */

/*Table structure for table `batch_job_seq` */

DROP TABLE IF EXISTS `BATCH_JOB_SEQ`;

CREATE TABLE `BATCH_JOB_SEQ` (
                                 `ID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `batch_job_seq` */

insert  into `BATCH_JOB_SEQ`(`ID`) values (39);

/*Table structure for table `batch_step_execution` */

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION`;

CREATE TABLE `BATCH_STEP_EXECUTION` (
                                        `STEP_EXECUTION_ID` bigint(20) NOT NULL,
                                        `VERSION` bigint(20) NOT NULL,
                                        `STEP_NAME` varchar(100) NOT NULL,
                                        `JOB_EXECUTION_ID` bigint(20) NOT NULL,
                                        `START_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        `END_TIME` datetime DEFAULT NULL,
                                        `STATUS` varchar(10) DEFAULT NULL,
                                        `COMMIT_COUNT` bigint(20) DEFAULT NULL,
                                        `READ_COUNT` bigint(20) DEFAULT NULL,
                                        `FILTER_COUNT` bigint(20) DEFAULT NULL,
                                        `WRITE_COUNT` bigint(20) DEFAULT NULL,
                                        `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
                                        `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
                                        `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
                                        `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
                                        `EXIT_CODE` varchar(2500) DEFAULT NULL,
                                        `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
                                        `LAST_UPDATED` datetime DEFAULT NULL,
                                        `CREATE_TIME` datetime NOT NULL,
                                        PRIMARY KEY (`STEP_EXECUTION_ID`),
                                        KEY `JOB_EXECUTION_ID` (`JOB_EXECUTION_ID`),
                                        CONSTRAINT `BATCH_STEP_EXECUTION_ibfk_1` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `batch_step_execution` */

ALTER TABLE BATCH_STEP_EXECUTION MODIFY COLUMN START_TIME DATETIME NULL;
ALTER TABLE BATCH_STEP_EXECUTION MODIFY COLUMN END_TIME DATETIME NULL;

/*Table structure for table `batch_step_execution_context` */

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_CONTEXT`;

CREATE TABLE `BATCH_STEP_EXECUTION_CONTEXT` (
                                                `STEP_EXECUTION_ID` bigint(20) NOT NULL,
                                                `SHORT_CONTEXT` varchar(5000) DEFAULT NULL,
                                                `SERIALIZED_CONTEXT` blob,
                                                KEY `STEP_EXECUTION_ID` (`STEP_EXECUTION_ID`),
                                                CONSTRAINT `BATCH_STEP_EXECUTION_CONTEXT_ibfk_1` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `batch_step_execution_context` */

/*Table structure for table `batch_step_execution_seq` */

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_SEQ`;

CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
                                            `ID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `batch_step_execution_seq` */

insert  into `BATCH_STEP_EXECUTION_SEQ`(`ID`) values (38);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
