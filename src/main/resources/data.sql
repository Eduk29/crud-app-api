INSERT INTO public.CRUD_APP_USER
(USERNAME_USER, PASSWORD_USER, LOGIN_COUNT_USER)
VALUES
    ('eshan.hammond', '12345678', 0),
    ('eleni.davis', '12345678', 0),
    ('leonardo.snider', '12345678', 0),
    ('darrell.sampson', '12345678', 0);

INSERT INTO public.CRUD_APP_PERSON
(NAME_PERSON, AGE_PERSON, BIRTHDAY_PERSON, ID_USER)
VALUES
    ('Eshan Hammond', 44, '1972-11-29T10:05:57Z', 1),
    ('Eleni Davis', 39, '1945-07-14T21:42:12Z', 2),
    ('Leonardo Snider', 41, '1990-06-29T01:45:19Z', 3),
    ('Darrell Sampson', 37, '1994-06-02T05:13:03Z', 4);