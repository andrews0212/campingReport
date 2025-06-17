-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-06-2025 a las 23:18:44
-- Versión del servidor: 10.6.17-MariaDB
-- Versión de PHP: 8.2.12

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
CREATE DATABASE IF NOT EXISTS `gestion_camping` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gestion_camping`;

DELIMITER $$
--
-- Procedimientos
--
DROP PROCEDURE IF EXISTS `actualizar_estado_manualmente`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizar_estado_manualmente` ()   BEGIN
  UPDATE recurso
  SET ESTADO = 'DISPONIBLE'
  WHERE ESTADO != 'MANTENIMIENTO';

  UPDATE recurso r
  JOIN reserva res ON r.IDRECURSO = res.IDRECURSO
  SET r.ESTADO = 'OCUPADO'
  WHERE CURDATE() BETWEEN res.FECHA_INICIO AND res.FECHA_FIN
    AND r.ESTADO != 'MANTENIMIENTO';
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `acompanante`
--

DROP TABLE IF EXISTS `acompanante`;
CREATE TABLE `acompanante` (
  `IDACOMPANANTE` int(10) UNSIGNED NOT NULL,
  `IDRESERVA` int(10) UNSIGNED NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(50) NOT NULL,
  `DNI` varchar(9) DEFAULT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `TELEFONO` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `acompanante`
--

INSERT INTO `acompanante` (`IDACOMPANANTE`, `IDRESERVA`, `NOMBRE`, `APELLIDO`, `DNI`, `EMAIL`, `TELEFONO`) VALUES
(11, 7, 'Laura', 'Gomez', '12345678A', 'laura.gomez@email.com', '600123456'),
(12, 7, 'Carlos', 'Martinez', '87654321B', 'carlos.martinez@email.com', '600654321'),
(14, 22, 'Miguel', 'Fernandez', '98765432C', 'miguel.fernandez@email.com', NULL),
(15, 22, 'Sofia', 'Perez', '12345678Z', 'sofia.perez@email.com', '600333444'),
(19, 24, 'Andrews', 'Dos Ramos', '12345679D', 'andrewsdosramos@gmail.com', '634127185');

--
-- Disparadores `acompanante`
--
DROP TRIGGER IF EXISTS `restar_persona_reserva`;
DELIMITER $$
CREATE TRIGGER `restar_persona_reserva` AFTER DELETE ON `acompanante` FOR EACH ROW BEGIN
    UPDATE reserva
    SET NUMERO_PERSONAS = NUMERO_PERSONAS - 1
    WHERE IDRESERVA = OLD.IDRESERVA;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `sumar_persona_reserva`;
DELIMITER $$
CREATE TRIGGER `sumar_persona_reserva` AFTER INSERT ON `acompanante` FOR EACH ROW BEGIN
    UPDATE reserva
    SET NUMERO_PERSONAS = NUMERO_PERSONAS + 1
    WHERE IDRESERVA = NEW.IDRESERVA;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

DROP TABLE IF EXISTS `cliente`;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`IDCLIENTE`, `NOMBRE`, `APELLIDO`, `DNI`, `EMAIL`, `TELEFONO`, `FECHA_NACIMIENTO`, `ESTADO`, `COMENTARIOS`) VALUES
(1, 'Cliente', 'Prueba', '12345678A', 'cliente.prueba@example.com', '600000001', '1980-01-01', 'ACTIVO', 'Cliente de prueba'),
(2, 'Andrews', 'Garcia', '87654321B', 'ana.garcia@example.com', '600654321', '1990-07-22', 'ACTIVO', 'Problemas con pagos.'),
(3, 'Luis', 'Lopez', '56781234C', 'luis.martinez@example.com', '600987654', NULL, 'BLOQUEADO', 'Incumplió normas.'),
(9, 'Andrews', 'Dos Ramos', '11111111A', 'andrews@gmail.com', '634127185', '2024-12-11', 'ACTIVO', ''),
(11, 'Andrews', 'Dos Ramos', '22222222B', 'andrewsdosramos@gmail.com', '634127185', '2025-03-15', 'ACTIVO', ''),
(12, 'Juan', 'Perez', '33333333C', 'juan.perez@email.com', '600123456', NULL, 'ACTIVO', NULL),
(13, 'Maria', 'Rodriguez', '44444444D', 'maria.rodriguez@email.com', '600654321', '1990-08-20', 'ACTIVO', NULL),
(14, 'Luis', 'Garcia', '55555555E', 'luis.garcia@email.com', '600111222', '1985-03-10', 'ACTIVO', NULL),
(18, 'Carlos', 'Ramirez', '66666666F', 'carlos.ramirez@email.com', '600987654', '1982-03-12', 'ACTIVO', NULL),
(19, 'Maria', 'Sanchez', '77777777G', 'maria.sanchez@email.com', '600123987', '1991-07-23', 'ACTIVO', NULL),
(20, 'Elena', 'Torres', '88888888H', 'elena.torres@email.com', '600456789', '1988-11-05', 'ACTIVO', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recurso`
--

DROP TABLE IF EXISTS `recurso`;
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
(3, 'Parcela 5', 'PARCELA', 6, 30, 1, 'MANTENIMIENTO'),
(5, 'EL1', 'BUNGALOW', 4, 50, 2, 'OCUPADO'),
(6, 'EL2', 'BUNGALOW', 5, 55, 2, 'DISPONIBLE'),
(7, 'EL3', 'BUNGALOW', 3, 45, 1, 'DISPONIBLE'),
(8, 'EL4', 'BUNGALOW', 4, 52, 2, 'MANTENIMIENTO'),
(9, 'DI', 'BUNGALOW', 4, 55, 2, 'DISPONIBLE'),
(10, 'DII', 'BUNGALOW', 5, 60, 2, 'MANTENIMIENTO'),
(11, 'B10', 'BUNGALOW', 4, 50, 2, 'OCUPADO'),
(12, 'B11', 'BUNGALOW', 5, 60, 2, 'OCUPADO'),
(13, 'B12', 'BUNGALOW', 3, 45, 1, 'DISPONIBLE'),
(14, 'B13', 'BUNGALOW', 6, 65, 3, 'MANTENIMIENTO'),
(15, 'B14', 'BUNGALOW', 4, 55, 2, 'DISPONIBLE'),
(16, 'B15', 'BUNGALOW', 5, 58, 2, 'DISPONIBLE'),
(17, 'B16', 'BUNGALOW', 4, 52, 2, 'DISPONIBLE'),
(18, 'B17', 'BUNGALOW', 3, 47, 1, 'DISPONIBLE'),
(19, 'B18', 'BUNGALOW', 6, 70, 3, 'DISPONIBLE'),
(20, 'B19', 'BUNGALOW', 5, 62, 2, 'MANTENIMIENTO'),
(21, 'B20', 'BUNGALOW', 4, 50, 2, 'OCUPADO'),
(22, 'B21', 'BUNGALOW', 5, 60, 2, 'DISPONIBLE'),
(23, 'B22', 'BUNGALOW', 4, 55, 2, 'OCUPADO'),
(24, 'B23', 'BUNGALOW', 3, 48, 1, 'DISPONIBLE'),
(25, 'B24', 'BUNGALOW', 6, 66, 3, 'DISPONIBLE'),
(26, 'B25', 'BUNGALOW', 4, 54, 2, 'DISPONIBLE'),
(27, 'B26', 'BUNGALOW', 5, 59, 2, 'MANTENIMIENTO'),
(28, 'B27', 'BUNGALOW', 3, 44, 1, 'DISPONIBLE'),
(29, 'B28', 'BUNGALOW', 4, 53, 2, 'DISPONIBLE'),
(30, 'B29', 'BUNGALOW', 5, 61, 2, 'DISPONIBLE'),
(31, 'A2', 'PARCELA', 6, 40, 1, 'DISPONIBLE'),
(32, 'A3', 'PARCELA', 5, 38, 1, 'DISPONIBLE'),
(33, 'A4', 'PARCELA', 4, 35, 1, 'DISPONIBLE'),
(34, 'A5', 'PARCELA', 6, 42, 1, 'OCUPADO'),
(35, 'A6', 'PARCELA', 5, 39, 1, 'MANTENIMIENTO'),
(36, 'A7', 'PARCELA', 6, 41, 1, 'DISPONIBLE'),
(37, 'A8', 'PARCELA', 4, 36, 1, 'DISPONIBLE'),
(38, 'A9', 'PARCELA', 5, 38, 1, 'DISPONIBLE'),
(39, 'A12', 'PARCELA', 6, 43, 1, 'DISPONIBLE'),
(40, 'A13', 'PARCELA', 5, 39, 1, 'DISPONIBLE'),
(41, 'A14', 'PARCELA', 6, 41, 1, 'OCUPADO'),
(42, 'A15', 'PARCELA', 4, 35, 1, 'DISPONIBLE'),
(43, 'A16', 'PARCELA', 5, 40, 1, 'DISPONIBLE'),
(44, 'A17', 'PARCELA', 6, 42, 1, 'DISPONIBLE'),
(45, 'A18', 'PARCELA', 5, 39, 1, 'DISPONIBLE'),
(46, 'A19', 'PARCELA', 4, 36, 1, 'DISPONIBLE'),
(47, 'A20', 'PARCELA', 6, 43, 1, 'DISPONIBLE'),
(48, 'A21', 'PARCELA', 5, 40, 1, 'DISPONIBLE'),
(49, 'A22', 'PARCELA', 6, 41, 1, 'DISPONIBLE'),
(50, 'A23', 'PARCELA', 4, 35, 1, 'DISPONIBLE'),
(51, 'A24', 'PARCELA', 5, 38, 1, 'DISPONIBLE'),
(52, 'A25', 'PARCELA', 6, 42, 1, 'DISPONIBLE'),
(53, 'A26', 'PARCELA', 5, 39, 1, 'DISPONIBLE'),
(54, 'A27', 'PARCELA', 6, 41, 1, 'DISPONIBLE'),
(55, 'A28', 'PARCELA', 4, 36, 1, 'DISPONIBLE'),
(56, 'A29', 'PARCELA', 5, 40, 1, 'DISPONIBLE'),
(57, 'A30', 'PARCELA', 6, 43, 1, 'DISPONIBLE'),
(58, 'S13', 'PARCELA', 5, 38, 1, 'DISPONIBLE'),
(59, 'S14', 'PARCELA', 6, 40, 1, 'OCUPADO'),
(60, 'S15', 'PARCELA', 4, 35, 1, 'DISPONIBLE'),
(61, 'S16', 'PARCELA', 5, 39, 1, 'MANTENIMIENTO'),
(62, 'S17', 'PARCELA', 6, 41, 1, 'DISPONIBLE'),
(63, 'S18', 'PARCELA', 5, 38, 1, 'DISPONIBLE'),
(64, 'S21', 'PARCELA', 4, 36, 1, 'DISPONIBLE'),
(65, 'S22', 'PARCELA', 5, 40, 1, 'DISPONIBLE'),
(66, 'S23', 'PARCELA', 6, 42, 1, 'DISPONIBLE'),
(67, 'S24', 'PARCELA', 5, 39, 1, 'DISPONIBLE'),
(68, 'S25', 'PARCELA', 4, 35, 1, 'DISPONIBLE'),
(69, 'S26', 'PARCELA', 5, 38, 1, 'DISPONIBLE'),
(70, 'S27', 'PARCELA', 6, 41, 1, 'DISPONIBLE'),
(71, 'S28', 'PARCELA', 5, 39, 1, 'DISPONIBLE'),
(72, 'S29', 'PARCELA', 4, 36, 1, 'DISPONIBLE'),
(73, 'S30', 'PARCELA', 5, 40, 1, 'DISPONIBLE'),
(74, 'S31', 'PARCELA', 6, 42, 1, 'DISPONIBLE'),
(75, 'S32', 'PARCELA', 5, 38, 1, 'DISPONIBLE'),
(76, 'S33', 'PARCELA', 4, 35, 1, 'DISPONIBLE'),
(77, 'S34', 'PARCELA', 5, 39, 1, 'DISPONIBLE'),
(78, 'S35', 'PARCELA', 6, 41, 1, 'DISPONIBLE'),
(79, 'S36', 'PARCELA', 5, 38, 1, 'DISPONIBLE'),
(80, 'S37', 'PARCELA', 4, 36, 1, 'DISPONIBLE'),
(81, 'S38', 'PARCELA', 5, 40, 1, 'DISPONIBLE'),
(82, 'S39', 'PARCELA', 6, 42, 1, 'DISPONIBLE'),
(83, 'barbacoa_1', 'BARBACOA', 6, 10, 1, 'OCUPADO'),
(84, 'barbacoa_2', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(85, 'barbacoa_3', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(86, 'barbacoa_4', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(87, 'barbacoa_5', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(88, 'barbacoa_6', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(89, 'barbacoa_7', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(90, 'barbacoa_8', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(91, 'barbacoa_9', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(92, 'barbacoa_10', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(93, 'barbacoa_11', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(94, 'barbacoa_12', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(95, 'barbacoa_13', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(96, 'barbacoa_14', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(97, 'barbacoa_15', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(98, 'barbacoa_16', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(99, 'barbacoa_17', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(100, 'barbacoa_18', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(101, 'barbacoa_19', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(102, 'barbacoa_20', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(103, 'barbacoa_21', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(104, 'barbacoa_22', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(105, 'barbacoa_23', 'BARBACOA', 6, 10, 1, 'OCUPADO'),
(106, 'barbacoa_24', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(107, 'barbacoa_25', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(108, 'barbacoa_26', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(109, 'barbacoa_27', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(110, 'barbacoa_28', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(111, 'barbacoa_29', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(112, 'barbacoa_30', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(113, 'barbacoa_31', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(114, 'barbacoa_32', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(115, 'barbacoa_33', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(116, 'barbacoa_34', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(117, 'barbacoa_35', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(118, 'barbacoa_36', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(119, 'barbacoa_37', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(120, 'barbacoa_38', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(121, 'barbacoa_39', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(122, 'barbacoa_40', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(123, 'barbacoa_41', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(124, 'barbacoa_42', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(125, 'barbacoa_43', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(126, 'barbacoa_44', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(127, 'barbacoa_45', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(128, 'barbacoa_46', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(129, 'barbacoa_47', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(130, 'barbacoa_48', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(131, 'barbacoa_49', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(132, 'barbacoa_50', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(133, 'barbacoa_51', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(134, 'barbacoa_52', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(135, 'barbacoa_53', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(136, 'barbacoa_54', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(137, 'barbacoa_55', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(138, 'barbacoa_56', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(139, 'barbacoa_57', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(140, 'barbacoa_58', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(141, 'barbacoa_59', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(142, 'barbacoa_60', 'BARBACOA', 6, 10, 1, 'DISPONIBLE'),
(143, 'B5', 'BUNGALOW', 4, 50, 2, 'DISPONIBLE'),
(144, 'B6', 'BUNGALOW', 4, 50, 2, 'DISPONIBLE'),
(145, 'B7', 'BUNGALOW', 4, 50, 2, 'MANTENIMIENTO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

DROP TABLE IF EXISTS `reserva`;
CREATE TABLE `reserva` (
  `IDRESERVA` int(10) UNSIGNED NOT NULL,
  `IDCLIENTE` int(10) UNSIGNED DEFAULT NULL,
  `IDRECURSO` int(10) UNSIGNED DEFAULT NULL,
  `FECHA_INICIO` date DEFAULT NULL,
  `FECHA_FIN` date DEFAULT NULL,
  `ESTADO` tinytext DEFAULT NULL,
  `PRECIO_TOTAL` int(5) UNSIGNED NOT NULL,
  `NUMERO_PERSONAS` int(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`IDRESERVA`, `IDCLIENTE`, `IDRECURSO`, `FECHA_INICIO`, `FECHA_FIN`, `ESTADO`, `PRECIO_TOTAL`, `NUMERO_PERSONAS`) VALUES
(7, 2, 1, '2024-12-01', '2024-12-05', 'ACTIVA', 400, 3),
(22, 18, 1, '2025-06-01', '2025-06-05', 'CONFIRMADA', 100, 2),
(23, 19, 2, '2025-06-10', '2025-06-15', 'CONFIRMADA', 150, 1),
(24, 20, 1, '2025-05-20', '2025-05-21', 'CANCELADA', 200, 3),
(25, 12, 1, '2025-05-12', '2025-05-13', 'ACTIVA', 200, 0),
(26, 12, 1, '2025-05-31', '2025-06-01', 'ACTIVA', 200, 0),
(27, 2, 31, '2025-07-01', '2025-07-05', 'ACTIVA', 160, 2),
(28, 3, 33, '2025-07-10', '2025-07-15', 'CONFIRMADA', 200, 3),
(29, 12, 40, '2025-08-01', '2025-08-03', 'CANCELADA', 100, 1),
(30, 18, 15, '2025-06-20', '2025-06-25', 'ACTIVA', 250, 4),
(31, 19, 16, '2025-09-05', '2025-09-10', 'CONFIRMADA', 280, 2),
(32, 20, 38, '2025-07-18', '2025-07-22', 'ACTIVA', 152, 3),
(33, 3, 144, '2025-08-10', '2025-08-12', 'ACTIVA', 100, 2),
(34, 2, 143, '2025-08-15', '2025-08-20', 'CONFIRMADA', 240, 4),
(35, 12, 55, '2025-09-01', '2025-09-05', 'CANCELADA', 150, 2),
(36, 18, 66, '2025-09-10', '2025-09-13', 'ACTIVA', 120, 1),
(54, 2, 5, '2025-06-16', '2025-06-20', 'ACTIVA', 200, 3),
(55, 3, 11, '2025-06-16', '2025-06-19', 'CONFIRMADA', 150, 4),
(56, 12, 34, '2025-06-16', '2025-06-21', 'ACTIVA', 210, 5),
(57, 18, 59, '2025-06-16', '2025-06-23', 'CONFIRMADA', 280, 4),
(58, 19, 85, '2025-06-16', '2025-06-16', 'ACTIVA', 10, 5),
(59, 20, 86, '2025-06-16', '2025-06-16', 'CONFIRMADA', 10, 6),
(60, 2, 2, '2025-06-17', '2025-06-17', 'ACTIVA', 50, 9),
(61, 3, 12, '2025-06-17', '2025-06-20', 'CONFIRMADA', 180, 5),
(63, 18, 23, '2025-06-17', '2025-06-22', 'CONFIRMADA', 275, 4),
(64, 19, 41, '2025-06-17', '2025-06-21', 'ACTIVA', 164, 6),
(65, 20, 105, '2025-06-17', '2025-06-17', 'CONFIRMADA', 10, 4),
(66, 2, 9, '2025-06-18', '2025-06-22', 'ACTIVA', 220, 3),
(67, 3, 13, '2025-06-18', '2025-06-23', 'CONFIRMADA', 225, 2),
(68, 12, 49, '2025-06-18', '2025-06-25', 'ACTIVA', 287, 5),
(69, 18, 70, '2025-06-18', '2025-06-28', 'CONFIRMADA', 410, 6),
(70, 19, 120, '2025-06-18', '2025-06-18', 'ACTIVA', 10, 5),
(73, 1, 5, '2025-06-16', '2025-06-18', 'CONFIRMADA', 150, 3),
(74, 2, 21, '2025-06-16', '2025-06-18', 'CONFIRMADA', 200, 4),
(75, 2, 5, '2025-06-14', '2025-06-16', 'CONFIRMADA', 120, 2),
(83, 1, 83, '2025-06-17', '2025-06-18', 'ACTIVA', 200, 0);

--
-- Disparadores `reserva`
--
DROP TRIGGER IF EXISTS `trg_reserva_delete`;
DELIMITER $$
CREATE TRIGGER `trg_reserva_delete` AFTER DELETE ON `reserva` FOR EACH ROW BEGIN
    
    IF NOT EXISTS (
        SELECT 1 FROM reserva
        WHERE idrecurso = OLD.idrecurso
          AND CURDATE() BETWEEN fecha_inicio AND fecha_fin
    ) THEN
        
        UPDATE recurso
        SET estado = 'DISPONIBLE'
        WHERE idrecurso = OLD.idrecurso
          AND estado != 'MANTENIMIENTO';
    END IF;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_reserva_insert`;
DELIMITER $$
CREATE TRIGGER `trg_reserva_insert` AFTER INSERT ON `reserva` FOR EACH ROW BEGIN
    
    IF CURDATE() BETWEEN NEW.fecha_inicio AND NEW.fecha_fin THEN
        UPDATE recurso
        SET estado = 'OCUPADO'
        WHERE idrecurso = NEW.idrecurso AND estado != 'MANTENIMIENTO';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDO` varchar(50) NOT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `NICKNAME` varchar(50) NOT NULL,
  `ESTADO` tinytext DEFAULT NULL,
  `CONTRASEÑA` varchar(60) NOT NULL,
  `intentos` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`ID`, `NOMBRE`, `APELLIDO`, `EMAIL`, `NICKNAME`, `ESTADO`, `CONTRASEÑA`, `intentos`) VALUES
(1, 'Andrews', 'Dos Ramos', 'andrewsdosramos@gmail.com', '0lin_D0s', 'ACTIVO', 'Dosramos02', 3),
(2, 'andrews', 'Dos ramos', 'andrewsdosramos@gmail.com', 'andrews', 'Activo', 'Dosramos02', 0),
(3, 'safa', 'asf', 'andrewsdosramos@gmail.com', 'fas', 'ACTIVO', 'Aa1$abcd', 0),
(4, 'isidro', 'Juan', 'isi@gmail.com', 'sasa', 'ACTIVO', 'Aa1$abcd', NULL),
(5, 'jose', 'perez', 'perez@gmail.com', 'lala', 'ACTIVO', '$2a$12$S93YMdfkbekobXqGkKy60uTeSE.MiTaxKtbI8P0VvlnLjJE/NXqgO', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `acompanante`
--
ALTER TABLE `acompanante`
  ADD PRIMARY KEY (`IDACOMPANANTE`),
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
-- AUTO_INCREMENT de la tabla `acompanante`
--
ALTER TABLE `acompanante`
  MODIFY `IDACOMPANANTE` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `IDCLIENTE` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `recurso`
--
ALTER TABLE `recurso`
  MODIFY `IDRECURSO` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=147;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `IDRESERVA` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `acompanante`
--
ALTER TABLE `acompanante`
  ADD CONSTRAINT `acompanante_ibfk_1` FOREIGN KEY (`IDRESERVA`) REFERENCES `reserva` (`IDRESERVA`);

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`IDCLIENTE`) REFERENCES `cliente` (`IDCLIENTE`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`IDRECURSO`) REFERENCES `recurso` (`IDRECURSO`);

DELIMITER $$
--
-- Eventos
--
DROP EVENT IF EXISTS `actualizar_estado_recursos`$$
CREATE DEFINER=`root`@`localhost` EVENT `actualizar_estado_recursos` ON SCHEDULE EVERY 1 MINUTE STARTS '2025-06-16 13:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
    UPDATE recurso
    SET estado = 'DISPONIBLE'
    WHERE estado != 'MANTENIMIENTO';

    UPDATE recurso r
    JOIN reserva res ON r.idrecurso = res.idrecurso
    SET r.estado = 'OCUPADO'
    WHERE CURDATE() BETWEEN res.fecha_inicio AND res.fecha_fin
      AND r.estado != 'MANTENIMIENTO';
END$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
