/*Insert Operation*/
INSERT INTO "User" VALUES (?,?,?,?,?);
INSERT INTO "Writer" VALUES (?,?);
INSERT INTO "BookmarkList" VALUES (?, (SELECT COALESCE(MAX(listID), 0) FROM "BookmarkList" WHERE userID = ?) + 1, ?);

/*Delete Operation*/
DELETE FROM "Post" WHERE postID=(?);

/*Update Operation*/
UPDATE "Post" SET title= ?, content= ? WHERE postID= ?

/*Selection*/
SELECT userName FROM "User" WHERE email = ? AND userPassword = ?;
SELECT * FROM "Category";
SELECT * FROM "BookmarkList" WHERE userID = ?;

/*Projection*/
SELECT P.postID, P.title, P.publishDate, P.userID, P.categoryID, C.categoryName, W.alias FROM "Post" P, "BookmarkListContains" B, "Writer" W, "Category" C WHERE P.postID = B.postID AND P.userID = W.userID AND P.categoryID = C.categoryID AND B.userID = ? AND B.listID = ?;

/*Join*/
SELECT * FROM "User" LEFT JOIN "Writer" ON "User".userID = "Writer".userID;
SELECT * FROM "Post" JOIN "Writer" ON "Post".userID="Writer".userID JOIN "Category" ON "Post".categoryID="Category".categoryID;
SELECT * FROM "Post" JOIN "Writer" ON "Post".userID="Writer".userID JOIN "Category" ON "Post".categoryID="Category".categoryID WHERE postID=(?);
SELECT * FROM "Post" JOIN "Writer" ON "Post".userID="Writer".userID JOIN "Category" ON "Post".categoryID="Category".categoryID WHERE "Post".categoryID = (?);
SELECT * FROM "Collection" JOIN "Writer" on "Collection".userID = "Writer".userID;
SELECT * FROM "Post" JOIN "CollectionContains" ON "Post".postID = "CollectionContains".postID JOIN "Category" ON "Post".categoryID = "Category".categoryID JOIN "Writer" ON "Post".userID = "Writer".userID WHERE "CollectionContains".collectionID =(?);

/*Aggregation with Group By*/
SELECT "Category".categoryName, COUNT(*) AS "postCount" FROM "Category" JOIN "Post" ON "Post".categoryID="Category".categoryID GROUP BY "Category".categoryName;

/*Aggregation with Having*/

/* Nested Aggregation with Group By */
SELECT R.userID, AVG(R.viewCount) AS avgViews FROM "ReadCount" R WHERE R.userID IN (
    SELECT userID FROM "Writer" W, "Follows" F WHERE F.followeeID = W.userID GROUP BY W.userID HAVING COUNT(*) >= 2
) GROUP BY R.userID;

/*Division*/

