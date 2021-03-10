WITH    quest_char (login, race, gender, day, realm, theme) AS
    (SELECT a.login, a.race, a.gender, q.day, q.realm, q.theme
    FROM    avatar as a, visit as v, actor as ac, quest as q
    WHERE   a.login = v.login
    AND     v.login = ac.login
    AND     a.name = v.name
    AND     v.day = ac.day
    AND     ac.day = q.day
    AND     v.realm = ac.realm
    AND     ac.realm = q.realm
    AND     ac.theme = q.theme
    AND     q.succeeded IS NOT NULL
    ORDER BY a.login, a.race, q.day, q.realm, q.theme),

    total_loot_gender(realm, race, gender, total) AS
    (SELECT qc.realm, qc.race, qc.gender, SUM(t.sql) as total
    FROM    loot as l, quest_char as qc, treasure as t
    WHERE   qc.day = l.day
    AND     qc.realm = l.realm
    AND     qc.theme = l.theme
    AND     qc.login = l.login
    AND     l.treasure = t.treasure
    GROUP BY qc.realm, qc.race, qc.gender
    ORDER BY qc.realm, qc.race, qc.gender),

    max_of_both(realm, race, total) AS
    (SELECT  t1.realm, t1.race, MAX(t1.total)
    FROM total_loot_gender as t1, total_loot_gender as t2
    GROUP BY t1.realm, t1.race
    ORDER BY t1.realm, t1.race)

SELECT t.realm, t.race, t.gender, t.total
FROM total_loot_gender as t, max_of_both as m
WHERE   t.realm = m.realm
AND     t.race = m.race
AND     t.total = m.total
ORDER BY t.realm, t.race, t.gender