CREATE TABLE IF NOT EXISTS tickets
(
    id         serial PRIMARY KEY,
    priority   smallint  NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS deliveries
(
    id                        serial PRIMARY KEY,
    customer_type             varchar(11) NOT NULL,
    delivery_status           varchar(11) NOT NULL,
    expected_delivery_time    timestamp,
    distance_to_destination   smallint,
    rider_rating              smallint,
    average_prepare_time      smallint,
    time_to_reach_destination smallint,
    ticket_id                 int REFERENCES tickets (id),
    created_at                timestamp   NOT NULL,
    updated_at                timestamp   NOT NULL
);