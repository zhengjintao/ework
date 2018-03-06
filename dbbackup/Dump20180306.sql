CREATE DATABASE  IF NOT EXISTS `ework` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ework`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 157.7.138.167    Database: ework
-- ------------------------------------------------------
-- Server version	5.6.32

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
  `userid` varchar(30) NOT NULL,
  `leavedate` date NOT NULL,
  `year` varchar(4) DEFAULT NULL,
  `month` varchar(2) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userid`,`leavedate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_leave`
--

LOCK TABLES `cdata_leave` WRITE;
/*!40000 ALTER TABLE `cdata_leave` DISABLE KEYS */;
INSERT INTO `cdata_leave` VALUES ('12345','2018-03-07','2018','03','大姨妈'),('admin','2018-02-03','2018','02','未填写理由');
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
INSERT INTO `cdata_notice` VALUES ('1','2','','                      赏樱花\r\n\r\n时间：2018/3/24 上午\r\n地点：上野公园\r\n\r\n集合地点：上野站中央出口\r\n集合时间：8点\r\n\r\n活动详细：\r\n  8-11点:赏花喝酒\r\n\r\n注意事项：\r\n  各自注意安全，开心出游','系统超级管理员','2018-03-03 20:11:39','0'),('2','1','','月底请提交个人勤务报告表。\r\n发送至邮箱xxx@bridge.co.jp','系统超级管理员','2018-03-03 20:13:10','0'),('3','2','','                      赏樱花\r\n\r\n时间：2018/3/24 上午\r\n地点：上野公园\r\n\r\n集合地点：上野站中央出口\r\n集合时间：8点\r\n\r\n活动详细：\r\n  8-11点:赏花喝酒\r\n\r\n注意事项：\r\n  各自注意安全，开心出游\r\n\r\n以上纯属虚构','系统超级管理员','2018-03-03 20:18:32','0'),('4','1','','月底请提交个人勤务报告表。\r\n发送至邮箱xxx@bridge.co.jp','系统超级管理员','2018-03-04 01:48:23','0'),('5','1','','测试\r\n请大家准时填写出勤时间。\r\n发送至邮箱xxx@bridge.co.jp','管理员','2018-03-04 17:03:07','0'),('6','2','','                      赏樱花（）\r\n\r\n时间：2018/3/24 上午\r\n地点：上野公园\r\n\r\n集合地点：上野站中央出口\r\n集合时间：8点\r\n\r\n活动详细：\r\n  8-11点:赏花喝酒\r\n\r\n注意事项：\r\n  各自注意安全，开心出游\r\n\r\n以上纯属虚构','管理员','2018-03-04 17:03:20','0');
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
  `year` varchar(4) NOT NULL,
  `month` varchar(2) NOT NULL,
  `day` varchar(2) NOT NULL,
  `date` date NOT NULL,
  `begintime` time(6) DEFAULT NULL,
  `endtime` time(6) DEFAULT NULL,
  `worktime` varchar(5) DEFAULT NULL,
  `comment` longtext,
  PRIMARY KEY (`userid`,`year`,`month`,`day`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_worktime`
--

LOCK TABLES `cdata_worktime` WRITE;
/*!40000 ALTER TABLE `cdata_worktime` DISABLE KEYS */;
INSERT INTO `cdata_worktime` VALUES ('102101','2018','03','01','2018-03-01','09:30:00.000000','19:30:00.000000','9.0',''),('102101','2018','03','02','2018-03-02','09:30:00.000000','18:30:00.000000','8.0',''),('102101','2018','03','05','2018-03-05','09:30:00.000000','20:00:00.000000','9.5',''),('102101','2018','03','06','2018-03-06','09:30:00.000000','18:30:00.000000','8.0','移行\r\n台账对应'),('11111','2018','03','01','2018-03-01','09:30:00.000000','19:30:00.000000','9.0',''),('12345','2018','03','03','2018-03-03','09:30:00.000000','18:30:00.000000','8.0',''),('admin','2018','03','03','2018-03-03','09:30:00.000000','17:30:00.000000','7.0',''),('admin','2018','03','04','2018-03-04','09:30:00.000000','17:30:00.000000','7.0',''),('admin','2018','03','05','2018-03-05','09:00:00.000000','18:00:00.000000','8.0',''),('admin','2018','03','07','2018-03-07','09:30:00.000000','17:30:00.000000','7.0',''),('liuhe','2018','03','05','2018-03-05','09:30:00.000000','18:30:00.000000','8.0',''),('user01','2018','03','03','2018-03-03','09:30:00.000000','18:30:00.000000','8.0',''),('user01','2018','03','04','2018-03-04','09:30:00.000000','00:00:00.000000','-10.5',''),('YAOLEI ','2018','03','05','2018-03-05','13:00:00.000000','22:30:00.000000','9.5',''),('yuanyuan','2018','03','01','2018-03-01','09:00:00.000000','18:00:00.000000','8.0',''),('yuanyuan','2018','03','02','2018-03-02','09:00:00.000000','18:00:00.000000','8.0',''),('yuanyuan','2018','03','05','2018-03-05','09:00:00.000000','18:00:00.000000','8.0',''),('zhangxiaoming','2018','03','01','2018-03-01','09:00:00.000000','18:00:00.000000','8.0',''),('zhangxiaoming','2018','03','02','2018-03-02','09:00:00.000000','18:00:00.000000','8.0',''),('zhangxiaoming','2018','03','05','2018-03-05','09:00:00.000000','18:00:00.000000','8.0',''),('zhengjintao','2018','03','01','2018-03-01','09:30:00.000000','19:30:00.000000','9.0',''),('zhengjintao','2018','03','02','2018-03-02','09:30:00.000000','18:30:00.000000','8.0',''),('zhengjintao','2018','03','05','2018-03-05','09:30:00.000000','20:00:00.000000','9.5','SP実装'),('zhengjintao','2018','03','06','2018-03-06','09:30:00.000000','18:30:00.000000','8.0','SP実装 6時間');
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
INSERT INTO `mstr_user` VALUES ('102101','房康明1','123456','0','09:30:00.0000','18:30:00.0000','M','2'),('11111','房康明','111111','1','09:30:00.0000','18:30:00.0000','M','2'),('12345','小丸子','111111','0','09:30:00.0000','18:30:00.0000','M','2'),('admin','管理员','admin','0','09:30:00.0000','18:30:00.0000','M','1'),('caiyang','蔡暘','111111','0','09:30:00.0000','18:30:00.0000','M','2'),('liuhe','刘贺','111111','0','09:30:00.0000','18:30:00.0000','F','2'),('system','系统超级管理员','system123','0','10:00:00.0000','17:00:00.0000','M','0'),('user01','用户','user01','0','09:30:00.0000','02:22:00.0000','F','2'),('YAOLEI ','姚磊','111111','0','09:30:00.0000','18:30:00.0000','M','2'),('yuanyuan','袁媛','111111','0','09:00:00.0000','18:00:00.0000','F','2'),('zhangxiaoming','张孝明','111111','0','09:30:00.0000','18:30:00.0000','M','2'),('zhengjintao','郑锦涛','111111','0','09:30:00.0000','18:30:00.0000','M','2');
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

-- Dump completed on 2018-03-06 23:58:49
