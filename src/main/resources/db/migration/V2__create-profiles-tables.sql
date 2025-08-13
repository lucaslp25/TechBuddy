
-- Tabela pai de todos os profiles da aplicação.
CREATE TABLE tb_user_profiles(

    user_id BIGINT PRIMARY KEY,
    profile_type VARCHAR(50) NOT NULL, -- Coluna discriminadora dessa tabela
    profile_name VARCHAR(100) UNIQUE, -- tipo o username do usuairo dentro da plataforma
    headline VARCHAR(150),
    profile_bio TEXT,
    profile_picture_url VARCHAR(255),
    profile_location VARCHAR(100),
    profile_linkedin_url VARCHAR(255),
    profile_github_url VARCHAR(255),

    CONSTRAINT fk_user_profile_id FOREIGN KEY (user_id) REFERENCES tb_users(id) -- faz a chave primaria da tabela user ser essa chave aqui.
);

-- tabela para armazenar as stacks dos perfils
CREATE TABLE tb_profile_stacks(

    user_profile_id BIGINT NOT NULL,
    stack_name VARCHAR(255) NOT NULL,

    CONSTRAINT fk_profile_stacks FOREIGN KEY (user_profile_id) REFERENCES tb_user_profiles(user_id)
);

-- tabela dos mentores
CREATE TABLE tb_mentor_profiles(

    user_id BIGINT PRIMARY KEY,
    experience_years INT,
    company VARCHAR(100),
    professional_title VARCHAR(100),
    available_for_mentoring BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_profile_mentor FOREIGN KEY (user_id) REFERENCES tb_user_profiles(user_id)
);

-- tabela dos devs
CREATE TABLE tb_dev_profiles(

    user_id BIGINT PRIMARY KEY,
    learning_goals TEXT,
    current_skills_level VARCHAR(50),

    CONSTRAINT fk_dev_profile FOREIGN KEY (user_id) REFERENCES tb_user_profiles(user_id)
);