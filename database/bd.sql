CREATE DATABASE  IF NOT EXISTS `proyecto` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `proyecto`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: proyecto
-- ------------------------------------------------------
-- Server version	8.4.6

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
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(200) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqcog8b7hps1hioi9onqwjdt6y` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Productos derivados del petróleo','Plástico'),(2,'Chatarra y fierros en general','Metal'),(3,'Todo lo que es papeleria','Papel');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `razon_social` varchar(150) NOT NULL,
  `ruc_dni` varchar(11) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKn4rnu2dhdlsvf7o3qwmqfhwbm` (`ruc_dni`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,_binary '','Jr. Marañon #323','gabrielaguadalupe150@gmail.com','Olguita Mayleen Rios Vargas','00014578','983941576'),(2,_binary '','Jr. Marañon #323','carlos@gamil.com','Sodimac','12356756865','987658493');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras` (
  `id` int NOT NULL AUTO_INCREMENT,
  `codigo_operacion` varchar(20) DEFAULT NULL,
  `fecha_compra` datetime(6) NOT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `total` double NOT NULL,
  `proveedor_id` int NOT NULL,
  `trabajador_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK44ydpoyo5p8u1ddqnou88eequ` (`codigo_operacion`),
  KEY `FKabfd3b61ss0f7ebhao6evn5ec` (`proveedor_id`),
  KEY `FKidldaqn2p3uv70ogvf43hp0mu` (`trabajador_id`),
  CONSTRAINT `FKabfd3b61ss0f7ebhao6evn5ec` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedores` (`id`),
  CONSTRAINT `FKidldaqn2p3uv70ogvf43hp0mu` FOREIGN KEY (`trabajador_id`) REFERENCES `trabajadores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
INSERT INTO `compras` VALUES (1,'CP-1767112265346','2025-12-30 16:31:05.346025','Pago al contado',18500,1,1),(2,'CP-1767121129732','2025-12-30 18:58:49.732638','',5,1,1),(3,'CP-1767649485140','2026-01-05 21:44:45.140961','Pago al contado',1665,2,1);
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalles_compra`
--

DROP TABLE IF EXISTS `detalles_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalles_compra` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad` double NOT NULL,
  `precio` double NOT NULL,
  `subtotal` double NOT NULL,
  `compra_id` int NOT NULL,
  `material_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhmao5favsu254hqpg7bv17jil` (`compra_id`),
  KEY `FKg2q4wf680g4qvoxmqgf9u23uq` (`material_id`),
  CONSTRAINT `FKg2q4wf680g4qvoxmqgf9u23uq` FOREIGN KEY (`material_id`) REFERENCES `materiales` (`id`),
  CONSTRAINT `FKhmao5favsu254hqpg7bv17jil` FOREIGN KEY (`compra_id`) REFERENCES `compras` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalles_compra`
--

LOCK TABLES `detalles_compra` WRITE;
/*!40000 ALTER TABLE `detalles_compra` DISABLE KEYS */;
INSERT INTO `detalles_compra` VALUES (1,100,10,1000,1,1),(2,2000,5,10000,1,2),(3,500,15,7500,1,3),(4,1,5,5,2,2),(5,111,15,1665,3,3);
/*!40000 ALTER TABLE `detalles_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalles_venta`
--

DROP TABLE IF EXISTS `detalles_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalles_venta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad` double NOT NULL,
  `precio` double NOT NULL,
  `subtotal` double NOT NULL,
  `material_id` int NOT NULL,
  `venta_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhjd065qptg9cch6g3nli0i6m0` (`material_id`),
  KEY `FK453xcyfk9n6snv6qnjlo0p65p` (`venta_id`),
  CONSTRAINT `FK453xcyfk9n6snv6qnjlo0p65p` FOREIGN KEY (`venta_id`) REFERENCES `ventas` (`id`),
  CONSTRAINT `FKhjd065qptg9cch6g3nli0i6m0` FOREIGN KEY (`material_id`) REFERENCES `materiales` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalles_venta`
--

LOCK TABLES `detalles_venta` WRITE;
/*!40000 ALTER TABLE `detalles_venta` DISABLE KEYS */;
INSERT INTO `detalles_venta` VALUES (1,2,50,100,4,1),(2,100,20,2000,6,1),(3,3,50,150,4,2),(4,1,40,40,5,3);
/*!40000 ALTER TABLE `detalles_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materiales`
--

DROP TABLE IF EXISTS `materiales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materiales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factor_conversion` double DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio_compra` double DEFAULT NULL,
  `precio_venta` double DEFAULT NULL,
  `stock` double NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `unidad` varchar(20) DEFAULT NULL,
  `categoria_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgfd517sdhguq0fu2nak1k3p09` (`categoria_id`),
  CONSTRAINT `FKgfd517sdhguq0fu2nak1k3p09` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materiales`
--

LOCK TABLES `materiales` WRITE;
/*!40000 ALTER TABLE `materiales` DISABLE KEYS */;
INSERT INTO `materiales` VALUES (1,NULL,'Botellas de plastico',10,0,50,'RESIDUO','KG',1),(2,NULL,'Periodico',5,0,1001,'RESIDUO','KG',3),(3,NULL,'Calamina',15,0,256,'RESIDUO','KG',2),(4,10,'Bloque de botellas',0,50,0,'PRODUCTO','UNIDAD',1),(5,1000,'Costal de papel',0,40,0,'PRODUCTO','TON',3),(6,1,'Calamina triturada',0,20,255,'PRODUCTO','KG',2),(7,NULL,'Hoja de cuaderno',10.5,0,0,'RESIDUO','KG',3),(8,NULL,'Chapa de botella',5,0,0,'RESIDUO','UNIDAD',1),(9,NULL,'wrf',243,0,0,'RESIDUO','TON',1);
/*!40000 ALTER TABLE `materiales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `documento` varchar(15) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `razon_social` varchar(150) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKffj6y49mpe4t7pj6klbksla0t` (`documento`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,_binary '','Jr. Marañon #323\r\nDepto.','71548695','delcimajomax@gmail.com','Delci Vargas Murayari','983941576'),(2,_binary '','Jr. 3 de Mayo #432','1547859657','j_gordon_11@hotmail.com','Reciclaje Perez','965487265');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKldv0v52e0udsh2h1rs0r0gw1n` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'GESTOR'),(3,'OPERARIO');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trabajadores`
--

DROP TABLE IF EXISTS `trabajadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trabajadores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `dni` varchar(8) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `rol_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK40fk2b36s84lu990wgpl9a5hv` (`username`),
  UNIQUE KEY `UK6li210jqt10hev0wj6qj482xs` (`dni`),
  KEY `FK72vpyuskuemi8sy0dipfdrdxe` (`rol_id`),
  CONSTRAINT `FK72vpyuskuemi8sy0dipfdrdxe` FOREIGN KEY (`rol_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabajadores`
--

LOCK TABLES `trabajadores` WRITE;
/*!40000 ALTER TABLE `trabajadores` DISABLE KEYS */;
INSERT INTO `trabajadores` VALUES (1,_binary '','Rios Vargas','72676535','Max Alberto','xtrial007','982742098','elmarv05',1),(2,_binary '','Rios Escudero','42578964','Pool Michel','123456','983941576','pool',2),(3,_binary '','Hidalgo Ochoa','74125896','Gianpaulo Giuliano','123456','959052934','gian',3),(4,_binary '','Ramirez Cachique','72645003','Carlos Andre','123456','935486712','carlos',1);
/*!40000 ALTER TABLE `trabajadores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transformaciones`
--

DROP TABLE IF EXISTS `transformaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transformaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad_destino` double NOT NULL,
  `cantidad_origen` double NOT NULL,
  `codigo_operacion` varchar(20) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha` datetime(6) NOT NULL,
  `material_destino_id` int NOT NULL,
  `material_origen_id` int NOT NULL,
  `trabajador_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKs1c25fb90cqbe0ixi9oudul31` (`codigo_operacion`),
  KEY `FKpiydtm0jsu1h8guc9n1ioa2xy` (`material_destino_id`),
  KEY `FKlq4pwg2md3s0o6epbs271xgas` (`material_origen_id`),
  KEY `FKf859273riap1ecpttuqwe1wc3` (`trabajador_id`),
  CONSTRAINT `FKf859273riap1ecpttuqwe1wc3` FOREIGN KEY (`trabajador_id`) REFERENCES `trabajadores` (`id`),
  CONSTRAINT `FKlq4pwg2md3s0o6epbs271xgas` FOREIGN KEY (`material_origen_id`) REFERENCES `materiales` (`id`),
  CONSTRAINT `FKpiydtm0jsu1h8guc9n1ioa2xy` FOREIGN KEY (`material_destino_id`) REFERENCES `materiales` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transformaciones`
--

LOCK TABLES `transformaciones` WRITE;
/*!40000 ALTER TABLE `transformaciones` DISABLE KEYS */;
INSERT INTO `transformaciones` VALUES (1,5,50,'TR-1767112327676',NULL,'2025-12-30 16:32:07.676661',4,1,1),(2,1,1000,'TR-1767112341620',NULL,'2025-12-30 16:32:21.620183',5,2,1),(3,300,300,'TR-1767112350835',NULL,'2025-12-30 16:32:30.835939',6,3,1),(4,10,10,'TR-1767125075893',NULL,'2025-12-30 20:04:35.893392',6,3,1),(5,45,45,'TR-1767561789183',NULL,'2026-01-04 21:23:09.183120',6,3,1);
/*!40000 ALTER TABLE `transformaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `codigo_operacion` varchar(20) DEFAULT NULL,
  `fecha_venta` datetime(6) NOT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `total` double NOT NULL,
  `cliente_id` int NOT NULL,
  `trabajador_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKghpcb2cdjk4qr5b0xw11fpken` (`codigo_operacion`),
  KEY `FK4dgjhccl2uuo8swmxlxb4ipb5` (`cliente_id`),
  KEY `FK59b5jytj0187hp2ie5h2y1qim` (`trabajador_id`),
  CONSTRAINT `FK4dgjhccl2uuo8swmxlxb4ipb5` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`),
  CONSTRAINT `FK59b5jytj0187hp2ie5h2y1qim` FOREIGN KEY (`trabajador_id`) REFERENCES `trabajadores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (1,'VN-1767112384426','2025-12-30 16:33:04.426651','Pago al contado',2100,1,1),(2,'VN-1767644654727','2026-01-05 20:24:14.727495','Pagado',150,1,1),(3,'VN-1767645478048','2026-01-05 20:37:58.048028','csds',40,1,1);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'proyecto'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_compras_por_mes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_compras_por_mes`()
BEGIN
    SELECT 
        MONTH(fecha_compra) as mes,
        YEAR(fecha_compra) as anio,
        SUM(total) as total
    FROM compras
    GROUP BY YEAR(fecha_compra), MONTH(fecha_compra)
    ORDER BY YEAR(fecha_compra) ASC, MONTH(fecha_compra) ASC
    LIMIT 12; -- Últimos 12 meses
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_ventas_por_mes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtener_ventas_por_mes`()
BEGIN
    
    SELECT 
        YEAR(fecha_venta) as anio, 
        MONTH(fecha_venta) as mes, 
        SUM(total) as total 
    FROM ventas 
    GROUP BY YEAR(fecha_venta), MONTH(fecha_venta) 
    ORDER BY YEAR(fecha_venta), MONTH(fecha_venta);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_sumar_compras_totales` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_sumar_compras_totales`()
BEGIN
    
    SELECT IFNULL(SUM(total), 0) FROM compras;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_sumar_ventas_totales` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_sumar_ventas_totales`()
BEGIN
   
    SELECT IFNULL(SUM(total), 0) FROM ventas;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-05 17:06:06
