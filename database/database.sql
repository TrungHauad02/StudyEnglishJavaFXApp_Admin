CREATE
DATABASE englishforkids
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
Use
englishforkids;
CREATE TABLE ACCOUNT
(
    IdAccount CHAR(10) PRIMARY KEY,
    Username  VARCHAR(255) NOT NULL UNIQUE,
    Password  VARCHAR(255) NOT NULL,
    Role      ENUM('admin', 'student')
);

CREATE TABLE USER
(
    IdUser      CHAR(10) PRIMARY KEY,
    Fullname    NVARCHAR(255),
    Birthday    DATE,
    Status      BOOLEAN,
    Avatar      VARCHAR(255),
    School      NVARCHAR(255),
    Class       NVARCHAR(255),
    Address     NVARCHAR(255),
    EmailParent VARCHAR(255) UNIQUE,
    Score       INT DEFAULT 0,
    IdAccount   CHAR(10),
    FOREIGN KEY (IdAccount) REFERENCES ACCOUNT (IdAccount)
);


CREATE TABLE REMEMBERACCOUNT
(
    IdAccount CHAR(10)     NOT NULL,
    IPAddress VARCHAR(255) NOT NULL,
    primary key (IdAccount, IPAddress),
    FOREIGN KEY (IdAccount) REFERENCES ACCOUNT (IdAccount)
);


CREATE TABLE LESSON
(
    IdLesson    CHAR(10) PRIMARY KEY,
    Name        VARCHAR(255),
    Serial      INT,
    Description VARCHAR(255),
    CreateDay   DATETIME,
    Status      ENUM('lock', 'unlock','hidden')
);

CREATE TABLE SUBMITLESSON
(
    IdSubmitLesson CHAR(10) PRIMARY KEY,
    IdUser         CHAR(10),
    IdLesson       CHAR(10),
    Status         ENUM('uncomplete', 'complete'),
    FOREIGN KEY (IdUser) REFERENCES USER (IdUser),
    FOREIGN KEY (IdLesson) REFERENCES LESSON (IdLesson)
);

CREATE TABLE LESSONPART
(
    IdLessonPart CHAR(10) PRIMARY KEY,
    Name         VARCHAR(255),
    Content      VARCHAR(255),
    Type         ENUM('vocabulary', 'listening','speaking','quiz', 'grammar'),
    IdLesson     CHAR(10) NOT NULL,
    FOREIGN KEY (IdLesson) REFERENCES LESSON (IdLesson)
);

CREATE TABLE VOCABULARY
(
    IdVocabulary CHAR(10) PRIMARY KEY,
    Word         VARCHAR(255) NOT NULL,
    Mean         VARCHAR(255) NOT NULL,
    Image        LONGBLOB,
    Phonetic     VARCHAR(255)
);

CREATE TABLE VOCABULARYPART
(
    IdVocabulary CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART (IdLessonPart),
    FOREIGN KEY (IdVocabulary) REFERENCES VOCABULARY (IdVocabulary),
    PRIMARY KEY (IdVocabulary, IdLessonPart)
);

CREATE TABLE SYNONYMS
(
    IdVocabulary CHAR(10),
    IdSynonyms   CHAR(10),
    FOREIGN KEY (IdVocabulary) REFERENCES VOCABULARY (IdVocabulary),
    FOREIGN KEY (IdSynonyms) REFERENCES VOCABULARY (IdVocabulary),
    PRIMARY KEY (IdVocabulary, IdSynonyms)
);

CREATE TABLE ANTONYMS
(
    IdVocabulary CHAR(10),
    IdAntonyms   CHAR(10),
    FOREIGN KEY (IdVocabulary) REFERENCES VOCABULARY (IdVocabulary),
    FOREIGN KEY (IdAntonyms) REFERENCES VOCABULARY (IdVocabulary),
    PRIMARY KEY (IdVocabulary, IdAntonyms)
);

CREATE TABLE QUIZ
(
    IdQuiz CHAR(10) PRIMARY KEY,
    Title  VARCHAR(255),
    Status ENUM('lock', 'unlock','hidden')
);

CREATE TABLE QUIZPART
(
    IdQuiz       CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART (IdLessonPart),
    FOREIGN KEY (IdQuiz) REFERENCES QUIZ (IdQuiz),
    PRIMARY KEY (IdQuiz, IdLessonPart)
);

CREATE TABLE QUESTIONQUIZ
(
    IdQuestionQuiz CHAR(10) PRIMARY KEY,
    Content        VARCHAR(255),
    Serial         int,
    IdQuiz         CHAR(10),
    Image          LONGBLOB,
    FOREIGN KEY (IdQuiz) REFERENCES QUIZ (IdQuiz)
);

CREATE TABLE ANSWERQUIZ
(
    IdAnswerQuiz   CHAR(10) PRIMARY KEY,
    Content        VARCHAR(255),
    IsCorrect      BOOLEAN,
    IdQuestionQuiz CHAR(10) NOT NULL,
    FOREIGN KEY (IdQuestionQuiz) REFERENCES QUESTIONQUIZ (IdQuestionQuiz)
);

CREATE TABLE SUBMITQUIZ
(
    IdSubmitQuiz CHAR(10) PRIMARY KEY,
    Score        INT,
    StartTime    DATETIME,
    EndTime      DATETIME,
    IdQuiz       CHAR(10) NOT NULL,
    IdUser       CHAR(10) NOT NULL,
    FOREIGN KEY (IdUser) REFERENCES USER (IdUser),
    FOREIGN KEY (IdQuiz) REFERENCES QUIZ (IdQuiz)
);

CREATE TABLE ANSWERSUBMITQUIZ
(
    IdSubmitQuiz CHAR(10),
    IdAnswerQuiz CHAR(20),
    FOREIGN KEY (IdSubmitQuiz) REFERENCES SUBMITQUIZ (IdSubmitQuiz),
    FOREIGN KEY (IdAnswerQuiz) REFERENCES ANSWERQUIZ (IdAnswerQuiz),
    PRIMARY KEY (IdSubmitQuiz, IdAnswerQuiz)
);

CREATE TABLE LISTENING
(
    IdListening CHAR(10) PRIMARY KEY,
    CreateDay   DATETIME,
    Title       VARCHAR(255),
    Description VARCHAR(255),
    Video       LONGBLOB,
    Script      TEXT
);

CREATE TABLE LISTENINGPART
(
    IdListening  CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART (IdLessonPart),
    FOREIGN KEY (IdListening) REFERENCES LISTENING (IdListening),
    PRIMARY KEY (IdListening, IdLessonPart)
);

CREATE TABLE SPEAKING
(
    IdSpeaking CHAR(10) PRIMARY KEY,
    Title      VARCHAR(255),
    Content    TEXT,
    Example    LONGBLOB
);

CREATE TABLE SPEAKINGPART
(
    IdSpeaking   CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART (IdLessonPart),
    FOREIGN KEY (IdSpeaking) REFERENCES SPEAKING (IdSpeaking),
    PRIMARY KEY (IdSpeaking, IdLessonPart)
);

CREATE TABLE GRAMMAR
(
    IdGrammar CHAR(10) PRIMARY KEY,
    Title     VARCHAR(255),
    Content   TEXT,
    Rule      TEXT,
    Image     LONGBLOB,
    Example   TEXT
);

CREATE TABLE GRAMMARPART
(
    IdGrammar    CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART (IdLessonPart),
    FOREIGN KEY (IdGrammar) REFERENCES GRAMMAR (IdGrammar),
    PRIMARY KEY (IdGrammar, IdLessonPart)
);


DELIMITER
//

CREATE TRIGGER before_insert_account
    BEFORE INSERT
    ON ACCOUNT
    FOR EACH ROW
BEGIN
    DECLARE nextId INT;
    DECLARE newId CHAR(10);

    SELECT COUNT(*) + 1 INTO nextId FROM ACCOUNT;
    SET newId = CONCAT('acc', LPAD(nextId, 7, '0'));
    SET NEW.IdAccount = newId;
END//

DELIMITER;


DELIMITER
//

CREATE TRIGGER before_insert_user
    BEFORE INSERT
    ON USER
    FOR EACH ROW
BEGIN
    DECLARE account_id CHAR(10);
    DECLARE nextId INT;
    DECLARE newId CHAR(10);

    SELECT IdAccount
    INTO account_id
    FROM ACCOUNT
    ORDER BY IdAccount DESC LIMIT 1;

    SET NEW.IdAccount = account_id;

    SELECT COUNT(*) + 1 INTO nextId FROM USER;
    SET newId = CONCAT('user', LPAD(nextId, 6, '0'));
    SET NEW.IdUser = newId;
END//

DELIMITER;

DELIMITER
//

CREATE TRIGGER before_insert_submitquiz
    BEFORE INSERT
    ON SUBMITQUIZ
    FOR EACH ROW
BEGIN
    DECLARE last_id CHAR(10);
    DECLARE last_number INT;

    SELECT IdSubmitQuiz
    INTO last_id
    FROM SUBMITQUIZ
    ORDER BY IdSubmitQuiz DESC LIMIT 1;

    IF last_id IS NULL THEN
        SET NEW.IdSubmitQuiz = 'subq000001';
    ELSE
        SET last_number = CAST(SUBSTRING(last_id, 5) AS SIGNED) + 1;

        SET NEW.IdSubmitQuiz = CONCAT('subq', LPAD(last_number, 6, '0'));
END IF;
END
//

DELIMITER ;



INSERT INTO ACCOUNT (IdAccount, Username, Password, Role)
VALUES ('acc0000001', '1', '1', 'student');
INSERT INTO USER (IdUser, Fullname, Birthday, Status, Avatar, School, Class, Address, EmailParent, Score, IdAccount)
VALUES ('user000001', 'Nguyễn Văn A', '2018-01-01', 1, 'avatar.jpg', 'Trường A', 'Lớp 1A', '123 Đường ABC, TP HCM',
        'trunghauad02@gmail.com', 0, 'acc0000001');


/*
 chỉnh bảng chứa khóa ngoại tự động bị xóa
 */
use
englishforkids;
ALTER TABLE quizpart
    ADD CONSTRAINT fk_lessonpart_id
        FOREIGN KEY (IdLessonPart)
            REFERENCES lessonpart (IdLessonPart)
            ON DELETE CASCADE;
/*
-- Query: SELECT * FROM englishforkids.lesson
LIMIT 0, 1000
lesson lớp 1
-- Date: 2024-05-08 00:44
*/
SELECT *
FROM englishforkids.lesson
    INSERT
INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000001', 'Unit 1 – In the Park', 1, 'Từ vựng bảng chữ cái B.Miêu tả công viên gần nơi bé sống có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000002', 'Unit 2 –  In the dining room', 2,
        'Từ vựng bảng chữ cái C.Miêu tả phòng bếp nhà bé có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000003', 'Unit 3 – At the street market', 3,
        'Từ vựng bảng chữ cái A.Miêu tả khu chợ nơi bé sống có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000004', 'Unit 4 – In the bedroom', 4,
        'Từ vựng bảng chữ cái D.Miêu tả phòng ngủ của bé có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000005', 'Unit 5 – At the fish and chip shop', 5,
        'Từ vựng bảng chữ cái I.Miêu tả vùng biển có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000006', 'Unit 6 – On the farm', 6, 'Từ vựng bảng chữ cái E.Miêu tả trang trại có những từ vựng nào mới ',
        'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000007', 'Unit 7 – In the garden', 7,
        'Từ vựng bảng chữ cái G.Miêu tả sân vườn nhà bé có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000008', 'Unit 8 – In the school playground', 8,
        'Từ vựng bảng chữ cái H.Miêu tả trường bé có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000009', 'Unit 9 – In the shop', 9,
        'Từ vựng bảng chữ cái B, M, P.Miêu tả cửa hàng có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000010', 'Unit 10 – At the zoo', 10, 'Từ vựng bảng chữ cái M.Miêu tả sở thú có những từ vựng nào mới ',
        'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000011', 'Unit 11 – At the bus stop', 11,
        'Từ vựng bảng chữ cái và cụm chữ cái  B , S, TR,R  .Miêu tả trạm xe buýt đến trường có những từ vựng nào mới ',
        'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000012', 'Unit 12 – At the lake', 12,
        'Từ vựng bảng chữ cái L  .Miêu tả sông hồ gần nơi bé sống có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000013', 'Unit 13 – In the school canteen', 13,
        'Từ vựng bảng chữ cái N  .Miêu tả khu ăn uống ở trường bé có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000014', 'Unit 14 – In the toy shop', 14,
        'Từ vựng bảng chữ cái T .Miêu tả khu vui chơi của bé có những từ vựng nào mới ', 'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000015', 'Unit 15 – At the football match', 15,
        'Từ vựng bảng chữ cái F .Miêu tả trận bóng đá hoặc hoạt động thể thao bé được xem có những từ vựng nào mới ',
        'unlock');
INSERT INTO `` (`IdLesson`, `Name`, `Serial`, `Description`, `Status`)
VALUES ('Less000016', 'Unit 16 – At home', 16,
        'Từ vựng bảng chữ cái W .Miêu tả nhà của bé và các hoạt động của bé ở nhà được xem có những từ vựng nào mới ',
        'unlock');


/*
-- Query: SELECT * FROM englishforkids.lessonpart
LIMIT 0, 1000

thêm lessonpart 4 :quiz cho 16 lesson lớp 1
-- Date: 2024-05-08 01:25
*/
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt001_4', 'Quiz  Unit 1 – In the Park', 'Unit 1 – In the Park', 'quiz', 'Less000001');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt002_4', 'Quiz Unit 2 –  In the dining room ', 'Unit 2 –  In the dining room ', 'quiz', 'Less000002');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt003_4', 'Quiz  Unit 3 – At the street market', 'Unit 3 – At the street market', 'quiz', 'Less000003');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt004_4', 'Quiz  Unit 4 – In the bedroom', 'Unit 4 – In the bedroom', 'quiz', 'Less000004');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt005_4', 'Quiz Unit 5 – At the fish and chip shop', 'Unit 5 – At the fish and chip shop', 'quiz',
        'Less000005');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt006_4', 'Quiz Unit 6 – On the farm', 'Unit 6 – On the farm', 'quiz', 'Less000006');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt007_4', 'Quiz Unit 7 – In the garden', 'Unit 7 – In the garden', 'quiz', 'Less000007');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt008_4', 'Quiz Unit 8 – In the school playground', 'Unit 8 – In the school playground', 'quiz',
        'Less000008');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt009_4', 'Quiz Unit 9 – In the shop', 'Unit 9 – In the shop', 'quiz', 'Less000009');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt010_4', 'Quiz Unit 10 – At the zoo', 'Unit 10 – At the zoo', 'quiz', 'Less000010');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt011_4', 'Quiz Unit 11 – At the bus stop', 'Unit 11 – At the bus stop', 'quiz', 'Less000011');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt012_4', 'Quiz Unit 12 – At the lake', 'Unit 12 – At the lake', 'quiz', 'Less000012');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt013_4', 'Quiz Unit 13 – In the school canteen', 'Unit 13 – In the school canteen', 'quiz', 'Less000013');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt014_4', 'Quiz Unit 14 – In the toy shop', 'Unit 14 – In the toy shop', 'quiz', 'Less000014');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt015_4', 'Quiz Unit 15 – At the football match', 'Unit 15 – At the football match', 'quiz', 'Less000015');
INSERT INTO `` (`IdLessonPart`, `Name`, `Content`, `Type`, `IdLesson`)
VALUES ('LPrt016_4', 'Quiz Unit 16 – At home', 'Unit 16 – At home', 'quiz', 'Less000016');


/*
-- Query: SELECT * FROM englishforkids.quizpart
LIMIT 0, 1000

-- Date: 2024-05-08 01:33
*/
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz001', 'LPrt001_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz002', 'LPrt002_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz003', 'LPrt003_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz004', 'LPrt004_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz005', 'LPrt005_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz006', 'LPrt006_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz007', 'LPrt007_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz008', 'LPrt008_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz009', 'LPrt009_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz010', 'LPrt010_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz011', 'LPrt011_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz012', 'LPrt012_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz013', 'LPrt013_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz014', 'LPrt014_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz015', 'LPrt015_4');
INSERT INTO `` (`IdQuiz`, `IdLessonPart`)
VALUES ('Quiz016', 'LPrt016_4');


/*
-- Query: SELECT * FROM englishforkids.quiz
LIMIT 0, 1000

-- Date: 2024-05-08 01:54
*/
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz001', 'Quiz for Lesson 1 ', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz002', 'Quiz for Lesson 2', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz003', 'Quiz for Lesson 3', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz004', 'Quiz for Lesson 4', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz005', 'Quiz for Lesson 5', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz006', 'Quiz for Lesson 6', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz007', 'Quiz for Lesson 7', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz008', 'Quiz for Lesson 8', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz009', 'Quiz for Lesson 9', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz010', 'Quiz for Lesson 10', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz011', 'Quiz for Lesson 11', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz012', 'Quiz for Lesson 12', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz013', 'Quiz for Lesson 13', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz014', 'Quiz for Lesson 14', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz015', 'Quiz for Lesson 15', 'unlock');
INSERT INTO `` (`IdQuiz`, `Title`, `Status`)
VALUES ('Quiz016', 'Quiz for Lesson 16', 'unlock');
/*
-- Query: SELECT * FROM englishforkids.questionquiz
LIMIT 0, 1000

-- Date: 2024-05-08 01:55
*/
INSERT INTO `` (`IdQuestionQuiz`, `Content`, `Serial`, `IdQuiz`, `Image`)
VALUES ('QuesQ001', 'Chọn từ vựng thích hợp với ảnh sau :', 1, 'Quiz001', NULL);
INSERT INTO `` (`IdQuestionQuiz`, `Content`, `Serial`, `IdQuiz`, `Image`)
VALUES ('QuesQ002', 'Chọn từ vựng thích hợp với ảnh sau :', 2, 'Quiz001', NULL);
INSERT INTO `` (`IdQuestionQuiz`, `Content`, `Serial`, `IdQuiz`, `Image`)
VALUES ('QuesQ003', 'Chọn từ vựng thích hợp với ảnh sau :', 3, 'Quiz001', NULL);
INSERT INTO `` (`IdQuestionQuiz`, `Content`, `Serial`, `IdQuiz`, `Image`)
VALUES ('QuesQ004', 'Chọn từ vựng thích hợp với ảnh sau :', 4, 'Quiz001', NULL);
INSERT INTO `` (`IdQuestionQuiz`, `Content`, `Serial`, `IdQuiz`, `Image`)
VALUES ('QuesQ005', 'Chọn từ vựng thích hợp với ảnh sau :', 5, 'Quiz001', NULL);
INSERT INTO `` (`IdQuestionQuiz`, `Content`, `Serial`, `IdQuiz`, `Image`)
VALUES ('QuesQ006', 'Chọn từ vựng thích hợp với ảnh sau :', 6, 'Quiz001', NULL);
INSERT INTO `` (`IdQuestionQuiz`, `Content`, `Serial`, `IdQuiz`, `Image`)
VALUES ('QuesQ007', 'Chọn từ vựng thích hợp với ảnh sau :', 7, 'Quiz001', NULL);
/*
-- Query: SELECT * FROM englishforkids.answerquiz
LIMIT 0, 1000

-- Date: 2024-05-08 01:56
*/
INSERT INTO `` (`IdAnswerQuiz`, `Content`, `IsCorrect`, `IdQuestionQuiz`)
VALUES ('AnsQ001_1', 'ball', 1, 'QuesQ001');
INSERT INTO `` (`IdAnswerQuiz`, `Content`, `IsCorrect`, `IdQuestionQuiz`)
VALUES ('AnsQ001_2', 'Bill', 0, 'QuesQ001');
INSERT INTO `` (`IdAnswerQuiz`, `Content`, `IsCorrect`, `IdQuestionQuiz`)
VALUES ('AnsQ001_3', 'Book', 0, 'QuesQ001');
INSERT INTO `` (`IdAnswerQuiz`, `Content`, `IsCorrect`, `IdQuestionQuiz`)
VALUES ('AnsQ001_4', 'Ba', 0, 'QuesQ001');
INSERT INTO `` (`IdAnswerQuiz`, `Content`, `IsCorrect`, `IdQuestionQuiz`)
VALUES ('AnsQ002_1', 'ball', 0, 'QuesQ002');
INSERT INTO `` (`IdAnswerQuiz`, `Content`, `IsCorrect`, `IdQuestionQuiz`)
VALUES ('AnsQ002_2', 'Bill', 1, 'QuesQ002');
INSERT INTO `` (`IdAnswerQuiz`, `Content`, `IsCorrect`, `IdQuestionQuiz`)
VALUES ('AnsQ002_3', 'Book', 0, 'QuesQ002');
INSERT INTO `` (`IdAnswerQuiz`, `Content`, `IsCorrect`, `IdQuestionQuiz`)
VALUES ('AnsQ002_4', 'Ba', 0, 'QuesQ002');

//

use englishforkids;
DELIMITER
//

CREATE TRIGGER before_insert_quiz_sssss
    BEFORE INSERT
    ON Quiz
    FOR EACH ROW
BEGIN
    DECLARE last_number INT;

    SELECT MAX(SUBSTRING(IdQuiz, 5)) INTO last_number FROM Quiz;

    IF last_number IS NULL THEN
        SET NEW.IdQuiz = 'quiz000001';
    ELSE
        SET last_number = last_number + 1;
        SET NEW.IdQuiz = CONCAT('quiz', LPAD(last_number, 6, '0'));
END IF;
END
//

DELIMITER ;
ALTER TABLE `englishforkids`.`questionquiz`
DROP
FOREIGN KEY `questionquiz_ibfk_1`;
ALTER TABLE `englishforkids`.`questionquiz`
    ADD CONSTRAINT `questionquiz_ibfk_1`
        FOREIGN KEY (`IdQuiz`)
            REFERENCES `englishforkids`.`quiz` (`IdQuiz`)
            ON UPDATE CASCADE;
ALTER TABLE `englishforkids`.`quizpart`
DROP FOREIGN KEY `fk_lessonpart_id`;
ALTER TABLE `englishforkids`.`quizpart`
    ADD CONSTRAINT `fk_lessonpart_id`
        FOREIGN KEY (`IdLessonPart`)
            REFERENCES `englishforkids`.`lessonpart` (`IdLessonPart`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;
ALTER TABLE `englishforkids`.`quizpart`
DROP FOREIGN KEY `quizpart_ibfk_1`;
ALTER TABLE `englishforkids`.`quizpart`
    ADD CONSTRAINT `quizpart_ibfk_1`
        FOREIGN KEY (`IdLessonPart`)
            REFERENCES `englishforkids`.`lessonpart` (`IdLessonPart`)
            ON UPDATE CASCADE;
ALTER TABLE `englishforkids`.`answerquiz`
DROP FOREIGN KEY `answerquiz_ibfk_1`;
ALTER TABLE `englishforkids`.`answerquiz`
    ADD CONSTRAINT `answerquiz_ibfk_1`
        FOREIGN KEY (`IdQuestionQuiz`)
            REFERENCES `englishforkids`.`questionquiz` (`IdQuestionQuiz`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;
