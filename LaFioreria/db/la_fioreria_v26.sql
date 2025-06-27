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
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
INSERT INTO `blog` VALUES (1,'6 Tips to Keep Your Flowers Fresh','Learn how to maintain your flowers for longer...','2025-05-19 07:54:53','2025-05-19 07:54:53','https://jardincaribe.com/wp-content/uploads/2020/12/Infograpic-6-tips.png','Test precontext','Hidden',2,6),(2,'The Meaning Behind Popular Flowers','Discover the symbolism of roses, lilies, and more...','2025-05-19 07:54:53','2025-05-19 07:54:53','https://flourishbakingcompany.com/cdn/shop/articles/chart_of_flowers_800x.jpg?v=1580648030','Test precontext','Active',4,7),(3,'How to Choose the Perfect Bouquet for Any Occasion','A guide to picking the right flowers...','2025-05-19 07:54:53','2025-05-19 07:54:53','https://cdn.shopify.com/s/files/1/2776/7900/files/Blog_10.jpg?v=1691462989','Test precontext','Active',4,7),(4,'The Art of Flower Arranging','Flower arranging is a delicate art that brings joy to many. In this post, we’ll explore techniques and tips for stunning floral designs.','2025-06-01 03:00:00','2025-06-01 03:00:00','https://cdn-media.sforum.vn/storage/app/media/anh-dep-72.jpg','A quick guide on flower arrangement tips.','Active',1,1),(5,'Summer Bouquets to Brighten Your Home','Discover the top flower combinations for vibrant summer bouquets. From sunflowers to daisies, make your home glow with color.','2025-06-05 02:00:00','2025-06-05 02:30:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxEEZV6f8peybupvxehPYCV_WuwllvucX9Cw&s','Top flower picks to refresh your home this summer.','Active',1,2),(6,'Behind the Scenes at La Fioreria','Take a look at what goes into preparing our floral collections every day. From early morning market visits to final arrangements.','2025-06-07 01:00:00','2025-06-08 07:20:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGsY-zxNnrhZxqj4a_6Kvuv_tU1oGJWdAY1g&s','Explore our daily routine and dedication to floral perfection.','Active',1,1),(7,'Caring for Your Roses: A Simple Guide','Roses are timeless, but they need the right care to stay beautiful. We’ll cover pruning, watering, and more.','2025-06-10 06:45:00','2025-06-10 06:45:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzbYTyntexjeYwbQP1GHJ2KwQAg_zZHmuecQ&s','Essential rose care tips for beginners.','Active',1,3),(8,'Top 5 Wedding Flower Trends in 2025','Planning a wedding? Check out these trending flower styles to make your special day unforgettable.','2025-06-12 04:00:00','2025-06-12 04:00:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYZQt3C_CBl287dMfxW1cttshB7VRWgn14ZQ&s','Must-know wedding flower styles this year.','Active',1,4),(9,'Why Flowers Make the Best Gifts','Flowers convey emotions in ways words sometimes can’t. Here’s why a bouquet is always a good idea.','2025-06-14 03:10:00','2025-06-14 05:00:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJznMaDtgrRc1hXSb577HXXDxYbF8tpaZgKw&s','Reasons why flowers are perfect for any occasion.','Active',1,2),(10,'Sustainable Floristry at La Fioreria','We believe in beauty with responsibility. Learn about our eco-friendly practices and sustainable choices.','2025-06-16 08:00:00','2025-06-17 02:00:00','https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/482763hUR/anh-mo-ta.png','How we keep floristry green and clean.','Active',1,1),(11,'Orchids: The Elegant Flower','Orchids symbolize beauty and strength. Discover how to grow and care for these stunning plants.','2025-06-18 01:30:00','2025-06-18 01:30:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBS9bJEXw5AYRgmLY_9Nyr79oQFPYEtJjmhA&s','A guide to caring for orchids with ease.','Active',1,3),(12,'Creating Floral Centerpieces Like a Pro','Level up your home décor with professional-looking centerpieces using simple materials and techniques.','2025-06-20 10:45:00','2025-06-21 01:00:00','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRE54qaXx3lT58QeBmeV7o0-iM_pvK-lz-FdA&s','Make your dining table bloom with flair.','Active',1,4),(13,'Flower Meanings: What Your Bouquet Says','Every flower has a message. Learn the language of flowers and what your bouquet might be saying.','2025-06-22 02:15:00','2025-06-22 02:15:00','https://cdn-media.sforum.vn/storage/app/media/anh-dep-68.jpg','Decode the hidden meanings behind common flowers.','Active',1,2);
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bouquet`
--

LOCK TABLES `bouquet` WRITE;
/*!40000 ALTER TABLE `bouquet` DISABLE KEYS */;
INSERT INTO `bouquet` VALUES (1,'Romantic Rose','A bouquet of fresh roses',1,250000,1250000,'valid'),(2,'Spring Delight','Colorful tulips and lilies',2,358000,1790000,'valid'),(3,'Sunny Love','Sunflowers and roses for a cheerful gift',5,70000,350000,'invalid'),(4,'Rose Radiance','A vibrant bouquet of fresh red roses symbolizing love and passion.',1,500000,2500000,'valid'),(5,'Lily Light','Elegant lilies arranged to brighten up any occasion.',2,130000,650000,'valid'),(6,'Sunflower Smile','Cheerful sunflowers to bring joy and energy.',3,120000,600000,'valid'),(7,'Tulip Treasure','An exotic bouquet of multicolored tulips.',4,500000,2500000,'valid'),(8,'Orchid Elegance','Luxurious white orchids in an elegant arrangement.',5,1200000,6000000,'valid'),(9,'Sun Dreams','A cheerful bouquet featuring sunflowers and baby\'s breath.',1,345000,1725000,'valid'),(10,'Peony Passion','Romantic peonies paired with soft accents.',2,700000,3500000,'valid'),(11,'Carnation Bliss','Elegant mix of carnations and daisies.',2,720000,3600000,'valid'),(12,'Hydrangea Harmony','Soothing tones with hydrangeas and baby\'s breath.',3,450000,2250000,'valid'),(27,'noo','123120301230102 3012 30123 1023 012301230123 12 31 23 1 231 0230',1,60000,300000,'valid'),(35,'Test add','bvcnbvmnb',4,65000,325000,'valid');
/*!40000 ALTER TABLE `bouquet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bouquet_images`
--

LOCK TABLES `bouquet_images` WRITE;
/*!40000 ALTER TABLE `bouquet_images` DISABLE KEYS */;
INSERT INTO `bouquet_images` VALUES (1,'1750524624204_ERD_GUIDE.png'),(1,'1750525046297_pt12.png'),(1,'1750590818622_sentinels-win-first-international-valorant-event.png'),(1,'1750590818634_472017959_1113363040262417_4635908413873429904_n.jpg'),(1,'1750590818634_b80cba3d-bfe8-4378-b7c7-828854b69244_low.jpg'),(2,'1750439131747_gk6kebe1ojjyxdgv0ok3.jpg'),(2,'1750704971253_wp12585891-sentinels-valorant-wallpapers.png'),(2,'1750704971265_472017959_1113363040262417_4635908413873429904_n.jpg'),(2,'1750704971265_rnnrJbh.jpeg'),(2,'1750704971265_sentinels-win-first-international-valorant-event.png'),(3,'1750258347762_fb150004_2_cheerful_sunflowers_bouquet_2_2_res.jpg'),(4,'1750258391571_image_fd2696a5-aa10-47d1-bf2c-2c33a7e65989.jpg'),(5,'1750258420386_thefloristmarket-scentales-28573-30243_1080x1080.jpg'),(6,'1750258456097_IMG_6004-scaled.jpg'),(7,'1750258487007_bohoa15.jpg'),(8,'1750258587648_bo-hoa-lan-trang-2.jpg'),(9,'1750316204090_Sunny-Baby-sunflower-bouquet-selangor.png'),(10,'1750316286853_lasting-love-peony-fafulflorist-896234.jpg'),(11,'1750316332512_DP09260.jpg'),(12,'1750316360972_whisper-blue-hydrangea-baby-breath-flower-bouquet-01.jpg'),(27,'1750593572167_8f95c217ba2d2346b499f9772ec15d3a.jpg'),(27,'1750593572188_227a65c4b3193e92b421cd3224eb4395.jpg'),(27,'1750593572192_480662810_643958421413188_2211506288672214070_n.jpg'),(27,'1750593572198_476573883_612916858033363_1219310901361798773_n.jpg'),(35,'1750670476639_sentinels-win-first-international-valorant-event.png'),(35,'1750670476645_472017959_1113363040262417_4635908413873429904_n.jpg'),(35,'1750670476646_b80cba3d-bfe8-4378-b7c7-828854b69244_low.jpg'),(35,'1750670476647_SnapshotLoad.jpg');
/*!40000 ALTER TABLE `bouquet_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bouquet_raw`
--

LOCK TABLES `bouquet_raw` WRITE;
/*!40000 ALTER TABLE `bouquet_raw` DISABLE KEYS */;
INSERT INTO `bouquet_raw` VALUES (1,1,10),(2,1,1),(2,2,5),(2,3,3),(2,8,3),(3,1,2),(3,5,4),(4,1,20),(5,3,5),(6,5,8),(7,2,10),(8,4,15),(9,5,3),(9,10,10),(10,7,7),(11,6,12),(11,8,10),(12,9,3),(12,10,10),(27,8,1),(35,4,1),(35,5,1);
/*!40000 ALTER TABLE `bouquet_raw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cartdetails`
--

LOCK TABLES `cartdetails` WRITE;
/*!40000 ALTER TABLE `cartdetails` DISABLE KEYS */;
INSERT INTO `cartdetails` VALUES (1,7,1,2),(2,7,3,1),(3,8,2,4),(4,8,1,1),(10,12,5,1),(14,1,2,2),(15,1,1,4),(16,1,6,4),(18,13,1,3),(19,13,2,1),(20,13,6,1);
/*!40000 ALTER TABLE `cartdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Romantic','Flowers meant to express love, ideal for Valentine’s Day, proposals, or anniversaries.'),(2,'Birthday','Bouquets designed to celebrate someone’s birthday with joy and color.'),(3,'Congratulations','Arrangements to celebrate milestones like graduations, promotions, or achievements.'),(4,'Sympathy','Sympathy flowers to offer comfort during times of loss or hardship.'),(5,'Anniversary','Bouquets for celebrating wedding anniversaries and long-lasting love.'),(6,'Tips & Tricks','Flower care tips and tricks'),(7,'Occasions','Blogs about special occasions and flower meanings'),(8,'New Arrivals','News about new bouquets and flowers');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'Tân Vũ','vuminhtan2004@gmail.com','lead','123','2025-05-22 23:45:27'),(2,'Tân Vũ','vuminhtan2004@gmail.com','lead','avavxvx','2025-05-22 23:46:19'),(3,'Tân Vũ','vuminhtan2004@gmail.com','lead','11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111','2025-05-22 23:46:51'),(4,'Tân Vũ','vuminhtan2004@gmail.com','ccc','câccacacac','2025-06-05 23:06:59');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `flower_batch`
--

LOCK TABLES `flower_batch` WRITE;
/*!40000 ALTER TABLE `flower_batch` DISABLE KEYS */;
INSERT INTO `flower_batch` VALUES (1,1,500,15000,'2025-07-01','2025-08-31',NULL,1,'fresh'),(2,2,300,35000,'2025-07-01','2025-08-25',NULL,1,'fresh'),(3,3,200,16000,'2025-07-01','2025-08-28',NULL,1,'fresh'),(4,4,100,55000,'2025-07-01','2025-08-30',NULL,2,'fresh'),(5,5,150,10000,'2025-07-01','2025-08-29',NULL,2,'fresh'),(6,6,400,7000,'2025-07-01','2025-07-05',NULL,1,'fresh'),(7,7,250,70000,'2025-07-01','2025-07-10',NULL,1,'fresh'),(8,8,350,40000,'2025-07-01','2025-07-03',NULL,2,'fresh'),(9,9,200,35000,'2025-07-01','2025-07-07',NULL,2,'fresh'),(10,10,500,20000,'2025-07-01','2025-07-08',NULL,1,'fresh'),(11,11,0,30000,'2025-07-01','2025-07-06',0,1,'fresh');
/*!40000 ALTER TABLE `flower_batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `flower_discard`
--

LOCK TABLES `flower_discard` WRITE;
/*!40000 ALTER TABLE `flower_discard` DISABLE KEYS */;
INSERT INTO `flower_discard` VALUES (7,1,'2025-07-01',10,'Quá hạn','Quá hạn sử dụng'),(8,1,'2025-07-02',5,'Mốc','Bị mốc do độ ẩm'),(9,2,'2025-07-03',7,'Hỏng do nhiệt độ','Bảo quản không đúng cách'),(10,3,'2025-07-04',4,'Dập nát','Lỗi khi vận chuyển'),(11,4,'2025-08-01',8,'Quá hạn','Không bán kịp'),(12,5,'2025-08-02',2,'Mốc','Do kho lạnh bị hỏng');
/*!40000 ALTER TABLE `flower_discard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `flower_type`
--

LOCK TABLES `flower_type` WRITE;
/*!40000 ALTER TABLE `flower_type` DISABLE KEYS */;
INSERT INTO `flower_type` VALUES (1,'Rose','3CC66EEC-0B20-4AC4-B2FD-E82A4734E596.jpeg',_binary ''),(2,'Tulip','Tulip.jpg',_binary ''),(3,'Lily','Lily.jpg',_binary ''),(4,'Orchid','Orchid.jpg',_binary ''),(5,'Sunflower','Sunflower.jpg',_binary ''),(6,'Daisy','Daisy.jpg',_binary ''),(7,'Peony','Peony.jpg',_binary ''),(8,'Carnation','Carnation.jpg',_binary ''),(9,'Hydrangea','Hydrangea.png',_binary ''),(10,'Baby\'s Breath','Baby\'s Breath.jpg',_binary ''),(11,'Lavender','Lavender.jpg',_binary '');
/*!40000 ALTER TABLE `flower_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `method`
--

LOCK TABLES `method` WRITE;
/*!40000 ALTER TABLE `method` DISABLE KEYS */;
INSERT INTO `method` VALUES (1,'Cash'),(2,'QR Payment');
/*!40000 ALTER TABLE `method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'2025-08-10',7,1,11,NULL,NULL,NULL,NULL,NULL),(2,'2025-08-11',8,4,11,'/uploads/8f95c217ba2d2346b499f9772ec15d3a.jpg',NULL,NULL,0,1222),(9,'2025-06-25',7,1,11,NULL,NULL,NULL,0,720000),(10,'2025-06-25',7,1,11,NULL,NULL,NULL,0,120000);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,1,1,250000,1250000,'not done'),(2,2,3,1,110000,550000,'not done'),(10,9,11,1,720000,NULL,'not done'),(11,9,11,1,720000,NULL,'not done'),(12,10,6,1,120000,400000,'done');
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `order_status`
--

LOCK TABLES `order_status` WRITE;
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
INSERT INTO `order_status` VALUES (1,'Pending'),(2,'Processing'),(3,'Shipped'),(4,'Delivered'),(5,'Cancelled');
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (21,1,7,1,250000,1,1),(22,2,8,1,110000,2,2);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `payment_status`
--

LOCK TABLES `payment_status` WRITE;
/*!40000 ALTER TABLE `payment_status` DISABLE KEYS */;
INSERT INTO `payment_status` VALUES (1,'Unpaid'),(2,'Paid'),(3,'Refunded');
/*!40000 ALTER TABLE `payment_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `raw_flower`
--

LOCK TABLES `raw_flower` WRITE;
/*!40000 ALTER TABLE `raw_flower` DISABLE KEYS */;
INSERT INTO `raw_flower` VALUES (1,'Rose','500',25000,'2025-08-31',1,'https://flooers.co.uk/wp-content/uploads/2023/02/3CC66EEC-0B20-4AC4-B2FD-E82A4734E596.jpeg',NULL,15000,_binary ''),(2,'Tulip','300',50000,'2025-08-25',1,'https://mobileimages.lowes.com/productimages/06095299-423a-422e-af57-7b50460097b3/02989030.jpg?size=pdhz',NULL,35000,_binary ''),(3,'Lily','200',26000,'2025-08-28',1,'https://flowerexplosion.com/cdn/shop/products/white-asiatic-lily_900x.jpg?v=1658372981',NULL,16000,_binary ''),(4,'Orchid','100',80000,'2025-08-30',2,'https://ahsam.imgix.net/assets/img/dictionary/orchid-blue-wall.jpg?w=450&dpr=2',NULL,55000,_binary ''),(5,'Sunflower','150',15000,'2025-08-29',2,'https://www.highmowingseeds.com/media/catalog/product/cache/95cbc1bb565f689da055dd93b41e1c28/7/1/7100-1.jpg',NULL,10000,_binary ''),(6,'Daisy','400',10000,'2025-07-05',1,'https://www.cumbriawildflowers.co.uk/image/data/products/Leucanthemum-vulgare2.jpg',NULL,7000,_binary ''),(7,'Peony','250',100000,'2025-07-10',1,'https://bizweb.dktcdn.net/thumb/grande/100/442/027/products/thie-t-ke-chu-a-co-te-n-1-png-dd9b3fd9-4e4c-4bdf-ba60-136fbbe6fc55.jpg?v=1713328120593',NULL,70000,_binary ''),(8,'Carnation','350',60000,'2025-07-03',2,'https://premierseedsdirect.com/wp-content/uploads/2017/02/Carnation-Scarlet-Red-2-scaled.jpg',NULL,40000,_binary ''),(9,'Hydrangea','200',50000,'2025-07-07',2,'https://ecolafa.com/wp-content/uploads/2021/10/hoa-cam-tu-cau.png',NULL,35000,_binary ''),(10,'Baby\'s Breath','500',30000,'2025-07-08',1,'https://www.bloombybunches.ca/cdn/shop/files/babys-breath-gypsophila-white-million-star-diy-wedding-events-720.webp?v=1740864684',NULL,20000,_binary ''),(11,'Lavender','0',45000,'2025-07-06',1,'https://charsawfarms.com/cdn/shop/files/PurpleBouquetlavender2.jpg?v=1710207668&width=1946',0,30000,_binary '');
/*!40000 ALTER TABLE `raw_flower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `repair_orders`
--

LOCK TABLES `repair_orders` WRITE;
/*!40000 ALTER TABLE `repair_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `repair_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'Sales Manager'),(3,'Seller'),(4,'Marketer'),(5,'Warehouse Staff'),(6,'Guest'),(7,'Customer'),(8,'Shipper');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
INSERT INTO `shipment` VALUES (1,1,'2025-05-11','2025-05-12',11,1,'Main Warehouse','303 Customer Place'),(2,2,'2025-05-12','2025-05-13',12,1,'Backup Warehouse','404 Rose Street');
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `slider`
--

LOCK TABLES `slider` WRITE;
/*!40000 ALTER TABLE `slider` DISABLE KEYS */;
INSERT INTO `slider` VALUES (1,'Spring Sale - Up to 30% Off','https://www.shutterstock.com/image-vector/spring-sale-30-off-handwritten-typography-1927215506','https://example.com/sale',1,'2025-05-19 08:05:45','2025-05-19 08:05:45',1),(2,'New Arrivals - Exotic Flowers','hhttps://juneflowers.ae/wp-content/uploads/2024/10/exotic-flowers-arrangements.jpg','https://example.com/new',1,'2025-05-19 08:05:45','2025-05-19 08:05:45',2),(3,'Mother\'s Day Special','https://as2.ftcdn.net/v2/jpg/02/64/43/55/1000_F_264435588_jYNwyWXusTSFZZsOfAZ2hpY4EwcdKAmt.jpg','https://example.com/mothers-day',1,'2025-05-19 08:05:45','2025-05-19 08:05:45',1);
/*!40000 ALTER TABLE `slider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tokenforgetpassword`
--

LOCK TABLES `tokenforgetpassword` WRITE;
/*!40000 ALTER TABLE `tokenforgetpassword` DISABLE KEYS */;
INSERT INTO `tokenforgetpassword` VALUES (1,'45f1544e-a5b0-48fa-a31e-9f9e412853ba','2025-05-23 18:08:49',_binary '\0',9),(2,'90da40eb-5a65-4abf-9b8f-da7889681dd9','2025-05-23 18:25:34',_binary '\0',9),(3,'7a1f8f19-9c03-45a3-8d85-de1612e247c6','2025-05-23 18:25:53',_binary '\0',9),(4,'468e926e-fa9b-41d8-94e4-4b5309a8546a','2025-05-23 18:26:11',_binary '\0',9),(5,'46c73120-0bd3-40c3-a7c9-d8329610689d','2025-05-23 18:28:12',_binary '\0',9),(6,'df881f25-f4e0-4318-8543-c20ab2428afb','2025-05-23 18:29:49',_binary '\0',9),(7,'4c8c54f2-5598-4d02-ad0c-8333e584c042','2025-05-23 18:29:50',_binary '\0',9),(8,'34014995-ede7-49b5-8375-688101611208','2025-05-23 13:05:56',_binary '\0',9),(9,'7b812d2b-c1b0-4152-9985-990929429d70','2025-05-23 13:09:29',_binary '\0',9),(10,'2fcb45e6-6a89-4e11-9db1-e29ad0007fe6','2025-05-23 23:50:00',_binary '\0',9),(11,'8f8e6c1c-4c6b-43c7-b93b-8b55a513a2c3','2025-05-23 23:54:38',_binary '\0',9),(12,'ce1ff1b5-5b96-43b8-b8e5-f88831b48a88','2025-05-24 00:02:03',_binary '\0',9),(13,'ec978c0b-c179-4b1e-86a5-4ccc38dc439d','2025-05-24 00:06:17',_binary '\0',9),(14,'7df613ef-1e9f-4d21-98b2-5b2471fd5bd9','2025-05-24 00:11:47',_binary '\0',9),(15,'f5528473-12c1-4eff-bc70-41ed552cedcb','2025-05-24 00:22:55',_binary '\0',9),(16,'5edb0eb5-dc40-41a9-bf49-9643b6702469','2025-05-24 00:28:53',_binary '\0',9),(17,'69b9ec7d-26dc-4518-9066-1325ffcfdf64','2025-05-24 00:37:29',_binary '\0',9),(18,'6108536e-d130-4fac-bcda-eb46d7db407a','2025-05-24 00:37:40',_binary '\0',9),(19,'4d8f7f9d-9247-485b-92dd-f8fb021c37f8','2025-05-24 01:13:20',_binary '',9),(20,'bcdd8229-a0eb-4e82-9233-7c813493e421','2025-05-24 01:36:14',_binary '',9),(21,'8745114e-a111-44fd-a3fa-0fe40ab116cc','2025-05-24 01:44:36',_binary '\0',9),(22,'3fcef83b-66b2-4f5e-bfe4-c95e021a5374','2025-05-24 01:52:15',_binary '',9),(23,'214365c2-4f2d-4561-8642-0fce17ba98a1','2025-05-24 09:35:58',_binary '',9),(24,'744ee63e-3ee0-42e0-a235-1eb50d127d54','2025-05-24 09:38:31',_binary '',9),(25,'8d28c087-51de-470c-99a0-7432076c9a94','2025-05-24 09:43:42',_binary '',9),(26,'d56935d7-a107-4867-a657-fd793f481f01','2025-05-24 09:45:42',_binary '',9),(27,'7c3d6ea4-cd95-4409-a31a-4035464693c9','2025-05-26 09:00:59',_binary '',9),(28,'455c9b85-3fb1-4197-b07e-df238627edfd','2025-05-26 18:16:29',_binary '\0',9),(29,'938806d2-65b6-4c6e-8828-f75cf22c303e','2025-05-26 18:18:58',_binary '\0',9),(30,'bdc923c0-d9f4-4716-a2f8-4741003fa374','2025-05-29 09:16:15',_binary '\0',9),(31,'04dee5a8-7527-4fac-94cb-acb188ac59c2','2025-06-02 20:32:49',_binary '',9),(32,'fc7e9ff6-2601-4c8a-b73b-cae7e7945121','2025-06-05 09:15:56',_binary '',10),(33,'64b0818d-02ce-4059-80b4-ef553a2fe92c','2025-06-09 19:44:41',_binary '\0',10),(34,'1d480c35-39c6-44ac-aa3a-6a3225bbd5b4','2025-06-12 08:01:36',_binary '\0',10),(35,'aee87711-379f-41e2-82ce-ec02c4740c9d','2025-06-12 19:25:03',_binary '\0',1);
/*!40000 ALTER TABLE `tokenforgetpassword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin01','$2a$10$Z6PeNnylaCP5O3tblM0SvejkMXoayRhCCTMj13s39DhrLumlhexG.','Alice Admin','alice@flower.com','0901111001','123 Admin Street',1,'active'),(2,'manager01','$2a$10$Ppw/3o3ugj2Mz4k6jRJnh.9EU3c.yQCMG49dIkccKRkeqnUO.xY9G','Bob Manager','bob@flower.com','0901111002','456 Manager Lane',2,'active'),(3,'seller01','$2a$10$tSuwcCCzTEyZ/5u9yet1TOOmKRzG8CyazFR2bZ6JesroUVcSckaS.','Carol Seller','carol@flower.com','0901111003','789 Seller Road',3,'active'),(4,'marketer01','$2a$10$1Te9hDvbo5e5UokNI/g4E.4r731nGFVe8r1DgVMCXKo4ZcxyHeJSa','David Marketer','david@flower.com','0901111004','101 Marketing Blvd',4,'active'),(5,'warehouse01','$2a$10$nITsu.VVg9mfWQj0K3YcZeM.GJIhGb9cvJ8xGsxeqaiWh9.bQ/V2m','Eva Warehouse','eva@flower.com','0901111005','202 Warehouse Ave',5,'active'),(6,'guest01','$2a$10$YrA1Uo2/.DzVfTJWtQvQv.Inx2adbtNz7Ffp6bCKIcoz4RKEaFVQO','Frank Guest','frank@flower.com','0901111006','No Address',6,'active'),(7,'cust01','$2a$10$iEeGuer0SeMaS9cyxyU7KOVE/icEWxtdgrERVc5HCn2Eo12eS6hwC','Grace Customer','grace@flower.com','0901111007','303 Customer Place',7,'active'),(8,'cust02','$2a$10$160t4egnUs0ior5RQoqIHufioc8RC3bzcezPyiayyrtcVCFyfnica','Helen Buyer','helen@flower.com','0901111008','404 Rose Street',7,'active'),(11,'shipper','$2a$10$ECPr17gXBybS4nWf1Nw7x.cq9a00AM/JW2l/8OmMH.l3M7y.jIOYW','Tan VU','vuminhtan2004@gmail.com','0977679888','123 Admin Street',8,'active'),(12,'taness','$2a$10$MBn3oAJGzoPq4tbl5feDMOtfE54jlz36xdtWZw31XJJhKhYcrBiae','tan vu','tanvmhe186791@fpt.edu.vn','0919994398','ca',7,'active'),(13,'quangvuminh','$2a$10$NZte8aSF1HSXp8KNH6yuLeQUUucasYq7R03dLaMWQu/F4D1l9x5sW','Quang Vu','lem03726@gmail.com','0786709182','368B Quang Trung, La Khê, Hà Đông, Hà Nội',7,'active');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

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

-- Dump completed on 2025-06-26 15:53:01
