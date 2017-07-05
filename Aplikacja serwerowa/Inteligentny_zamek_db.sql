-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 05 Lip 2017, 17:01
-- Wersja serwera: 10.1.21-MariaDB
-- Wersja PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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
(124, 2, '2017-07-05 00:39:48', 1);

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
(18, 'Can delete session', 6, 'delete_session');

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
(6, 'sessions', 'session');

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

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `locks`
--

CREATE TABLE `locks` (
  `ID_LOCK` int(10) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  `LOCALIZATION` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
  `MAC_ADDRESS` varchar(17) NOT NULL,
  `ADMIN_KEY` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `locks`
--

INSERT INTO `locks` (`ID_LOCK`, `NAME`, `LOCALIZATION`, `MAC_ADDRESS`, `ADMIN_KEY`) VALUES
(1, 'Zamek testowy', 'Opis zamka', 'B8:27:EB:FC:73:A1', 'B8:27:EB:FC:73:A2'),
(2, 'Drugi zamek', 'Drugi zamek testowy', 'B8:27:EB:FC:73:A2', 'B8:27:EB:FC:73:A2');

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
-- Zrzut danych tabeli `locks_keys`
--

INSERT INTO `locks_keys` (`ID_KEY`, `ID_LOCK`, `ID_USER`, `LOCK_KEY`, `FROM_DATE`, `TO_DATE`, `ISACTUAL`, `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY`, `IS_PERNAMENT`, `NAME`, `SURNAME`) VALUES
(2, 2, 134, 'ASDF', '2017-04-29 05:31:31', '2017-07-07 07:28:24', NULL, NULL, NULL, '8-10;14-23', '0-10', NULL, NULL, '2-23', 1, 'Maciej', 'MM'),
(3, 1, 130, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjuGUXgcoOU4trV3auJTP459nDE2w4OgxRlS5gw7KjY40qGz7bGKskBBQ+hiamcva5Ao9Ts4YldDG8TTtu3si/TcVWJSgXMfVXWeXWgbTUeRNoDDhoLGZVr81Ldcw0naMaGtiU9OIvnjlt4oK3H+hQgdQRZHyHhH0jVB/yBKC6owIDAQAB', '2017-04-30 12:00:00', '2018-04-30 12:00:00', '2017-06-01 00:40:52', 'NULL', 'NULL', 'NULL', '8-12', 'NULL', 'NULL', 'NULL', 0, 'Maciej', 'mm'),
(5, 1, 134, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMltZm96JrgFHG8uXGTmhY5lflTwFY23yGOxli+Wf5oQ6tmKe8GsRhwhW9/pr9aTEqet/Lr8lqAZ+gpx170G6/jNgokH1dMImMo1zaTHgxjtkictYgQhwNGPQtxJ7PZfozJqFu7l6cAF5uwVWUff4gw0e5TDjuw/UcwP3Uo8K+3QIDAQAB', '2017-04-30 12:00:00', '2018-04-30 12:00:00', '2017-07-05 00:41:18', 'NULL', 'NULL', 'NULL', '8-12', 'NULL', 'NULL', 'NULL', 0, 'Maciej', 'mm'),
(7, 1, 131, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKLRztJOA5CHqVwxezoDWrTHztC+6TTkpSVI7dXX4ebp+Rz6AOAR3ijlEuUnVH5pf+mAGcZQx+SRZnJgOOuzzgJFfUses3PzfIFa/cNygkXoQSBq8DqlwMktc+slBGOZ8x4sLvFLoi5UCFUUTI799BcYZobXIbqBow0YwBNZ23EwIDAQAB', '2017-06-01 00:00:00', '2017-07-11 00:00:00', NULL, '', '', '', '5-13;', '', '', '', 0, 'Damian', 'F');

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
  `IS_ADMIN` tinyint(1) NOT NULL,
  `PUBLIC_KEY` varchar(255) DEFAULT NULL,
  `TOKEN` varchar(300) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `ISACTIVATED` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`ID_USER`, `LOGIN`, `PASSWORD`, `NAME`, `SURNAME`, `IS_ADMIN`, `PUBLIC_KEY`, `TOKEN`, `ISACTIVATED`) VALUES
(127, 'Mapet', 'E3763FC44D96B0FB4812C1843D6AE626CCFD674B92175B809048AD5E2F86F04F', 'Maciej', 'Marciniak', 0, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSMm5JpnNRb34q0ttCis+fjH1EBr+qD30CiqfF\ndFchlEcmoMK/E3FhdI90mOeEoEhkTWHVFNXOPFWL8cdFAjg23wMmaVtIRqh/FOij2HDJN06oMNwj\nkAvN02V31zHe6HIkfyrnUAXQa7exKzylGTnNj8gh3/+5Vb/w1q+SvJ+lowIDAQ', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZP3VR/Bt+w1NYE1zHXq3UMyD8wSasRhTJvLo99NzHHZcrfHeJjTUbF/YckFfg7AY8M9PwW+NDUCPs/eq23gYasHzoyes0FxYSU1eYi4XeyzkbheQSUlAAgU6wBf8Nw8BVW8lPkkQYp5tQszSqokE/+KO1rCbLVR59G2zxlg6Y2QIDAQAB', 0),
(128, 'Mapet1', 'E3763FC44D96B0FB4812C1843D6AE626CCFD674B92175B809048AD5E2F86F04F', 'Mo', 'Mm', 0, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5MyrnJoshfrcrxIFiyFcAthu7/WRdXxL9niUq\nCeEworOW27bJ7uW1DBBFWMTwwv1xKATa681cpwuObvRf2rZHLZmMS5gLWFNml/7T6RaVh9OCH52P\n1gABiAXFa5RLAM1HTGcMtlUwggJaHZIikvn6nbfuSIZUmBsL+aqruSjR0wIDAQAB\n', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+XxCdhQeQdFMtmgNHJ6ncG04fbRhivXiZItC3rbPId3FhDhzaIFbCec718+DImUGbvVuZSoCh/a7s8S+6u6mXVuKvWUnpmHVeVrMY+nxT/nzZ976LUhmy8imPb/Qoj3Upofkuit6SJqc7KVee5JXd//OcZ2vopPaq5eMzb6kOuwIDAQAB', 0),
(129, 'Mapet2', '8FA1DDDD53606CEB933C5C6A12E714ED41E11D37A2B7BC48E91D15B54171D033', 'Mm', 'Mm', 1, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4Rlu0pDh1WjlFqQIaUZ3vEwCfYFZbglDAdqIR\nSm9D9Q7MuPA7OLMezyhTUE1t2MCxDnMD6FJ2YkVHbQwX55o55gUwBymkYoV3e+ZrrM7vAwKKvuwT\nmx6aK1NnYrkZ2rqLisaIgIGlnJrg12r36Hzuvryg/PgNsPpUEZQ3w4f6LQIDAQAB\n', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxoHoiSSPOG9UoW/lQE0YGpKK+Xrau+VZtxESOlQu2mfgg6X+2Jss44cbaYA5tzIq/Lo8KIYFkPnamNOnmspGv0stpmQJ+2fUHocQ0yRMnDTGNf1Vbb1XbvoG6JRkQyWUkTb0yyTJxbJCVXwUs3eDKd7n1/LVD5jIBE1l+aOqv9wIDAQAB', 0),
(130, 'Mm', '4654D793972C3B6A1D48FB0AB58D9CB0DE46C3D33D605F9222C283DFAA12D420', 'Maciej', 'Marc', 0, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtzWtDZb17J9JWR2Ij1dUUkQswUM5/a46SsLHE\nNFKLIdePtcbN4iJCwBwY3DJDlzvC5IiU4klcA91Feah90cJVQlhjV7Qgdint1iobn0+dEOozXKaM\naOCUUenTmil1IqeG4SFxBHGMIgWOKJ90UJkLj/XohnuF0m9/gDAhVHmjzwIDAQAB\n', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7z3EB4UgbzJm1JUCN53VELYOaQty0+3AHTBXpvGi+XGqSkEPpe06VzCCdnB4YPd83lfeSq11k3jlG5AnnyjPsheOp08ZbUvUk8BPKuEBPfhVzpvEiHu2dNzQrBKkUYUeZbp75vGp+bL8LTxtRLST9v0oMHLhZLUz9e961LMbk5wIDAQAB', 0),
(131, 'Aaa', '8FA1DDDD53606CEB933C5C6A12E714ED41E11D37A2B7BC48E91D15B54171D033', 'Mm', 'F', 0, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzuO9a9xbSrWYVs8hpD1hKACBrVhZxyET2V2rG\nBEXs3E4r/D3ScJ5L8JcUdAJgA0b+5/OIMThBJfHcRrInBXj5Io/HGkizF8AXXMcCIKFarMhtOpwk\nLw3cRD3H4EBWrh8yjnyWSb1zYzJll9SAg09LArA3f2bh4QWiB9K2mLR18wIDAQAB\n', '', 0),
(132, 'GUEST', 'passwd', 'GUEST', 'GUEST', 0, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhWA2LKnznq6P9jbzTX+HzNZmtnGoqEJH5tKgfFrVjNZDsyxDZuwHnnbcPlPqd10a9R8CKAWPYZhVKxsh5pkWV/spqoPnCftX6oirbOTbRiDbGjjC2P/yo3D+DPgxOo8G7CVSGFPChzhYeM75E19oEYFVBQVovsHJuezVgV0Xz3QIDAQAB', NULL, 0),
(134, 'Maciej1', '01D48A697442ED3231773C944973E87315FFD44232A2503D1B68B02F1D677FF5', 'Maciej', 'Marciniak', 1, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7S6eMyvEGsKWJSV9nxp2EY58oswYz3y851cfg\n1b5ukFslrnCBNTRHVwd42ZyBwRfs7Qo1AbWCUfMEgCuIORdLgvwNVKehAcgIiN38NZo5L6jFXR2U\ngV03FijFCA5O5pqPlLIwEPDfVaAFLjMy9wTpzzLPgbwfNv2f3OF3Uov8hQIDAQAB\n', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCO8VDZMy/FsdOaF64+SWQASCFb3xi/7VK9ZNwrS/GKdLlUkTiCM/NzVmc2/Itt4HRIyG42imBaQF4scHxUIKsGqzlYcuQ45pZFze4v6TMsO8zV7nGG7GNMu2nc/l8hv/2KJNLZt5+43AgdBu/8WIuAApJQ6SdvNSXy8IGSCJY0UQIDAQAB', 0);

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
-- Zrzut danych tabeli `wait_locks_keys`
--

INSERT INTO `wait_locks_keys` (`ID_KEY`, `ID_LOCK`, `ID_USER`) VALUES
(3, 2, 124),
(4, 2, 124);

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
  ADD UNIQUE KEY `PUBLIC_KEY` (`PUBLIC_KEY`);

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
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT dla tabeli `auth_user`
--
ALTER TABLE `auth_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `django_admin_log`
--
ALTER TABLE `django_admin_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `django_content_type`
--
ALTER TABLE `django_content_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT dla tabeli `django_migrations`
--
ALTER TABLE `django_migrations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT dla tabeli `locks`
--
ALTER TABLE `locks`
  MODIFY `ID_LOCK` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT dla tabeli `locks_keys`
--
ALTER TABLE `locks_keys`
  MODIFY `ID_KEY` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `ID_USER` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=135;
--
-- AUTO_INCREMENT dla tabeli `wait_locks_keys`
--
ALTER TABLE `wait_locks_keys`
  MODIFY `ID_KEY` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `access_to_locks`
--
ALTER TABLE `access_to_locks`
  ADD CONSTRAINT `ACCESS_TO_LOCKS_ibfk_1` FOREIGN KEY (`ID_KEY`) REFERENCES `locks_keys` (`ID_KEY`);

--
-- Ograniczenia dla tabeli `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
  ADD CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  ADD CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`);

--
-- Ograniczenia dla tabeli `auth_permission`
--
ALTER TABLE `auth_permission`
  ADD CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`);

--
-- Ograniczenia dla tabeli `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
  ADD CONSTRAINT `auth_user_groups_group_id_97559544_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`),
  ADD CONSTRAINT `auth_user_groups_user_id_6a12ed8b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Ograniczenia dla tabeli `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
  ADD CONSTRAINT `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`),
  ADD CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Ograniczenia dla tabeli `django_admin_log`
--
ALTER TABLE `django_admin_log`
  ADD CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
  ADD CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Ograniczenia dla tabeli `locks_keys`
--
ALTER TABLE `locks_keys`
  ADD CONSTRAINT `LOCKS_KEYS_ibfk_1` FOREIGN KEY (`ID_LOCK`) REFERENCES `locks` (`ID_LOCK`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `LOCKS_KEYS_ibfk_2` FOREIGN KEY (`ID_USER`) REFERENCES `users` (`ID_USER`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
