SELECT DISTINCT q.realm, q.day, q.theme
FROM quest as q, loot as l
WHERE   q.realm = l.realm
 and    q.day   = l.day
 and    q.theme = l.theme
 and    q.succeeded IS NOT NULL
 and    l.treasure ~ '.*Gold.*'
ORDER BY q.day, q.realm, q.theme;