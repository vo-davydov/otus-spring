ALTER TABLE IF EXISTS AUTHOR
    DROP CONSTRAINT IF EXISTS unique_name;
DROP TABLE IF EXISTS AUTHOR CASCADE;

CREATE TABLE AUTHOR
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(255)
);

ALTER TABLE IF EXISTS GENRE
    DROP CONSTRAINT IF EXISTS unique_name;
DROP TABLE IF EXISTS GENRE CASCADE;
CREATE TABLE GENRE
(
    ID   BIGSERIAL PRIMARY KEY,
    NAME VARCHAR(255)
);

ALTER TABLE GENRE
    ADD CONSTRAINT genre_unique_name UNIQUE (NAME);

ALTER TABLE IF EXISTS BOOK
    DROP CONSTRAINT IF EXISTS FK_AUTHOR;
ALTER TABLE IF EXISTS BOOK
    DROP CONSTRAINT IF EXISTS FK_GENRE;
DROP TABLE IF EXISTS BOOK CASCADE;
CREATE TABLE BOOK
(
    ID        BIGSERIAL PRIMARY KEY,
    NAME      VARCHAR(255),
    AUTHOR_ID BIGINT,
    GENRE_ID  BIGINT,
    CONSTRAINT FK_AUTHOR
        FOREIGN KEY (AUTHOR_ID)
            REFERENCES AUTHOR (ID),
    CONSTRAINT FK_GENRE
        FOREIGN KEY (GENRE_ID)
            REFERENCES GENRE (ID)
);

ALTER TABLE IF EXISTS COMMENT
    DROP CONSTRAINT IF EXISTS FK_BOOK;
DROP TABLE IF EXISTS COMMENT CASCADE;

CREATE TABLE COMMENT
(
    ID      BIGSERIAL PRIMARY KEY,
    TEXT    VARCHAR(65535),
    BOOK_ID BIGINT,
    CONSTRAINT FK_BOOK
        FOREIGN KEY (BOOK_ID)
            REFERENCES BOOK (ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS ROLE CASCADE;
CREATE TABLE ROLE
(
    ID   BIGSERIAL PRIMARY KEY,
    ROLE VARCHAR(65535)
);

DROP TABLE IF EXISTS SYS_USER CASCADE;

CREATE TABLE SYS_USER
(
    ID                      BIGSERIAL PRIMARY KEY,
    USERNAME                VARCHAR(65535) UNIQUE,
    PASSWORD                VARCHAR(65535),
    is_account_non_expired     boolean,
    is_account_non_locked      boolean,
    is_credentials_non_expired boolean,
    is_enabled               boolean
);

DROP TABLE IF EXISTS USER_ROLES CASCADE;

CREATE TABLE USER_ROLES
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    CONSTRAINT FK_USER
        FOREIGN KEY (USER_ID)
            REFERENCES SYS_USER (ID),
    CONSTRAINT FK_ROLE
        FOREIGN KEY (ROLE_ID)
            REFERENCES ROLE (ID)
);

DROP TABLE IF EXISTS acl_sid CASCADE;

CREATE TABLE IF NOT EXISTS acl_sid
(
    id        BIGSERIAL PRIMARY KEY,
    principal int          NOT NULL,
    sid       varchar(100) NOT NULL,
    CONSTRAINT unique_uk_1 UNIQUE (sid, principal)
);

DROP TABLE IF EXISTS acl_class CASCADE;

CREATE TABLE IF NOT EXISTS acl_class
(
    id    BIGSERIAL PRIMARY KEY,
    class varchar(255) NOT NULL,
    CONSTRAINT unique_uk_2 UNIQUE (class)
);

DROP TABLE IF EXISTS acl_entry CASCADE;

CREATE TABLE IF NOT EXISTS acl_entry
(
    id                  BIGSERIAL PRIMARY KEY,
    acl_object_identity bigint NOT NULL,
    ace_order           int    NOT NULL,
    sid                 bigint NOT NULL,
    mask                int    NOT NULL,
    granting            int    NOT NULL,
    audit_success       int    NOT NULL,
    audit_failure       int    NOT NULL,
    CONSTRAINT unique_uk_4 UNIQUE (acl_object_identity, ace_order)
);

DROP TABLE IF EXISTS acl_object_identity CASCADE;

CREATE TABLE IF NOT EXISTS acl_object_identity
(
    id                 BIGSERIAL PRIMARY KEY,
    object_id_class    bigint NOT NULL,
    object_id_identity varchar(36) NOT NULL,
    parent_object      bigint DEFAULT NULL,
    owner_sid          bigint DEFAULT NULL,
    entries_inheriting int    NOT NULL,
    CONSTRAINT unique_uk_3 UNIQUE (object_id_class, object_id_identity)
);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);