-- PRAGMA Anweisungen

PRAGMA encoding = "UTF-8";
PRAGMA foreign_keys = ON;
PRAGMA auto_vacuum = 1;
PRAGMA automatic_index = ON;



-- CREATE Anweisungen

CREATE TABLE IF NOT EXISTS Nutzer
(
Benutzername VARCHAR PRIMARY KEY NOT NULL,
EMail VARCHAR NOT NULL UNIQUE,
Passwort    VARCHAR NOT NULL
);
CREATE TABLE IF NOT EXISTS Premium_Nutzer
(
Benutzername VARCHAR PRIMARY KEY NOT NULL,
FOREIGN KEY(Benutzername) REFERENCES Nutzer(Benutzername) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS Schauspieler
(
    Benutzername VARCHAR PRIMARY KEY
                         NOT NULL,
    Vorname      VARCHAR NOT NULL,
    Nachname     VARCHAR NOT NULL,
    Kunstlername VARCHAR,
    Geburtsdatum VARCHAR NOT NULL,
    Geburtsort   VARCHAR NOT NULL,
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Premium_Nutzer (Benutzername) ON DELETE CASCADE
                                             ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Medienkonzern
(
Bezeichnung VARCHAR PRIMARY KEY NOT NULL
);
CREATE TABLE IF NOT EXISTS Video
(
    Bezeichnung               VARCHAR     PRIMARY KEY
                                          NOT NULL,
    Spieldauer                INTEGER     NOT NULL,
    Erscheinungsjahr          VARCHAR (4) NOT NULL,
    Informationen             TEXT,
    Medienkonzern_Bezeichnung VARCHAR     NOT NULL,
    FOREIGN KEY (
        Medienkonzern_Bezeichnung
    )
    REFERENCES Medienkonzern (Bezeichnung) ON DELETE CASCADE
                                           ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Watchlist
(
    ID  INTEGER      PRIMARY KEY AUTOINCREMENT
                                    NOT NULL,
    Bezeichnung        VARCHAR      NOT NULL,
    Privatspharestatus VARCHAR (10) NOT NULL
                                    CHECK (Privatspharestatus = 'privat' OR
                                           Privatspharestatus = 'öffentlich'),
    Benutzername       VARCHAR      NOT NULL,
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Premium_Nutzer (Benutzername) ON DELETE CASCADE
                                             ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Film
(
    Bezeichnung  VARCHAR PRIMARY KEY
                              NOT NULL,
    Produktionsbudget INTEGER NOT NULL,
    FOREIGN KEY (
        Bezeichnung
    )
    REFERENCES Video (Bezeichnung) ON DELETE CASCADE
                                   ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Film_Kommentar
(
    ID  INTEGER PRIMARY KEY AUTOINCREMENT
                         NOT NULL,
    Kommentar    TEXT    NOT NULL,
    Benutzername VARCHAR NOT NULL,
    Bezeichnung  VARCHAR NOT NULL,
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Premium_Nutzer (Benutzername) ON DELETE CASCADE
                                             ON UPDATE CASCADE,
    FOREIGN KEY (
        Bezeichnung
    )
    REFERENCES Film (Bezeichnung)
);
CREATE TABLE IF NOT EXISTS Serie
(
    ID         INTEGER PRIMARY KEY AUTOINCREMENT
                       NOT NULL,
    Serienname VARCHAR NOT NULL
);
CREATE TABLE IF NOT EXISTS Staffel
(
    ID            INTEGER PRIMARY KEY AUTOINCREMENT
                          NOT NULL,
    Staffelnummer INTEGER NOT NULL,
    SerieID       INTEGER NOT NULL,
    FOREIGN KEY (
        SerieID
    )
    REFERENCES Serie (ID) ON DELETE CASCADE
                          ON UPDATE CASCADE,

    UNIQUE (
        Staffelnummer ,
        SerieID
    )
);
CREATE TABLE IF NOT EXISTS Folge
(
    Bezeichnung VARCHAR PRIMARY KEY
                        NOT NULL,
    StaffelId   INTEGER NOT NULL,
    FOREIGN KEY (
        Bezeichnung
    )
    REFERENCES Video (Bezeichnung) ON DELETE CASCADE
                                   ON UPDATE CASCADE,
    FOREIGN KEY (
        StaffelId
    )
    REFERENCES Staffel (ID)
);
CREATE TABLE IF NOT EXISTS Staffel_Kommentar
(
    ID           INTEGER PRIMARY KEY AUTOINCREMENT
                         NOT NULL,
    Kommentar    TEXT    NOT NULL,
    Benutzername VARCHAR NOT NULL,
    StaffelId    INTEGER NOT NULL,
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Premium_Nutzer (Benutzername) ON DELETE CASCADE
                                             ON UPDATE CASCADE,
    FOREIGN KEY (
        StaffelId
    )
    REFERENCES Staffel (ID) ON DELETE CASCADE
                            ON UPDATE CASCADE
);
 
CREATE TABLE IF NOT EXISTS Trailer
(
    Bezeichnung     VARCHAR     PRIMARY KEY
                                NOT NULL,
    FilmBezeichnung VARCHAR     NOT NULL,
    Qualitat        VARCHAR (2) NOT NULL
                                CHECK (Qualitat = 'LQ' OR
                                       Qualitat = 'HQ'),
    FOREIGN KEY (
        Bezeichnung
    )
    REFERENCES Video (Bezeichnung) ON DELETE CASCADE
                                   ON UPDATE CASCADE,
    FOREIGN KEY (
        FilmBezeichnung
    )
    REFERENCES Film (Bezeichnung)
);
CREATE TABLE IF NOT EXISTS Genre
(
    Bezeichnung VARCHAR PRIMARY KEY
                      NOT NULL
);
 
CREATE TABLE IF NOT EXISTS Bewertet
(
    Benutzername VARCHAR NOT NULL,
    Bezeichnung  VARCHAR NOT NULL,
    Bewertung    INTEGER NOT NULL
                         CHECK (Bewertung IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10) ),
    PRIMARY KEY (
        Benutzername,
        Bezeichnung
    ),
    FOREIGN KEY (
        Bezeichnung
    )
    REFERENCES Film (Bezeichnung) ON DELETE CASCADE
                                  ON UPDATE CASCADE,
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Nutzer (Benutzername) ON DELETE CASCADE
                                     ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Enthält
(
    Bezeichnung VARCHAR NOT NULL,
    WatchlistId INTEGER NOT NULL,
    PRIMARY KEY (
        Bezeichnung,
        WatchlistId
    ),
    FOREIGN KEY (
        Bezeichnung
    )
    REFERENCES Video (Bezeichnung) ON DELETE CASCADE
                                   ON UPDATE CASCADE,
    FOREIGN KEY (
        WatchlistId
    )
    REFERENCES Watchlist (ID) ON DELETE CASCADE
                              ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Wirkt_mit_Film
(
    Bezeichnung  VARCHAR NOT NULL,
    Benutzername VARCHAR NOT NULL,
    PRIMARY KEY (
        Bezeichnung,
        Benutzername
    ),
    FOREIGN KEY (
        Bezeichnung
    )
    REFERENCES Film (Bezeichnung) ON DELETE CASCADE
                                  ON UPDATE CASCADE,
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Schauspieler (Benutzername) ON DELETE CASCADE
                                           ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Wirkt_mit_Serie
(
    SerieId      INTEGER NOT NULL,
    Benutzername VARCHAR NOT NULL,
    PRIMARY KEY (
        SerieId,
        Benutzername
    ),
    FOREIGN KEY (
        SerieId
    )
    REFERENCES Serie (ID) ON DELETE CASCADE
                          ON UPDATE CASCADE,
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Schauspieler (Benutzername) ON DELETE CASCADE
                                           ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Veröffentlicht
(
Medienkonzert_Bezeichnung VARCHAR NOT NULL,
    Film_Bezeichnung          VARCHAR NOT NULL,
    PRIMARY KEY (
        Medienkonzert_Bezeichnung,
        Film_Bezeichnung
    ),
    FOREIGN KEY (
        Medienkonzert_Bezeichnung
    )
    REFERENCES Medienkonzern (Bezeichnung) ON DELETE CASCADE
                                           ON UPDATE CASCADE,
    FOREIGN KEY (
        Film_Bezeichnung
    )
    REFERENCES Film (Bezeichnung) ON DELETE CASCADE
                                  ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Gehört_an
(
Video_Bezeichnung VARCHAR NOT NULL,
    Genre_Bezeichnung VARCHAR NOT NULL,
    PRIMARY KEY (
        Video_Bezeichnung,
        Genre_Bezeichnung
    ),
    FOREIGN KEY (
        Video_Bezeichnung
    )
    REFERENCES Video (Bezeichnung) ON DELETE CASCADE
                                   ON UPDATE CASCADE,
    FOREIGN KEY (
        Genre_Bezeichnung
    )
    REFERENCES Genre (Bezeichnung) ON DELETE CASCADE
                                   ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Folgt
(
Benutzername  VARCHAR NOT NULL,
    Benutzername1 VARCHAR NOT NULL,
    PRIMARY KEY (
        Benutzername,
        Benutzername1
    ),
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Premium_Nutzer (Benutzername) ON DELETE CASCADE
                                             ON UPDATE CASCADE,
    FOREIGN KEY (
        Benutzername1
    )
    REFERENCES Premium_Nutzer (Benutzername) ON DELETE CASCADE
                                             ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS Steht_unter_Vertrag
(
Benutzername VARCHAR NOT NULL,
    Bezeichnung  VARCHAR NOT NULL,
    PRIMARY KEY (
        Benutzername,
        Bezeichnung
    ),
    FOREIGN KEY (
        Benutzername
    )
    REFERENCES Schauspieler (Benutzername) ON DELETE CASCADE
                                           ON UPDATE CASCADE,
    FOREIGN KEY (
        Bezeichnung
    )
    REFERENCES Medienkonzern (Bezeichnung) ON DELETE CASCADE
                                           ON UPDATE CASCADE
);
 
