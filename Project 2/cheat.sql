WITH cheat (login, day) as 
        (SELECT login, day
        FROM actor
        GROUP BY login, day
        HAVING count(login) > 1)

SELECT p.login, p.name, a.day, a.realm, a.theme
FROM    player as p, actor as a, cheat as c
WHERE   p.login = a.login
and     a.login = c.login
and     a.day = c.day
ORDER BY p.login, p.name, a.day, a.realm, a.theme;