-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 07, 2021 at 04:28 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `justduit`
--
CREATE DATABASE IF NOT EXISTS `justduit` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `justduit`;
-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `EmployeeID` int(11) NOT NULL,
  `RoleID` int(11) NOT NULL,
  `EmployeeName` varchar(50) NOT NULL,
  `EmployeeUsername` varchar(50) NOT NULL,
  `EmployeeSalary` int(11) NOT NULL,
  `EmployeeStatus` varchar(50) NOT NULL,
  `EmployeePassword` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`EmployeeID`, `RoleID`, `EmployeeName`, `EmployeeUsername`, `EmployeeSalary`, `EmployeeStatus`, `EmployeePassword`) VALUES
(2, 2, 'Gregorius Ryan', 'gregryan', 3600000, 'Active', 'gregryan'),
(3, 4, 'Kenjovan Nanggala', 'Kenjo', 3400000, 'Active', 'Kenjo'),
(4, 3, 'Arief Liman', 'ariefliman', 4400000, 'Active', 'ariefliman'),
(5, 1, 'Indra Gunawan', 'igun', 1230000, 'Active', 'igun');

-- --------------------------------------------------------

--
-- Table structure for table `employeerole`
--

CREATE TABLE `employeerole` (
  `RoleID` int(11) NOT NULL,
  `RoleName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employeerole`
--

INSERT INTO `employeerole` (`RoleID`, `RoleName`) VALUES
(1, 'Manager'),
(2, 'Human Resources'),
(3, 'Product Management'),
(4, 'Cashier');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ProductID` int(11) NOT NULL,
  `ProductName` varchar(25) NOT NULL,
  `ProductDescription` varchar(50) NOT NULL,
  `ProductPrice` int(11) NOT NULL,
  `ProductStock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`ProductID`, `ProductName`, `ProductDescription`, `ProductPrice`, `ProductStock`) VALUES
(1, 'Nike Jordan 14', 'Nike From Michael Jordan Version 14', 1500000, 4),
(2, 'Nike Black Mamba', 'Black Mamba From Kobe Bryant', 1600000, 2),
(3, 'Nike Jordan 15', 'Sepatu Nike Jordan Type 15', 1800000, 8),
(4, 'Nike Jordan 16', 'Jordan 16 Warna Remix', 1900000, 8);

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` int(11) NOT NULL,
  `ProductID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `ProductID`, `Quantity`) VALUES
(1, 1, 2),
(2, 4, 2);

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` int(11) NOT NULL,
  `TransactionDate` date NOT NULL,
  `EmployeeID` int(11) NOT NULL,
  `TotalPrice` int(11) NOT NULL,
  `PaymentType` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `TransactionDate`, `EmployeeID`, `TotalPrice`, `PaymentType`) VALUES
(1, '2021-06-07', 3, 3000000, 'Cash'),
(2, '2021-06-07', 3, 3800000, 'Card');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`EmployeeID`),
  ADD UNIQUE KEY `EmployeeUsername` (`EmployeeUsername`);

--
-- Indexes for table `employeerole`
--
ALTER TABLE `employeerole`
  ADD PRIMARY KEY (`RoleID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ProductID`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `EmployeeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `employeerole`
--
ALTER TABLE `employeerole`
  MODIFY `RoleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `transactionheader`
--
ALTER TABLE `transactionheader`
  MODIFY `TransactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
