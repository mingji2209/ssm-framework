CREATE TABLE `ssm`.`user`  (
  `id` varchar(32) NOT NULL,
  `name` varchar(20) NOT NULL,
  `account` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_name_index`(`name`) USING BTREE,
  KEY `user_account_index`(`account`) USING BTREE
)ENGINE=Innodb DEFAULT CHARSET = utf8;



CREATE TABLE `ssm`.`mood`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `content` varchar(255)  DEFAULT NULL,
  `user_id` bigint(32)  DEFAULT NULL,
  `publish_time` datetime(0)  DEFAULT NULL,
  `praise_num` int(11)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mood_user_id_index`(`user_id`) USING BTREE
)ENGINE=Innodb DEFAULT CHARSET = utf8;



CREATE TABLE `ssm`.`user_mood_praise_rel`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32)  DEFAULT NULL,
  `mood_id` bigint(32)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_mood_praise_rel_user_id_index`(`user_id`) USING BTREE,
	KEY `user_mood_praise_rel_mood_id_index`(`mood_id`) USING BTREE
)ENGINE=Innodb  DEFAULT CHARSET = utf8;
