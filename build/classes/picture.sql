CREATE DATABASE `af_gallery`;

USE `af_gallery`;

/*Table structure for table `picture` */

DROP TABLE IF EXISTS `picture`;

CREATE TABLE `picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `realName` varchar(200) DEFAULT NULL COMMENT '原始文件名',
  `name` varchar(200) DEFAULT NULL COMMENT '修改后的文件名',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `storePath` varchar(200) DEFAULT NULL COMMENT '存储路径',
  `tag` varchar(100) DEFAULT NULL COMMENT '标签,以逗号分开',
  `timeCreated` datetime DEFAULT NULL,
  `timeModified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

