with Visit (sin, placename, enterTime, exitTime) as (
        select  Entry.sin, 
                Entry.placename,
                Entry.time,
                min(Exit.time)
        from  Recon as Entry,
              Recon as Exit
        where Entry.sin = Exit.sin
          and Entry.placename = Exit.placename
          and Entry.method    = 'registry sign in'
          and Exit.method     = 'registry sign out'
          and Entry.time     <= Exit.time
        group by Entry.sin, Entry.placename, Entry.time
)
select  P.sin, P.name, T.time as testTime, V.enterTime, V.exitTime
from    Person as P, Test as T, Visit as V
where   P.sin        = T.sin
  and   T.sin        = V.sin
  and   T.testcentre = V.placename
  and   T.time      >= V.enterTime
  and   T.time      <= V.exitTime;