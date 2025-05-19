-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: la_fioreria
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `bouquet`
--

DROP TABLE IF EXISTS `bouquet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bouquet` (
  `Bouquet_ID` int NOT NULL AUTO_INCREMENT,
  `bouquet_name` varchar(45) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Bouquet_ID`),
  UNIQUE KEY `Bouquet_ID_UNIQUE` (`Bouquet_ID`),
  KEY `created_by_idx` (`created_by`),
  CONSTRAINT `created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bouquet`
--

LOCK TABLES `bouquet` WRITE;
/*!40000 ALTER TABLE `bouquet` DISABLE KEYS */;
INSERT INTO `bouquet` VALUES (1,'Romantic Rose','2025-08-01','2025-08-15',3,'A bouquet of fresh roses'),(2,'Spring Delight','2025-08-05','2025-08-20',3,'Colorful tulips and lilies'),(3,'Sunny Love','2025-08-10','2025-08-25',3,'Sunflowers and roses for a cheerful gift');
/*!40000 ALTER TABLE `bouquet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bouquet_raw`
--

DROP TABLE IF EXISTS `bouquet_raw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bouquet_raw` (
  `bouquet_id` int NOT NULL,
  `raw_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`bouquet_id`,`raw_id`),
  KEY `raw_flower_id_idx` (`raw_id`),
  CONSTRAINT `bouquet_raw_id` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`),
  CONSTRAINT `raw_flower_id` FOREIGN KEY (`raw_id`) REFERENCES `raw_flower` (`raw_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bouquet_raw`
--

LOCK TABLES `bouquet_raw` WRITE;
/*!40000 ALTER TABLE `bouquet_raw` DISABLE KEYS */;
INSERT INTO `bouquet_raw` VALUES (1,1,10),(2,2,5),(2,3,3),(3,1,2),(3,5,4);
/*!40000 ALTER TABLE `bouquet_raw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `method`
--

DROP TABLE IF EXISTS `method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `method` (
  `Method_id` int NOT NULL AUTO_INCREMENT,
  `method_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Method_id`),
  UNIQUE KEY `Method_id_UNIQUE` (`Method_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `method`
--

LOCK TABLES `method` WRITE;
/*!40000 ALTER TABLE `method` DISABLE KEYS */;
INSERT INTO `method` VALUES (1,'Cash'),(2,'QR Payment');
/*!40000 ALTER TABLE `method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_date` varchar(45) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `total_amount` varchar(45) DEFAULT NULL,
  `status_id` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `idnew_table_UNIQUE` (`order_id`),
  KEY `customer_id_idx` (`customer_id`),
  KEY `status_id_idx` (`status_id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `status_id` FOREIGN KEY (`status_id`) REFERENCES `order_status` (`order_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'2025-08-10',7,'250000',1),(2,'2025-08-11',8,'110000',2);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `bouquet_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit_price` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`order_item_id`),
  UNIQUE KEY `order_item_id_UNIQUE` (`order_item_id`),
  KEY `order_id_idx` (`order_id`),
  KEY `bouquet_id_idx` (`bouquet_id`),
  CONSTRAINT `bouquet_id` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,1,1,250000),(2,2,3,1,110000);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_status` (
  `order_status_id` int NOT NULL AUTO_INCREMENT,
  `status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`order_status_id`),
  UNIQUE KEY `order_status_id_UNIQUE` (`order_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_status`
--

LOCK TABLES `order_status` WRITE;
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
INSERT INTO `order_status` VALUES (1,'Pending'),(2,'Processing'),(3,'Shipped'),(4,'Delivered'),(5,'Cancelled');
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `processed_by` int DEFAULT NULL,
  `amount_paid` decimal(10,0) DEFAULT NULL,
  `method_id` int DEFAULT NULL,
  `payment_status_id` int DEFAULT NULL,
  UNIQUE KEY `payment_id_UNIQUE` (`payment_id`),
  KEY `order-id_idx` (`order_id`),
  KEY `customer_id_idx` (`customer_id`),
  KEY `processed_by_idx` (`processed_by`),
  KEY `method_idx` (`method_id`),
  KEY `payment_status_id_idx` (`payment_status_id`),
  CONSTRAINT `customer` FOREIGN KEY (`customer_id`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `method` FOREIGN KEY (`method_id`) REFERENCES `method` (`Method_id`),
  CONSTRAINT `order-id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  CONSTRAINT `payment_status_id` FOREIGN KEY (`payment_status_id`) REFERENCES `payment_status` (`payment_status_id`),
  CONSTRAINT `processed_by` FOREIGN KEY (`processed_by`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (21,1,7,1,250000,1,1),(22,2,8,1,110000,2,2);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_status`
--

DROP TABLE IF EXISTS `payment_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_status` (
  `payment_status_id` int NOT NULL AUTO_INCREMENT,
  `Status_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`payment_status_id`),
  UNIQUE KEY `payment_status_id_UNIQUE` (`payment_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_status`
--

LOCK TABLES `payment_status` WRITE;
/*!40000 ALTER TABLE `payment_status` DISABLE KEYS */;
INSERT INTO `payment_status` VALUES (1,'Unpaid'),(2,'Paid'),(3,'Refunded');
/*!40000 ALTER TABLE `payment_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raw_flower`
--

DROP TABLE IF EXISTS `raw_flower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `raw_flower` (
  `raw_id` int NOT NULL AUTO_INCREMENT,
  `raw_name` varchar(45) DEFAULT NULL,
  `raw_quantity` varchar(45) DEFAULT NULL,
  `unit_price` decimal(10,0) DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `Warehouse_id` int DEFAULT NULL,
  PRIMARY KEY (`raw_id`),
  UNIQUE KEY `raw_id_UNIQUE` (`raw_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raw_flower`
--

LOCK TABLES `raw_flower` WRITE;
/*!40000 ALTER TABLE `raw_flower` DISABLE KEYS */;
INSERT INTO `raw_flower` VALUES (1,'Rose','500',25000,'2025-08-31',1),(2,'Tulip','300',50000,'2025-08-25',1),(3,'Lily','200',26000,'2025-08-28',1),(4,'Orchid','100',80000,'2025-08-30',2),(5,'Sunflower','150',15000,'2025-08-29',2);
/*!40000 ALTER TABLE `raw_flower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `Role_id` int NOT NULL AUTO_INCREMENT,
  `Role_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Role_id`),
  UNIQUE KEY `Role_id_UNIQUE` (`Role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'Sales Manager'),(3,'Seller'),(4,'Marketer'),(5,'Warehouse Staff'),(6,'Guest'),(7,'Customer');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
  `Shipment_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `depart_time` date DEFAULT NULL,
  `arrival_time` date DEFAULT NULL,
  `distance` decimal(10,0) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `depart_from` varchar(45) DEFAULT NULL,
  `depart_to` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Shipment_id`),
  UNIQUE KEY `Shipment_id_UNIQUE` (`Shipment_id`),
  KEY `order_id_idx` (`order_id`),
  CONSTRAINT `shipemt_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
INSERT INTO `shipment` VALUES (1,1,'2025-05-11','2025-05-12',11,1,'Main Warehouse','303 Customer Place'),(2,2,'2025-05-12','2025-05-13',12,1,'Backup Warehouse','404 Rose Street');
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `User_ID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Fullname` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Phone` varchar(10) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `Role` int NOT NULL,
  PRIMARY KEY (`User_ID`),
  UNIQUE KEY `User_ID_UNIQUE` (`User_ID`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  KEY `Role_idx` (`Role`),
  CONSTRAINT `Role` FOREIGN KEY (`Role`) REFERENCES `role` (`Role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin01','admin123','Alice Admin','alice@flower.com','0901111001','123 Admin Street',1),(2,'manager01','manager123','Bob Manager','bob@flower.com','0901111002','456 Manager Lane',2),(3,'seller01','seller123','Carol Seller','carol@flower.com','0901111003','789 Seller Road',3),(4,'marketer01','marketing1','David Marketer','david@flower.com','0901111004','101 Marketing Blvd',4),(5,'warehouse01','store123','Eva Warehouse','eva@flower.com','0901111005','202 Warehouse Ave',5),(6,'guest01','guest123','Frank Guest','frank@flower.com','0901111006','No Address',6),(7,'cust01','cust123','Grace Customer','grace@flower.com','0901111007','303 Customer Place',7),(8,'cust02','cust456','Helen Buyer','helen@flower.com','0901111008','404 Rose Street',7);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `Warehouse_ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `Manager_id` int DEFAULT NULL,
  PRIMARY KEY (`Warehouse_ID`),
  UNIQUE KEY `Warehouse_ID_UNIQUE` (`Warehouse_ID`),
  KEY `manager_id_idx` (`Manager_id`),
  CONSTRAINT `manager_id` FOREIGN KEY (`Manager_id`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,'Main Warehouse','111 Warehouse St',5),(2,'Backup Warehouse','222 Storage Rd',5);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-17 17:03:27
