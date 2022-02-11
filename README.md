# web_scraping

## Pré-requisitos
pip install BeautifulSoup4

## Dados:
Idade do whisky, nome, preço, teor alcoolico, destilaria, país de origem e ingredientes.

## site 1
https://www.casadabebida.com.br/whisky/

## site 2
https://www.superadega.com.br/whisky

## site 3
https://caledoniastore.com.br

## pgadmin configuration
CREATE ROLE "trabalho-bd-user" WITH
	LOGIN
	NOSUPERUSER
	CREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'senha';
  
## server configuration 
name: trabalho-bd
host: localhost
port: 5432
maintaince database: postgres
username: trabalho-bd-user

## Database configuration
CREATE DATABASE "trabalho-bd"
    WITH 
    OWNER = "trabalho-bd-user"
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE SCHEMA j2ee;
CREATE TABLE j2ee.website
(
  	id serial NOT NULL,
	nome character varying(40) NOT NULL,
  	url character varying(100) NOT NULL,
  CONSTRAINT pk_website PRIMARY KEY (id),
  CONSTRAINT uq_website_nome UNIQUE (nome)
);
