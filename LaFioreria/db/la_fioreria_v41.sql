CREATE DATABASE  IF NOT EXISTS `la_fioreria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `la_fioreria`;
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
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `pre_context` text NOT NULL,
  `status` varchar(100) NOT NULL,
  `author_id` int NOT NULL,
  `cid` int NOT NULL,
  PRIMARY KEY (`blog_id`),
  UNIQUE KEY `blog_id_UNIQUE` (`blog_id`),
  KEY `author_id_idx` (`author_id`),
  KEY `cid_idx` (`cid`),
  CONSTRAINT `author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `blog_cid` FOREIGN KEY (`cid`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
INSERT INTO `blog` VALUES (1,'6 Tips to Keep Your Flowers Fresh','Learn how to maintain your flowers for longer...','2025-05-19 07:54:53','2025-05-19 07:54:53','https://jardincaribe.com/wp-content/uploads/2020/12/Infograpic-6-tips.png','Test precontext','Hidden',2,6),(2,'The Meaning Behind Popular Flowers','Discover the symbolism of roses, lilies, and more...','2025-05-19 07:54:53','2025-05-19 07:54:53','https://flourishbakingcompany.com/cdn/shop/articles/chart_of_flowers_800x.jpg?v=1580648030','Test precontext','Active',4,7),(3,'How to Choose the Perfect Bouquet for Any Occasion','A guide to picking the right flowers...','2025-05-19 07:54:53','2025-05-19 07:54:53','https://cdn.shopify.com/s/files/1/2776/7900/files/Blog_10.jpg?v=1691462989','Test precontext','Active',4,7),(4,'The Art of Flower Arranging','Flower arranging is a delicate art that brings joy to many. In this post, we’ll explore techniques and tips for stunning floral designs.','2025-06-01 03:00:00','2025-06-01 03:00:00','https://cdn-media.sforum.vn/storage/app/media/anh-dep-72.jpg','A quick guide on flower arrangement tips.','Active',1,1),(5,'Summer Bouquets to Brighten Your Home','Discover the top flower combinations for vibrant summer bouquets. From sunflowers to daisies, make your home glow with color.','2025-06-05 02:00:00','2025-06-05 02:30:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxEEZV6f8peybupvxehPYCV_WuwllvucX9Cw&s','Top flower picks to refresh your home this summer.','Active',1,2),(6,'Behind the Scenes at La Fioreria','Take a look at what goes into preparing our floral collections every day. From early morning market visits to final arrangements.','2025-06-07 01:00:00','2025-06-08 07:20:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGsY-zxNnrhZxqj4a_6Kvuv_tU1oGJWdAY1g&s','Explore our daily routine and dedication to floral perfection.','Active',1,1),(7,'Caring for Your Roses: A Simple Guide','Roses are timeless, but they need the right care to stay beautiful. We’ll cover pruning, watering, and more.','2025-06-10 06:45:00','2025-06-10 06:45:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzbYTyntexjeYwbQP1GHJ2KwQAg_zZHmuecQ&s','Essential rose care tips for beginners.','Active',1,3),(8,'Top 5 Wedding Flower Trends in 2025','Planning a wedding? Check out these trending flower styles to make your special day unforgettable.','2025-06-12 04:00:00','2025-06-12 04:00:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYZQt3C_CBl287dMfxW1cttshB7VRWgn14ZQ&s','Must-know wedding flower styles this year.','Active',1,4),(9,'Why Flowers Make the Best Gifts','Flowers convey emotions in ways words sometimes can’t. Here’s why a bouquet is always a good idea.','2025-06-14 03:10:00','2025-06-14 05:00:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJznMaDtgrRc1hXSb577HXXDxYbF8tpaZgKw&s','Reasons why flowers are perfect for any occasion.','Active',1,2),(10,'Sustainable Floristry at La Fioreria','We believe in beauty with responsibility. Learn about our eco-friendly practices and sustainable choices.','2025-06-16 08:00:00','2025-06-17 02:00:00','https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/482763hUR/anh-mo-ta.png','How we keep floristry green and clean.','Active',1,1),(11,'Orchids: The Elegant Flower','Orchids symbolize beauty and strength. Discover how to grow and care for these stunning plants.','2025-06-18 01:30:00','2025-06-18 01:30:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBS9bJEXw5AYRgmLY_9Nyr79oQFPYEtJjmhA&s','A guide to caring for orchids with ease.','Active',1,3),(12,'Creating Floral Centerpieces Like a Pro','Level up your home décor with professional-looking centerpieces using simple materials and techniques.','2025-06-20 10:45:00','2025-06-21 01:00:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRE54qaXx3lT58QeBmeV7o0-iM_pvK-lz-FdA&s','Make your dining table bloom with flair.','Active',1,4),(13,'Flower Meanings: What Your Bouquet Says','Every flower has a message. Learn the language of flowers and what your bouquet might be saying.','2025-06-22 02:15:00','2025-06-22 02:15:00','https://cdn-media.sforum.vn/storage/app/media/anh-dep-68.jpg','Decode the hidden meanings behind common flowers.','Active',1,2);
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
  `cid` int NOT NULL,
  `price` int DEFAULT NULL,
  `sellPrice` int DEFAULT NULL,
  `status` varchar(50) DEFAULT 'valid',
  PRIMARY KEY (`Bouquet_ID`),
  UNIQUE KEY `Bouquet_ID_UNIQUE` (`Bouquet_ID`),
  KEY `cid_idx` (`cid`),
  CONSTRAINT `bouquet_cid` FOREIGN KEY (`cid`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bouquet`
--

LOCK TABLES `bouquet` WRITE;
/*!40000 ALTER TABLE `bouquet` DISABLE KEYS */;
INSERT INTO `bouquet` VALUES (1,'Romantic Rose','A bouquet of fresh roses',1,250000,1250000,'valid'),(2,'Spring Delight','Colorful tulips and lilies',2,358000,1790000,'valid'),(3,'Sunny Love','Sunflowers and roses for a cheerful gift',5,70000,350000,'invalid'),(4,'Rose Radiance','A vibrant bouquet of fresh red roses symbolizing love and passion.',1,500000,2500000,'valid'),(5,'Lily Light','Elegant lilies arranged to brighten up any occasion.',2,130000,650000,'valid'),(6,'Sunflower Smile','Cheerful sunflowers to bring joy and energy.',3,120000,600000,'valid'),(7,'Tulip Treasure','An exotic bouquet of multicolored tulips.',4,500000,2500000,'valid'),(8,'Orchid Elegance','Luxurious white orchids in an elegant arrangement.',5,1200000,6000000,'valid'),(9,'Sun Dreams','A cheerful bouquet featuring sunflowers and baby\'s breath.',1,345000,1725000,'valid'),(10,'Peony Passion','Romantic peonies paired with soft accents.',2,700000,3500000,'needs_repair'),(11,'Carnation Bliss','Elegant mix of carnations and daisies.',2,720000,3600000,'valid'),(12,'Hydrangea Harmony','Soothing tones with hydrangeas and baby\'s breath.',3,450000,2250000,'valid'),(27,'noo','123120301230102 3012 30123 1023 012301230123 12 31 23 1 231 0230',1,60000,300000,'valid'),(35,'Test add','bvcnbvmnb',4,65000,325000,'valid'),(36,'hoa',' ',1,20000,100000,'valid');
/*!40000 ALTER TABLE `bouquet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bouquet_images`
--

DROP TABLE IF EXISTS `bouquet_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bouquet_images` (
  `Bouquet_ID` int NOT NULL,
  `image_url` varchar(255) NOT NULL,
  PRIMARY KEY (`Bouquet_ID`,`image_url`),
  CONSTRAINT `bid` FOREIGN KEY (`Bouquet_ID`) REFERENCES `bouquet` (`Bouquet_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bouquet_images`
--

LOCK TABLES `bouquet_images` WRITE;
/*!40000 ALTER TABLE `bouquet_images` DISABLE KEYS */;
INSERT INTO `bouquet_images` VALUES (1,'1750524624204_ERD_GUIDE.png'),(1,'1750525046297_pt12.png'),(1,'1750590818622_sentinels-win-first-international-valorant-event.png'),(1,'1750590818634_472017959_1113363040262417_4635908413873429904_n.jpg'),(1,'1750590818634_b80cba3d-bfe8-4378-b7c7-828854b69244_low.jpg'),(2,'1750439131747_gk6kebe1ojjyxdgv0ok3.jpg'),(2,'1750704971253_wp12585891-sentinels-valorant-wallpapers.png'),(2,'1750704971265_472017959_1113363040262417_4635908413873429904_n.jpg'),(2,'1750704971265_rnnrJbh.jpeg'),(2,'1750704971265_sentinels-win-first-international-valorant-event.png'),(3,'1750258347762_fb150004_2_cheerful_sunflowers_bouquet_2_2_res.jpg'),(4,'1750258391571_image_fd2696a5-aa10-47d1-bf2c-2c33a7e65989.jpg'),(5,'1750258420386_thefloristmarket-scentales-28573-30243_1080x1080.jpg'),(6,'1750258456097_IMG_6004-scaled.jpg'),(7,'1750258487007_bohoa15.jpg'),(8,'1750258587648_bo-hoa-lan-trang-2.jpg'),(9,'1750316204090_Sunny-Baby-sunflower-bouquet-selangor.png'),(10,'1750316286853_lasting-love-peony-fafulflorist-896234.jpg'),(11,'1750316332512_DP09260.jpg'),(12,'1750316360972_whisper-blue-hydrangea-baby-breath-flower-bouquet-01.jpg'),(27,'1750593572167_8f95c217ba2d2346b499f9772ec15d3a.jpg'),(27,'1750593572188_227a65c4b3193e92b421cd3224eb4395.jpg'),(27,'1750593572192_480662810_643958421413188_2211506288672214070_n.jpg'),(27,'1750593572198_476573883_612916858033363_1219310901361798773_n.jpg'),(35,'1750670476639_sentinels-win-first-international-valorant-event.png'),(35,'1750670476645_472017959_1113363040262417_4635908413873429904_n.jpg'),(35,'1750670476646_b80cba3d-bfe8-4378-b7c7-828854b69244_low.jpg'),(35,'1750670476647_SnapshotLoad.jpg'),(36,'1751290403074_3d5cf84149f02efd5a342e9083c65f7e.jpg');
/*!40000 ALTER TABLE `bouquet_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bouquet_raw`
--

DROP TABLE IF EXISTS `bouquet_raw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bouquet_raw` (
  `bouquet_id` int NOT NULL,
  `batch_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`bouquet_id`,`batch_id`),
  KEY `raw_flower_id_idx` (`batch_id`),
  CONSTRAINT `bouquet_raw_id` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`),
  CONSTRAINT `flower_batch_id` FOREIGN KEY (`batch_id`) REFERENCES `flower_batch` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bouquet_raw`
--

LOCK TABLES `bouquet_raw` WRITE;
/*!40000 ALTER TABLE `bouquet_raw` DISABLE KEYS */;
INSERT INTO `bouquet_raw` VALUES (1,1,10),(2,1,1),(2,2,5),(2,3,3),(2,8,3),(3,1,2),(3,5,4),(4,1,20),(5,3,5),(6,5,8),(7,2,10),(8,4,15),(9,5,3),(9,10,10),(10,7,7),(11,6,12),(11,8,10),(12,9,3),(12,10,10),(27,8,1),(35,4,1),(35,5,1),(36,17,10);
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartdetails`
--

LOCK TABLES `cartdetails` WRITE;
/*!40000 ALTER TABLE `cartdetails` DISABLE KEYS */;
INSERT INTO `cartdetails` VALUES (1,7,1,2),(2,7,3,1),(3,8,2,4),(4,8,1,1),(38,13,2,1);
/*!40000 ALTER TABLE `cartdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartwholesaledetails`
--

DROP TABLE IF EXISTS `cartwholesaledetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartwholesaledetails` (
  `cartWholeSaleID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `bouquetID` int NOT NULL,
  `quantity` int NOT NULL,
  `pricePerUnit` int NOT NULL,
  `totalValue` int NOT NULL,
  `expense` int NOT NULL,
  `request_group_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cartWholeSaleID`),
  KEY `wholeSale_cart_bouquet_id_idx` (`bouquetID`),
  KEY `wholeSale_cart_user_ID_idx` (`userID`),
  CONSTRAINT `wholeSale_cart_bouquet_id` FOREIGN KEY (`bouquetID`) REFERENCES `bouquet` (`Bouquet_ID`),
  CONSTRAINT `wholeSale_cart_user_ID` FOREIGN KEY (`userID`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartwholesaledetails`
--

LOCK TABLES `cartwholesaledetails` WRITE;
/*!40000 ALTER TABLE `cartwholesaledetails` DISABLE KEYS */;
INSERT INTO `cartwholesaledetails` VALUES (19,13,2,50,1089000,54450000,363000,'2fc3ed51-0410-4700-a787-1b32e06c4028'),(20,13,6,52,360000,18720000,120000,'2fc3ed51-0410-4700-a787-1b32e06c4028');
/*!40000 ALTER TABLE `cartwholesaledetails` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Romantic','Flowers meant to express love, ideal for Valentine’s Day, proposals, or anniversaries.'),(2,'Birthday','Bouquets designed to celebrate someone’s birthday with joy and color.'),(3,'Congratulations','Arrangements to celebrate milestones like graduations, promotions, or achievements.'),(4,'Sympathy','Sympathy flowers to offer comfort during times of loss or hardship.'),(5,'Anniversary','Bouquets for celebrating wedding anniversaries and long-lasting love.'),(6,'Tips & Tricks','Flower care tips and tricks'),(7,'Occasions','Blogs about special occasions and flower meanings'),(8,'New Arrivals','News about new bouquets and flowers');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'Tân Vũ','vuminhtan2004@gmail.com','lead','123','2025-05-22 23:45:27'),(2,'Tân Vũ','vuminhtan2004@gmail.com','lead','avavxvx','2025-05-22 23:46:19'),(3,'Tân Vũ','vuminhtan2004@gmail.com','lead','11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111','2025-05-22 23:46:51'),(4,'Tân Vũ','vuminhtan2004@gmail.com','ccc','câccacacac','2025-06-05 23:06:59');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_info`
--

DROP TABLE IF EXISTS `customer_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_info` (
  `Customer_ID` int NOT NULL AUTO_INCREMENT,
  `User_ID` int NOT NULL,
  `Customer_Code` varchar(20) NOT NULL,
  `Join_Date` date DEFAULT NULL,
  `Loyalty_Point` int DEFAULT '0',
  `Birthday` date DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Customer_ID`),
  UNIQUE KEY `Customer_Code_UNIQUE` (`Customer_Code`),
  KEY `FK_User_Customer_idx` (`User_ID`),
  CONSTRAINT `FK_User_Customer` FOREIGN KEY (`User_ID`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_info`
--

LOCK TABLES `customer_info` WRITE;
/*!40000 ALTER TABLE `customer_info` DISABLE KEYS */;
INSERT INTO `customer_info` VALUES (1,7,'CUST007','2023-01-01',120,'2000-05-10','Female'),(2,8,'CUST008','2023-02-15',80,'1998-09-22','Female'),(3,12,'CUST012','2024-03-01',50,'2004-08-18','Male'),(4,13,'CUST013','2024-06-15',295,'2003-12-07','Male');
/*!40000 ALTER TABLE `customer_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount_code`
--

DROP TABLE IF EXISTS `discount_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount_code` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `description` text,
  `discount_type` enum('PERCENT','FIXED') NOT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `max_discount` decimal(10,2) DEFAULT NULL,
  `min_order_amount` decimal(10,2) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `usage_limit` int DEFAULT NULL,
  `used_count` int DEFAULT '0',
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount_code`
--

LOCK TABLES `discount_code` WRITE;
/*!40000 ALTER TABLE `discount_code` DISABLE KEYS */;
INSERT INTO `discount_code` VALUES (1,'SUMMER2024','Giảm 10% mùa hè','PERCENT',10.00,100000.00,500000.00,'2025-07-19 22:01:38','2025-07-27 22:01:38',100,4,1);
/*!40000 ALTER TABLE `discount_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_info`
--

DROP TABLE IF EXISTS `employee_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_info` (
  `Employee_ID` int NOT NULL AUTO_INCREMENT,
  `User_ID` int NOT NULL,
  `Employee_Code` varchar(20) NOT NULL,
  `Contract_Type` varchar(50) DEFAULT NULL,
  `Start_Date` date DEFAULT NULL,
  `End_Date` date DEFAULT NULL,
  `Department` varchar(50) DEFAULT NULL,
  `Position` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Employee_ID`),
  UNIQUE KEY `Employee_Code_UNIQUE` (`Employee_Code`),
  KEY `FK_User_Employee_idx` (`User_ID`),
  CONSTRAINT `FK_User_Employee` FOREIGN KEY (`User_ID`) REFERENCES `user` (`User_ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_info`
--

LOCK TABLES `employee_info` WRITE;
/*!40000 ALTER TABLE `employee_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `bouquet_id` int NOT NULL,
  `order_id` int DEFAULT NULL,
  `rating` int NOT NULL,
  `comment` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(50) DEFAULT 'pending',
  PRIMARY KEY (`feedback_id`),
  KEY `fk_feedback_customer` (`customer_id`),
  KEY `fk_feedback_bouquet` (`bouquet_id`),
  KEY `fk_feedback_order` (`order_id`),
  CONSTRAINT `fk_feedback_bouquet` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_feedback_customer` FOREIGN KEY (`customer_id`) REFERENCES `user` (`User_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_feedback_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE SET NULL,
  CONSTRAINT `feedback_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,12,1,NULL,5,'Romantic Rose thật tuyệt vời!','2025-07-21 20:31:54','approved'),(2,13,2,NULL,4,'Spring Delight rất tươi mới.','2025-07-21 20:31:54','approved'),(3,12,3,NULL,3,'Sunny Love hơi đơn giản nhưng ổn.','2025-07-21 20:31:54','approved'),(4,13,4,NULL,5,'Rose Radiance cực kỳ nổi bật.','2025-07-21 20:31:54','approved'),(5,12,5,NULL,4,'Lily Light nhẹ nhàng và sang trọng.','2025-07-21 20:31:54','approved'),(6,13,6,NULL,5,'Sunflower Smile làm tôi vui cả ngày.','2025-07-21 20:31:54','approved'),(7,12,7,NULL,4,'Tulip Treasure màu sắc rất đẹp.','2025-07-21 20:31:54','approved'),(8,13,8,NULL,5,'Orchid Elegance thật sự đẳng cấp.','2025-07-21 20:31:54','approved'),(9,12,9,NULL,4,'Sun Dreams mang năng lượng tích cực.','2025-07-21 20:31:54','approved'),(10,13,10,NULL,5,'Peony Passion rất lãng mạn.','2025-07-21 20:31:54','approved'),(11,12,11,NULL,4,'Carnation Bliss rất thanh lịch.','2025-07-21 20:31:54','approved'),(12,13,12,NULL,5,'Hydrangea Harmony tuyệt vời.','2025-07-21 20:31:54','approved'),(13,12,27,NULL,3,'Bó hoa này bình thường, không quá nổi bật.','2025-07-21 20:31:54','approved'),(14,13,35,NULL,4,'Test add trông ổn áp.','2025-07-21 20:31:54','approved'),(15,12,36,NULL,3,'hoa – cần cải thiện thêm.','2025-07-21 20:31:54','approved');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_images`
--

DROP TABLE IF EXISTS `feedback_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback_images` (
  `feedback_id` int NOT NULL,
  `image_url` varchar(255) NOT NULL,
  PRIMARY KEY (`feedback_id`,`image_url`),
  CONSTRAINT `fk_feedback_images_feedback` FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`feedback_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_images`
--

LOCK TABLES `feedback_images` WRITE;
/*!40000 ALTER TABLE `feedback_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback_images` ENABLE KEYS */;
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
  `unit_price` int DEFAULT NULL,
  `import_date` date DEFAULT NULL,
  `expiration_date` date DEFAULT NULL,
  `hold` int DEFAULT NULL,
  `warehouse_id` int NOT NULL,
  `status` varchar(50) DEFAULT 'fresh',
  `real_time_quantity` int DEFAULT NULL,
  PRIMARY KEY (`batch_id`),
  UNIQUE KEY `batch_id_UNIQUE` (`batch_id`),
  KEY `flower_id_idx` (`flower_id`),
  KEY `warehouse_id_idx` (`warehouse_id`),
  CONSTRAINT `flower_id` FOREIGN KEY (`flower_id`) REFERENCES `flower_type` (`flower_id`),
  CONSTRAINT `warehouse_id` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`Warehouse_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flower_batch`
--

LOCK TABLES `flower_batch` WRITE;
/*!40000 ALTER TABLE `flower_batch` DISABLE KEYS */;
INSERT INTO `flower_batch` VALUES (1,1,89,15000,'2025-07-01','2025-08-31',NULL,1,'fresh',-362),(2,2,295,35000,'2025-07-01','2025-08-25',NULL,1,'fresh',190),(3,3,197,16000,'2025-07-01','2025-08-28',NULL,1,'fresh',29),(4,4,100,55000,'2025-07-01','2025-08-30',NULL,2,'fresh',70),(5,5,150,10000,'2025-07-01','2025-08-29',NULL,2,'fresh',110),(6,6,400,7000,'2025-07-01','2025-07-05',NULL,1,'expired',400),(7,7,250,70000,'2025-07-01','2025-07-10',NULL,1,'expired',250),(8,8,347,40000,'2025-07-01','2025-07-03',NULL,2,'expired',284),(9,9,200,35000,'2025-07-01','2025-07-07',NULL,2,'expired',50),(10,10,500,20000,'2025-07-01','2025-07-08',NULL,1,'expired',0),(11,11,0,30000,'2025-07-01','2025-07-06',0,1,'expired',0),(17,1,10,2000,'2025-06-19','2025-06-19',0,1,'expired',10),(18,2,100,10000,'2025-06-30','2025-06-30',0,1,'expired',100);
/*!40000 ALTER TABLE `flower_batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flower_batch_allocation`
--

DROP TABLE IF EXISTS `flower_batch_allocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flower_batch_allocation` (
  `allocation_id` int NOT NULL AUTO_INCREMENT,
  `batch_id` int NOT NULL,
  `flower_id` int NOT NULL,
  `order_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `status` enum('soft_hold','reserved','used','cancelled') NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cart_id` int DEFAULT NULL,
  PRIMARY KEY (`allocation_id`),
  KEY `batchId_idx` (`batch_id`),
  KEY `flowerId_idx` (`flower_id`),
  CONSTRAINT `batchIdAllocation` FOREIGN KEY (`batch_id`) REFERENCES `flower_batch` (`batch_id`),
  CONSTRAINT `flowerIdAllocation` FOREIGN KEY (`flower_id`) REFERENCES `flower_type` (`flower_id`),
  CONSTRAINT `flower_batch_allocation_chk_1` CHECK ((`quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flower_batch_allocation`
--

LOCK TABLES `flower_batch_allocation` WRITE;
/*!40000 ALTER TABLE `flower_batch_allocation` DISABLE KEYS */;
INSERT INTO `flower_batch_allocation` VALUES (1,1,1,NULL,10,'cancelled','2025-07-10 01:56:54',23),(2,2,2,NULL,50,'cancelled','2025-07-10 01:56:54',23),(3,3,3,NULL,30,'cancelled','2025-07-10 01:56:54',23),(4,8,8,NULL,30,'cancelled','2025-07-10 01:56:54',23),(8,1,1,NULL,10,'cancelled','2025-07-10 01:57:53',23),(9,2,2,NULL,50,'cancelled','2025-07-10 01:57:53',23),(10,3,3,NULL,30,'cancelled','2025-07-10 01:57:53',23),(11,8,8,NULL,30,'cancelled','2025-07-10 01:57:53',23),(12,3,3,NULL,100,'cancelled','2025-07-10 01:57:53',24),(13,3,3,NULL,5,'cancelled','2025-07-11 07:27:53',25),(14,1,1,NULL,20,'cancelled','2025-07-11 07:40:47',26),(15,2,2,NULL,100,'cancelled','2025-07-11 07:40:47',26),(16,3,3,NULL,60,'cancelled','2025-07-11 07:40:47',26),(17,8,8,NULL,60,'cancelled','2025-07-11 07:40:47',26),(21,1,1,NULL,200,'cancelled','2025-07-11 07:41:44',27),(22,1,1,NULL,200,'cancelled','2025-07-11 07:41:55',27),(23,1,1,NULL,20,'cancelled','2025-07-11 07:41:55',28),(24,5,5,NULL,40,'cancelled','2025-07-11 07:41:55',28),(25,1,1,NULL,200,'cancelled','2025-07-11 07:45:38',27),(26,5,5,NULL,80,'cancelled','2025-07-11 07:45:38',29),(28,1,1,NULL,100,'cancelled','2025-07-11 07:51:47',30),(29,1,1,NULL,450,'cancelled','2025-07-11 07:52:16',31),(30,1,1,NULL,10,'cancelled','2025-07-11 07:53:08',32),(31,2,2,NULL,50,'cancelled','2025-07-11 07:53:08',32),(32,3,3,NULL,30,'cancelled','2025-07-11 07:53:08',32),(33,8,8,NULL,30,'cancelled','2025-07-11 07:53:08',32),(37,1,1,NULL,400,'cancelled','2025-07-11 09:46:36',33),(38,1,1,NULL,400,'cancelled','2025-07-12 05:22:07',33),(39,1,1,NULL,10,'cancelled','2025-07-12 05:22:07',34),(40,1,1,NULL,40,'cancelled','2025-07-13 23:16:59',35),(41,4,4,NULL,30,'cancelled','2025-07-13 23:18:54',36),(42,9,9,NULL,150,'cancelled','2025-07-13 23:21:38',37),(43,10,10,NULL,500,'cancelled','2025-07-13 23:21:38',37),(45,1,1,NULL,1,'cancelled','2025-07-14 07:55:17',38),(46,2,2,NULL,5,'cancelled','2025-07-14 07:55:17',38),(47,3,3,NULL,3,'cancelled','2025-07-14 07:55:17',38),(48,8,8,NULL,3,'cancelled','2025-07-14 07:55:17',38);
/*!40000 ALTER TABLE `flower_batch_allocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flower_discard`
--

DROP TABLE IF EXISTS `flower_discard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flower_discard` (
  `discard_id` int NOT NULL AUTO_INCREMENT,
  `batch_id` int NOT NULL,
  `discard_date` date DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`discard_id`),
  KEY `batch_id_idx` (`batch_id`),
  CONSTRAINT `fk_batch_id` FOREIGN KEY (`batch_id`) REFERENCES `flower_batch` (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flower_discard`
--

LOCK TABLES `flower_discard` WRITE;
/*!40000 ALTER TABLE `flower_discard` DISABLE KEYS */;
INSERT INTO `flower_discard` VALUES (7,1,'2025-07-01',10,'Quá hạn','Quá hạn sử dụng'),(8,1,'2025-07-02',5,'Mốc','Bị mốc do độ ẩm'),(9,2,'2025-07-03',7,'Hỏng do nhiệt độ','Bảo quản không đúng cách'),(10,3,'2025-07-04',4,'Dập nát','Lỗi khi vận chuyển'),(11,4,'2025-08-01',8,'Quá hạn','Không bán kịp'),(12,5,'2025-08-02',2,'Mốc','Do kho lạnh bị hỏng');
/*!40000 ALTER TABLE `flower_discard` ENABLE KEYS */;
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
  `active` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`flower_id`),
  UNIQUE KEY `flower_id_UNIQUE` (`flower_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flower_type`
--

LOCK TABLES `flower_type` WRITE;
/*!40000 ALTER TABLE `flower_type` DISABLE KEYS */;
INSERT INTO `flower_type` VALUES (1,'Rose','3CC66EEC-0B20-4AC4-B2FD-E82A4734E596.jpeg',_binary ''),(2,'Tulip','Tulip.jpg',_binary ''),(3,'Lily','Lily.jpg',_binary ''),(4,'Orchid','Orchid.jpg',_binary ''),(5,'Sunflower','Sunflower.jpg',_binary ''),(6,'Daisy','Daisy.jpg',_binary ''),(7,'Peony','Peony.jpg',_binary ''),(8,'Carnation','Carnation.jpg',_binary ''),(9,'Hydrangea','Hydrangea.png',_binary ''),(10,'Baby\'s Breath','Baby\'s Breath.jpg',_binary ''),(11,'Lavender','Lavender.jpg',_binary '');
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
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `message` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(50) DEFAULT 'unread',
  PRIMARY KEY (`notification_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`User_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (2,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:06:09','read'),(3,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:08:47','read'),(4,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:08:48','read'),(5,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:08:52','read'),(6,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:08:53','read'),(7,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:10:19','read'),(8,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:10:20','read'),(9,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:10:37','read'),(10,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:10:38','read'),(11,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:10:46','read'),(12,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:10:47','read'),(13,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:11:46','read'),(14,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:11:46','read'),(15,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:13:23','read'),(16,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:13:23','read'),(17,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:13:27','read'),(18,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:13:28','read'),(19,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:17:13','read'),(20,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:17:13','read'),(21,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:17:56','read'),(22,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:17:57','read'),(23,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:18:04','read'),(24,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:18:05','read'),(25,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:19:47','read'),(26,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:19:48','read'),(27,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:20:25','read'),(28,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-06-27 02:20:25','read'),(29,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 11:31:35','unread'),(30,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 11:31:35','unread'),(31,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 11:31:36','unread'),(32,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 11:31:36','unread'),(33,1,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 11:31:44','unread'),(34,14,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 11:31:44','unread'),(35,1,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 11:31:47','unread'),(36,14,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 11:31:47','unread'),(37,1,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 11:31:54','unread'),(38,14,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 11:31:54','unread'),(39,1,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 11:31:56','unread'),(40,14,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 11:31:56','unread'),(41,1,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 11:32:03','unread'),(42,14,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 11:32:03','unread'),(43,1,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 11:32:05','unread'),(44,14,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 11:32:05','unread'),(45,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 11:37:15','unread'),(46,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 11:37:15','unread'),(47,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 11:37:15','unread'),(48,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 11:37:15','unread'),(49,1,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:23','unread'),(50,14,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:23','unread'),(51,1,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:24','unread'),(52,14,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:24','unread'),(53,1,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:32','unread'),(54,14,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:32','unread'),(55,1,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:33','unread'),(56,14,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:33','unread'),(57,1,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:40','unread'),(58,14,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:40','unread'),(59,1,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:41','unread'),(60,14,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 11:37:41','unread'),(61,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 21:07:32','unread'),(62,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 21:07:32','unread'),(63,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 21:07:32','unread'),(64,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-15 21:07:32','unread'),(65,1,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 21:07:43','unread'),(66,14,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 21:07:43','unread'),(67,1,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 21:07:44','unread'),(68,14,'Giỏ hoa Romantic Rose (ID: 1) cần 5000 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-15 21:07:44','unread'),(69,1,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 21:07:53','unread'),(70,14,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 21:07:53','unread'),(71,1,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 21:07:54','unread'),(72,14,'Giỏ hoa Spring Delight (ID: 2) cần 3684 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-15 21:07:54','unread'),(73,1,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 21:08:04','unread'),(74,14,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 21:08:04','unread'),(75,1,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 21:08:06','unread'),(76,14,'Giỏ hoa Sunflower Smile (ID: 6) cần 1600 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-15 21:08:06','unread'),(77,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-21 17:11:03','unread'),(78,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-21 17:11:03','unread'),(79,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-21 17:11:03','unread'),(80,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-21 17:11:03','unread'),(81,1,'Giỏ hoa Romantic Rose (ID: 1) cần 5020 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:09','unread'),(82,14,'Giỏ hoa Romantic Rose (ID: 1) cần 5020 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:09','unread'),(83,1,'Giỏ hoa Romantic Rose (ID: 1) cần 5020 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:10','unread'),(84,14,'Giỏ hoa Romantic Rose (ID: 1) cần 5020 hoa, tồn kho 89. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:10','unread'),(85,1,'Giỏ hoa Spring Delight (ID: 2) cần 3732 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:17','unread'),(86,14,'Giỏ hoa Spring Delight (ID: 2) cần 3732 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:17','unread'),(87,1,'Giỏ hoa Spring Delight (ID: 2) cần 3732 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:18','unread'),(88,14,'Giỏ hoa Spring Delight (ID: 2) cần 3732 hoa, tồn kho 928. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:18','unread'),(89,1,'Giỏ hoa Sunflower Smile (ID: 6) cần 1624 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:24','unread'),(90,14,'Giỏ hoa Sunflower Smile (ID: 6) cần 1624 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:24','unread'),(91,1,'Giỏ hoa Sunflower Smile (ID: 6) cần 1624 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:24','unread'),(92,14,'Giỏ hoa Sunflower Smile (ID: 6) cần 1624 hoa, tồn kho 150. Vui lòng nhập thêm lô hoa.','2025-07-21 17:11:24','unread'),(93,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 03:49:44','unread'),(94,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 03:49:44','unread'),(95,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 03:49:56','unread'),(96,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 03:49:56','unread'),(97,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 03:52:37','unread'),(98,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 03:52:37','unread'),(99,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 03:52:37','unread'),(100,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:31','unread'),(101,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:31','unread'),(102,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:31','unread'),(103,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:43','unread'),(104,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:43','unread'),(105,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:43','unread'),(106,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:43','unread'),(107,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:43','unread'),(108,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:43','unread'),(109,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:55','unread'),(110,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:55','unread'),(111,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:55','unread'),(112,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:57','unread'),(113,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:57','unread'),(114,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:13:57','unread'),(115,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:14:09','unread'),(116,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:14:09','unread'),(117,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:14:09','unread'),(118,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:14:09','unread'),(119,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:14:09','unread'),(120,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:14:09','unread'),(121,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:16','unread'),(122,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:16','unread'),(123,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:16','unread'),(124,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:16','unread'),(125,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:16','unread'),(126,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:16','unread'),(127,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:28','unread'),(128,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:28','unread'),(129,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:28','unread'),(130,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:29','unread'),(131,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:29','unread'),(132,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:29','unread'),(133,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:42','unread'),(134,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:42','unread'),(135,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:42','unread'),(136,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:42','unread'),(137,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:42','unread'),(138,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:16:42','unread'),(139,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:24:09','unread'),(140,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:24:09','unread'),(141,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:24:09','unread'),(142,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:24:10','unread'),(143,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:24:10','unread'),(144,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 04:24:10','unread'),(145,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:37','unread'),(146,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:37','unread'),(147,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:37','unread'),(148,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:40','unread'),(149,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:40','unread'),(150,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:40','unread'),(151,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:58','unread'),(152,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:58','unread'),(153,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:37:58','unread'),(154,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:00','unread'),(155,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:00','unread'),(156,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:00','unread'),(157,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:30','unread'),(158,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:30','unread'),(159,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:30','unread'),(160,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:31','unread'),(161,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:31','unread'),(162,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:31','unread'),(163,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:56','unread'),(164,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:56','unread'),(165,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:56','unread'),(166,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:57','unread'),(167,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:57','unread'),(168,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:38:57','unread'),(169,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:46:36','unread'),(170,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:46:36','unread'),(171,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:46:36','unread'),(172,1,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:46:37','unread'),(173,14,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:46:37','unread'),(174,18,'Lô hoa 11 (Lavender) có số lượng thấp: 0. Vui lòng nhập thêm.','2025-07-22 09:46:37','unread');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
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
  `status_id` int DEFAULT NULL,
  `shipper_id` int DEFAULT NULL,
  `delivery_confirmation_image_path` varchar(255) DEFAULT NULL,
  `cancellation_image_path` varchar(255) DEFAULT NULL,
  `cancellation_reason` text,
  `total_sell` int DEFAULT NULL,
  `total_import` int DEFAULT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `customer_phone` varchar(20) DEFAULT NULL,
  `customer_address` varchar(255) DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `discount_code_id` int DEFAULT NULL,
  `discount_amount` decimal(10,2) DEFAULT '0.00',
  `type` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `idnew_table_UNIQUE` (`order_id`),
  KEY `customer_id_idx` (`customer_id`),
  KEY `status_id_idx` (`status_id`),
  KEY `fk_shipper_user` (`shipper_id`),
  KEY `discount_code_id` (`discount_code_id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `fk_shipper_user` FOREIGN KEY (`shipper_id`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`discount_code_id`) REFERENCES `discount_code` (`id`),
  CONSTRAINT `status_id` FOREIGN KEY (`status_id`) REFERENCES `order_status` (`order_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (2,'2025-08-11',8,4,11,'/uploads/8f95c217ba2d2346b499f9772ec15d3a.jpg',NULL,NULL,0,1222,NULL,NULL,NULL,NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(9,'2025-06-25',7,1,11,NULL,NULL,NULL,0,720000,NULL,NULL,NULL,NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(10,'2025-06-25',7,3,11,NULL,NULL,NULL,0,120000,NULL,NULL,NULL,NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(11,'2025-06-28',7,1,11,NULL,NULL,NULL,3600000,720000,NULL,NULL,NULL,NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(13,'2025-06-27',NULL,3,11,NULL,NULL,NULL,1200000,700000,'Nguyễn Văn A','0901234567','123 Đường Láng, Đống Đa, Hà Nội',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(14,'2025-06-28',NULL,3,11,NULL,NULL,NULL,1820000,364000,'tan vu','0919994398','Huyện Bát Xát - Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(15,'2025-06-28',13,1,11,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(16,'2025-06-29',12,3,11,NULL,NULL,NULL,6680000,1336000,'tan vu','0919994398','Huyện Bát Xát - Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(17,'2025-06-29',NULL,3,11,NULL,NULL,NULL,1820000,364000,'Tân Vũ','0919994398','so 04 to 8, Phường Nông Tiến, Thành phố Tuyên Quang, Tỉnh Tuyên Quang',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(18,'2025-06-29',7,3,11,NULL,NULL,NULL,5850000,1170000,NULL,NULL,NULL,NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(19,'2025-06-29',NULL,1,NULL,NULL,NULL,NULL,1820000,364000,'Tân Vũ','0919994398','so 04 to 8, Xã Khuôn Hà, Huyện Lâm Bình, Tỉnh Tuyên Quang',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(20,'2025-06-29',NULL,1,NULL,NULL,NULL,NULL,1820000,364000,'Tân Vũ','0919994398','so 04 to 8, Xã Khuôn Hà, Huyện Lâm Bình, Tỉnh Tuyên Quang',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(21,'2025-06-29',NULL,1,NULL,NULL,NULL,NULL,1820000,364000,'Tân Vũ','0919994398','so 04 to 8, Xã Tả Phời, Thành phố Lào Cai, Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(22,'2025-06-29',12,1,NULL,NULL,NULL,NULL,1820000,364000,'tan vu','0919994398','caccccccccccccc, Xã Bản Xèo, Huyện Bát Xát, Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(23,'2025-06-29',NULL,1,NULL,NULL,NULL,NULL,680000,136000,'Tân Vũ','0919994398','caâccacacac, Xã Nam Hưng, Huyện Tiên Lãng, Thành phố Hải Phòng',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(24,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,680000,136000,'Tân Vũ','0919994398','caâccacacac, Phường Nam Cường, Thành phố Lào Cai, Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(25,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,680000,136000,'Tân Vũ','0919994398','caccccccccccccc, Xã Đông Cứu, Huyện Gia Bình, Tỉnh Bắc Ninh',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(26,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,355000,71000,'Tân Vũ','0919994398','caccccccccccccc, Xã Thanh Bình, Huyện Mường Khương, Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(27,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,355000,71000,'Tân Vũ','0919994398','caccccccccccccc, Xã Thanh Bình, Huyện Mường Khương, Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(28,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,355000,71000,'Tân Vũ','0919994398','caccccccccccccc, Xã Thanh Bình, Huyện Mường Khương, Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(29,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,355000,71000,'Tân Vũ','0919994398','caccccccccccccc, Xã Thanh Bình, Huyện Mường Khương, Tỉnh Lào Cai',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(30,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,2530000,506000,'Tân Vũ','0919994398','caccccccccccccc, Xã Long Châu, Huyện Yên Phong, Tỉnh Bắc Ninh',NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(31,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,1820000,364000,'Tân Vũ','0919994398','caccccccccccccc, Phường Quyết Thắng, Thành phố Lai Châu, Tỉnh Lai Châu','vietqr','2025-07-01 11:16:49',NULL,0.00,NULL),(32,'2025-06-30',NULL,1,NULL,NULL,NULL,NULL,2170000,434000,'Tân Vũ','0919994398','caccccccccccccc, Phường Nông Tiến, Thành phố Tuyên Quang, Tỉnh Tuyên Quang','cod','2025-07-01 11:16:49',NULL,0.00,NULL),(33,'2025-06-30',NULL,3,NULL,NULL,NULL,NULL,2170000,434000,'Tân Vũ','0919994398','caccccccccccccc, Phường Nông Tiến, Thành phố Tuyên Quang, Tỉnh Tuyên Quang','vietqr','2025-07-01 11:16:49',NULL,0.00,NULL),(34,'2025-06-30',NULL,3,NULL,NULL,NULL,NULL,1820000,364000,'Tân Vũ','0919994398','caccccccccccccc, Xã Keo Lôm, Huyện Điện Biên Đông, Tỉnh Điện Biên','vietqr','2025-07-01 11:16:49',NULL,0.00,NULL),(35,'2025-06-30',13,1,16,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,'2025-07-01 11:16:49',NULL,0.00,NULL),(36,'2025-07-01',NULL,1,NULL,NULL,NULL,NULL,1820000,364000,'Tân Vũ','0919994398','caccccccccccccc, Phường Tân Phong, Thành phố Lai Châu, Tỉnh Lai Châu','vietqr','2025-07-01 11:27:09',NULL,0.00,NULL),(37,'2025-07-09',NULL,1,NULL,NULL,NULL,NULL,1820000,364000,'Quang Vu','0786709182','368B Quang Trung, La Khê, Hà Đông, Hà Nội, Xã Tả Lèng, Huyện Tam Đường, Tỉnh Lai Châu','vietqr','2025-07-09 23:46:02',NULL,0.00,NULL),(38,'2025-07-09',NULL,1,NULL,NULL,NULL,NULL,1820000,364000,'Quang Vu','0786709182','368B Quang Trung, La Khê, Hà Đông, Hà Nội, Xã Tả Lèng, Huyện Tam Đường, Tỉnh Lai Châu','cod','2025-07-09 23:46:24',NULL,0.00,NULL),(39,'2025-07-12',13,3,NULL,NULL,NULL,NULL,51280000,10256000,'Quang Vu','0786709182','368B Quang Trung, La Khê, Hà Đông, Hà Nội, Xã Pa Cheo, Huyện Bát Xát, Tỉnh Lào Cai','cod','2025-07-12 05:33:59',NULL,0.00,NULL),(40,'2025-07-15',NULL,3,11,NULL,NULL,NULL,2530000,506000,'Tân Vũ','0919994398','caccccccccccccc, Xã Tân Lập, Huyện Vũ Thư, Tỉnh Thái Bình','vietqr','2025-07-15 03:16:28',NULL,0.00,NULL),(41,'2025-07-15',NULL,3,11,NULL,NULL,NULL,2530000,506000,'Tân Vũ','0919994398','caccccccccccccc, Xã Việt Hải, Huyện Cát Hải, Thành phố Hải Phòng','vietqr','2025-07-15 21:12:56',NULL,0.00,NULL),(42,'2025-07-17',NULL,3,11,NULL,NULL,NULL,8530000,1706000,'Tân Vũ','0919994398','caccccccccccccc, Xã Mường Pồn, Huyện Điện Biên, Tỉnh Điện Biên','vietqr','2025-07-17 21:19:57',NULL,0.00,NULL),(43,'2025-07-19',8,4,11,'/upload/ConfirmDeliveryIMG/z6820132212347_c8ccddc52fd7f7f3e960efaf318f057e.jpg',NULL,NULL,2550000,510000,NULL,NULL,NULL,NULL,'2025-07-19 16:23:22',NULL,0.00,NULL),(44,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,2330000,466000,'Tân Vũ','0919994398','caccccccccccccc, Xã Thọ Văn, Huyện Tam Nông, Tỉnh Phú Thọ','vietqr','2025-07-21 13:39:58',NULL,0.00,NULL),(45,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,1620000,324000,'Tân Vũ','0919994398','caccccccccccccc, Xã Phình Giàng, Huyện Điện Biên Đông, Tỉnh Điện Biên','cod','2025-07-21 13:40:47',NULL,0.00,NULL),(46,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,1620000,324000,'tan vu','0919994398','caAAAAAAAA, Xã Đông Lâm, Huyện Tiền Hải, Tỉnh Thái Bình','vietqr','2025-07-21 13:42:14',NULL,0.00,NULL),(47,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,2530000,506000,'tan vu','0919994398','caAAAAAAAA, Xã Đồng Quang, Huyện Gia Lộc, Tỉnh Hải Dương','cod','2025-07-21 13:44:57',NULL,0.00,NULL),(48,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,1620000,324000,'tan vu','0919994398','caAAAAAAAA, Xã Noọng Hẹt, Huyện Điện Biên, Tỉnh Điện Biên','vietqr','2025-07-21 13:53:04',NULL,0.00,NULL),(49,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,1720000,344000,'tan vu','0919994398','caAAAAAAAA, Xã Tây Phong, Huyện Tiền Hải, Tỉnh Thái Bình','vietqr','2025-07-21 13:54:56',NULL,0.00,NULL),(50,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,2330000,466000,'tan vu','0919994398','caAAAAAAAA, Thị trấn Điện Biên Đông, Huyện Điện Biên Đông, Tỉnh Điện Biên','vietqr','2025-07-21 13:55:39',NULL,0.00,NULL),(51,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,2330000,466000,'Tân Vũ','0919994398','caccccccccccccc, Xã Thủ Sỹ, Huyện Tiên Lữ, Tỉnh Hưng Yên','vietqr','2025-07-21 13:58:06',NULL,0.00,NULL),(52,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,2430000,486000,'Tân Vũ','0919994398','caccccccccccccc, Xã Hưng Nhân, Huyện Vĩnh Bảo, Thành phố Hải Phòng','cod','2025-07-21 13:58:18',NULL,0.00,NULL),(53,'2025-07-21',NULL,1,NULL,NULL,NULL,NULL,2430000,486000,'Tân Vũ','0919994398','caccccccccccccc, Xã Mường Pồn, Huyện Điện Biên, Tỉnh Điện Biên','cod','2025-07-21 13:58:52',NULL,0.00,NULL),(54,'2025-07-21',12,1,NULL,NULL,NULL,NULL,1030000,206000,'tan vu','0919994398','caAAAAAAAA, Xã Văn Môn, Huyện Yên Phong, Tỉnh Bắc Ninh','cod','2025-07-21 14:00:46',NULL,0.00,NULL),(55,'2025-07-21',1,1,NULL,NULL,NULL,NULL,3470000,694000,'Alice Admin','0901111001','123 Admin Street, Xã Bản Xèo, Huyện Bát Xát, Tỉnh Lào Cai','cod','2025-07-21 14:02:48',NULL,0.00,NULL);
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
  `unit_price` int DEFAULT NULL,
  `sellPrice` int DEFAULT NULL,
  `status` varchar(45) DEFAULT 'not done',
  `request_group_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_item_id`),
  UNIQUE KEY `order_item_id_UNIQUE` (`order_item_id`),
  KEY `order_id_idx` (`order_id`),
  KEY `bouquet_id_idx` (`bouquet_id`),
  CONSTRAINT `bouquet_id` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
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
INSERT INTO `payment` VALUES (22,2,8,1,110000,2,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
-- Table structure for table `repair_history`
--

DROP TABLE IF EXISTS `repair_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repair_history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `repair_id` int NOT NULL,
  `bouquet_id` int NOT NULL,
  `old_batch_id` int NOT NULL,
  `new_batch_id` int DEFAULT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `old_flower_name` varchar(255) DEFAULT NULL,
  `new_flower_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `repair_id` (`repair_id`),
  KEY `bouquet_id` (`bouquet_id`),
  KEY `old_batch_id` (`old_batch_id`),
  KEY `new_batch_id` (`new_batch_id`),
  CONSTRAINT `repair_history_ibfk_1` FOREIGN KEY (`repair_id`) REFERENCES `repair_orders` (`repair_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `repair_history_ibfk_2` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `repair_history_ibfk_3` FOREIGN KEY (`old_batch_id`) REFERENCES `flower_batch` (`batch_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `repair_history_ibfk_4` FOREIGN KEY (`new_batch_id`) REFERENCES `flower_batch` (`batch_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair_history`
--

LOCK TABLES `repair_history` WRITE;
/*!40000 ALTER TABLE `repair_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `repair_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repair_orders`
--

DROP TABLE IF EXISTS `repair_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repair_orders` (
  `repair_id` int NOT NULL AUTO_INCREMENT,
  `bouquet_id` int NOT NULL,
  `batch_id` int NOT NULL,
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'pending',
  PRIMARY KEY (`repair_id`),
  KEY `fk_repair_bouquet` (`bouquet_id`),
  KEY `fk_repair_batch` (`batch_id`),
  CONSTRAINT `fk_repair_batch` FOREIGN KEY (`batch_id`) REFERENCES `flower_batch` (`batch_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_repair_bouquet` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair_orders`
--

LOCK TABLES `repair_orders` WRITE;
/*!40000 ALTER TABLE `repair_orders` DISABLE KEYS */;
INSERT INTO `repair_orders` VALUES (1,10,7,'Giỏ hoa chứa lô hoa gần hết hạn: batch_id=7','2025-07-09 00:01:49','pending'),(2,10,7,'Giỏ hoa chứa lô hoa gần hết hạn: batch_id=7','2025-07-09 00:01:49','pending');
/*!40000 ALTER TABLE `repair_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requestflower`
--

DROP TABLE IF EXISTS `requestflower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requestflower` (
  `Order_ID` int NOT NULL,
  `Order_Item_ID` int NOT NULL,
  `Flower_ID` int NOT NULL,
  `Quantity` int DEFAULT NULL,
  `Status` varchar(45) DEFAULT 'pending',
  `Request_Creation_Date` date DEFAULT NULL,
  `Request_Confirmation_Date` date DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`Order_ID`,`Order_Item_ID`,`Flower_ID`),
  KEY `orderItemId_idx` (`Order_Item_ID`),
  KEY `flowerId_idx` (`Flower_ID`),
  CONSTRAINT `flowerId` FOREIGN KEY (`Flower_ID`) REFERENCES `flower_type` (`flower_id`),
  CONSTRAINT `orderId` FOREIGN KEY (`Order_ID`) REFERENCES `order` (`order_id`),
  CONSTRAINT `orderItemId` FOREIGN KEY (`Order_Item_ID`) REFERENCES `order_item` (`order_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requestflower`
--

LOCK TABLES `requestflower` WRITE;
/*!40000 ALTER TABLE `requestflower` DISABLE KEYS */;
/*!40000 ALTER TABLE `requestflower` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
INSERT INTO `shipment` VALUES (2,2,'2025-05-12','2025-05-13',12,1,'Backup Warehouse','404 Rose Street');
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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokenforgetpassword`
--

LOCK TABLES `tokenforgetpassword` WRITE;
/*!40000 ALTER TABLE `tokenforgetpassword` DISABLE KEYS */;
INSERT INTO `tokenforgetpassword` VALUES (1,'45f1544e-a5b0-48fa-a31e-9f9e412853ba','2025-05-23 18:08:49',_binary '\0',9),(2,'90da40eb-5a65-4abf-9b8f-da7889681dd9','2025-05-23 18:25:34',_binary '\0',9),(3,'7a1f8f19-9c03-45a3-8d85-de1612e247c6','2025-05-23 18:25:53',_binary '\0',9),(4,'468e926e-fa9b-41d8-94e4-4b5309a8546a','2025-05-23 18:26:11',_binary '\0',9),(5,'46c73120-0bd3-40c3-a7c9-d8329610689d','2025-05-23 18:28:12',_binary '\0',9),(6,'df881f25-f4e0-4318-8543-c20ab2428afb','2025-05-23 18:29:49',_binary '\0',9),(7,'4c8c54f2-5598-4d02-ad0c-8333e584c042','2025-05-23 18:29:50',_binary '\0',9),(8,'34014995-ede7-49b5-8375-688101611208','2025-05-23 13:05:56',_binary '\0',9),(9,'7b812d2b-c1b0-4152-9985-990929429d70','2025-05-23 13:09:29',_binary '\0',9),(10,'2fcb45e6-6a89-4e11-9db1-e29ad0007fe6','2025-05-23 23:50:00',_binary '\0',9),(11,'8f8e6c1c-4c6b-43c7-b93b-8b55a513a2c3','2025-05-23 23:54:38',_binary '\0',9),(12,'ce1ff1b5-5b96-43b8-b8e5-f88831b48a88','2025-05-24 00:02:03',_binary '\0',9),(13,'ec978c0b-c179-4b1e-86a5-4ccc38dc439d','2025-05-24 00:06:17',_binary '\0',9),(14,'7df613ef-1e9f-4d21-98b2-5b2471fd5bd9','2025-05-24 00:11:47',_binary '\0',9),(15,'f5528473-12c1-4eff-bc70-41ed552cedcb','2025-05-24 00:22:55',_binary '\0',9),(16,'5edb0eb5-dc40-41a9-bf49-9643b6702469','2025-05-24 00:28:53',_binary '\0',9),(17,'69b9ec7d-26dc-4518-9066-1325ffcfdf64','2025-05-24 00:37:29',_binary '\0',9),(18,'6108536e-d130-4fac-bcda-eb46d7db407a','2025-05-24 00:37:40',_binary '\0',9),(19,'4d8f7f9d-9247-485b-92dd-f8fb021c37f8','2025-05-24 01:13:20',_binary '',9),(20,'bcdd8229-a0eb-4e82-9233-7c813493e421','2025-05-24 01:36:14',_binary '',9),(21,'8745114e-a111-44fd-a3fa-0fe40ab116cc','2025-05-24 01:44:36',_binary '\0',9),(22,'3fcef83b-66b2-4f5e-bfe4-c95e021a5374','2025-05-24 01:52:15',_binary '',9),(23,'214365c2-4f2d-4561-8642-0fce17ba98a1','2025-05-24 09:35:58',_binary '',9),(24,'744ee63e-3ee0-42e0-a235-1eb50d127d54','2025-05-24 09:38:31',_binary '',9),(25,'8d28c087-51de-470c-99a0-7432076c9a94','2025-05-24 09:43:42',_binary '',9),(26,'d56935d7-a107-4867-a657-fd793f481f01','2025-05-24 09:45:42',_binary '',9),(27,'7c3d6ea4-cd95-4409-a31a-4035464693c9','2025-05-26 09:00:59',_binary '',9),(28,'455c9b85-3fb1-4197-b07e-df238627edfd','2025-05-26 18:16:29',_binary '\0',9),(29,'938806d2-65b6-4c6e-8828-f75cf22c303e','2025-05-26 18:18:58',_binary '\0',9),(30,'bdc923c0-d9f4-4716-a2f8-4741003fa374','2025-05-29 09:16:15',_binary '\0',9),(31,'04dee5a8-7527-4fac-94cb-acb188ac59c2','2025-06-02 20:32:49',_binary '',9),(32,'fc7e9ff6-2601-4c8a-b73b-cae7e7945121','2025-06-05 09:15:56',_binary '',10),(33,'64b0818d-02ce-4059-80b4-ef553a2fe92c','2025-06-09 19:44:41',_binary '\0',10),(34,'1d480c35-39c6-44ac-aa3a-6a3225bbd5b4','2025-06-12 08:01:36',_binary '\0',10),(35,'aee87711-379f-41e2-82ce-ec02c4740c9d','2025-06-12 19:25:03',_binary '\0',1),(36,'788410c3-59e5-4290-9cb2-c43d29fcc085','2025-07-15 04:25:19',_binary '\0',12),(37,'ff5a2fe9-93dc-43bc-b32d-ded87f4ec152','2025-07-15 04:28:41',_binary '\0',12),(38,'667475b1-da58-44c6-8878-8cab0b8ab868','2025-07-15 04:28:44',_binary '\0',12),(39,'6f52ba0f-add9-4f72-8eca-02efe2a7739c','2025-07-15 04:28:46',_binary '\0',12),(40,'b3759f08-5a60-4a00-9942-1ed0bccb0221','2025-07-15 04:28:53',_binary '\0',11),(41,'8732a3dc-b83a-4783-81ad-c1a66f0f217e','2025-07-15 04:33:41',_binary '\0',11),(42,'f34ac251-3a70-49cb-99d9-e7e7c9faf0a1','2025-07-15 04:33:49',_binary '\0',11),(43,'8aa541b6-f876-4dfa-b1b8-f12919d201cc','2025-07-15 04:35:58',_binary '\0',11),(44,'8d1877c2-aa78-4744-94fc-fbca763010ab','2025-07-15 04:36:07',_binary '\0',12),(45,'40d40a98-6f96-4785-a3e5-3121b3d0f623','2025-07-15 04:36:49',_binary '\0',12),(46,'7b12400a-cd39-44c7-aa87-9309e058ccde','2025-07-15 04:42:30',_binary '\0',12),(47,'206c996d-bd2e-4a67-8e4d-368f7ec78e31','2025-07-17 15:48:19',_binary '',12);
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
  `Address` varchar(255) DEFAULT NULL,
  `Role` int NOT NULL,
  `status` varchar(10) DEFAULT 'active',
  PRIMARY KEY (`User_ID`),
  UNIQUE KEY `User_ID_UNIQUE` (`User_ID`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  KEY `Role_idx` (`Role`),
  CONSTRAINT `Role` FOREIGN KEY (`Role`) REFERENCES `role` (`Role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin01','$2a$10$Z6PeNnylaCP5O3tblM0SvejkMXoayRhCCTMj13s39DhrLumlhexG.','Alice Admin','alice@flower.com','0901111001','123 Admin Street',1,'active'),(2,'manager01','$2a$10$Ppw/3o3ugj2Mz4k6jRJnh.9EU3c.yQCMG49dIkccKRkeqnUO.xY9G','Bob Manager','bob@flower.com','0901111002','456 Manager Lane',2,'active'),(3,'seller01','$2a$10$tSuwcCCzTEyZ/5u9yet1TOOmKRzG8CyazFR2bZ6JesroUVcSckaS.','Carol Seller','carol@flower.com','0901111003','789 Seller Road',3,'active'),(4,'marketer01','$2a$10$1Te9hDvbo5e5UokNI/g4E.4r731nGFVe8r1DgVMCXKo4ZcxyHeJSa','David Marketer','david@flower.com','0901111004','101 Marketing Blvd',4,'active'),(5,'warehouse01','$2a$10$nITsu.VVg9mfWQj0K3YcZeM.GJIhGb9cvJ8xGsxeqaiWh9.bQ/V2m','Eva Warehouse','eva@flower.com','0901111005','202 Warehouse Ave',5,'active'),(6,'guest01','$2a$10$YrA1Uo2/.DzVfTJWtQvQv.Inx2adbtNz7Ffp6bCKIcoz4RKEaFVQO','Frank Guest','frank@flower.com','0901111006','No Address',6,'active'),(7,'cust01','$2a$10$iEeGuer0SeMaS9cyxyU7KOVE/icEWxtdgrERVc5HCn2Eo12eS6hwC','Grace Customer','grace@flower.com','0901111007','303 Customer Place',7,'active'),(8,'cust02','$2a$10$160t4egnUs0ior5RQoqIHufioc8RC3bzcezPyiayyrtcVCFyfnica','Helen Buyer','helen@flower.com','0901111008','404 Rose Street',7,'active'),(11,'shipper','$2a$10$ECPr17gXBybS4nWf1Nw7x.cq9a00AM/JW2l/8OmMH.l3M7y.jIOYW','Tan VU','vuminhtan2004@gmail.com','0977679888','123 Admin Street',8,'active'),(12,'taness','$2a$10$5EmYJ49G27s9LboBsIDxbuxPeISr0Nfuh8d79x.7s.0sZXIbZA/bW','tan vu','tanvmhe186791@fpt.edu.vn','0919994398','ca',7,'active'),(13,'quangvuminh','$2a$10$NZte8aSF1HSXp8KNH6yuLeQUUucasYq7R03dLaMWQu/F4D1l9x5sW','Quang Vu','lem03726@gmail.com','0786709182','368B Quang Trung, La Khê, Hà Đông, Hà Nội',7,'active'),(14,'admin02','$2a$10$L/BC/a7UJYd.LM6yQoMwUeoOG/p4q7QkBV3KjaK8vLEfwgFbDr/wC','Hoang Trung Kien','kienhthe181323@fpt.edu.vn','0123456789','123 Test Street',1,'active'),(15,'quang','$2a$10$oxNlDYxvg.7WadSgNzytKe4FCCdMeefLwbWx4Sksvapkyz6unUsSG','Quang Vũ','quangvmhe181542@fpt.edu.vn','0123456789','Đống Đa Hà NỘi',2,'active'),(16,'quangShipper','$2a$10$t8PJLIdvLX/640BGl0OROOhW0XManwEUlWTYgtq0WvkUj9UDJleVC','Vũ Quang','quangvmhe181542@fpt.edu.vn','0123456789','Hà Nội',8,'active'),(17,'nnnnn','$2a$10$sF5wFG7sW09gg4TpQWFgNu72j7Ik05un5drF9aAVyQn.bLY8xkTwC','Tân Vũ','he186791vuminhtan@gmail.com','0919994398','ca',7,'active'),(18,'quangAdmin','$2a$10$5P493qPPhEwjiQfoSBlo2.NHEhJ0AB2XCKf.7r1HyT9MmllkPPUWy','Vu Minh Quang','quangvmhe181542@fpt.edu.vn','0786709182','Ha Dong, Ha Noi',1,'active');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_discount_code`
--

DROP TABLE IF EXISTS `user_discount_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_discount_code` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `discount_code_id` int NOT NULL,
  `used_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `discount_code_id` (`discount_code_id`),
  CONSTRAINT `user_discount_code_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`User_ID`),
  CONSTRAINT `user_discount_code_ibfk_2` FOREIGN KEY (`discount_code_id`) REFERENCES `discount_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_discount_code`
--

LOCK TABLES `user_discount_code` WRITE;
/*!40000 ALTER TABLE `user_discount_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_discount_code` ENABLE KEYS */;
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
-- Table structure for table `wholesale_quote_flower_detail`
--

DROP TABLE IF EXISTS `wholesale_quote_flower_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wholesale_quote_flower_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `wholesale_request_id` int NOT NULL,
  `bouquet_id` int NOT NULL,
  `flower_id` int NOT NULL,
  `flower_ws_price` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ws_request_idx` (`wholesale_request_id`),
  KEY `fk_ws_bouquet_idx` (`bouquet_id`),
  KEY `fk_ws_flower_idx` (`flower_id`),
  CONSTRAINT `fk_ws_bouquet` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`),
  CONSTRAINT `fk_ws_flower` FOREIGN KEY (`flower_id`) REFERENCES `flower_type` (`flower_id`),
  CONSTRAINT `fk_ws_request` FOREIGN KEY (`wholesale_request_id`) REFERENCES `wholesale_quote_request` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wholesale_quote_flower_detail`
--

LOCK TABLES `wholesale_quote_flower_detail` WRITE;
/*!40000 ALTER TABLE `wholesale_quote_flower_detail` DISABLE KEYS */;
INSERT INTO `wholesale_quote_flower_detail` VALUES (119,37,2,1,20000),(120,37,2,2,35000),(121,37,2,3,16000),(122,37,2,8,40000),(123,38,6,5,15000),(124,39,2,1,15000),(125,39,2,2,35000),(126,39,2,3,16000),(127,39,2,8,40000),(128,40,5,3,16000);
/*!40000 ALTER TABLE `wholesale_quote_flower_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wholesale_quote_request`
--

DROP TABLE IF EXISTS `wholesale_quote_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wholesale_quote_request` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `bouquet_id` int NOT NULL,
  `requested_quantity` int NOT NULL,
  `note` mediumtext,
  `quoted_price` int DEFAULT NULL,
  `total_price` int DEFAULT NULL,
  `quoted_at` datetime DEFAULT NULL,
  `responded_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('PENDING','QUOTED','ACCEPTED','REJECTED','SHOPPING','COMPLETED','EMAILED') DEFAULT 'SHOPPING',
  `expense` int DEFAULT NULL,
  `request_group_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userWholeSale_idx` (`user_id`),
  KEY `bouquetWholeSale_idx` (`bouquet_id`),
  KEY `idx_user_created_group` (`user_id`,`created_at`,`request_group_id`),
  CONSTRAINT `bouquetWholeSale` FOREIGN KEY (`bouquet_id`) REFERENCES `bouquet` (`Bouquet_ID`),
  CONSTRAINT `userWholeSale` FOREIGN KEY (`user_id`) REFERENCES `user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wholesale_quote_request`
--

LOCK TABLES `wholesale_quote_request` WRITE;
/*!40000 ALTER TABLE `wholesale_quote_request` DISABLE KEYS */;
INSERT INTO `wholesale_quote_request` VALUES (37,13,2,50,'hello',1089000,54450000,'2025-07-22 00:00:00','2025-07-22 00:00:00','2025-07-22 00:00:00','ACCEPTED',363000,'2fc3ed51-0410-4700-a787-1b32e06c4028'),(38,13,6,52,'hi',360000,18720000,'2025-07-22 00:00:00','2025-07-22 00:00:00','2025-07-22 00:00:00','ACCEPTED',120000,'2fc3ed51-0410-4700-a787-1b32e06c4028'),(39,13,2,50,'test',1074000,53700000,'2025-07-22 00:00:00',NULL,'2025-07-22 00:00:00','EMAILED',358000,'0b4739c2-3a35-47f6-99e3-d702a0348335'),(40,13,5,60,'test',240000,14400000,'2025-07-22 00:00:00',NULL,'2025-07-22 00:00:00','EMAILED',80000,'0b4739c2-3a35-47f6-99e3-d702a0348335');
/*!40000 ALTER TABLE `wholesale_quote_request` ENABLE KEYS */;
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

-- Dump completed on 2025-07-25  9:40:14
