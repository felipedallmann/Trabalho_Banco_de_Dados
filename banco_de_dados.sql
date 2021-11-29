CREATE SCHEMA IF NOT EXISTS t2;

CREATE TABLE IF NOT EXISTS t2.destilaria
(
    nome VARCHAR(50),
    fundacao DATE NOT NULL,
    pais_origem_nome VARCHAR(50) NOT NULL,
	CONSTRAINT pk_destilaria PRIMARY KEY (nome),
    CONSTRAINT fk_destilaria_pais_origem FOREIGN KEY (pais_origem_nome)
        REFERENCES t2.pais_origem (nome)
);

CREATE TABLE IF NOT EXISTS t2.loja
(
    nome VARCHAR(50),
    url VARCHAR(50) NOT NULL,
	CONSTRAINT pk_loja PRIMARY KEY (nome)
);

CREATE TABLE IF NOT EXISTS t2.whisky
(
    id SERIAL,
    idade INT NOT NULL,
    nome VARCHAR(50) NOT NULL,
    preco DECIMAL(2) NOT NULL,
    preco_desconto DECIMAL(2) NOT NULL,
    teor_alcolico DECIMAL(2) NOT NULL,
    destilaria_nome VARCHAR(50) NOT NULL,
    pais_origem_nome VARCHAR(50) NOT NULL,
	CONSTRAINT pk_whisky PRIMARY KEY (id),
    CONSTRAINT fk_whisky_destilaria FOREIGN KEY (destilaria_nome)
        REFERENCES t2.destilaria (nome),
    CONSTRAINT fk_whisky_pais_origem FOREIGN KEY (pais_origem_nome)
        REFERENCES t2.pais_origem (nome),
   CHECK (preco >= 0 AND preco_desconto >= 0 AND teor_alcolico >= 0)
);

CREATE TABLE IF NOT EXISTS t2.loja_vende_whisky
(
    whisky_id INT,
    loja_nome VARCHAR(50),
	CONSTRAINT pk_loja_vende_whisky PRIMARY KEY (whisky_id, loja_nome),
    CONSTRAINT fk_loja_vende_whisky_whisky FOREIGN KEY (whisky_id)
        REFERENCES t2.whisky (id),
    CONSTRAINT fk_loja_vende_whisky_loja FOREIGN KEY (loja_nome)
        REFERENCES t2.loja (nome)
);
