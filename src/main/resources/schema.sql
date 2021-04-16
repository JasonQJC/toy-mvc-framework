CREATE TABLE `coffee` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255),
    `createBy` varchar(255),
    `price` decimal(10,2),
    `remark` varchar(255),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
TRUNCATE coffee;
INSERT INTO coffee (name, createBy, price, remark) VALUES ('name1', 'createBy1', 9.9, 'remark1');
INSERT INTO coffee (name, createBy, price, remark) VALUES ('name2', 'createBy2', 19.9, 'remark2');