CREATE SCHEMA IF NOT EXISTS projetobd;

CREATE TABLE IF NOT EXISTS projetobd.pais_origem
(
    nome VARCHAR(50),
    CONSTRAINT pk_pais_origem PRIMARY KEY(nome)
);

CREATE TABLE IF NOT EXISTS projetobd.destilaria
(
    nome VARCHAR(50),
    data_fundacao DATE NOT NULL,
    pais_origem_nome VARCHAR(50) NOT NULL,
	CONSTRAINT pk_destilaria PRIMARY KEY (nome),
    CONSTRAINT fk_destilaria_pais_origem FOREIGN KEY (pais_origem_nome)
        REFERENCES projetobd.pais_origem (nome)
);

CREATE TABLE IF NOT EXISTS projetobd.loja
(
    nome VARCHAR(50),
    url VARCHAR(50) NOT NULL,
    numero_itens_vendido INT NOT NULL,
    preco_medio DECIMAL(2) NOT NULL,
	CONSTRAINT pk_loja PRIMARY KEY (nome)
);

CREATE TABLE IF NOT EXISTS projetobd.whisky
(
    id SERIAL,
    idade INT NOT NULL,
    nome VARCHAR(50) NOT NULL,
    teor_alcolico DECIMAL(2) NOT NULL,
    destilaria_nome VARCHAR(50) NOT NULL,
    pais_origem_nome VARCHAR(50) NOT NULL,
	CONSTRAINT pk_whisky PRIMARY KEY (id),
    CONSTRAINT fk_whisky_destilaria FOREIGN KEY (destilaria_nome)
        REFERENCES projetobd.destilaria (nome),
    CONSTRAINT fk_whisky_pais_origem FOREIGN KEY (pais_origem_nome)
        REFERENCES projetobd.pais_origem (nome),
    CHECK (teor_alcolico >= 0)
);

CREATE TABLE IF NOT EXISTS projetobd.historico
(
    whisky_id INT,
    loja_nome VARCHAR(50),
    preco_sem_desconto DECIMAL(2) NOT NULL,
    preco_com_desconto DECIMAL(2),
    acessado_em DATE NOT NULL,
	CONSTRAINT pk_historico PRIMARY KEY (whisky_id, loja_nome, acessado_em),
    CONSTRAINT fk_historico_whisky FOREIGN KEY (whisky_id)
        REFERENCES projetobd.whisky (id),
    CONSTRAINT fk_historico_loja FOREIGN KEY (loja_nome)
        REFERENCES projetobd.loja (nome),
    CHECK (preco_sem_desconto >= 0 AND preco_com_desconto >= 0)
);

CREATE TABLE IF NOT EXISTS projetobd.ingrediente
(
    nome VARCHAR(50),
    CONSTRAINT pk_ingrediente PRIMARY KEY (nome)
);

CREATE TABLE IF NOT EXISTS projetobd.destilaria_utiliza_ingrediente
(
    destilaria_nome VARCHAR(50),
    ingrediente_nome VARCHAR(50),
    CONSTRAINT pk_destilaria_utiliza_ingrediente PRIMARY KEY (destilaria_nome, ingrediente_nome),
    CONSTRAINT fk_destilaria_utiliza_ingrediente_destilaria FOREIGN KEY (destilaria_nome)
        REFERENCES projetobd.destilaria (nome),
    CONSTRAINT destilaria_utiliza_ingrediente_ingrediente FOREIGN KEY (ingrediente_nome)
        REFERENCES projetobd.ingrediente (nome)
);

CREATE TABLE IF NOT EXISTS projetobd.script
(
    loja_nome VARCHAR(50),
    data_insercao DATE,
    codigo TEXT,
	CONSTRAINT pk_loja_vende_whisky PRIMARY KEY (loja_nome, data_insercao),
    CONSTRAINT fk_loja_vende_whisky_loja FOREIGN KEY (loja_nome)
        REFERENCES projetobd.loja (nome)
);

CREATE TABLE IF NOT EXISTS projetobd.execucao_scripts
(
    script_loja_nome VARCHAR(50),
    script_data_insercao DATE,
    data_execucao DATE,
    CONSTRAINT pk_execucao_scripts PRIMARY KEY (script_loja_nome, script_data_insercao, data_execucao),
    CONSTRAINT fk_execucao_scripts_script_loja_nome FOREIGN KEY (script_loja_nome, script_data_insercao)
		REFERENCES projetobd.script(loja_nome, data_insercao)
);
