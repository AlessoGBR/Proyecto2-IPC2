CREATE DATABASE  IF NOT EXISTS `Proyecto2`;
USE `Proyecto2`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: Proyecto2
-- ------------------------------------------------------
-- Server version	8.0.39-0ubuntu0.24.04.2

--
-- Table structure for table `Anunciante`
--

DROP TABLE IF EXISTS `Anunciante`;
CREATE TABLE `Anunciante` (
  `idAnunciante` int NOT NULL AUTO_INCREMENT,
  `cartera` double DEFAULT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  PRIMARY KEY (`idAnunciante`,`nombre_usuario`),
  KEY `fk_Anunciante_Usuario1_idx` (`nombre_usuario`),
  CONSTRAINT `fk_Anunciante_Usuario1` FOREIGN KEY (`nombre_usuario`) REFERENCES `Usuario` (`nombre_usuario`)
);

--
-- Table structure for table `Anuncio`
--

DROP TABLE IF EXISTS `Anuncio`;

CREATE TABLE `Anuncio` (
  `idAnuncio` int NOT NULL AUTO_INCREMENT,
  `texto` mediumtext,
  `url_video` varchar(200) DEFAULT NULL,
  `path_imagen` varchar(200) DEFAULT NULL,
  `activo` tinyint DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_final` date DEFAULT NULL,
  `pago` double DEFAULT NULL,
  `nombre_anunciante` int NOT NULL,
  `tipo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idAnuncio`,`nombre_anunciante`),
  KEY `fk_Anuncio_Anunciante1_idx` (`nombre_anunciante`),
  CONSTRAINT `fk_Anuncio_Anunciante1` FOREIGN KEY (`nombre_anunciante`) REFERENCES `Anunciante` (`idAnunciante`)
) ;

--
-- Table structure for table `Anuncio_Etiquetas`
--

DROP TABLE IF EXISTS `Anuncio_Etiquetas`;
CREATE TABLE `Anuncio_Etiquetas` (
  `idAnuncio` int NOT NULL,
  `nombre_etiqueta` varchar(50) NOT NULL,
  PRIMARY KEY (`idAnuncio`,`nombre_etiqueta`),
  KEY `fk_Anuncio_Etiquetas_Etiqueta1_idx` (`nombre_etiqueta`),
  CONSTRAINT `fk_Anuncio_Etiquetas_Anuncio1` FOREIGN KEY (`idAnuncio`) REFERENCES `Anuncio` (`idAnuncio`),
  CONSTRAINT `fk_Anuncio_Etiquetas_Etiqueta1` FOREIGN KEY (`nombre_etiqueta`) REFERENCES `Etiqueta` (`nombre_etiqueta`)
) ;

--
-- Table structure for table `Categoria`
--

DROP TABLE IF EXISTS `Categoria`;
CREATE TABLE `Categoria` (
  `nombre_Categoria` varchar(50) NOT NULL,
  PRIMARY KEY (`nombre_Categoria`)
) ;

--
-- Table structure for table `Categoria_Revista`
--

DROP TABLE IF EXISTS `Categoria_Revista`;
CREATE TABLE `Categoria_Revista` (
  `nombre_Categoria` varchar(50) NOT NULL,
  `idRevista` int NOT NULL,
  PRIMARY KEY (`nombre_Categoria`,`idRevista`),
  KEY `fk_table1_Revista1_idx` (`idRevista`),
  CONSTRAINT `fk_table1_Categoria1` FOREIGN KEY (`nombre_Categoria`) REFERENCES `Categoria` (`nombre_Categoria`),
  CONSTRAINT `fk_table1_Revista1` FOREIGN KEY (`idRevista`) REFERENCES `Revista` (`idRevista`)
) ;

--
-- Table structure for table `Comentario`
--

DROP TABLE IF EXISTS `Comentario`;
CREATE TABLE `Comentario` (
  `idComentario` int NOT NULL AUTO_INCREMENT,
  `comentario` varchar(200) NOT NULL,
  `fecha` date NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  PRIMARY KEY (`idComentario`,`nombre_usuario`),
  KEY `fk_Comentario_Usuario1_idx` (`nombre_usuario`),
  CONSTRAINT `fk_Comentario_Usuario1` FOREIGN KEY (`nombre_usuario`) REFERENCES `Usuario` (`nombre_usuario`)
);

--
-- Table structure for table `Comentario_Revista`
--

DROP TABLE IF EXISTS `Comentario_Revista`;
CREATE TABLE `Comentario_Revista` (
  `idComentario` int NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `idRevista` int NOT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idComentario`,`nombre_usuario`,`idRevista`),
  KEY `fk_comentario_Revista_Revista1_idx` (`idRevista`),
  CONSTRAINT `fk_comentario_Revista_Comentario1` FOREIGN KEY (`idComentario`, `nombre_usuario`) REFERENCES `Comentario` (`idComentario`, `nombre_usuario`),
  CONSTRAINT `fk_comentario_Revista_Revista1` FOREIGN KEY (`idRevista`) REFERENCES `Revista` (`idRevista`)
) ;

--
-- Table structure for table `Etiqueta`
--

DROP TABLE IF EXISTS `Etiqueta`;
CREATE TABLE `Etiqueta` (
  `nombre_etiqueta` varchar(50) NOT NULL,
  PRIMARY KEY (`nombre_etiqueta`)
) ;

--
-- Table structure for table `Etiqueta_Revista`
--

DROP TABLE IF EXISTS `Etiqueta_Revista`;
CREATE TABLE `Etiqueta_Revista` (
  `nombre_etiqueta` varchar(50) NOT NULL,
  `idRevista` int NOT NULL,
  PRIMARY KEY (`nombre_etiqueta`,`idRevista`),
  KEY `fk_Etiqueta_Revista_Etiqueta1_idx` (`nombre_etiqueta`),
  KEY `fk_Etiqueta_Revista_Revista1_idx` (`idRevista`),
  CONSTRAINT `fk_Etiqueta_Revista_Etiqueta1` FOREIGN KEY (`nombre_etiqueta`) REFERENCES `Etiqueta` (`nombre_etiqueta`),
  CONSTRAINT `fk_Etiqueta_Revista_Revista1` FOREIGN KEY (`idRevista`) REFERENCES `Revista` (`idRevista`)
) ;

--
-- Table structure for table `Perfil`
--

DROP TABLE IF EXISTS `Perfil`;
CREATE TABLE `Perfil` (
  `idPerfil` int NOT NULL AUTO_INCREMENT,
  `fotoPerfil` text,
  `descripcion` varchar(200) DEFAULT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  PRIMARY KEY (`idPerfil`,`nombre_usuario`),
  KEY `fk_Perfil_Usuario_idx` (`nombre_usuario`),
  CONSTRAINT `fk_Perfil_Usuario` FOREIGN KEY (`nombre_usuario`) REFERENCES `Usuario` (`nombre_usuario`)
) ;

--
-- Table structure for table `Perfil_Categoria`
--

DROP TABLE IF EXISTS `Perfil_Categoria`;
CREATE TABLE `Perfil_Categoria` (
  `idPerfil` int NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `nombre_Categoria` varchar(50) NOT NULL,
  PRIMARY KEY (`idPerfil`,`nombre_usuario`,`nombre_Categoria`),
  KEY `fk_table1_Categoria2_idx` (`nombre_Categoria`),
  CONSTRAINT `fk_table1_Categoria2` FOREIGN KEY (`nombre_Categoria`) REFERENCES `Categoria` (`nombre_Categoria`),
  CONSTRAINT `fk_table1_Perfil1` FOREIGN KEY (`idPerfil`, `nombre_usuario`) REFERENCES `Perfil` (`idPerfil`, `nombre_usuario`)
) ;

--
-- Table structure for table `Perfil_Etiquetas`
--

DROP TABLE IF EXISTS `Perfil_Etiquetas`;
CREATE TABLE `Perfil_Etiquetas` (
  `nombre_etiqueta` varchar(50) NOT NULL,
  `idPerfil` int NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  PRIMARY KEY (`nombre_etiqueta`,`idPerfil`,`nombre_usuario`),
  KEY `fk_Perfil_Etiquetas_Perfil1_idx` (`idPerfil`,`nombre_usuario`),
  CONSTRAINT `fk_Perfil_Etiquetas_Etiqueta1` FOREIGN KEY (`nombre_etiqueta`) REFERENCES `Etiqueta` (`nombre_etiqueta`),
  CONSTRAINT `fk_Perfil_Etiquetas_Perfil1` FOREIGN KEY (`idPerfil`, `nombre_usuario`) REFERENCES `Perfil` (`idPerfil`, `nombre_usuario`)
) ;

--
-- Table structure for table `Reaccion`
--

DROP TABLE IF EXISTS `Reaccion`;
CREATE TABLE `Reaccion` (
  `idReaccion` int NOT NULL AUTO_INCREMENT,
  `reacciones` tinyint NOT NULL,
  `fecha` date NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  PRIMARY KEY (`idReaccion`,`nombre_usuario`),
  KEY `fk_Reaccion_Usuario1_idx` (`nombre_usuario`),
  CONSTRAINT `fk_Reaccion_Usuario1` FOREIGN KEY (`nombre_usuario`) REFERENCES `Usuario` (`nombre_usuario`)
) ;

--
-- Table structure for table `Reaccion_Revista`
--

DROP TABLE IF EXISTS `Reaccion_Revista`;
CREATE TABLE `Reaccion_Revista` (
  `idReaccion` int NOT NULL,
  `idRevista` int NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`idReaccion`,`idRevista`,`nombre_usuario`),
  KEY `fk_Reaccion_Revista_Revista1_idx` (`idRevista`,`nombre_usuario`),
  KEY `fk_Reaccion_Revista_Reaccion1` (`idReaccion`,`nombre_usuario`),
  CONSTRAINT `fk_Reaccion_Revista_Reaccion1` FOREIGN KEY (`idReaccion`, `nombre_usuario`) REFERENCES `Reaccion` (`idReaccion`, `nombre_usuario`),
  CONSTRAINT `fk_Reaccion_Revista_Revista1` FOREIGN KEY (`idRevista`) REFERENCES `Revista` (`idRevista`)
) ;

--
-- Table structure for table `Revista`
--

DROP TABLE IF EXISTS `Revista`;
CREATE TABLE `Revista` (
  `idRevista` int NOT NULL AUTO_INCREMENT,
  `revista` text,
  `titulo` varchar(50) NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `no_version` varchar(50) DEFAULT NULL,
  `aprobada` tinyint NOT NULL,
  `suscripciones` tinyint NOT NULL,
  `comentarios` tinyint NOT NULL,
  `reacciones` tinyint NOT NULL,
  `fecha` date DEFAULT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `denegada` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idRevista`,`nombre_usuario`),
  KEY `fk_Revista_Usuario1_idx` (`nombre_usuario`),
  CONSTRAINT `fk_Revista_Usuario1` FOREIGN KEY (`nombre_usuario`) REFERENCES `Usuario` (`nombre_usuario`)
) ;

--
-- Table structure for table `Suscripcion`
--

DROP TABLE IF EXISTS `Suscripcion`;
CREATE TABLE `Suscripcion` (
  `idSuscripcion` int NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `idRevista` int NOT NULL,
  PRIMARY KEY (`idSuscripcion`,`nombre_usuario`,`idRevista`),
  KEY `fk_Suscripcion_Usuario1_idx` (`nombre_usuario`),
  KEY `fk_Suscripcion_Revista1_idx` (`idRevista`),
  CONSTRAINT `fk_Suscripcion_Revista1` FOREIGN KEY (`idRevista`) REFERENCES `Revista` (`idRevista`),
  CONSTRAINT `fk_Suscripcion_Usuario1` FOREIGN KEY (`nombre_usuario`) REFERENCES `Usuario` (`nombre_usuario`)
) ;

--
-- Table structure for table `TipoUsuario`
--

DROP TABLE IF EXISTS `TipoUsuario`;
CREATE TABLE `TipoUsuario` (
  `idTipoUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre_tipo` varchar(15) NOT NULL,
  PRIMARY KEY (`idTipoUsuario`)
) ;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
CREATE TABLE `Usuario` (
  `nombre_usuario` varchar(50) NOT NULL,
  `password` varchar(200) NOT NULL,
  `idTipoUsuario` int NOT NULL,
  PRIMARY KEY (`nombre_usuario`,`idTipoUsuario`),
  KEY `fk_Usuario_TipoUsuario1_idx` (`idTipoUsuario`),
  CONSTRAINT `fk_Usuario_TipoUsuario1` FOREIGN KEY (`idTipoUsuario`) REFERENCES `TipoUsuario` (`idTipoUsuario`)
);

