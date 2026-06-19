/*A5*/

CREATE TABLE adresse (
    adresse_id SERIAL PRIMARY KEY ,
    aktiv BOOLEAN NOT NULL ,
    strasse VARCHAR(60) NOT NULL ,
    hausnummer VARCHAR(10) NOT NULL ,
    plz VARCHAR NOT NULL ,
    ort VARCHAR(255) NOT NULL ,
    land VARCHAR(255) NOT NULL ,
    CONSTRAINT strasse_pattern CHECK (
        strasse ~ '^[A-ZÄÖÜ][a-zäöüß]*$'
        ) ,
    CONSTRAINT hausnummer_pattern CHECK (
        hausnummer ~ '^[0-9]+[a-z]?$'
    )
);

CREATE TABLE kunde (
    kunde_id SERIAL PRIMARY KEY ,
    email VARCHAR(256) NOT NULL UNIQUE ,
    vorname VARCHAR(32) NOT NULL ,
    nachname VARCHAR(32) NOT NULL ,
    passwort VARCHAR(20) NOT NULL ,
    CONSTRAINT vorname_pattern CHECK (
        vorname ~ '^[A-ZÄÖÜa-zäöüß]+$'
        ) ,
    CONSTRAINT nachname_pattern CHECK (
        nachname ~ '^[A-ZÄÖÜa-zäöüß]+$'
        ) ,
    CONSTRAINT email_pattern CHECK (
        email ~ '^[^@]+@[^@]+\.[^@]+$'
    ) ,
    CONSTRAINT kunde_passwort_length CHECK (
        LENGTH(passwort) >= 5
    ) ,
    CONSTRAINT passwort_pattern CHECK (
        passwort ~ '[A-Za-z]' AND
        passwort ~ '[0-9]' AND
        passwort ~ '[^A-Za-z0-9]'
    )
) ;

CREATE TABLE kunde_hat_adressen (
    adresse_id INTEGER NOT NULL ,
    kunde_id INTEGER NOT NULL ,
    typ VARCHAR(255) NOT NULL ,
    PRIMARY KEY (adresse_id, kunde_id) ,
    CONSTRAINT fk_kunde_hat_adressen_adresse FOREIGN KEY (adresse_id)
        REFERENCES adresse(adresse_id) ON DELETE CASCADE ,
    CONSTRAINT fk_kunde_hat_adressen_kunde FOREIGN KEY (kunde_id)
        REFERENCES kunde(kunde_id) ON DELETE CASCADE,
    CONSTRAINT check_typ CHECK (typ IN ('Lieferadresse', 'Rechnungsadresse'))
) ;

CREATE TABLE mitarbeiter (
    personal_nr SERIAL PRIMARY KEY ,
    vorname VARCHAR(255) NOT NULL ,
    nachname VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL ,
    passwort VARCHAR(20) NOT NULL ,
    CONSTRAINT mitarbeiter_passwort_length CHECK (
        LENGTH(passwort) >= 5
    ) ,
    CONSTRAINT mitarbeiter_passwort_pattern CHECK (
        passwort ~ '[A-Za-z]' AND
        passwort ~ '[0-9]' AND
        passwort ~ '[^A-Za-z0-9]'
    )
);

CREATE TABLE bestellung (
    bestellung_id SERIAL PRIMARY KEY ,
    datum TIMESTAMP NOT NULL ,
    status VARCHAR(20) NOT NULL ,
    mitarbeiterzuweis INTEGER ,
    kunde_id INTEGER NOT NULL ,
    CONSTRAINT fk_bestellung_mitarbeiter FOREIGN KEY (mitarbeiterzuweis)
        REFERENCES mitarbeiter(personal_nr) ON DELETE SET NULL ,
    CONSTRAINT fk_bestellung_kunde FOREIGN KEY (kunde_id)
        REFERENCES kunde(kunde_id) ON DELETE CASCADE,
    CONSTRAINT check_status CHECK (
        status IN ('neu', 'bezahlt', 'versendet', 'abgeschlossen', 'storniert')
    )
) ;

CREATE OR REPLACE FUNCTION status_change_to_storniert()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'storniert' AND OLD.status NOT IN ('neu', 'bezahlt') THEN
        RAISE EXCEPTION 'Status darf nur auf "storniert" geändert werden, wenn der vorherige Status "neu" oder "bezahlt" war' ;
END IF ;
RETURN NEW ;
END ;
$$ LANGUAGE plpgsql ;

CREATE TRIGGER trigger_check_status_change
    BEFORE UPDATE ON bestellung
    FOR EACH ROW
    WHEN (NEW.status = 'storniert' AND OLD.status <> NEW.status)
    EXECUTE FUNCTION status_change_to_storniert() ;

CREATE TABLE produkt (
    sku VARCHAR(255) PRIMARY KEY ,
    name VARCHAR(255) NOT NULL ,
    preis NUMERIC(10, 2) NOT NULL ,
    lagerbestand INTEGER NOT NULL ,
    angelegt_von INTEGER NOT NULL ,
    CONSTRAINT fk_produkt_mitarbeiter FOREIGN KEY (angelegt_von)
        REFERENCES mitarbeiter(personal_nr) ON DELETE RESTRICT ,
    CONSTRAINT check_lagerbestand CHECK (
        lagerbestand >= 0
    )
) ;

CREATE TABLE bestellposition (
    position_id SERIAL PRIMARY KEY ,
    menge INTEGER NOT NULL ,
    sku VARCHAR(255) NOT NULL ,
    bestellung_id INTEGER NOT NULL ,
    CONSTRAINT fk_bestellposition_produkt FOREIGN KEY (sku)
        REFERENCES produkt(sku) ON DELETE CASCADE ,
    CONSTRAINT fk_bestellposition_bestellung FOREIGN KEY (bestellung_id)
        REFERENCES bestellung(bestellung_id) ON DELETE CASCADE ,
    CONSTRAINT check_menge CHECK (menge > 0)
) ;

CREATE OR REPLACE FUNCTION bestellposition_insert_trigger()
RETURNS TRIGGER AS $$
DECLARE
v_lagerbestand INTEGER ;
    v_status VARCHAR(255) ;
BEGIN
    SELECT status INTO v_status FROM bestellung WHERE bestellung_id = NEW.bestellung_id ;
    IF v_status <> 'storniert' THEN
        SELECT lagerbestand INTO v_lagerbestand FROM produkt WHERE sku = NEW.sku ;
            IF v_lagerbestand < NEW.menge THEN
                RAISE EXCEPTION 'Nicht genügend Lagerbestand für Produkt % vorhanden.  Verfügbar: %, Benötigt: %' ,
                NEW.sku, v_lagerbestand, NEW.menge ;
            END IF ;
            UPDATE produkt SET lagerbestand = lagerbestand - NEW.menge WHERE sku = NEW.sku ;
    END IF ;

    RETURN NEW ;
END ;
$$ LANGUAGE plpgsql ;

CREATE TRIGGER trigger_bestellposition_insert
    AFTER INSERT ON bestellposition
    FOR EACH ROW
    EXECUTE FUNCTION bestellposition_insert_trigger() ;

CREATE OR REPLACE FUNCTION bestellposition_update_trigger()
RETURNS TRIGGER AS $$
DECLARE
    v_lagerbestand INTEGER ;
    v_differenz INTEGER ;
    v_status VARCHAR(255) ;
BEGIN
    SELECT status INTO v_status FROM bestellung WHERE bestellung_id = NEW.bestellung_id ;
    IF v_status <> 'storniert' THEN
        v_differenz := NEW.menge - OLD.menge ;
        SELECT lagerbestand INTO v_lagerbestand FROM produkt WHERE sku = NEW. sku ;
        IF v_differenz > 0 THEN
            IF v_lagerbestand < v_differenz THEN
                RAISE EXCEPTION 'Nicht genügend Lagerbestand für Produkt % vorhanden. Verfügbar: %, Benötigt:  %' ,
                    NEW. sku, v_lagerbestand, v_differenz ;
            END IF ;
        END IF ;
        UPDATE produkt SET lagerbestand = lagerbestand - v_differenz WHERE sku = NEW.sku ;
    END IF ;

    RETURN NEW ;
END ;
$$ LANGUAGE plpgsql ;

CREATE TRIGGER trigger_bestellposition_update
    AFTER UPDATE ON bestellposition
    FOR EACH ROW
    WHEN (OLD.menge <> NEW.menge)
    EXECUTE FUNCTION bestellposition_update_trigger() ;

CREATE OR REPLACE FUNCTION bestellposition_delete_trigger()
RETURNS TRIGGER AS $$
DECLARE
v_status VARCHAR(255) ;
BEGIN
    SELECT status INTO v_status FROM bestellung WHERE bestellung_id = OLD.bestellung_id ;
    IF v_status <> 'storniert' THEN
        UPDATE produkt SET lagerbestand = lagerbestand + OLD.menge WHERE sku = OLD. sku ;
    END IF ;
    RETURN OLD ;
END ;
$$ LANGUAGE plpgsql ;

CREATE TRIGGER trigger_bestellposition_delete
    AFTER DELETE ON bestellposition
    FOR EACH ROW
    EXECUTE FUNCTION bestellposition_delete_trigger() ;

CREATE OR REPLACE FUNCTION bestellung_storniert_trigger()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'storniert' AND OLD.status <> 'storniert' THEN
        UPDATE produkt p
        SET lagerbestand = lagerbestand + bp.menge
        FROM bestellposition bp
        WHERE bp.bestellung_id = NEW.bestellung_id AND bp. sku = p.sku ;
    END IF ;

    RETURN NEW ;
END ;
$$ LANGUAGE plpgsql ;

CREATE TRIGGER trigger_bestellung_storniert
    AFTER UPDATE ON bestellung
    FOR EACH ROW
    WHEN (NEW.status = 'storniert' AND OLD.status <> 'storniert')
    EXECUTE FUNCTION bestellung_storniert_trigger() ;



/* A6 */

CREATE VIEW v_kunde_summe_anzahl_bestellungen AS
SELECT
    k.kunde_id,
    k.email,
    COALESCE(COUNT(DISTINCT b.bestellung_id), 0) AS anzahl_bestellungen,
    COALESCE(SUM(bp.menge * p.preis), 0) AS gesamtsumme
FROM kunde k
         LEFT JOIN bestellung b ON k.kunde_id = b.kunde_id
         LEFT JOIN bestellposition bp ON b.bestellung_id = bp.bestellung_id
         LEFT JOIN produkt p ON bp.sku = p.sku
GROUP BY k.kunde_id, k.email
ORDER BY k.kunde_id ASC;

CREATE VIEW v_produkt_verkaufszahlen AS
SELECT
    p.sku,
    p.name,COALESCE(SUM(CASE
                            WHEN b.status NOT IN ('neu', 'storniert') THEN bp.menge
                            ELSE 0
    END), 0) AS gesamt_verkaufte_menge,
    COALESCE(SUM(CASE
                     WHEN b.status NOT IN ('neu', 'storniert') THEN bp.menge * p.preis
                     ELSE 0
        END), 0) AS umsatz,
    COUNT(DISTINCT CASE
                       WHEN bp.position_id IS NOT NULL THEN b.bestellung_id
        END) AS anzahl_bestellungen
FROM produkt p
         LEFT JOIN bestellposition bp ON p.sku = bp.sku
         LEFT JOIN bestellung b ON bp.bestellung_id = b.bestellung_id
GROUP BY p.sku, p.name
ORDER BY gesamt_verkaufte_menge DESC;


CREATE VIEW v_mitarbeiter_uebersicht AS
SELECT
    m.personal_nr,
    COALESCE(COUNT(DISTINCT b.bestellung_id), 0) AS anzahl_verwalteter_bestellungen,
    COALESCE(COUNT(DISTINCT p.sku), 0) AS anzahl_angelegter_produkte
FROM mitarbeiter m
         LEFT JOIN bestellung b ON m.personal_nr = b.mitarbeiterzuweis
         LEFT JOIN produkt p ON m.personal_nr = p.angelegt_von
GROUP BY m.personal_nr
ORDER BY m.personal_nr ASC;


CREATE VIEW v_mitarbeiter_bestellstatus_uebersicht AS
SELECT
    m.personal_nr,
    s.status,
    COUNT(b.bestellung_id) AS anzahl_bestellungen
FROM mitarbeiter m
         CROSS JOIN (
    VALUES ('neu'), ('bezahlt'), ('versendet'), ('abgeschlossen'), ('storniert')
) AS s(status)
         LEFT JOIN bestellung b ON m.personal_nr = b.mitarbeiterzuweis
    AND b.status = s.status
GROUP BY m.personal_nr, s.status
ORDER BY m.personal_nr ASC, s.status ASC;


