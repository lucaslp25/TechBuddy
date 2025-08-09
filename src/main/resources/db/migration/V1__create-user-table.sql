CREATE TABLE tb_users(

    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Para poder guardar senhas hash grandes!
    role VARCHAR(20) NOT NULL
);