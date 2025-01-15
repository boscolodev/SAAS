CREATE TABLE TB_ROLE (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         role VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE TB_USER (
                         email VARCHAR(255) NOT NULL PRIMARY KEY,
                         password VARCHAR(12) NOT NULL,
                         status VARCHAR(255) NOT NULL,
                         nome VARCHAR(255),
                         sobrenome VARCHAR(255),
                         cpf_cnpj VARCHAR(255),
                         rg_ie VARCHAR(255),
                         data_nascimento VARCHAR(255),
                         telefone VARCHAR(255)
);

CREATE TABLE USER_ROLE (
                           user_email VARCHAR(255) NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (user_email, role_id),
                           FOREIGN KEY (user_email) REFERENCES TB_USER(email),
                           FOREIGN KEY (role_id) REFERENCES TB_ROLE(id)
);

CREATE TABLE USER_ADDRESSES (
                                user_email VARCHAR(255) NOT NULL,
                                id VARCHAR(255) NOT NULL ,
                                cep VARCHAR(255) NOT NULL,
                                logradouro VARCHAR(255),
                                numero VARCHAR(255),
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
                                FOREIGN KEY (user_email) REFERENCES TB_USER(email)
);

INSERT INTO TB_ROLE (role) VALUES ('ADMIN');
INSERT INTO TB_ROLE (role) VALUES ('USER');
INSERT INTO TB_ROLE (role) VALUES ('MASTER');
INSERT INTO TB_ROLE (role) VALUES ('GUEST');

INSERT INTO TB_USER (email, password, status, nome, sobrenome, cpf_cnpj, rg_ie, data_nascimento, telefone) VALUES
                                                                                                               ('admin@example.com', 'adminPass', 'ATIVO' , 'Admin', 'User', '00000000000', '0000000', '1970-01-01', '0000000000'),
                                                                                                               ('user@example.com', 'userPass', 'BLOQUEADO', 'Regular', 'User', '11111111111', '1111111', '1980-01-01', '1111111111'),
                                                                                                               ('master@example.com', 'masterPass', 'ATIVO', 'Master', 'User', '22222222222', '2222222', '1990-01-01', '2222222222'),
                                                                                                               ('guest@example.com', 'guestPass', 'INATIVO', 'Guest', 'User', '33333333333', '3333333', '2000-01-01', '3333333333');

INSERT INTO USER_ROLE (user_email, role_id) VALUES
                                                ('admin@example.com', (SELECT id FROM TB_ROLE WHERE role = 'ADMIN')),
                                                ('admin@example.com', (SELECT id FROM TB_ROLE WHERE role = 'USER')),
                                                ('admin@example.com', (SELECT id FROM TB_ROLE WHERE role = 'GUEST')),
                                                ('user@example.com', (SELECT id FROM TB_ROLE WHERE role = 'USER')),
                                                ('master@example.com', (SELECT id FROM TB_ROLE WHERE role = 'ADMIN')),
                                                ('master@example.com', (SELECT id FROM TB_ROLE WHERE role = 'USER')),
                                                ('master@example.com', (SELECT id FROM TB_ROLE WHERE role = 'MASTER')),
                                                ('master@example.com', (SELECT id FROM TB_ROLE WHERE role = 'GUEST')),
                                                ('guest@example.com', (SELECT id FROM TB_ROLE WHERE role = 'GUEST'));

INSERT INTO USER_ADDRESSES (id, user_email, cep, logradouro, numero, complemento, bairro, localidade, uf, estado, regiao, ibge, gia, ddd, siafi) VALUES
                                                                                                                                                     (1, 'user@example.com', '12345-678', 'Rua User', '100', 'Apto 1', 'Bairro User', 'Cidade User', 'UF', 'Estado User', 'Região User', '1234567', '1234', '12', '1234'),
                                                                                                                                                     (2, 'admin@example.com', '23456-789', 'Rua Admin', '200', 'Apto 2', 'Bairro Admin', 'Cidade Admin', 'UF', 'Estado Admin', 'Região Admin', '2345678', '2345', '23', '2345'),
                                                                                                                                                     (3, 'guest@example.com', '34567-890', 'Rua Guest', '300', 'Apto 3', 'Bairro Guest', 'Cidade Guest', 'UF', 'Estado Guest', 'Região Guest', '3456789', '3456', '34', '3456'),
                                                                                                                                                     (4, 'guest@example.com', '45678-901', 'Rua Guest 2', '400', 'Apto 4', 'Bairro Guest 2', 'Cidade Guest 2', 'UF', 'Estado Guest 2', 'Região Guest 2', '4567890', '4567', '45', '4567'),
                                                                                                                                                     (5, 'master@example.com', '56789-012', 'Rua Master', '500', 'Apto 5', 'Bairro Master', 'Cidade Master', 'UF', 'Estado Master', 'Região Master', '5678901', '5678', '56', '5678'),
                                                                                                                                                     (6, 'master@example.com', '67890-123', 'Rua Master 2', '600', 'Apto 6', 'Bairro Master 2', 'Cidade Master 2', 'UF', 'Estado Master 2', 'Região Master 2', '6789012', '6789', '67', '6789'),
                                                                                                                                                     (7, 'master@example.com', '78901-234', 'Rua Master 3', '700', 'Apto 7', 'Bairro Master 3', 'Cidade Master 3', 'UF', 'Estado Master 3', 'Região Master 3', '7890123', '7890', '78', '7890');