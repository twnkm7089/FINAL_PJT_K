-- db dummy information

USE `workout`;
DESC `workout`.`User`;
DESC `workout`.`Score`;
DESC `workout`.`Exercise`;

INSERT INTO `User` (id, nickname, password, email, introduction)
VALUES ('ssafy', '김운동', '123456789@', 'email@email.com', '안녕하세요, 운동 뉴비입니다.'),
('ssafy2', '헬스의신김유경', '123456789@', 'ykkim@hello.com', '삼성 신입사원 김유경입니다.');

INSERT INTO `Score` (userId, totalScore, todayScore, lastRegDate)
VALUES ('ssafy', 120, 12, current_timestamp()),
('ssafy2', 500, 40, current_timestamp());

INSERT INTO `Exercise` (name, score)
VALUES ('버피', 13),
('한손 팔굽혀펴기', 12),
('L싯풀업', 11),
('딥스', 10),
('피스톨스쿼트', 9),
('마운틴 클라이머', 8),
('턱걸이', 7),
('바이시클 메뉴버', 6),
('팔굽혀펴기', 5),
('플랭크(1분)', 4),
('브릿지', 3),
('윗몸일으키기', 2),
('맨몸 스쿼트', 1)
;

SELECT * FROM `User`;
SELECT * FROM `Score`;
SELECT * FROM `Exercise` ORDER BY score;
