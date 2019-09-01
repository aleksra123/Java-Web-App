-- MySQL dump 10.13  Distrib 8.0.12, for osx10.13 (x86_64)
--
-- Host: localhost    Database: cms
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `contacts` (
  `idcontacts` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idcontacts`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'Hollie','Voss','mark@example.com'),(2,'Emerson','Milton','hollie@example.com'),(3,'Healy','Colette','boticario@example.com'),(4,'Brigitte','Cobb','emerson@example.com'),(5,'Elba','Lockhart','healy@example.com'),(6,'Claudio','Engle','brigitte@example.com'),(7,'Dena','Pacheco','elba@example.com'),(8,'Christina','Blake','claudio@example.com'),(9,'Gail','Horton','dena@example.com'),(10,'Orville','Daniel','brasilsp@example.com'),(11,'Rae','Childers','parker@example.com'),(12,'Mildred','Starnes','derbvktqsr@example.com'),(13,'Candice','Carson','qetlyxxogg@example.com'),(14,'Louise','Kelchner','antenas_sul@example.com'),(15,'Emilio','Hutchinson','cblake@example.com'),(16,'Geneva','Underwood','gailh@example.com'),(17,'Heriberto','Rush','orville@example.com'),(18,'Bulrush','Bouchard','post_master@example.com'),(19,'Abigail','Louis','rchilders@example.com'),(20,'Chad','Andrews','buster@example.com'),(21,'Terry','English','user31065@example.com'),(22,'Bell','Snedden','ftsgeolbx@example.com');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modules`
--

DROP TABLE IF EXISTS `modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `modules` (
  `idmodules` int(11) NOT NULL AUTO_INCREMENT,
  `classname` varchar(45) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`idmodules`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modules`
--

LOCK TABLES `modules` WRITE;
/*!40000 ALTER TABLE `modules` DISABLE KEYS */;
INSERT INTO `modules` VALUES (1,'ODSOFT','this is ODSOFT'),(2,'ARQAM','this is ARQAM'),(3,'SIMOV','this is SIMOV'),(4,'Relaxation','this is bliss');
/*!40000 ALTER TABLE `modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `students` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `gender` varchar(45) DEFAULT 'male',
  `birthdate` date DEFAULT NULL,
  `modules_idmodules` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_students_modules_idx1` (`modules_idmodules`),
  CONSTRAINT `fk_students_modules` FOREIGN KEY (`modules_idmodules`) REFERENCES `modules` (`idmodules`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Abigail','Louis','female','1994-03-01',3),(2,'Bell','Snedden','female','1900-01-01',3),(3,'Brigitte','Cobb','female','1900-01-01',1),(4,'Bulrush','Bouchard','male','1990-09-12',1),(5,'Candice','Carson','female','1900-01-02',1),(6,'Chad','Andrews','male','1989-09-12',1),(7,'Christina','Blake','female','1994-09-12',1),(8,'Claudio','Engle','male','1994-09-12',1),(9,'Dena','Pacheco','female','1983-09-12',1),(10,'Elba','Lockhart','female','1994-09-12',1),(11,'Emerson','Milton','male','2008-09-12',2),(12,'Emilio','Hutchinson','male','1978-09-12',2),(13,'Gail','Horton','male','1994-09-12',2),(14,'Geneva','Underwood','female','1994-09-12',2),(15,'Healy','Colette','female','1998-09-12',3),(16,'Heriberto','Rush','male','1994-09-12',3),(17,'Hollie','Voss','female','1994-09-12',3),(18,'Louise','Kelchner','female','2000-09-12',3),(19,'Mildred','Starnes','female','1994-09-12',3),(20,'Orville','Daniel','male','2001-09-12',3),(21,'Rae','Childers','male','1994-09-12',3),(22,'Terry','English','male','2003-09-12',3);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-21 17:11:12
