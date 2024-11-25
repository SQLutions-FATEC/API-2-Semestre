CREATE DATABASE  IF NOT EXISTS `avaliador`;
USE `avaliador`;

DROP TABLE IF EXISTS `nota`;
DROP TABLE IF EXISTS `usuario`;
DROP TABLE IF EXISTS `tipo_usuario`;
DROP TABLE IF EXISTS `criterio_periodo`;
DROP TABLE IF EXISTS `equipe_periodo`;
DROP TABLE IF EXISTS `criterio`;
DROP TABLE IF EXISTS `pontuacao`;
DROP TABLE IF EXISTS `sprint`;
DROP TABLE IF EXISTS `periodo`;
DROP TABLE IF EXISTS `equipe`;

CREATE TABLE `nota` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`valor` TINYINT NOT NULL,
	`avaliador` INTEGER NOT NULL,
	`avaliado` INTEGER NOT NULL,
	`criterio` INTEGER NOT NULL,
	`periodo` INTEGER NOT NULL,
	`sprint` INTEGER NOT NULL,
	PRIMARY KEY(`id`)
);

CREATE TABLE `usuario` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`nome` CHAR(255) NOT NULL,
	`ra` BIGINT,
	`email` VARCHAR(255) NOT NULL UNIQUE,
	`senha` VARCHAR(255) NOT NULL,
	`tipo` INTEGER NOT NULL,
	`equipe` INTEGER,
	`deleted_at` DATETIME,
	PRIMARY KEY(`id`)
);

CREATE TABLE `tipo_usuario` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`descricao` CHAR(100) NOT NULL UNIQUE,
    `deleted_at` DATETIME,
	PRIMARY KEY(`id`)
);

CREATE TABLE `criterio` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
    `nome` CHAR(30) NOT NULL UNIQUE,
	`descricao` VARCHAR(255) NOT NULL,
    `deleted_at` DATETIME,
	PRIMARY KEY(`id`)
);

CREATE TABLE `periodo` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`semestre` tinyint NOT NULL,
    `ano` smallint NOT NULL,
	PRIMARY KEY(`id`)
);

CREATE TABLE `sprint` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`descricao` VARCHAR(255) NOT NULL,
	`data_inicio` DATE NOT NULL,
	`data_fim` DATE NOT NULL,
	`periodo` INTEGER NOT NULL,
	`data_hora` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`id`)
);


CREATE TABLE `pontuacao` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`valor` TINYINT NOT NULL,
	`sprint` INTEGER NOT NULL,
	`equipe` INTEGER NOT NULL,
	PRIMARY KEY(`id`)
);

CREATE TABLE `equipe` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`nome` VARCHAR(255) NOT NULL,
    `deleted_at` DATETIME,
	PRIMARY KEY(`id`)
);

CREATE TABLE `criterio_periodo` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
	`criterio_id` INTEGER NOT NULL,
	`periodo_id` INTEGER NOT NULL,
    `deleted_at` DATETIME,
	PRIMARY KEY(`id`)
);

CREATE TABLE `equipe_periodo` (
	`id` INTEGER NOT NULL AUTO_INCREMENT UNIQUE,
    `github` VARCHAR(255) NOT NULL UNIQUE,
	`periodo_id` INTEGER NOT NULL,
	`equipe_id` INTEGER NOT NULL,
    `deleted_at` DATETIME,
	PRIMARY KEY(`id`)
);

ALTER TABLE `nota`
ADD FOREIGN KEY(`avaliador`) REFERENCES `usuario`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `nota`
ADD FOREIGN KEY(`avaliado`) REFERENCES `usuario`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `usuario`
ADD FOREIGN KEY(`tipo`) REFERENCES `tipo_usuario`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `nota`
ADD FOREIGN KEY(`criterio`) REFERENCES `criterio`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `nota`
ADD FOREIGN KEY(`periodo`) REFERENCES `periodo`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `nota`
ADD FOREIGN KEY(`sprint`) REFERENCES `sprint`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `usuario`
ADD FOREIGN KEY(`equipe`) REFERENCES `equipe`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `pontuacao`
ADD FOREIGN KEY(`equipe`) REFERENCES `equipe`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `pontuacao`
ADD FOREIGN KEY(`sprint`) REFERENCES `sprint`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `sprint`
ADD FOREIGN KEY(`periodo`) REFERENCES `periodo`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `criterio_periodo`
ADD FOREIGN KEY(`criterio_id`) REFERENCES `criterio`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `criterio_periodo`
ADD FOREIGN KEY(`periodo_id`) REFERENCES `periodo`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `equipe_periodo`
ADD FOREIGN KEY(`equipe_id`) REFERENCES `equipe`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `equipe_periodo`
ADD FOREIGN KEY(`periodo_id`) REFERENCES `periodo`(`id`)
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE `nota`
ADD CONSTRAINT unique_nota UNIQUE (`avaliador`, `avaliado`, `criterio`, `periodo`, `sprint`);

ALTER TABLE `periodo`
ADD CONSTRAINT unique_periodo UNIQUE (`semestre`, `ano`);

ALTER TABLE `pontuacao`
ADD CONSTRAINT unique_pontuacao UNIQUE (`sprint`, `equipe`);

ALTER TABLE `criterio_periodo`
ADD CONSTRAINT unique_criterio_periodo UNIQUE (`criterio_id`, `periodo_id`);

ALTER TABLE `equipe_periodo`
ADD CONSTRAINT unique_equipe_periodo UNIQUE (`equipe_id`, `periodo_id`);
