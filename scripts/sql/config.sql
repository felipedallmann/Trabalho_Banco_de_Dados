CREATE SCHEMA j2ee;

CREATE TABLE IF NOT EXISTS j2ee.website (
    id serial NOT NULL,
    nome character varying(40) NOT NULL,
    url character varying(100) NOT NULL,
    CONSTRAINT pk_website PRIMARY KEY (id),
    CONSTRAINT uq_website_nome UNIQUE (nome)
);
