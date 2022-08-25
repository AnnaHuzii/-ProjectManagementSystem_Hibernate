CREATE TABLE developer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(250),
    birth_date DATE,
    sex ENUM('male', 'female', 'unknown')
        NOT NULL,
    email VARCHAR(250),
    skype VARCHAR(250),
    salary DECIMAL(10,0)
   );
CREATE TABLE skill(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    industry ENUM('Java', 'C++', 'C#', 'JS')
        NOT NULL,
    skill_level ENUM('Junior', 'Middle', 'Senior')
        NOT NULL
    );
CREATE TABLE company (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200),
    description VARCHAR(150)
);
CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200),
    edrpou INT,
    product VARCHAR(150)
);
CREATE TABLE project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    start_date date not null,
    name VARCHAR(200),
    description VARCHAR(150),
    cost DECIMAL(10,0),
    company_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL
);
CREATE TABLE project_developer (
    project_id BIGINT NOT NULL,
    developer_id BIGINT NOT NULL,
    FOREIGN KEY(project_id) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY(developer_id) REFERENCES developer(id) ON DELETE CASCADE
);

CREATE TABLE developer_skill (
    developer_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    FOREIGN KEY(developer_id) REFERENCES developer(id) ON DELETE CASCADE,
    FOREIGN KEY(skill_id) REFERENCES skill(id) ON DELETE CASCADE
);
