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
-- Table structure for table `blog`
--

DROP TABLE IF EXISTS `blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog` (
  `blog_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `context` text,
  `author_id` int NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `cid` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`blog_id`),
  UNIQUE KEY `blog_id_UNIQUE` (`blog_id`),
  KEY `author_id_idx` (`author_id`),
  KEY `cid_idx` (`cid`),
  CONSTRAINT `author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `blog_cid` FOREIGN KEY (`cid`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
INSERT INTO `blog` VALUES (1,'6 Tips to Keep Your Flowers Fresh','Learn how to maintain your flowers for longer...',2,'2025-05-19 07:54:53','2025-05-19 07:54:53',6,'https://jardincaribe.com/wp-content/uploads/2020/12/Infograpic-6-tips.png'),(2,'The Meaning Behind Popular Flowers','Discover the symbolism of roses, lilies, and more...',4,'2025-05-19 07:54:53','2025-05-19 07:54:53',7,'https://flourishbakingcompany.com/cdn/shop/articles/chart_of_flowers_800x.jpg?v=1580648030'),(3,'How to Choose the Perfect Bouquet for Any Occasion','A guide to picking the right flowers...',4,'2025-05-19 07:54:53','2025-05-19 07:54:53',7,'https://cdn.shopify.com/s/files/1/2776/7900/files/Blog_10.jpg?v=1691462989');
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bouquet`
--

DROP TABLE IF EXISTS `bouquet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bouquet` (
  `Bouquet_ID` int NOT NULL AUTO_INCREMENT,
  `bouquet_name` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `cid` int NOT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`Bouquet_ID`),
  UNIQUE KEY `Bouquet_ID_UNIQUE` (`Bouquet_ID`),
  KEY `cid_idx` (`cid`),
  CONSTRAINT `bouquet_cid` FOREIGN KEY (`cid`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bouquet`
--

LOCK TABLES `bouquet` WRITE;
/*!40000 ALTER TABLE `bouquet` DISABLE KEYS */;
INSERT INTO `bouquet` VALUES (1,'Romantic Rose','A bouquet of fresh roses','https://www.clareflorist.co.uk/images/product/obsession-36-red-roseslg.jpg',1,250000),(2,'Spring Delight','Colorful tulips and lilies','https://res.cloudinary.com/dizexseir/image/upload/v1638189470/ProImages/gk6kebe1ojjyxdgv0ok3.jpg',2,328000),(3,'Sunny Love','Sunflowers and roses for a cheerful gift','https://giftflowers.com.hk/media/catalog/product/cache/81b20432be5bed9a616e6ab3bf908e57/f/b/fb150004_2_cheerful_sunflowers_bouquet_2_2_res.jpg',5,110000),(4,'Rose Radiance','A vibrant bouquet of fresh red roses symbolizing love and passion.','https://summerswintersg.com/cdn/shop/products/image_fd2696a5-aa10-47d1-bf2c-2c33a7e65989.jpg?v=1706926878&width=713',1,500000),(5,'Lily Light','Elegant lilies arranged to brighten up any occasion.','https://cdn.thefloristmarket.com/cdn/images/vendoritem/thefloristmarket-scentales-28573-30243_1080x1080.jpg',2,130000),(6,'Sunflower Smile','Cheerful sunflowers to bring joy and energy.','https://hoathangtu.com/wp-content/uploads/2022/11/IMG_6004-scaled.jpg',3,120000),(7,'Tulip Treasure','An exotic bouquet of multicolored tulips.','https://hoatuoimymy.com/wp-content/uploads/2024/09/bohoa15.jpg',4,500000),(8,'Orchid Elegance','Luxurious white orchids in an elegant arrangement.','https://flowersight.com/wp-content/uploads/2024/10/bo-hoa-lan-trang-2.jpg',5,1200000),(9,'Sun Dreams','A cheerful bouquet featuring sunflowers and baby\'s breath.','https://hobbyfloristkl.com/wp-content/uploads/2021/01/Sunny-Baby-sunflower-bouquet-selangor.png',1,345000),(10,'Peony Passion','Romantic peonies paired with soft accents.','https://fafulflorist.com/cdn/shop/files/lasting-love-peony-fafulflorist-896234.jpg?v=1716431923',2,700000),(11,'Carnation Bliss','Elegant mix of carnations and daisies.','https://www.dpsainiflorist.com/wp-content/uploads/2022/07/DP09260.jpg',2,720000),(12,'Hydrangea Harmony','Soothing tones with hydrangeas and baby\'s breath.','https://thebloombox.sg/cdn/shop/files/whisper-blue-hydrangea-baby-breath-flower-bouquet-01.jpg?v=1728608783',3,450000);
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
INSERT INTO `bouquet_raw` VALUES (1,1,10),(2,2,5),(2,3,3),(3,1,2),(3,5,4),(4,1,20),(5,3,5),(6,5,8),(7,2,10),(8,4,15),(9,5,3),(9,10,10),(10,7,7),(11,6,12),(11,8,10),(12,9,3),(12,10,10);
/*!40000 ALTER TABLE `bouquet_raw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartdetails`
--

DROP TABLE IF EXISTS `cartdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartdetails` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `bouquet_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`cart_id`),
  UNIQUE KEY `cart_id_UNIQUE` (`cart_id`),
  KEY `customer_id_idx` (`customer_id`),
  KEY `bouquet_id_idx` (`bouquet_id`),
  CONSTRAINT `cart_bouquet_id` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`),
  CONSTRAINT `cart_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartdetails`
--

LOCK TABLES `cartdetails` WRITE;
/*!40000 ALTER TABLE `cartdetails` DISABLE KEYS */;
INSERT INTO `cartdetails` VALUES (1,7,1,2),(2,7,3,1),(3,8,2,4),(4,8,1,1);
/*!40000 ALTER TABLE `cartdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_id_UNIQUE` (`category_id`),
  UNIQUE KEY `category_name_UNIQUE` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Romantic','Flowers meant to express love, ideal for Valentine\'s Day, proposals, or anniversaries.'),(2,'Birthday','Bouquets designed to celebrate someone’s birthday with joy and color.'),(3,'Congratulations','Arrangements to celebrate milestones like graduations, promotions, or achievements.'),(4,'Sympathy','Sympathy flowers to offer comfort during times of loss or hardship.'),(5,'Anniversary','Bouquets for celebrating wedding anniversaries and long-lasting love.'),(6,'Tips & Tricks','Flower care tips and tricks'),(7,'Occasions','Blogs about special occasions and flower meanings'),(8,'New Arrivals','News about new bouquets and flowers');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
  `contact_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `subject` varchar(150) DEFAULT NULL,
  `message` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'Tân Vũ','vuminhtan2004@gmail.com','lead','123','2025-05-22 23:45:27'),(2,'Tân Vũ','vuminhtan2004@gmail.com','lead','avavxvx','2025-05-22 23:46:19'),(3,'Tân Vũ','vuminhtan2004@gmail.com','lead','11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111','2025-05-22 23:46:51');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flower_batch`
--

DROP TABLE IF EXISTS `flower_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flower_batch` (
  `batch_id` int NOT NULL AUTO_INCREMENT,
  `flower_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `import_price` int DEFAULT NULL,
  `import_date` date DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `hold` int DEFAULT NULL,
  PRIMARY KEY (`batch_id`),
  UNIQUE KEY `batch_id_UNIQUE` (`batch_id`),
  KEY `flower_id_idx` (`flower_id`),
  CONSTRAINT `flower_id` FOREIGN KEY (`flower_id`) REFERENCES `flower_type` (`flower_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flower_batch`
--

LOCK TABLES `flower_batch` WRITE;
/*!40000 ALTER TABLE `flower_batch` DISABLE KEYS */;
INSERT INTO `flower_batch` VALUES (1,1,500,15000,'2025-07-01','2025-08-31',NULL),(2,2,300,35000,'2025-07-01','2025-08-25',NULL),(3,3,200,16000,'2025-07-01','2025-08-28',NULL),(4,4,100,55000,'2025-07-01','2025-08-30',NULL),(5,5,150,10000,'2025-07-01','2025-08-29',NULL),(6,6,400,7000,'2025-07-01','2025-07-05',NULL),(7,7,250,70000,'2025-07-01','2025-07-10',NULL),(8,8,350,40000,'2025-07-01','2025-07-03',NULL),(9,9,200,35000,'2025-07-01','2025-07-07',NULL),(10,10,500,20000,'2025-07-01','2025-07-08',NULL),(11,11,0,30000,'2025-07-01','2025-07-06',0);
/*!40000 ALTER TABLE `flower_batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flower_type`
--

DROP TABLE IF EXISTS `flower_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flower_type` (
  `flower_id` int NOT NULL AUTO_INCREMENT,
  `flower_name` varchar(45) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `warehouse_id` int NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`flower_id`),
  UNIQUE KEY `flower_id_UNIQUE` (`flower_id`),
  KEY `warehouse_id_idx` (`warehouse_id`),
  CONSTRAINT `warehouse_id` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`Warehouse_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flower_type`
--

LOCK TABLES `flower_type` WRITE;
/*!40000 ALTER TABLE `flower_type` DISABLE KEYS */;
INSERT INTO `flower_type` VALUES (1,'Rose','https://flooers.co.uk/wp-content/uploads/2023/02/3CC66EEC-0B20-4AC4-B2FD-E82A4734E596.jpeg',1,_binary ''),(2,'Tulip','https://mobileimages.lowes.com/productimages/06095299-423a-422e-af57-7b50460097b3/02989030.jpg?size=pdhz',1,_binary ''),(3,'Lily','https://flowerexplosion.com/cdn/shop/products/white-asiatic-lily_900x.jpg?v=1658372981',1,_binary ''),(4,'Orchid','https://ahsam.imgix.net/assets/img/dictionary/orchid-blue-wall.jpg?w=450&dpr=2',2,_binary ''),(5,'Sunflower','https://www.highmowingseeds.com/media/catalog/product/cache/95cbc1bb565f689da055dd93b41e1c28/7/1/7100-1.jpg',2,_binary ''),(6,'Daisy','https://www.cumbriawildflowers.co.uk/image/data/products/Leucanthemum-vulgare2.jpg',1,_binary ''),(7,'Peony','https://bizweb.dktcdn.net/thumb/grande/100/442/027/products/thie-t-ke-chu-a-co-te-n-1-png-dd9b3fd9-4e4c-4bdf-ba60-136fbbe6fc55.jpg?v=1713328120593',1,_binary ''),(8,'Carnation','https://premierseedsdirect.com/wp-content/uploads/2017/02/Carnation-Scarlet-Red-2-scaled.jpg',2,_binary ''),(9,'Hydrangea','https://ecolafa.com/wp-content/uploads/2021/10/hoa-cam-tu-cau.png',2,_binary ''),(10,'Baby\'s Breath','https://www.bloombybunches.ca/cdn/shop/files/babys-breath-gypsophila-white-million-star-diy-wedding-events-720.webp?v=1740864684',1,_binary ''),(11,'Lavender','https://charsawfarms.com/cdn/shop/files/PurpleBouquetlavender2.jpg?v=1710207668&width=1946',1,_binary '');
/*!40000 ALTER TABLE `flower_type` ENABLE KEYS */;
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
  `image_url` varchar(255) DEFAULT NULL,
  `hold` int DEFAULT NULL,
  `import_price` int DEFAULT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`raw_id`),
  UNIQUE KEY `raw_id_UNIQUE` (`raw_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raw_flower`
--

LOCK TABLES `raw_flower` WRITE;
/*!40000 ALTER TABLE `raw_flower` DISABLE KEYS */;
INSERT INTO `raw_flower` VALUES (1,'Rose','500',25000,'2025-08-31',1,'https://flooers.co.uk/wp-content/uploads/2023/02/3CC66EEC-0B20-4AC4-B2FD-E82A4734E596.jpeg',NULL,15000,_binary ''),(2,'Tulip','300',50000,'2025-08-25',1,'https://mobileimages.lowes.com/productimages/06095299-423a-422e-af57-7b50460097b3/02989030.jpg?size=pdhz',NULL,35000,_binary ''),(3,'Lily','200',26000,'2025-08-28',1,'https://flowerexplosion.com/cdn/shop/products/white-asiatic-lily_900x.jpg?v=1658372981',NULL,16000,_binary ''),(4,'Orchid','100',80000,'2025-08-30',2,'https://ahsam.imgix.net/assets/img/dictionary/orchid-blue-wall.jpg?w=450&dpr=2',NULL,55000,_binary ''),(5,'Sunflower','150',15000,'2025-08-29',2,'https://www.highmowingseeds.com/media/catalog/product/cache/95cbc1bb565f689da055dd93b41e1c28/7/1/7100-1.jpg',NULL,10000,_binary ''),(6,'Daisy','400',10000,'2025-07-05',1,'https://www.cumbriawildflowers.co.uk/image/data/products/Leucanthemum-vulgare2.jpg',NULL,7000,_binary ''),(7,'Peony','250',100000,'2025-07-10',1,'https://bizweb.dktcdn.net/thumb/grande/100/442/027/products/thie-t-ke-chu-a-co-te-n-1-png-dd9b3fd9-4e4c-4bdf-ba60-136fbbe6fc55.jpg?v=1713328120593',NULL,70000,_binary ''),(8,'Carnation','350',60000,'2025-07-03',2,'https://premierseedsdirect.com/wp-content/uploads/2017/02/Carnation-Scarlet-Red-2-scaled.jpg',NULL,40000,_binary ''),(9,'Hydrangea','200',50000,'2025-07-07',2,'https://ecolafa.com/wp-content/uploads/2021/10/hoa-cam-tu-cau.png',NULL,35000,_binary ''),(10,'Baby\'s Breath','500',30000,'2025-07-08',1,'https://www.bloombybunches.ca/cdn/shop/files/babys-breath-gypsophila-white-million-star-diy-wedding-events-720.webp?v=1740864684',NULL,20000,_binary ''),(11,'Lavender','0',45000,'2025-07-06',1,'https://charsawfarms.com/cdn/shop/files/PurpleBouquetlavender2.jpg?v=1710207668&width=1946',0,30000,_binary '');
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
INSERT INTO `role` VALUES (1,'Admin'),(2,'Sales Manager'),(3,'Seller'),(4,'Marketer'),(5,'Warehouse Staff'),(6,'Guest'),(7,'Customer'),(8,'Shipper');

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
-- Table structure for table `slider`
--

DROP TABLE IF EXISTS `slider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slider` (
  `slider_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `is_active` tinyint NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `author_id` int NOT NULL,
  PRIMARY KEY (`slider_id`),
  UNIQUE KEY `slider_id_UNIQUE` (`slider_id`),
  KEY `author_id_idx` (`author_id`),
  CONSTRAINT `slider_author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slider`
--

LOCK TABLES `slider` WRITE;
/*!40000 ALTER TABLE `slider` DISABLE KEYS */;
INSERT INTO `slider` VALUES (1,'Spring Sale - Up to 30% Off','https://www.shutterstock.com/image-vector/spring-sale-30-off-handwritten-typography-1927215506','https://example.com/sale',1,'2025-05-19 08:05:45','2025-05-19 08:05:45',1),(2,'New Arrivals - Exotic Flowers','hhttps://juneflowers.ae/wp-content/uploads/2024/10/exotic-flowers-arrangements.jpg','https://example.com/new',1,'2025-05-19 08:05:45','2025-05-19 08:05:45',2),(3,'Mother\'s Day Special','https://as2.ftcdn.net/v2/jpg/02/64/43/55/1000_F_264435588_jYNwyWXusTSFZZsOfAZ2hpY4EwcdKAmt.jpg','https://example.com/mothers-day',1,'2025-05-19 08:05:45','2025-05-19 08:05:45',1);
/*!40000 ALTER TABLE `slider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokenforgetpassword`
--

DROP TABLE IF EXISTS `tokenforgetpassword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokenforgetpassword` (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `expiryTime` timestamp NOT NULL,
  `isUsed` bit(1) NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `tokenforgetpassword_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokenforgetpassword`
--

LOCK TABLES `tokenforgetpassword` WRITE;
/*!40000 ALTER TABLE `tokenforgetpassword` DISABLE KEYS */;
INSERT INTO `tokenforgetpassword` VALUES (1,'45f1544e-a5b0-48fa-a31e-9f9e412853ba','2025-05-23 18:08:49',_binary '\0',9),(2,'90da40eb-5a65-4abf-9b8f-da7889681dd9','2025-05-23 18:25:34',_binary '\0',9),(3,'7a1f8f19-9c03-45a3-8d85-de1612e247c6','2025-05-23 18:25:53',_binary '\0',9),(4,'468e926e-fa9b-41d8-94e4-4b5309a8546a','2025-05-23 18:26:11',_binary '\0',9),(5,'46c73120-0bd3-40c3-a7c9-d8329610689d','2025-05-23 18:28:12',_binary '\0',9),(6,'df881f25-f4e0-4318-8543-c20ab2428afb','2025-05-23 18:29:49',_binary '\0',9),(7,'4c8c54f2-5598-4d02-ad0c-8333e584c042','2025-05-23 18:29:50',_binary '\0',9),(8,'34014995-ede7-49b5-8375-688101611208','2025-05-23 13:05:56',_binary '\0',9),(9,'7b812d2b-c1b0-4152-9985-990929429d70','2025-05-23 13:09:29',_binary '\0',9),(10,'2fcb45e6-6a89-4e11-9db1-e29ad0007fe6','2025-05-23 23:50:00',_binary '\0',9),(11,'8f8e6c1c-4c6b-43c7-b93b-8b55a513a2c3','2025-05-23 23:54:38',_binary '\0',9),(12,'ce1ff1b5-5b96-43b8-b8e5-f88831b48a88','2025-05-24 00:02:03',_binary '\0',9),(13,'ec978c0b-c179-4b1e-86a5-4ccc38dc439d','2025-05-24 00:06:17',_binary '\0',9),(14,'7df613ef-1e9f-4d21-98b2-5b2471fd5bd9','2025-05-24 00:11:47',_binary '\0',9),(15,'f5528473-12c1-4eff-bc70-41ed552cedcb','2025-05-24 00:22:55',_binary '\0',9),(16,'5edb0eb5-dc40-41a9-bf49-9643b6702469','2025-05-24 00:28:53',_binary '\0',9),(17,'69b9ec7d-26dc-4518-9066-1325ffcfdf64','2025-05-24 00:37:29',_binary '\0',9),(18,'6108536e-d130-4fac-bcda-eb46d7db407a','2025-05-24 00:37:40',_binary '\0',9),(19,'4d8f7f9d-9247-485b-92dd-f8fb021c37f8','2025-05-24 01:13:20',_binary '',9),(20,'bcdd8229-a0eb-4e82-9233-7c813493e421','2025-05-24 01:36:14',_binary '',9),(21,'8745114e-a111-44fd-a3fa-0fe40ab116cc','2025-05-24 01:44:36',_binary '\0',9),(22,'3fcef83b-66b2-4f5e-bfe4-c95e021a5374','2025-05-24 01:52:15',_binary '',9),(23,'214365c2-4f2d-4561-8642-0fce17ba98a1','2025-05-24 09:35:58',_binary '',9),(24,'744ee63e-3ee0-42e0-a235-1eb50d127d54','2025-05-24 09:38:31',_binary '',9),(25,'8d28c087-51de-470c-99a0-7432076c9a94','2025-05-24 09:43:42',_binary '',9),(26,'d56935d7-a107-4867-a657-fd793f481f01','2025-05-24 09:45:42',_binary '',9),(27,'7c3d6ea4-cd95-4409-a31a-4035464693c9','2025-05-26 09:00:59',_binary '',9),(28,'455c9b85-3fb1-4197-b07e-df238627edfd','2025-05-26 18:16:29',_binary '\0',9),(29,'938806d2-65b6-4c6e-8828-f75cf22c303e','2025-05-26 18:18:58',_binary '\0',9),(30,'bdc923c0-d9f4-4716-a2f8-4741003fa374','2025-05-29 09:16:15',_binary '\0',9),(31,'04dee5a8-7527-4fac-94cb-acb188ac59c2','2025-06-02 20:32:49',_binary '',9);
/*!40000 ALTER TABLE `tokenforgetpassword` ENABLE KEYS */;
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
  `Password` varchar(100) NOT NULL,
  `Fullname` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Phone` varchar(10) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `Role` int NOT NULL,
  `status` varchar(10) DEFAULT 'active',
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
INSERT INTO `user` VALUES (1,'admin01','$2a$10$Z6PeNnylaCP5O3tblM0SvejkMXoayRhCCTMj13s39DhrLumlhexG.','Alice Admin','alice@flower.com','0901111001','123 Admin Street',1,'active'),(2,'manager01','$2a$10$Ppw/3o3ugj2Mz4k6jRJnh.9EU3c.yQCMG49dIkccKRkeqnUO.xY9G','Bob Manager','bob@flower.com','0901111002','456 Manager Lane',2,'active'),(3,'seller01','$2a$10$tSuwcCCzTEyZ/5u9yet1TOOmKRzG8CyazFR2bZ6JesroUVcSckaS.','Carol Seller','carol@flower.com','0901111003','789 Seller Road',3,'active'),(4,'marketer01','$2a$10$1Te9hDvbo5e5UokNI/g4E.4r731nGFVe8r1DgVMCXKo4ZcxyHeJSa','David Marketer','david@flower.com','0901111004','101 Marketing Blvd',4,'active'),(5,'warehouse01','$2a$10$nITsu.VVg9mfWQj0K3YcZeM.GJIhGb9cvJ8xGsxeqaiWh9.bQ/V2m','Eva Warehouse','eva@flower.com','0901111005','202 Warehouse Ave',5,'active'),(6,'guest01','$2a$10$YrA1Uo2/.DzVfTJWtQvQv.Inx2adbtNz7Ffp6bCKIcoz4RKEaFVQO','Frank Guest','frank@flower.com','0901111006','No Address',6,'active'),(7,'cust01','$2a$10$iEeGuer0SeMaS9cyxyU7KOVE/icEWxtdgrERVc5HCn2Eo12eS6hwC','Grace Customer','grace@flower.com','0901111007','303 Customer Place',7,'active'),(8,'cust02','$2a$10$160t4egnUs0ior5RQoqIHufioc8RC3bzcezPyiayyrtcVCFyfnica','Helen Buyer','helen@flower.com','0901111008','404 Rose Street',7,'active'),(11,'shipper','$2a$10$ECPr17gXBybS4nWf1Nw7x.cq9a00AM/JW2l/8OmMH.l3M7y.jIOYW','Tan VU','vuminhtan2004@gmail.com','0977679888','123 Admin Street',8,'active'),(12,'taness','$2a$10$MBn3oAJGzoPq4tbl5feDMOtfE54jlz36xdtWZw31XJJhKhYcrBiae','tan vu','tanvmhe186791@fpt.edu.vn','0919994398','ca',7,'active');
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

--
-- Dumping events for database 'la_fioreria'
--

--
-- Dumping routines for database 'la_fioreria'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-14 22:17:36
