CREATE OR REPLACE TRIGGER nombreMaxFilmsAbo
-- verifie qu'on ne depasse pas le nombre max de locations avec un abonnement
BEFORE INSERT ON location_abonne
FOR EACH ROW 
DECLARE
    nbLocs INTEGER
BEGIN
    SELECT nbFilmsLoues
    INTO nbLocs
    FROM carte_abonnement c
    WHERE c.id = NEW.carteId;
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
BEFORE UPDATE ON carte_abonnement
FOR EACH ROW
BEGIN
    IF NEW.solde > OLD.solde AND (NEW.solde - OLD.solde) < 10 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Credit minimum autorisé : 10 euros')
    END IF;
END;
/

CREATE OR REPLACE TRIGGER soldeNegatifAbonnement
-- gèle l'utilisation d'une carte d'abonnement si son solde est négatif
BEFORE INSERT ON location_abonne
FOR EACH ROW
DECLARE
    solde_carte INTEGER
BEGIN
    SELECT solde
    INTO solde_carte
    FROM carte_abonnement c
    WHERE c.numeroCarte = NEW.carteId;
    IF solde_carte < 0 THEN
        RAISE_APPLICATION_ERROR(-20004, 'Solde negatif, veuillez créditer la carte')
    END IF;
    --reste à débiter automatiquement la carte de debit si plus de 30 jours
END;
/


CREATE OR REPLACE TRIGGER programmeFidelite
-- va ajouter 10 euros au solde de la carte si 20 locations de films dans le mois

CREATE OR REPLACE TRIGGER securiteHorsService
-- assure qu'une transaction n'est pas validée en cas de panne de la machine

CREATE OR REPLACE TRIGGER verifRetourFilm
-- empêche la mise à jour de la table des films loués si le mauvais film est retourné
