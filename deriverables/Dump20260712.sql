-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nuoto_db
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `esercizi_scheda`
--

DROP TABLE IF EXISTS `esercizi_scheda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `esercizi_scheda` (
  `id_esercizio` int NOT NULL AUTO_INCREMENT,
  `id_scheda` varchar(50) NOT NULL,
  `nome` varchar(100) DEFAULT NULL,
  `stile` varchar(50) DEFAULT NULL,
  `distanza` int DEFAULT NULL,
  `info` text,
  PRIMARY KEY (`id_esercizio`),
  KEY `id_scheda` (`id_scheda`),
  CONSTRAINT `esercizi_scheda_ibfk_1` FOREIGN KEY (`id_scheda`) REFERENCES `schede_nuoto` (`id_scheda`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `esercizi_scheda`
--

LOCK TABLES `esercizi_scheda` WRITE;
/*!40000 ALTER TABLE `esercizi_scheda` DISABLE KEYS */;
INSERT INTO `esercizi_scheda` VALUES (12,'01','lavoro cebtrale','dorso',200,'2x100 forti'),(13,'001','riscaldamento','misti',1200,'12x100 misti'),(14,'001','esercizio per lelos','misti',1200,'carico lele'),(17,'25','risc','rana',2000,'tuttop stile'),(19,'354666','yjj','uy',9000,'r5r'),(20,'005','er','hy',344,'efr'),(21,'1999','peppo','ert',234,'no'),(22,'1999','rfrtft','frfr',345,'re'),(23,'102154','riscaldamento','misti',400,'8x50 misti');
/*!40000 ALTER TABLE `esercizi_scheda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `richieste_scheda`
--

DROP TABLE IF EXISTS `richieste_scheda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `richieste_scheda` (
  `id_richiesta` int NOT NULL AUTO_INCREMENT,
  `email_user` varchar(100) NOT NULL,
  `email_istruttore` varchar(100) NOT NULL,
  `livello_utente` varchar(50) DEFAULT NULL,
  `info_aggiuntive` text,
  `stato_richiesta` varchar(30) DEFAULT NULL,
  `data_richiesta` date DEFAULT NULL,
  PRIMARY KEY (`id_richiesta`),
  KEY `email_user` (`email_user`),
  KEY `email_istruttore` (`email_istruttore`),
  CONSTRAINT `richieste_scheda_ibfk_1` FOREIGN KEY (`email_user`) REFERENCES `utenti` (`email`) ON DELETE CASCADE,
  CONSTRAINT `richieste_scheda_ibfk_2` FOREIGN KEY (`email_istruttore`) REFERENCES `utenti` (`email`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=981 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `richieste_scheda`
--

LOCK TABLES `richieste_scheda` WRITE;
/*!40000 ALTER TABLE `richieste_scheda` DISABLE KEYS */;
INSERT INTO `richieste_scheda` VALUES (20,'pato@mail.it','matt@mail.it','miao','miao','ACCETTATA','2026-06-12'),(94,'alices@mail.it','matt@mail.it','base','sono fortissima','ACCETTATA','2026-06-11'),(201,'alices@mail.it','elli@mail.it','BASE','nuoto molto male','INCORSO','2026-06-14'),(204,'elly@maiò.it','matt@mail.it','INTERMEDIO','matteeeeee','ACCETTATA','2026-06-14'),(347,'EliG@mail.it','mattiaercolani@mail.it','INTERMEDIO','Vorrei ricominciare a nuotare dopo un lungo stop.','ACCETTATA','2026-07-08'),(350,'alices@mail.it','matt@mail.it','BASE','sono una sincronetta, nuoto malissimo','ACCETTATA','2026-06-14'),(375,'alices@mail.it','matt@mail.it','pro','ciao','ACCETTATA','2026-06-09'),(379,'alices@mail.it','matt@mail.it','BASE','hh','ACCETTATA','2026-07-04'),(399,'elly@maiò.it','matt@mail.it','pro','sono fortissima','ACCETTATA','2026-06-09'),(483,'alices@mail.it','Peppog@mail.it','BASE','voglio migliorare il delfino','ACCETTATA','2026-06-14'),(492,'alices@mail.it','matt@mail.it','INTERMEDIO','ciao','ACCETTATA','2026-07-01'),(597,'AliceSar@mail.it','mattiaercolani@mail.it','INTERMEDIO','Voglio una scheda per migliorare il delfino.','INCORSO','2026-07-08'),(647,'alices@mail.it','matt@mail.it','AVANZATO','w','ACCETTATA','2026-07-04'),(675,'alices@mail.it','matt@mail.it','base','er','ACCETTATA','2026-07-07'),(692,'alices@mail.it','matt@mail.it','pro','sono scarsa','ACCETTATA','2026-06-09'),(713,'Elly@mail.it','mattiaercolani@mail.it','INTERMEDIO','Sto preparando una gara di triathlon, devo migliorare la resistenza.','INCORSO','2026-07-08'),(717,'alices@mail.it','matt@mail.it','AVANZATO','u','ACCETTATA','2026-07-07'),(744,'eli@mail.com','matt@mail.it','base','sono una sincronette, non so nuotare bene','RIFIUTATA','2026-06-12'),(796,'elly@maiò.it','matt@mail.it','INTERMEDIO','vogliuo migliorare lo stile liberro','ACCETTATA','2026-06-14'),(854,'alices@mail.it','matt@mail.it','INTERMEDIO','devo migliorare la velocità','ACCETTATA','2026-06-24'),(882,'MattTrab@mail.it','mattiaercolani@mail.it','AVANZATO','Voglio una scheda per migliorare la velocità.','INCORSO','2026-07-08'),(970,'wan@mail.it','matt@mail.it','AVANZATO','','ACCETTATA','2026-07-04'),(980,'alices@mail.it','matt@mail.it','INTERMEDIO','Vorrei migliorare il dorso','ACCETTATA','2026-06-14');
/*!40000 ALTER TABLE `richieste_scheda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schede_nuoto`
--

DROP TABLE IF EXISTS `schede_nuoto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schede_nuoto` (
  `id_scheda` varchar(50) NOT NULL,
  `distanza_totale` int DEFAULT NULL,
  `durata` int DEFAULT NULL,
  `livello` varchar(50) DEFAULT NULL,
  `id_richiesta` int DEFAULT NULL,
  `email_istruttore` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_scheda`),
  UNIQUE KEY `id_richiesta` (`id_richiesta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schede_nuoto`
--

LOCK TABLES `schede_nuoto` WRITE;
/*!40000 ALTER TABLE `schede_nuoto` DISABLE KEYS */;
INSERT INTO `schede_nuoto` VALUES ('001',2500,60,'AVANZATO',2,'Peppog@mail.it'),('005',1000,120,'INTERMEDIO',3,'matt@mail.it'),('01',2500,80,'INTERMEDIO',1,'matt@mail.it'),('102154',2000,60,'INTERMEDIO',10,'mattiaercolani@mail.it'),('12234',2500,100,'INTERMEDIO',5,'matt@mail.it'),('1999',123,123,'base',9,'matt@mail.it'),('25',2000,50,'INTERMEDIO',4,'matt@mail.it'),('256',122344,120,'AVANZATO',6,'matt@mail.it'),('354666',32434,4555,'INTERMEDIO',7,'matt@mail.it'),('525252525',44,45,'BASE',8,'matt@mail.it');
/*!40000 ALTER TABLE `schede_nuoto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utenti` (
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nome` varchar(50) DEFAULT NULL,
  `cognome` varchar(50) DEFAULT NULL,
  `ruolo` tinyint(1) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `level` varchar(50) DEFAULT NULL,
  `certificate` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES ('','','0','',0,NULL,NULL,NULL),('4f4f4f4f','r54t5y6','r4tty6yj','true',1,NULL,NULL,NULL),('alex@mail.it','alex','alessio','conti',0,22,'BASE',''),('alices@mail.it','pato','alice','saracino',0,NULL,NULL,NULL),('AliceSar@mail.it','alix','Alice','Saracino',0,19,'INTERMEDIO',''),('asd@mail.it','asdrubale','Asdrubale','annibale',0,23,'INTERMEDIO',''),('asdr@mail.it','asd','asdrubale','rossi',1,23,'','fiun'),('eijridr','ciao','ercp','erft',1,NULL,NULL,NULL),('eli@mail.com','elisa','Elisa','Garofolo',0,22,'base',''),('eli@mail.it','asdrubale','elisa','garofolo',1,22,'','Istruttore nuoto FIN'),('EliG@mail.it','eli','Elisa','Garofolo',0,22,'INTERMEDIO',''),('elli@mail.it','ciao','elisa ','garofolo',1,22,'','nulla'),('Elly@mail.it','elly','Eliana Vanessa','De Angelis',0,23,'INTERMEDIO',''),('elly@maiò.it','mattee','eliana ','de angelis',0,NULL,NULL,NULL),('frat@mail.it','fra','francesco','totti',1,40,'','zero'),('gian@mail.it','gian','gian','pier',1,23,'','null'),('i2hu3hd3h','hello','mattix','peppix',0,NULL,NULL,NULL),('mar@mail.it','mario','mario','rossi',0,20,'BASE',''),('mare@mail.it','mari','mari','mari',0,22,'INTERMEDIO',''),('marior@mail.it','rossi','Mario','Rossi',0,45,'INTERMEDIO',''),('matt@mail.it','ciao','mattia','erco',1,NULL,NULL,NULL),('mattiaercolani@mail.it','mattia','Mattia','Ercolani',1,26,'','Istruttore Nuoto FIN 1° Livello'),('mattmail.it','ciao','Mattia','ededfe',1,33,'','nulla'),('mattt@mail.com','matte','matteo','trabassi ',0,NULL,NULL,NULL),('MattTrab@mail.it','matt','Matteo','Trabassi',0,24,'AVANZATO',''),('pato@mail.it','paton','Pato','Patone',0,99,'AVANZATO',''),('pe@mail.it','pep','pepp','p',0,45,'BASE',''),('Peppog@mail.it','peppog','Peppo','Gagliante',1,35,'','Istruttore nuoto FIN 1° livello'),('wan@mail.it','ciao','Wanda','Romanelli',0,64,'AVANZATO','');
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-12 16:11:43
