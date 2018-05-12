CREATE DATABASE  IF NOT EXISTS `ework` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ework`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: ework
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cdata_companyapply`
--

DROP TABLE IF EXISTS `cdata_companyapply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_companyapply` (
  `companyid` varchar(30) NOT NULL,
  `userid` varchar(45) NOT NULL,
  `companynm` varchar(45) DEFAULT NULL,
  `companyexp` varchar(45) DEFAULT NULL,
  `reason` varchar(45) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`companyid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_companyapply`
--

LOCK TABLES `cdata_companyapply` WRITE;
/*!40000 ALTER TABLE `cdata_companyapply` DISABLE KEYS */;
INSERT INTO `cdata_companyapply` VALUES ('0000000001','zhengjintao','ddds','dds','ss','1'),('0000000002','admin','ddddd','ddddddd','ddddddddddddd','1');
/*!40000 ALTER TABLE `cdata_companyapply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdata_companyuser`
--

DROP TABLE IF EXISTS `cdata_companyuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_companyuser` (
  `companyid` varchar(30) NOT NULL,
  `userid` varchar(45) NOT NULL,
  `rolekbn` char(1) DEFAULT NULL,
  `delflg` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`companyid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_companyuser`
--

LOCK TABLES `cdata_companyuser` WRITE;
/*!40000 ALTER TABLE `cdata_companyuser` DISABLE KEYS */;
INSERT INTO `cdata_companyuser` VALUES ('0000000001','zhengjintao','1','0'),('0000000002','admin','1','0');
/*!40000 ALTER TABLE `cdata_companyuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdata_leave`
--

DROP TABLE IF EXISTS `cdata_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_leave` (
  `userid` varchar(30) NOT NULL,
  `companyid` varchar(45) NOT NULL,
  `leavedate` date NOT NULL,
  `year` varchar(4) DEFAULT NULL,
  `month` varchar(2) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userid`,`leavedate`,`companyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_leave`
--

LOCK TABLES `cdata_leave` WRITE;
/*!40000 ALTER TABLE `cdata_leave` DISABLE KEYS */;
INSERT INTO `cdata_leave` VALUES ('12345','0000000001','2018-03-07','2018','03','大姨妈'),('admin','0000000001','2018-02-03','2018','02','未填写理由'),('Angela','0000000001','2018-05-01','2018','05','回国'),('Angela','0000000001','2018-05-02','2018','05','回国'),('Angela','0000000001','2018-05-07','2018','05','回国'),('caiyang','0000000001','2018-03-22','2018','03','帰国'),('caiyang','0000000001','2018-03-23','2018','03','帰国'),('caiyang','0000000001','2018-04-26','2018','04','帰国'),('caiyang','0000000001','2018-04-27','2018','04','帰国'),('caiyang','0000000001','2018-05-01','2018','05','帰国'),('caiyang','0000000001','2018-05-02','2018','05','帰国'),('caiyang','0000000001','2018-05-07','2018','05','帰国'),('system','0000000001','2018-02-06','2018','02','mj'),('system','0000000001','2018-02-07','2018','02','mj'),('system','0000000001','2018-03-07','2018','03','sss'),('system','0000000001','2018-03-13','2018','03','搜索'),('system','0000000001','2018-03-14','2018','03','等'),('system','0000000001','2018-03-15','2018','03','ddd'),('system','0000000001','2018-03-20','2018','03','等'),('system','0000000001','2018-03-21','2018','03','回祖国'),('system','0000000001','2018-03-27','2018','03','dddd'),('system','0000000001','2018-03-28','2018','03','车费反反复复地晚在北京召开新闻'),('system','0000000001','2018-05-02','2018','05','dddd'),('tangjj','0000000001','2018-03-05','2018','05','回国'),('tangjj','0000000001','2018-05-08','2018','05','回国'),('tangjj','0000000001','2018-05-09','2018','05','回国'),('zhabei','0000000001','2018-05-01','2018','05','回国'),('zhabei','0000000001','2018-05-02','2018','05','回国'),('zhengjintao','0000000001','2018-03-12','2018','03','回家'),('zhengjintao','0000000001','2018-03-30','2018','03','回家'),('zhengjintao','0000000001','2018-04-03','2018','04','汇价'),('zhengjintao','0000000001','2018-04-04','2018','04','汇价'),('zhengjintao','0000000001','2018-04-05','2018','04','汇价'),('zhengjintao','0000000001','2018-04-10','2018','04','汇价'),('zhengjintao','0000000001','2018-04-24','2018','04','请假');
/*!40000 ALTER TABLE `cdata_leave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdata_notice`
--

DROP TABLE IF EXISTS `cdata_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_notice` (
  `companyid` varchar(30) NOT NULL,
  `id` varchar(10) NOT NULL,
  `type` varchar(2) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` longtext,
  `createusername` varchar(20) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `delflg` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`,`companyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_notice`
--

LOCK TABLES `cdata_notice` WRITE;
/*!40000 ALTER TABLE `cdata_notice` DISABLE KEYS */;
INSERT INTO `cdata_notice` VALUES ('0000000001','1','2','','                      赏樱花\r\n\r\n时间：2018/3/24 上午\r\n地点：上野公园\r\n\r\n集合地点：上野站中央出口\r\n集合时间：8点\r\n\r\n活动详细：\r\n  8-11点:赏花喝酒\r\n\r\n注意事项：\r\n  各自注意安全，开心出游','系统超级管理员','2018-03-03 20:11:39','0'),('0000000001','10','1','','大家都已完成注册，不再公布管理员账号信息。\r\n\r\n需要追加用户，初始化密码等请联系下列管理员权限人员\r\n李桑、陈桑\r\n\r\n发现BUG或有兴趣参与开发e-work系统开发的同事联系郑或房。\r\n','系统超级管理员','2018-03-22 00:12:42','0'),('0000000001','11','1','','大家都已完成注册，不再公布管理员账号信息。\r\n\r\n需要追加用户，初始化密码等请联系下列管理员权限人员\r\n李桑、陈桑\r\n\r\n发现BUG或有兴趣参与开发e-work系统开发的同事联系郑或房。\r\n','系统超级管理员','2018-03-22 00:15:26','0'),('0000000001','12','1','','大家都已完成注册，不再公布管理员账号信息。\r\n\r\n需要追加用户，初始化密码等请联系下列管理员权限人员\r\n李桑、陈桑\r\n\r\n发现BUG或有兴趣参与开发e-work系统开发的同事联系郑或房。\r\n','系统超级管理员','2018-03-22 00:17:01','0'),('0000000001','13','2','','ce shi','系统超级管理员','2018-04-24 16:49:19','0'),('0000000001','14','1','','通知','系统超级管理员','2018-04-24 16:52:05','0'),('0000000001','2','1','','月底请提交个人勤务报告表。\r\n发送至邮箱xxx@bridge.co.jp','系统超级管理员','2018-03-03 20:13:10','0'),('0000000001','3','2','','                      赏樱花\r\n\r\n时间：2018/3/24 上午\r\n地点：上野公园\r\n\r\n集合地点：上野站中央出口\r\n集合时间：8点\r\n\r\n活动详细：\r\n  8-11点:赏花喝酒\r\n\r\n注意事项：\r\n  各自注意安全，开心出游\r\n\r\n以上纯属虚构','系统超级管理员','2018-03-03 20:18:32','0'),('0000000001','4','1','','月底请提交个人勤务报告表。\r\n发送至邮箱xxx@bridge.co.jp','系统超级管理员','2018-03-04 01:48:23','0'),('0000000001','5','1','','测试\r\n请大家准时填写出勤时间。\r\n发送至邮箱xxx@bridge.co.jp','管理员','2018-03-04 17:03:07','0'),('0000000001','6','2','','                      赏樱花（）\r\n\r\n时间：2018/3/24 上午\r\n地点：上野公园\r\n\r\n集合地点：上野站中央出口\r\n集合时间：8点\r\n\r\n活动详细：\r\n  8-11点:赏花喝酒\r\n\r\n注意事项：\r\n  各自注意安全，开心出游\r\n\r\n以上纯属虚构','管理员','2018-03-04 17:03:20','0'),('0000000001','7','1','','请大家在这周五(3/9)前使用管理员用户自助完成注册，3/9之后将不再公开管理员账号密码，统一由管理员负责追加用户。\r\n\r\n管理员信息\r\n账号：admin\r\n密码：admin\r\n\r\n发现BUG或有兴趣参与开发e-work系统开发的同事联系郑或房。\r\n','系统超级管理员','2018-03-07 19:33:00','0'),('0000000001','8','2','','目前木有活动。','系统超级管理员','2018-03-07 19:34:30','0'),('0000000001','9','1','','大家都已完成注册，不再公布管理员账号信息。\r\n\r\n需要追加用户，初始化密码等请联系下列管理员权限人员\r\n李桑、陈桑\r\n\r\n发现BUG或有兴趣参与开发e-work系统开发的同事联系郑或房。\r\n','管理员','2018-03-08 23:46:46','0');
/*!40000 ALTER TABLE `cdata_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdata_userapply`
--

DROP TABLE IF EXISTS `cdata_userapply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_userapply` (
  `companyid` varchar(30) NOT NULL,
  `userid` varchar(45) NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`companyid`,`userid`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_userapply`
--

LOCK TABLES `cdata_userapply` WRITE;
/*!40000 ALTER TABLE `cdata_userapply` DISABLE KEYS */;
INSERT INTO `cdata_userapply` VALUES ('0000000002','admin','0'),('0000000002','system','0');
/*!40000 ALTER TABLE `cdata_userapply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdata_worktime`
--

DROP TABLE IF EXISTS `cdata_worktime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_worktime` (
  `userid` varchar(30) NOT NULL,
  `companyid` varchar(30) NOT NULL,
  `year` varchar(4) NOT NULL,
  `month` varchar(2) NOT NULL,
  `day` varchar(2) NOT NULL,
  `date` date NOT NULL,
  `begintime` time(6) DEFAULT NULL,
  `endtime` time(6) DEFAULT NULL,
  `worktime` decimal(10,1) DEFAULT NULL,
  `comment` longtext,
  `beginlat` varchar(20) DEFAULT NULL,
  `beginlng` varchar(20) DEFAULT NULL,
  `beginaddr` longtext,
  `endlat` varchar(20) DEFAULT NULL,
  `endlng` varchar(20) DEFAULT NULL,
  `endaddr` longtext,
  PRIMARY KEY (`userid`,`year`,`month`,`day`,`date`,`companyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_worktime`
--

LOCK TABLES `cdata_worktime` WRITE;
/*!40000 ALTER TABLE `cdata_worktime` DISABLE KEYS */;
INSERT INTO `cdata_worktime` VALUES ('102101','0000000001','2018','03','01','2018-03-01','09:30:00.000000','19:30:00.000000',9.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('102101','0000000001','2018','03','02','2018-03-02','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('102101','0000000001','2018','03','05','2018-03-05','09:30:00.000000','20:00:00.000000',9.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('102101','0000000001','2018','03','06','2018-03-06','09:30:00.000000','18:30:00.000000',8.0,'移行\r\n台账对应',NULL,NULL,NULL,NULL,NULL,NULL),('102101','0000000001','2018','03','07','2018-03-07','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('102101','0000000001','2018','03','08','2018-03-08','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('11111','0000000001','2018','03','01','2018-03-01','09:30:00.000000','19:30:00.000000',9.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('12345','0000000001','2018','03','03','2018-03-03','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('Angela','0000000001','2018','03','01','2018-03-01','09:30:00.000000','19:00:00.000000',8.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('Angela','0000000001','2018','03','02','2018-03-02','09:30:00.000000','19:00:00.000000',8.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('Angela','0000000001','2018','03','05','2018-03-05','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('Angela','0000000001','2018','03','06','2018-03-06','09:30:00.000000','19:00:00.000000',8.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('Angela','0000000001','2018','03','07','2018-03-07','11:30:00.000000','18:30:00.000000',6.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('Angela','0000000001','2018','03','08','2018-03-08','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('Angela','0000000001','2018','03','09','2018-03-09','09:30:00.000000','18:00:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('caiyang','0000000001','2018','03','01','2018-03-01','09:30:00.000000','21:00:00.000000',10.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('caiyang','0000000001','2018','03','02','2018-03-02','09:30:00.000000','22:15:00.000000',11.8,'',NULL,NULL,NULL,NULL,NULL,NULL),('caiyang','0000000001','2018','03','05','2018-03-05','09:30:00.000000','21:00:00.000000',10.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('caiyang','0000000001','2018','03','06','2018-03-06','09:30:00.000000','19:30:00.000000',9.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('caiyang','0000000001','2018','03','07','2018-03-07','09:30:00.000000','20:00:00.000000',9.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('caiyang','0000000001','2018','03','08','2018-03-08','09:30:00.000000','20:20:00.000000',9.8,'',NULL,NULL,NULL,NULL,NULL,NULL),('caiyang','0000000001','2018','03','09','2018-03-09','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('liuhe','0000000001','2018','03','05','2018-03-05','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('liuhe','0000000001','2018','03','06','2018-03-06','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('liuhe','0000000001','2018','03','07','2018-03-07','09:00:00.000000','17:30:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('liuhe','0000000001','2018','03','08','2018-03-08','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('liuhe','0000000001','2018','03','09','2018-03-09','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('panmaoyan','0000000001','2018','03','01','2018-03-01','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('panmaoyan','0000000001','2018','03','02','2018-03-02','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('panmaoyan','0000000001','2018','03','05','2018-03-05','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('panmaoyan','0000000001','2018','03','06','2018-03-06','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('panmaoyan','0000000001','2018','03','07','2018-03-07','09:00:00.000000','17:30:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('panmaoyan','0000000001','2018','03','08','2018-03-08','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('system','0000000001','2018','03','10','2018-03-10','10:00:00.000000','17:00:00.000000',6.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('system','0000000001','2018','03','11','2018-03-11','10:00:00.000000','17:00:00.000000',6.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('system','0000000001','2018','03','12','2018-03-12','10:00:00.000000','17:00:00.000000',6.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('system','0000000001','2018','03','23','2018-03-23','10:00:00.000000','17:00:00.000000',6.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('system','0000000001','2018','03','24','2018-03-24','10:00:00.000000','17:00:00.000000',6.0,'','35.83264915990204','139.68366940935266','日本、〒333-0853 埼玉県川口市芝園町３−２','35.83264915990204','139.68366940935266','日本、〒333-0853 埼玉県川口市芝園町３−２'),('system','0000000001','2018','05','11','2018-05-11','10:00:00.000000','17:00:00.000000',6.0,'','','','','','',''),('tangjj','0000000001','2018','03','01','2018-03-01','09:30:00.000000','22:00:00.000000',11.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('tangjj','0000000001','2018','03','02','2018-03-02','10:00:00.000000','22:00:00.000000',11.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('tangjj','0000000001','2018','03','07','2018-03-07','09:30:00.000000','20:00:00.000000',9.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('tangjj','0000000001','2018','03','08','2018-03-08','10:00:00.000000','18:30:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('tiantian','0000000001','2018','03','09','2018-03-09','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('user01','0000000001','2018','03','03','2018-03-03','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('user01','0000000001','2018','03','04','2018-03-04','09:30:00.000000','00:00:00.000000',-10.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('YAOLEI ','0000000001','2018','03','01','2018-03-01','10:15:00.000000','23:30:00.000000',12.2,'',NULL,NULL,NULL,NULL,NULL,NULL),('YAOLEI ','0000000001','2018','03','02','2018-03-02','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('YAOLEI ','0000000001','2018','03','05','2018-03-05','13:00:00.000000','22:30:00.000000',9.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('YAOLEI ','0000000001','2018','03','06','2018-03-06','09:30:00.000000','19:30:00.000000',9.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('YAOLEI ','0000000001','2018','03','07','2018-03-07','10:30:00.000000','20:00:00.000000',8.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('YAOLEI ','0000000001','2018','03','08','2018-03-08','09:30:00.000000','20:30:00.000000',10.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('YAOLEI ','0000000001','2018','03','09','2018-03-09','09:30:00.000000','18:00:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('yuanyuan','0000000001','2018','03','01','2018-03-01','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('yuanyuan','0000000001','2018','03','02','2018-03-02','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('yuanyuan','0000000001','2018','03','05','2018-03-05','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('yuanyuan','0000000001','2018','03','06','2018-03-06','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('yuanyuan','0000000001','2018','03','07','2018-03-07','09:00:00.000000','17:30:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('yuanyuan','0000000001','2018','03','08','2018-03-08','09:00:00.000000','18:15:00.000000',8.2,'',NULL,NULL,NULL,NULL,NULL,NULL),('yuanyuan','0000000001','2018','03','09','2018-03-09','09:00:00.000000','18:15:00.000000',8.2,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhabei','0000000001','2018','03','01','2018-03-01','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhabei','0000000001','2018','03','02','2018-03-02','09:30:00.000000','18:00:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhabei','0000000001','2018','03','05','2018-03-05','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhabei','0000000001','2018','03','06','2018-03-06','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhabei','0000000001','2018','03','07','2018-03-07','09:30:00.000000','19:30:00.000000',9.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhabei','0000000001','2018','03','08','2018-03-08','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhabei','0000000001','2018','03','09','2018-03-09','10:00:00.000000','18:30:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhangxiaoming','0000000001','2018','03','01','2018-03-01','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhangxiaoming','0000000001','2018','03','02','2018-03-02','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhangxiaoming','0000000001','2018','03','05','2018-03-05','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhangxiaoming','0000000001','2018','03','06','2018-03-06','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhangxiaoming','0000000001','2018','03','07','2018-03-07','09:00:00.000000','17:30:00.000000',7.5,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhangxiaoming','0000000001','2018','03','08','2018-03-08','09:00:00.000000','18:00:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','01','23','2018-01-23','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','02','23','2018-02-23','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','03','01','2018-03-01','09:30:00.000000','19:30:00.000000',9.0,'事業部組織ダイアログーテスト',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','03','02','2018-03-02','09:30:00.000000','18:30:00.000000',8.0,'事業部組織ダイアログーテスト',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','03','05','2018-03-05','09:30:00.000000','20:00:00.000000',9.5,'SP-ロール・アカント実装 8時間',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','03','06','2018-03-06','09:30:00.000000','18:30:00.000000',8.0,'SP-ロール・アカント実装 8時間',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','03','07','2018-03-07','09:30:00.000000','18:30:00.000000',8.0,'SP-ロール・アカント実装 8時間',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','03','08','2018-03-08','09:30:00.000000','18:30:00.000000',8.0,'SP-ロール・アカント実装',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','03','09','2018-03-09','09:50:00.000000','18:50:00.000000',8.0,'同上\r\n\r\n\r\n\r\n',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','03','23','2018-03-23','09:30:00.000000','18:30:00.000000',8.0,'',NULL,NULL,NULL,NULL,NULL,NULL),('zhengjintao','0000000001','2018','04','21','2018-04-21','09:30:00.000000','18:30:00.000000',8.0,'','','','','','',''),('zhengjintao','0000000002','2018','04','21','2018-04-21','09:30:00.000000','18:30:00.000000',8.0,'','','','','','','');
/*!40000 ALTER TABLE `cdata_worktime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdate_expenses`
--

DROP TABLE IF EXISTS `cdate_expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdate_expenses` (
  `userid` varchar(30) NOT NULL,
  `companyid` varchar(30) NOT NULL,
  `expdetailno` int(11) NOT NULL,
  `expkbn` varchar(20) NOT NULL,
  `expdate` date DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `month` varchar(2) DEFAULT NULL,
  `stationf` varchar(45) DEFAULT NULL,
  `stationt` varchar(45) DEFAULT NULL,
  `money` decimal(10,0) DEFAULT NULL,
  `notes` varchar(200) DEFAULT NULL,
  `delflg` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`userid`,`companyid`,`expdetailno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='经费报销';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdate_expenses`
--

LOCK TABLES `cdate_expenses` WRITE;
/*!40000 ALTER TABLE `cdate_expenses` DISABLE KEYS */;
INSERT INTO `cdate_expenses` VALUES ('system','0000000001',0,'交通费','2018-03-27','2018','03','这样','在北京举行',111,'这样一来','0'),('system','0000000001',1,'交通费','2018-03-27','2018','03','这样','在北京举行',111,'这样一来','0'),('system','0000000001',2,'其他','2018-03-27','2018','03','','',1122,'d d d d','0'),('system','0000000001',3,'其他','2018-03-27','2018','03','','',1122,'d d d d','0'),('system','0000000001',4,'其他','2018-03-27','2018','03','','',1122,'d d d d','0'),('system','0000000001',5,'交通费','2018-03-27','2018','03','你们是','这样的话',13333,'这样一来饿死我是你','0');
/*!40000 ALTER TABLE `cdate_expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mstr_company`
--

DROP TABLE IF EXISTS `mstr_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mstr_company` (
  `companyid` varchar(30) NOT NULL,
  `companynm` varchar(45) DEFAULT NULL,
  `comapnylbl` varchar(20) DEFAULT NULL,
  `companyexp` longtext,
  `companytype` varchar(45) DEFAULT NULL,
  `companyaddr` varchar(45) DEFAULT NULL,
  `companytel` varchar(45) DEFAULT NULL,
  `delflg` char(1) DEFAULT '0',
  PRIMARY KEY (`companyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mstr_company`
--

LOCK TABLES `mstr_company` WRITE;
/*!40000 ALTER TABLE `mstr_company` DISABLE KEYS */;
INSERT INTO `mstr_company` VALUES ('0000000001','ddd','ddd','ddd','1','','','0'),('0000000002','DDUUM','DDUUM','DDUUM','1','','','0');
/*!40000 ALTER TABLE `mstr_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mstr_user`
--

DROP TABLE IF EXISTS `mstr_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mstr_user` (
  `userid` varchar(30) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `delflg` varchar(1) DEFAULT NULL,
  `begintime` time(4) DEFAULT NULL,
  `endtime` time(4) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL COMMENT '0：超级管理员\n1：管理员\n2：普通用户',
  `authflg` char(1) DEFAULT '2',
  `birthday` date DEFAULT NULL,
  `mail` varchar(50) DEFAULT NULL,
  `openid` varchar(50) DEFAULT NULL,
  `rest` varchar(10) DEFAULT '1',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mstr_user`
--

LOCK TABLES `mstr_user` WRITE;
/*!40000 ALTER TABLE `mstr_user` DISABLE KEYS */;
INSERT INTO `mstr_user` VALUES ('102101','房康明','123456','0','09:30:00.0000','18:30:00.0000','M','2',NULL,NULL,NULL,'1'),('11111','房康明','111111','1','09:30:00.0000','18:30:00.0000','M','2',NULL,NULL,NULL,'1'),('12345','小丸子','111111','1','09:30:00.0000','18:30:00.0000','M','2',NULL,NULL,NULL,'1'),('20180321174606558','用户20180321174606558','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,'','','1'),('20180321174631834','用户20180321174631834','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,'','','1'),('20180321175429385','用户20180321175429385','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,'','','1'),('20180321175448041','用户20180321175448041','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,'','','1'),('20180321175549101','用户20180321175549101','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,'','','1'),('admin','管理员','admin','0','09:30:00.0000','18:30:00.0000','M','1',NULL,'',NULL,'1'),('Angela','高姗','angela','0','09:30:00.0000','18:30:00.0000','F','2',NULL,NULL,NULL,'1'),('caiyang','蔡暘','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,NULL,NULL,'1'),('litengfeng','李滕锋','111111','0','09:30:00.0000','18:30:00.0000','M','1',NULL,NULL,NULL,'1'),('liuhe','刘贺','111111','0','09:00:00.0000','18:00:00.0000','F','2',NULL,NULL,NULL,'1'),('ofXqDwuWaJ-C9emIdcqXZnquKHWo','11','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,'','','1'),('panmaoyan','潘茂艳','111111','0','09:00:00.0000','18:00:00.0000','F','2',NULL,NULL,NULL,'1'),('system','系统超级管理员','system123','0','10:00:00.0000','17:00:00.0000','M','0',NULL,'xiaonei0912@qq.com',NULL,NULL),('tangjj','唐軍杰','111111','0','09:30:00.0000','17:30:00.0000','M','2',NULL,NULL,NULL,'1'),('tiantian','陈捷','111111','0','09:30:00.0000','18:30:00.0000','M','1',NULL,NULL,NULL,'1'),('user01','用户','user01','1','09:30:00.0000','02:22:00.0000','F','2',NULL,NULL,NULL,'1'),('YAOLEI ','姚磊','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,NULL,NULL,'1'),('yuanyuan','袁媛','111111','0','09:00:00.0000','18:00:00.0000','F','2',NULL,NULL,NULL,'1'),('zhabei','查蓓','111111','0','09:30:00.0000','18:30:00.0000','F','2',NULL,NULL,NULL,'1'),('zhangxiaoming','张孝明','111111','0','09:00:00.0000','18:00:00.0000','M','2',NULL,NULL,NULL,'1'),('zhengjintao','郑锦涛','111111','0','09:30:00.0000','18:30:00.0000','M','2',NULL,'xiaonei0912@qq.com','ofXqDwsbmAUZrHh85BuJkBwSpfaA','1');
/*!40000 ALTER TABLE `mstr_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mstr_user_comp`
--

DROP TABLE IF EXISTS `mstr_user_comp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mstr_user_comp` (
  `userid` varchar(30) NOT NULL,
  `companyid` varchar(45) NOT NULL,
  `defaultflg` char(1) DEFAULT NULL,
  `delflg` char(1) DEFAULT NULL,
  PRIMARY KEY (`userid`,`companyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mstr_user_comp`
--

LOCK TABLES `mstr_user_comp` WRITE;
/*!40000 ALTER TABLE `mstr_user_comp` DISABLE KEYS */;
INSERT INTO `mstr_user_comp` VALUES ('admin','0000000002','1','0'),('zhengjintao','0000000001','1','0');
/*!40000 ALTER TABLE `mstr_user_comp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-12 10:51:06
