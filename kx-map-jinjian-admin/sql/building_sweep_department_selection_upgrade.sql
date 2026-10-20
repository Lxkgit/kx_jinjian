-- 已存在 building_sweep_report 表的环境执行一次。
ALTER TABLE building_sweep_report
    ADD COLUMN participant_dept_codes VARCHAR(2000) DEFAULT NULL
        COMMENT '参与部门编码，多个用逗号分隔'
        AFTER lead_dept_name;
