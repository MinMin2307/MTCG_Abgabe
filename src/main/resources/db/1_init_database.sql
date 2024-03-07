DROP TABLE IF EXISTS deck CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS package CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS battle_history CASCADE;
DROP TABLE IF EXISTS trade CASCADE;
DROP TABLE IF EXISTS statistics CASCADE;


CREATE TABLE "user" (
                        id uuid NOT NULL DEFAULT gen_random_uuid(),
                        coins integer NOT NULL,
                        password varchar(255),
                        username varchar(255),
                        name varchar(255),
                        bio varchar(255),
                        image varchar(255),
                        PRIMARY KEY (id)
);

CREATE TABLE package (
                         id uuid NOT NULL DEFAULT gen_random_uuid(),
                         available boolean,
                         PRIMARY KEY (id)
);


CREATE TABLE card (
                      id uuid NOT NULL DEFAULT gen_random_uuid(),
                      name varchar(255),
                      damage double precision NOT NULL,
                      card_type varchar(255),
                      element_type varchar(255),
                      user_id uuid,
                      package_id uuid,
                      PRIMARY KEY (id),
                      FOREIGN KEY (user_id) REFERENCES "user"(id),
                      FOREIGN KEY (package_id) REFERENCES package(id)
);



CREATE TABLE deck (
                      id uuid NOT NULL DEFAULT gen_random_uuid(),
                      user_id uuid NOT NULL,
                      card_id uuid NOT NULL,
                      PRIMARY KEY (id),
                      FOREIGN KEY (user_id) REFERENCES "user"(id),
                      FOREIGN KEY (card_id) REFERENCES card(id)
);

-- Transactions Table
CREATE TABLE transactions (
				    transaction_id uuid NOT NULL DEFAULT gen_random_uuid(),
				    user_id uuid NOT NULL,
				    package_id uuid,
				    transaction_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
				    amount integer NOT NULL,
				    PRIMARY KEY (transaction_id),
				    FOREIGN KEY (user_id) REFERENCES "user"(id),
				    FOREIGN KEY (package_id) REFERENCES package(id)
);

-- Battle History Table
CREATE TABLE battle_history (
				    battle_id uuid NOT NULL DEFAULT gen_random_uuid(),
				    date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
				    participant_user_id_1 uuid NOT NULL,
				    participant_user_id_2 uuid NOT NULL,
				    winner_user_id uuid,
				    PRIMARY KEY (battle_id),
				    FOREIGN KEY (participant_user_id_1) REFERENCES "user"(id),
				    FOREIGN KEY (participant_user_id_2) REFERENCES "user"(id),
				    FOREIGN KEY (winner_user_id) REFERENCES "user"(id)
);

-- Trading Table
CREATE TABLE trade (
				    trade_id uuid NOT NULL DEFAULT gen_random_uuid(),
				    offer_user_id uuid NOT NULL,
				    request_user_id uuid,
				    offered_card_id uuid NOT NULL,
				    requested_card_id uuid,
				    trade_status varchar(255),
				    PRIMARY KEY (trade_id),
				    FOREIGN KEY (offer_user_id) REFERENCES "user"(id),
				    FOREIGN KEY (request_user_id) REFERENCES "user"(id),
				    FOREIGN KEY (offered_card_id) REFERENCES card(id),
				    FOREIGN KEY (requested_card_id) REFERENCES card(id)
);

-- Statistics Table
CREATE TABLE statistics (
				    user_id uuid NOT NULL,
				    number_of_battles integer NOT NULL,
				    wins integer NOT NULL,
				    losses integer NOT NULL,
				    PRIMARY KEY (user_id),
				    FOREIGN KEY (user_id) REFERENCES "user"(id)
);