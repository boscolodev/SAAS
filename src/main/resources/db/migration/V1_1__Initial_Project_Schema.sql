CREATE SCHEMA IF NOT EXISTS authentication;
SET SCHEMA authentication;

CREATE TABLE tb_role (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         role VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tb_user (
                         email VARCHAR(255) NOT NULL PRIMARY KEY,
                         password VARCHAR(12) NOT NULL,
                         status BOOLEAN NOT NULL,
                         nome VARCHAR(255),
                         sobrenome VARCHAR(255),
                         cpfCnpj VARCHAR(255),
                         rgIe VARCHAR(255),
                         dataNascimento VARCHAR(255),
                         telefone VARCHAR(255)
);

CREATE TABLE user_role (
                           user_email VARCHAR(255) NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (user_email, role_id),
                           FOREIGN KEY (user_email) REFERENCES tb_user(email),
                           FOREIGN KEY (role_id) REFERENCES tb_role(id)
);

CREATE TABLE user_addresses (
                                user_email VARCHAR(255) NOT NULL,
                                cep VARCHAR(255),
                                logradouro VARCHAR(255),
                                complemento VARCHAR(255),
                                bairro VARCHAR(255),
                                localidade VARCHAR(255),
                                uf VARCHAR(255),
                                estado VARCHAR(255),
                                regiao VARCHAR(255),
                                ibge VARCHAR(255),
                                gia VARCHAR(255),
                                ddd VARCHAR(255),
                                siafi VARCHAR(255),
                                FOREIGN KEY (user_email) REFERENCES tb_user(email)
);

-- Insert roles
INSERT INTO tb_role (role) VALUES ('ADMIN');
INSERT INTO tb_role (role) VALUES ('USER');
INSERT INTO tb_role (role) VALUES ('MASTER');
INSERT INTO tb_role (role) VALUES ('GUEST');

-- Insert users
INSERT INTO tb_user (email, password, status, nome, sobrenome, cpfCnpj, rgIe, dataNascimento, telefone) VALUES
                                                                                                            ('admin@example.com', 'adminPass', true, 'Admin', 'User', '00000000000', '0000000', '1970-01-01', '0000000000'),
                                                                                                            ('user@example.com', 'userPass', true, 'Regular', 'User', '11111111111', '1111111', '1980-01-01', '1111111111'),
                                                                                                            ('master@example.com', 'masterPass', true, 'Master', 'User', '22222222222', '2222222', '1990-01-01', '2222222222'),
                                                                                                            ('guest@example.com', 'guestPass', true, 'Guest', 'User', '33333333333', '3333333', '2000-01-01', '3333333333');

-- Assign roles to users
INSERT INTO user_role (user_email, role_id) VALUES
                                                ('admin@example.com', (SELECT id FROM tb_role WHERE role = 'ADMIN')),
                                                ('user@example.com', (SELECT id FROM tb_role WHERE role = 'USER')),
                                                ('master@example.com', (SELECT id FROM tb_role WHERE role = 'MASTER')),
                                                ('guest@example.com', (SELECT id FROM tb_role WHERE role = 'GUEST'));