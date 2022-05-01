CREATE DATABASE  IF NOT EXISTS `ID343381_g29rummikub` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ID343381_g29rummikub`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: ID343381_g29rummikub.db.webhosting.be    Database: ID343381_g29rummikub
-- ------------------------------------------------------
-- Server version	5.7.33-36-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Speler`
--

DROP TABLE IF EXISTS `Speler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Speler` (
  `gebruikersnaam` mediumtext NOT NULL,
  `wachtwoord` mediumtext NOT NULL,
  `score` varchar(100) NOT NULL,
  `totaalscore` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='eerste tabel';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Speler`
--

LOCK TABLES `Speler` WRITE;
/*!40000 ALTER TABLE `Speler` DISABLE KEYS */;
INSERT INTO `Speler` VALUES ('Tom','1234','0  148',148),('Sam','Wachtwoord','0  0',0),('Robbe','test123','0',0),('Maarten','zeker_weten','0',0);
/*!40000 ALTER TABLE `Speler` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-16 14:35:55
