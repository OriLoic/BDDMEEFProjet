
-- Supprimer les tables dans l'ordre des dépendances dans le cas où il y aurait un doublon
DROP TABLE film_categories CASCADE CONSTRAINTS;
DROP TABLE location_abonne CASCADE CONSTRAINTS;
DROP TABLE location_carte_credit CASCADE CONSTRAINTS;
DROP TABLE client_carte_abonnement CASCADE CONSTRAINTS;
DROP TABLE films CASCADE CONSTRAINTS;
DROP TABLE categories CASCADE CONSTRAINTS;
DROP TABLE carte_abonnement CASCADE CONSTRAINTS;
DROP TABLE clients CASCADE CONSTRAINTS;

-- Supprimer les séquences associées aux tables
DROP SEQUENCE seq_clients;
DROP SEQUENCE seq_carte_abonnement;
DROP SEQUENCE seq_films;
DROP SEQUENCE seq_location_carte_credit;
DROP SEQUENCE seq_location_abonne;
DROP SEQUENCE seq_categories;


-- Création de la table des clients
CREATE SEQUENCE seq_clients START WITH 1 INCREMENT BY 1;

CREATE TABLE clients (
    id INT PRIMARY KEY,
    nom VARCHAR2(100) NOT NULL,
    numerosCartes VARCHAR2(255)
);

CREATE OR REPLACE TRIGGER trg_clients_id
BEFORE INSERT ON clients
FOR EACH ROW
BEGIN
    :NEW.id := seq_clients.NEXTVAL;
END;
/

-- Table des cartes d'abonnement
CREATE SEQUENCE seq_carte_abonnement START WITH 1 INCREMENT BY 1;

CREATE TABLE carte_abonnement (
    id INT PRIMARY KEY,
    numeroCarte INT UNIQUE NOT NULL,
    solde NUMBER(10, 2) DEFAULT 0.0,
    nbFilmsLoues INT DEFAULT 0
);

CREATE OR REPLACE TRIGGER trg_carte_abonnement_id
BEFORE INSERT ON carte_abonnement
FOR EACH ROW
BEGIN
    :NEW.id := seq_carte_abonnement.NEXTVAL;
END;
/

-- Table pour lier un client à une carte d'abonnement
CREATE TABLE client_carte_abonnement (
    clientId INT,
    carteId INT,
    PRIMARY KEY (clientId, carteId),
    FOREIGN KEY (clientId) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (carteId) REFERENCES carte_abonnement(id) ON DELETE CASCADE
);

-- Table des films
CREATE SEQUENCE seq_films START WITH 1 INCREMENT BY 1;

CREATE TABLE films (
    idFilm INT PRIMARY KEY,
    titre VARCHAR2(255) NOT NULL,
    realisateur VARCHAR2(255),
    theme VARCHAR2(255),
    dateSortie DATE,
    noteFilm INT,
    supportBluRay CHAR(1) DEFAULT 'N' CHECK (supportBluRay IN ('Y', 'N')),
    supportDematerialise CHAR(1) DEFAULT 'N' CHECK (supportDematerialise IN ('Y', 'N'))
);

CREATE OR REPLACE TRIGGER trg_films_id
BEFORE INSERT ON films
FOR EACH ROW
BEGIN
    :NEW.idFilm := seq_films.NEXTVAL;
END;
/

-- Table des locations avec carte de crédit
CREATE SEQUENCE seq_location_carte_credit START WITH 1 INCREMENT BY 1;

CREATE TABLE location_carte_credit (
    id INT PRIMARY KEY,
    clientId INT,
    filmId INT,
    supportLocation VARCHAR2(20) CHECK (supportLocation IN ('BLU_RAY', 'DEMATERIALISE')),
    dureeLocation INT NOT NULL,
    dateLocation DATE NOT NULL,
    FOREIGN KEY (clientId) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (filmId) REFERENCES films(idFilm) ON DELETE CASCADE
);

CREATE OR REPLACE TRIGGER trg_location_carte_credit_id
BEFORE INSERT ON location_carte_credit
FOR EACH ROW
BEGIN
    :NEW.id := seq_location_carte_credit.NEXTVAL;
END;
/

-- Table des locations avec carte d'abonnement
CREATE SEQUENCE seq_location_abonne START WITH 1 INCREMENT BY 1;

CREATE TABLE location_abonne (
    id INT PRIMARY KEY,
    carteId INT,
    filmId INT,
    supportLocation VARCHAR2(20) CHECK (supportLocation IN ('BLU_RAY', 'DEMATERIALISE')),
    dureeLocation INT NOT NULL,
    dateLocation DATE NOT NULL,
    FOREIGN KEY (carteId) REFERENCES carte_abonnement(id) ON DELETE CASCADE,
    FOREIGN KEY (filmId) REFERENCES films(idFilm) ON DELETE CASCADE
);

CREATE OR REPLACE TRIGGER trg_location_abonne_id
BEFORE INSERT ON location_abonne
FOR EACH ROW
BEGIN
    :NEW.id := seq_location_abonne.NEXTVAL;
END;
/

-- Table des catégories
CREATE SEQUENCE seq_categories START WITH 1 INCREMENT BY 1;

CREATE TABLE categories (
    id INT PRIMARY KEY,
    nom VARCHAR2(255) NOT NULL UNIQUE
);

CREATE OR REPLACE TRIGGER trg_categories_id
BEFORE INSERT ON categories
FOR EACH ROW
BEGIN
    :NEW.id := seq_categories.NEXTVAL;
END;
/

-- Lien entre films et catégories
CREATE TABLE film_categories (
    filmId INT,
    categorieId INT,
    PRIMARY KEY (filmId, categorieId),
    FOREIGN KEY (filmId) REFERENCES films(idFilm) ON DELETE CASCADE,
    FOREIGN KEY (categorieId) REFERENCES categories(id) ON DELETE CASCADE
);

-- Insertion des clients
INSERT INTO clients (nom, numerosCartes) VALUES ('Alice Martin', '1001');
INSERT INTO clients (nom, numerosCartes) VALUES ('Bob Dupont', '1002,1003');

-- Insertion des cartes d'abonnement
INSERT INTO carte_abonnement (numeroCarte, solde, nbFilmsLoues) VALUES (1003, 50.0, 0);

-- Lien entre client et carte d'abonnement
INSERT INTO client_carte_abonnement (clientId, carteId)
VALUES ((SELECT id FROM clients WHERE numerosCartes LIKE '%1002%'), 
        (SELECT id FROM carte_abonnement WHERE numeroCarte = 1003));

-- Insertion des films
INSERT INTO films (titre, realisateur, theme, dateSortie, noteFilm, supportBluRay, supportDematerialise)
VALUES ('Inception', 'Christopher Nolan', 'Science Fiction', TO_DATE('2010-07-16', 'YYYY-MM-DD'), 9, 'Y', 'Y');

INSERT INTO films (titre, realisateur, theme, dateSortie, noteFilm, supportBluRay, supportDematerialise)
VALUES ('Titanic', 'James Cameron', 'Romance', TO_DATE('1997-12-19', 'YYYY-MM-DD'), 8, 'Y', 'N');

INSERT INTO films (titre, realisateur, theme, dateSortie, noteFilm, supportBluRay, supportDematerialise)
VALUES ('The Godfather', 'Francis Ford Coppola', 'Crime', TO_DATE('1972-03-24', 'YYYY-MM-DD'), 10, 'Y', 'Y');

INSERT INTO films (titre, realisateur, theme, dateSortie, noteFilm, supportBluRay, supportDematerialise)
VALUES ('Avengers: Endgame', 'Anthony and Joe Russo', 'Superhero', TO_DATE('2019-04-26', 'YYYY-MM-DD'), 9, 'N', 'Y');

-- Insertion des catégories
INSERT INTO categories (nom) VALUES ('Science Fiction');
INSERT INTO categories (nom) VALUES ('Romance');
INSERT INTO categories (nom) VALUES ('Crime');
INSERT INTO categories (nom) VALUES ('Superhero');

-- Lien entre films et catégories
INSERT INTO film_categories (filmId, categorieId)
VALUES ((SELECT idFilm FROM films WHERE titre = 'Inception'), (SELECT id FROM categories WHERE nom = 'Science Fiction'));

INSERT INTO film_categories (filmId, categorieId)
VALUES ((SELECT idFilm FROM films WHERE titre = 'Titanic'), (SELECT id FROM categories WHERE nom = 'Romance'));

INSERT INTO film_categories (filmId, categorieId)
VALUES ((SELECT idFilm FROM films WHERE titre = 'The Godfather'), (SELECT id FROM categories WHERE nom = 'Crime'));

INSERT INTO film_categories (filmId, categorieId)
VALUES ((SELECT idFilm FROM films WHERE titre = 'Avengers: Endgame'), (SELECT id FROM categories WHERE nom = 'Superhero'));

-- Insertion des locations avec carte de crédit
INSERT INTO location_carte_credit (clientId, filmId, supportLocation, dureeLocation, dateLocation)
VALUES ((SELECT id FROM clients WHERE numerosCartes LIKE '%1001%'), 
        (SELECT idFilm FROM films WHERE titre = 'Inception'), 
        'DEMATERIALISE', 3, SYSDATE);

-- Insertion des locations avec carte d'abonnement
INSERT INTO location_abonne (carteId, filmId, supportLocation, dureeLocation, dateLocation)
VALUES ((SELECT id FROM carte_abonnement WHERE numeroCarte = 1003),
        (SELECT idFilm FROM films WHERE titre = 'The Godfather'),
        'BLU_RAY', 5, SYSDATE);

