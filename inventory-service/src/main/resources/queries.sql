 CREATE TABLE `inventory` (
   `inventory_id` bigint(20) NOT NULL AUTO_INCREMENT,
   `inventory_name` varchar(255) DEFAULT NULL,
   `quantity` int(11) DEFAULT NULL,
   `create_time` datetime NOT NULL,
   `update_time` datetime DEFAULT NULL,
   PRIMARY KEY (`inventory_id`)
 ) ;