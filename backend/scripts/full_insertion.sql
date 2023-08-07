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

/* Categories */

INSERT INTO "Category" (categoryID, categoryName)
VALUES (1, 'Education');

INSERT INTO "Category" (categoryID, categoryName)
VALUES (2, 'Animal');

INSERT INTO "Category" (categoryID, categoryName)
VALUES (3, 'University');

INSERT INTO "Category" (categoryID, categoryName)
VALUES (5, 'Friends');

INSERT INTO "Category" (categoryID, categoryName)
VALUES (6, 'Admin');

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
           'Community Rules', TO_DATE('2023-07-24 21:34:13', 'YYYY-MM-DD HH24:MI:SS'), 1, 4);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (4, 'Add me as a friend and I'' ll send you a surprise.',
           'Add friend!', TO_DATE('2023-07-24 21:39:52', 'YYYY-MM-DD HH24:MI:SS'), 2, 5);

INSERT INTO "Post" (postID, content, title, publishDate, categoryID, userID)
VALUES (5, 'I really don''t know what to post.', 'Something', TO_DATE('2023-07-24
    21:46:11', 'YYYY-MM-DD HH24:MI:SS'), 3, 1);