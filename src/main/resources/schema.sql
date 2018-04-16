DROP TABLE IF EXISTS `T_VERIFICATION_TOKEN`;
DROP TABLE IF EXISTS `T_COMMENT`;
DROP TABLE IF EXISTS `T_POST`;
DROP TABLE IF EXISTS `T_USER`;
DROP TABLE IF EXISTS `T_CATEGORY`;

CREATE TABLE `T_USER` (
  `id`                     BIGINT       NOT NULL AUTO_INCREMENT,
  `username`               VARCHAR(255) NOT NULL,
  `password`               VARCHAR(255) NOT NULL,
  `email`                  VARCHAR(255) NOT NULL,
  `activated`              TINYINT(1)   NOT NULL DEFAULT 1,
  `date_created`           DATETIME     NOT NULL,
  `avatar_location`        VARCHAR(255) DEFAULT 'avatar/default_avatar.png',
  `bio`                    VARCHAR(255),
  `roles`                  VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `T_CATEGORY` (
  `id`                 BIGINT       NOT NULL AUTO_INCREMENT,
  `weight`             INT          NOT NULL,
  `name`               VARCHAR(255) NOT NULL,
  `display_name`       VARCHAR(255) NOT NULL,
  `username`           VARCHAR(255),
  `date_created`       DATETIME     NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `T_POST` (
  `id`                 BIGINT       NOT NULL AUTO_INCREMENT,
  `title`              VARCHAR(255) NOT NULL,
  `body`               TEXT,
  `date_created`       DATETIME     NOT NULL,
  `user_id`            BIGINT,
  `category_id`        BIGINT,
  `comment_count`      INT DEFAULT 0,
  `hit_count`          BIGINT,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`)  REFERENCES T_CATEGORY(`id`),
  FOREIGN KEY (`user_id`)      REFERENCES T_USER(`id`)
);

CREATE TABLE `T_COMMENT` (
  `id`                  BIGINT       NOT NULL AUTO_INCREMENT,
  `body`                TEXT         NOT NULL,
  `post_id`             BIGINT       NOT NULL,
  `user_id`             BIGINT       NOT NULL,
  `date_created`        DATETIME     NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`)      REFERENCES T_USER(`id`),
  FOREIGN KEY (`post_id`)      REFERENCES T_POST(`id`)
);

CREATE TABLE `T_VERIFICATION_TOKEN` (
  `id`                  BIGINT       NOT NULL AUTO_INCREMENT,
  `token`               TEXT         NOT NULL,
  `user_id`             BIGINT       NOT NULL,
  `expiry_date`         DATETIME     NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`)      REFERENCES T_USER(`id`)
);