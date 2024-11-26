CREATE OR REPLACE TRIGGER nombreMaxFilmsAbo
-- verifie qu'on ne depasse pas le nombre max de locations avec un abonnement
BEFORE INSERT ON location_abonne
FOR EACH ROW 
DECLARE
    nbLocs INTEGER
BEGIN
    -- on compte de nombre de locations sur la carte
    SELECT COUNT(*)
    INTO nbLocs 
    FROM location_abonne l
    WHERE l.carteId = NEW.carteId;
    IF nbLocs >= 3 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Nombre maximum de locations sur une carte atteint')
    END IF;
END;
/

CREATE OR REPLACE TRIGGER nombreMaxFilmsCredit
-- verifie qu'on ne depasse pas le nombre max de locations avec une carte de credit
BEFORE INSERT ON location_carte_credit
FOR EACH ROW 
DECLARE
    nbLocs INTEGER
BEGIN
    -- on compte de nombre de locations sur la carte
    SELECT COUNT(*)
    INTO nbLocs 
    FROM location_carte_credit l
    WHERE l.clientId = NEW.clientId;
    IF nbLocs != 1 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Déjà une location en cours avec cette carte')
    END IF;
END;
/

CREATE OR REPLACE TRIGGER minimumCredit
-- assure le minimum de 10 euros lors du crédit d'une carte d'abonnement

CREATE OR REPLACE TRIGGER programmeFidelite
-- va ajouter 10 euros au solde de la carte si 20 locations de films dans le mois

CREATE OR REPLACE TRIGGER securiteHorsService
-- assure qu'une transaction n'est pas validée en cas de panne de la machine

CREATE OR REPLACE TRIGGER soldeNegatifAbonnement
-- gèle l'utilisation d'une carte d'abonnement si son solde est négatif

CREATE OR REPLACE TRIGGER verifRetourFilm
-- empêche la mise à jour de la table des films loués si le mauvais film est retourné


