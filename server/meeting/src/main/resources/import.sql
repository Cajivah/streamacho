INSERT INTO room
  (id, organiser, name, description, start_at_date, status, closed, created_date, modified_date)
VALUES
  (1, 'malyjasiak', 'Test Room 1', 'Lorem impsum but a little bit longer', '2018-06-30 12:55:44.192501', 'PLANNED', false, '2018-04-30 12:55:44.192501', '2018-04-30 12:55:44.192501'),
  (2, 'malyjasiak', 'Test Room 2', 'Lorem impsum but a little bit longer', '2018-06-29 12:55:44.192501', 'PLANNED', false, '2018-03-30 12:55:44.192501', '2018-04-30 12:55:44.192501'),
  (3, 'admin', 'Test Room Admin', 'Lorem impsum but a little bit longer', '2018-06-20 12:55:44.192501', 'PLANNED', false, '2018-03-21 12:55:44.192501', '2018-03-21 12:55:44.192501')
;

INSERT INTO room_tags
  (room_id, tags)
VALUES
  (1, 'Programming'),
  (2, 'Life Style'),
  (2, 'Dance'),
  (2, 'Spain'),
  (3, 'Cooking'),
  (3, 'Baking')
;