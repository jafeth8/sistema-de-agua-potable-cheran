-- MySQL dump 10.13  Distrib 5.7.24, for Win64 (x86_64)
--
-- Host: localhost    Database: agua_potable_temp
-- ------------------------------------------------------
-- Server version	5.7.24

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
-- Table structure for table `c_estado_clientes`
--

DROP TABLE IF EXISTS `c_estado_clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_estado_clientes` (
  `id_estado_cliente` tinyint(4) NOT NULL,
  `descripcion` varchar(30) NOT NULL,
  PRIMARY KEY (`id_estado_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_estado_clientes`
--

LOCK TABLES `c_estado_clientes` WRITE;
/*!40000 ALTER TABLE `c_estado_clientes` DISABLE KEYS */;
INSERT INTO `c_estado_clientes` VALUES (1,'activo'),(2,'inactivo');
/*!40000 ALTER TABLE `c_estado_clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_estado_pagos`
--

DROP TABLE IF EXISTS `c_estado_pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_estado_pagos` (
  `id_estado_pago` tinyint(4) NOT NULL,
  `descripcion` varchar(30) NOT NULL,
  PRIMARY KEY (`id_estado_pago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_estado_pagos`
--

LOCK TABLES `c_estado_pagos` WRITE;
/*!40000 ALTER TABLE `c_estado_pagos` DISABLE KEYS */;
INSERT INTO `c_estado_pagos` VALUES (1,'pagado'),(2,'en deuda');
/*!40000 ALTER TABLE `c_estado_pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `c_pago_minimo`
--

DROP TABLE IF EXISTS `c_pago_minimo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `c_pago_minimo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pago_minimo` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_pago_minimo`
--

LOCK TABLES `c_pago_minimo` WRITE;
/*!40000 ALTER TABLE `c_pago_minimo` DISABLE KEYS */;
INSERT INTO `c_pago_minimo` VALUES (1,35);
/*!40000 ALTER TABLE `c_pago_minimo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `numero_cliente` int(11) NOT NULL,
  `numero_contrato` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido_paterno` varchar(20) NOT NULL,
  `apellido_materno` varchar(20) NOT NULL,
  `domicilio` varchar(100) NOT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `barrio` int(11) NOT NULL,
  `fk_tipo_descuento` varchar(20) NOT NULL,
  `fk_tipo_tarifa` varchar(20) NOT NULL,
  `fk_id_estado_cliente` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_cliente`),
  KEY `relacion_descuentos` (`fk_tipo_descuento`),
  KEY `relacion_tarifas` (`fk_tipo_tarifa`),
  KEY `fk_id_estado_cliente` (`fk_id_estado_cliente`),
  CONSTRAINT `relacion_descuentos` FOREIGN KEY (`fk_tipo_descuento`) REFERENCES `descuentos` (`tipo_descuento`) ON UPDATE CASCADE,
  CONSTRAINT `relacion_estado_clientes` FOREIGN KEY (`fk_id_estado_cliente`) REFERENCES `c_estado_clientes` (`id_estado_cliente`) ON UPDATE CASCADE,
  CONSTRAINT `relacion_tarifas` FOREIGN KEY (`fk_tipo_tarifa`) REFERENCES `tarifas` (`tipo_tarifa`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,123,987,'Romeo Jafet','Ascencio','Alonso','Francisco I madero # 584 col. lazaro cardenas','452-125-23-78',1,'no descuento','casa habitada',1),(2,1231,9871,'Areli Ajelet','Ascencio','Alonso','Francisco I madero #8','452-125-23-79',2,'no descuento','casa habitada',1),(3,1232,9872,'Jesus ','Ortega','Ramos','calle taretsuruan col. cancha municipal','452-125-22-22',3,'jubilado','casa habitada',1),(4,1233,9873,'Roberto','Hernandez','Silva','calle janitzio #12 col. del pino','452-129-22-22',4,'jubilado','casa no habitada',1),(5,12334,123456,'Jibril','Ascencio','alonso','janitzio #8','452-125-23-78',1,'no descuento','negocio',1),(6,180396,180396,'Ari','Gonzales','Alvares','1 de noviembre','123445678',1,'no descuento','casa habitada',1),(7,180396,180396,'Ari','Gonzales','Alvares','1 de noviembre','123445678',1,'no descuento','casa habitada',1),(8,180396,180396,'Misa','amane','misato','1 de noviembre','123456789',2,'no descuento','casa habitada',1),(9,123456,1234567,'Lizbeth','Zalapa','Hernandez','Calle del pino #8 colonia buena visrta','123456678',2,'jubilado','negocio',1);
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descuentos`
--

DROP TABLE IF EXISTS `descuentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descuentos` (
  `tipo_descuento` varchar(20) NOT NULL,
  `descuento` float NOT NULL,
  `descuento_anual` float DEFAULT NULL,
  `estado` varchar(15) NOT NULL DEFAULT 'activo',
  PRIMARY KEY (`tipo_descuento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descuentos`
--

LOCK TABLES `descuentos` WRITE;
/*!40000 ALTER TABLE `descuentos` DISABLE KEYS */;
INSERT INTO `descuentos` VALUES ('jubilado',35,420,'activo'),('no descuento',0,0,'activo'),('tercera edad',40,480,'activo');
/*!40000 ALTER TABLE `descuentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pagos`
--

DROP TABLE IF EXISTS `detalle_pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_pagos` (
  `id_registro` int(11) NOT NULL AUTO_INCREMENT,
  `fk_id_pago` int(11) NOT NULL,
  `mes` varchar(20) NOT NULL,
  `importe` float NOT NULL,
  `pago_realizado` float DEFAULT NULL,
  `cambio_entregado` float DEFAULT NULL,
  `fecha_pago` date DEFAULT NULL,
  `fk_id_estado_pago` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_registro`),
  KEY `relacion_pagos` (`fk_id_pago`),
  KEY `relacion_estado_pago` (`fk_id_estado_pago`),
  CONSTRAINT `relacion_estado_pago` FOREIGN KEY (`fk_id_estado_pago`) REFERENCES `c_estado_pagos` (`id_estado_pago`),
  CONSTRAINT `relacion_pagos` FOREIGN KEY (`fk_id_pago`) REFERENCES `pagos` (`id_pago`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=378 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pagos`
--

LOCK TABLES `detalle_pagos` WRITE;
/*!40000 ALTER TABLE `detalle_pagos` DISABLE KEYS */;
INSERT INTO `detalle_pagos` VALUES (337,71,'enero',60,0,0,'2022-05-05',1),(338,71,'febrero',60,NULL,NULL,NULL,2),(339,71,'marzo',60,NULL,NULL,NULL,2),(340,71,'abril',60,NULL,NULL,NULL,2),(341,71,'mayo',60,NULL,NULL,NULL,2),(342,71,'junio',60,NULL,NULL,NULL,2),(343,71,'julio',60,NULL,NULL,NULL,2),(344,71,'agosto',60,NULL,NULL,NULL,2),(345,71,'septiempre',60,NULL,NULL,NULL,2),(346,71,'octubre',60,NULL,NULL,NULL,2),(347,71,'noviembre',60,NULL,NULL,NULL,2),(348,71,'diciembre',60,NULL,NULL,NULL,2),(349,72,'enero-diciembre',600,NULL,NULL,NULL,2),(350,73,'enero',25,0,0,'2022-05-05',1),(351,73,'febrero',25,NULL,NULL,NULL,2),(352,73,'marzo',25,NULL,NULL,NULL,2),(353,73,'abril',25,NULL,NULL,NULL,2),(354,73,'mayo',25,NULL,NULL,NULL,2),(355,73,'junio',25,NULL,NULL,NULL,2),(356,73,'julio',25,NULL,NULL,NULL,2),(357,73,'agosto',25,NULL,NULL,NULL,2),(358,73,'septiempre',25,NULL,NULL,NULL,2),(359,73,'octubre',25,NULL,NULL,NULL,2),(360,73,'noviembre',25,NULL,NULL,NULL,2),(361,73,'diciembre',25,NULL,NULL,NULL,2),(362,74,'enero-diciembre',250,0,0,'2022-05-05',1),(363,75,'enero',35,0,0,'2022-05-05',1),(364,75,'febrero',35,NULL,NULL,NULL,2),(365,75,'marzo',35,NULL,NULL,NULL,2),(366,75,'abril',35,NULL,NULL,NULL,2),(367,75,'mayo',35,NULL,NULL,NULL,2),(368,75,'junio',35,NULL,NULL,NULL,2),(369,75,'julio',35,NULL,NULL,NULL,2),(370,75,'agosto',35,NULL,NULL,NULL,2),(371,75,'septiempre',35,NULL,NULL,NULL,2),(372,75,'octubre',35,NULL,NULL,NULL,2),(373,75,'noviembre',35,NULL,NULL,NULL,2),(374,75,'diciembre',35,NULL,NULL,NULL,2),(375,76,'enero-diciembre',350,0,0,'2022-05-05',1),(376,77,'enero-diciembre',350,0,0,'2022-05-05',1),(377,78,'enero-diciembre',350,0,0,'2022-05-05',1);
/*!40000 ALTER TABLE `detalle_pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagos` (
  `id_pago` int(11) NOT NULL AUTO_INCREMENT,
  `fk_id_cliente` int(11) NOT NULL,
  `tipo_tarifa` varchar(20) NOT NULL,
  `precio_tarifa` float NOT NULL,
  `tipo_descuento` varchar(20) NOT NULL,
  `descuento` float NOT NULL,
  `tipo_pago` varchar(50) NOT NULL,
  `descuento_anual` float NOT NULL DEFAULT '0',
  `total` float NOT NULL,
  `total_pagado` float DEFAULT '0',
  `deuda` float DEFAULT NULL,
  `periodo` year(4) NOT NULL,
  `fk_id_estado_pago` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_pago`),
  KEY `fk_id_cliente` (`fk_id_cliente`),
  KEY `fk_id_estado_pago` (`fk_id_estado_pago`),
  CONSTRAINT `estado_pago_relacion` FOREIGN KEY (`fk_id_estado_pago`) REFERENCES `c_estado_pagos` (`id_estado_pago`),
  CONSTRAINT `relacion_cliente` FOREIGN KEY (`fk_id_cliente`) REFERENCES `clientes` (`id_cliente`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (71,1,'casa habitada',720,'no descuento',0,'Mensual',0,720,60,660,2022,2),(72,2,'casa habitada',720,'no descuento',0,'Anual',120,600,0,600,2022,2),(73,3,'casa habitada',720,'jubilado',35,'Mensual',0,300,25,275,2022,2),(74,3,'casa habitada',720,'jubilado',35,'Anual',120,250,250,0,2023,1),(75,4,'casa no habitada',420,'jubilado',35,'Mensual',0,420,35,385,2022,2),(76,4,'casa no habitada',420,'jubilado',35,'Anual',70,350,350,0,2023,1),(77,4,'casa no habitada',420,'jubilado',35,'Anual',70,350,350,0,2024,1),(78,4,'casa no habitada',420,'jubilado',35,'Anual',70,350,350,0,2025,1);
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruta_respaldo`
--

DROP TABLE IF EXISTS `ruta_respaldo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ruta_respaldo` (
  `id` tinyint(4) NOT NULL,
  `ruta` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruta_respaldo`
--

LOCK TABLES `ruta_respaldo` WRITE;
/*!40000 ALTER TABLE `ruta_respaldo` DISABLE KEYS */;
INSERT INTO `ruta_respaldo` VALUES (1,'C:\\Users\\jafeth888\\Documents\\NetBeansProjects\\sistemaCheranAguaPotable\\respaldo_agua');
/*!40000 ALTER TABLE `ruta_respaldo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarifas`
--

DROP TABLE IF EXISTS `tarifas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarifas` (
  `tipo_tarifa` varchar(20) NOT NULL,
  `tarifa_anual` float NOT NULL,
  `tarifa_mensual` float DEFAULT NULL,
  `estado` varchar(15) NOT NULL DEFAULT 'activo',
  PRIMARY KEY (`tipo_tarifa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarifas`
--

LOCK TABLES `tarifas` WRITE;
/*!40000 ALTER TABLE `tarifas` DISABLE KEYS */;
INSERT INTO `tarifas` VALUES ('casa habitada',720,60,'activo'),('casa no habitada',420,35,'activo'),('negocio',900,75,'activo');
/*!40000 ALTER TABLE `tarifas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-08 17:10:19
