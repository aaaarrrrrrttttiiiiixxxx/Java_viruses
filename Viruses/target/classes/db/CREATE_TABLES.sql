CREATE TYPE city_size AS ENUM ('MEGAPOLIS', 'CITY', 'TOWN');

CREATE TABLE city
(
    id SERIAL NOT NULL PRIMARY KEY,
    city_size city_size,
    population INTEGER,
    city_transport_links FLOAT,
    intra_city_transport_links FLOAT,
    sick_one_weak INTEGER,
    sick_two_weak INTEGER,
    sick_three_weak INTEGER,
    vaccinated_one_weak INTEGER,
    vaccinated_two_weak INTEGER,
    vaccinated_three_weak INTEGER,
    vaccinated INTEGER,
    model_id INTEGER
);

CREATE TYPE season AS ENUM ('WINTER', 'SPRING', 'SUMMER', 'AUTUMN');

CREATE TABLE model
(
    id SERIAL NOT NULL PRIMARY KEY,
    season season,
    money BIGINT,
    vaccination_cost INTEGER,
    modeling_period INTEGER,
    start_date Date,
    curr_date Date
);