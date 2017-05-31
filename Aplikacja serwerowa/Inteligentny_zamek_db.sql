-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Czas generowania: 31 Maj 2017, 15:02
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
-- Struktura tabeli dla tabeli `auth_group`
--

CREATE TABLE IF NOT EXISTS `auth_group` (
`id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_group_permissions`
--

CREATE TABLE IF NOT EXISTS `auth_group_permissions` (
`id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_permission`
--

CREATE TABLE IF NOT EXISTS `auth_permission` (
`id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

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

CREATE TABLE IF NOT EXISTS `auth_user` (
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

CREATE TABLE IF NOT EXISTS `auth_user_groups` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `auth_user_user_permissions`
--

CREATE TABLE IF NOT EXISTS `auth_user_user_permissions` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `django_admin_log`
--

CREATE TABLE IF NOT EXISTS `django_admin_log` (
`id` int(11) NOT NULL,
  `action_time` datetime NOT NULL,
  `object_id` longtext,
  `object_repr` varchar(200) NOT NULL,
  `action_flag` smallint(5) unsigned NOT NULL,
  `change_message` longtext NOT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `django_content_type`
--

CREATE TABLE IF NOT EXISTS `django_content_type` (
`id` int(11) NOT NULL,
  `app_label` varchar(100) NOT NULL,
  `model` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

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

CREATE TABLE IF NOT EXISTS `django_migrations` (
`id` int(11) NOT NULL,
  `app` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applied` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

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

CREATE TABLE IF NOT EXISTS `django_session` (
  `session_key` varchar(40) NOT NULL,
  `session_data` longtext NOT NULL,
  `expire_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `LOCKS`
--

CREATE TABLE IF NOT EXISTS `LOCKS` (
`ID_LOCK` int(10) NOT NULL,
  `NAME` varchar(30) NOT NULL,
  `LOCALIZATION` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
  `MAC_ADDRESS` varchar(17) NOT NULL,
  `ADMIN_KEY` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `LOCKS`
--

INSERT INTO `LOCKS` (`ID_LOCK`, `NAME`, `LOCALIZATION`, `MAC_ADDRESS`, `ADMIN_KEY`) VALUES
(1, 'Zamek testowy', 'Opis zamka', 'B8:27:EB:FC:73:A1', 'B8:27:EB:FC:73:A2'),
(2, 'Drugi zamek', 'Drugi zamek testowy', 'b8:27:eb:fc:73:a2', 'B8:27:EB:FC:73:A2');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `LOCKS_KEYS`
--

CREATE TABLE IF NOT EXISTS `LOCKS_KEYS` (
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `LOCKS_KEYS`
--

INSERT INTO `LOCKS_KEYS` (`ID_KEY`, `ID_LOCK`, `ID_USER`, `LOCK_KEY`, `FROM_DATE`, `TO_DATE`, `ISACTUAL`, `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY`, `IS_PERNAMENT`, `NAME`, `SURNAME`) VALUES
(1, 1, 124, 'asdf2', '2017-04-18 00:00:00', '2018-04-17 00:00:00', NULL, '8-12;14-16', NULL, NULL, NULL, NULL, NULL, NULL, 0, 'Maciej', 'Marciniak'),
(2, 2, 124, 'ASDF', '2017-04-18 00:00:00', '2017-04-19 00:00:00', '2017-05-29 20:55:09', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'Maciej', 'MM');

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
  `IS_ADMIN` tinyint(1) NOT NULL,
  `PUBLIC_KEY` varchar(32) DEFAULT NULL,
  `TOKEN` varchar(300) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
  `ISACTIVATED` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `USERS`
--

INSERT INTO `USERS` (`ID_USER`, `LOGIN`, `PASSWORD`, `NAME`, `SURNAME`, `IS_ADMIN`, `PUBLIC_KEY`, `TOKEN`, `ISACTIVATED`) VALUES
(123, 'mapet', 'c411bba96157e3dfeecbc80e11ef3c18465aec26b7348cfcb13296d0ba523068', 'Maciej', 'Marciniak', 1, NULL, NULL, 0),
(124, 'Maciej', 'aaa', 'Maciej', 'MM', 0, NULL, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrZS6gvAgDsdjnuYUbt/iA6GxM5GsML4p77OFD769IwzXmRepC46XgNk6+I6FCFYeRwBCxUtv1Pj8jdqGXTEKupzsLR3nd6BLjtgJ0zX4+KHbqk3MtLnrNFS/+ktAxu1sKa2edbuJ7pXqcEKpi/oCf1zJyb4EpQjUXh/ZAz8XYgwIDAQAB', 0),
(126, 'Maciej123', 'E3763FC44D96B0FB4812C1843D6AE626CCFD674B92175B809048AD5E2F86F04F', 'Mm', 'Mm', 0, NULL, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdiR8evz6q0TpZrHY8Cl1mO2dHWnK9Ma8wGQIPkoXV899cQ/rZamTNaS79PjOhxYpHPy/wdd2im5B1e83ds2u/UQJuCsuhqK6ybxSEbpa6/snGW8QL1kdMp+/76CYuoO/a46+QcLDSRQQSiHWhhgGz65yz6MbUio1KuTCfn+tEewIDAQAB', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `WAIT_LOCKS_KEYS`
--

CREATE TABLE IF NOT EXISTS `WAIT_LOCKS_KEYS` (
`ID_KEY` int(10) NOT NULL,
  `ID_LOCK` int(10) NOT NULL,
  `ID_USER` int(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `WAIT_LOCKS_KEYS`
--

INSERT INTO `WAIT_LOCKS_KEYS` (`ID_KEY`, `ID_LOCK`, `ID_USER`) VALUES
(3, 2, 124),
(4, 2, 124);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `ACCESS_TO_LOCKS`
--
ALTER TABLE `ACCESS_TO_LOCKS`
 ADD PRIMARY KEY (`ID`,`ID_KEY`), ADD KEY `ID_KEY` (`ID_KEY`);

--
-- Indexes for table `auth_group`
--
ALTER TABLE `auth_group`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `auth_group_permissions_group_id_permission_id_0cd325b0_uniq` (`group_id`,`permission_id`), ADD KEY `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` (`permission_id`);

--
-- Indexes for table `auth_permission`
--
ALTER TABLE `auth_permission`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `auth_permission_content_type_id_codename_01ab375a_uniq` (`content_type_id`,`codename`);

--
-- Indexes for table `auth_user`
--
ALTER TABLE `auth_user`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `auth_user_groups`
--
ALTER TABLE `auth_user_groups`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `auth_user_groups_user_id_group_id_94350c0c_uniq` (`user_id`,`group_id`), ADD KEY `auth_user_groups_group_id_97559544_fk_auth_group_id` (`group_id`);

--
-- Indexes for table `auth_user_user_permissions`
--
ALTER TABLE `auth_user_user_permissions`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `auth_user_user_permissions_user_id_permission_id_14a6b632_uniq` (`user_id`,`permission_id`), ADD KEY `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` (`permission_id`);

--
-- Indexes for table `django_admin_log`
--
ALTER TABLE `django_admin_log`
 ADD PRIMARY KEY (`id`), ADD KEY `django_admin_log_content_type_id_c4bce8eb_fk_django_co` (`content_type_id`), ADD KEY `django_admin_log_user_id_c564eba6_fk_auth_user_id` (`user_id`);

--
-- Indexes for table `django_content_type`
--
ALTER TABLE `django_content_type`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `django_content_type_app_label_model_76bd3d3b_uniq` (`app_label`,`model`);

--
-- Indexes for table `django_migrations`
--
ALTER TABLE `django_migrations`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `django_session`
--
ALTER TABLE `django_session`
 ADD PRIMARY KEY (`session_key`), ADD KEY `django_session_expire_date_a5c62663` (`expire_date`);

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
 ADD PRIMARY KEY (`ID_USER`), ADD UNIQUE KEY `LOGIN` (`LOGIN`), ADD UNIQUE KEY `PUBLIC_KEY` (`PUBLIC_KEY`);

--
-- Indexes for table `WAIT_LOCKS_KEYS`
--
ALTER TABLE `WAIT_LOCKS_KEYS`
 ADD PRIMARY KEY (`ID_KEY`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `ACCESS_TO_LOCKS`
--
ALTER TABLE `ACCESS_TO_LOCKS`
MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT;
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
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
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
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT dla tabeli `django_migrations`
--
ALTER TABLE `django_migrations`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT dla tabeli `LOCKS`
--
ALTER TABLE `LOCKS`
MODIFY `ID_LOCK` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT dla tabeli `LOCKS_KEYS`
--
ALTER TABLE `LOCKS_KEYS`
MODIFY `ID_KEY` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT dla tabeli `USERS`
--
ALTER TABLE `USERS`
MODIFY `ID_USER` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=127;
--
-- AUTO_INCREMENT dla tabeli `WAIT_LOCKS_KEYS`
--
ALTER TABLE `WAIT_LOCKS_KEYS`
MODIFY `ID_KEY` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `ACCESS_TO_LOCKS`
--
ALTER TABLE `ACCESS_TO_LOCKS`
ADD CONSTRAINT `ACCESS_TO_LOCKS_ibfk_1` FOREIGN KEY (`ID_KEY`) REFERENCES `LOCKS_KEYS` (`ID_KEY`);

--
-- Ograniczenia dla tabeli `auth_group_permissions`
--
ALTER TABLE `auth_group_permissions`
ADD CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`),
ADD CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`);

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
ADD CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`),
ADD CONSTRAINT `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`);

--
-- Ograniczenia dla tabeli `django_admin_log`
--
ALTER TABLE `django_admin_log`
ADD CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`),
ADD CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`);

--
-- Ograniczenia dla tabeli `LOCKS_KEYS`
--
ALTER TABLE `LOCKS_KEYS`
ADD CONSTRAINT `LOCKS_KEYS_ibfk_1` FOREIGN KEY (`ID_LOCK`) REFERENCES `LOCKS` (`ID_LOCK`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `LOCKS_KEYS_ibfk_2` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
