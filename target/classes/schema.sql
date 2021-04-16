CREATE TABLE `coffee` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255),
    `createBy` varchar(255),
    `price` decimal(10,2),
    `remark` varchar(255),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8