-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 28 Sty 2018, 12:57
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
(1, 'administrator', '8160320e01e29fb48889045d3024dc37b4d338e76360142c978caddcf40c1dd8', 'Administrator', 'Administrator', NULL, 0, 1, '', '', NULL, 1, 'RSA', 'SHA-256');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT dla tabeli `auth_user`
--
ALTER TABLE `auth_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
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
  MODIFY `ID_KEY` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `ID_USER` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=151;
--
-- AUTO_INCREMENT dla tabeli `wait_locks_keys`
--
ALTER TABLE `wait_locks_keys`
  MODIFY `ID_KEY` int(10) NOT NULL AUTO_INCREMENT;
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
