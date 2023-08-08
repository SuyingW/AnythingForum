INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
    VALUES (1, 'Why do you think it''s a great course?', null, 1, 3, TO_DATE('2023-07-28 15:37:59', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO "Comment" (commentID, content, replyingToID, postID, userID, commentDate)
VALUES (2, 'bc the mt is so easy!!!', 1, 1, 3, TO_DATE('2023-07-29 15:37:59', 'YYYY-MM-DD HH24:MI:SS'));