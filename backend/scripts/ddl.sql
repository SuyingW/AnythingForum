CREATE TABLE "Category" (
  categoryID INT PRIMARY KEY,
  categoryName VARCHAR2(50) UNIQUE NOT NULL
);

CREATE TABLE "User" (
  userID INT PRIMARY KEY,
  registrationDate DATE NOT NULL,
  userName VARCHAR2(20) NOT NULL,
  email VARCHAR2(320) NOT NULL,
  userPassword VARCHAR2(100) NOT NULL
);

CREATE TABLE "Post" (
  postID INT PRIMARY KEY, 
  content VARCHAR2(4000) NOT NULL, 
  title VARCHAR2(100)	NOT NULL, 
  publishDate DATE NOT NULL, 
  categoryID INT NOT NULL, 
  userID INT NOT NULL,
  FOREIGN KEY (categoryID) REFERENCES "Category",
  FOREIGN KEY (userID) REFERENCES "User"
    ON DELETE CASCADE
);

CREATE TABLE "Tag" (
  tagID INT PRIMARY KEY,
  tagName VARCHAR2(50) UNIQUE NOT NULL
);

CREATE TABLE "Collection" (
  collectionID INT PRIMARY KEY,
  title VARCHAR2(100) NOT NULL,
  userID INT NOT NULL,
  FOREIGN KEY (userID) REFERENCES "User"
    ON DELETE CASCADE
);

CREATE TABLE "Writer" (
  userID INT PRIMARY KEY,
  alias VARCHAR2(50) DEFAULT 'Anonymous',
  FOREIGN KEY (userID) REFERENCES "User"
    ON DELETE CASCADE
);

CREATE TABLE "BookmarkList" (
  userID INT,
  listID INT,
  listName VARCHAR2(50) NOT NULL,
  PRIMARY KEY (userID, listID),
  FOREIGN KEY (userID) REFERENCES "User"
    ON DELETE CASCADE
);

CREATE TABLE "Comment" (
  commentID INT PRIMARY KEY,
  content VARCHAR2(1000) NOT NULL,
  replyingToID INT,
  postID INT NOT NULL,
  userID INT NOT NULL,
  commentDate DATE NOT NULL,
  FOREIGN KEY (replyingToID) REFERENCES "Comment"
    ON DELETE CASCADE,
  FOREIGN KEY (postID) REFERENCES "Post"
    ON DELETE CASCADE,
  FOREIGN KEY (userID) REFERENCES "User"
    ON DELETE CASCADE
);

CREATE TABLE "Message" (
  messageID INT PRIMARY KEY,
  messageDate TIMESTAMP NOT NULL,
  content VARCHAR2(1000) NOT NULL,
  fromUserID INT NOT NULL,
  toUserID INT NOT NULL,
  FOREIGN KEY (fromUserID) REFERENCES "User"
    ON DELETE CASCADE,
  FOREIGN KEY (toUserID) REFERENCES "User"
    ON DELETE CASCADE
);

CREATE TABLE "Follows" (
  followerID INT,
  followeeID INT,
  PRIMARY KEY (followerID, followeeID),
  FOREIGN KEY (followerID) REFERENCES "User"
    ON DELETE CASCADE,
  FOREIGN KEY (followeeID) REFERENCES "User"
    ON DELETE CASCADE
);

CREATE TABLE "Reads" (
  userID INT,
  postID INT,
  readDate DATE NOT NULL,
  PRIMARY KEY (userID, postID),
  FOREIGN KEY (userID) REFERENCES "User"
    ON DELETE CASCADE,
  FOREIGN KEY (postID) REFERENCES "Post"
    ON DELETE CASCADE
);

CREATE TABLE "CollectionContains" (
  collectionID INT,
  postID INT,
  PRIMARY KEY (collectionID, postID),
  FOREIGN KEY (collectionID) REFERENCES "User"
    ON DELETE CASCADE,
  FOREIGN KEY (postID) REFERENCES "Post"
    ON DELETE CASCADE
);

CREATE TABLE "BookmarkListContains" (
  userID INT,
  listID INT,
  postID INT,
  PRIMARY KEY (userID, listID, postID),
  FOREIGN KEY (userID, listID) REFERENCES "BookmarkList"
    ON DELETE CASCADE,
  FOREIGN KEY (postID) REFERENCES "Post"
    ON DELETE CASCADE
);

CREATE TABLE "PostHas" (
  postID INT,
  tagID INT,
  PRIMARY KEY (postID, tagID),
  FOREIGN KEY (postID) REFERENCES "Post"
    ON DELETE CASCADE,
  FOREIGN KEY (tagID) REFERENCES "Tag"
    ON DELETE CASCADE
);

/* Views */

CREATE VIEW "ReadCount" AS
    SELECT P.postID, W.userID, COUNT(R.postID) AS viewCount FROM "Reads" R RIGHT JOIN "Post" P ON R.postID = P.postID
    RIGHT JOIN "Writer" W ON P.userID = W.userID
    GROUP BY P.postID, W.userID;
