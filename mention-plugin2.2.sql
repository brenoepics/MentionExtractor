-- ----------------------------
-- Table structure for mention_logs
-- ----------------------------
DROP TABLE IF EXISTS `mention_logs`;
CREATE TABLE `mention_logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_from_id` int NOT NULL,
  `user_to` varchar(255) NOT NULL,
  `message` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `timestamp` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE,
  KEY `user_from_id` (`user_from_id`) USING BTREE,
  KEY `user_to_id` (`user_to`) USING BTREE,
  KEY `message` (`message`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
