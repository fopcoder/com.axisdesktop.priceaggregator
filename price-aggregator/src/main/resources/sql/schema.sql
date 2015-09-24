/*!40101 SET NAMES utf8 */;


CREATE TABLE `catalog_category_status` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(100)  NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) /*!40101 ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci */;


CREATE TABLE `catalog_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200)  NOT NULL,
  `idx_left` int(11) NOT NULL,
  `idx_right` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `path` varchar(100)  NOT NULL,
  `uri` varchar(500)  NOT NULL,
  `meta_title` varchar(200)  NOT NULL,
  `meta_keywords` varchar(200)  NOT NULL,
  `meta_description` text  NOT NULL,
  `description` text  NOT NULL,
  `status_id` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `idx_left` UNIQUE (`idx_left`),
  CONSTRAINT `idx_right` UNIQUE (`idx_right`),
  CONSTRAINT `status_id` INDEX (`status_id`),
  CONSTRAINT `catalog_category_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `catalog_category_status` (`id`)
) /*!40101 ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci */;


CREATE TABLE `catalog_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `path` varchar(200) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) /*!40101 ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci */;


CREATE TABLE `catalog_category_item` (
  `category_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY (`category_id`,`item_id`),
  CONSTRAINT `item_id` INDEX (`item_id`),
  CONSTRAINT `catalog_category_item_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `catalog_category` (`id`),
  CONSTRAINT `catalog_category_item_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `catalog_item` (`id`)
) /*!40101  ENGINE=InnoDB  */;



