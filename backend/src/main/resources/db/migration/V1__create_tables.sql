CREATE TABLE users (
    id          UUID        NOT NULL PRIMARY KEY,
    username    VARCHAR(50) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20) NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP   NOT NULL,
    deleted_at  TIMESTAMP
);

CREATE TABLE employees (
    id          UUID         NOT NULL PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    role        VARCHAR(20)  NOT NULL,
    manager_id  UUID         REFERENCES employees(id),
    user_id     UUID         NOT NULL UNIQUE REFERENCES users(id),
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL,
    deleted_at  TIMESTAMP
);

CREATE TABLE vacations (
     id          UUID        NOT NULL PRIMARY KEY,
     employee_id UUID        NOT NULL REFERENCES employees(id),
     start_date  DATE        NOT NULL,
     end_date    DATE        NOT NULL,
     status      VARCHAR(20) NOT NULL,
     created_at  TIMESTAMP   NOT NULL,
     updated_at  TIMESTAMP   NOT NULL,
     deleted_at  TIMESTAMP
);
