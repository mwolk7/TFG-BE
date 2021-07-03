
/* ADD DEFAULT USER*/
INSERT INTO `USERS` (`CREATED_DATE`, `LAST_MODIFIED_DATE`, `VERSION`, `NAME`, `PASSWORD`, `USERNAME`) VALUES (NOW(), NOW(), '0', 'juampi', '123', 'juampi');

/* Add default project */
INSERT INTO `PROJECTS` (`CREATED_DATE`, `LAST_MODIFIED_DATE`, `VERSION`, `DESCRIPTION`, `NAME`) VALUES (NOW(), NOW(), '0', 'Test project', 'Test Project');

/* Add user project relation */
INSERT INTO `USER_PROJECTS` (`CREATED_DATE`, `LAST_MODIFIED_DATE`, `VERSION`, `PROJECT_ID`, `USER_ID`, ROLE) VALUES (NOW(), NOW(), '0', '1', '1', 'Owner');

