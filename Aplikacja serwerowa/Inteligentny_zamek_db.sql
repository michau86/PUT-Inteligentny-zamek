-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 10 Gru 2017, 23:46
-- Wersja serwera: 10.1.25-MariaDB
-- Wersja PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `inteligentny_zamek_db`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `ID_USER` int(10) NOT NULL,
  `LOGIN` varchar(50) NOT NULL,
  `PASSWORD` varchar(64) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  `SURNAME` varchar(50) NOT NULL,
  `TOKEN` varchar(300) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `ISACTIVATED` tinyint(1) NOT NULL DEFAULT '0',
  `IS_ADMIN` tinyint(1) NOT NULL,
  `PUBLIC_KEY` varchar(300) DEFAULT NULL,
  `Serial_number` varchar(300) DEFAULT NULL,
  `Validitiy_period` datetime DEFAULT NULL,
  `Version` int(11) NOT NULL DEFAULT '1',
  `Signature_Algorithm_Identifier` varchar(30) NOT NULL DEFAULT 'RSA',
  `Hash_Algorithm` varchar(30) NOT NULL DEFAULT 'SHA-256'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`ID_USER`, `LOGIN`, `PASSWORD`, `NAME`, `SURNAME`, `TOKEN`, `ISACTIVATED`, `IS_ADMIN`, `PUBLIC_KEY`, `Serial_number`, `Validitiy_period`, `Version`, `Signature_Algorithm_Identifier`, `Hash_Algorithm`) VALUES
(134, 'Maciej1', '01D48A697442ED3231773C944973E87315FFD44232A2503D1B68B02F1D677FF5', 'Maciej', 'Marciniak', 'aaa', 0, 1, 'ddd', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKPkWrNcWpBxWQLILzAN3xLG7U9J2DM8d/70yxR6DbjY9/2nSTJQ6/uUY/t9O8Fy6AGjKWocaUuIbMuUrtkgc7WPHn6zIQIFlhht/GgBx7h7CWD47ZFzjtEGdP552lVA4fiSEBkvSRVooTew0msngLPvbEthqs9HeyP3mPY8/QEwIDAQAB', '2018-12-10 23:46:11', 1, 'RSA', 'SHA-256'),
(136, 'Maciej3', '01D48A697442ED3231773C944973E87315FFD44232A2503D1B68B02F1D677FF5', 'Mm', 'Mm', NULL, 0, 1, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHZcJstVK0Vg5ZEnHq0UTC1dhM8h8srCR3jTl3\nSpk+V0xWapLga3gRSN3ne0nPjRw+YpRe+ZAWd5E9OuEUBi0UwfYYEmLU3t3djMR4J2q++tyvQ4g8\ny/Q2mOLnXzw5gDtl3jfhdmZOVIEJeJkpqCVINNRNcHSAg+P7KGAiUFm7JQIDAQAB\n', '', NULL, 1, 'RSA', 'SHA-256');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID_USER`),
  ADD UNIQUE KEY `LOGIN` (`LOGIN`),
  ADD UNIQUE KEY `PUBLIC_KEY` (`PUBLIC_KEY`),
  ADD KEY `Serial_number` (`Serial_number`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `ID_USER` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=137;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
