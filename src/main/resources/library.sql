-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Gegenereerd op: 28 apr 2023 om 11:37
-- Serverversie: 10.4.27-MariaDB
-- PHP-versie: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `_archives_book_lending`
--

CREATE TABLE `_archives_book_lending` (
  `id` bigint(20) NOT NULL,
  `email` varchar(64) NOT NULL,
  `ISBN13` varchar(13) NOT NULL,
  `date_of_hand_out` varchar(10) NOT NULL,
  `date_of_hand_in` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `_book`
--

CREATE TABLE `_book` (
  `ISBN13` varchar(13) NOT NULL,
  `title` varchar(64) NOT NULL,
  `authors` varchar(64) DEFAULT NULL,
  `publisher` varchar(32) DEFAULT NULL,
  `language` varchar(2) DEFAULT NULL,
  `cover_path` varchar(64) DEFAULT NULL,
  `publication_date` varchar(10) NOT NULL,
  `pages` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `_book`
--

INSERT INTO `_book` (`ISBN13`, `title`, `authors`, `publisher`, `language`, `cover_path`, `publication_date`, `pages`) VALUES
('9781665935616', 'Mysteries of Thorn Manor', 'Margaret Rogerson', NULL, 'EN', NULL, '17-01-2023', 192),
('9782362315053', 'Sorcery of Thorns (édition Grimoire): Edition Grimoire', 'Margaret Rogerson\r\n', 'Bigbang', 'FR', 'src/main/resources/images/SorceryOfThornsFR.jpg', '05-10-2022', 576);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `_book_lending`
--

CREATE TABLE `_book_lending` (
  `email` varchar(64) NOT NULL,
  `ISBN13` varchar(13) NOT NULL,
  `return_before` varchar(10) NOT NULL,
  `date_of_hand_out` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `_damage`
--

CREATE TABLE `_damage` (
  `id` bigint(20) NOT NULL,
  `archived_book_lending_id` bigint(20) NOT NULL,
  `type_of_damage` varchar(128) NOT NULL,
  `cost_of_damage` int(11) NOT NULL,
  `paid` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `_reservation_book`
--

CREATE TABLE `_reservation_book` (
  `id` bigint(20) NOT NULL,
  `email` varchar(64) NOT NULL,
  `ISBN13` varchar(13) NOT NULL,
  `date_for_reservation` varchar(10) NOT NULL,
  `weeks_reserved` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `_user`
--

CREATE TABLE `_user` (
  `email` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `user_role` varchar(16) NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `_user`
--

INSERT INTO `_user` (`email`, `name`, `password`, `user_role`) VALUES
('admin@email.com', 'admin', '$2a$10$4Efrrblm4bB0x49fAqcWpO8d5jlqQGEnmtB7rVszLPqlwaAxhsGmO', 'ADMIN'),
('employee@email.com', 'employee', '$2a$10$azawDxq.bY8jq4pVWv45au0fXo.1y82jpUTHfHpYbZXF9zM1jJt0e', 'EMPLOYEE'),
('test@email.com', 'testname', '$2a$10$gjpABzQId1/2.t7EKRECneEdP1CcY9rnTHeIptn6AGYRiPwy0ZeyK', 'USER');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `_archives_book_lending`
--
ALTER TABLE `_archives_book_lending`
  ADD PRIMARY KEY (`id`),
  ADD KEY `email` (`email`),
  ADD KEY `ISBN13` (`ISBN13`);

--
-- Indexen voor tabel `_book`
--
ALTER TABLE `_book`
  ADD PRIMARY KEY (`ISBN13`),
  ADD UNIQUE KEY `cover_path` (`cover_path`);

--
-- Indexen voor tabel `_book_lending`
--
ALTER TABLE `_book_lending`
  ADD PRIMARY KEY (`ISBN13`),
  ADD KEY `email` (`email`);

--
-- Indexen voor tabel `_damage`
--
ALTER TABLE `_damage`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `_reservation_book`
--
ALTER TABLE `_reservation_book`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `_user`
--
ALTER TABLE `_user`
  ADD PRIMARY KEY (`email`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `_archives_book_lending`
--
ALTER TABLE `_archives_book_lending`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT voor een tabel `_damage`
--
ALTER TABLE `_damage`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT voor een tabel `_reservation_book`
--
ALTER TABLE `_reservation_book`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `_archives_book_lending`
--
ALTER TABLE `_archives_book_lending`
  ADD CONSTRAINT `_archives_book_lending_ibfk_1` FOREIGN KEY (`email`) REFERENCES `_user` (`email`),
  ADD CONSTRAINT `_archives_book_lending_ibfk_2` FOREIGN KEY (`ISBN13`) REFERENCES `_book` (`ISBN13`);

--
-- Beperkingen voor tabel `_book_lending`
--
ALTER TABLE `_book_lending`
  ADD CONSTRAINT `_book_lending_ibfk_2` FOREIGN KEY (`email`) REFERENCES `_user` (`email`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `_book_lending_ibfk_3` FOREIGN KEY (`ISBN13`) REFERENCES `_book` (`ISBN13`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
