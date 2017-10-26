CREATE DATABASE  IF NOT EXISTS `monitoresami` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `monitoresami`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: monitoresami
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `donatore`
--

DROP TABLE IF EXISTS `donatore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donatore` (
  `email` varchar(45) NOT NULL,
  `gruppoSanguigno` varchar(45) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donatore`
--

LOCK TABLES `donatore` WRITE;
/*!40000 ALTER TABLE `donatore` DISABLE KEYS */;
INSERT INTO `donatore` VALUES ('benigno.pisani@gmail.com','0-POS'),('virgilio.schiavone@gmail.com','AB-NEG');
/*!40000 ALTER TABLE `donatore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donazione`
--

DROP TABLE IF EXISTS `donazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donazione` (
  `idDonazione` int(11) NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `donatore` varchar(45) NOT NULL,
  PRIMARY KEY (`idDonazione`),
  KEY `donatore_idx` (`donatore`),
  CONSTRAINT `donatore_FK` FOREIGN KEY (`donatore`) REFERENCES `donatore` (`email`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donazione`
--

LOCK TABLES `donazione` WRITE;
/*!40000 ALTER TABLE `donazione` DISABLE KEYS */;
INSERT INTO `donazione` VALUES (1,'2017-07-20','benigno.pisani@gmail.com'),(2,'2017-07-05','benigno.pisani@gmail.com'),(3,'2017-05-26','benigno.pisani@gmail.com'),(4,'2017-06-13','benigno.pisani@gmail.com'),(5,'2017-07-05','virgilio.schiavone@gmail.com'),(6,'2017-06-23','virgilio.schiavone@gmail.com'),(7,'2017-04-10','virgilio.schiavone@gmail.com');
/*!40000 ALTER TABLE `donazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risultatoesame`
--

DROP TABLE IF EXISTS `risultatoesame`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `risultatoesame` (
  `esame` varchar(45) NOT NULL,
  `risultato` double DEFAULT NULL,
  `unitaMisura` varchar(45) DEFAULT NULL,
  `riferimentoMin` double DEFAULT NULL,
  `riferimentoMax` double DEFAULT NULL,
  `donazione` int(11) NOT NULL,
  PRIMARY KEY (`esame`,`donazione`),
  KEY `idDonazione_FK_idx` (`donazione`),
  CONSTRAINT `idDonazione_FK` FOREIGN KEY (`donazione`) REFERENCES `donazione` (`idDonazione`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risultatoesame`
--

LOCK TABLES `risultatoesame` WRITE;
/*!40000 ALTER TABLE `risultatoesame` DISABLE KEYS */;
INSERT INTO `risultatoesame` VALUES ('acido urico',5.5,'mg/dL',2.5,7.2,1),('acido urico',4,'mg/dL',2.5,7.2,2),('acido urico',7,'mg/dL',2.5,7.2,3),('acido urico',2.8,'mg/dL',2.5,7.2,4),('acido urico',5.2,'mg/dL',2.5,7.2,5),('acido urico',5.5,'mg/dL',2.5,7.2,6),('acido urico',3.8,'mg/dL',2.5,7.2,7),('alanina aminotransferasi',23,'U/L',10,35,1),('alanina aminotransferasi',74,'U/L',10,35,2),('alanina aminotransferasi',54,'U/L',10,35,3),('alanina aminotransferasi',32,'U/L',10,35,4),('alanina aminotransferasi',31,'U/L',10,35,5),('alanina aminotransferasi',23,'U/L',10,35,6),('alanina aminotransferasi',29,'U/L',10,35,7),('albumina',4.5,'g/dL',3.5,5.5,1),('albumina',3.5,'g/dL',3.5,5.5,2),('albumina',5,'g/dL',3.5,5.5,3),('albumina',4.5,'g/dL',3.5,5.5,4),('albumina',3.8,'g/dL',3.5,5.5,5),('albumina',4.5,'g/dL',3.5,5.5,6),('albumina',4.1,'g/dL',3.5,5.5,7),('amilasi',44,'U/L',25,125,1),('amilasi',57,'U/L',25,125,2),('amilasi',97,'U/L',25,125,3),('amilasi',99,'U/L',25,125,4),('amilasi',27,'U/L',25,125,5),('amilasi',44,'U/L',25,125,6),('amilasi',63,'U/L',25,125,7),('aspartato aminotransferasi',34,'U/L',10,40,1),('aspartato aminotransferasi',25,'U/L',10,40,2),('aspartato aminotransferasi',13,'U/L',10,40,3),('aspartato aminotransferasi',21,'U/L',10,40,4),('aspartato aminotransferasi',35,'U/L',10,40,5),('aspartato aminotransferasi',34,'U/L',10,40,6),('aspartato aminotransferasi',24,'U/L',10,40,7),('bilirubina diretta',0.17,'mg/dL',0,0.5,1),('bilirubina diretta',0.15,'mg/dL',0,0.5,2),('bilirubina diretta',0.45,'mg/dL',0,0.5,3),('bilirubina diretta',0.43,'mg/dL',0,0.5,4),('bilirubina diretta',0.21,'mg/dL',0,0.5,5),('bilirubina diretta',0.17,'mg/dL',0,0.5,6),('bilirubina diretta',0.36,'mg/dL',0,0.5,7),('bilirubina totale',0.35,'mg/dL',0,1,1),('bilirubina totale',1,'mg/dL',0,1,2),('bilirubina totale',0.32,'mg/dL',0,1,3),('bilirubina totale',0.8,'mg/dL',0,1,4),('bilirubina totale',0.94,'mg/dL',0,1,5),('bilirubina totale',0.35,'mg/dL',0,1,6),('bilirubina totale',0.37,'mg/dL',0,1,7),('cloro',103,'mmol/L',99,109,1),('cloro',99,'mmol/L',99,109,2),('cloro',108,'mmol/L',99,109,3),('cloro',101,'mmol/L',99,109,4),('cloro',100,'mmol/L',99,109,5),('cloro',103,'mmol/L',99,109,6),('cloro',107,'mmol/L',99,109,7),('colesterolo totale ',196,'mg/dL',50,210,1),('colesterolo totale ',210,'mg/dL',50,210,2),('colesterolo totale ',135,'mg/dL',50,210,3),('colesterolo totale ',86,'mg/dL',50,210,4),('colesterolo totale ',90,'mg/dL',50,210,5),('colesterolo totale ',196,'mg/dL',50,210,6),('colesterolo totale ',119,'mg/dL',50,210,7),('colesterolo-HDL',35,'mg/dL',40,60,1),('colesterolo-HDL',45,'mg/dL',40,60,2),('colesterolo-HDL',65,'mg/dL',40,60,3),('colesterolo-HDL',54,'mg/dL',40,60,4),('colesterolo-HDL',51,'mg/dL',40,60,5),('colesterolo-HDL',35,'mg/dL',40,60,6),('colesterolo-HDL',46,'mg/dL',40,60,7),('colesterolo-LDL',133,'mg/dL',0,160,1),('colesterolo-LDL',54,'mg/dL',0,160,2),('colesterolo-LDL',23,'mg/dL',0,160,3),('colesterolo-LDL',16,'mg/dL',0,160,4),('colesterolo-LDL',64,'mg/dL',0,160,5),('colesterolo-LDL',133,'mg/dL',0,160,6),('colesterolo-LDL',122,'mg/dL',0,160,7),('colinesterasi',8305,'U/L',4000,12600,1),('colinesterasi',4652,'U/L',4000,12600,2),('colinesterasi',7653,'U/L',4000,12600,3),('colinesterasi',5555,'U/L',4000,12600,4),('colinesterasi',10000,'U/L',4000,12600,5),('colinesterasi',8305,'U/L',4000,12600,6),('colinesterasi',4321,'U/L',4000,12600,7),('creatin chinasi',54,'U/L',40,160,1),('creatin chinasi',34,'U/L',40,160,2),('creatin chinasi',54,'U/L',40,160,3),('creatin chinasi',49,'U/L',40,160,4),('creatin chinasi',78,'U/L',40,160,5),('creatin chinasi',54,'U/L',40,160,6),('creatin chinasi',69,'U/L',40,160,7),('creatinina',1.3,'mg/dL',0.7,1.2,1),('creatinina',0.8,'mg/dL',0.7,1.2,2),('creatinina',0.76,'mg/dL',0.7,1.2,3),('creatinina',1.1,'mg/dL',0.7,1.2,4),('creatinina',1,'mg/dL',0.7,1.2,5),('creatinina',1.3,'mg/dL',0.7,1.2,6),('creatinina',0.8,'mg/dL',0.7,1.2,7),('ferro',42,'microg/dL',35,156,1),('ferro',54,'microg/dL',35,156,2),('ferro',39,'microg/dL',35,156,3),('ferro',42,'microg/dL',35,156,4),('ferro',152,'microg/dL',35,156,5),('ferro',42,'microg/dL',35,156,6),('ferro',123,'microg/dL',35,156,7),('fosfatasi alcalina',86,'U/L',40,150,1),('fosfatasi alcalina',65,'U/L',40,150,2),('fosfatasi alcalina',44,'U/L',40,150,3),('fosfatasi alcalina',89,'U/L',40,150,4),('fosfatasi alcalina',76,'U/L',40,150,5),('fosfatasi alcalina',86,'U/L',40,150,6),('fosfatasi alcalina',94,'U/L',40,150,7),('fosforo ingorganico ',3.5,'mg/dL',2.7,4.5,1),('fosforo ingorganico ',2.8,'mg/dL',2.7,4.5,2),('fosforo ingorganico ',4.2,'mg/dL',2.7,4.5,3),('fosforo ingorganico ',3.5,'mg/dL',2.7,4.5,4),('fosforo ingorganico ',3.1,'mg/dL',2.7,4.5,5),('fosforo ingorganico ',3.5,'mg/dL',2.7,4.5,6),('fosforo ingorganico ',4.4,'mg/dL',2.7,4.5,7),('glucosio',74,'mg/dL',70,105,1),('glucosio',86,'mg/dL',70,105,2),('glucosio',105,'mg/dL',70,105,3),('glucosio',78,'mg/dL',70,105,4),('glucosio',95,'mg/dL',70,105,5),('glucosio',74,'mg/dL',70,105,6),('glucosio',88,'mg/dL',70,105,7),('lettato-deidrogenasi',172,'U/L',100,195,1),('lettato-deidrogenasi',105,'U/L',100,195,2),('lettato-deidrogenasi',160,'U/L',100,195,3),('lettato-deidrogenasi',155,'U/L',100,195,4),('lettato-deidrogenasi',152,'U/L',100,195,5),('lettato-deidrogenasi',172,'U/L',100,195,6),('lettato-deidrogenasi',128,'U/L',100,195,7),('lipasi',31,'U/L',8,78,1),('lipasi',21,'U/L',8,78,2),('lipasi',14,'U/L',8,78,3),('lipasi',64,'U/L',8,78,4),('lipasi',48,'U/L',8,78,5),('lipasi',31,'U/L',8,78,6),('lipasi',68,'U/L',8,78,7),('potassio',4.3,'mmol/L',3.5,5.1,1),('potassio',4.1,'mmol/L',3.5,5.1,2),('potassio',4.6,'mmol/L',3.5,5.1,3),('potassio',4.1,'mmol/L',3.5,5.1,4),('potassio',4.2,'mmol/L',3.5,5.1,5),('potassio',4.3,'mmol/L',3.5,5.1,6),('potassio',4.2,'mmol/L',3.5,5.1,7),('proteine totali',8,'g/dL',6.4,8.3,1),('proteine totali',8.1,'g/dL',6.4,8.3,2),('proteine totali',7.2,'g/dL',6.4,8.3,3),('proteine totali',8.2,'g/dL',6.4,8.3,4),('proteine totali',7.7,'g/dL',6.4,8.3,5),('proteine totali',8,'g/dL',6.4,8.3,6),('proteine totali',7.5,'g/dL',6.4,8.3,7),('sodio',137,'mmol/L',136,145,1),('sodio',136,'mmol/L',136,145,2),('sodio',140,'mmol/L',136,145,3),('sodio',139,'mmol/L',136,145,4),('sodio',142,'mmol/L',136,145,5),('sodio',137,'mmol/L',136,145,6),('sodio',140,'mmol/L',136,145,7),('trigliceridi',238,'mg/dL',30,200,1),('trigliceridi',210,'mg/dL',30,200,2),('trigliceridi',190,'mg/dL',30,200,3),('trigliceridi',185,'mg/dL',30,200,4),('trigliceridi',163,'mg/dL',30,200,5),('trigliceridi',238,'mg/dL',30,200,6),('trigliceridi',184,'mg/dL',30,200,7),('urea ',15,'mg/dL',10,50,1),('urea ',47,'mg/dL',10,50,2),('urea ',32,'mg/dL',10,50,3),('urea ',26,'mg/dL',10,50,4),('urea ',27,'mg/dL',10,50,5),('urea ',15,'mg/dL',10,50,6),('urea ',42,'mg/dL',10,50,7);
/*!40000 ALTER TABLE `risultatoesame` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-24 15:51:51
