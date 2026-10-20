-- 线索上报管理（MySQL 5.7+）
CREATE TABLE IF NOT EXISTS risk_clue_report (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    clue_no VARCHAR(32) NOT NULL COMMENT '线索编号',
    clue_source TINYINT UNSIGNED DEFAULT NULL COMMENT '线索来源：1预警系统推送、2扫楼清街发现、3群众举报、4监管通报、5舆情监测、6公安协查、7日常巡检、8新机构排查、9其他',
    clue_title VARCHAR(200) DEFAULT NULL COMMENT '线索标题',
    clue_type TINYINT UNSIGNED DEFAULT NULL COMMENT '线索类型：1非法集资、2金融诈骗、3违规借贷、4不法中介、5其他',
    risk_subject VARCHAR(200) DEFAULT NULL COMMENT '涉险主体',
    unified_social_credit_code VARCHAR(32) DEFAULT NULL COMMENT '统一社会信用代码',
    clue_description VARCHAR(1000) DEFAULT NULL COMMENT '线索描述',
    subject_address VARCHAR(500) DEFAULT NULL COMMENT '涉险主体地址',
    contact_name VARCHAR(100) DEFAULT NULL COMMENT '联系人',
    contact_phone VARCHAR(32) DEFAULT NULL COMMENT '联系电话',
    location_address VARCHAR(500) DEFAULT NULL COMMENT '定位地址',
    longitude DECIMAL(10,7) DEFAULT NULL COMMENT '经度',
    latitude DECIMAL(9,7) DEFAULT NULL COMMENT '纬度',
    coordinate_system VARCHAR(16) NOT NULL DEFAULT 'GCJ02' COMMENT '坐标系',
    risk_level TINYINT UNSIGNED DEFAULT NULL COMMENT '风险等级：1高、2中、3低',
    report_org_code VARCHAR(64) DEFAULT NULL COMMENT '报送机构编码快照',
    report_org_name VARCHAR(100) NOT NULL DEFAULT '' COMMENT '报送机构名称快照',
    reporter_user_name VARCHAR(64) DEFAULT NULL COMMENT '报送人账号',
    reporter_nick_name VARCHAR(100) NOT NULL DEFAULT '' COMMENT '报送人姓名',
    area_code VARCHAR(32) DEFAULT NULL COMMENT '所属行政区编码',
    area_full_name VARCHAR(500) DEFAULT NULL COMMENT '所属行政区全称',
    street_code VARCHAR(32) DEFAULT NULL COMMENT '所属街道编码',
    street_name VARCHAR(100) DEFAULT NULL COMMENT '所属街道名称',
    source_biz_type VARCHAR(32) DEFAULT NULL COMMENT '来源业务类型',
    source_biz_id VARCHAR(64) DEFAULT NULL COMMENT '来源业务记录ID',
    review_status TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '复核状态：0草稿、1待复核、2复核通过、3退回补正',
    review_round INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '当前复核轮次',
    transfer_status TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '移送状态：0待移送、1已移送',
    feedback_status TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '反馈状态：0待反馈、1已反馈',
    first_report_time DATETIME DEFAULT NULL COMMENT '首次正式报送时间',
    report_time DATETIME DEFAULT NULL COMMENT '最近一次报送时间',
    del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志：0正常、1删除',
    create_by VARCHAR(64) DEFAULT NULL COMMENT '创建人',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT NULL COMMENT '更新人',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '想·',
    PRIMARY KEY (id),
    UNIQUE KEY uk_risk_clue_no (clue_no),
    KEY idx_risk_clue_review_time (review_status, report_time),
    KEY idx_risk_clue_transfer_time (transfer_status, report_time),
    KEY idx_risk_clue_feedback_time (feedback_status, report_time),
    KEY idx_risk_clue_type (clue_type),
    KEY idx_risk_clue_level (risk_level),
    KEY idx_risk_clue_street (street_code),
    KEY idx_risk_clue_report_time (report_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线索上报主表';

CREATE TABLE IF NOT EXISTS risk_clue_file (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    clue_id BIGINT NOT NULL COMMENT '线索ID',
    file_type TINYINT UNSIGNED NOT NULL COMMENT '文件类型：1现场照片、2附件资料',
    original_name VARCHAR(255) NOT NULL COMMENT '原始文件名',
    object_name VARCHAR(500) NOT NULL COMMENT 'MinIO对象名称',
    file_url VARCHAR(1000) DEFAULT NULL COMMENT '文件访问地址',
    file_ext VARCHAR(20) DEFAULT NULL COMMENT '扩展名',
    file_size BIGINT DEFAULT NULL COMMENT '文件大小（字节）',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序',
    del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志：0正常、1删除',
    create_by VARCHAR(64) DEFAULT NULL COMMENT '上传人',
    create_time DATETIME NOT NULL COMMENT '上传时间',
    PRIMARY KEY (id),
    KEY idx_risk_clue_file (clue_id, file_type, del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线索附件表';

CREATE TABLE IF NOT EXISTS risk_clue_review_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    clue_id BIGINT NOT NULL COMMENT '线索ID',
    review_round INT UNSIGNED NOT NULL COMMENT '复核轮次',
    review_result TINYINT UNSIGNED NOT NULL COMMENT '复核结果：2复核通过、3退回补正',
    review_opinion VARCHAR(1000) NOT NULL COMMENT '复核意见',
    reviewer_user_id BIGINT DEFAULT NULL COMMENT '复核人用户ID',
    reviewer_user_name VARCHAR(64) DEFAULT NULL COMMENT '复核人账号',
    reviewer_nick_name VARCHAR(100) DEFAULT NULL COMMENT '复核人姓名',
    reviewer_dept_id BIGINT DEFAULT NULL COMMENT '复核部门ID',
    reviewer_dept_name VARCHAR(100) DEFAULT NULL COMMENT '复核部门名称',
    review_time DATETIME NOT NULL COMMENT '复核时间',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_risk_clue_review_round (clue_id, review_round),
    KEY idx_risk_clue_review_time (review_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线索复核记录表';

CREATE TABLE IF NOT EXISTS risk_clue_transfer_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    clue_id BIGINT NOT NULL COMMENT '线索ID',
    transfer_basis VARCHAR(1000) NOT NULL COMMENT '移送依据',
    receive_org_id BIGINT DEFAULT NULL COMMENT '接收单位ID',
    receive_org_code VARCHAR(64) DEFAULT NULL COMMENT '接收单位编码',
    receive_org_name VARCHAR(200) NOT NULL COMMENT '接收单位名称',
    transfer_method TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '移送方式：1电子送达、2纸质移交、3其他',
    document_info VARCHAR(1000) NOT NULL COMMENT '移送公文信息',
    transfer_time DATETIME NOT NULL COMMENT '移送时间',
    operator_user_id BIGINT DEFAULT NULL COMMENT '操作人用户ID',
    operator_user_name VARCHAR(64) DEFAULT NULL COMMENT '操作人账号',
    operator_nick_name VARCHAR(100) DEFAULT NULL COMMENT '操作人姓名',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_risk_clue_transfer (clue_id),
    KEY idx_risk_clue_transfer_time (transfer_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线索移送记录表';

CREATE TABLE IF NOT EXISTS risk_clue_feedback_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    clue_id BIGINT NOT NULL COMMENT '线索ID',
    feedback_org_name VARCHAR(200) DEFAULT NULL COMMENT '反馈单位',
    feedback_person VARCHAR(100) DEFAULT NULL COMMENT '反馈人',
    feedback_phone VARCHAR(32) DEFAULT NULL COMMENT '反馈人联系电话',
    feedback_content VARCHAR(2000) NOT NULL COMMENT '反馈内容',
    feedback_time DATETIME NOT NULL COMMENT '反馈时间',
    create_by VARCHAR(64) DEFAULT NULL COMMENT '录入人',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_risk_clue_feedback (clue_id, feedback_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线索反馈记录表';

-- 新增独立菜单，不修改已有线索、扫楼等菜单。
SET @risk_clue_parent_id = (
    SELECT menu_id FROM sys_menu
    WHERE menu_name IN ('打非工作', '网格化管理', '网格管理') AND menu_type = 'M'
    ORDER BY FIELD(menu_name, '打非工作', '网格化管理', '网格管理'), menu_id LIMIT 1
);
SET @risk_clue_menu_id = (
    SELECT menu_id FROM sys_menu
    WHERE perms = 'system:riskClue:list'
       OR component = 'module-gridManagement/views/crackdown/views/clue/index'
    ORDER BY menu_id LIMIT 1
);
SET @risk_clue_menu_id = IFNULL(@risk_clue_menu_id, (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu));

INSERT INTO sys_menu (
    menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
    menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT @risk_clue_menu_id, '线索上报管理', IFNULL(@risk_clue_parent_id, 0), 99, 'riskClueManagement',
       'module-gridManagement/views/crackdown/views/clue/index', NULL, 1, 0,
       'C', '0', '0', 'system:riskClue:list', 'list', 'admin', NOW(), '', NULL, '线索上报管理独立功能'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = @risk_clue_menu_id);

UPDATE sys_menu SET perms = 'system:riskClue:list'
WHERE menu_id = @risk_clue_menu_id
  AND component = 'module-gridManagement/views/crackdown/views/clue/index';

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu SELECT @next_menu_id, '线索查询', @risk_clue_menu_id, 1, '#', '', NULL, 1, 0, 'F', '0', '0',
    'system:riskClue:query', '#', 'admin', NOW(), '', NULL, ''
WHERE @risk_clue_menu_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:riskClue:query');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu SELECT @next_menu_id, '线索新增', @risk_clue_menu_id, 2, '#', '', NULL, 1, 0, 'F', '0', '0',
    'system:riskClue:add', '#', 'admin', NOW(), '', NULL, ''
WHERE @risk_clue_menu_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:riskClue:add');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu SELECT @next_menu_id, '线索修改', @risk_clue_menu_id, 3, '#', '', NULL, 1, 0, 'F', '0', '0',
    'system:riskClue:edit', '#', 'admin', NOW(), '', NULL, ''
WHERE @risk_clue_menu_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:riskClue:edit');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu SELECT @next_menu_id, '线索复核', @risk_clue_menu_id, 4, '#', '', NULL, 1, 0, 'F', '0', '0',
    'system:riskClue:review', '#', 'admin', NOW(), '', NULL, ''
WHERE @risk_clue_menu_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:riskClue:review');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu SELECT @next_menu_id, '线索移送', @risk_clue_menu_id, 5, '#', '', NULL, 1, 0, 'F', '0', '0',
    'system:riskClue:transfer', '#', 'admin', NOW(), '', NULL, ''
WHERE @risk_clue_menu_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:riskClue:transfer');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu SELECT @next_menu_id, '线索反馈', @risk_clue_menu_id, 6, '#', '', NULL, 1, 0, 'F', '0', '0',
    'system:riskClue:feedback', '#', 'admin', NOW(), '', NULL, ''
WHERE @risk_clue_menu_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:riskClue:feedback');

SET @next_menu_id = (SELECT IFNULL(MAX(menu_id), 0) + 1 FROM sys_menu);
INSERT INTO sys_menu SELECT @next_menu_id, '线索删除', @risk_clue_menu_id, 7, '#', '', NULL, 1, 0, 'F', '0', '0',
    'system:riskClue:remove', '#', 'admin', NOW(), '', NULL, ''
WHERE @risk_clue_menu_id IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'system:riskClue:remove');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT parent_role.role_id, child.menu_id
FROM sys_role_menu parent_role
JOIN sys_menu child ON child.parent_id = @risk_clue_menu_id AND child.menu_type = 'F'
WHERE parent_role.menu_id = @risk_clue_menu_id;
