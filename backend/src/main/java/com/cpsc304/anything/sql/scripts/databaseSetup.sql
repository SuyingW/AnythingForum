CREATE TABLE forumUser (
                      userID varchar2(50) PRIMARY KEY,
--                       registrationDate DATE NOT NULL,
                      userName varchar2(50) NOT NULL,
                      email varchar2(50) NOT NULL,
                      userPassWord varchar(50) NOT NULL
);

INSERT INTO forumUser(userID, userName, email, userPassWord)
VALUES (1, "wong so wing", "wongsowing@gmail.com", "gdhdmd");