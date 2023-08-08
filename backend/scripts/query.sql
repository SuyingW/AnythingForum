/* Nested Aggregation with Group By */

SELECT R.userID, AVG(R.viewCount) FROM "ReadCount" R WHERE R.userID IN (
        SELECT userID FROM "Writer" W, "Follows" F WHERE F.followeeID = W.userID GROUP BY W.userID HAVING COUNT(*) >= 2
    ) GROUP BY R.userID;
