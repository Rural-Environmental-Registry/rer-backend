---------------------------------------------
-- Tabelas de divisão territorial IBGE (país, UF, município)
---------------------------------------------

CREATE TABLE country
(
    id          SERIAL PRIMARY KEY,
    geometry    geometry(MultiPolygon, 4326),
    country     VARCHAR(255),
    area_km2    DOUBLE PRECISION,
    create_date TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE state
(
    id          SERIAL PRIMARY KEY,
    geometry    geometry(MultiPolygon, 4326),
    country_id  INTEGER REFERENCES country(id),
    cd_uf       VARCHAR(2),
    nm_uf       VARCHAR(255),
    sigla_uf    VARCHAR(2),
    cd_regiao   VARCHAR(10),
    nm_regiao   VARCHAR(255),
    sigla_rg    VARCHAR(10),
    area_km2    DOUBLE PRECISION,
    create_date TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE municipality
(
    id          SERIAL PRIMARY KEY,
    geometry    geometry(MultiPolygon, 4326),
    cd_mun      VARCHAR(20),
    nm_mun      VARCHAR(255),
    cd_uf       VARCHAR(2),
    nm_uf       VARCHAR(255),
    sigla_uf    VARCHAR(2),
    cd_regiao   VARCHAR(10),
    nm_regiao   VARCHAR(255),
    sigla_rg    VARCHAR(10),
    area_km2    DOUBLE PRECISION,
    create_date TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_country_geometry ON country USING GIST (geometry);
CREATE INDEX idx_state_geometry ON state USING GIST (geometry);
CREATE INDEX idx_municipality_geometry ON municipality USING GIST (geometry);

---------------------------------------------
-- Views de sub_area por area_type
-- create_date é o instante da consulta (NOW()), não a data de gravação da linha
---------------------------------------------

CREATE OR REPLACE VIEW consolidated_area AS
SELECT id,
       geometry,
       property_id,
       NOW() AS create_date
FROM sub_area
WHERE area_type = 'CONSOLIDATED_AREA';

CREATE OR REPLACE VIEW legal_reserve AS
SELECT id,
       geometry,
       property_id,
       NOW() AS create_date
FROM sub_area
WHERE area_type = 'LEGAL_RESERVE';

CREATE OR REPLACE VIEW remaining_native_vegetation AS
SELECT id,
       geometry,
       property_id,
       NOW() AS create_date
FROM sub_area
WHERE area_type = 'REMAINING_NATIVE_VEGETATION';

CREATE OR REPLACE VIEW river AS
SELECT id,
       geometry,
       property_id,
       NOW() AS create_date
FROM sub_area
WHERE area_type = 'RIVER';

CREATE OR REPLACE VIEW river_up_to_10m AS
SELECT id,
       geometry,
       property_id,
       NOW() AS create_date
FROM sub_area
WHERE area_type = 'RIVER_UP_TO_10M';
