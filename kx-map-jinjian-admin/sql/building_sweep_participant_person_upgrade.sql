-- 已存在 building_sweep_report 表的环境执行一次。
ALTER TABLE building_sweep_report
    ADD COLUMN participant_person_user_names VARCHAR(2000) DEFAULT NULL
        COMMENT '参与排查登录账号，多个用逗号分隔'
        AFTER participant_dept_names;

-- 如果此前已经执行过旧版 participant_person_user_ids 脚本，请不要执行上面的 ADD，改执行：
-- ALTER TABLE building_sweep_report
--     CHANGE COLUMN participant_person_user_ids participant_person_user_names VARCHAR(2000) DEFAULT NULL
--     COMMENT '参与排查登录账号，多个用逗号分隔';
