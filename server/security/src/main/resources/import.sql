INSERT INTO user_credentials (id, created, email, modified, password, username, verified, locked)
        VALUES (1, '2018-03-05 03:08:10.898380', 'kuba@kuba.com', '2018-03-05 03:08:10.898380',
                        '$2a$10$5SEEZxF3UwGYqgiTEkIXNOx7O16mvXu/zP4haioE9QwC026k8RuR.', 'malyjasiak', TRUE, FALSE), -- password: KubaKuba0-
                (2, '2018-03-05 03:08:10.898380', 'admin@kuba.com', '2018-03-20 03:08:10.898380',
                        '$2a$04$ynq.Yqu.FiQitU234BEvJ.h36Dezxst3ZNh6wpWW.hXnTpL9Q15XO', 'admin', TRUE, FALSE) -- password: AdminAdmin0-
;

INSERT INTO role (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN')
;

INSERT INTO user_credentials_roles VALUES (1, 1), (2, 1), (2, 2)
;

-- workaround for sequence not being updated
select setval('hibernate_sequence', 1000);