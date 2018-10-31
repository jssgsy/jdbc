DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;

INSERT INTO `test` (`id`, `name`, `age`)
VALUES
  (1,'univ',26),
  (2,'电动车',22),
  (1,'abc',23),
  (1,'abc',23),
  (200,'aaa',23),
  (200,'aaa',23),
  (1,'abc',23);

/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;