WITH    quest_char (login, name, day, realm, theme) as
        (SELECT DISTINCT v.login, v.name, a.day, a.realm, a.theme 
        FROM actor as a, visit as v
        WHERE a.login = v.login 
        AND a.realm = v.realm 
        AND a.day = v.day),

        check1 (companion1, fname, realm, companion2, lname) as
        (SELECT DISTINCT p1.login, p1.name, p1.realm, p2.login, p2.name
        FROM quest_char p1, quest_char p2
        WHERE p1.login < p2.login 
        AND p1.name <> p2.name 
        AND p1.realm = p2.realm 
        AND p1.theme = p2.theme 
        AND p1.day = p2.day)

SELECT *
FROM check1 as c
WHERE NOT EXISTS (	
                (SELECT day, realm, theme
                FROM quest_char
                WHERE login = c.companion1
                AND realm = c.realm
                AND name = c.fname)

                EXCEPT

                (SELECT day, realm, theme
                FROM quest_char
                WHERE login = c.companion2
                AND realm = c.realm 
                AND name = c.lname))
AND NOT EXISTS (
                (SELECT day, realm, theme
                FROM quest_char
                WHERE login = c.companion2
                AND realm = c.realm
                AND name = c.lname)

                EXCEPT

                (SELECT day, realm, theme
                FROM quest_char
                WHERE login = c.companion1
                AND name = c.fname
                AND realm = c.realm))

ORDER BY realm, companion1, fname, companion2, lname;