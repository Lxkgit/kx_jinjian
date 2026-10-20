-- 扫楼上报管理（MySQL 5.7+）
CREATE TABLE IF NOT EXISTS building_sweep_report (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    report_no VARCHAR(32) NOT NULL COMMENT '排查编号',
    inspection_time DATETIME NOT NULL COMMENT '排查时间',
    lead_dept_code VARCHAR(64) DEFAULT NULL COMMENT '牵头部门编码',
    lead_dept_name VARCHAR(100) NOT NULL COMMENT '牵头部门名称',
    participant_dept_codes VARCHAR(2000) DEFAULT NULL COMMENT '参与部门编码，多个用逗号分隔',
    participant_dept_names VARCHAR(1000) DEFAULT NULL COMMENT '参与部门名称，多个用逗号分隔',
    participant_person_user_names VARCHAR(2000) DEFAULT NULL COMMENT '参与排查登录账号，多个用逗号分隔',
    participant_person_names VARCHAR(1000) DEFAULT NULL COMMENT '参与排查人员，多个用逗号分隔',
    province_code VARCHAR(20) DEFAULT NULL COMMENT '省编码',
    province_name VARCHAR(100) DEFAULT NULL COMMENT '省名称',
    city_code VARCHAR(20) DEFAULT NULL COMMENT '市编码',
    city_name VARCHAR(100) DEFAULT NULL COMMENT '市名称',
    district_code VARCHAR(20) DEFAULT NULL COMMENT '区县编码',
    district_name VARCHAR(100) DEFAULT NULL COMMENT '区县名称',
    street_code VARCHAR(20) DEFAULT NULL COMMENT '街道编码',
    street_name VARCHAR(100) NOT NULL COMMENT '街道名称',
    community_code VARCHAR(20) DEFAULT NULL COMMENT '社区编码',
    community_name VARCHAR(100) NOT NULL COMMENT '社区名称',
    area_full_name VARCHAR(500) NOT NULL COMMENT '完整排查区域',
    detail_address VARCHAR(500) NOT NULL COMMENT '详细地址',
    subject_types VARCHAR(32) NOT NULL COMMENT '主体类型：1商户、2小微企业、3办公室，多个用逗号分隔',
    covered_subject_count INT NOT NULL COMMENT '覆盖主体数',
    inspection_person_times INT NOT NULL COMMENT '排查人次',
    issue_summary TEXT COMMENT '排查发现问题简述',
    evidence_materials VARCHAR(2000) DEFAULT NULL COMMENT 'MinIO文件名称，多个用逗号分隔',
    reporter_user_name VARCHAR(64) DEFAULT NULL COMMENT '填报人工号',
    reporter_nick_name VARCHAR(100) NOT NULL COMMENT '填报人姓名',
    review_status TINYINT NOT NULL DEFAULT 1 COMMENT '复核状态：1待复核、2复核通过、3退回补正',
    review_opinion VARCHAR(1000) DEFAULT NULL COMMENT '复核意见',
    reviewer_user_name VARCHAR(64) DEFAULT NULL COMMENT '复核人工号',
    reviewer_nick_name VARCHAR(100) DEFAULT NULL COMMENT '复核人姓名',
    review_time DATETIME DEFAULT NULL COMMENT '复核时间',
    related_clue_no VARCHAR(64) DEFAULT NULL COMMENT '关联线索编号',
    report_time DATETIME NOT NULL COMMENT '上报时间',
    del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志：0正常、1删除',
    create_by VARCHAR(64) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_building_sweep_report_no (report_no),
    KEY idx_building_sweep_inspection_time (inspection_time),
    KEY idx_building_sweep_review_status (review_status),
    KEY idx_building_sweep_street_name (street_name),
    KEY idx_building_sweep_report_time (report_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='扫楼上报表';

-- 在“扫楼清街管理”下新增菜单。若目标环境菜单名不同，请先调整下面的查询条件。
SET @sweep_parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '扫楼清街管理' AND menu_type IN ('M', 'C') LIMIT 1);
SET @sweep_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);

INSERT INTO sys_menu (
    menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
    menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT @sweep_menu_id, '扫楼上报管理', @sweep_parent_id, 1, 'buildingSweepReport',
       'module-gridManagement/views/buildingSweepManagement/views/index', NULL, 1, 0,
       'C', '0', '0', 'system:buildingSweep:list', 'list', 'admin', NOW(), '', NULL, '扫楼上报管理菜单'
WHERE @sweep_parent_id IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:buildingSweep:list');

SET @sweep_menu_id = (SELECT menu_id FROM sys_menu WHERE perms = 'system:buildingSweep:list' LIMIT 1);

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu
SELECT @next_menu_id, '扫楼上报查询', @sweep_menu_id, 1, '#', '', NULL, 1, 0, 'F', '0', '0',
       'system:buildingSweep:query', '#', 'admin', NOW(), '', NULL, ''
WHERE @sweep_menu_id IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:buildingSweep:query');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu
SELECT @next_menu_id, '扫楼上报新增', @sweep_menu_id, 2, '#', '', NULL, 1, 0, 'F', '0', '0',
       'system:buildingSweep:add', '#', 'admin', NOW(), '', NULL, ''
WHERE @sweep_menu_id IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:buildingSweep:add');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu
SELECT @next_menu_id, '扫楼上报修改', @sweep_menu_id, 3, '#', '', NULL, 1, 0, 'F', '0', '0',
       'system:buildingSweep:edit', '#', 'admin', NOW(), '', NULL, ''
WHERE @sweep_menu_id IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:buildingSweep:edit');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu
SELECT @next_menu_id, '扫楼上报复核', @sweep_menu_id, 4, '#', '', NULL, 1, 0, 'F', '0', '0',
       'system:buildingSweep:review', '#', 'admin', NOW(), '', NULL, ''
WHERE @sweep_menu_id IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:buildingSweep:review');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu
SELECT @next_menu_id, '扫楼上报删除', @sweep_menu_id, 5, '#', '', NULL, 1, 0, 'F', '0', '0',
       'system:buildingSweep:remove', '#', 'admin', NOW(), '', NULL, ''
WHERE @sweep_menu_id IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:buildingSweep:remove');

-- 继承拥有父菜单的角色权限。
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT role_id, @sweep_menu_id FROM sys_role_menu WHERE menu_id = @sweep_parent_id;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT parent_role.role_id, child.menu_id
FROM sys_role_menu parent_role
JOIN sys_menu child ON child.parent_id = @sweep_menu_id AND child.menu_type = 'F'
WHERE parent_role.menu_id = @sweep_parent_id;
