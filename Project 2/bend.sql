WITH avatarCount (login, avatars) as 
    (SELECT login, count(login)
    FROM avatar
    GROUP BY login
    HAVING count(login) > 1)

SELECT DISTINCT p.login, p.name, p.gender, c.avatars
FROM player as p, avatar as a, avatarCount as c
WHERE   p.login = a.login
AND     p.login = c.login
AND     p.gender != a.gender
ORDER BY p.login;