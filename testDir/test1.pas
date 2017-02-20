PROGRAM dia4;
VAR
   a : INTEGER;
   PROCEDURE P1;
   VAR
       a : REAL;
       k : INTEGER;
       PROCEDURE P2;
           VAR
           a, z : INTEGER;
       BEGIN {P2}
           z := 777;
       END;  {P2}
   BEGIN {P1}
   END;  {P1}
BEGIN {dia4}
   a := 10;
   b := 5;
END.  {dia4}
