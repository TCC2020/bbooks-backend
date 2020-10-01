CREATE TABLE books (
    ID                  INT PRIMARY KEY AUTO_INCREMENT ,
    CATEGORY            VARCHAR(255),
    DESCRIPTION         VARCHAR(255),
    EDITION             VARCHAR(255),
    ISBN10              VARCHAR(255),
    LANGUAGE            VARCHAR(255),
    NUMBER_PAGE         INTEGER(10) NOT NULL,
    PRINT_TYPE          VARCHAR(255),
    PUBLISHED_DATE      TIMESTAMP(26, 6),
    PUBLISHER           VARCHAR(255),
    TITLE               VARCHAR(255)
)