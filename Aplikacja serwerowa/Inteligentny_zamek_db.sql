-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas generowania: 17 Kwi 2017, 22:04
-- Wersja serwera: 5.5.54-0+deb8u1
-- Wersja PHP: 5.6.30-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `Inteligentny_zamek_db`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ACCESS_TO_LOCKS`
--

CREATE TABLE IF NOT EXISTS `ACCESS_TO_LOCKS` (
  `ID` int(10) NOT NULL,
  `ID_KEY` int(10) NOT NULL,
  `DATE` date NOT NULL,
  `ACCESS` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `LOCKS`
--

CREATE TABLE IF NOT EXISTS `LOCKS` (
  `ID_LOCK` int(10) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  `LOCALIZATION` text CHARACTER SET utf8 COLLATE utf8_polish_ci
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `LOCKS`
--

INSERT INTO `LOCKS` (`ID_LOCK`, `NAME`, `LOCALIZATION`) VALUES
(1, 'Zamek testowy', NULL);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `LOCKS_KEYS`
--

CREATE TABLE IF NOT EXISTS `LOCKS_KEYS` (
  `ID_KEY` int(10) NOT NULL,
  `ID_LOCK` int(10) NOT NULL,
  `ID_USER` int(10) NOT NULL,
  `LOCK_KEY` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `FROM_DATE` date NOT NULL,
  `TO_DATE` date NOT NULL,
  `ISACTUAL` timestamp NULL DEFAULT NULL,
  `MONDAY` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `TUESDAY` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `WEDNESDAY` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `THURSDAY` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `FRIDAY` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `SATURDAY` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `SUNDAY` varchar(10) COLLATE utf8_polish_ci DEFAULT NULL,
  `IS_PERNAMENT` tinyint(1) NOT NULL,
  `NAME` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `SURNAME` varchar(50) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `LOCKS_KEYS`
--

INSERT INTO `LOCKS_KEYS` (`ID_KEY`, `ID_LOCK`, `ID_USER`, `LOCK_KEY`, `FROM_DATE`, `TO_DATE`, `ISACTUAL`, `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY`, `IS_PERNAMENT`, `NAME`, `SURNAME`) VALUES
(1, 1, 123, 'c411bba96157e3dfeecbc80e11ef3c18465aec26b7348cfcb13296d0ba523068', '2017-04-18', '2018-04-17', NULL, '8-12;14-16', NULL, NULL, NULL, NULL, NULL, NULL, 0, 'Maciej', 'Marciniak');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `USERS`
--

CREATE TABLE IF NOT EXISTS `USERS` (
  `ID_USER` int(10) NOT NULL,
  `LOGIN` varchar(50) NOT NULL,
  `PASSWORD` varchar(64) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  `SURNAME` varchar(50) NOT NULL,
  `IS_ADMIN` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `USERS`
--

INSERT INTO `USERS` (`ID_USER`, `LOGIN`, `PASSWORD`, `NAME`, `SURNAME`, `IS_ADMIN`) VALUES
(123, 'mapet', 'c411bba96157e3dfeecbc80e11ef3c18465aec26b7348cfcb13296d0ba523068', 'Maciej', 'Marciniak', 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `ACCESS_TO_LOCKS`
--
ALTER TABLE `ACCESS_TO_LOCKS`
 ADD PRIMARY KEY (`ID`,`ID_KEY`), ADD KEY `ID_KEY` (`ID_KEY`);

--
-- Indexes for table `LOCKS`
--
ALTER TABLE `LOCKS`
 ADD PRIMARY KEY (`ID_LOCK`), ADD UNIQUE KEY `NAME` (`NAME`);

--
-- Indexes for table `LOCKS_KEYS`
--
ALTER TABLE `LOCKS_KEYS`
 ADD PRIMARY KEY (`ID_KEY`), ADD UNIQUE KEY `KEY` (`LOCK_KEY`), ADD KEY `ID_LOCK` (`ID_LOCK`), ADD KEY `ID_LOCK_2` (`ID_LOCK`,`ID_USER`), ADD KEY `ID_USER` (`ID_USER`);

--
-- Indexes for table `USERS`
--
ALTER TABLE `USERS`
 ADD PRIMARY KEY (`ID_USER`), ADD UNIQUE KEY `LOGIN` (`LOGIN`);

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `ACCESS_TO_LOCKS`
--
ALTER TABLE `ACCESS_TO_LOCKS`
ADD CONSTRAINT `ACCESS_TO_LOCKS_ibfk_1` FOREIGN KEY (`ID_KEY`) REFERENCES `LOCKS_KEYS` (`ID_KEY`);

--
-- Ograniczenia dla tabeli `LOCKS_KEYS`
--
ALTER TABLE `LOCKS_KEYS`
ADD CONSTRAINT `LOCKS_KEYS_ibfk_1` FOREIGN KEY (`ID_LOCK`) REFERENCES `LOCKS` (`ID_LOCK`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `LOCKS_KEYS_ibfk_2` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
