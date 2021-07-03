
    drop table if exists BACKLOG_CREDENTIAL;

    drop table if exists BUG_REPORTER_CREDENTIALS;

    drop table if exists BUG_REPORTER_LOG;

    drop table if exists FILES;

    drop table if exists KC_SYNC_JOB_STATUS;

    drop table if exists PROJECT_VERSIONS;

    drop table if exists PROJECTS;

    drop table if exists TEST_SUITE;

    drop table if exists TEST_SUITE_RUNNER_STATISTIC;

    drop table if exists TEST_SUITE_RUNNERS;

    drop table if exists TestSuite_browsers;

    drop table if exists TestSuite_devices;

    drop table if exists TestSuite_os;

    drop table if exists TestSuiteRunner_browsers;

    drop table if exists TestSuiteRunner_devices;

    drop table if exists TestSuiteRunner_os;

    drop table if exists TOKEN_FILE;

    drop table if exists USER_PROJECTS;

    drop table if exists USERS;

    drop table if exists USERS_TEST_SUITE_RUNNERS;

    create table BACKLOG_CREDENTIAL (
       API_KEY varchar(255),
        URL varchar(255),
        ID bigint not null,
        primary key (ID)
    ) engine=MyISAM;

    create table BUG_REPORTER_CREDENTIALS (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        NAME varchar(255),
        USER_ID bigint not null,
        primary key (ID)
    ) engine=MyISAM;

    create table BUG_REPORTER_LOG (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        BUG_REPORTER_PROJECT varchar(255),
        ISSUE longtext,
        REQUEST_BODY longtext,
        BUG_REPORTER_CREDENTIAL_ID bigint,
        TEST_SUITE_RUNNER_ID bigint,
        USER_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table FILES (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        BUCKET varchar(255),
        CONTENT_TYPE varchar(255),
        DATA_TYPE varchar(255),
        NAME varchar(255),
        NAME_TO_STORAGE varchar(255),
        PROJECT_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table KC_SYNC_JOB_STATUS (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        JOB_NAME varchar(255),
        REFERENCE_DATE varchar(255),
        JOB_STATUS varchar(255),
        primary key (ID)
    ) engine=MyISAM;

    create table PROJECT_VERSIONS (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        PROJECT_VERSION varchar(255),
        PROJECT_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table PROJECTS (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        NAME varchar(255),
        TAG varchar(255),
        PARENT_PROJECT_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table TEST_SUITE (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        DESCRIPTION varchar(255),
        MODULES longtext,
        NAME varchar(255),
        TEST_SUITE_VERSION varchar(255),
        CREATOR_USER_ID bigint,
        PROJECT_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table TEST_SUITE_RUNNER_STATISTIC (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        BUGS_REPORTED integer,
        CNT integer,
        FAILED integer,
        FINISH_DATE datetime,
        NA integer,
        PASSED integer,
        REMAINING double precision,
        START_DATE datetime,
        TOTAL_MODULES integer,
        TOTAL_TEST_CASES integer,
        TOTAL_TIME bigint,
        TEST_SUITE_RUNNER_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table TEST_SUITE_RUNNERS (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        DUE_DATE datetime,
        FINISH_DATE datetime,
        MODULES longtext,
        NAME varchar(255),
        START_DATE datetime,
        STATUS varchar(255),
        CREATOR_USER_ID bigint,
        PROJECT_VERSION_ID bigint,
        STATISTICS bigint,
        TEST_SUIT_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table TestSuite_browsers (
       TestSuite_ID bigint not null,
        BROWSERS varchar(255)
    ) engine=MyISAM;

    create table TestSuite_devices (
       TestSuite_ID bigint not null,
        DEVICES varchar(255)
    ) engine=MyISAM;

    create table TestSuite_os (
       TestSuite_ID bigint not null,
        OS varchar(255)
    ) engine=MyISAM;

    create table TestSuiteRunner_browsers (
       TestSuiteRunner_ID bigint not null,
        BROWSERS varchar(255)
    ) engine=MyISAM;

    create table TestSuiteRunner_devices (
       TestSuiteRunner_ID bigint not null,
        DEVICES varchar(255)
    ) engine=MyISAM;

    create table TestSuiteRunner_os (
       TestSuiteRunner_ID bigint not null,
        OS varchar(255)
    ) engine=MyISAM;

    create table TOKEN_FILE (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        TOKEN varchar(255),
        PROJECT_ID bigint,
        USER_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table USER_PROJECTS (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        ROLE varchar(255),
        PROJECT_ID bigint,
        USER_ID bigint,
        primary key (ID)
    ) engine=MyISAM;

    create table USERS (
       ID bigint not null auto_increment,
        CREATED_DATE datetime,
        LAST_MODIFIED_DATE datetime,
        VERSION bigint not null DEFAULT 0,
        EMAIL varchar(255),
        EMAIL_VERIFIED bit,
        ENABLE bit,
        KEYCLOAK_KEY varchar(255),
        NAME varchar(255),
        PASSWORD varchar(255),
        SURNAME varchar(255),
        USERNAME varchar(255),
        primary key (ID)
    ) engine=MyISAM;

    create table USERS_TEST_SUITE_RUNNERS (
       TEST_SUIT_RUNNER_ID bigint not null,
        USER_ID bigint not null
    ) engine=MyISAM;

    alter table BACKLOG_CREDENTIAL 
       add constraint FKnge9l7xww5mg7vst4ec0i9ypp 
       foreign key (ID) 
       references BUG_REPORTER_CREDENTIALS (ID);

    alter table BUG_REPORTER_CREDENTIALS 
       add constraint FKafa1xjr7colqqbukocncgk58j 
       foreign key (USER_ID) 
       references USERS (ID);

    alter table BUG_REPORTER_LOG 
       add constraint FK8gxhumujfuli5jdtrtiv73j9l 
       foreign key (BUG_REPORTER_CREDENTIAL_ID) 
       references BUG_REPORTER_CREDENTIALS (ID);

    alter table BUG_REPORTER_LOG 
       add constraint FKp0brws8ev18rteyrt1lj2qhvr 
       foreign key (TEST_SUITE_RUNNER_ID) 
       references TEST_SUITE_RUNNERS (ID);

    alter table BUG_REPORTER_LOG 
       add constraint FKbmoh6apuefqxu4r9p7p7w1e77 
       foreign key (USER_ID) 
       references USERS (ID);

    alter table FILES 
       add constraint FKiffps8xygdsph0rnft37xbmd8 
       foreign key (PROJECT_ID) 
       references PROJECTS (ID);

    alter table PROJECT_VERSIONS 
       add constraint FK5ocqtxdore068gv1hiliw465h 
       foreign key (PROJECT_ID) 
       references PROJECTS (ID);

    alter table PROJECTS 
       add constraint FK627t6vril61bnxfqyjkdwdjwu 
       foreign key (PARENT_PROJECT_ID) 
       references PROJECTS (ID);

    alter table TEST_SUITE 
       add constraint FKku38nk8kqvxorlqd1qdltvny1 
       foreign key (CREATOR_USER_ID) 
       references USERS (ID);

    alter table TEST_SUITE 
       add constraint FKd57f2jb78e68cc4yqf65ys9o9 
       foreign key (PROJECT_ID) 
       references PROJECTS (ID);

    alter table TEST_SUITE_RUNNER_STATISTIC 
       add constraint FKdfm2h7tbt0f7pt1lajhs75q96 
       foreign key (TEST_SUITE_RUNNER_ID) 
       references TEST_SUITE_RUNNERS (ID);

    alter table TEST_SUITE_RUNNERS 
       add constraint FKksn39n8jc8wjaos8akoy90urb 
       foreign key (CREATOR_USER_ID) 
       references USERS (ID);

    alter table TEST_SUITE_RUNNERS 
       add constraint FK5h62htditb0si0n5sr6f2m6p7 
       foreign key (PROJECT_VERSION_ID) 
       references PROJECT_VERSIONS (ID);

    alter table TEST_SUITE_RUNNERS 
       add constraint FKo59mgob7wovoo9cttt1k9vufw 
       foreign key (STATISTICS) 
       references TEST_SUITE_RUNNER_STATISTIC (ID);

    alter table TEST_SUITE_RUNNERS 
       add constraint FK3c0hgqf3hnctpbf8t7bvspycc 
       foreign key (TEST_SUIT_ID) 
       references TEST_SUITE (ID);

    alter table TestSuite_browsers 
       add constraint FKk5s3kc5wn0pkk8nh2bjpplm0m 
       foreign key (TestSuite_ID) 
       references TEST_SUITE (ID);

    alter table TestSuite_devices 
       add constraint FKimsyk0quh26s1jsrdyjldrhqw 
       foreign key (TestSuite_ID) 
       references TEST_SUITE (ID);

    alter table TestSuite_os 
       add constraint FKlo9y0d00hf1emyet2i6iacfnx 
       foreign key (TestSuite_ID) 
       references TEST_SUITE (ID);

    alter table TestSuiteRunner_browsers 
       add constraint FK3cljf7m4dkh197kmgkpnlllo4 
       foreign key (TestSuiteRunner_ID) 
       references TEST_SUITE_RUNNERS (ID);

    alter table TestSuiteRunner_devices 
       add constraint FK3k1hk31cof64axy3aow7v88ax 
       foreign key (TestSuiteRunner_ID) 
       references TEST_SUITE_RUNNERS (ID);

    alter table TestSuiteRunner_os 
       add constraint FK4ri7me5yf6c099liic1y5a3i0 
       foreign key (TestSuiteRunner_ID) 
       references TEST_SUITE_RUNNERS (ID);

    alter table TOKEN_FILE 
       add constraint FKbg1hsr3gttq0lqnohv6mug259 
       foreign key (PROJECT_ID) 
       references PROJECTS (ID);

    alter table TOKEN_FILE 
       add constraint FKf0sbbgp521v47xutw14xdhnyv 
       foreign key (USER_ID) 
       references USERS (ID);

    alter table USER_PROJECTS 
       add constraint FKlmcyuse15cc6p19s2q4q1fhm6 
       foreign key (PROJECT_ID) 
       references PROJECTS (ID);

    alter table USER_PROJECTS 
       add constraint FKp6prn6biu7lql5x4u4m6u3h1b 
       foreign key (USER_ID) 
       references USERS (ID);

    alter table USERS_TEST_SUITE_RUNNERS 
       add constraint FKichqd1kv5hwf1fm5klmpe9v6v 
       foreign key (USER_ID) 
       references USERS (ID);

    alter table USERS_TEST_SUITE_RUNNERS 
       add constraint FKf64mmffneac4t6kl9seamacjd 
       foreign key (TEST_SUIT_RUNNER_ID) 
       references TEST_SUITE_RUNNERS (ID);
