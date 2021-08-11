CREATE TABLE IF NOT EXISTS `account` (
    `id` bigint NOT NULL,
    `name` varchar(255) NOT NULL,
    `last_seen` timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP(3),
    `note` varchar(255) DEFAULT NULL,
    CONSTRAINT `ACCOUNT_PK` PRIMARY KEY (`id`)
);
CREATE INDEX `ACCOUNT_NAME_K` ON `account` (`name`);
