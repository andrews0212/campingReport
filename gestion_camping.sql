-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 28-01-2025 a las 08:19:44
-- Versión del servidor: 10.6.17-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestion_camping`
--
Create database gestion_camping;
use gestion_camping;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `acompañante`
--

CREATE TABLE `acompañante` (
  `IDACOMPAÑANTE` int(10) UNSIGNED NOT NULL,
  `IDCLIENTE` int(10) UNSIGNED NOT NULL,
  `IDRESERVA` int(10) UNSIGNED NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(50) NOT NULL,
  `DNI` varchar(9) DEFAULT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `TELEFONO` varchar(9) DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `IDCLIENTE` int(10) UNSIGNED NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(50) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `TELEFONO` varchar(9) DEFAULT NULL,
  `FECHA_NACIMIENTO` date DEFAULT NULL,
  `ESTADO` tinytext NOT NULL,
  `COMENTARIOS` varchar(300) DEFAULT NULL
) ;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`IDCLIENTE`, `NOMBRE`, `APELLIDO`, `DNI`, `EMAIL`, `TELEFONO`, `FECHA_NACIMIENTO`, `ESTADO`, `COMENTARIOS`) VALUES
(2, 'andrews', 'García', '87654321B', 'ana.garcia@example.com', '600654321', '1990-07-22', 'ACTIVO', 'Problemas con pagos.'),
(3, 'Luis', 'Lopez', '56781234C', 'luis.martinez@example.com', '600987654', NULL, 'BLOQUEADO', 'Incumplió normas.'),
(9, 'andrews', 'Dos ramos', '12345678B', 'andrews@gmail.com', '634127185', '2024-12-11', 'ACTIVO', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recurso`
--

CREATE TABLE `recurso` (
  `IDRECURSO` int(10) UNSIGNED NOT NULL,
  `NOMBRE` varchar(25) NOT NULL,
  `TIPO` tinytext NOT NULL,
  `CAPACIDAD` int(3) NOT NULL,
  `PRECIO` int(5) NOT NULL,
  `MINIMO_PERSONAS` int(3) NOT NULL,
  `ESTADO` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `recurso`
--

INSERT INTO `recurso` (`IDRECURSO`, `NOMBRE`, `TIPO`, `CAPACIDAD`, `PRECIO`, `MINIMO_PERSONAS`, `ESTADO`) VALUES
(1, 'Bungalow A', 'BUNGALOW', 4, 100, 2, 'DISPONIBLE'),
(2, 'Barbacoa Grande', 'BARBACOA', 10, 50, 4, 'OCUPADO'),
(3, 'Parcela 5', 'PARCELA', 6, 30, 1, 'MANTENIMIENTO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `IDRESERVA` int(10) UNSIGNED NOT NULL,
  `IDCLIENTE` int(10) UNSIGNED DEFAULT NULL,
  `IDRECURSO` int(10) UNSIGNED DEFAULT NULL,
  `FECHA_INICIO` date DEFAULT NULL,
  `FECHA_FIN` date DEFAULT NULL,
  `ESTADO` tinytext DEFAULT NULL,
  `PRECIO_TOTAL` int(5) UNSIGNED NOT NULL,
  `NUMERO_PERSONAS` int(3) UNSIGNED NOT NULL
) ;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`IDRESERVA`, `IDCLIENTE`, `IDRECURSO`, `FECHA_INICIO`, `FECHA_FIN`, `ESTADO`, `PRECIO_TOTAL`, `NUMERO_PERSONAS`) VALUES
(7, 2, 1, '2024-12-01', '2024-12-05', 'ACTIVA', 400, 3),
(8, 2, 2, '2024-12-10', '2024-12-12', 'FINALIZADA', 100, 0),
(9, 3, 3, '2024-12-15', '2024-12-20', 'CANCELADA', 150, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(50) NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `NICKNAME` varchar(50) NOT NULL,
  `ESTADO` tinytext DEFAULT NULL,
  `CONTRASEÑA` varchar(60) NOT NULL
) ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`ID`, `NOMBRE`, `APELLIDO`, `EMAIL`, `NICKNAME`, `ESTADO`, `CONTRASEÑA`) VALUES
(1, 'Andrews', 'Dos Ramos', 'andrewsdosramos@gmail.com', '0lin_D0s', 'ACTIVO', 'Dosramos02');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `acompañante`
--
ALTER TABLE `acompañante`
  ADD PRIMARY KEY (`IDACOMPAÑANTE`,`IDCLIENTE`,`IDRESERVA`),
  ADD KEY `IDRESERVA` (`IDRESERVA`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`IDCLIENTE`),
  ADD UNIQUE KEY `DNI` (`DNI`);

--
-- Indices de la tabla `recurso`
--
ALTER TABLE `recurso`
  ADD PRIMARY KEY (`IDRECURSO`),
  ADD UNIQUE KEY `NOMBRE` (`NOMBRE`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`IDRESERVA`),
  ADD KEY `IDCLIENTE` (`IDCLIENTE`),
  ADD KEY `IDRECURSO` (`IDRECURSO`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `acompañante`
--
ALTER TABLE `acompañante`
  MODIFY `IDACOMPAÑANTE` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `IDCLIENTE` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `recurso`
--
ALTER TABLE `recurso`
  MODIFY `IDRECURSO` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `IDRESERVA` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `acompañante`
--
ALTER TABLE `acompañante`
  ADD CONSTRAINT `acompañante_ibfk_1` FOREIGN KEY (`IDRESERVA`) REFERENCES `reserva` (`IDRESERVA`);

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`IDCLIENTE`) REFERENCES `cliente` (`IDCLIENTE`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`IDRECURSO`) REFERENCES `recurso` (`IDRECURSO`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
