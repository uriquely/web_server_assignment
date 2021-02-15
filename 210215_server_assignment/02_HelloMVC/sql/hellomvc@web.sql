--==========================================
-- 관리자(system) 계정
--==========================================
--web계정 생성
create user web
identified by web
default tablespace users;

grant connect, resource to web;


--==========================================
-- web 계정
--==========================================
create table member (
    member_id varchar2(15),                     --PK
    password varchar2(300) not null,            --필수
    member_name varchar2(30) not null,          --필수
    member_role char(1) default 'U' not null,   --필수
    gender char(1),
    birthday date,
    email varchar2(100),
    phone char(11) not null,                    --필수
    address varchar2(200),
    hobby varchar2(100),
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint ck_gender check(gender in ('M', 'F')),
    constraint ck_member_role check(member_role in ('U', 'A'))
);

--회원 추가
insert into member
values (
    'honggd', '1234', '홍길동', 'U', 'M', to_date('20000909','yyyymmdd'),
    'honggd@naver.com', '01012341234', '서울시 강남구', '운동,등산,독서', default
);

insert into member
values (
    'qwerty', '1234', '쿠어티', 'U', 'F', to_date('19900215','yyyymmdd'),
    'qwerty@naver.com', '01012341234', '서울시 송파구', '운동,등산', default
);

insert into member
values (
    'admin', '1234', '관리자', 'A', 'M', to_date('19801010','yyyymmdd'),
    'admin@naver.com', '01056785678', '서울시 관악구', '게임,독서', default
);

select * from member;
commit;

desc member;

update member 
set password = '1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==';

commit;
--1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==
--Dm6EiH9LY3KEe1YC8zINomgxhqUBPWHJzAxT5SqxoBvEWhVg+hyrsI08vt8SvDc+hWeOxptlgTyi48iGjE8INA==


--페이징
--rownum
select *
from (
        select rownum rnum, E.*
        from (
                select *
                from member
                order by enroll_date desc
            ) E
        ) E
where rnum between 11 and 20;

--row_number
select *
from (
        select M.*,
               row_number() over(order by enroll_date desc) rnum
        from member M
    ) M
where rnum between 11 and 20;

--select * from ( select M.*, row_number() over(order by enroll_date desc) rnum from member M ) M where rnum between 11 and 20

--페이징 검색
select *
from (
        select M.*,
               row_number() over(order by enroll_date desc) rnum
        from member M
        where member_id like '%a%'
    )
where rnum between 11 and 20;

--select * from ( select M.*, row_number() over(order by enroll_date desc) rnum from member M where # like ? ) where rnum between ? and ?

--------------------------------------------
-- 게시판
--------------------------------------------

--게시판생성
CREATE TABLE BOARD (
    BOARD_NO NUMBER,
    BOARD_TITLE VARCHAR2(50) NOT NULL,
    BOARD_WRITER VARCHAR2(15),
    BOARD_CONTENT VARCHAR2(2000) NOT NULL,
    BOARD_ORIGINAL_FILENAME VARCHAR2(200),
    BOARD_RENAMED_FILENAME VARCHAR2(200),
    BOARD_DATE DATE DEFAULT SYSDATE,
    BOARD_READ_COUNT NUMBER DEFAULT 0,
    CONSTRAINT PK_BOARD_NO PRIMARY KEY(BOARD_NO),
    CONSTRAINT FK_BOARD_WRITER FOREIGN KEY(BOARD_WRITER)
                                         REFERENCES MEMBER(MEMBER_ID)
                                         ON DELETE SET NULL
);

COMMENT ON TABLE BOARD IS '게시판테이블';
COMMENT ON COLUMN "BOARD"."BOARD_NO" IS '게시글번호';
COMMENT ON COLUMN "BOARD"."BOARD_TITLE" IS '게시글제목';
COMMENT ON COLUMN "BOARD"."BOARD_WRITER" IS '게시글작성자 아이디';
COMMENT ON COLUMN "BOARD"."BOARD_CONTENT" IS '게시글내용';
COMMENT ON COLUMN "BOARD"."BOARD_ORIGINAL_FILENAME" IS '첨부파일원래이름';
COMMENT ON COLUMN "BOARD"."BOARD_RENAMED_FILENAME" IS '첨부파일변경이름';
COMMENT ON COLUMN "BOARD"."BOARD_DATE" IS '게시글올린날짜';
COMMENT ON COLUMN "BOARD"."BOARD_READ_COUNT" IS '조회수';

--게시판시퀀스생성
CREATE SEQUENCE SEQ_BOARD_NO
START WITH 1
INCREMENT BY 1
NOMINVALUE
NOMAXVALUE
NOCYCLE
NOCACHE;
	
