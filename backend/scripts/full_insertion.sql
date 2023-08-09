/* create tables */

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



/* insert tuples */

/* Users */

INSERT INTO "User" (userID, registrationDate, userName, email, userPassword)
  VALUES (1, TO_DATE('2023-07-24', 'YYYY-MM-DD'), 'Diana', 'diana@gmail.com',
'112233');

INSERT INTO "User" (userID, registrationDate, userName, email, userPassword)
  VALUES (2, TO_DATE('2023-07-24', 'YYYY-MM-DD'), 'Suying', 'suying@suying.com',
'ccygfxgfst');

INSERT INTO "User" (userID, registrationDate, userName, email, userPassword)
  VALUES (3, TO_DATE('2023-07-24', 'YYYY-MM-DD'), 'William', 'zwh@legendword.com',
'23454321');

INSERT INTO "User" (userID, registrationDate, userName, email, userPassword)
  VALUES (4, TO_DATE('2023-07-23', 'YYYY-MM-DD'), 'Admin', 'admin@admin.com',
'admin12345');

INSERT INTO "User" (userID, registrationDate, userName, email, userPassword)
  VALUES (5, TO_DATE('2023-07-23', 'YYYY-MM-DD'), 'Visitor', 'visitor',
'admin12345');

INSERT INTO "User" (userID, registrationDate, userName, email, userPassword)
  VALUES (6, TO_DATE('2023-07-25', 'YYYY-MM-DD'), 'Visitor2', 'visitor2',
        'asfh3yj');

INSERT INTO "User" (userID, registrationDate, userName, email, userPassword)
VALUES (7, TO_DATE('2023-07-26', 'YYYY-MM-DD'), 'Visitor3', 'visitor3',
        'asfh3yj');


/* Categories */

INSERT INTO "Category" (categoryID, categoryName)
VALUES (1, 'Education');

INSERT INTO "Category" (categoryID, categoryName)
VALUES (2, 'Animal');

INSERT INTO "Category" (categoryID, categoryName)
VALUES (3, 'University');

INSERT INTO "Category" (categoryID, categoryName)
VALUES (4, 'Friends');

INSERT INTO "Category" (categoryID, categoryName)
VALUES (5, 'Admin');

/* Writers */

INSERT INTO "Writer" (userID, alias) VALUES (1, 'Diana');

INSERT INTO "Writer" (userId, alias) VALUES (2, 'Suying');

INSERT INTO "Writer" (userId, alias) VALUES (3, 'William');

/* Posts */

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (1, 'CPSC 304 is such a great course!!!', 'My thoughts after the midterm',
        TO_DATE('2023-07-24 15:37:59', 'YYYY-MM-DD HH24:MI:SS'), 1, 1);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (2, 'ChatGPT is not going to replace jobs, but it certainly will make
teachers'' jobs much harder.', 'What about ChatGPT?', TO_DATE('2023-07-24 21:25:31',
'YYYY-MM-DD HH24:MI:SS'), 1, 1);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (3, 'Please do not post any offensive or inappropriate content, thanks!',
           'Community Rules', TO_DATE('2023-07-24 21:34:13', 'YYYY-MM-DD HH24:MI:SS'), 1, 3);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (4, 'Add me as a friend and I'' ll send you a surprise.',
           'Add friend!', TO_DATE('2023-07-24 21:39:52', 'YYYY-MM-DD HH24:MI:SS'), 4, 2);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (5, 'I really don''t know what to post.', 'Something', TO_DATE('2023-07-24
    21:46:11', 'YYYY-MM-DD HH24:MI:SS'), 3, 1);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (6, 'I want to do a CS master''s degree in Canada, hopefully master of science in computer science (MSC) at UBC. Does anyone know where I can find the past admission statistics for this program? ','CS Graduate Program Admission', TO_DATE('2023-08-02
    21:46:11', 'YYYY-MM-DD HH24:MI:SS'), 1, 2);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (7, 'How do I find my first coop job? Any advice would be appreciated.', 'UBC First Coop Term', TO_DATE('2023-07-29
    21:46:11', 'YYYY-MM-DD HH24:MI:SS'), 3, 2);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (8, 'Look at my dog! She is 30 years old', 'My Dog', TO_DATE('2023-07-30
    21:46:11', 'YYYY-MM-DD HH24:MI:SS'), 2, 2);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (9, 'Can the admin stop deleting my posts??????', 'DON''T DELETE MY POSTS', TO_DATE('2023-07-31
    21:46:11', 'YYYY-MM-DD HH24:MI:SS'), 5, 2);



/* Tags */

INSERT INTO "Tag" (tagID, tagName)
  VALUES (1, 'tipsforstudy');
INSERT INTO "Tag" (tagID, tagName)
  VALUES (2, 'catloversclub');
INSERT INTO "Tag" (tagID, tagName)
  VALUES (3, 'photooftheday');
INSERT INTO "Tag" (tagID, tagName)
  VALUES (4, 'ubc');
INSERT INTO "Tag" (tagID, tagName)
  VALUES (5, 'ChatGPT');

/* Collections */

INSERT INTO "Collection" (collectionID, title, userID)
  VALUES (1, 'CPSC304 Notes', 1);
INSERT INTO "Collection" (collectionID, title, userID)
  VALUES (2, 'CPSC221 Notes', 1);
INSERT INTO "Collection" (collectionID, title, userID)
  VALUES (3, 'CPSC213 Notes', 1);
INSERT INTO "Collection" (collectionID, title, userID)
  VALUES (4, 'CPSC210 Notes', 2);
INSERT INTO "Collection" (collectionID, title, userID)
  VALUES (5, 'CPSC110 Notes', 2);

/* Bookmark Lists */

INSERT INTO "BookmarkList" (userID, listID, listName) VALUES (1, 1, 'Reading List');
INSERT INTO "BookmarkList" (userID, listID, listName) VALUES (1, 2, 'Favourites');
INSERT INTO "BookmarkList" (userID, listID, listName) VALUES (3, 1, 'Interesting Things');
INSERT INTO "BookmarkList" (userID, listID, listName) VALUES (4, 1, 'Inappropriate Posts');
INSERT INTO "BookmarkList" (userID, listID, listName) VALUES (4, 2, 'Next Up');

/* Comments */

INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (1, 'This is fantastic!', NULL, 1, 1, TO_DATE('2023-07-24 21:40:52', 
'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (2, 'Thanks!', 1, 1, 3, TO_DATE('2023-07-24 22:13:42', 
'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (3, 'Of course sir.', NULL, 3, 3, TO_DATE('2023-07-25 00:23:10', 
'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (4, 'Yes', NULL, 3, 5, TO_DATE('2023-07-25 08:00:01', 
'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (5, 'This is just a comment..', 3, 3, 5, TO_DATE('2023-07-25 12:54:33', 
'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (6, 'I agree.', NULL, 8, 6, TO_DATE('2023-07-25 13:54:33', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (7, 'Yeah', NULL, 9, 6, TO_DATE('2023-07-26 12:42:33', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (9, 'Hi', NULL, 1, 7, TO_DATE('2023-07-26 12:43:33', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (10, 'Hi', NULL, 2, 7, TO_DATE('2023-07-26 12:43:33', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
  VALUES (11, 'Hi', NULL, 3, 7, TO_DATE('2023-07-26 12:43:33', 'YYYY-MM-DD HH24:MI:SS'));


/* Messages */

INSERT INTO "Message" (messageID, messageDate, content, fromUserID, toUserID)
 VALUES (1, TO_DATE('2023-07-25 00:25:10', 'YYYY-MM-DD HH24:MI:SS'), 'Hey', 1, 2);
INSERT INTO "Message" (messageID, messageDate, content, fromUserID, toUserID)
 VALUES (2, TO_DATE('2023-07-25 00:28:10', 'YYYY-MM-DD HH24:MI:SS'), 'Hi', 2, 1);
INSERT INTO "Message" (messageID, messageDate, content, fromUserID, toUserID)
 VALUES (3, TO_DATE('2023-07-25 01:20:10', 'YYYY-MM-DD HH24:MI:SS'), 'Sorry', 1, 3);
INSERT INTO "Message" (messageID, messageDate, content, fromUserID, toUserID)
 VALUES (4, TO_DATE('2023-07-25 05:25:10', 'YYYY-MM-DD HH24:MI:SS'), 'Thanks', 1, 4);
INSERT INTO "Message" (messageID, messageDate, content, fromUserID, toUserID)
 VALUES (5, TO_DATE('2023-07-25 00:25:10', 'YYYY-MM-DD HH24:MI:SS'), 'Bye', 1, 5);

/* Follows */

INSERT INTO "Follows" (followerID, followeeID) VALUES (1, 2);
INSERT INTO "Follows" (followerID, followeeID) VALUES (1, 3);
INSERT INTO "Follows" (followerID, followeeID) VALUES (2, 1);
INSERT INTO "Follows" (followerID, followeeID) VALUES (3, 1);
INSERT INTO "Follows" (followerID, followeeID) VALUES (2, 3);
INSERT INTO "Follows" (followerID, followeeID) VALUES (3, 2);

/* Reads */

INSERT INTO "Reads" (userID, postID, readDate) 
 VALUES (4, 1, TO_DATE('2023-07-25 22:13:42', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate) 
 VALUES (4, 2, TO_DATE('2023-07-25 21:13:42', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate) 
 VALUES (5, 1, TO_DATE('2023-07-25 23:13:42', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate) 
 VALUES (5, 2, TO_DATE('2023-07-25 20:13:42', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate) 
 VALUES (4, 3, TO_DATE('2023-07-25 22:18:42', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate)
 VALUES (1, 4, TO_DATE('2023-07-25 19:45:12', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate)
 VALUES (2, 7, TO_DATE('2023-07-25 18:30:21', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate)
 VALUES (6, 5, TO_DATE('2023-07-25 17:15:39', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate)
 VALUES (3, 8, TO_DATE('2023-07-25 15:20:18', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate)
 VALUES (4, 9, TO_DATE('2023-07-25 14:10:57', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "Reads" (userID, postID, readDate)
 VALUES (5, 6, TO_DATE('2023-07-25 13:05:36', 'YYYY-MM-DD HH24:MI:SS'));


/* CollectionContains */

INSERT INTO "CollectionContains" (collectionID, postID)
 VALUES (1, 1);
INSERT INTO "CollectionContains" (collectionID, postID)
 VALUES (1, 2);
INSERT INTO "CollectionContains" (collectionID, postID)
 VALUES (2, 1);
INSERT INTO "CollectionContains" (collectionID, postID)
 VALUES (3, 3);
INSERT INTO "CollectionContains" (collectionID, postID)
 VALUES (2, 3);

/* BookmarkListContains */

INSERT INTO "BookmarkListContains" (userID, listID, postID) VALUES (1, 1, 1);
INSERT INTO "BookmarkListContains" (userID, listID, postID) VALUES (1, 1, 2);
INSERT INTO "BookmarkListContains" (userID, listID, postID) VALUES (1, 2, 5);
INSERT INTO "BookmarkListContains" (userID, listID, postID) VALUES (4, 1, 4);
INSERT INTO "BookmarkListContains" (userID, listID, postID) VALUES (4, 2, 5);

/* PostHas */

INSERT INTO "PostHas" (postID, tagID) VALUES (1, 1);
INSERT INTO "PostHas" (postID, tagID) VALUES (2, 5);
INSERT INTO "PostHas" (postID, tagID) VALUES (2, 4);
INSERT INTO "PostHas" (postID, tagID) VALUES (4, 1);
INSERT INTO "PostHas" (postID, tagID) VALUES (4, 3);
INSERT INTO "PostHas" (postID, tagID) VALUES (4, 4);
