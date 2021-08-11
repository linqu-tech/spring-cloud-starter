CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint NOT NULL,
    `username` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `nickname` varchar(45) DEFAULT NULL,
    `gender` tinyint DEFAULT NULL,
    `avatar` varchar(256) DEFAULT NULL,
    `status` tinyint NOT NULL DEFAULT '0',
    `created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    CONSTRAINT `USER_PK` PRIMARY KEY (`id`),
    CONSTRAINT `USER_USERNAME_UK` UNIQUE KEY (`username`)
);
