-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 25 Sty 2018, 21:52
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
-- Struktura tabeli dla tabeli `access_to_locks`
--

CREATE TABLE `access_to_locks` (
  `ID` int(10) NOT NULL,
  `ID_KEY` int(10) NOT NULL,
  `DATE` datetime NOT NULL,
  `ACCESS` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `access_to_locks`
--

INSERT INTO `access_to_locks` (`ID`, `ID_KEY`, `DATE`, `ACCESS`) VALUES
(1, 2, '2017-05-31 00:00:00', 0),
(2, 2, '2017-05-31 00:00:00', 1),
(3, 2, '2017-05-31 00:00:00', 1),
(4, 2, '2017-05-31 00:00:00', 0),
(5, 2, '2017-05-31 00:00:00', 0),
(6, 2, '2017-05-31 00:00:00', 0),
(7, 2, '2017-05-31 00:00:00', 0),
(8, 2, '2017-05-31 22:32:32', 0),
(9, 2, '2017-05-31 22:33:35', 0),
(10, 2, '2017-05-31 22:40:16', 0),
(11, 2, '2017-05-31 22:40:40', 0),
(12, 2, '2017-05-31 22:41:45', 0),
(13, 2, '2017-05-31 22:42:37', 0),
(14, 2, '2017-05-31 22:43:40', 0),
(15, 2, '2017-05-31 22:45:01', 0),
(16, 2, '2017-05-31 22:46:20', 0),
(17, 2, '2017-05-31 22:47:10', 0),
(18, 2, '2017-05-31 22:48:42', 0),
(19, 2, '2017-05-31 22:51:08', 0),
(20, 2, '2017-06-01 00:52:47', 0),
(21, 2, '2017-06-01 00:54:55', 0),
(22, 2, '2017-06-01 00:55:17', 0),
(23, 2, '2017-06-01 00:56:02', 0),
(24, 2, '2017-06-01 00:58:26', 1),
(25, 2, '2017-06-01 00:59:33', 1),
(26, 2, '2017-06-01 00:59:56', 1),
(27, 2, '2017-06-01 01:00:08', 1),
(28, 2, '2017-06-01 01:00:49', 1),
(29, 2, '2017-06-01 01:01:05', 1),
(30, 2, '2017-06-01 01:01:17', 1),
(31, 2, '2017-06-01 01:01:46', 1),
(32, 2, '2017-06-01 01:23:34', 1),
(33, 2, '2017-06-01 01:45:01', 1),
(34, 2, '2017-06-01 01:45:49', 1),
(35, 2, '2017-06-01 08:11:44', 1),
(36, 2, '2017-06-01 08:15:11', 1),
(37, 2, '2017-06-01 08:15:22', 1),
(38, 2, '2017-06-01 08:33:17', 1),
(39, 2, '2017-06-01 08:34:22', 1),
(40, 2, '2017-06-01 08:34:38', 1),
(41, 2, '2017-06-01 08:34:54', 1),
(42, 2, '2017-06-01 08:46:31', 0),
(43, 2, '2017-06-01 10:02:54', 0),
(44, 2, '2017-06-01 10:02:55', 0),
(45, 2, '2017-06-01 10:02:56', 0),
(46, 2, '2017-06-01 10:09:14', 0),
(47, 2, '2017-06-01 10:13:49', 1),
(48, 2, '2017-06-01 10:36:29', 1),
(49, 2, '2017-06-01 10:41:53', 1),
(50, 2, '2017-06-01 10:44:22', 1),
(51, 2, '2017-06-01 10:44:39', 1),
(52, 2, '2017-06-01 10:44:54', 1),
(53, 2, '2017-06-01 10:45:24', 1),
(54, 2, '2017-06-01 10:56:15', 1),
(55, 2, '2017-06-01 10:58:17', 0),
(56, 2, '2017-06-01 10:58:38', 0),
(57, 2, '2017-07-02 15:00:18', 0),
(58, 2, '2017-07-02 15:07:44', 0),
(59, 2, '2017-07-02 15:12:34', 0),
(60, 2, '2017-07-02 15:12:35', 0),
(61, 2, '2017-07-02 15:14:19', 1),
(62, 2, '2017-07-02 15:14:31', 1),
(63, 2, '2017-07-02 15:14:49', 1),
(64, 2, '2017-07-02 15:15:13', 1),
(65, 2, '2017-07-02 15:16:34', 1),
(66, 2, '2017-07-02 15:17:15', 1),
(67, 2, '2017-07-02 15:17:57', 1),
(68, 2, '2017-07-02 16:06:03', 1),
(69, 2, '2017-07-02 16:07:24', 1),
(70, 2, '2017-07-02 16:09:29', 1),
(71, 2, '2017-07-02 16:09:56', 0),
(72, 2, '2017-07-04 02:26:05', 0),
(73, 2, '2017-07-04 02:26:17', 0),
(74, 2, '2017-07-04 02:26:40', 0),
(75, 2, '2017-07-04 02:26:53', 0),
(76, 2, '2017-07-04 02:27:09', 0),
(77, 2, '2017-07-04 02:27:47', 1),
(78, 2, '2017-07-04 02:29:18', 0),
(79, 2, '2017-07-04 02:29:33', 0),
(80, 2, '2017-07-04 02:29:51', 0),
(81, 2, '2017-07-04 02:30:49', 1),
(82, 2, '2017-07-04 02:59:25', 1),
(83, 2, '2017-07-04 16:54:38', 0),
(84, 2, '2017-07-04 16:55:50', 0),
(85, 2, '2017-07-04 16:55:56', 0),
(86, 2, '2017-07-04 16:58:19', 0),
(87, 2, '2017-07-04 17:29:09', 0),
(88, 2, '2017-07-04 17:29:46', 0),
(89, 2, '2017-07-04 17:30:19', 0),
(90, 2, '2017-07-04 17:30:37', 0),
(91, 2, '2017-07-04 17:30:54', 0),
(92, 2, '2017-07-04 17:31:42', 0),
(93, 2, '2017-07-04 17:32:01', 0),
(94, 2, '2017-07-04 17:32:01', 0),
(95, 2, '2017-07-04 17:32:33', 0),
(96, 2, '2017-07-04 17:32:34', 0),
(97, 2, '2017-07-04 17:34:26', 0),
(98, 2, '2017-07-04 17:43:40', 0),
(99, 2, '2017-07-04 18:08:04', 0),
(100, 2, '2017-07-04 18:08:38', 0),
(101, 2, '2017-07-04 18:08:38', 0),
(102, 2, '2017-07-04 18:09:24', 0),
(103, 2, '2017-07-04 18:10:25', 0),
(104, 2, '2017-07-04 18:18:49', 0),
(105, 2, '2017-07-04 18:19:06', 0),
(106, 2, '2017-07-04 18:19:55', 0),
(107, 2, '2017-07-04 18:20:10', 0),
(108, 2, '2017-07-04 18:39:24', 0),
(109, 2, '2017-07-04 18:40:14', 0),
(110, 2, '2017-07-04 18:41:19', 0),
(111, 2, '2017-07-04 18:48:30', 0),
(112, 2, '2017-07-04 19:21:45', 0),
(113, 2, '2017-07-04 20:39:19', 0),
(114, 2, '2017-07-04 20:43:33', 0),
(115, 2, '2017-07-04 22:30:28', 0),
(116, 2, '2017-07-04 23:02:53', 1),
(117, 2, '2017-07-04 23:04:34', 1),
(118, 2, '2017-07-04 23:04:51', 1),
(119, 2, '2017-07-04 23:06:19', 1),
(120, 2, '2017-07-04 23:06:38', 1),
(121, 2, '2017-07-04 23:13:33', 1),
(122, 2, '2017-07-04 23:20:15', 1),
(123, 2, '2017-07-04 23:29:46', 1),
(124, 2, '2017-07-05 00:39:48', 1),
(125, 5, '2017-12-02 22:54:01', 0),
(126, 5, '2017-12-02 22:54:32', 0),
(127, 5, '2017-12-02 23:01:16', 0),
(128, 5, '2017-12-02 23:06:56', 0),
(129, 5, '2017-12-02 23:12:00', 1),
(130, 5, '2017-12-02 23:12:57', 1),
(131, 5, '2017-12-02 23:13:27', 1),
(132, 5, '2017-12-03 11:19:34', 1),
(133, 5, '2017-12-03 11:23:51', 1),
(134, 5, '2017-12-03 11:24:26', 1),
(135, 5, '2017-12-03 11:25:57', 1),
(136, 5, '2017-12-03 11:34:57', 1),
(137, 5, '2017-12-03 11:57:57', 1),
(138, 5, '2017-12-03 11:58:08', 1),
(139, 5, '2017-12-03 12:25:38', 1),
(140, 5, '2017-12-06 15:53:36', 1),
(141, 5, '2017-12-06 16:00:14', 1),
(142, 5, '2017-12-06 20:25:57', 1),
(143, 5, '2017-12-11 08:09:05', 1),
(144, 5, '2017-12-11 08:27:46', 1),
(145, 5, '2017-12-11 08:40:14', 1),
(146, 5, '2017-12-11 08:40:24', 1),
(147, 5, '2017-12-11 08:40:34', 1),
(148, 5, '2017-12-11 08:48:25', 1),
(149, 14, '2018-01-25 05:30:11', 0),
(150, 14, '2018-01-25 09:34:40', 0),
(151, 14, '2018-01-25 09:35:49', 0),
(152, 14, '2018-01-25 09:36:15', 0),
(153, 14, '2018-01-25 09:36:52', 0),
(154, 14, '2018-01-25 09:42:57', 0),
(155, 14, '2018-01-25 09:44:10', 0),
(156, 14, '2018-01-25 13:53:35', 0),
(157, 14, '2018-01-25 14:17:41', 1),
(158, 14, '2018-01-25 14:18:09', 1),
(159, 14, '2018-01-25 14:18:42', 1),
(160, 14, '2018-01-25 14:21:32', 1),
(161, 14, '2018-01-25 14:22:10', 1),
(162, 14, '2018-01-25 14:25:31', 1),
(163, 14, '2018-01-25 14:25:57', 1),
(164, 14, '2018-01-25 14:26:28', 1),
(165, 14, '2018-01-25 14:28:50', 0),
(166, 14, '2018-01-25 14:29:11', 0),
(167, 14, '2018-01-25 14:29:30', 0),
(168, 14, '2018-01-25 14:29:49', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_group`
--

CREATE TABLE `auth_group` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_group_permissions`
--

CREATE TABLE `auth_group_permissions` (
  `id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_permission`
--

CREATE TABLE `auth_permission` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `auth_permission`
--

INSERT INTO `auth_permission` (`id`, `name`, `content_type_id`, `codename`) VALUES
(1, 'Can add log entry', 1, 'add_logentry'),
(2, 'Can change log entry', 1, 'change_logentry'),
(3, 'Can delete log entry', 1, 'delete_logentry'),
(4, 'Can add group', 2, 'add_group'),
(5, 'Can change group', 2, 'change_group'),
(6, 'Can delete group', 2, 'delete_group'),
(7, 'Can add permission', 3, 'add_permission'),
(8, 'Can change permission', 3, 'change_permission'),
(9, 'Can delete permission', 3, 'delete_permission'),
(10, 'Can add user', 4, 'add_user'),
(11, 'Can change user', 4, 'change_user'),
(12, 'Can delete user', 4, 'delete_user'),
(13, 'Can add content type', 5, 'add_contenttype'),
(14, 'Can change content type', 5, 'change_contenttype'),
(15, 'Can delete content type', 5, 'delete_contenttype'),
(16, 'Can add session', 6, 'add_session'),
(17, 'Can change session', 6, 'change_session'),
(18, 'Can delete session', 6, 'delete_session'),
(19, 'Can add site', 7, 'add_site'),
(20, 'Can change site', 7, 'change_site'),
(21, 'Can delete site', 7, 'delete_site');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_user`
--

CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL,
  `password` varchar(128) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(254) NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `auth_user`
--

INSERT INTO `auth_user` (`id`, `password`, `last_login`, `is_superuser`, `username`, `first_name`, `last_name`, `email`, `is_staff`, `is_active`, `date_joined`) VALUES
(1, 'pbkdf2_sha256$36000$6cFw4EZmhCsa$eKSZxMZJy0kSoJY2Zks3FCiI/CVOaKYO+3tN1GQXBWA=', '2018-01-25 15:30:37', 1, 'damian', '', '', 'a@wp.pl', 1, 1, '2018-01-15 19:57:44'),
(2, 'pbkdf2_sha256$20000$5Ig7Px8IhL3g$KusfaBd3JpFhZcC1Nq4IxbDPMqijpfeVu2NylDi1sQ0=', NULL, 1, 'dam', '', '', 'a@wp.pl', 1, 1, '2018-01-15 20:32:18'),
(3, 'pbkdf2_sha256$36000$g9CoxGW6ol8G$05BUSYWEPLxqTv9LXWf7PIbSan+h/RWNvlAARXIRzqs=', '2018-01-16 00:41:23', 1, 'Damian2', '', '', 'a@wp.pl', 1, 1, '2018-01-15 23:20:43');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_user_groups`
--

CREATE TABLE `auth_user_groups` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_user_user_permissions`
--

CREATE TABLE `auth_user_user_permissions` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `django_admin_log`
--

CREATE TABLE `django_admin_log` (
  `id` int(11) NOT NULL,
  `action_time` datetime NOT NULL,
  `object_id` longtext,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint(5) UNSIGNED NOT NULL,
  `change_message` longtext NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `django_content_type`
--

CREATE TABLE `django_content_type` (
  `id` int(11) NOT NULL,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `django_content_type`
--

INSERT INTO `django_content_type` (`id`, `app_label`, `model`) VALUES
(1, 'admin', 'logentry'),
(2, 'auth', 'group'),
(3, 'auth', 'permission'),
(4, 'auth', 'user'),
(5, 'contenttypes', 'contenttype'),
(6, 'sessions', 'session'),
(7, 'sites', 'site');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `django_migrations`
--

CREATE TABLE `django_migrations` (
  `id` int(11) NOT NULL,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `django_migrations`
--

INSERT INTO `django_migrations` (`id`, `app`, `name`, `applied`) VALUES
(0, 'sites', '0001_initial', '2018-01-15 21:45:24'),
(1, 'contenttypes', '0001_initial', '2017-05-28 08:09:48'),
(2, 'auth', '0001_initial', '2017-05-28 08:09:52'),
(3, 'admin', '0001_initial', '2017-05-28 08:09:52'),
(4, 'admin', '0002_logentry_remove_auto_add', '2017-05-28 08:09:52'),
(5, 'contenttypes', '0002_remove_content_type_name', '2017-05-28 08:09:53'),
(6, 'auth', '0002_alter_permission_name_max_length', '2017-05-28 08:09:53'),
(7, 'auth', '0003_alter_user_email_max_length', '2017-05-28 08:09:53'),
(8, 'auth', '0004_alter_user_username_opts', '2017-05-28 08:09:53'),
(9, 'auth', '0005_alter_user_last_login_null', '2017-05-28 08:09:53'),
(10, 'auth', '0006_require_contenttypes_0002', '2017-05-28 08:09:53'),
(11, 'auth', '0007_alter_validators_add_error_messages', '2017-05-28 08:09:53'),
(12, 'auth', '0008_alter_user_username_max_length', '2017-05-28 08:09:54'),
(13, 'sessions', '0001_initial', '2017-05-28 08:09:55');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `django_session`
--

CREATE TABLE `django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `django_session`
--

INSERT INTO `django_session` (`session_key`, `session_data`, `expire_date`) VALUES
('oc9v0kkbyxx9qist4c6b66acbjctp99e', 'YjM3MzY1MjRiYjViNDMyY2ZhM2JjY2Q2OTc3MTY5MzI4ZTQwNjVkYzp7Il9hdXRoX3VzZXJfaGFzaCI6IjE0MTEzZDcwZjk1ZjRlOWJlMmQxZThhMmM1NzNmYmYyNTg3NTAwZTIiLCJfYXV0aF91c2VyX2JhY2tlbmQiOiJkamFuZ28uY29udHJpYi5hdXRoLmJhY2tlbmRzLk1vZGVsQmFja2VuZCIsIl9hdXRoX3VzZXJfaWQiOiIxIn0=', '2018-02-01 13:49:49');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `locks`
--

CREATE TABLE `locks` (
  `ID_LOCK` int(10) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  `LOCALIZATION` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
  `MAC_ADDRESS` varchar(17) NOT NULL,
  `People_inside` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `locks`
--

INSERT INTO `locks` (`ID_LOCK`, `NAME`, `LOCALIZATION`, `MAC_ADDRESS`, `People_inside`) VALUES
(1, 'Zamek testowy', 'Opis zamka', 'B8:27:EB:FC:73:A1', 0),
(2, 'Drugi zamek', 'Drugi zamek testowy', 'B8:27:EB:FC:73:A2', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `locks_keys`
--

CREATE TABLE `locks_keys` (
  `ID_KEY` int(10) NOT NULL,
  `ID_LOCK` int(10) NOT NULL,
  `ID_USER` int(10) NOT NULL,
  `LOCK_KEY` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `FROM_DATE` datetime NOT NULL,
  `TO_DATE` datetime NOT NULL,
  `ISACTUAL` datetime DEFAULT NULL,
  `MONDAY` varchar(20) COLLATE utf8_polish_ci DEFAULT NULL,
  `TUESDAY` varchar(20) COLLATE utf8_polish_ci DEFAULT NULL,
  `WEDNESDAY` varchar(20) COLLATE utf8_polish_ci DEFAULT NULL,
  `THURSDAY` varchar(20) COLLATE utf8_polish_ci DEFAULT NULL,
  `FRIDAY` varchar(20) COLLATE utf8_polish_ci DEFAULT NULL,
  `SATURDAY` varchar(20) COLLATE utf8_polish_ci DEFAULT NULL,
  `SUNDAY` varchar(20) COLLATE utf8_polish_ci DEFAULT NULL,
  `IS_PERNAMENT` tinyint(1) NOT NULL,
  `NAME` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `SURNAME` varchar(50) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `locks_keys`
--

INSERT INTO `locks_keys` (`ID_KEY`, `ID_LOCK`, `ID_USER`, `LOCK_KEY`, `FROM_DATE`, `TO_DATE`, `ISACTUAL`, `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY`, `IS_PERNAMENT`, `NAME`, `SURNAME`) VALUES
(1, 2, 137, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0r0MlBmKIr03UHTrx8CZAyEfraDtEPKAZJkm1RRa28JPcSCRgvp6Qt/uxekzvMs76W4S+QJrWoYazl4ykNWM8t7cZBdw4mKq0Y50D/ZBDOxX4v6HCHUFcz59yMmdxwQeVjJxxt1wLWIZMmxGy4i4HuASvu8T5AF8JR028ku6fUQIDAQAB', '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, '0-24;', '0-24;', '', '', '', '', '', 0, 'sadx', 'xcv'),
(2, 2, 145, 'ASDF', '2017-04-29 05:31:31', '2018-07-07 07:28:24', '2017-12-19 15:37:29', NULL, NULL, '8-10;14-23', '0-10', NULL, NULL, '2-23', 1, 'Maciej', 'MM'),
(5, 1, 145, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMltZm96JrgFHG8uXGTmhY5lflTwFY23yGOxli+Wf5oQ6tmKe8GsRhwhW9/pr9aTEqet/Lr8lqAZ+gpx170G6/jNgokH1dMImMo1zaTHgxjtkictYgQhwNGPQtxJ7PZfozJqFu7l6cAF5uwVWUff4gw0e5TDjuw/UcwP3Uo8K+3QIDAQAB', '2017-11-07 12:00:00', '2018-04-29 12:29:00', NULL, '', 'NULL', '0-12', '8-12', 'NULL', 'NULL', 'NULL', 1, 'Maciej', 'mm'),
(6, 2, 145, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDS4rwG0PNP65fOfEdiltVHvTDi3L5DtQNOemWxkgkmRdT/RbZWLQNAZLz0bOduWX83pnzssCjnzRm7pmB6iVwt/QeIBWYS8J60AyUOmrEdGtljcbA1aPX5Nz8yVOGxEjCzN1nfN7xohpGJEpXbxobOnMmR88pQV2XEi3iEPjGoKQIDAQAB', '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, '7-13;', '', '', '', '0-24;', '', '', 0, 'Damian', 'moj nowy cert'),
(7, 2, 137, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQQXy/jA0MzE1Slbom9whZzSQh8+RvAD/GchCHfxMMs35abxO6/Yk/mgGpMgzCzETICSLZXEeDE7oJr3KHAEnVJ6PrCojvj94Kj+isryD1U9i713cbBjS+Ddtb2Tpj0BmhDWtSvULREoNPi/mnCA3HyUuDIv6BnTHdMDiWU/OmEQIDAQAB', '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, '1-3;4-6;', '7-15;', '', '', '', '', '', 0, 'da', 'nanowow'),
(8, 2, 137, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9Y4Vb4lz6eZjjXPRS62zD7iN2hzVtbER5rd8rsF6JQpmRwUDvRnOfljbsW2DHxvUNzSh3tN/qQeVIoOAw7vEYR1jiLIFvj3+vrYEKs1/Vf3faN7ENeR36IMjSVMQgmfRZ4pK4CSTnZtlb7P8ZA+O5jh2cNoesEl+zhGL/Xv3QYQIDAQAB', '0000-00-00 00:00:00', '0000-00-00 00:00:00', NULL, '1-4;', '', '', '', '', '', '', 0, 'asddsfadsgfdsfg', 'dsfgdfg'),
(9, 2, 137, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIzZiAeNgUim3WtgWJTYpECspbZ2uHETGXQi3CIyR1lMqw3rLI3inCb9c6csHSe7lGlAsaLaMblB8/pCZfOrG0ltuBgozuQu1eP9tosKi2WCTBv+WrOSGdF0ase0LfzoMMASszKHtLLHgGVYmbCr4AlUEaWk53UvABMg5o1TI3kwIDAQAB', '2017-12-29 00:00:00', '2017-12-30 00:00:00', NULL, '2-4;', '5-6;7-8;', '', '', '', '', '', 0, 'dgsd', 'dfg'),
(10, 2, 145, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJFkLpvqtuxWZb5hQye9JHYwo6Z5IN3ebmhvpOvzjyh6+htvQIg1jCCfT/bgH9bOuwdKfTNnNHcSExsbbCdtAnFsPRkT49NVbJb6NJ2Iz3wBa33lFSsm5YstY0hyTdmBsz03LpgH6aRcXEroVWmC3EgeNgTpGA2nokxu+Iq3QcWwIDAQAB', '2018-01-01 00:00:00', '2018-01-31 00:00:00', '2018-01-06 19:16:15', '6:07-12:07', '', '', '', '', '', '', 0, 'zx', 'xczv'),
(11, 2, 137, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJC419YwnThjrVCDMeqSygHPf9/SBbQgLo8rx3yzsHuGBPNqKoJsUsJR3YbonQQUrNw0bTWinJoEAn5c9dcospa5zsMRBeA8KxeQYvn07L4IQvaX0mxvFpdQU5VXXXuGf3+OmKZhP5Afj616Laj+4gLKk/o7WVxVu17BDXFldNmQIDAQAB', '2018-01-01 00:00:00', '2018-01-16 00:00:00', NULL, '10:07-12:0', '', '', '', '', '', '', 0, 'asd', 'sdfa'),
(14, 2, 148, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhfu664DccYOI1SlvNV6emQTG2wMMDjXBZyCv041fYGaigwo9x7xaMUtmKhu5WFKxVrz8lrr2h9OHcJFsHb6jP+a79SCjBjEdvagTPRQsXKhafq/5OkWdTwSqmfLOdshummAncxChdxIUGfaVlt68gn0U8DCAfztbsBDt3JqXCAQIDAQAB', '2018-01-01 00:00:00', '2018-01-31 00:00:00', NULL, '10:02-13:00', '', '', '00:01-23:59', '', '', '', 1, '', '');

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
(1, 'potestujmy', '913BEEB024BAA2BE29C3D19B1F859B5508BDFCE177554446073478C4B86F6A9F', 'saddf', 'fsdasdf', NULL, 2, 0, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDH9LZSsOFVnkGeJd4lzwYxDDOXQlsJGWit2Spp\nwiHRG+cSv6BSbrTwVuMFErkyhpCzLtXJBG42N7/v0bUmk4NcfETheG1d7y6/4XKCQ7HiZZh226E5\n6/0azb9gPMFLq6MRCmDnyFAq3qXPMzjP+nCG7ayBpyD++f4n9OYe0VXBPwIDAQAB\n', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5IlbTGE42Iz7dniaCfP1fJOLrhnoz+mCiURSlcVnGqOzb1vXJADYrsoTgLC6JTOt91WxhRpZQYMYCdvaR6ApwzH+uwQI1jUityYrxarDmBNK1UU6CwFS/qTytm5u5MJWc5t2FPmS1/t9LeADd9EiFioIe9Nt9AGZaSw2gr5ln6QIDAQAB', '2018-01-07 12:11:08', 1, 'RSA', 'SHA-256'),
(134, 'Maciej1', '01D48A697442ED3231773C944973E87315FFD44232A2503D1B68B02F1D677FF5', 'Maciej', 'Marciniak', 'aaa', 2, 1, 'ddd', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyoCd559OOPfZAtzHqj31zcBqLglDekGGaGzJ8AQpVZWoWSs8GSm14r0LdO7qb0QYjExp0diGiyhSkgAhJ+jDJFyD2v4M62OebiCgl35y2a0EZeYaVWKFyNTax3IDKtW/ZvZP7H25ahP1Ea6BlpFFYNxEi52cJG7LP06JtnMBDMwIDAQAB', '2018-12-10 23:50:32', 1, 'RSA', 'SHA-256'),
(137, 'Damian', '913BEEB024BAA2BE29C3D19B1F859B5508BDFCE177554446073478C4B86F6A9F', 'D am ian', 'Xff', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1r6tsAtcwxJAQysUMCB0HCME/wfbj5vgs/FrLE/tcRNskXyI/KXqMjFv033PF1bZGqGc4+1mL31HdT5gU9RmeGjgNzca5g2pMNuri70foCoJnTotkKeD5dh6UHM4I/V4ezQlW5g4s+X9Z7+f/djIZJaAwL8+8AxdyJxkIfRf7AwIDAQAB', 0, 0, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9cVoaNxtzARE4uFW29KZPOUI0yHt8fZzESNht\n/UGQoiOizX+3mholAbRXFEVxg7DXs0qa6UD6bPE9hLqm5+3H8LhSWuY+Hbyg6mPFFn6o66gW3aY+\nXCV5fzlsnj7crK3ohuYedhJ+psST2FsIJImWPbJy1ytm1JteyuimqRJbMQIDAQAB\n', NULL, NULL, 1, 'RSA', 'SHA-256'),
(140, 'potestujmy4', '913BEEB024BAA2BE29C3D19B1F859B5508BDFCE177554446073478C4B86F6A9F', 'saddf', 'fsdasdf', NULL, 0, 0, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/tweAAg0+3Ipt/QT9/VUOdek3+vaqQXCzcCPO\nuw0yC/v2CWh3eHDH1mtWr+3w/bp2HW/1DJjj0DJfxFviF0qCpBYF+EcPNj+4BZvu7yQO1twQueh5\n2E0BIjkZ2zS1vNFx/IRYQGb+W8Av8hCVgDtHwSAnAOy4zXQrmaftP9jm3QIDAQAB\n', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTirrwd839ZBF/vys/gK9jfoRzQP9E9l8bKyeIoVDt421TJRSmdB6H8x7SzU8p6XqcYh6rVukn6YjE4Fu2tv1dsAhjnHXwwHfGyi0jJCks/8FfL6OVWLlloW3f8EqSAjc2cvpavuGpuJfL8U1ROcuBmmHaKq+4w230zqNgJt9duwIDAQAB', '2018-12-15 20:02:57', 1, 'RSA', 'SHA-256'),
(145, 'DamianTest10', '851EB1F79381A0A9771954693A6ACF988DD5D1FFC23C266A64071C661978BE93', 'Damian', 'Test', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDXomqGBabdtDY052WVQF5esCcntn6lcqvxlUs4wnGfhvKM6B1yX+xMCuaiHaTkIGibb2fVnD9Tonh6xnBKCux1YdoCzivcR8kDUE2udq4zdOyFE1r8aHPEiwErbCf1om70MipJR2AX+8Ek83O99SPwfw+qrDLxcMYURVZyiIY7DQIDAQAB', 0, 1, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6bIhggJ76s5na/Qfi8lj0l8mHz2k+m6fQ8KoD\nOVxSVgpa0cbSsR2NGtbQkcEAqTk7S8PSCmRhbpmCMmU6IzLhV1fiSjs4kCEUH0FcObREk+Qrlqtn\nOfWHKRuINoOOIOazZD80/RhVOYWp1ryM8LKaJNX+Fz1A2pDE6UygFs5c1QIDAQAB\n', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyl5+tiTKv2+Y+UY2XR0rBdlL9Z+QtAfOibhdeP2do0X5uLnaNhYhmSvaxr7q8uNNXrO3EV4UUCzJoM3vZGKCYHbidIFGo1z108gF9+7OEhpzUFJVzFLOzjxwEwXwBbN8ymIja8fZevlSpIh06nce9Qt3+XJfD3j7ETA+p6JFNrQIDAQAB', '2019-01-07 19:44:40', 1, 'RSA', 'SHA-256'),
(148, 'TestowyZwykly', '913BEEB024BAA2BE29C3D19B1F859B5508BDFCE177554446073478C4B86F6A9F', 'Testowy', 'Testowy', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChBReWAEB6IGasvVaS4KuQEAIQxHkJAWe24Qat8BODEgTJ+/wx0ck3S/5dLcUIlo+FF8hqtPmK2jzHfOTglbzijnH212O4F3YoxqnY8xAn6vXzzMNjJ2ZvzYPQbU366yYdM7bOYtlX/8rbOa0n3zyN9oYDtOXQfTqKjorqVztjVwIDAQAB', 0, 1, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDW0Ix06jGKlkPPtm6barlzUdXbPoJolcYJJkkD\n2Poy4AfkqDrObNwWFO4xEVmyNNAyw0MX9J6XWcDuZub46odWJrxlZd74MUcVvzhBBEBaLczzouqa\ns1OQV/HgyvmEgaWKbjSWHW6gAH0HdRdkNJUIbYoFNdwZ7BzpoQfzCIIBqwIDAQAB\n', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCljA9k/ZLOziZumhjIw1v0csb4U8MQD6TYKyfEHW5TlmTZ0GW7vlB+eX9z7yHprHjH+B/GNMwiQOPLNpK6sihQ+GpI1vfxMEsoj6jlq25NdmQ5vTwKYSRbc+NJX0WUFzzQ46LNbntio75vRyE5tyrfHLREfPw+4MDzYPqti1hZnQIDAQAB', '2019-01-25 14:29:38', 1, 'RSA', 'SHA-256');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wait_locks_keys`
--

CREATE TABLE `wait_locks_keys` (
  `ID_KEY` int(10) NOT NULL,
  `ID_LOCK` int(10) NOT NULL,
  `ID_USER` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `access_to_locks`
--
ALTER TABLE `access_to_locks`
  ADD PRIMARY KEY (`ID`,`ID_KEY`),
  ADD KEY `ID_KEY` (`ID_KEY`);

--
-- Indexes for table `auth_group`
--
ALTER TABLE `auth_group`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_group_permissions_group_id_permission_id_0cd325b0_uniq` (`group_id`,`permission_id`),
  ADD KEY `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` (`permission_id`);

--
-- Indexes for table `auth_permission`
--
ALTER TABLE `auth_permission`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`);

--
-- Indexes for table `auth_user`
--
ALTER TABLE `auth_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_user_groups_user_id_group_id_94350c0c_uniq` (`user_id`,`group_id`),
  ADD KEY `auth_user_groups_group_id_97559544_fk_auth_group_id` (`group_id`);

--
-- Indexes for table `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `auth_user_user_permissions_user_id_permission_id_14a6b632_uniq` (`user_id`,`permission_id`),
  ADD KEY `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` (`permission_id`);

--
-- Indexes for table `django_admin_log`
--
ALTER TABLE `django_admin_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `django_admin_log_content_type_id_c4bce8eb_fk_django_co` (`content_type_id`),
  ADD KEY `django_admin_log_user_id_c564eba6_fk_auth_user_id` (`user_id`);

--
-- Indexes for table `django_content_type`
--
ALTER TABLE `django_content_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `django_content_type_app_label_model_76bd3d3b_uniq` (`app_label`,`model`);

--
-- Indexes for table `django_migrations`
--
ALTER TABLE `django_migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `django_session`
--
ALTER TABLE `django_session`
  ADD PRIMARY KEY (`session_key`),
  ADD KEY `django_session_expire_date_a5c62663` (`expire_date`);

--
-- Indexes for table `locks`
--
ALTER TABLE `locks`
  ADD PRIMARY KEY (`ID_LOCK`),
  ADD UNIQUE KEY `NAME` (`NAME`);

--
-- Indexes for table `locks_keys`
--
ALTER TABLE `locks_keys`
  ADD PRIMARY KEY (`ID_KEY`),
  ADD UNIQUE KEY `KEY` (`LOCK_KEY`),
  ADD KEY `ID_LOCK` (`ID_LOCK`),
  ADD KEY `ID_LOCK_2` (`ID_LOCK`,`ID_USER`),
  ADD KEY `ID_USER` (`ID_USER`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID_USER`),
  ADD UNIQUE KEY `LOGIN` (`LOGIN`),
  ADD UNIQUE KEY `PUBLIC_KEY` (`PUBLIC_KEY`),
  ADD KEY `Serial_number` (`Serial_number`);

--
-- Indexes for table `wait_locks_keys`
--
ALTER TABLE `wait_locks_keys`
  ADD PRIMARY KEY (`ID_KEY`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `access_to_locks`
--
ALTER TABLE `access_to_locks`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=169;
--
-- AUTO_INCREMENT dla tabeli `auth_group`
--
ALTER TABLE `auth_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `auth_permission`
--
ALTER TABLE `auth_permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT dla tabeli `auth_user`
--
ALTER TABLE `auth_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT dla tabeli `django_content_type`
--
ALTER TABLE `django_content_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT dla tabeli `locks`
--
ALTER TABLE `locks`
  MODIFY `ID_LOCK` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT dla tabeli `locks_keys`
--
ALTER TABLE `locks_keys`
  MODIFY `ID_KEY` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `ID_USER` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=149;
--
-- AUTO_INCREMENT dla tabeli `wait_locks_keys`
--
ALTER TABLE `wait_locks_keys`
  MODIFY `ID_KEY` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `auth_permission`
--
ALTER TABLE `auth_permission`
  ADD CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`);

--
-- Ograniczenia dla tabeli `locks_keys`
--
ALTER TABLE `locks_keys`
  ADD CONSTRAINT `locks_keys_ibfk_1` FOREIGN KEY (`ID_LOCK`) REFERENCES `locks` (`ID_LOCK`),
  ADD CONSTRAINT `locks_keys_ibfk_2` FOREIGN KEY (`ID_USER`) REFERENCES `users` (`ID_USER`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
