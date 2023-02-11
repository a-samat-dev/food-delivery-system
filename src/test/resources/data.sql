INSERT INTO deliveries (id, customer_type, delivery_status, expected_delivery_time, distance_to_destination,
                        rider_rating, average_prepare_time, time_to_reach_destination, ticket_id, created_at,
                        updated_at)
VALUES (1, 'NEW', 'PICKED_UP', NOW(), 16, 2, 9, 59, null, NOW(), NOW());

INSERT INTO deliveries (id, customer_type, delivery_status, expected_delivery_time, distance_to_destination,
                        rider_rating, average_prepare_time, time_to_reach_destination, ticket_id, created_at,
                        updated_at)
VALUES (2, 'VIP', 'RECEIVED', NOW(), 20, 9, 15, 20, null, NOW(), NOW());

INSERT INTO deliveries (id, customer_type, delivery_status, expected_delivery_time, distance_to_destination,
                        rider_rating, average_prepare_time, time_to_reach_destination, ticket_id, created_at,
                        updated_at)
VALUES (3, 'LOYAL', 'RECEIVED', NOW(), 2, 7, 5, 5, null, NOW(), NOW());

INSERT INTO deliveries (id, customer_type, delivery_status, expected_delivery_time, distance_to_destination,
                        rider_rating, average_prepare_time, time_to_reach_destination, ticket_id, created_at,
                        updated_at)
VALUES (4, 'NEW', 'PREPARING', NOW(), 5, 6, 20, 20, null, NOW(), NOW());

INSERT INTO deliveries (id, customer_type, delivery_status, expected_delivery_time, distance_to_destination,
                        rider_rating, average_prepare_time, time_to_reach_destination, ticket_id, created_at,
                        updated_at)
VALUES (5, 'NEW', 'RECEIVED', NOW(), 11, 8, 10, 30, null, NOW(), NOW());
