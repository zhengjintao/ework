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
-- Table structure for table `cdata_leave`
--

DROP TABLE IF EXISTS `cdata_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_leave` (
  `id` varchar(10) NOT NULL,
  `userid` varchar(30) NOT NULL,
  `leavedate` date DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_leave`
--

LOCK TABLES `cdata_leave` WRITE;
/*!40000 ALTER TABLE `cdata_leave` DISABLE KEYS */;
INSERT INTO `cdata_leave` VALUES ('11','zhengjintao','2018-02-01','11'),('2','zhengjintao','2018-02-09','11'),('34','zhengjintao','2018-01-01','11'),('5','fang','2018-02-01','11');
/*!40000 ALTER TABLE `cdata_leave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdata_notice`
--

DROP TABLE IF EXISTS `cdata_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_notice` (
  `id` varchar(10) NOT NULL,
  `type` varchar(2) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` longtext,
  `createusername` varchar(20) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `delflg` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_notice`
--

LOCK TABLES `cdata_notice` WRITE;
/*!40000 ALTER TABLE `cdata_notice` DISABLE KEYS */;
INSERT INTO `cdata_notice` VALUES ('1','1','','                     通知\r\n\r\n今天我要表扬一个同学，他助人为乐！','房康明','2018-02-06 11:28:38','0'),('2','2','','                公司活动\r\n\r\n\r\n地点：xxxxxxxxxx\r\n时间：xxxxxxxxxxxxxxxxxxxx \r\n            xxxxxxxxxxxxxxxxxxxxxx\r\n\r\nPS：大家都参加。不要钱¥¥¥','房康明','2018-02-06 11:29:31','0'),('3','2','','','房康明','2018-02-06 11:29:47','0'),('4','1','','                               通知\r\n\r\n今天我要表扬一个同学，他助人为乐！ver2','房康明','2018-02-24 10:01:47','0'),('5','2','','近期没活动，自己high，自己浪！ver2','房康明','2018-02-24 10:01:58','0'),('6','1','','                               通知\r\n\r\n今天我要表扬一个同学，他助人为乐！ver23','房康明','2018-02-24 10:50:48','0'),('7','2','','近期没活动，自己high，自己浪！ver23333444','房康明','2018-02-24 10:50:55','0'),('8','2','','近期没活动，自己high，自己浪！ver23333444\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns\r\ns','房康明','2018-02-24 10:51:06','0');
/*!40000 ALTER TABLE `cdata_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdata_worktime`
--

DROP TABLE IF EXISTS `cdata_worktime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_worktime` (
  `userid` varchar(30) NOT NULL,
  `year` varchar(4) DEFAULT NULL,
  `month` varchar(2) DEFAULT NULL,
  `day` varchar(2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `begintime` datetime DEFAULT NULL,
  `endtime` datetime DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_worktime`
--

LOCK TABLES `cdata_worktime` WRITE;
/*!40000 ALTER TABLE `cdata_worktime` DISABLE KEYS */;
/*!40000 ALTER TABLE `cdata_worktime` ENABLE KEYS */;
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
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mstr_user`
--

LOCK TABLES `mstr_user` WRITE;
/*!40000 ALTER TABLE `mstr_user` DISABLE KEYS */;
INSERT INTO `mstr_user` VALUES ('fangkangming','房康明','111111','0','08:30:00.0000','17:30:00.0000','M','2'),('system','系统管理员','system','0','08:30:00.0000','17:30:00.0000','F','0'),('zhengjintao','郑锦涛','111111','0','08:30:00.0000','05:59:00.0000','M','2');
/*!40000 ALTER TABLE `mstr_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-24 11:59:16
