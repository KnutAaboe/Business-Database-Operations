DROP SCHEMA IF EXISTS oblig3 CASCADE;

CREATE SCHEMA oblig3;

SET search_path TO oblig3;

CREATE TABLE ansatt(
	ansId		SERIAL,
	bNavn		VARCHAR(4),
	fNavn		VARCHAR(255),
	eNavn		VARCHAR(255),
	ansDato		DATE,
	stilling	VARCHAR(255),
	mndLonn		INTEGER,
	avd			INTEGER,
	prosj		VARCHAR(255),
	CONSTRAINT ansattPK PRIMARY KEY (ansId),
	CONSTRAINT ansattUnik UNIQUE (ansId, bNavn),
	CONSTRAINT bNavnLengde CHECK (char_length(bNavn) BETWEEN 3 AND 4)
);

CREATE TABLE avdeling(
	avdId		SERIAL,
	navn		VARCHAR(255),
	sjef		INTEGER,
	CONSTRAINT avdelingPK PRIMARY KEY (avdId),
	CONSTRAINT avdelingFK FOREIGN KEY (sjef) REFERENCES ansatt(ansId),
	CONSTRAINT avdelingUnik UNIQUE (avdId)
);

CREATE TABLE prosjekt(
	prosId		SERIAL,
	navn		VARCHAR(255),
	beskrivelse	VARCHAR(255),
	CONSTRAINT prosjektPK PRIMARY KEY (prosId)
);

CREATE TABLE prosjektdeltakelse(
	
	ansId		INTEGER,
	prosId		INTEGER,
	timer		INTEGER,
	rolle		VARCHAR(255),
	CONSTRAINT prosjektdeltakelsePK PRIMARY KEY (ansId, prosId),
	CONSTRAINT ansFK FOREIGN KEY (ansId) REFERENCES ansatt(ansId),
	CONSTRAINT prosFK FOREIGN KEY (prosId) REFERENCES prosjekt(prosId)
);

INSERT INTO ansatt(bNavn, fNavn, eNavn, ansDato, stilling, mndLonn, avd, prosj) 
VALUES('pepe', 'Per', 'Persen', '2016-08-04', 'Dataingeniør', 50000, null, 'Helpdesk');
INSERT INTO ansatt(bNavn, fNavn, eNavn, ansDato, stilling, mndLonn, avd, prosj) 
VALUES('ansk', 'Anita', 'Skarstad', '1998-01-05', 'Sjef', 80000, null, 'Mange');

INSERT INTO avdeling(navn, sjef) VALUES ('Datahjelp', 2);
INSERT INTO avdeling(navn, sjef) VALUES ('Penger', 1);


UPDATE ansatt SET avd = 1 WHERE avd IS NULL;
UPDATE ansatt set avd = 2 WHERE ansId = 1;

ALTER TABLE ansatt ADD CONSTRAINT ansattAvdFK FOREIGN KEY (avd) REFERENCES avdeling(avdId);