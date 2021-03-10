WITH    pass_quest_count(theme, total) AS
        (SELECT theme, COUNT(*)
        FROM quest
        WHERE succeeded IS NOT NULL
        GROUP BY theme
        ORDER BY theme
        ),

        all_quest_count(theme, total) AS
        (SELECT theme, COUNT(*)
        FROM quest
        GROUP BY theme
        ORDER BY theme
        )

SELECT DISTINCT p.theme, a.total
FROM pass_quest_count as p, all_quest_count as a
WHERE p.theme = a.theme
AND p.total = a.total
ORDER BY p.theme;