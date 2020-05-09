CREATE TABLE users (
    userName         VARCHAR(100) PRIMARY KEY NOT NULL  ,
    email            VARCHAR(255) NOT NULL              ,
    password         VARCHAR(100)                       ,
    token            VARCHAR(255)                       ,
    profile_id       VARCHAR(255) UNIQUE                ,
    creation_date    DATETIME
)