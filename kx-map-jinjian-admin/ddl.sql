create table F_OCRM_F_CM_MKT_TEAM
(
	mkt_team_id BIGINT(19) not null,
	mkt_team_name VARCHAR(200),
	org_id VARCHAR(100),
	team_leader VARCHAR(100),
	create_date DATE(13),
	lead_telephone VARCHAR(100),
	team_status VARCHAR(100),
	create_user_id VARCHAR(100),
	create_user_name VARCHAR(100),
	create_user_org_id VARCHAR(100),
	team_leader_id VARCHAR(100),
	three_org_id VARCHAR(100),
	org_name VARCHAR(100),
	deptfirst VARCHAR(60),
	deptsecond VARCHAR(60),
	last_modify_date VARCHAR(10),
	last_modify_user VARCHAR(10),
	if_check VARCHAR(2)
);

comment on column F_OCRM_F_CM_MKT_TEAM.mkt_team_id is '营销团队编号';

comment on column F_OCRM_F_CM_MKT_TEAM.mkt_team_name is '营销团队名称';

comment on column F_OCRM_F_CM_MKT_TEAM.org_id is '所属机构';

comment on column F_OCRM_F_CM_MKT_TEAM.team_leader is '营销团队负责人';

comment on column F_OCRM_F_CM_MKT_TEAM.create_date is '创建日期';

comment on column F_OCRM_F_CM_MKT_TEAM.lead_telephone is '负责人手机号';

comment on column F_OCRM_F_CM_MKT_TEAM.team_status is '营销团队状态';

comment on column F_OCRM_F_CM_MKT_TEAM.create_user_id is '创建人ID';

comment on column F_OCRM_F_CM_MKT_TEAM.create_user_name is '创建人名称';

comment on column F_OCRM_F_CM_MKT_TEAM.create_user_org_id is '创建人所属机构';

comment on column F_OCRM_F_CM_MKT_TEAM.team_leader_id is '营销团队负责人ID';

comment on column F_OCRM_F_CM_MKT_TEAM.three_org_id is '三级机构';

comment on column F_OCRM_F_CM_MKT_TEAM.org_name is '所属机构名称';

comment on column F_OCRM_F_CM_MKT_TEAM.deptfirst is '一级部门';

comment on column F_OCRM_F_CM_MKT_TEAM.deptsecond is '二级部门';

comment on column F_OCRM_F_CM_MKT_TEAM.last_modify_date is '最近一次修改日期';

comment on column F_OCRM_F_CM_MKT_TEAM.last_modify_user is '最近一次修改人';

comment on column F_OCRM_F_CM_MKT_TEAM.if_check is '是否考核';

create unique index INDEX33654292
	on F_OCRM_F_CM_MKT_TEAM (mkt_team_id);

alter table F_OCRM_F_CM_MKT_TEAM
	add constraint CONS134252722
		primary key (mkt_team_id);

create table F_OCRM_F_CM_TEAM_CUST_MANAGER
(
	id BIGINT(19) not null,
	join_date DATE(13),
	user_id VARCHAR(100) not null,
	mkt_team_id BIGINT(19) not null,
	is_leader VARCHAR(1),
	create_user_id VARCHAR(100),
	last_modify_date VARCHAR(10),
	last_modify_user VARCHAR(10),
	position VARCHAR(100),
	enterdate VARCHAR(20)
);

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.id is '主键';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.join_date is '加入日期';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.user_id is '成员号';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.mkt_team_id is '所属营销团队ID';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.is_leader is '是否团队负责人ID';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.create_user_id is '创建人ID';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.last_modify_date is '最近一次修改日期';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.last_modify_user is '最近一次修改人';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.position is '岗位';

comment on column F_OCRM_F_CM_TEAM_CUST_MANAGER.enterdate is '入行日期';

create unique index INDEX33654290
	on F_OCRM_F_CM_TEAM_CUST_MANAGER (id);

alter table F_OCRM_F_CM_TEAM_CUST_MANAGER
	add constraint CONS134252721
		primary key (id);

create table anchor_change_log
(
	id BIGINT(19),
	anchor_id BIGINT(19),
	anchor_name VARCHAR(255),
	type INT(10),
	type_name VARCHAR(255),
	old_address VARCHAR(255),
	old_address_type_code INT(10),
	old_address_type VARCHAR(255),
	new_address VARCHAR(255),
	new_address_type_code INT(10),
	new_address_type VARCHAR(255),
	create_user VARCHAR(255),
	create_time TIMESTAMP(29)
);

comment on table anchor_change_log is '锚点变更记录';

comment on column anchor_change_log.id is '主键';

comment on column anchor_change_log.anchor_id is '锚点id';

comment on column anchor_change_log.anchor_name is '锚点名称';

comment on column anchor_change_log.type is '锚点类型代码';

comment on column anchor_change_log.type_name is '锚点类型名称';

comment on column anchor_change_log.old_address is '原网格地址';

comment on column anchor_change_log.old_address_type_code is '原网格地址类型代码';

comment on column anchor_change_log.old_address_type is '原网格地址类型';

comment on column anchor_change_log.new_address is '新网格地址';

comment on column anchor_change_log.new_address_type_code is '新网格地址类型代码';

comment on column anchor_change_log.new_address_type is '新网格地址类型';

comment on column anchor_change_log.create_user is '操作人';

comment on column anchor_change_log.create_time is '调整时间';

create unique index INDEX33654489
	on anchor_change_log (id);

alter table anchor_change_log
	add constraint CONS134252831
		primary key (id);

create table anchor_point
(
	id BIGINT(19),
	customer_id VARCHAR(8188),
	customer_nm VARCHAR(8188),
	identity VARCHAR(8188),
	TYPE VARCHAR(8188),
	anchor_name VARCHAR(8188),
	anchor_type_code INT(10),
	anchor_type VARCHAR(8188),
	address_desc VARCHAR(8188),
	update_time TIMESTAMP(36,6),
	lon DECIMAL(0),
	lat DECIMAL(0),
	address_type INT(10),
	belong_grid_name VARCHAR(8188),
	belong_grid VARCHAR(8188),
	belong_grid_type INT(10),
	area_code VARCHAR(8188),
	belong_org_code VARCHAR(8188),
	belong_org_name VARCHAR(8188),
	customer_phone VARCHAR(8188),
	create_time TIMESTAMP(36,6),
	belong_manager_code VARCHAR(8188),
	belong_manager_name VARCHAR(8188),
	grid_manager VARCHAR(8188)
);

create unique index INDEX33654512
	on anchor_point (id);

alter table anchor_point
	add constraint CONS134252842
		primary key (id);

create table building_anchor_point
(
	id BIGINT(19),
	building_name VARCHAR(255),
	building_type TINYINT(3),
	building_province VARCHAR(50),
	building_city VARCHAR(50),
	building_area VARCHAR(50),
	building_street VARCHAR(50),
	building_address VARCHAR(100),
	lon DEC(18,8),
	lat DEC(18,8),
	floor_num INT(10),
	unit_num INT(10),
	household_num INT(10),
	belong_grid VARCHAR(30),
	belong_grid_name VARCHAR(255),
	network_code VARCHAR(255)
);

comment on table building_anchor_point is '楼宇锚点';

comment on column building_anchor_point.id is '楼宇锚点id';

comment on column building_anchor_point.building_name is '楼宇名称';

comment on column building_anchor_point.building_type is '楼宇类型';

comment on column building_anchor_point.building_province is '省';

comment on column building_anchor_point.building_city is '市';

comment on column building_anchor_point.building_area is '区';

comment on column building_anchor_point.building_street is '街道';

comment on column building_anchor_point.building_address is '详细地址';

comment on column building_anchor_point.lon is '经度';

comment on column building_anchor_point.lat is '纬度';

comment on column building_anchor_point.floor_num is '层数';

comment on column building_anchor_point.unit_num is '单元数';

comment on column building_anchor_point.household_num is '户数';

comment on column building_anchor_point.belong_grid is '归属网格';

comment on column building_anchor_point.belong_grid_name is '归属网格名称';

comment on column building_anchor_point.network_code is '省市区级联编码';

create unique index INDEX33654261
	on building_anchor_point (id);

alter table building_anchor_point
	add constraint CONS134252706
		primary key (id);

create table business_opportunity
(
	id BIGINT(19),
	name VARCHAR(255),
	identity VARCHAR(255)
		constraint idx_identity
			unique,
	type VARCHAR(255),
	business_name VARCHAR(255),
	business_extend VARCHAR(255),
	business_status VARCHAR(255),
	business_resource VARCHAR(255),
	common_status VARCHAR(255),
	create_by VARCHAR(255),
	execute_manager_name VARCHAR(255),
	execute_manager_code VARCHAR(255),
	execute_network_name VARCHAR(255),
	execute_network_code VARCHAR(255),
	start_time TIMESTAMP(29),
	end_time TIMESTAMP(29),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on column business_opportunity.id is '商机id';

comment on column business_opportunity.name is '客户名称';

comment on column business_opportunity.identity is '证件号/统一社会信用代码';

comment on column business_opportunity.type is '客户类型 1-个人 2-企业 3-商户';

comment on column business_opportunity.business_name is '商机名称';

comment on column business_opportunity.business_extend is '业务扩展方向 0-潜客扩展 1-信贷提升  2-存量活客';

comment on column business_opportunity.business_status is '商机状态 0-营销中 1-已完成 2-已结束';

comment on column business_opportunity.business_resource is '商机来源 0-手工创建 1-信贷系统 2-外部接入';

comment on column business_opportunity.common_status is '公共池状态 1-机构公共池 2-总行公共池 3-已分配';

comment on column business_opportunity.create_by is '商机发布人';

comment on column business_opportunity.execute_manager_name is '执行客户经理名称';

comment on column business_opportunity.execute_manager_code is '执行客户经理编号';

comment on column business_opportunity.execute_network_name is '执行机构名称';

comment on column business_opportunity.execute_network_code is '执行机构编号';

comment on column business_opportunity.start_time is '商机开始时间';

comment on column business_opportunity.end_time is '商机结束时间';

comment on column business_opportunity.create_time is '创建时间';

comment on column business_opportunity.update_time is '更新时间';

create unique index INDEX33654239
	on business_opportunity (id);

alter table business_opportunity
	add constraint CONS134252699
		primary key (id);

create table business_product_info
(
	id BIGINT(19) not null,
	business_code VARCHAR(50),
	business_status TINYINT(3),
	business_short_name VARCHAR(50),
	business_name VARCHAR(100),
	business_nature TINYINT(3),
	busibess_type TINYINT(3),
	industry_type TINYINT(3),
	busibess_category TINYINT(3),
	manager_work_code VARCHAR(50),
	manager_name VARCHAR(50),
	settlement_way TINYINT(3),
	entry_way VARCHAR(50),
	rate_name VARCHAR(50),
	settlement_card VARCHAR(100),
	reduction_amount_used DEC(10,2),
	reduction_amount DEC(10,2),
	credit_card_limit_day DEC(10,2),
	credit_card_limit_unit DEC(10,2),
	credit_card_limit_month DEC(10,2),
	debit_card_limit_day DEC(10,2),
	debit_card_limit_unit DEC(10,2),
	debit_card_limit_month DEC(10,2)
);

comment on table business_product_info is '商户产品信息';

comment on column business_product_info.id is '主键id';

comment on column business_product_info.business_code is '商户编号';

comment on column business_product_info.business_status is '商户状态';

comment on column business_product_info.business_short_name is '商户简称';

comment on column business_product_info.business_name is '商户全称';

comment on column business_product_info.business_nature is '商户性质';

comment on column business_product_info.busibess_type is '商户类型';

comment on column business_product_info.industry_type is '行业类型';

comment on column business_product_info.busibess_category is '商户类别';

comment on column business_product_info.manager_work_code is '客户经理工号';

comment on column business_product_info.manager_name is '客户经理姓名';

comment on column business_product_info.settlement_way is '结算方式';

comment on column business_product_info.entry_way is '入账方式';

comment on column business_product_info.rate_name is '费率名称';

comment on column business_product_info.settlement_card is '结算卡号';

comment on column business_product_info.reduction_amount_used is '当前减免已使用金额';

comment on column business_product_info.reduction_amount is '当前减免金额';

comment on column business_product_info.credit_card_limit_day is '信用卡日累计限额';

comment on column business_product_info.credit_card_limit_unit is '信用卡单笔限额';

comment on column business_product_info.credit_card_limit_month is '信用卡月累计限额';

comment on column business_product_info.debit_card_limit_day is '借记卡日累计限额';

comment on column business_product_info.debit_card_limit_unit is '借记卡单笔限额';

comment on column business_product_info.debit_card_limit_month is '借记卡月累计限额';

create unique index INDEX33654263
	on business_product_info (id);

alter table business_product_info
	add constraint CONS134252707
		primary key (id);

create table config_info
(
	id BIGINT(19),
	data_id VARCHAR(255) not null,
	group_id VARCHAR(255),
	content CLOB not null,
	md5 VARCHAR(32),
	gmt_create TIMESTAMP(29) default CURRENT_TIMESTAMP() not null,
	gmt_modified TIMESTAMP(29) default CURRENT_TIMESTAMP() not null,
	src_user TEXT(max),
	src_ip VARCHAR(50),
	app_name VARCHAR(128),
	tenant_id VARCHAR(128) default '',
	c_desc VARCHAR(256),
	c_use VARCHAR(64),
	effect VARCHAR(64),
	type VARCHAR(64),
	c_schema TEXT(max),
	encrypted_data_key TEXT(max),
	constraint INDEX33654266
		unique (data_id, group_id, tenant_id)
);

comment on table config_info is 'config_info';

comment on column config_info.id is 'id';

comment on column config_info.data_id is 'data_id';

comment on column config_info.content is 'content';

comment on column config_info.md5 is 'md5';

comment on column config_info.gmt_create is '创建时间';

comment on column config_info.gmt_modified is '修改时间';

comment on column config_info.src_user is 'source user';

comment on column config_info.src_ip is 'source ip';

comment on column config_info.tenant_id is '租户字段';

comment on column config_info.encrypted_data_key is '秘钥';

create unique index INDEX33654265
	on config_info (id);

alter table config_info
	add constraint CONS134252708
		primary key (id);

create table config_info_aggr
(
	id BIGINT(19),
	data_id VARCHAR(255) not null,
	group_id VARCHAR(255) not null,
	datum_id VARCHAR(255) not null,
	content CLOB not null,
	gmt_modified TIMESTAMP(29) not null,
	app_name VARCHAR(128),
	tenant_id VARCHAR(128) default '',
	constraint INDEX33654269
		unique (data_id, group_id, tenant_id, datum_id)
);

comment on table config_info_aggr is '增加租户字段';

comment on column config_info_aggr.id is 'id';

comment on column config_info_aggr.data_id is 'data_id';

comment on column config_info_aggr.group_id is 'group_id';

comment on column config_info_aggr.datum_id is 'datum_id';

comment on column config_info_aggr.content is '内容';

comment on column config_info_aggr.gmt_modified is '修改时间';

comment on column config_info_aggr.tenant_id is '租户字段';

create unique index INDEX33654268
	on config_info_aggr (id);

alter table config_info_aggr
	add constraint CONS134252710
		primary key (id);

create table config_info_beta
(
	id BIGINT(19),
	data_id VARCHAR(255) not null,
	group_id VARCHAR(128) not null,
	app_name VARCHAR(128),
	content CLOB not null,
	beta_ips VARCHAR(1024),
	md5 VARCHAR(32),
	gmt_create TIMESTAMP(29) default CURRENT_TIMESTAMP() not null,
	gmt_modified TIMESTAMP(29) default CURRENT_TIMESTAMP() not null,
	src_user TEXT(max),
	src_ip VARCHAR(50),
	tenant_id VARCHAR(128) default '',
	encrypted_data_key TEXT(max),
	constraint INDEX33654272
		unique (data_id, group_id, tenant_id)
);

comment on table config_info_beta is 'config_info_beta';

comment on column config_info_beta.id is 'id';

comment on column config_info_beta.data_id is 'data_id';

comment on column config_info_beta.group_id is 'group_id';

comment on column config_info_beta.app_name is 'app_name';

comment on column config_info_beta.content is 'content';

comment on column config_info_beta.beta_ips is 'betaIps';

comment on column config_info_beta.md5 is 'md5';

comment on column config_info_beta.gmt_create is '创建时间';

comment on column config_info_beta.gmt_modified is '修改时间';

comment on column config_info_beta.src_user is 'source user';

comment on column config_info_beta.src_ip is 'source ip';

comment on column config_info_beta.tenant_id is '租户字段';

comment on column config_info_beta.encrypted_data_key is '秘钥';

create unique index INDEX33654271
	on config_info_beta (id);

alter table config_info_beta
	add constraint CONS134252712
		primary key (id);

create table config_info_tag
(
	id BIGINT(19),
	data_id VARCHAR(255) not null,
	group_id VARCHAR(128) not null,
	tenant_id VARCHAR(128) default '',
	tag_id VARCHAR(128) not null,
	app_name VARCHAR(128),
	content CLOB not null,
	md5 VARCHAR(32),
	gmt_create TIMESTAMP(29) default CURRENT_TIMESTAMP() not null,
	gmt_modified TIMESTAMP(29) default CURRENT_TIMESTAMP() not null,
	src_user TEXT(max),
	src_ip VARCHAR(50),
	constraint INDEX33654275
		unique (data_id, group_id, tenant_id, tag_id)
);

comment on table config_info_tag is 'config_info_tag';

comment on column config_info_tag.id is 'id';

comment on column config_info_tag.data_id is 'data_id';

comment on column config_info_tag.group_id is 'group_id';

comment on column config_info_tag.tenant_id is 'tenant_id';

comment on column config_info_tag.tag_id is 'tag_id';

comment on column config_info_tag.app_name is 'app_name';

comment on column config_info_tag.content is 'content';

comment on column config_info_tag.md5 is 'md5';

comment on column config_info_tag.gmt_create is '创建时间';

comment on column config_info_tag.gmt_modified is '修改时间';

comment on column config_info_tag.src_user is 'source user';

comment on column config_info_tag.src_ip is 'source ip';

create unique index INDEX33654274
	on config_info_tag (id);

alter table config_info_tag
	add constraint CONS134252714
		primary key (id);

create table config_tags_relation
(
	id BIGINT(19) not null,
	tag_name VARCHAR(128) not null,
	tag_type VARCHAR(64),
	data_id VARCHAR(255) not null,
	group_id VARCHAR(128) not null,
	tenant_id VARCHAR(128) default ''
		constraint idx_tenant_id
			unique,
	nid BIGINT(19),
	constraint INDEX33654253
		unique (id, tag_name, tag_type)
);

comment on table config_tags_relation is 'config_tag_relation';

comment on column config_tags_relation.id is 'id';

comment on column config_tags_relation.tag_name is 'tag_name';

comment on column config_tags_relation.tag_type is 'tag_type';

comment on column config_tags_relation.data_id is 'data_id';

comment on column config_tags_relation.group_id is 'group_id';

comment on column config_tags_relation.tenant_id is 'tenant_id';

create unique index INDEX33654252
	on config_tags_relation (nid);

alter table config_tags_relation
	add constraint CONS134252702
		primary key (nid);

create table credit_customer_info
(
	id BIGINT(19),
	customer_nm VARCHAR(8188),
	customer_name VARCHAR(8188),
	customer_phone VARCHAR(8188),
	customer_type VARCHAR(8188),
	customer_address VARCHAR(8188),
	customer_identity VARCHAR(8188),
	customer_level VARCHAR(8188),
	credit_amount DEC(30,2),
	using_credit_amount DEC(30,2),
	five_level VARCHAR(8188),
	belong_grid_code VARCHAR(8188),
	belong_grid_name VARCHAR(8188),
	grid_manager VARCHAR(8188),
	credit_manager VARCHAR(8188),
	branch_bank VARCHAR(8188),
	separate_bank VARCHAR(8188),
	guarantee_method VARCHAR(8188),
	using_type INT(10),
	is_in_arrears INT(10),
	exchange_method INT(10),
	product_label VARCHAR(8188),
	offline_attract VARCHAR(8188),
	credit_promotion VARCHAR(8188),
	other_label INT(10),
	delete_flag INT(10),
	risk_type VARCHAR(8188),
	village_loan_type VARCHAR(8188),
	is_product INT(10),
	gender VARCHAR(8188),
	birthday VARCHAR(8188),
	age INT(10),
	marital_status VARCHAR(8188),
	JOB VARCHAR(8188),
	work_unit VARCHAR(8188),
	legal_person_identity_num VARCHAR(8188),
	legal_person_name VARCHAR(8188),
	legal_person_phone VARCHAR(8188),
	business_address VARCHAR(8188),
	create_time TIMESTAMP(36,6),
	separate_bank_id VARCHAR(8188),
	org_num VARCHAR(8188),
	dept_id BIGINT(19),
	credit_manager_job_number VARCHAR(8188),
	addr_type VARCHAR(8188),
	new_legal_person VARCHAR(8188),
	update_time DATE(13)
);

create table credit_customer_info_bak20240601
(
	id BIGINT(19),
	customer_nm VARCHAR(8188),
	customer_name VARCHAR(8188),
	customer_phone VARCHAR(8188),
	customer_type VARCHAR(8188),
	customer_address VARCHAR(8188),
	customer_identity VARCHAR(8188),
	customer_level VARCHAR(8188),
	credit_amount DEC(30,2),
	using_credit_amount DEC(30,2),
	five_level VARCHAR(8188),
	belong_grid_code VARCHAR(8188),
	belong_grid_name VARCHAR(8188),
	grid_manager VARCHAR(8188),
	credit_manager VARCHAR(8188),
	branch_bank VARCHAR(8188),
	separate_bank VARCHAR(8188),
	guarantee_method VARCHAR(8188),
	using_type INT(10),
	is_in_arrears INT(10),
	exchange_method INT(10),
	product_label VARCHAR(8188),
	offline_attract VARCHAR(8188),
	credit_promotion VARCHAR(8188),
	other_label INT(10),
	delete_flag INT(10),
	risk_type VARCHAR(8188),
	village_loan_type VARCHAR(8188),
	is_product INT(10),
	gender VARCHAR(8188),
	birthday VARCHAR(8188),
	age INT(10),
	marital_status VARCHAR(8188),
	JOB VARCHAR(8188),
	work_unit VARCHAR(8188),
	legal_person_identity_num VARCHAR(8188),
	legal_person_name VARCHAR(8188),
	legal_person_phone VARCHAR(8188),
	business_address VARCHAR(8188),
	create_time TIMESTAMP(36,6),
	separate_bank_id VARCHAR(8188),
	org_num VARCHAR(8188),
	dept_id BIGINT(19),
	credit_manager_job_number VARCHAR(8188),
	addr_type VARCHAR(8188),
	new_legal_person VARCHAR(8188),
	update_time DATE(13)
);

create table credit_record
(
	TYPE VARCHAR(1020),
	contract_num VARCHAR(1020),
	product_name VARCHAR(1020),
	create_time TIMESTAMP(36,6),
	expire_date TIMESTAMP(36,6),
	manager VARCHAR(1020),
	identity VARCHAR(1020),
	customer_nm VARCHAR(32752),
	custom_id BIGINT(19),
	etl_date TIMESTAMP(36,6),
	manager_num VARCHAR(8188),
	credit_amount DEC(20,2),
	loan_amount DEC(20,2)
);

create table credit_role_temp
(
	USERID VARCHAR(200),
	USERNAME VARCHAR(200),
	BELONGORG VARCHAR(200),
	"GETORGNAME(BELONGORG)" VARCHAR(200),
	ROLEID VARCHAR(200),
	ROLENAME VARCHAR(200)
);

create table custom_addr_info
(
	id BIGINT(19),
	CUSTNO VARCHAR(255),
	ADDRESS VARCHAR(255),
	BAIDULEVEL VARCHAR(255),
	LNG NUMBER(22),
	LAT NUMBER(22),
	area_code VARCHAR(255),
	area VARCHAR(255)
);

comment on column custom_addr_info.CUSTNO is '客户编码';

comment on column custom_addr_info.ADDRESS is '地址';

comment on column custom_addr_info.BAIDULEVEL is '地址等级';

comment on column custom_addr_info.LNG is '经度';

comment on column custom_addr_info.LAT is '纬度';

comment on column custom_addr_info.area_code is '地区编码';

comment on column custom_addr_info.area is '网格名称';

create table custom_group
(
	id BIGINT(19),
	custom_type TINYINT(3) not null,
	category_id BIGINT(19),
	custom_group_code VARCHAR(50),
	custom_group_name VARCHAR(50),
	label_ids VARCHAR(500),
	custom_member VARCHAR(500),
	custom_num INT(10),
	custom_group_source VARCHAR(100),
	group_desc VARCHAR(255),
	update_flag TINYINT(3),
	update_cycle TINYINT(3),
	group_status TINYINT(3) default 0,
	create_by VARCHAR(64),
	create_time TIMESTAMP(29) default CURRENT_TIMESTAMP(),
	update_by VARCHAR(64),
	update_time TIMESTAMP(29),
	del_flag TINYINT(3) default 0,
	create_num VARCHAR(50)
);

comment on table custom_group is '客群';

comment on column custom_group.id is '客群id';

comment on column custom_group.custom_type is '客群类型（1：绩效客户；2：授信客户；2：建档客户）';

comment on column custom_group.category_id is '客群分类id（1：绩效客户；2：授信客户；2：建档客户）';

comment on column custom_group.custom_group_code is '客群编号';

comment on column custom_group.custom_group_name is '客群名称';

comment on column custom_group.label_ids is '标签id集合';

comment on column custom_group.custom_member is '客户成员id集合';

comment on column custom_group.custom_num is '客户数量';

comment on column custom_group.custom_group_source is '客群来源';

comment on column custom_group.group_desc is '说明';

comment on column custom_group.update_flag is '是否自动更新0：否；1：是';

comment on column custom_group.update_cycle is '更新周期';

comment on column custom_group.group_status is '状态（0：待生成；1：已生成）';

comment on column custom_group.create_by is '创建人';

comment on column custom_group.create_time is '创建时间';

comment on column custom_group.update_by is '更新人';

comment on column custom_group.update_time is '更新时间';

comment on column custom_group.del_flag is '删除标志（0：正常；1：删除）';

comment on column custom_group.create_num is '创建人工号';

create unique index INDEX33654485
	on custom_group (id);

alter table custom_group
	add constraint CONS134252829
		primary key (id);

create table custom_group_customer
(
	id BIGINT(19),
	custom_group_id BIGINT(19),
	customer_id BIGINT(19)
);

create unique index INDEX33654510
	on custom_group_customer (id);

alter table custom_group_customer
	add constraint CONS134252841
		primary key (id);

create table customer_group_category
(
	id BIGINT(19),
	category_name VARCHAR(100) not null,
	parent_id BIGINT(19) not null
);

comment on table customer_group_category is '客群分类';

comment on column customer_group_category.id is '主键id';

comment on column customer_group_category.category_name is '分类名称';

comment on column customer_group_category.parent_id is '上级id';

create unique index INDEX33654280
	on customer_group_category (id);

alter table customer_group_category
	add constraint CONS134252716
		primary key (id);

create table customer_info
(
	id BIGINT(19),
	customer_type TINYINT(3) not null
		constraint idx_customer_info_customer_type
			unique,
	bank_inter_flag TINYINT(3) not null,
	common_pool_flag TINYINT(3) not null,
	customer_name VARCHAR(100) not null,
	customer_category TINYINT(3),
	card_type TINYINT(3),
	card_num VARCHAR(100),
	contact_phone VARCHAR(50),
	customer_inter_num VARCHAR(100),
	customer_address VARCHAR(255),
	belong_grid INT(10)
		constraint idx_customer_info_belong_grid
			unique,
	belong_grid_name VARCHAR(255),
	duty_dept_id INT(10),
	grid_user_id INT(10),
	business_manage_user VARCHAR(10),
	files_status TINYINT(3),
	credit_code VARCHAR(100),
	merchant_manager VARCHAR(20),
	legal_person VARCHAR(100),
	legal_person_phone VARCHAR(50),
	legal_person_card_num VARCHAR(50),
	customer_sex TINYINT(3),
	customer_age INT(10),
	career VARCHAR(50),
	householder_flag TINYINT(3),
	marriage_flag TINYINT(3),
	citizens_flag TINYINT(3),
	work_address VARCHAR(255),
	business_license_image VARCHAR(255),
	business_place_type TINYINT(3),
	income_year DEC(10,2),
	register_capital VARCHAR(50),
	grid_addr VARCHAR(255),
	birthday TIMESTAMP(29),
	customer_level VARCHAR(255)
);

comment on table customer_info is '客户信息主表';

comment on column customer_info.id is '主键id';

comment on column customer_info.bank_inter_flag is '是否行内客户（0：否；1：是）';

comment on column customer_info.common_pool_flag is '是否公共池（0：否；1：是）';

comment on column customer_info.customer_name is '客户名称';

comment on column customer_info.customer_category is '客户类别';

comment on column customer_info.card_type is '证件类型';

comment on column customer_info.card_num is '证件号码';

comment on column customer_info.contact_phone is '联系方式';

comment on column customer_info.customer_inter_num is '客户内码';

comment on column customer_info.customer_address is '客户地址';

comment on column customer_info.belong_grid is '所属网格id';

comment on column customer_info.belong_grid_name is '所属网格名称';

comment on column customer_info.duty_dept_id is '责任部门id';

comment on column customer_info.grid_user_id is '网格员';

comment on column customer_info.business_manage_user is '业务管户人';

comment on column customer_info.files_status is '建档状态（0：未建档；1：已建档）';

comment on column customer_info.credit_code is '统一社会信用代码';

comment on column customer_info.merchant_manager is '商户经营者';

comment on column customer_info.legal_person is '企业法人';

comment on column customer_info.legal_person_phone is '企业法人联系方式';

comment on column customer_info.legal_person_card_num is '法人身份证号码';

comment on column customer_info.customer_sex is '性别';

comment on column customer_info.customer_age is '年龄';

comment on column customer_info.career is '职业';

comment on column customer_info.householder_flag is '是否户主（0：否；1：是）';

comment on column customer_info.marriage_flag is '婚姻状况';

comment on column customer_info.citizens_flag is '是否新市民（0：否；1：是）';

comment on column customer_info.work_address is '工作单位';

comment on column customer_info.business_license_image is '营业执照';

comment on column customer_info.business_place_type is '经营场所类型';

comment on column customer_info.income_year is '年收入/近一年收入';

comment on column customer_info.register_capital is '注册资本';

comment on column customer_info.grid_addr is '网格地址';

comment on column customer_info.birthday is '生日';

comment on column customer_info.customer_level is '客户等级';

create unique index INDEX33654230
	on customer_info (id);

alter table customer_info
	add constraint CONS134252696
		primary key (id);

create table customer_label
(
	id BIGINT(19),
	customer_id BIGINT(19),
	label_id BIGINT(19) not null,
	label_type TINYINT(3),
	type VARCHAR(255),
	customer_nm VARCHAR(255)
);

comment on table customer_label is '用户标签';

comment on column customer_label.id is '主键id';

comment on column customer_label.customer_id is '客户id';

comment on column customer_label.label_id is '标签id';

comment on column customer_label.label_type is '标签种类（0：基础标签；1：网络标签）';

comment on column customer_label.type is '客户类型 1-绩效客户 2-授信客户 3-建档客户';

comment on column customer_label.customer_nm is '客户编码';

create unique index INDEX33654282
	on customer_label (id);

alter table customer_label
	add constraint CONS134252717
		primary key (id);

create table customer_label_bak20240601
(
	id BIGINT(19),
	customer_id BIGINT(19),
	label_id BIGINT(19),
	label_type TINYINT(3),
	type VARCHAR(255),
	customer_nm VARCHAR(255)
);

create table customer_product_detail
(
	id BIGINT(19),
	holder_id BIGINT(19) not null,
	product_type TINYINT(3) not null,
	product_id BIGINT(19) not null,
	product_code VARCHAR(50),
	product_name VARCHAR(50),
	handle_amount DEC(10,2),
	handle_time TIMESTAMP(29),
	end_time TIMESTAMP(29),
	manager BIGINT(19),
	interest_date DATE(13)
);

comment on table customer_product_detail is '客户持有产品详情';

comment on column customer_product_detail.id is '主键id';

comment on column customer_product_detail.holder_id is '持有产品人id';

comment on column customer_product_detail.product_type is '产品种类（0：负债类；1：资产类；2：产品类）';

comment on column customer_product_detail.product_id is '持有产品id';

comment on column customer_product_detail.product_code is '产品编码';

comment on column customer_product_detail.product_name is '产品名称';

comment on column customer_product_detail.handle_amount is '金额';

comment on column customer_product_detail.handle_time is '办理时间';

comment on column customer_product_detail.end_time is '到期时间';

comment on column customer_product_detail.manager is '负责人id';

comment on column customer_product_detail.interest_date is '起息日';

create unique index INDEX33654284
	on customer_product_detail (id);

alter table customer_product_detail
	add constraint CONS134252718
		primary key (id);

create table customer_route
(
	route_id BIGINT(19),
	customer_id BIGINT(19),
	identity VARCHAR(255),
	card_open_time TIMESTAMP(29),
	card_all_type VARCHAR(255),
	card_entity_back VARCHAR(255),
	grant_credit_product_name VARCHAR(255),
	grant_credit_time TIMESTAMP(29),
	grant_credit_bal VARCHAR(255),
	visits_time TIMESTAMP(29),
	visits_manager VARCHAR(255)
);

comment on table customer_route is '客户旅程表';

comment on column customer_route.route_id is '客户旅程id';

comment on column customer_route.customer_id is '客户id';

comment on column customer_route.identity is '证件号 身份证号/统一社会信用代码';

comment on column customer_route.card_open_time is '开卡时间';

comment on column customer_route.card_all_type is '所有卡类别';

comment on column customer_route.card_entity_back is '实体卡尾号4位数';

comment on column customer_route.grant_credit_product_name is '授信产品名称';

comment on column customer_route.grant_credit_time is '授信时间';

comment on column customer_route.grant_credit_bal is '授信金额';

comment on column customer_route.visits_time is '走访时间';

comment on column customer_route.visits_manager is '走访人员';

create unique index INDEX33654286
	on customer_route (route_id);

alter table customer_route
	add constraint CONS134252719
		primary key (route_id);

create table customer_trade
(
	id BIGINT(19),
	customer_id BIGINT(19),
	trade_year VARCHAR(5),
	trade_month VARCHAR(5),
	trade_day VARCHAR(50),
	trade_amount DEC(10,2),
	trade_card_num BIGINT(19),
	trade_time TIMESTAMP(29)
);

comment on table customer_trade is '商户交易金额表';

comment on column customer_trade.id is '主键id';

comment on column customer_trade.customer_id is '客户id';

comment on column customer_trade.trade_year is '交易年份';

comment on column customer_trade.trade_month is '交易月份';

comment on column customer_trade.trade_day is '交易日';

comment on column customer_trade.trade_amount is '交易金额';

comment on column customer_trade.trade_card_num is '交易卡号';

comment on column customer_trade.trade_time is '交易时间';

create unique index INDEX33654288
	on customer_trade (id);

alter table customer_trade
	add constraint CONS134252720
		primary key (id);

create table customer_visits
(
	id BIGINT(19),
	customer_id BIGINT(19),
	customer_nm VARCHAR(255),
	customer_type VARCHAR(10),
	type VARCHAR(10),
	identity VARCHAR(50),
	customer_name VARCHAR(100),
	customer_phone VARCHAR(255),
	work_unit VARCHAR(255),
	grid_address VARCHAR(255),
	other_bank_due_demand VARCHAR(255),
	due_time TIMESTAMP(29),
	due_bal VARCHAR(255),
	other_demand VARCHAR(255),
	other_demand_remark VARCHAR(255),
	is_visits_black VARCHAR(10),
	risk_customer_remark VARCHAR(255),
	visits_check VARCHAR(10),
	visits_time TIMESTAMP(29),
	visits_address VARCHAR(255),
	visits_remark VARCHAR(255),
	visits_manager VARCHAR(255),
	visits_way VARCHAR(255),
	remark VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	grid_manager VARCHAR(255),
	grid_address_type VARCHAR(255),
	grid_address_detail VARCHAR(255),
	visits_manager_code VARCHAR(255),
	visits_org VARCHAR(255),
	visits_org_code VARCHAR(255),
	checkin_address VARCHAR(8188),
	visit_label VARCHAR(8188),
	visits_picture TEXT(max)
);

comment on table customer_visits is '客户走访信息表';

comment on column customer_visits.id is '最近走访记录id';

comment on column customer_visits.customer_id is '客户id';

comment on column customer_visits.customer_nm is '客户编码';

comment on column customer_visits.customer_type is '客户小类型 1-个人 2-企业 3-商户';

comment on column customer_visits.type is '客户大类型 1-绩效客户 2-授信客户 3-建档客户 4-收单客户';

comment on column customer_visits.identity is '证件号 (身份证号/统一社会信用代码)';

comment on column customer_visits.customer_name is '客户名称';

comment on column customer_visits.customer_phone is '联系电话';

comment on column customer_visits.work_unit is '工作单位';

comment on column customer_visits.grid_address is '网格地址';

comment on column customer_visits.other_bank_due_demand is '他行存储到期需求';

comment on column customer_visits.due_time is '到期日期';

comment on column customer_visits.due_bal is '到期金额(万元)';

comment on column customer_visits.other_demand is '其他需求';

comment on column customer_visits.other_demand_remark is '其他需求备注';

comment on column customer_visits.is_visits_black is '走访黑户 0-否 1-是';

comment on column customer_visits.risk_customer_remark is '风险客户备注';

comment on column customer_visits.visits_check is '走访打卡 0-否 1-是';

comment on column customer_visits.visits_time is '走访时间';

comment on column customer_visits.visits_address is '走访地址';

comment on column customer_visits.visits_remark is '走访备注';

comment on column customer_visits.visits_manager is '走访人员';

comment on column customer_visits.visits_way is '走访形式';

comment on column customer_visits.remark is '备注';

comment on column customer_visits.create_time is '创建时间';

comment on column customer_visits.update_time is '更新时间';

comment on column customer_visits.grid_manager is '网格员';

comment on column customer_visits.grid_address_type is '地址类型';

comment on column customer_visits.grid_address_detail is '详细地址';

comment on column customer_visits.visits_manager_code is '走访人员工号';

create unique index INDEX33654491
	on customer_visits (id);

alter table customer_visits
	add constraint CONS134252832
		primary key (id);

create table dept_location_info
(
	org_num VARCHAR(32767),
	point VARCHAR(32767),
	org_name VARCHAR(32767),
	address VARCHAR(32767)
);

create table filing_customer_info
(
	id BIGINT(19),
	customer_nm VARCHAR(255),
	model_id BIGINT(19),
	customer_type VARCHAR(255),
	customer_name VARCHAR(255),
	customer_address VARCHAR(255),
	customer_address_type VARCHAR(255),
	customer_phone VARCHAR(255),
	customer_identity VARCHAR(255),
	customer_source VARCHAR(255),
	customer_introducer_identity VARCHAR(255),
	customer_photo VARCHAR(255),
	clock_in VARCHAR(255),
	customer_level VARCHAR(255),
	belong_grid_code VARCHAR(255),
	belong_grid_name VARCHAR(255),
	filing_manager_code VARCHAR(255),
	filing_manager_name VARCHAR(255),
	filing_org_code VARCHAR(255),
	filing_org_name VARCHAR(255),
	filing_progress VARCHAR(255),
	filing_source VARCHAR(255),
	filing_status VARCHAR(255),
	filing_date DATE(13),
	grid_manager VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	branch_bank VARCHAR(255),
	separate_bank VARCHAR(255)
);

comment on table filing_customer_info is '建档客户基础信息表';

comment on column filing_customer_info.id is '建档id';

comment on column filing_customer_info.customer_nm is '建档客户号';

comment on column filing_customer_info.model_id is '当前客户绑定的模板id';

comment on column filing_customer_info.customer_type is '客户类型 1-个人 2-企业 3-商户 4-其他';

comment on column filing_customer_info.customer_name is '客户名称';

comment on column filing_customer_info.customer_address is '网格地址';

comment on column filing_customer_info.customer_address_type is '地址类型 1-经营地址 2-工作地址 3-家庭地址 4-户籍地址';

comment on column filing_customer_info.customer_phone is '手机号码';

comment on column filing_customer_info.customer_identity is '证件号(身份证号/统一社会信用代码)';

comment on column filing_customer_info.customer_source is '客户来源 1-客户介绍 2-第三方介绍 3-陌拜';

comment on column filing_customer_info.customer_introducer_identity is '介绍人证件号(身份证号/统一社会信用代码)';

comment on column filing_customer_info.customer_photo is '客户图片';

comment on column filing_customer_info.clock_in is '打卡(经纬度)';

comment on column filing_customer_info.customer_level is '客户等级';

comment on column filing_customer_info.belong_grid_code is '所属网格编码';

comment on column filing_customer_info.belong_grid_name is '所属网格名称';

comment on column filing_customer_info.filing_manager_code is '建档人编码';

comment on column filing_customer_info.filing_manager_name is '建档人名称';

comment on column filing_customer_info.filing_org_code is '建档机构编码';

comment on column filing_customer_info.filing_org_name is '建档机构名称';

comment on column filing_customer_info.filing_progress is '建档进度';

comment on column filing_customer_info.filing_source is '建档来源 1-人工导入 2-批量导入';

comment on column filing_customer_info.filing_status is '建档状态 0-临时建档 1-新增建档 2-有效建档';

comment on column filing_customer_info.filing_date is '建档日期';

comment on column filing_customer_info.grid_manager is '网格员';

comment on column filing_customer_info.create_time is '创建日期';

comment on column filing_customer_info.update_time is '更新日期';

comment on column filing_customer_info.branch_bank is '支行';

comment on column filing_customer_info.separate_bank is '分行';

create unique index INDEX33654502
	on filing_customer_info (id);

alter table filing_customer_info
	add constraint CONS134252837
		primary key (id);

create table filing_customer_transfer
(
	id BIGINT(19),
	filing_id BIGINT(19),
	customer_name VARCHAR(100),
	customer_type VARCHAR(10),
	customer_identity VARCHAR(100),
	customer_phone VARCHAR(50),
	belong_grid_name VARCHAR(50),
	move_out_manager_name VARCHAR(50),
	move_out_manager_code VARCHAR(50),
	move_out_org_name VARCHAR(50),
	move_out_org_code VARCHAR(50),
	move_in_manager_name VARCHAR(50),
	move_in_manager_code VARCHAR(50),
	move_in_org_name VARCHAR(50),
	move_in_org_code VARCHAR(50),
	move_judge_name VARCHAR(50),
	move_judge_code VARCHAR(50),
	move_manual VARCHAR(255),
	judge_indicate VARCHAR(255),
	move_status VARCHAR(10),
	move_date DATE(13),
	finish_date DATE(13),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	move_judge_dept_id BIGINT(19)
);

comment on table filing_customer_transfer is '建档客户移交审批表';

comment on column filing_customer_transfer.id is '移交id';

comment on column filing_customer_transfer.filing_id is '建档客户id';

comment on column filing_customer_transfer.customer_name is '客户名称';

comment on column filing_customer_transfer.customer_type is '客户类型 1-个人 2-企业 3-商户 4-其他';

comment on column filing_customer_transfer.customer_identity is '客户证件号(身份证号/统一社会信用代码)';

comment on column filing_customer_transfer.customer_phone is '客户联系电话';

comment on column filing_customer_transfer.belong_grid_name is '所属网格名称';

comment on column filing_customer_transfer.move_out_manager_name is '移交人名称';

comment on column filing_customer_transfer.move_out_manager_code is '移交人工号';

comment on column filing_customer_transfer.move_out_org_name is '移交人机构名称';

comment on column filing_customer_transfer.move_out_org_code is '移交人机构编码';

comment on column filing_customer_transfer.move_in_manager_name is '接收人名称';

comment on column filing_customer_transfer.move_in_manager_code is '接收人工号';

comment on column filing_customer_transfer.move_in_org_name is '接收人机构名称';

comment on column filing_customer_transfer.move_in_org_code is '接收人机构编码';

comment on column filing_customer_transfer.move_judge_name is '审批人名称';

comment on column filing_customer_transfer.move_judge_code is '审批人工号';

comment on column filing_customer_transfer.move_manual is '移交说明';

comment on column filing_customer_transfer.judge_indicate is '审批意见';

comment on column filing_customer_transfer.move_status is '移交状态 0-未审核 1-同意 2-拒绝';

comment on column filing_customer_transfer.move_date is '申请日期';

comment on column filing_customer_transfer.finish_date is '完成日期';

comment on column filing_customer_transfer.create_time is '创建时间';

comment on column filing_customer_transfer.update_time is '更新时间';

create unique index INDEX33654504
	on filing_customer_transfer (id);

alter table filing_customer_transfer
	add constraint CONS134252838
		primary key (id);

create table filing_field_info
(
	id BIGINT(19),
	model_id BIGINT(19),
	chinese_name VARCHAR(100),
	english_name VARCHAR(100),
	use_type VARCHAR(10) default '1',
	field_type VARCHAR(10),
	is_finish VARCHAR(10) default '1',
	is_must VARCHAR(20),
	remark VARCHAR(300),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	content VARCHAR(255)
);

comment on table filing_field_info is '建档考核字段表';

comment on column filing_field_info.id is '建档字段表id';

comment on column filing_field_info.model_id is '模板表id';

comment on column filing_field_info.chinese_name is '字段中文名';

comment on column filing_field_info.english_name is '字段英文名称';

comment on column filing_field_info.use_type is '使用者类型 1-家庭 2-企业  3-商户 4-其他';

comment on column filing_field_info.field_type is '字段类型';

comment on column filing_field_info.is_finish is '是否为完成要素  0-否  1-是';

comment on column filing_field_info.is_must is '是否必填 0-否 1是';

comment on column filing_field_info.remark is '备注';

comment on column filing_field_info.create_time is '创建时间';

comment on column filing_field_info.update_time is '更新时间';

comment on column filing_field_info.content is '字段阈值';

create unique index INDEX33654294
	on filing_field_info (id);

alter table filing_field_info
	add constraint CONS134252723
		primary key (id);

create table filing_history_update
(
	id BIGINT(19),
	model BIGINT(19),
	identity VARCHAR(60),
	filing_id BIGINT(19),
	update_by VARCHAR(20),
	update_by_code VARCHAR(20),
	update_org VARCHAR(20),
	update_org_code VARCHAR(20),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on column filing_history_update.id is '操作档案历史记录表 主键id';

comment on column filing_history_update.model is '模板表id';

comment on column filing_history_update.identity is '被操作档案的证件号';

comment on column filing_history_update.filing_id is '被操作档案的建档id';

comment on column filing_history_update.update_by is '档案修改人';

comment on column filing_history_update.update_by_code is '档案修改人编码';

comment on column filing_history_update.update_org is '档案修改机构';

comment on column filing_history_update.update_org_code is '档案修改机构编码';

comment on column filing_history_update.create_time is '创建时间';

comment on column filing_history_update.update_time is '更新时间';

create unique index INDEX33654296
	on filing_history_update (id);

alter table filing_history_update
	add constraint CONS134252724
		primary key (id);

create table filing_import_record
(
	id INT(10),
	import_status VARCHAR(10),
	import_num INT(10),
	user_name VARCHAR(50),
	user_id INT(10),
	fail_reason VARCHAR(255),
	create_time TIMESTAMP(29)
);

comment on table filing_import_record is '建档客户导入记录';

comment on column filing_import_record.id is '建档客户导入记录id';

comment on column filing_import_record.import_status is '导入状态 0-导入中 1-导入成功 2-导入失败';

comment on column filing_import_record.import_num is '导入数据量';

comment on column filing_import_record.user_name is '操作用户名称';

comment on column filing_import_record.user_id is '操作用户id';

comment on column filing_import_record.fail_reason is '失败原因';

comment on column filing_import_record.create_time is '导入时间';

create unique index INDEX33654506
	on filing_import_record (id);

alter table filing_import_record
	add constraint CONS134252839
		primary key (id);

create table filing_message_detail
(
	id BIGINT(19),
	model_id BIGINT(19),
	filing_id BIGINT(19),
	json_message VARCHAR(8188),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	json_finish VARCHAR(8188)
);

comment on column filing_message_detail.id is '档案信息id';

comment on column filing_message_detail.model_id is '模板id';

comment on column filing_message_detail.filing_id is '建档id';

comment on column filing_message_detail.json_message is '模板信息(json格式)';

comment on column filing_message_detail.create_time is '创建时间';

comment on column filing_message_detail.update_time is '更新时间';

comment on column filing_message_detail.json_finish is '建档完成(json格式)';

create unique index INDEX33654298
	on filing_message_detail (id);

alter table filing_message_detail
	add constraint CONS134252725
		primary key (id);

create table filing_model_info
(
	id BIGINT(19),
	model_name VARCHAR(100),
	model_type VARCHAR(10) default '1',
	status VARCHAR(10) default '0',
	create_by VARCHAR(100),
	create_org VARCHAR(100),
	priority VARCHAR(10),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	constraint idx
		unique (model_type, create_org, status)
);

comment on table filing_model_info is '表单字段模板表';

comment on column filing_model_info.id is '采集字段模板表id';

comment on column filing_model_info.model_name is '模板表名称';

comment on column filing_model_info.model_type is '模板表类型 1-个人客户 2-企业 3-商户';

comment on column filing_model_info.status is '状态  0-未发布  1-已发布';

comment on column filing_model_info.create_by is '创建人';

comment on column filing_model_info.create_org is '创建组织code';

comment on column filing_model_info.priority is '优先级  1-业务优先  2-网格优先';

comment on column filing_model_info.create_time is '创建时间';

comment on column filing_model_info.update_time is '更新日期';

create unique index INDEX33654222
	on filing_model_info (id);

alter table filing_model_info
	add constraint CONS134252694
		primary key (id);

create table gen_table
(
	table_id BIGINT(19),
	table_name VARCHAR(200) default '',
	table_comment VARCHAR(500) default '',
	sub_table_name VARCHAR(64),
	sub_table_fk_name VARCHAR(64),
	class_name VARCHAR(100) default '',
	tpl_category VARCHAR(200) default 'crud',
	package_name VARCHAR(100),
	module_name VARCHAR(30),
	business_name VARCHAR(30),
	function_name VARCHAR(50),
	function_author VARCHAR(50),
	gen_type CHAR(1) default '0',
	gen_path VARCHAR(200) default '/',
	options VARCHAR(1000),
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29),
	remark VARCHAR(500),
	tpl_web_type INT(10)
);

comment on table gen_table is '代码生成业务表';

comment on column gen_table.table_id is '编号';

comment on column gen_table.table_name is '表名称';

comment on column gen_table.table_comment is '表描述';

comment on column gen_table.sub_table_name is '关联子表的表名';

comment on column gen_table.sub_table_fk_name is '子表关联的外键名';

comment on column gen_table.class_name is '实体类名称';

comment on column gen_table.tpl_category is '使用的模板（crud单表操作 tree树表操作）';

comment on column gen_table.package_name is '生成包路径';

comment on column gen_table.module_name is '生成模块名';

comment on column gen_table.business_name is '生成业务名';

comment on column gen_table.function_name is '生成功能名';

comment on column gen_table.function_author is '生成功能作者';

comment on column gen_table.gen_type is '生成代码方式（0zip压缩包 1自定义路径）';

comment on column gen_table.gen_path is '生成路径（不填默认项目路径）';

comment on column gen_table.options is '其它生成选项';

comment on column gen_table.create_by is '创建者';

comment on column gen_table.create_time is '创建时间';

comment on column gen_table.update_by is '更新者';

comment on column gen_table.update_time is '更新时间';

comment on column gen_table.remark is '备注';

create unique index INDEX33654301
	on gen_table (table_id);

alter table gen_table
	add constraint CONS134252728
		primary key (table_id);

create table gen_table_column
(
	column_id BIGINT(19),
	table_id VARCHAR(64),
	column_name VARCHAR(200),
	column_comment VARCHAR(500),
	column_type VARCHAR(100),
	java_type VARCHAR(500),
	java_field VARCHAR(200),
	is_pk CHAR(1),
	is_increment CHAR(1),
	is_required CHAR(1),
	is_insert CHAR(1),
	is_edit CHAR(1),
	is_list CHAR(1),
	is_query CHAR(1),
	query_type VARCHAR(200) default 'EQ',
	html_type VARCHAR(200),
	dict_type VARCHAR(200) default '',
	sort INT(10),
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29)
);

comment on table gen_table_column is '代码生成业务表字段';

comment on column gen_table_column.column_id is '编号';

comment on column gen_table_column.table_id is '归属表编号';

comment on column gen_table_column.column_name is '列名称';

comment on column gen_table_column.column_comment is '列描述';

comment on column gen_table_column.column_type is '列类型';

comment on column gen_table_column.java_type is 'JAVA类型';

comment on column gen_table_column.java_field is 'JAVA字段名';

comment on column gen_table_column.is_pk is '是否主键（1是）';

comment on column gen_table_column.is_increment is '是否自增（1是）';

comment on column gen_table_column.is_required is '是否必填（1是）';

comment on column gen_table_column.is_insert is '是否为插入字段（1是）';

comment on column gen_table_column.is_edit is '是否编辑字段（1是）';

comment on column gen_table_column.is_list is '是否列表字段（1是）';

comment on column gen_table_column.is_query is '是否查询字段（1是）';

comment on column gen_table_column.query_type is '查询方式（等于、不等于、大于、小于、范围）';

comment on column gen_table_column.html_type is '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）';

comment on column gen_table_column.dict_type is '字典类型';

comment on column gen_table_column.sort is '排序';

comment on column gen_table_column.create_by is '创建者';

comment on column gen_table_column.create_time is '创建时间';

comment on column gen_table_column.update_by is '更新者';

comment on column gen_table_column.update_time is '更新时间';

create unique index INDEX33654303
	on gen_table_column (column_id);

alter table gen_table_column
	add constraint CONS134252729
		primary key (column_id);

create table grid_crux_info
(
	id BIGINT(19),
	grid_message_id BIGINT(19),
	grid_code VARCHAR(255),
	crux_name VARCHAR(255),
	crux_sex VARCHAR(255),
	crux_phone VARCHAR(255),
	crux_address VARCHAR(255),
	crux_work VARCHAR(255),
	crux_identity VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table grid_crux_info is '网格关键信息维护表';

comment on column grid_crux_info.id is '网格关键信息表主键id';

comment on column grid_crux_info.grid_message_id is '网格基础信息id';

comment on column grid_crux_info.grid_code is '网格编码';

comment on column grid_crux_info.crux_name is '关键人名称';

comment on column grid_crux_info.crux_sex is '关键人性别';

comment on column grid_crux_info.crux_phone is '关键人手机';

comment on column grid_crux_info.crux_address is '关键人地址';

comment on column grid_crux_info.crux_work is '关键人职位';

comment on column grid_crux_info.crux_identity is '关键人证件号';

comment on column grid_crux_info.create_time is '创建时间';

comment on column grid_crux_info.update_time is '更新时间';

create unique index INDEX33654305
	on grid_crux_info (id);

alter table grid_crux_info
	add constraint CONS134252730
		primary key (id);

create table grid_manager_org
(
	code VARCHAR(8188),
	jobNo VARCHAR(8188),
	orgNo VARCHAR(8188)
);

create table grid_message_info
(
	id BIGINT(19),
	grid_code VARCHAR(50),
	grid_name VARCHAR(100),
	grid_customer_total VARCHAR(50),
	grid_main_manager_name VARCHAR(50),
	grid_main_manager_code VARCHAR(50),
	grid_side_manager_name VARCHAR(50),
	grid_side_manager_code VARCHAR(50),
	grid_overview VARCHAR(2000),
	grid_remark VARCHAR(2000),
	belong_team VARCHAR(100),
	first_level_branch VARCHAR(100),
	second_level_branch VARCHAR(100),
	branch VARCHAR(100),
	picture VARCHAR(1000),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	rural_grid_type VARCHAR(100),
	is_modify VARCHAR(10) default '0'
);

comment on table grid_message_info is '网格基础信息维护表';

comment on column grid_message_info.id is '网格基础信息表主键id';

comment on column grid_message_info.grid_code is '网格编码';

comment on column grid_message_info.grid_name is '网格名称';

comment on column grid_message_info.grid_customer_total is '网格客户总数';

comment on column grid_message_info.grid_main_manager_name is '网格主管员名称';

comment on column grid_message_info.grid_main_manager_code is '网格主管员工号';

comment on column grid_message_info.grid_side_manager_name is '网格副管员名称';

comment on column grid_message_info.grid_side_manager_code is '网格副管员工号';

comment on column grid_message_info.grid_overview is '网格概况';

comment on column grid_message_info.grid_remark is '网格备注';

comment on column grid_message_info.belong_team is '所属团队';

comment on column grid_message_info.first_level_branch is '一级支行';

comment on column grid_message_info.second_level_branch is '二级支行';

comment on column grid_message_info.branch is '分行';

comment on column grid_message_info.picture is '网格图片';

comment on column grid_message_info.create_time is '创建时间';

comment on column grid_message_info.update_time is '更新时间';

create unique index INDEX33654307
	on grid_message_info (id);

alter table grid_message_info
	add constraint CONS134252731
		primary key (id);

create table grid_rating_info
(
	id BIGINT(19),
	grid_message_id BIGINT(19),
	grid_code VARCHAR(255),
	rating_year VARCHAR(255),
	grid_rating VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table grid_rating_info is '网格评级信息维护表';

comment on column grid_rating_info.id is '网格评级信息表主键id';

comment on column grid_rating_info.grid_message_id is '网格信息id';

comment on column grid_rating_info.grid_code is '网格编码';

comment on column grid_rating_info.rating_year is '评级年度';

comment on column grid_rating_info.grid_rating is '网格评级';

comment on column grid_rating_info.create_time is '创建时间';

comment on column grid_rating_info.update_time is '更新时间';

create unique index INDEX33654309
	on grid_rating_info (id);

alter table grid_rating_info
	add constraint CONS134252732
		primary key (id);

create table group_capacity
(
	id BIGINT(19),
	group_id VARCHAR(128) default '' not null
		constraint INDEX33654312
			unique,
	quota BIGINT(19) default 0 not null,
	usage BIGINT(19) default 0 not null,
	max_size BIGINT(19) default 0 not null,
	max_aggr_count BIGINT(19) default 0 not null,
	max_aggr_size BIGINT(19) default 0 not null,
	max_history_count BIGINT(19) default 0 not null,
	gmt_create TIMESTAMP(29) default CURRENT_TIMESTAMP() not null,
	gmt_modified TIMESTAMP(29) default CURRENT_TIMESTAMP() not null
);

comment on table group_capacity is '集群、各Group容量信息表';

comment on column group_capacity.id is '主键ID';

comment on column group_capacity.group_id is 'Group ID，空字符表示整个集群';

comment on column group_capacity.quota is '配额，0表示使用默认值';

comment on column group_capacity.usage is '使用量';

comment on column group_capacity.max_size is '单个配置大小上限，单位为字节，0表示使用默认值';

comment on column group_capacity.max_aggr_count is '聚合子配置最大个数，，0表示使用默认值';

comment on column group_capacity.max_aggr_size is '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';

comment on column group_capacity.max_history_count is '最大变更历史数量';

comment on column group_capacity.gmt_create is '创建时间';

comment on column group_capacity.gmt_modified is '修改时间';

create unique index INDEX33654311
	on group_capacity (id);

alter table group_capacity
	add constraint CONS134252733
		primary key (id);

create table his_config_info
(
	id DECIMAL(20) not null,
	nid BIGINT(19),
	data_id VARCHAR(255) not null
		constraint idx_did
			unique,
	group_id VARCHAR(128) not null,
	app_name VARCHAR(128),
	content CLOB not null,
	md5 VARCHAR(32),
	gmt_create TIMESTAMP(29) default CURRENT_TIMESTAMP() not null
		constraint idx_gmt_create
			unique,
	gmt_modified TIMESTAMP(29) default CURRENT_TIMESTAMP() not null
		constraint idx_gmt_modified
			unique,
	src_user TEXT(max),
	src_ip VARCHAR(50),
	op_type CHAR(10),
	tenant_id VARCHAR(128) default '',
	encrypted_data_key TEXT(max)
);

comment on table his_config_info is '多租户改造';

comment on column his_config_info.app_name is 'app_name';

comment on column his_config_info.tenant_id is '租户字段';

comment on column his_config_info.encrypted_data_key is '秘钥';

create unique index INDEX33654234
	on his_config_info (nid);

alter table his_config_info
	add constraint CONS134252697
		primary key (nid);

create table job_code_relation
(
	job_code VARCHAR(32752),
	job_name VARCHAR(32752)
);

create table kpi_indicator
(
	id BIGINT(19),
	parent_id BIGINT(19),
	name VARCHAR(50) not null
		constraint name_idx
			unique,
	enable NUMBER(1) default '0' not null,
	type VARCHAR(20),
	remark VARCHAR(255),
	cal_type VARCHAR(5),
	cal_expression VARCHAR(255),
	cal_expression_front VARCHAR(255),
	create_user_id BIGINT(19),
	del_flag NUMBER(1) default '0' not null,
	create_time TIMESTAMP(29) default CURRENT_TIMESTAMP(),
	update_time TIMESTAMP(29) default CURRENT_TIMESTAMP()
);

comment on table kpi_indicator is '考核指标表';

comment on column kpi_indicator.parent_id is '父id';

comment on column kpi_indicator.name is '指标名称';

comment on column kpi_indicator.enable is '是否启用 1-启用 0-关闭';

comment on column kpi_indicator.type is '业务类型';

comment on column kpi_indicator.remark is '指标备注';

comment on column kpi_indicator.cal_type is '计算方式 SYS-系统计算 ART-人工填写';

comment on column kpi_indicator.cal_expression is '计算表达式, 变量使用${field_id}匹配';

comment on column kpi_indicator.cal_expression_front is '计算表达式前端格式存储';

comment on column kpi_indicator.create_user_id is '创建人用户id';

comment on column kpi_indicator.del_flag is '删除标记 1-删除';

comment on column kpi_indicator.create_time is '创建时间';

comment on column kpi_indicator.update_time is '更新时间';

create unique index INDEX33654315
	on kpi_indicator (id);

alter table kpi_indicator
	add constraint CONS134252741
		primary key (id);

create table kpi_indicator_network
(
	id BIGINT(19),
	indicator_id BIGINT(19),
	network_id BIGINT(19),
	create_time TIMESTAMP(29) default CURRENT_TIMESTAMP(),
	update_time TIMESTAMP(29) default CURRENT_TIMESTAMP(),
	constraint INDEX33654321
		unique (indicator_id, network_id)
);

comment on table kpi_indicator_network is '指标和机构关联表';

comment on column kpi_indicator_network.indicator_id is '指标id';

comment on column kpi_indicator_network.network_id is '机构id';

comment on column kpi_indicator_network.create_time is '创建时间';

comment on column kpi_indicator_network.update_time is '更新时间';

create unique index INDEX33654320
	on kpi_indicator_network (id);

alter table kpi_indicator_network
	add constraint CONS134252743
		primary key (id);

create table label_group
(
	id BIGINT(19),
	label_group_name VARCHAR(100),
	parent_id BIGINT(19),
	type INT(10),
	is_multiple INT(10),
	del_flag TINYINT(3) default 0
);

comment on table label_group is '标签分组表';

comment on column label_group.id is '标签组id';

comment on column label_group.label_group_name is '标签组名称';

comment on column label_group.parent_id is '上级分组id';

comment on column label_group.type is '标签类型(0-网格标签，1-系统标签)';

comment on column label_group.is_multiple is '是否多选(0:单选，1：多选)';

comment on column label_group.del_flag is '删除标志（0：正常；1：删除）';

create unique index INDEX33654972
	on label_group (id);

alter table label_group
	add constraint CONS134252885
		primary key (id);

create table label_info
(
	id BIGINT(19),
	label_name VARCHAR(50),
	label_group_id BIGINT(19),
	create_by VARCHAR(64),
	create_time TIMESTAMP(29) default CURRENT_TIMESTAMP(),
	update_by VARCHAR(64),
	update_time TIMESTAMP(29),
	del_flag TINYINT(3) default 0
);

comment on table label_info is '标签信息';

comment on column label_info.id is '标签id';

comment on column label_info.label_name is '标签名称';

comment on column label_info.label_group_id is '标签组id';

comment on column label_info.create_by is '创建人';

comment on column label_info.create_time is '创建时间';

comment on column label_info.update_by is '更新人';

comment on column label_info.update_time is '更新时间';

comment on column label_info.del_flag is '删除标志（0：正常；1：删除）';

create unique index INDEX33654799
	on label_info (id);

alter table label_info
	add constraint CONS134252876
		primary key (id);

create table manager_transfer_refer_index
(
	manager_num VARCHAR(200) not null,
	two_year_loan_pass_rate DECIMAL(26,6),
	last_month_normal_inte_rate DECIMAL(26,6)
);

comment on table manager_transfer_refer_index is '客户经理移交参数指标表';

comment on column manager_transfer_refer_index.manager_num is '客户经理工号';

comment on column manager_transfer_refer_index.two_year_loan_pass_rate is '近2年贷款通过率';

comment on column manager_transfer_refer_index.last_month_normal_inte_rate is '上月正常收息率';

create unique index INDEX33654259
	on manager_transfer_refer_index (manager_num);

alter table manager_transfer_refer_index
	add constraint manager_transfer_refer_index_pk
		primary key (manager_num);

create table opening_card_record
(
	TYPE VARCHAR(1020),
	card_num VARCHAR(1020),
	card_type VARCHAR(80),
	create_time TIMESTAMP(36,6),
	manager VARCHAR(200),
	identity VARCHAR(1020),
	custom_nm VARCHAR(32752),
	manager_num VARCHAR(32752),
	etl_date TIMESTAMP(36,6)
);

create table pa_addr_info
(
	id BIGINT(19) not null,
	code VARCHAR(30)
		constraint idx_pa_addr_info_CODE
			unique,
	name VARCHAR(100),
	parent_code VARCHAR(30),
	is_maintained VARCHAR(1),
	belong_network_code VARCHAR(30),
	update_time TIMESTAMP(29),
	credit_level VARCHAR(255),
	lon DEC(18,8),
	lat DEC(18,8),
	another_name VARCHAR(50),
	boundary_points TEXT(max),
	center_point VARCHAR(50),
	coordinates CLOB,
	addr_level INT(10),
	type VARCHAR(2),
	etl_dt VARCHAR(50)
);

comment on table pa_addr_info is '基础网格边界数据';

comment on column pa_addr_info.id is '主键id';

comment on column pa_addr_info.code is '行政区划编码';

comment on column pa_addr_info.name is '行政区名称';

comment on column pa_addr_info.parent_code is '父级行政区划编码';

comment on column pa_addr_info.is_maintained is '是否被维护，1-已维护，0-未维护';

comment on column pa_addr_info.belong_network_code is '所属网点编码';

comment on column pa_addr_info.update_time is '更新时间';

comment on column pa_addr_info.credit_level is '信用村标志名';

comment on column pa_addr_info.lon is '经度';

comment on column pa_addr_info.lat is '维度';

comment on column pa_addr_info.another_name is '别名/简称';

comment on column pa_addr_info.boundary_points is '边界点集';

comment on column pa_addr_info.center_point is '中心点坐标';

comment on column pa_addr_info.coordinates is '边界数据';

comment on column pa_addr_info.addr_level is '行政编码层级';

comment on column pa_addr_info.type is '边界类型 0行政 1产业园区 2小区 3商圈';

create table pa_addr_info_chouzhou
(
	id BIGINT(19),
	code VARCHAR(30)
		constraint INDEX4642278740440400
			unique,
	name VARCHAR(100),
	parent_code VARCHAR(30),
	is_maintained VARCHAR(1),
	belong_network_code VARCHAR(30),
	update_time TIMESTAMP(29),
	credit_level VARCHAR(255),
	lon DEC(18,8),
	lat DEC(18,8),
	another_name VARCHAR(50),
	boundary_points TEXT(max),
	center_point VARCHAR(50),
	coordinates CLOB,
	addr_level INT(10),
	type VARCHAR(2),
	etl_dt VARCHAR(50),
	grid_people_num INT(10),
	gdp VARCHAR(255),
	village_num INT(10),
	village_people_num INT(10),
	Industrial_park_num INT(10),
	company_num INT(10),
	financial_institution_num INT(10),
	business_area_num INT(10)
);

comment on table pa_addr_info_chouzhou is '基础网格边界数据';

comment on column pa_addr_info_chouzhou.id is '主键id';

comment on column pa_addr_info_chouzhou.code is '行政区划编码';

comment on column pa_addr_info_chouzhou.name is '行政区名称';

comment on column pa_addr_info_chouzhou.parent_code is '父级行政区划编码';

comment on column pa_addr_info_chouzhou.is_maintained is '是否被维护，1-已维护，0-未维护';

comment on column pa_addr_info_chouzhou.belong_network_code is '所属网点编码';

comment on column pa_addr_info_chouzhou.update_time is '更新时间';

comment on column pa_addr_info_chouzhou.credit_level is '信用村标志名';

comment on column pa_addr_info_chouzhou.lon is '经度';

comment on column pa_addr_info_chouzhou.lat is '维度';

comment on column pa_addr_info_chouzhou.another_name is '别名/简称';

comment on column pa_addr_info_chouzhou.boundary_points is '边界点集';

comment on column pa_addr_info_chouzhou.center_point is '中心点坐标';

comment on column pa_addr_info_chouzhou.coordinates is '边界数据';

comment on column pa_addr_info_chouzhou.addr_level is '行政编码层级';

comment on column pa_addr_info_chouzhou.type is '边界类型 0行政 1产业园区 2小区 3商圈';

comment on column pa_addr_info_chouzhou.grid_people_num is '网格人数';

comment on column pa_addr_info_chouzhou.gdp is '经济总量（万元）';

comment on column pa_addr_info_chouzhou.village_num is '小区数量';

comment on column pa_addr_info_chouzhou.village_people_num is '小区人口';

comment on column pa_addr_info_chouzhou.Industrial_park_num is '工业园数量';

comment on column pa_addr_info_chouzhou.company_num is '企业数量';

comment on column pa_addr_info_chouzhou.financial_institution_num is '金融机构数量';

comment on column pa_addr_info_chouzhou.business_area_num is '商圈数量';

create unique index INDEX33654326
	on pa_addr_info_chouzhou (id);

alter table pa_addr_info_chouzhou
	add constraint CONS134252746
		primary key (id);

create table pa_addr_info_type
(
	id BIGINT(19),
	code VARCHAR(100),
	name VARCHAR(100),
	parent_code VARCHAR(100),
	is_maintained VARCHAR(1),
	belong_network_code VARCHAR(30),
	update_time TIMESTAMP(29),
	credit_level VARCHAR(255),
	lon DEC(18,8),
	lat DEC(18,8),
	another_name VARCHAR(50),
	boundary_points TEXT(max),
	center_point VARCHAR(50),
	coordinates CLOB,
	addr_level INT(10),
	type VARCHAR(2),
	etl_dt VARCHAR(50),
	type_code BIGINT(19),
	user_id BIGINT(19),
	second_user_id BIGINT(19),
	dept_id BIGINT(19),
	manager_num VARCHAR(255),
	rural_grid_type VARCHAR(1),
	dept_code VARCHAR(255),
	user_manager_num VARCHAR(20),
	second_manager_num VARCHAR(20),
	ancestor_codes VARCHAR(255)
);

comment on table pa_addr_info_type is '特色网格边界数据';

comment on column pa_addr_info_type.id is '主键id';

comment on column pa_addr_info_type.code is '行政区划编码';

comment on column pa_addr_info_type.name is '行政区名称';

comment on column pa_addr_info_type.parent_code is '父级行政区划编码';

comment on column pa_addr_info_type.is_maintained is '是否被维护，1-已维护，0-未维护';

comment on column pa_addr_info_type.belong_network_code is '所属网点编码';

comment on column pa_addr_info_type.update_time is '更新时间';

comment on column pa_addr_info_type.credit_level is '信用村标志名';

comment on column pa_addr_info_type.lon is '经度';

comment on column pa_addr_info_type.lat is '维度';

comment on column pa_addr_info_type.another_name is '别名/简称';

comment on column pa_addr_info_type.boundary_points is '边界点集';

comment on column pa_addr_info_type.center_point is '中心点坐标';

comment on column pa_addr_info_type.coordinates is '边界数据';

comment on column pa_addr_info_type.addr_level is '行政编码层级';

comment on column pa_addr_info_type.type is '边界类型 0行政 1产业园区 2小区 3商圈';

comment on column pa_addr_info_type.type_code is '边界类型';

comment on column pa_addr_info_type.user_id is '网格主管员userid';

comment on column pa_addr_info_type.second_user_id is '网格副管员userid';

comment on column pa_addr_info_type.dept_id is '责任机构id';

comment on column pa_addr_info_type.manager_num is '客户经理工号';

comment on column pa_addr_info_type.rural_grid_type is '网格类型(0-非农村网格，1-农村网格)';

comment on column pa_addr_info_type.dept_code is '责任机构code编码';

comment on column pa_addr_info_type.user_manager_num is '主网格员工号';

comment on column pa_addr_info_type.second_manager_num is '副网格员工号';

create unique index INDEX33654329
	on pa_addr_info_type (id);

alter table pa_addr_info_type
	add constraint CONS134252747
		primary key (id);

create table pa_addr_info_type_bak61
(
	id BIGINT(19),
	code VARCHAR(100),
	name VARCHAR(100),
	parent_code VARCHAR(100),
	is_maintained VARCHAR(1),
	belong_network_code VARCHAR(30),
	update_time TIMESTAMP(29),
	credit_level VARCHAR(255),
	lon DEC(18,8),
	lat DEC(18,8),
	another_name VARCHAR(50),
	boundary_points TEXT(max),
	center_point VARCHAR(50),
	coordinates CLOB,
	addr_level INT(10),
	type VARCHAR(2),
	etl_dt VARCHAR(50),
	type_code BIGINT(19),
	user_id BIGINT(19),
	second_user_id BIGINT(19),
	dept_id BIGINT(19),
	manager_num VARCHAR(255),
	rural_grid_type VARCHAR(1),
	dept_code VARCHAR(255),
	user_manager_num VARCHAR(20),
	second_manager_num VARCHAR(20),
	ancestor_codes VARCHAR(255)
);

create table pa_backlog
(
	id BIGINT(19) not null,
	matter_type BIGINT(19),
	matter_content VARCHAR(255),
	status TINYINT(3),
	promoter_user_id BIGINT(19),
	promoter_time TIMESTAMP(29),
	operator_user_id BIGINT(19),
	operator_time TIMESTAMP(29)
);

comment on table pa_backlog is '待办事项';

comment on column pa_backlog.id is '主键';

comment on column pa_backlog.matter_type is '事项类型';

comment on column pa_backlog.matter_content is '事项说明';

comment on column pa_backlog.status is '状态（待处理、已处理）';

comment on column pa_backlog.promoter_user_id is '发起人';

comment on column pa_backlog.promoter_time is '发起时间';

comment on column pa_backlog.operator_user_id is '经办人';

comment on column pa_backlog.operator_time is '经办时间';

create unique index INDEX33654331
	on pa_backlog (id);

alter table pa_backlog
	add constraint CONS134252748
		primary key (id);

create table pa_change_log
(
	id BIGINT(19),
	subject VARCHAR(255),
	operate_type VARCHAR(255),
	log_type VARCHAR(255),
	config_before CLOB,
	config_update CLOB,
	log_content VARCHAR(255),
	operator_id BIGINT(19),
	operator_name VARCHAR(255),
	create_time TIMESTAMP(29)
);

comment on table pa_change_log is '变更记录表';

comment on column pa_change_log.id is '主键';

comment on column pa_change_log.subject is '变更主体';

comment on column pa_change_log.operate_type is '操作类型（0-新增,1-修改,2-删除）';

comment on column pa_change_log.log_type is '日志类型（0-网格,1-网格管理员,2-锚点）';

comment on column pa_change_log.config_before is '操作前配置';

comment on column pa_change_log.config_update is '操作后配置';

comment on column pa_change_log.log_content is '操作内容';

comment on column pa_change_log.operator_id is '操作人Id';

comment on column pa_change_log.operator_name is '操作人姓名';

comment on column pa_change_log.create_time is '操作时间';

create unique index INDEX33654487
	on pa_change_log (id);

alter table pa_change_log
	add constraint CONS134252830
		primary key (id);

create table pa_custom_message
(
	id BIGINT(19),
	nm VARCHAR(50),
	message_type VARCHAR(10),
	message_title VARCHAR(255),
	message_sources VARCHAR(10),
	notify_object VARCHAR(50),
	notify_time TIMESTAMP(29),
	priority VARCHAR(255),
	message_state VARCHAR(10),
	message_star VARCHAR(10),
	message_read_state VARCHAR(10),
	message_remark VARCHAR(255),
	message_details TEXT(max),
	network_code VARCHAR(255),
	manager_id VARCHAR(255),
	create_by VARCHAR(64),
	create_time TIMESTAMP(29),
	update_by VARCHAR(64),
	update_time TIMESTAMP(29),
	del_flag TINYINT(3),
	constraint INDEX4642278848232100
		unique (notify_object, message_state)
);

comment on table pa_custom_message is '消息表';

comment on column pa_custom_message.nm is '客户证件号';

comment on column pa_custom_message.message_type is '消息类型';

comment on column pa_custom_message.message_title is '消息标题';

comment on column pa_custom_message.message_sources is '消息来源';

comment on column pa_custom_message.notify_object is '通知对象';

comment on column pa_custom_message.notify_time is '通知时间';

comment on column pa_custom_message.priority is '优先级';

comment on column pa_custom_message.message_state is '消息状态 1-待处理，2-已处理';

comment on column pa_custom_message.message_star is '星标 0-否,1-是';

comment on column pa_custom_message.message_read_state is '是否已读 0-否，1-是';

comment on column pa_custom_message.message_remark is '消息备注';

comment on column pa_custom_message.message_details is '消息详情';

comment on column pa_custom_message.network_code is '所属机构';

comment on column pa_custom_message.manager_id is '经办人';

comment on column pa_custom_message.create_by is '创建人';

comment on column pa_custom_message.create_time is '创建时间';

comment on column pa_custom_message.update_by is '修改人';

comment on column pa_custom_message.update_time is '修改时间';

comment on column pa_custom_message.del_flag is '删除标识 0-未删除，1-已删除';

create unique index INDEX33654333
	on pa_custom_message (id);

alter table pa_custom_message
	add constraint CONS134252749
		primary key (id);

create table pa_out_data_community_info
(
	id INT(10),
	ajk_id VARCHAR(100) not null
		constraint INDEX33654337
			unique,
	name VARCHAR(255),
	province_name VARCHAR(255),
	city_name VARCHAR(255),
	district_name VARCHAR(255),
	town_name VARCHAR(255),
	village_name VARCHAR(255),
	province_code VARCHAR(255),
	city_code VARCHAR(255),
	admin_code VARCHAR(255),
	town_code VARCHAR(255),
	village_code VARCHAR(255),
	address TEXT(max),
	lon VARCHAR(255),
	lat VARCHAR(255),
	points CLOB,
	property_type VARCHAR(50),
	ownership_class VARCHAR(50),
	completion_time VARCHAR(255),
	property_rights VARCHAR(50),
	total_households INT(10),
	total_building INT(10),
	total_floor_area DEC(12,2),
	plot_ratio DEC(12,2),
	greening_rate VARCHAR(50),
	building_type VARCHAR(100),
	commercial_circle VARCHAR(100),
	is_central_heating VARCHAR(50),
	utilities_type VARCHAR(50),
	parking_space VARCHAR(100),
	property_fee VARCHAR(100),
	parking_fee VARCHAR(100),
	parking_management_fee VARCHAR(100),
	property_management_company VARCHAR(100),
	developers VARCHAR(255)
);

comment on table pa_out_data_community_info is '小区数据';

comment on column pa_out_data_community_info.id is 'id';

comment on column pa_out_data_community_info.ajk_id is '安居客id';

comment on column pa_out_data_community_info.name is '小区名称';

comment on column pa_out_data_community_info.province_name is '省份名称';

comment on column pa_out_data_community_info.city_name is '城市名称';

comment on column pa_out_data_community_info.district_name is '区域名称';

comment on column pa_out_data_community_info.town_name is '街道名称';

comment on column pa_out_data_community_info.village_name is '社区名称';

comment on column pa_out_data_community_info.province_code is '省份行政区域编码';

comment on column pa_out_data_community_info.city_code is '城市行政区域编码';

comment on column pa_out_data_community_info.admin_code is '区域行政区域编码';

comment on column pa_out_data_community_info.town_code is '街道行政区域编码';

comment on column pa_out_data_community_info.village_code is '社区行政区域编码';

comment on column pa_out_data_community_info.address is '小区地址';

comment on column pa_out_data_community_info.lon is '经度';

comment on column pa_out_data_community_info.lat is '纬度';

comment on column pa_out_data_community_info.points is '围栏';

comment on column pa_out_data_community_info.property_type is '物业类型';

comment on column pa_out_data_community_info.ownership_class is '权属类别';

comment on column pa_out_data_community_info.completion_time is '竣工时间';

comment on column pa_out_data_community_info.property_rights is '产权年限';

comment on column pa_out_data_community_info.total_households is '总户数';

comment on column pa_out_data_community_info.total_building is '楼栋数';

comment on column pa_out_data_community_info.total_floor_area is '总建面积';

comment on column pa_out_data_community_info.plot_ratio is '容积率';

comment on column pa_out_data_community_info.greening_rate is '绿化率';

comment on column pa_out_data_community_info.building_type is '建筑类型';

comment on column pa_out_data_community_info.commercial_circle is '所属商圈';

comment on column pa_out_data_community_info.is_central_heating is '统一供暖';

comment on column pa_out_data_community_info.utilities_type is '供水供电';

comment on column pa_out_data_community_info.parking_space is '停车位';

comment on column pa_out_data_community_info.property_fee is '物业费';

comment on column pa_out_data_community_info.parking_fee is '停车费';

comment on column pa_out_data_community_info.parking_management_fee is '车位管理费';

comment on column pa_out_data_community_info.property_management_company is '物业公司';

comment on column pa_out_data_community_info.developers is '开发商';

create unique index INDEX33654336
	on pa_out_data_community_info (id);

alter table pa_out_data_community_info
	add constraint CONS134252750
		primary key (id);

create table pa_out_data_company_total
(
	id INT(10),
	province_name VARCHAR(255),
	city_name VARCHAR(255),
	district_name VARCHAR(255),
	town_name VARCHAR(255),
	province_code VARCHAR(255),
	city_code VARCHAR(255),
	admin_code VARCHAR(255),
	town_code VARCHAR(255),
	total INT(10)
);

comment on table pa_out_data_company_total is '企业数量数据';

comment on column pa_out_data_company_total.id is 'id';

comment on column pa_out_data_company_total.province_name is '省份名称';

comment on column pa_out_data_company_total.city_name is '城市名称';

comment on column pa_out_data_company_total.district_name is '区域名称';

comment on column pa_out_data_company_total.town_name is '街道名称';

comment on column pa_out_data_company_total.province_code is '省份行政区域编码';

comment on column pa_out_data_company_total.city_code is '城市行政区域编码';

comment on column pa_out_data_company_total.admin_code is '区域行政区域编码';

comment on column pa_out_data_company_total.town_code is '街道行政区域编码';

comment on column pa_out_data_company_total.total is '企业数量';

create unique index INDEX33654339
	on pa_out_data_company_total (id);

alter table pa_out_data_company_total
	add constraint CONS134252752
		primary key (id);

create table pa_out_data_economic_info
(
	id INT(10),
	province_name VARCHAR(255),
	city_name VARCHAR(255),
	district_name VARCHAR(255),
	province_code VARCHAR(255),
	city_code VARCHAR(255),
	admin_code VARCHAR(255),
	gdp VARCHAR(255),
	towner_disposable_income VARCHAR(255),
	village_disposable_income VARCHAR(255)
);

comment on table pa_out_data_economic_info is '经济概况数据';

comment on column pa_out_data_economic_info.id is 'id';

comment on column pa_out_data_economic_info.province_name is '省份名称';

comment on column pa_out_data_economic_info.city_name is '城市名称';

comment on column pa_out_data_economic_info.district_name is '区域名称';

comment on column pa_out_data_economic_info.province_code is '省份行政区域编码';

comment on column pa_out_data_economic_info.city_code is '城市行政区域编码';

comment on column pa_out_data_economic_info.admin_code is '区域行政区域编码';

comment on column pa_out_data_economic_info.gdp is 'GDP数据';

comment on column pa_out_data_economic_info.towner_disposable_income is '城镇居民可支配收入';

comment on column pa_out_data_economic_info.village_disposable_income is '农村居民可支配收入';

create unique index INDEX33654341
	on pa_out_data_economic_info (id);

alter table pa_out_data_economic_info
	add constraint CONS134252753
		primary key (id);

create table pa_out_data_financial_org
(
	id INT(10),
	org_code VARCHAR(255),
	org_name VARCHAR(255),
	org_address VARCHAR(255),
	issue_date VARCHAR(255),
	approval_date VARCHAR(255),
	issue_authority VARCHAR(255),
	serial VARCHAR(255),
	scope CLOB,
	exit_date VARCHAR(255),
	lon VARCHAR(255),
	lat VARCHAR(255),
	province_name VARCHAR(255),
	city_name VARCHAR(255),
	district_name VARCHAR(255),
	town_name VARCHAR(255),
	village_name VARCHAR(255),
	province_code VARCHAR(255),
	city_code VARCHAR(255),
	admin_code VARCHAR(255),
	town_code VARCHAR(255),
	village_code VARCHAR(255)
);

comment on table pa_out_data_financial_org is '金融机构数据';

comment on column pa_out_data_financial_org.id is 'id';

comment on column pa_out_data_financial_org.org_code is '机构编码';

comment on column pa_out_data_financial_org.org_name is '机构名称';

comment on column pa_out_data_financial_org.org_address is '机构住所';

comment on column pa_out_data_financial_org.issue_date is '发证日期';

comment on column pa_out_data_financial_org.approval_date is '批准日期';

comment on column pa_out_data_financial_org.issue_authority is '发证机关';

comment on column pa_out_data_financial_org.serial is '流水号';

comment on column pa_out_data_financial_org.scope is '业务范围';

comment on column pa_out_data_financial_org.exit_date is '退出或失控日期';

comment on column pa_out_data_financial_org.lon is '经度';

comment on column pa_out_data_financial_org.lat is '纬度';

comment on column pa_out_data_financial_org.province_name is '省份名称';

comment on column pa_out_data_financial_org.city_name is '城市名称';

comment on column pa_out_data_financial_org.district_name is '区域名称';

comment on column pa_out_data_financial_org.town_name is '街道名称';

comment on column pa_out_data_financial_org.village_name is '社区名称';

comment on column pa_out_data_financial_org.province_code is '省份行政区域编码';

comment on column pa_out_data_financial_org.city_code is '城市行政区域编码';

comment on column pa_out_data_financial_org.admin_code is '区域行政区域编码';

comment on column pa_out_data_financial_org.town_code is '街道行政区域编码';

comment on column pa_out_data_financial_org.village_code is '社区行政区域编码';

create unique index INDEX33654343
	on pa_out_data_financial_org (id);

alter table pa_out_data_financial_org
	add constraint CONS134252754
		primary key (id);

create table pa_out_data_market_info
(
	id INT(10),
	mapbar_id VARCHAR(100),
	navinfo_id VARCHAR(255),
	name VARCHAR(255),
	ename VARCHAR(255),
	lon VARCHAR(255),
	lat VARCHAR(255),
	points TEXT(max),
	province_name VARCHAR(255),
	city_name VARCHAR(255),
	district_name VARCHAR(255),
	town_name VARCHAR(255),
	village_name VARCHAR(255),
	province_code VARCHAR(255),
	city_code VARCHAR(255),
	admin_code VARCHAR(255),
	town_code VARCHAR(255),
	village_code VARCHAR(255),
	kind_s VARCHAR(255),
	kind_m VARCHAR(255),
	kind_b VARCHAR(255),
	address TEXT(max),
	eaddress TEXT(max),
	src VARCHAR(255),
	telephone VARCHAR(255),
	hot VARCHAR(255)
);

comment on table pa_out_data_market_info is '专业市场数据';

comment on column pa_out_data_market_info.id is 'id';

comment on column pa_out_data_market_info.mapbar_id is 'mapbar_id';

comment on column pa_out_data_market_info.navinfo_id is 'navinfo_id';

comment on column pa_out_data_market_info.name is '市场名称';

comment on column pa_out_data_market_info.ename is '英文名';

comment on column pa_out_data_market_info.lon is '经度';

comment on column pa_out_data_market_info.lat is '纬度';

comment on column pa_out_data_market_info.points is '围栏';

comment on column pa_out_data_market_info.province_name is '省份名称';

comment on column pa_out_data_market_info.city_name is '城市名称';

comment on column pa_out_data_market_info.district_name is '区域名称';

comment on column pa_out_data_market_info.town_name is '街道名称';

comment on column pa_out_data_market_info.village_name is '社区名称';

comment on column pa_out_data_market_info.province_code is '省份行政区域编码';

comment on column pa_out_data_market_info.city_code is '城市行政区域编码';

comment on column pa_out_data_market_info.admin_code is '区域行政区域编码';

comment on column pa_out_data_market_info.town_code is '街道行政区域编码';

comment on column pa_out_data_market_info.village_code is '社区行政区域编码';

comment on column pa_out_data_market_info.kind_s is '分类代码';

comment on column pa_out_data_market_info.kind_m is '分类代码';

comment on column pa_out_data_market_info.kind_b is '分类代码';

comment on column pa_out_data_market_info.address is '地址';

comment on column pa_out_data_market_info.eaddress is '英文地址';

comment on column pa_out_data_market_info.src is 'src';

comment on column pa_out_data_market_info.telephone is '电话';

comment on column pa_out_data_market_info.hot is 'hot';

create unique index INDEX33654345
	on pa_out_data_market_info (id);

alter table pa_out_data_market_info
	add constraint CONS134252755
		primary key (id);

create table pa_out_data_park
(
	id INT(10),
	name VARCHAR(255),
	country_name VARCHAR(255),
	province_name VARCHAR(255),
	city_name VARCHAR(255),
	district_name VARCHAR(255),
	town_name VARCHAR(255),
	village_name VARCHAR(255),
	country_code VARCHAR(255),
	province_code VARCHAR(255),
	city_code VARCHAR(255),
	admin_code VARCHAR(255),
	town_code VARCHAR(255),
	village_code VARCHAR(255),
	address TEXT(max),
	land_area DEC(20,2),
	enterprise_count DEC(20,2),
	lon VARCHAR(255),
	lat VARCHAR(255),
	points TEXT(max),
	ext_id VARCHAR(255)
		constraint INDEX33654348
			unique
);

comment on table pa_out_data_park is '园区明细';

comment on column pa_out_data_park.id is 'id';

comment on column pa_out_data_park.name is '园区名称';

comment on column pa_out_data_park.country_name is '国家名称';

comment on column pa_out_data_park.province_name is '省份名称';

comment on column pa_out_data_park.city_name is '城市名称';

comment on column pa_out_data_park.district_name is '区域名称';

comment on column pa_out_data_park.town_name is '街道名称';

comment on column pa_out_data_park.village_name is '社区名称';

comment on column pa_out_data_park.country_code is '国家行政区域编码';

comment on column pa_out_data_park.province_code is '省份行政区域编码';

comment on column pa_out_data_park.city_code is '城市行政区域编码';

comment on column pa_out_data_park.admin_code is '区域行政区域编码';

comment on column pa_out_data_park.town_code is '街道行政区域编码';

comment on column pa_out_data_park.village_code is '社区行政区域编码';

comment on column pa_out_data_park.address is '园区地址';

comment on column pa_out_data_park.land_area is '园区面积(亩)';

comment on column pa_out_data_park.enterprise_count is '注册企业(家)';

comment on column pa_out_data_park.lon is '经度';

comment on column pa_out_data_park.lat is '纬度';

comment on column pa_out_data_park.points is '围栏';

comment on column pa_out_data_park.ext_id is '外部来源标识id';

create unique index INDEX33654347
	on pa_out_data_park (id);

alter table pa_out_data_park
	add constraint CONS134252756
		primary key (id);

create table pa_out_data_people_num
(
	id INT(10),
	province_name VARCHAR(255),
	city_name VARCHAR(255),
	district_name VARCHAR(255),
	town_name VARCHAR(255),
	province_code VARCHAR(255),
	city_code VARCHAR(255),
	admin_code VARCHAR(255),
	town_code VARCHAR(255),
	years VARCHAR(255),
	total_num INT(10),
	"0_14_num" INT(10),
	"15_59_num" INT(10),
	"60_num" INT(10),
	"65_num" INT(10),
	local_num INT(10)
);

comment on table pa_out_data_people_num is '街道级人口数据';

comment on column pa_out_data_people_num.id is 'id';

comment on column pa_out_data_people_num.province_name is '省份名称';

comment on column pa_out_data_people_num.city_name is '城市名称';

comment on column pa_out_data_people_num.district_name is '区域名称';

comment on column pa_out_data_people_num.town_name is '街道名称';

comment on column pa_out_data_people_num.province_code is '省份行政区域编码';

comment on column pa_out_data_people_num.city_code is '城市行政区域编码';

comment on column pa_out_data_people_num.admin_code is '区域行政区域编码';

comment on column pa_out_data_people_num.town_code is '街道行政区域编码';

comment on column pa_out_data_people_num.years is '统计年份';

comment on column pa_out_data_people_num.total_num is '总人口';

comment on column pa_out_data_people_num."0_14_num" is '0-14岁人口（人）';

comment on column pa_out_data_people_num."15_59_num" is '15-59岁人口（人）';

comment on column pa_out_data_people_num."60_num" is '60岁及以上人口（人）';

comment on column pa_out_data_people_num."65_num" is '65岁及以上人口（人）';

comment on column pa_out_data_people_num.local_num is '居住本乡、镇、街道，户口在本乡、镇、街道人口（人）';

create unique index INDEX33654350
	on pa_out_data_people_num (id);

alter table pa_out_data_people_num
	add constraint CONS134252758
		primary key (id);

create table pa_out_data_shopping_mall
(
	id INT(10),
	shop_id VARCHAR(100),
	name VARCHAR(255),
	province_name VARCHAR(255),
	city_name VARCHAR(255),
	district_name VARCHAR(255),
	town_name VARCHAR(255),
	village_name VARCHAR(255),
	province_code VARCHAR(255),
	city_code VARCHAR(255),
	admin_code VARCHAR(255),
	town_code VARCHAR(255),
	village_code VARCHAR(255),
	lon VARCHAR(255),
	lat VARCHAR(255),
	points CLOB,
	is_closed VARCHAR(255),
	phone VARCHAR(255),
	region VARCHAR(255),
	address TEXT(max),
	avg_price TEXT(max),
	cross_road VARCHAR(255),
	stars VARCHAR(255),
	review_count VARCHAR(255),
	good_remarks VARCHAR(255),
	bad_remarks VARCHAR(255),
	product_rating VARCHAR(255),
	environment_rating VARCHAR(255),
	service_rating VARCHAR(255),
	default_pic TEXT(max)
);

comment on table pa_out_data_shopping_mall is '商场数据';

comment on column pa_out_data_shopping_mall.id is 'id';

comment on column pa_out_data_shopping_mall.shop_id is '商场id';

comment on column pa_out_data_shopping_mall.name is '商场名称';

comment on column pa_out_data_shopping_mall.province_name is '省份名称';

comment on column pa_out_data_shopping_mall.city_name is '城市名称';

comment on column pa_out_data_shopping_mall.district_name is '区域名称';

comment on column pa_out_data_shopping_mall.town_name is '街道名称';

comment on column pa_out_data_shopping_mall.village_name is '社区名称';

comment on column pa_out_data_shopping_mall.province_code is '省份行政区域编码';

comment on column pa_out_data_shopping_mall.city_code is '城市行政区域编码';

comment on column pa_out_data_shopping_mall.admin_code is '区域行政区域编码';

comment on column pa_out_data_shopping_mall.town_code is '街道行政区域编码';

comment on column pa_out_data_shopping_mall.village_code is '社区行政区域编码';

comment on column pa_out_data_shopping_mall.lon is '经度';

comment on column pa_out_data_shopping_mall.lat is '纬度';

comment on column pa_out_data_shopping_mall.points is '围栏';

comment on column pa_out_data_shopping_mall.is_closed is '是否停业';

comment on column pa_out_data_shopping_mall.phone is '电话';

comment on column pa_out_data_shopping_mall.region is '区域';

comment on column pa_out_data_shopping_mall.address is '地址';

comment on column pa_out_data_shopping_mall.avg_price is '平均消费';

comment on column pa_out_data_shopping_mall.cross_road is '十字路口';

comment on column pa_out_data_shopping_mall.stars is '星级';

comment on column pa_out_data_shopping_mall.review_count is '评论数';

comment on column pa_out_data_shopping_mall.good_remarks is '好评数';

comment on column pa_out_data_shopping_mall.bad_remarks is '差评数';

comment on column pa_out_data_shopping_mall.product_rating is '产品评分';

comment on column pa_out_data_shopping_mall.environment_rating is '环境评分';

comment on column pa_out_data_shopping_mall.service_rating is '服务评分';

comment on column pa_out_data_shopping_mall.default_pic is '图片';

create unique index INDEX33654352
	on pa_out_data_shopping_mall (id);

alter table pa_out_data_shopping_mall
	add constraint CONS134252759
		primary key (id);

create table pa_schedule
(
	id BIGINT(19),
	schedule_title VARCHAR(255),
	start_time TIMESTAMP(29),
	notice_time_type BIGINT(19),
	end_time TIMESTAMP(29),
	need_notice TINYINT(3),
	auto_finish TINYINT(3),
	notice_time TIMESTAMP(29),
	mark_color VARCHAR(255),
	remark VARCHAR(255),
	create_by VARCHAR(255),
	batch_id VARCHAR(255),
	task_id BIGINT(19),
	owner BIGINT(19),
	owner_ids VARCHAR(255),
	owner_names VARCHAR(255),
	status BIGINT(19)
);

comment on table pa_schedule is '日程表';

comment on column pa_schedule.id is '主键';

comment on column pa_schedule.schedule_title is '日程标题';

comment on column pa_schedule.start_time is '开始时间';

comment on column pa_schedule.notice_time_type is '提醒时间类型';

comment on column pa_schedule.end_time is '结束时间';

comment on column pa_schedule.need_notice is '是否开启提醒';

comment on column pa_schedule.auto_finish is '到期是否自动完成';

comment on column pa_schedule.notice_time is '提醒时间';

comment on column pa_schedule.mark_color is '标记颜色';

comment on column pa_schedule.remark is '备注';

comment on column pa_schedule.create_by is '创建人';

comment on column pa_schedule.batch_id is '批次号';

comment on column pa_schedule.task_id is '任务id';

comment on column pa_schedule.owner is '日程拥有者';

comment on column pa_schedule.owner_ids is '日程拥有者id';

comment on column pa_schedule.owner_names is '日程拥有者姓名';

comment on column pa_schedule.status is '状态';

create unique index INDEX33654354
	on pa_schedule (id);

alter table pa_schedule
	add constraint CONS134252760
		primary key (id);

create table pa_task
(
	id BIGINT(19),
	task_name VARCHAR(255),
	task_type BIGINT(19),
	task_type_name VARCHAR(255),
	dept_id VARCHAR(255),
	dept_name VARCHAR(255),
	task_level TINYINT(3),
	start_time TIMESTAMP(29),
	end_time TIMESTAMP(29),
	create_by VARCHAR(255),
	owner BIGINT(19),
	task_status TINYINT(3),
	custom_group_id VARCHAR(255),
	custom_group_name VARCHAR(255),
	is_cycle INT(10) default 0,
	cycle_type INT(10),
	cycle_time INT(10),
	task_desc VARCHAR(255),
	complete_process VARCHAR(50) default '0%',
	task_object_id INT(10),
	delete_flag INT(10) default 0 not null
);

comment on table pa_task is '任务表';

comment on column pa_task.id is '主键';

comment on column pa_task.task_name is '任务名称';

comment on column pa_task.task_type is '任务类型';

comment on column pa_task.task_type_name is '任务类型名称';

comment on column pa_task.dept_id is '责任机构id';

comment on column pa_task.dept_name is '责任机构名称';

comment on column pa_task.task_level is '任务优先级';

comment on column pa_task.start_time is '开始时间';

comment on column pa_task.end_time is '结束时间';

comment on column pa_task.create_by is '创建人';

comment on column pa_task.owner is '拥有者';

comment on column pa_task.task_status is '任务状态（0待开始 1考核中 2已结束）';

comment on column pa_task.custom_group_id is '客群id';

comment on column pa_task.custom_group_name is '客群名称';

comment on column pa_task.is_cycle is '是否重复周期（0-否，1-是）';

comment on column pa_task.cycle_type is '周期类型';

comment on column pa_task.cycle_time is '周期时间';

comment on column pa_task.task_desc is '任务描述';

comment on column pa_task.complete_process is '完成度';

comment on column pa_task.task_object_id is '客户类型1-绩效，2-授信，3-建档';

comment on column pa_task.delete_flag is '删除标志 0-未删除 1-已删除';

create unique index INDEX33654498
	on pa_task (id);

alter table pa_task
	add constraint CONS134252835
		primary key (id);

create table pa_task_detail
(
	id BIGINT(19),
	task_id BIGINT(19),
	client_id BIGINT(19),
	customer_name VARCHAR(255),
	customer_identity VARCHAR(255),
	status INT(10),
	integrity VARCHAR(50) default '0%',
	task_method VARCHAR(255),
	task_method_code INT(10),
	contact_time TIMESTAMP(29),
	pic VARCHAR(1024),
	remark VARCHAR(255),
	manager_job_number VARCHAR(11),
	manager_name VARCHAR(50),
	plan_date TIMESTAMP(29),
	plan_method VARCHAR(255),
	plan_method_code INT(10),
	dept_id BIGINT(19),
	dept_name VARCHAR(255),
	separate_bank VARCHAR(255),
	feedback TEXT(max),
	delete_flag INT(10) default 0 not null,
	customer_nm VARCHAR(8188),
	customer_type INT(10)
);

comment on column pa_task_detail.task_id is '任务id';

comment on column pa_task_detail.client_id is '客户id';

comment on column pa_task_detail.customer_name is '客户名称';

comment on column pa_task_detail.customer_identity is '身份证号 统一社会信用代码';

comment on column pa_task_detail.status is '任务状态（0-未完成，1-已完成，2-已结束）';

comment on column pa_task_detail.integrity is '信息完整度';

comment on column pa_task_detail.task_method is '任务方式';

comment on column pa_task_detail.contact_time is '联系时间';

comment on column pa_task_detail.pic is '图片';

comment on column pa_task_detail.remark is '备注';

comment on column pa_task_detail.manager_job_number is '客户经理工号';

comment on column pa_task_detail.manager_name is '执行人';

comment on column pa_task_detail.plan_date is '预约日期';

comment on column pa_task_detail.plan_method is '预约方式';

comment on column pa_task_detail.plan_method_code is '预约方式Code';

comment on column pa_task_detail.dept_id is '责任机构id';

comment on column pa_task_detail.dept_name is '机构名称';

comment on column pa_task_detail.separate_bank is '分行名称';

comment on column pa_task_detail.feedback is '反馈内容json';

comment on column pa_task_detail.delete_flag is '删除标志 0-未删 1-已删';

create unique index INDEX33654500
	on pa_task_detail (id);

alter table pa_task_detail
	add constraint CONS134252836
		primary key (id);

create table pa_task_position
(
	id BIGINT(19),
	manager_num VARCHAR(255) not null,
	lon DEC(18,8) not null,
	lat DEC(18,8) not null,
	date TIMESTAMP(36,6) not null
);

comment on table pa_task_position is '任务时空轨迹';

comment on column pa_task_position.id is '主键';

comment on column pa_task_position.manager_num is '客户经理工号';

comment on column pa_task_position.lon is '经度';

comment on column pa_task_position.lat is '维度';

comment on column pa_task_position.date is '时间';

create unique index INDEX33654356
	on pa_task_position (id);

alter table pa_task_position
	add constraint CONS134252761
		primary key (id);

create table pa_view_deposit_module_manager
(
	deposit_module_id BIGINT(19),
	manager_code VARCHAR(255),
	customer_type VARCHAR(255),
	save_ftp VARCHAR(255),
	save_balance INT(10),
	save_balance_last_month VARCHAR(255),
	save_balance_begin_year VARCHAR(255),
	save_day_avg VARCHAR(255),
	save_day_avg_last_month VARCHAR(255),
	save_day_avg_begin_year VARCHAR(255),
	deposit_save_balance VARCHAR(255),
	deposit_save_balance_last_month VARCHAR(255),
	deposit_save_balance_begin_year VARCHAR(255),
	deposit_save_day_avg VARCHAR(255),
	deposit_save_day_avg_last_month VARCHAR(255),
	deposit_save_day_avg_begin_year VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_deposit_module_manager is '小微视图-存款模块-客户经理纬度';

comment on column pa_view_deposit_module_manager.deposit_module_id is '存款模块id';

comment on column pa_view_deposit_module_manager.manager_code is '客户经理编号';

comment on column pa_view_deposit_module_manager.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户';

comment on column pa_view_deposit_module_manager.save_ftp is '存款FTP (万元，保留两位小数)';

comment on column pa_view_deposit_module_manager.save_balance is '存款余额(万元，整数)';

comment on column pa_view_deposit_module_manager.save_balance_last_month is '存款余额较上月(万元，整数)';

comment on column pa_view_deposit_module_manager.save_balance_begin_year is '存款余额较年初(万元，整数)';

comment on column pa_view_deposit_module_manager.save_day_avg is '存款日均(万元，整数)';

comment on column pa_view_deposit_module_manager.save_day_avg_last_month is '存款日均较上月(万元，整数)';

comment on column pa_view_deposit_module_manager.save_day_avg_begin_year is '存款日均较年初(万元，整数)';

comment on column pa_view_deposit_module_manager.deposit_save_balance is '储蓄存款余额(万元，整数)';

comment on column pa_view_deposit_module_manager.deposit_save_balance_last_month is '储蓄存款余额较上月(万元，整数)';

comment on column pa_view_deposit_module_manager.deposit_save_balance_begin_year is '储蓄存款余额较年初(万元，整数)';

comment on column pa_view_deposit_module_manager.deposit_save_day_avg is '储蓄存款日均(万元，整数)';

comment on column pa_view_deposit_module_manager.deposit_save_day_avg_last_month is '储蓄存款日均较上月(万元，整数)';

comment on column pa_view_deposit_module_manager.deposit_save_day_avg_begin_year is '储蓄存款日均较年初(万元，整数)';

comment on column pa_view_deposit_module_manager.create_time is '创建时间';

comment on column pa_view_deposit_module_manager.update_time is '更新时间';

create unique index INDEX33654358
	on pa_view_deposit_module_manager (deposit_module_id);

alter table pa_view_deposit_module_manager
	add constraint CONS134252762
		primary key (deposit_module_id);

create table pa_view_deposit_module_manager_grid
(
	deposit_module_id BIGINT(19),
	manager_code VARCHAR(255),
	grid_code VARCHAR(255),
	customer_type VARCHAR(255),
	save_ftp VARCHAR(255),
	save_balance INT(10),
	save_balance_last_month VARCHAR(255),
	save_balance_begin_year VARCHAR(255),
	save_day_avg VARCHAR(255),
	save_day_avg_last_month VARCHAR(255),
	save_day_avg_begin_year VARCHAR(255),
	deposit_save_balance VARCHAR(255),
	deposit_save_balance_last_month VARCHAR(255),
	deposit_save_balance_begin_year VARCHAR(255),
	deposit_save_day_avg VARCHAR(255),
	deposit_save_day_avg_last_month VARCHAR(255),
	deposit_save_day_avg_begin_year VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_deposit_module_manager_grid is '小微视图-存款模块-网格、客户经理纬度';

comment on column pa_view_deposit_module_manager_grid.deposit_module_id is '存款模块id';

comment on column pa_view_deposit_module_manager_grid.manager_code is '客户经理编号';

comment on column pa_view_deposit_module_manager_grid.grid_code is '网格编号';

comment on column pa_view_deposit_module_manager_grid.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户';

comment on column pa_view_deposit_module_manager_grid.save_ftp is '存款FTP (万元，保留两位小数)';

comment on column pa_view_deposit_module_manager_grid.save_balance is '存款余额(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.save_balance_last_month is '存款余额较上月(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.save_balance_begin_year is '存款余额较年初(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.save_day_avg is '存款日均(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.save_day_avg_last_month is '存款日均较上月(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.save_day_avg_begin_year is '存款日均较年初(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.deposit_save_balance is '储蓄存款余额(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.deposit_save_balance_last_month is '储蓄存款余额较上月(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.deposit_save_balance_begin_year is '储蓄存款余额较年初(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.deposit_save_day_avg is '储蓄存款日均(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.deposit_save_day_avg_last_month is '储蓄存款日均较上月(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.deposit_save_day_avg_begin_year is '储蓄存款日均较年初(万元，整数)';

comment on column pa_view_deposit_module_manager_grid.create_time is '创建时间';

comment on column pa_view_deposit_module_manager_grid.update_time is '更新时间';

create unique index INDEX33654360
	on pa_view_deposit_module_manager_grid (deposit_module_id);

alter table pa_view_deposit_module_manager_grid
	add constraint CONS134252763
		primary key (deposit_module_id);

create table pa_view_deposit_module_org
(
	deposit_module_id BIGINT(19),
	org_code VARCHAR(255),
	customer_type VARCHAR(255),
	save_ftp VARCHAR(255),
	save_balance INT(10),
	save_balance_last_month VARCHAR(255),
	save_balance_begin_year VARCHAR(255),
	save_day_avg VARCHAR(255),
	save_day_avg_last_month VARCHAR(255),
	save_day_avg_begin_year VARCHAR(255),
	deposit_save_balance VARCHAR(255),
	deposit_save_balance_last_month VARCHAR(255),
	deposit_save_balance_begin_year VARCHAR(255),
	deposit_save_day_avg VARCHAR(255),
	deposit_save_day_avg_last_month VARCHAR(255),
	deposit_save_day_avg_begin_year VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_deposit_module_org is '小微视图-存款模块-机构纬度';

comment on column pa_view_deposit_module_org.deposit_module_id is '存款模块id';

comment on column pa_view_deposit_module_org.org_code is '机构编码';

comment on column pa_view_deposit_module_org.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户';

comment on column pa_view_deposit_module_org.save_ftp is '存款FTP (万元，保留两位小数)';

comment on column pa_view_deposit_module_org.save_balance is '存款余额(万元，整数)';

comment on column pa_view_deposit_module_org.save_balance_last_month is '存款余额较上月(万元，整数)';

comment on column pa_view_deposit_module_org.save_balance_begin_year is '存款余额较年初(万元，整数)';

comment on column pa_view_deposit_module_org.save_day_avg is '存款日均(万元，整数)';

comment on column pa_view_deposit_module_org.save_day_avg_last_month is '存款日均较上月(万元，整数)';

comment on column pa_view_deposit_module_org.save_day_avg_begin_year is '存款日均较年初(万元，整数)';

comment on column pa_view_deposit_module_org.deposit_save_balance is '储蓄存款余额(万元，整数)';

comment on column pa_view_deposit_module_org.deposit_save_balance_last_month is '储蓄存款余额较上月(万元，整数)';

comment on column pa_view_deposit_module_org.deposit_save_balance_begin_year is '储蓄存款余额较年初(万元，整数)';

comment on column pa_view_deposit_module_org.deposit_save_day_avg is '储蓄存款日均(万元，整数)';

comment on column pa_view_deposit_module_org.deposit_save_day_avg_last_month is '储蓄存款日均较上月(万元，整数)';

comment on column pa_view_deposit_module_org.deposit_save_day_avg_begin_year is '储蓄存款日均较年初(万元，整数)';

comment on column pa_view_deposit_module_org.create_time is '创建时间';

comment on column pa_view_deposit_module_org.update_time is '更新时间';

create unique index INDEX33654362
	on pa_view_deposit_module_org (deposit_module_id);

alter table pa_view_deposit_module_org
	add constraint CONS134252764
		primary key (deposit_module_id);

create table pa_view_deposit_module_org_grid
(
	deposit_module_id BIGINT(19),
	org_code VARCHAR(255),
	grid_code VARCHAR(255),
	customer_type VARCHAR(255),
	save_ftp VARCHAR(255),
	save_balance INT(10),
	save_balance_last_month VARCHAR(255),
	save_balance_begin_year VARCHAR(255),
	save_day_avg VARCHAR(255),
	save_day_avg_last_month VARCHAR(255),
	save_day_avg_begin_year VARCHAR(255),
	deposit_save_balance VARCHAR(255),
	deposit_save_balance_last_month VARCHAR(255),
	deposit_save_balance_begin_year VARCHAR(255),
	deposit_save_day_avg VARCHAR(255),
	deposit_save_day_avg_last_month VARCHAR(255),
	deposit_save_day_avg_begin_year VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_deposit_module_org_grid is '小微视图-存款模块-网格、机构纬度';

comment on column pa_view_deposit_module_org_grid.deposit_module_id is '存款模块id';

comment on column pa_view_deposit_module_org_grid.org_code is '机构编码';

comment on column pa_view_deposit_module_org_grid.grid_code is '网格编号';

comment on column pa_view_deposit_module_org_grid.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户';

comment on column pa_view_deposit_module_org_grid.save_ftp is '存款FTP (万元，保留两位小数)';

comment on column pa_view_deposit_module_org_grid.save_balance is '存款余额(万元，整数)';

comment on column pa_view_deposit_module_org_grid.save_balance_last_month is '存款余额较上月(万元，整数)';

comment on column pa_view_deposit_module_org_grid.save_balance_begin_year is '存款余额较年初(万元，整数)';

comment on column pa_view_deposit_module_org_grid.save_day_avg is '存款日均(万元，整数)';

comment on column pa_view_deposit_module_org_grid.save_day_avg_last_month is '存款日均较上月(万元，整数)';

comment on column pa_view_deposit_module_org_grid.save_day_avg_begin_year is '存款日均较年初(万元，整数)';

comment on column pa_view_deposit_module_org_grid.deposit_save_balance is '储蓄存款余额(万元，整数)';

comment on column pa_view_deposit_module_org_grid.deposit_save_balance_last_month is '储蓄存款余额较上月(万元，整数)';

comment on column pa_view_deposit_module_org_grid.deposit_save_balance_begin_year is '储蓄存款余额较年初(万元，整数)';

comment on column pa_view_deposit_module_org_grid.deposit_save_day_avg is '储蓄存款日均(万元，整数)';

comment on column pa_view_deposit_module_org_grid.deposit_save_day_avg_last_month is '储蓄存款日均较上月(万元，整数)';

comment on column pa_view_deposit_module_org_grid.deposit_save_day_avg_begin_year is '储蓄存款日均较年初(万元，整数)';

comment on column pa_view_deposit_module_org_grid.create_time is '创建时间';

comment on column pa_view_deposit_module_org_grid.update_time is '更新时间';

create unique index INDEX33654364
	on pa_view_deposit_module_org_grid (deposit_module_id);

alter table pa_view_deposit_module_org_grid
	add constraint CONS134252765
		primary key (deposit_module_id);

create table pa_view_grid_overview
(
	grid_code VARCHAR(255),
	grid_name VARCHAR(255),
	grid_user_name VARCHAR(255),
	grid_level VARCHAR(255),
	org_code VARCHAR(255),
	org_name VARCHAR(255),
	level_four_grid_num INT(10),
	level_five_grid_num INT(10),
	grid_people_num INT(10),
	gdp VARCHAR(255),
	village_num INT(10),
	village_people_num INT(10),
	Industrial_park_num INT(10),
	company_num INT(10),
	financial_institution_num INT(10),
	business_area_num INT(10),
	create_time TIMESTAMP(36,6),
	update_time TIMESTAMP(36,6)
);

comment on table pa_view_grid_overview is '小微视图-网格概况-网格、机构纬度';

comment on column pa_view_grid_overview.grid_code is '网格编号';

comment on column pa_view_grid_overview.grid_name is '网格名称';

comment on column pa_view_grid_overview.grid_user_name is '网格主管员';

comment on column pa_view_grid_overview.grid_level is '网格等级';

comment on column pa_view_grid_overview.org_name is '所属机构';

comment on column pa_view_grid_overview.level_four_grid_num is '四级网格数量';

comment on column pa_view_grid_overview.level_five_grid_num is '五级网格数量';

comment on column pa_view_grid_overview.grid_people_num is '网格人数';

comment on column pa_view_grid_overview.gdp is '经济总量（万元）';

comment on column pa_view_grid_overview.village_num is '小区数量';

comment on column pa_view_grid_overview.village_people_num is '小区人口';

comment on column pa_view_grid_overview.Industrial_park_num is '工业园数量';

comment on column pa_view_grid_overview.company_num is '企业数量';

comment on column pa_view_grid_overview.financial_institution_num is '金融机构数量';

comment on column pa_view_grid_overview.business_area_num is '商圈数量';

comment on column pa_view_grid_overview.create_time is '创建时间';

comment on column pa_view_grid_overview.update_time is '更新时间';

create table pa_view_grid_overview_manager
(
	grid_overview_id BIGINT(19),
	manager_code VARCHAR(255),
	ftp VARCHAR(255),
	customer_type VARCHAR(255),
	level_four_grid_num INT(10),
	level_five_grid_num INT(10),
	customer_total_num INT(10),
	performance_customer_num INT(10),
	performance_inside_customer_num INT(10),
	performance_inside_customer_ratio VARCHAR(255),
	performance_outside_customer_num INT(10),
	performance_outside_customer_ratio VARCHAR(255),
	credit_customer_num INT(10),
	credit_inside_customer_num INT(10),
	credit_inside_customer_ratio VARCHAR(255),
	credit_outside_customer_num INT(10),
	credit_outside_customer_ratio VARCHAR(255),
	acquire_customer_num INT(10),
	acquire_inside_customer_num INT(10),
	acquire_inside_customer_ratio VARCHAR(255),
	acquire_outside_customer_num INT(10),
	acquire_outside_customer_ratio VARCHAR(255),
	value_customer_num INT(10),
	value_inside_customer_num INT(10),
	value_inside_customer_ratio VARCHAR(255),
	value_outside_customer_num INT(10),
	value_outside_customer_ratio VARCHAR(255),
	record_customer_num INT(10),
	record_inside_customer_num INT(10),
	record_inside_customer_ratio VARCHAR(255),
	record_outside_customer_num INT(10),
	record_outside_customer_ratio VARCHAR(255),
	grid_people_num INT(10),
	gdp VARCHAR(255),
	village_num INT(10),
	village_people_num INT(10),
	Industrial_park_num INT(10),
	company_num INT(10),
	financial_institution_num INT(10),
	business_area_num INT(10),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_grid_overview_manager is '小微视图-网格概况-客户经理纬度';

comment on column pa_view_grid_overview_manager.grid_overview_id is '网格概况id';

comment on column pa_view_grid_overview_manager.manager_code is '客户经理编号 1';

comment on column pa_view_grid_overview_manager.ftp is '当年累计FTP模拟利润(万元,保留两位小数) 1';

comment on column pa_view_grid_overview_manager.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户 1';

comment on column pa_view_grid_overview_manager.level_four_grid_num is '四级网格数量';

comment on column pa_view_grid_overview_manager.level_five_grid_num is '五级网格数量';

comment on column pa_view_grid_overview_manager.customer_total_num is '客户总数';

comment on column pa_view_grid_overview_manager.performance_customer_num is '绩效客户总数 1';

comment on column pa_view_grid_overview_manager.performance_inside_customer_num is '网格内绩效客户数 1';

comment on column pa_view_grid_overview_manager.performance_inside_customer_ratio is '网格内绩效客户数占比 1';

comment on column pa_view_grid_overview_manager.performance_outside_customer_num is '网格外绩效客户数 1';

comment on column pa_view_grid_overview_manager.performance_outside_customer_ratio is '网格外绩效客户数占比 1';

comment on column pa_view_grid_overview_manager.credit_customer_num is '授信客户总数 1';

comment on column pa_view_grid_overview_manager.credit_inside_customer_num is '网格内授信客户数 1';

comment on column pa_view_grid_overview_manager.credit_inside_customer_ratio is '网格内授信客户数占比 1';

comment on column pa_view_grid_overview_manager.credit_outside_customer_num is '网格外授信客户数 1';

comment on column pa_view_grid_overview_manager.credit_outside_customer_ratio is '网格外授信客户数占比 1';

comment on column pa_view_grid_overview_manager.acquire_customer_num is '收单客户总数 1';

comment on column pa_view_grid_overview_manager.acquire_inside_customer_num is '网格内收单客户数 1';

comment on column pa_view_grid_overview_manager.acquire_inside_customer_ratio is '网格内收单客户数占比 1';

comment on column pa_view_grid_overview_manager.acquire_outside_customer_num is '网格外收单客户数 1';

comment on column pa_view_grid_overview_manager.acquire_outside_customer_ratio is '网格外收单客户数占比 1';

comment on column pa_view_grid_overview_manager.value_customer_num is '价值客户数 1';

comment on column pa_view_grid_overview_manager.value_inside_customer_num is '网格内价值客户数 1';

comment on column pa_view_grid_overview_manager.value_inside_customer_ratio is '网格内价值客户数占比 1';

comment on column pa_view_grid_overview_manager.value_outside_customer_num is '网格外价值客户数 1';

comment on column pa_view_grid_overview_manager.value_outside_customer_ratio is '网格外价值客户数占比 1';

comment on column pa_view_grid_overview_manager.record_customer_num is '建档客户数';

comment on column pa_view_grid_overview_manager.record_inside_customer_num is '网格内建档客户数';

comment on column pa_view_grid_overview_manager.record_inside_customer_ratio is '网格内建档客户数占比';

comment on column pa_view_grid_overview_manager.record_outside_customer_num is '网格外建档客户数';

comment on column pa_view_grid_overview_manager.record_outside_customer_ratio is '网格内建档客户数占比';

comment on column pa_view_grid_overview_manager.grid_people_num is '网格人数';

comment on column pa_view_grid_overview_manager.gdp is '经济总量（万元）';

comment on column pa_view_grid_overview_manager.village_num is '小区数量';

comment on column pa_view_grid_overview_manager.village_people_num is '小区人口';

comment on column pa_view_grid_overview_manager.Industrial_park_num is '工业园数量';

comment on column pa_view_grid_overview_manager.company_num is '企业数量';

comment on column pa_view_grid_overview_manager.financial_institution_num is '金融机构数量';

comment on column pa_view_grid_overview_manager.business_area_num is '商圈数量';

comment on column pa_view_grid_overview_manager.create_time is '创建时间';

comment on column pa_view_grid_overview_manager.update_time is '更新时间';

create unique index INDEX33654366
	on pa_view_grid_overview_manager (grid_overview_id);

alter table pa_view_grid_overview_manager
	add constraint CONS134252766
		primary key (grid_overview_id);

create table pa_view_grid_overview_manager_grid
(
	grid_overview_id INT(10),
	manager_code VARCHAR(255),
	grid_code VARCHAR(255),
	ftp VARCHAR(255),
	customer_type VARCHAR(255),
	level_four_grid_num INT(10),
	level_five_grid_num INT(10),
	customer_total_num INT(10),
	performance_customer_num INT(10),
	performance_inside_customer_num INT(10),
	performance_inside_customer_ratio VARCHAR(255),
	performance_outside_customer_num INT(10),
	performance_outside_customer_ratio VARCHAR(255),
	credit_customer_num INT(10),
	credit_inside_customer_num INT(10),
	credit_inside_customer_ratio VARCHAR(255),
	credit_outside_customer_num INT(10),
	credit_outside_customer_ratio VARCHAR(255),
	acquire_customer_num INT(10),
	acquire_inside_customer_num INT(10),
	acquire_inside_customer_ratio VARCHAR(255),
	acquire_outside_customer_num INT(10),
	acquire_outside_customer_ratio VARCHAR(255),
	value_customer_num INT(10),
	value_inside_customer_num INT(10),
	value_inside_customer_ratio VARCHAR(255),
	value_outside_customer_num INT(10),
	value_outside_customer_ratio VARCHAR(255),
	record_customer_num INT(10),
	record_inside_customer_num INT(10),
	record_inside_customer_ratio VARCHAR(255),
	record_outside_customer_num INT(10),
	record_outside_customer_ratio VARCHAR(255),
	grid_people_num INT(10),
	gdp VARCHAR(255),
	village_num INT(10),
	village_people_num INT(10),
	Industrial_park_num INT(10),
	company_num INT(10),
	financial_institution_num INT(10),
	business_area_num INT(10),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_grid_overview_manager_grid is '小微视图-网格概况-网格、客户经理纬度';

comment on column pa_view_grid_overview_manager_grid.grid_overview_id is '网格概况id';

comment on column pa_view_grid_overview_manager_grid.manager_code is '客户经理编号 1';

comment on column pa_view_grid_overview_manager_grid.grid_code is '网格编号 1';

comment on column pa_view_grid_overview_manager_grid.ftp is '当年累计FTP模拟利润(万元,保留两位小数) 1';

comment on column pa_view_grid_overview_manager_grid.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户 1';

comment on column pa_view_grid_overview_manager_grid.level_four_grid_num is '四级网格数量';

comment on column pa_view_grid_overview_manager_grid.level_five_grid_num is '五级网格数量';

comment on column pa_view_grid_overview_manager_grid.customer_total_num is '客户总数';

comment on column pa_view_grid_overview_manager_grid.performance_customer_num is '绩效客户总数 1';

comment on column pa_view_grid_overview_manager_grid.performance_inside_customer_num is '网格内绩效客户数 1';

comment on column pa_view_grid_overview_manager_grid.performance_inside_customer_ratio is '网格内绩效客户数占比 1';

comment on column pa_view_grid_overview_manager_grid.performance_outside_customer_num is '网格外绩效客户数';

comment on column pa_view_grid_overview_manager_grid.performance_outside_customer_ratio is '网格外绩效客户数占比';

comment on column pa_view_grid_overview_manager_grid.credit_customer_num is '授信客户总数 1';

comment on column pa_view_grid_overview_manager_grid.credit_inside_customer_num is '网格内授信客户数 1';

comment on column pa_view_grid_overview_manager_grid.credit_inside_customer_ratio is '网格内授信客户数占比 1';

comment on column pa_view_grid_overview_manager_grid.credit_outside_customer_num is '网格外授信客户数';

comment on column pa_view_grid_overview_manager_grid.credit_outside_customer_ratio is '网格外授信客户数占比';

comment on column pa_view_grid_overview_manager_grid.acquire_customer_num is '收单客户总数 1';

comment on column pa_view_grid_overview_manager_grid.acquire_inside_customer_num is '网格内收单客户数 1';

comment on column pa_view_grid_overview_manager_grid.acquire_inside_customer_ratio is '网格内收单客户数占比 1';

comment on column pa_view_grid_overview_manager_grid.acquire_outside_customer_num is '网格外收单客户数';

comment on column pa_view_grid_overview_manager_grid.acquire_outside_customer_ratio is '网格外收单客户数占比';

comment on column pa_view_grid_overview_manager_grid.value_customer_num is '价值客户数 1';

comment on column pa_view_grid_overview_manager_grid.value_inside_customer_num is '网格内价值客户数 1';

comment on column pa_view_grid_overview_manager_grid.value_inside_customer_ratio is '网格内价值客户数占比 1';

comment on column pa_view_grid_overview_manager_grid.value_outside_customer_num is '网格外价值客户数';

comment on column pa_view_grid_overview_manager_grid.value_outside_customer_ratio is '网格外价值客户数占比';

comment on column pa_view_grid_overview_manager_grid.record_customer_num is '建档客户数';

comment on column pa_view_grid_overview_manager_grid.record_inside_customer_num is '网格内建档客户数';

comment on column pa_view_grid_overview_manager_grid.record_inside_customer_ratio is '网格内建档客户数占比';

comment on column pa_view_grid_overview_manager_grid.record_outside_customer_num is '网格外建档客户数';

comment on column pa_view_grid_overview_manager_grid.record_outside_customer_ratio is '网格内建档客户数占比';

comment on column pa_view_grid_overview_manager_grid.grid_people_num is '网格人数';

comment on column pa_view_grid_overview_manager_grid.gdp is '经济总量（万元）';

comment on column pa_view_grid_overview_manager_grid.village_num is '小区数量';

comment on column pa_view_grid_overview_manager_grid.village_people_num is '小区人口';

comment on column pa_view_grid_overview_manager_grid.Industrial_park_num is '工业园数量';

comment on column pa_view_grid_overview_manager_grid.company_num is '企业数量';

comment on column pa_view_grid_overview_manager_grid.financial_institution_num is '金融机构数量';

comment on column pa_view_grid_overview_manager_grid.business_area_num is '商圈数量';

comment on column pa_view_grid_overview_manager_grid.create_time is '创建时间';

comment on column pa_view_grid_overview_manager_grid.update_time is '更新时间';

create unique index INDEX33654368
	on pa_view_grid_overview_manager_grid (grid_overview_id);

alter table pa_view_grid_overview_manager_grid
	add constraint CONS134252767
		primary key (grid_overview_id);

create table pa_view_grid_overview_org
(
	grid_overview_id BIGINT(19),
	org_code VARCHAR(255),
	ftp VARCHAR(255),
	customer_type VARCHAR(255),
	level_four_grid_num INT(10),
	level_five_grid_num INT(10),
	customer_total_num INT(10),
	performance_customer_num INT(10),
	performance_inside_customer_num INT(10),
	performance_inside_customer_ratio VARCHAR(255),
	performance_outside_customer_num INT(10),
	performance_outside_customer_ratio VARCHAR(255),
	credit_customer_num INT(10),
	credit_inside_customer_num INT(10),
	credit_inside_customer_ratio VARCHAR(255),
	credit_outside_customer_num INT(10),
	credit_outside_customer_ratio VARCHAR(255),
	acquire_customer_num INT(10),
	acquire_inside_customer_num INT(10),
	acquire_inside_customer_ratio VARCHAR(255),
	acquire_outside_customer_num INT(10),
	acquire_outside_customer_ratio VARCHAR(255),
	value_customer_num INT(10),
	value_inside_customer_num INT(10),
	value_inside_customer_ratio VARCHAR(255),
	value_outside_customer_num INT(10),
	value_outside_customer_ratio VARCHAR(255),
	record_customer_num INT(10),
	record_inside_customer_num INT(10),
	record_inside_customer_ratio VARCHAR(255),
	record_outside_customer_num INT(10),
	record_outside_customer_ratio VARCHAR(255),
	grid_people_num INT(10),
	gdp VARCHAR(255),
	village_num INT(10),
	village_people_num INT(10),
	Industrial_park_num INT(10),
	company_num INT(10),
	financial_institution_num INT(10),
	business_area_num INT(10),
	create_time TIMESTAMP(36,6),
	update_time TIMESTAMP(36,6)
);

comment on table pa_view_grid_overview_org is '小微视图-网格概况-机构纬度';

comment on column pa_view_grid_overview_org.grid_overview_id is '网格概况id';

comment on column pa_view_grid_overview_org.org_code is '机构编号 1';

comment on column pa_view_grid_overview_org.ftp is '当年累计FTP模拟利润(万元,保留两位小数) 1';

comment on column pa_view_grid_overview_org.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户 1';

comment on column pa_view_grid_overview_org.level_four_grid_num is '四级网格数量';

comment on column pa_view_grid_overview_org.level_five_grid_num is '五级网格数量';

comment on column pa_view_grid_overview_org.customer_total_num is '客户总数';

comment on column pa_view_grid_overview_org.performance_customer_num is '绩效客户总数 1';

comment on column pa_view_grid_overview_org.performance_inside_customer_num is '网格内绩效客户数 1';

comment on column pa_view_grid_overview_org.performance_inside_customer_ratio is '网格内绩效客户数占比 1';

comment on column pa_view_grid_overview_org.performance_outside_customer_num is '网格外绩效客户数 1';

comment on column pa_view_grid_overview_org.performance_outside_customer_ratio is '网格外绩效客户数占比 1';

comment on column pa_view_grid_overview_org.credit_customer_num is '授信客户总数 1';

comment on column pa_view_grid_overview_org.credit_inside_customer_num is '网格内授信客户数 1';

comment on column pa_view_grid_overview_org.credit_inside_customer_ratio is '网格内授信客户数占比 1';

comment on column pa_view_grid_overview_org.credit_outside_customer_num is '网格外授信客户数 1';

comment on column pa_view_grid_overview_org.credit_outside_customer_ratio is '网格外授信客户数占比 1';

comment on column pa_view_grid_overview_org.acquire_customer_num is '收单客户总数 1';

comment on column pa_view_grid_overview_org.acquire_inside_customer_num is '网格内收单客户数 1';

comment on column pa_view_grid_overview_org.acquire_inside_customer_ratio is '网格内收单客户数占比 1';

comment on column pa_view_grid_overview_org.acquire_outside_customer_num is '网格外收单客户数 1';

comment on column pa_view_grid_overview_org.acquire_outside_customer_ratio is '网格外收单客户数占比 1';

comment on column pa_view_grid_overview_org.value_customer_num is '价值客户数 1';

comment on column pa_view_grid_overview_org.value_inside_customer_num is '网格内价值客户数 1';

comment on column pa_view_grid_overview_org.value_inside_customer_ratio is '网格内价值客户数占比 1';

comment on column pa_view_grid_overview_org.value_outside_customer_num is '网格外价值客户数 1';

comment on column pa_view_grid_overview_org.value_outside_customer_ratio is '网格外价值客户数占比 1';

comment on column pa_view_grid_overview_org.record_customer_num is '建档客户数';

comment on column pa_view_grid_overview_org.record_inside_customer_num is '网格内建档客户数';

comment on column pa_view_grid_overview_org.record_inside_customer_ratio is '网格内建档客户数占比';

comment on column pa_view_grid_overview_org.record_outside_customer_num is '网格外建档客户数';

comment on column pa_view_grid_overview_org.record_outside_customer_ratio is '网格内建档客户数占比';

comment on column pa_view_grid_overview_org.grid_people_num is '网格人数';

comment on column pa_view_grid_overview_org.gdp is '经济总量（万元）';

comment on column pa_view_grid_overview_org.village_num is '小区数量';

comment on column pa_view_grid_overview_org.village_people_num is '小区人口';

comment on column pa_view_grid_overview_org.Industrial_park_num is '工业园数量';

comment on column pa_view_grid_overview_org.company_num is '企业数量';

comment on column pa_view_grid_overview_org.financial_institution_num is '金融机构数量';

comment on column pa_view_grid_overview_org.business_area_num is '商圈数量';

comment on column pa_view_grid_overview_org.create_time is '创建时间';

comment on column pa_view_grid_overview_org.update_time is '更新时间';

create table pa_view_grid_overview_org_grid
(
	grid_overview_id BIGINT(19),
	org_code VARCHAR(255),
	grid_code VARCHAR(255),
	ftp VARCHAR(255),
	customer_type VARCHAR(255),
	level_four_grid_num INT(10),
	level_five_grid_num INT(10),
	customer_total_num INT(10),
	performance_customer_num INT(10),
	performance_inside_customer_num INT(10),
	performance_inside_customer_ratio VARCHAR(255),
	performance_outside_customer_num INT(10),
	performance_outside_customer_ratio VARCHAR(255),
	credit_customer_num INT(10),
	credit_inside_customer_num INT(10),
	credit_inside_customer_ratio VARCHAR(255),
	credit_outside_customer_num INT(10),
	credit_outside_customer_ratio VARCHAR(255),
	acquire_customer_num INT(10),
	acquire_inside_customer_num INT(10),
	acquire_inside_customer_ratio VARCHAR(255),
	acquire_outside_customer_num INT(10),
	acquire_outside_customer_ratio VARCHAR(255),
	value_customer_num INT(10),
	value_inside_customer_num INT(10),
	value_inside_customer_ratio VARCHAR(255),
	value_outside_customer_num INT(10),
	value_outside_customer_ratio VARCHAR(255),
	record_customer_num INT(10),
	record_inside_customer_num INT(10),
	record_inside_customer_ratio VARCHAR(255),
	record_outside_customer_num INT(10),
	record_outside_customer_ratio VARCHAR(255),
	grid_people_num INT(10),
	gdp VARCHAR(255),
	village_num INT(10),
	village_people_num INT(10),
	Industrial_park_num INT(10),
	company_num INT(10),
	financial_institution_num INT(10),
	business_area_num INT(10),
	create_time TIMESTAMP(36,6),
	update_time TIMESTAMP(36,6)
);

comment on table pa_view_grid_overview_org_grid is '小微视图-网格概况-网格、机构纬度';

comment on column pa_view_grid_overview_org_grid.grid_overview_id is '网格概况id';

comment on column pa_view_grid_overview_org_grid.org_code is '机构编号 1';

comment on column pa_view_grid_overview_org_grid.grid_code is '网格编号 1';

comment on column pa_view_grid_overview_org_grid.ftp is '当年累计FTP模拟利润(万元,保留两位小数) 1';

comment on column pa_view_grid_overview_org_grid.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户 1';

comment on column pa_view_grid_overview_org_grid.level_four_grid_num is '四级网格数量';

comment on column pa_view_grid_overview_org_grid.level_five_grid_num is '五级网格数量';

comment on column pa_view_grid_overview_org_grid.customer_total_num is '客户总数';

comment on column pa_view_grid_overview_org_grid.performance_customer_num is '绩效客户总数 1';

comment on column pa_view_grid_overview_org_grid.performance_inside_customer_num is '网格内绩效客户数 1';

comment on column pa_view_grid_overview_org_grid.performance_inside_customer_ratio is '网格内绩效客户数占比 1';

comment on column pa_view_grid_overview_org_grid.performance_outside_customer_num is '网格外绩效客户数';

comment on column pa_view_grid_overview_org_grid.performance_outside_customer_ratio is '网格外绩效客户数占比';

comment on column pa_view_grid_overview_org_grid.credit_customer_num is '授信客户总数 1';

comment on column pa_view_grid_overview_org_grid.credit_inside_customer_num is '网格内授信客户数 1';

comment on column pa_view_grid_overview_org_grid.credit_inside_customer_ratio is '网格内授信客户数占比 1';

comment on column pa_view_grid_overview_org_grid.credit_outside_customer_num is '网格外授信客户数';

comment on column pa_view_grid_overview_org_grid.credit_outside_customer_ratio is '网格外授信客户数占比';

comment on column pa_view_grid_overview_org_grid.acquire_customer_num is '收单客户总数 1';

comment on column pa_view_grid_overview_org_grid.acquire_inside_customer_num is '网格内收单客户数 1';

comment on column pa_view_grid_overview_org_grid.acquire_inside_customer_ratio is '网格内收单客户数占比 1';

comment on column pa_view_grid_overview_org_grid.acquire_outside_customer_num is '网格外收单客户数';

comment on column pa_view_grid_overview_org_grid.acquire_outside_customer_ratio is '网格外收单客户数占比';

comment on column pa_view_grid_overview_org_grid.value_customer_num is '价值客户数 1';

comment on column pa_view_grid_overview_org_grid.value_inside_customer_num is '网格内价值客户数 1';

comment on column pa_view_grid_overview_org_grid.value_inside_customer_ratio is '网格内价值客户数占比 1';

comment on column pa_view_grid_overview_org_grid.value_outside_customer_num is '网格外价值客户数';

comment on column pa_view_grid_overview_org_grid.value_outside_customer_ratio is '网格外价值客户数占比';

comment on column pa_view_grid_overview_org_grid.record_customer_num is '建档客户数';

comment on column pa_view_grid_overview_org_grid.record_inside_customer_num is '网格内建档客户数';

comment on column pa_view_grid_overview_org_grid.record_inside_customer_ratio is '网格内建档客户数占比';

comment on column pa_view_grid_overview_org_grid.record_outside_customer_num is '网格外建档客户数';

comment on column pa_view_grid_overview_org_grid.record_outside_customer_ratio is '网格内建档客户数占比';

comment on column pa_view_grid_overview_org_grid.grid_people_num is '网格人数';

comment on column pa_view_grid_overview_org_grid.gdp is '经济总量（万元）';

comment on column pa_view_grid_overview_org_grid.village_num is '小区数量';

comment on column pa_view_grid_overview_org_grid.village_people_num is '小区人口';

comment on column pa_view_grid_overview_org_grid.Industrial_park_num is '工业园数量';

comment on column pa_view_grid_overview_org_grid.company_num is '企业数量';

comment on column pa_view_grid_overview_org_grid.financial_institution_num is '金融机构数量';

comment on column pa_view_grid_overview_org_grid.business_area_num is '商圈数量';

comment on column pa_view_grid_overview_org_grid.create_time is '创建时间';

comment on column pa_view_grid_overview_org_grid.update_time is '更新时间';

create unique index INDEX33654371
	on pa_view_grid_overview_org_grid (grid_overview_id);

alter table pa_view_grid_overview_org_grid
	add constraint CONS134252768
		primary key (grid_overview_id);

create table pa_view_loan_manager
(
	loan_id BIGINT(19),
	loan_ftp VARCHAR(255),
	manager_code VARCHAR(255),
	customer_type VARCHAR(255),
	grant_credit_num INT(10),
	grant_credit_compare_month_num INT(10),
	grant_credit_compare_year_num INT(10),
	grant_credit_grid_inside_num INT(10),
	grant_credit_inside_num_ratio VARCHAR(255),
	grant_credit_inside_compare_month_num INT(10),
	grant_credit_inside_compare_year_num INT(10),
	grant_credit_grid_outside_num INT(10),
	grant_credit_outside_num_ratio VARCHAR(255),
	grant_credit_outside_compare_month_num INT(10),
	grant_credit_outside_compare_year_num INT(10),
	use_credit_num INT(10),
	use_credit_compare_month_num INT(10),
	use_credit_compare_year_num INT(10),
	use_credit_grid_inside_num INT(10),
	use_credit_inside_num_ratio VARCHAR(255),
	use_credit_inside_compare_month_num INT(10),
	use_credit_inside_compare_year_num INT(10),
	use_credit_grid_outside_num INT(10),
	use_credit_outside_num_ratio VARCHAR(255),
	use_credit_outside_compare_month_num INT(10),
	use_credit_outside_compare_year_num INT(10),
	grant_credit_bal VARCHAR(255),
	grant_credit_compare_month_bal VARCHAR(255),
	grant_credit_compare_year_bal VARCHAR(255),
	grant_credit_grid_inside_bal VARCHAR(255),
	grant_credit_inside_bal_ratio VARCHAR(255),
	grant_credit_inside_compare_month_bal VARCHAR(255),
	grant_credit_inside_compare_year_bal VARCHAR(255),
	grant_credit_grid_outside_bal VARCHAR(255),
	grant_credit_outside_bal_ratio VARCHAR(255),
	grant_credit_outside_compare_month_bal VARCHAR(255),
	grant_credit_outside_compare_year_bal VARCHAR(255),
	use_credit_bal VARCHAR(255),
	use_credit_compare_month_bal VARCHAR(255),
	use_credit_compare_year_bal VARCHAR(255),
	use_credit_grid_inside_bal VARCHAR(255),
	use_credit_inside_bal_ratio VARCHAR(255),
	use_credit_inside_compare_month_bal VARCHAR(255),
	use_credit_inside_compare_year_bal VARCHAR(255),
	use_credit_grid_outside_bal VARCHAR(255),
	use_credit_outside_bal_ratio VARCHAR(255),
	use_credit_outside_compare_month_bal VARCHAR(255),
	use_credit_outside_compare_year_bal VARCHAR(255),
	total_bad_bal VARCHAR(225),
	total_bad_compare_month_bal VARCHAR(225),
	total_bad_compare_year_bal VARCHAR(225),
	total_bad_ratio VARCHAR(225),
	total_bad_grid_inside_ratio VARCHAR(225),
	total_bad_grid_outside_ratio VARCHAR(225),
	total_overdue_bal VARCHAR(225),
	total_overdue_compare_month_bal VARCHAR(225),
	total_overdue_compare_year_bal VARCHAR(225),
	total_overdue_ratio VARCHAR(225),
	total_overdue_grid_inside_ratio VARCHAR(225),
	total_overdue_grid_outside_ratio VARCHAR(225),
	total_interest_ratio VARCHAR(225),
	total_interest_grid_inside_ratio VARCHAR(225),
	total_interest_grid_outside_ratio VARCHAR(225),
	credit_num INT(10),
	credit_bal VARCHAR(225),
	credit_bad_bal VARCHAR(225),
	credit_bad_ratio VARCHAR(225),
	credit_overdue_bal VARCHAR(225),
	credit_overdue_ratio VARCHAR(225),
	ensure_num INT(10),
	ensure_bal VARCHAR(225),
	ensure_bad_bal VARCHAR(225),
	ensure_bad_ratio VARCHAR(225),
	ensure_overdue_bal VARCHAR(225),
	ensure_overdue_ratio VARCHAR(225),
	mortgage_num INT(10),
	mortgage_bal VARCHAR(225),
	mortgage_bad_bal VARCHAR(225),
	mortgage_bad_ratio VARCHAR(225),
	mortgage_overdue_bal VARCHAR(225),
	mortgage_overdue_ratio VARCHAR(225),
	pledge_num INT(10),
	pledge_bal VARCHAR(225),
	pledge_bad_bal VARCHAR(225),
	pledge_bad_ratio VARCHAR(225),
	pledge_overdue_bal VARCHAR(225),
	pledge_overdue_ratio VARCHAR(225),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_loan_manager is '小微视图-资产模块-客户经理纬度';

comment on column pa_view_loan_manager.loan_id is '贷款视图id';

comment on column pa_view_loan_manager.loan_ftp is '贷款ftp(万元，保留两位小数)';

comment on column pa_view_loan_manager.manager_code is '客户经理编号';

comment on column pa_view_loan_manager.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户';

comment on column pa_view_loan_manager.grant_credit_num is '授信户数';

comment on column pa_view_loan_manager.grant_credit_compare_month_num is '授信户数较上月';

comment on column pa_view_loan_manager.grant_credit_compare_year_num is '授信户数较年初';

comment on column pa_view_loan_manager.grant_credit_grid_inside_num is '网格内授信户数';

comment on column pa_view_loan_manager.grant_credit_inside_num_ratio is '网格内授信户数占比';

comment on column pa_view_loan_manager.grant_credit_inside_compare_month_num is '网格内授信户数较上月';

comment on column pa_view_loan_manager.grant_credit_inside_compare_year_num is '网格内授信户数较年初';

comment on column pa_view_loan_manager.grant_credit_grid_outside_num is '网格外授信户数';

comment on column pa_view_loan_manager.grant_credit_outside_num_ratio is '网格外授信户数占比';

comment on column pa_view_loan_manager.grant_credit_outside_compare_month_num is '网格外授信户数较上月';

comment on column pa_view_loan_manager.grant_credit_outside_compare_year_num is '网格外授信户数较年初';

comment on column pa_view_loan_manager.use_credit_num is '用信户数';

comment on column pa_view_loan_manager.use_credit_compare_month_num is '用信户数较上月';

comment on column pa_view_loan_manager.use_credit_compare_year_num is '用信户数较年初';

comment on column pa_view_loan_manager.use_credit_grid_inside_num is '网格内用信户数';

comment on column pa_view_loan_manager.use_credit_inside_num_ratio is '网格内用信户数占比';

comment on column pa_view_loan_manager.use_credit_inside_compare_month_num is '网格内用信户数较上月';

comment on column pa_view_loan_manager.use_credit_inside_compare_year_num is '网格内用信户数较年初';

comment on column pa_view_loan_manager.use_credit_grid_outside_num is '网格外用信户数';

comment on column pa_view_loan_manager.use_credit_outside_num_ratio is '网格外用信户数占比';

comment on column pa_view_loan_manager.use_credit_outside_compare_month_num is '网格外用信户数较上月';

comment on column pa_view_loan_manager.use_credit_outside_compare_year_num is '网格外用信户数较年初';

comment on column pa_view_loan_manager.grant_credit_bal is '授信金额(万元，整数)';

comment on column pa_view_loan_manager.grant_credit_compare_month_bal is '授信金额较上月(万元，整数)';

comment on column pa_view_loan_manager.grant_credit_compare_year_bal is '授信金额较年初(万元，整数)';

comment on column pa_view_loan_manager.grant_credit_grid_inside_bal is '网格内授信金额(万元，整数)';

comment on column pa_view_loan_manager.grant_credit_inside_bal_ratio is '网格内授信金额占比';

comment on column pa_view_loan_manager.grant_credit_inside_compare_month_bal is '网格内授信金额较上月(万元，整数)';

comment on column pa_view_loan_manager.grant_credit_inside_compare_year_bal is '网格内授信金额较年初(万元，整数)';

comment on column pa_view_loan_manager.grant_credit_grid_outside_bal is '网格外授信金额(万元，整数)';

comment on column pa_view_loan_manager.grant_credit_outside_bal_ratio is '网格外授信金额占比';

comment on column pa_view_loan_manager.grant_credit_outside_compare_month_bal is '网格外授信金额较上月(万元，整数)';

comment on column pa_view_loan_manager.grant_credit_outside_compare_year_bal is '网格外授信金额较年初(万元，整数)';

comment on column pa_view_loan_manager.use_credit_bal is '用信金额(万元，整数)';

comment on column pa_view_loan_manager.use_credit_compare_month_bal is '用信金额较上月(万元，整数)';

comment on column pa_view_loan_manager.use_credit_compare_year_bal is '用信金额较年初(万元，整数)';

comment on column pa_view_loan_manager.use_credit_grid_inside_bal is '网格内用信金额(万元，整数)';

comment on column pa_view_loan_manager.use_credit_inside_bal_ratio is '网格内用信金额占比';

comment on column pa_view_loan_manager.use_credit_inside_compare_month_bal is '网格内用信金额较上月(万元，整数)';

comment on column pa_view_loan_manager.use_credit_inside_compare_year_bal is '网格内用信金额较年初(万元，整数)';

comment on column pa_view_loan_manager.use_credit_grid_outside_bal is '网格外用信金额(万元，整数)';

comment on column pa_view_loan_manager.use_credit_outside_bal_ratio is '网格外用信金额占比';

comment on column pa_view_loan_manager.use_credit_outside_compare_month_bal is '网格外用信金额较上月(万元，整数)';

comment on column pa_view_loan_manager.use_credit_outside_compare_year_bal is '网格外用信金额较年初(万元，整数)';

comment on column pa_view_loan_manager.total_bad_bal is '总和-不良余额(万元，整数)';

comment on column pa_view_loan_manager.total_bad_compare_month_bal is '总和-不良余额较上月(万元，整数)';

comment on column pa_view_loan_manager.total_bad_compare_year_bal is '总和-不良余额较年初(万元，整数)';

comment on column pa_view_loan_manager.total_bad_ratio is '总和-不良率';

comment on column pa_view_loan_manager.total_bad_grid_inside_ratio is '总和-网格内不良率';

comment on column pa_view_loan_manager.total_bad_grid_outside_ratio is '总和-网格外不良率';

comment on column pa_view_loan_manager.total_overdue_bal is '总和-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager.total_overdue_compare_month_bal is '总和-逾欠余额较上月(万元，整数)';

comment on column pa_view_loan_manager.total_overdue_compare_year_bal is '总和-逾欠余额较年初(万元，整数)';

comment on column pa_view_loan_manager.total_overdue_ratio is '总和-逾欠率';

comment on column pa_view_loan_manager.total_overdue_grid_inside_ratio is '总和-网格内逾欠率';

comment on column pa_view_loan_manager.total_overdue_grid_outside_ratio is '总和-网格外逾欠率';

comment on column pa_view_loan_manager.total_interest_ratio is '总和-收息率';

comment on column pa_view_loan_manager.total_interest_grid_inside_ratio is '总和-网格内收息率';

comment on column pa_view_loan_manager.total_interest_grid_outside_ratio is '总和-网格外收息率';

comment on column pa_view_loan_manager.credit_num is '信用-户数';

comment on column pa_view_loan_manager.credit_bal is '信用-金额(万元，整数)';

comment on column pa_view_loan_manager.credit_bad_bal is '信用-不良余额(万元，整数)';

comment on column pa_view_loan_manager.credit_bad_ratio is '信用-不良率';

comment on column pa_view_loan_manager.credit_overdue_bal is '信用-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager.credit_overdue_ratio is '信用-逾欠率';

comment on column pa_view_loan_manager.ensure_num is '保证-户数';

comment on column pa_view_loan_manager.ensure_bal is '保证-金额(万元，整数)';

comment on column pa_view_loan_manager.ensure_bad_bal is '保证-不良余额(万元，整数)';

comment on column pa_view_loan_manager.ensure_bad_ratio is '保证-不良率';

comment on column pa_view_loan_manager.ensure_overdue_bal is '保证-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager.ensure_overdue_ratio is '保证-逾欠率';

comment on column pa_view_loan_manager.mortgage_num is '抵押-户数';

comment on column pa_view_loan_manager.mortgage_bal is '抵押-金额(万元，整数)';

comment on column pa_view_loan_manager.mortgage_bad_bal is '抵押-不良余额(万元，整数)';

comment on column pa_view_loan_manager.mortgage_bad_ratio is '抵押-不良率';

comment on column pa_view_loan_manager.mortgage_overdue_bal is '抵押-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager.mortgage_overdue_ratio is '抵押-逾欠率';

comment on column pa_view_loan_manager.pledge_num is '质押-户数';

comment on column pa_view_loan_manager.pledge_bal is '质押-金额(万元，整数)';

comment on column pa_view_loan_manager.pledge_bad_bal is '质押-不良余额(万元，整数)';

comment on column pa_view_loan_manager.pledge_bad_ratio is '质押-不良率';

comment on column pa_view_loan_manager.pledge_overdue_bal is '质押-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager.pledge_overdue_ratio is '质押-逾欠率';

comment on column pa_view_loan_manager.create_time is '创建时间';

comment on column pa_view_loan_manager.update_time is '更新时间';

create unique index INDEX33654373
	on pa_view_loan_manager (loan_id);

alter table pa_view_loan_manager
	add constraint CONS134252769
		primary key (loan_id);

create table pa_view_loan_manager_grid
(
	loan_id BIGINT(19),
	loan_ftp VARCHAR(255),
	manager_code VARCHAR(255),
	grid_code VARCHAR(255),
	customer_type VARCHAR(255),
	grant_credit_num INT(10),
	grant_credit_compare_month_num INT(10),
	grant_credit_compare_year_num INT(10),
	grant_credit_grid_inside_num INT(10),
	grant_credit_inside_num_ratio VARCHAR(255),
	grant_credit_inside_compare_month_num INT(10),
	grant_credit_inside_compare_year_num INT(10),
	grant_credit_grid_outside_num INT(10),
	grant_credit_outside_num_ratio VARCHAR(255),
	grant_credit_outside_compare_month_num INT(10),
	grant_credit_outside_compare_year_num INT(10),
	use_credit_num INT(10),
	use_credit_compare_month_num INT(10),
	use_credit_compare_year_num INT(10),
	use_credit_grid_inside_num INT(10),
	use_credit_inside_num_ratio VARCHAR(255),
	use_credit_inside_compare_month_num INT(10),
	use_credit_inside_compare_year_num INT(10),
	use_credit_grid_outside_num INT(10),
	use_credit_outside_num_ratio VARCHAR(255),
	use_credit_outside_compare_month_num INT(10),
	use_credit_outside_compare_year_num INT(10),
	grant_credit_bal VARCHAR(255),
	grant_credit_compare_month_bal VARCHAR(255),
	grant_credit_compare_year_bal VARCHAR(255),
	grant_credit_grid_inside_bal VARCHAR(255),
	grant_credit_inside_bal_ratio VARCHAR(255),
	grant_credit_inside_compare_month_bal VARCHAR(255),
	grant_credit_inside_compare_year_bal VARCHAR(255),
	grant_credit_grid_outside_bal VARCHAR(255),
	grant_credit_outside_bal_ratio VARCHAR(255),
	grant_credit_outside_compare_month_bal VARCHAR(255),
	grant_credit_outside_compare_year_bal VARCHAR(255),
	use_credit_bal VARCHAR(255),
	use_credit_compare_month_bal VARCHAR(255),
	use_credit_compare_year_bal VARCHAR(255),
	use_credit_grid_inside_bal VARCHAR(255),
	use_credit_inside_bal_ratio VARCHAR(255),
	use_credit_inside_compare_month_bal VARCHAR(255),
	use_credit_inside_compare_year_bal VARCHAR(255),
	use_credit_grid_outside_bal VARCHAR(255),
	use_credit_outside_bal_ratio VARCHAR(255),
	use_credit_outside_compare_month_bal VARCHAR(255),
	use_credit_outside_compare_year_bal VARCHAR(255),
	total_bad_bal VARCHAR(225),
	total_bad_compare_month_bal VARCHAR(225),
	total_bad_compare_year_bal VARCHAR(225),
	total_bad_ratio VARCHAR(225),
	total_bad_grid_inside_ratio VARCHAR(225),
	total_bad_grid_outside_ratio VARCHAR(225),
	total_overdue_bal VARCHAR(225),
	total_overdue_compare_month_bal VARCHAR(225),
	total_overdue_compare_year_bal VARCHAR(225),
	total_overdue_ratio VARCHAR(225),
	total_overdue_grid_inside_ratio VARCHAR(225),
	total_overdue_grid_outside_ratio VARCHAR(225),
	total_interest_ratio VARCHAR(225),
	total_interest_grid_inside_ratio VARCHAR(225),
	total_interest_grid_outside_ratio VARCHAR(225),
	credit_num INT(10),
	credit_bal VARCHAR(225),
	credit_bad_bal VARCHAR(225),
	credit_bad_ratio VARCHAR(225),
	credit_overdue_bal VARCHAR(225),
	credit_overdue_ratio VARCHAR(225),
	ensure_num INT(10),
	ensure_bal VARCHAR(225),
	ensure_bad_bal VARCHAR(225),
	ensure_bad_ratio VARCHAR(225),
	ensure_overdue_bal VARCHAR(225),
	ensure_overdue_ratio VARCHAR(225),
	mortgage_num INT(10),
	mortgage_bal VARCHAR(225),
	mortgage_bad_bal VARCHAR(225),
	mortgage_bad_ratio VARCHAR(225),
	mortgage_overdue_bal VARCHAR(225),
	mortgage_overdue_ratio VARCHAR(225),
	pledge_num INT(10),
	pledge_bal VARCHAR(225),
	pledge_bad_bal VARCHAR(225),
	pledge_bad_ratio VARCHAR(225),
	pledge_overdue_bal VARCHAR(225),
	pledge_overdue_ratio VARCHAR(225),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_loan_manager_grid is '小微视图-贷款模块-网格、客户经理纬度';

comment on column pa_view_loan_manager_grid.loan_id is '贷款视图id';

comment on column pa_view_loan_manager_grid.loan_ftp is '贷款ftp(万元，保留两位小数)';

comment on column pa_view_loan_manager_grid.manager_code is '客户经理编号';

comment on column pa_view_loan_manager_grid.grid_code is '网格编号';

comment on column pa_view_loan_manager_grid.customer_type is '客户类型 1-绩效客户 2-授信客户  3-不含乡贷授信客户';

comment on column pa_view_loan_manager_grid.grant_credit_num is '授信户数';

comment on column pa_view_loan_manager_grid.grant_credit_compare_month_num is '授信户数较上月';

comment on column pa_view_loan_manager_grid.grant_credit_compare_year_num is '授信户数较年初';

comment on column pa_view_loan_manager_grid.grant_credit_grid_inside_num is '网格内授信户数';

comment on column pa_view_loan_manager_grid.grant_credit_inside_num_ratio is '网格内授信户数占比';

comment on column pa_view_loan_manager_grid.grant_credit_inside_compare_month_num is '网格内授信户数较上月';

comment on column pa_view_loan_manager_grid.grant_credit_inside_compare_year_num is '网格内授信户数较年初';

comment on column pa_view_loan_manager_grid.grant_credit_grid_outside_num is '网格外授信户数 0';

comment on column pa_view_loan_manager_grid.grant_credit_outside_num_ratio is '网格外授信户数占比 0';

comment on column pa_view_loan_manager_grid.grant_credit_outside_compare_month_num is '网格外授信户数较上月 0';

comment on column pa_view_loan_manager_grid.grant_credit_outside_compare_year_num is '网格外授信户数较年初 0';

comment on column pa_view_loan_manager_grid.use_credit_num is '用信户数';

comment on column pa_view_loan_manager_grid.use_credit_compare_month_num is '用信户数较上月';

comment on column pa_view_loan_manager_grid.use_credit_compare_year_num is '用信户数较年初';

comment on column pa_view_loan_manager_grid.use_credit_grid_inside_num is '网格内用信户数';

comment on column pa_view_loan_manager_grid.use_credit_inside_num_ratio is '网格内用信户数占比';

comment on column pa_view_loan_manager_grid.use_credit_inside_compare_month_num is '网格内用信户数较上月';

comment on column pa_view_loan_manager_grid.use_credit_inside_compare_year_num is '网格内用信户数较年初';

comment on column pa_view_loan_manager_grid.use_credit_grid_outside_num is '网格外用信户数 0';

comment on column pa_view_loan_manager_grid.use_credit_outside_num_ratio is '网格外用信户数占比 0';

comment on column pa_view_loan_manager_grid.use_credit_outside_compare_month_num is '网格外用信户数较上月 0';

comment on column pa_view_loan_manager_grid.use_credit_outside_compare_year_num is '网格外用信户数较年初 0';

comment on column pa_view_loan_manager_grid.grant_credit_bal is '授信金额(万元，整数)';

comment on column pa_view_loan_manager_grid.grant_credit_compare_month_bal is '授信金额较上月(万元，整数)';

comment on column pa_view_loan_manager_grid.grant_credit_compare_year_bal is '授信金额较年初(万元，整数)';

comment on column pa_view_loan_manager_grid.grant_credit_grid_inside_bal is '网格内授信金额(万元，整数)';

comment on column pa_view_loan_manager_grid.grant_credit_inside_bal_ratio is '网格内授信金额占比';

comment on column pa_view_loan_manager_grid.grant_credit_inside_compare_month_bal is '网格内授信金额较上月(万元，整数)';

comment on column pa_view_loan_manager_grid.grant_credit_inside_compare_year_bal is '网格内授信金额较年初(万元，整数)';

comment on column pa_view_loan_manager_grid.grant_credit_grid_outside_bal is '网格外授信金额(万元，整数) 0';

comment on column pa_view_loan_manager_grid.grant_credit_outside_bal_ratio is '网格外授信金额占比 0';

comment on column pa_view_loan_manager_grid.grant_credit_outside_compare_month_bal is '网格外授信金额较上月(万元，整数) 0';

comment on column pa_view_loan_manager_grid.grant_credit_outside_compare_year_bal is '网格外授信金额较年初(万元，整数) 0';

comment on column pa_view_loan_manager_grid.use_credit_bal is '用信金额(万元，整数)';

comment on column pa_view_loan_manager_grid.use_credit_compare_month_bal is '用信金额较上月(万元，整数)';

comment on column pa_view_loan_manager_grid.use_credit_compare_year_bal is '用信金额较年初(万元，整数)';

comment on column pa_view_loan_manager_grid.use_credit_grid_inside_bal is '网格内用信金额(万元，整数)';

comment on column pa_view_loan_manager_grid.use_credit_inside_bal_ratio is '网格内用信金额占比';

comment on column pa_view_loan_manager_grid.use_credit_inside_compare_month_bal is '网格内用信金额较上月(万元，整数)';

comment on column pa_view_loan_manager_grid.use_credit_inside_compare_year_bal is '网格内用信金额较年初(万元，整数)';

comment on column pa_view_loan_manager_grid.use_credit_grid_outside_bal is '网格外用信金额(万元，整数) 0';

comment on column pa_view_loan_manager_grid.use_credit_outside_bal_ratio is '网格外用信金额占比 0';

comment on column pa_view_loan_manager_grid.use_credit_outside_compare_month_bal is '网格外用信金额较上月(万元，整数) 0';

comment on column pa_view_loan_manager_grid.use_credit_outside_compare_year_bal is '网格外用信金额较年初(万元，整数) 0';

comment on column pa_view_loan_manager_grid.total_bad_bal is '总和-不良余额(万元，整数)';

comment on column pa_view_loan_manager_grid.total_bad_compare_month_bal is '总和-不良余额较上月(万元，整数)';

comment on column pa_view_loan_manager_grid.total_bad_compare_year_bal is '总和-不良余额较年初(万元，整数)';

comment on column pa_view_loan_manager_grid.total_bad_ratio is '总和-不良率';

comment on column pa_view_loan_manager_grid.total_bad_grid_inside_ratio is '总和-网格内不良率';

comment on column pa_view_loan_manager_grid.total_bad_grid_outside_ratio is '总和-网格外不良率 0';

comment on column pa_view_loan_manager_grid.total_overdue_bal is '总和-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager_grid.total_overdue_compare_month_bal is '总和-逾欠余额较上月(万元，整数)';

comment on column pa_view_loan_manager_grid.total_overdue_compare_year_bal is '总和-逾欠余额较年初(万元，整数)';

comment on column pa_view_loan_manager_grid.total_overdue_ratio is '总和-逾欠率';

comment on column pa_view_loan_manager_grid.total_overdue_grid_inside_ratio is '总和-网格内逾欠率';

comment on column pa_view_loan_manager_grid.total_overdue_grid_outside_ratio is '总和-网格外逾欠率 0';

comment on column pa_view_loan_manager_grid.total_interest_ratio is '总和-收息率';

comment on column pa_view_loan_manager_grid.total_interest_grid_inside_ratio is '总和-网格内收息率';

comment on column pa_view_loan_manager_grid.total_interest_grid_outside_ratio is '总和-网格外收息率 0';

comment on column pa_view_loan_manager_grid.credit_num is '信用-户数';

comment on column pa_view_loan_manager_grid.credit_bal is '信用-金额(万元，整数)';

comment on column pa_view_loan_manager_grid.credit_bad_bal is '信用-不良余额(万元，整数)';

comment on column pa_view_loan_manager_grid.credit_bad_ratio is '信用-不良率';

comment on column pa_view_loan_manager_grid.credit_overdue_bal is '信用-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager_grid.credit_overdue_ratio is '信用-逾欠率';

comment on column pa_view_loan_manager_grid.ensure_num is '保证-户数';

comment on column pa_view_loan_manager_grid.ensure_bal is '保证-金额(万元，整数)';

comment on column pa_view_loan_manager_grid.ensure_bad_bal is '保证-不良余额(万元，整数)';

comment on column pa_view_loan_manager_grid.ensure_bad_ratio is '保证-不良率';

comment on column pa_view_loan_manager_grid.ensure_overdue_bal is '保证-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager_grid.ensure_overdue_ratio is '保证-逾欠率';

comment on column pa_view_loan_manager_grid.mortgage_num is '抵押-户数';

comment on column pa_view_loan_manager_grid.mortgage_bal is '抵押-金额(万元，整数)';

comment on column pa_view_loan_manager_grid.mortgage_bad_bal is '抵押-不良余额(万元，整数)';

comment on column pa_view_loan_manager_grid.mortgage_bad_ratio is '抵押-不良率';

comment on column pa_view_loan_manager_grid.mortgage_overdue_bal is '抵押-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager_grid.mortgage_overdue_ratio is '抵押-逾欠率';

comment on column pa_view_loan_manager_grid.pledge_num is '质押-户数';

comment on column pa_view_loan_manager_grid.pledge_bal is '质押-金额(万元，整数)';

comment on column pa_view_loan_manager_grid.pledge_bad_bal is '质押-不良余额(万元，整数)';

comment on column pa_view_loan_manager_grid.pledge_bad_ratio is '质押-不良率';

comment on column pa_view_loan_manager_grid.pledge_overdue_bal is '质押-逾欠余额(万元，整数)';

comment on column pa_view_loan_manager_grid.pledge_overdue_ratio is '质押-逾欠率';

comment on column pa_view_loan_manager_grid.create_time is '创建时间';

comment on column pa_view_loan_manager_grid.update_time is '更新时间';

create unique index INDEX33654375
	on pa_view_loan_manager_grid (loan_id);

alter table pa_view_loan_manager_grid
	add constraint CONS134252770
		primary key (loan_id);

create table pa_view_loan_org
(
	loan_id BIGINT(19),
	loan_ftp VARCHAR(255),
	org_code VARCHAR(255),
	customer_type VARCHAR(255),
	grant_credit_num INT(10),
	grant_credit_compare_month_num INT(10),
	grant_credit_compare_year_num INT(10),
	grant_credit_grid_inside_num INT(10),
	grant_credit_inside_num_ratio VARCHAR(255),
	grant_credit_inside_compare_month_num INT(10),
	grant_credit_inside_compare_year_num INT(10),
	grant_credit_grid_outside_num INT(10),
	grant_credit_outside_num_ratio VARCHAR(255),
	grant_credit_outside_compare_month_num INT(10),
	grant_credit_outside_compare_year_num INT(10),
	use_credit_num INT(10),
	use_credit_compare_month_num INT(10),
	use_credit_compare_year_num INT(10),
	use_credit_grid_inside_num INT(10),
	use_credit_inside_num_ratio VARCHAR(255),
	use_credit_inside_compare_month_num INT(10),
	use_credit_inside_compare_year_num INT(10),
	use_credit_grid_outside_num INT(10),
	use_credit_outside_num_ratio VARCHAR(255),
	use_credit_outside_compare_month_num INT(10),
	use_credit_outside_compare_year_num INT(10),
	grant_credit_bal VARCHAR(255),
	grant_credit_compare_month_bal VARCHAR(255),
	grant_credit_compare_year_bal VARCHAR(255),
	grant_credit_grid_inside_bal VARCHAR(255),
	grant_credit_inside_bal_ratio VARCHAR(255),
	grant_credit_inside_compare_month_bal VARCHAR(255),
	grant_credit_inside_compare_year_bal VARCHAR(255),
	grant_credit_grid_outside_bal VARCHAR(255),
	grant_credit_outside_bal_ratio VARCHAR(255),
	grant_credit_outside_compare_month_bal VARCHAR(255),
	grant_credit_outside_compare_year_bal VARCHAR(255),
	use_credit_bal VARCHAR(255),
	use_credit_compare_month_bal VARCHAR(255),
	use_credit_compare_year_bal VARCHAR(255),
	use_credit_grid_inside_bal VARCHAR(255),
	use_credit_inside_bal_ratio VARCHAR(255),
	use_credit_inside_compare_month_bal VARCHAR(255),
	use_credit_inside_compare_year_bal VARCHAR(255),
	use_credit_grid_outside_bal VARCHAR(255),
	use_credit_outside_bal_ratio VARCHAR(255),
	use_credit_outside_compare_month_bal VARCHAR(255),
	use_credit_outside_compare_year_bal VARCHAR(255),
	total_bad_bal VARCHAR(225),
	total_bad_compare_month_bal VARCHAR(225),
	total_bad_compare_year_bal VARCHAR(225),
	total_bad_ratio VARCHAR(225),
	total_bad_grid_inside_ratio VARCHAR(225),
	total_bad_grid_outside_ratio VARCHAR(225),
	total_overdue_bal VARCHAR(225),
	total_overdue_compare_month_bal VARCHAR(225),
	total_overdue_compare_year_bal VARCHAR(225),
	total_overdue_ratio VARCHAR(225),
	total_overdue_grid_inside_ratio VARCHAR(225),
	total_overdue_grid_outside_ratio VARCHAR(225),
	total_interest_ratio VARCHAR(225),
	total_interest_grid_inside_ratio VARCHAR(225),
	total_interest_grid_outside_ratio VARCHAR(225),
	credit_num INT(10),
	credit_bal VARCHAR(225),
	credit_bad_bal VARCHAR(225),
	credit_bad_ratio VARCHAR(225),
	credit_overdue_bal VARCHAR(225),
	credit_overdue_ratio VARCHAR(225),
	ensure_num INT(10),
	ensure_bal VARCHAR(225),
	ensure_bad_bal VARCHAR(225),
	ensure_bad_ratio VARCHAR(225),
	ensure_overdue_bal VARCHAR(225),
	ensure_overdue_ratio VARCHAR(225),
	mortgage_num INT(10),
	mortgage_bal VARCHAR(225),
	mortgage_bad_bal VARCHAR(225),
	mortgage_bad_ratio VARCHAR(225),
	mortgage_overdue_bal VARCHAR(225),
	mortgage_overdue_ratio VARCHAR(225),
	pledge_num INT(10),
	pledge_bal VARCHAR(225),
	pledge_bad_bal VARCHAR(225),
	pledge_bad_ratio VARCHAR(225),
	pledge_overdue_bal VARCHAR(225),
	pledge_overdue_ratio VARCHAR(225),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_loan_org is '小微视图-资产模块-机构纬度';

comment on column pa_view_loan_org.loan_id is '贷款视图id';

comment on column pa_view_loan_org.loan_ftp is '贷款ftp(万元，保留两位小数)';

comment on column pa_view_loan_org.org_code is '机构编号';

comment on column pa_view_loan_org.customer_type is '客户类型 1-绩效客户 2-授信客户  3-不含乡贷授信客户';

comment on column pa_view_loan_org.grant_credit_num is '授信户数';

comment on column pa_view_loan_org.grant_credit_compare_month_num is '授信户数较上月';

comment on column pa_view_loan_org.grant_credit_compare_year_num is '授信户数较年初';

comment on column pa_view_loan_org.grant_credit_grid_inside_num is '网格内授信户数';

comment on column pa_view_loan_org.grant_credit_inside_num_ratio is '网格内授信户数占比';

comment on column pa_view_loan_org.grant_credit_inside_compare_month_num is '网格内授信户数较上月';

comment on column pa_view_loan_org.grant_credit_inside_compare_year_num is '网格内授信户数较年初';

comment on column pa_view_loan_org.grant_credit_grid_outside_num is '网格外授信户数';

comment on column pa_view_loan_org.grant_credit_outside_num_ratio is '网格外授信户数占比';

comment on column pa_view_loan_org.grant_credit_outside_compare_month_num is '网格外授信户数较上月';

comment on column pa_view_loan_org.grant_credit_outside_compare_year_num is '网格外授信户数较年初';

comment on column pa_view_loan_org.use_credit_num is '用信户数';

comment on column pa_view_loan_org.use_credit_compare_month_num is '用信户数较上月';

comment on column pa_view_loan_org.use_credit_compare_year_num is '用信户数较年初';

comment on column pa_view_loan_org.use_credit_grid_inside_num is '网格内用信户数';

comment on column pa_view_loan_org.use_credit_inside_num_ratio is '网格内用信户数占比';

comment on column pa_view_loan_org.use_credit_inside_compare_month_num is '网格内用信户数较上月';

comment on column pa_view_loan_org.use_credit_inside_compare_year_num is '网格内用信户数较年初';

comment on column pa_view_loan_org.use_credit_grid_outside_num is '网格外用信户数';

comment on column pa_view_loan_org.use_credit_outside_num_ratio is '网格外用信户数占比';

comment on column pa_view_loan_org.use_credit_outside_compare_month_num is '网格外用信户数较上月';

comment on column pa_view_loan_org.use_credit_outside_compare_year_num is '网格外用信户数较年初';

comment on column pa_view_loan_org.grant_credit_bal is '授信金额(万元)';

comment on column pa_view_loan_org.grant_credit_compare_month_bal is '授信金额较上月(万元)';

comment on column pa_view_loan_org.grant_credit_compare_year_bal is '授信金额较年初(万元)';

comment on column pa_view_loan_org.grant_credit_grid_inside_bal is '网格内授信金额(万元)';

comment on column pa_view_loan_org.grant_credit_inside_bal_ratio is '网格内授信金额占比';

comment on column pa_view_loan_org.grant_credit_inside_compare_month_bal is '网格内授信金额较上月(万元)';

comment on column pa_view_loan_org.grant_credit_inside_compare_year_bal is '网格内授信金额较年初(万元)';

comment on column pa_view_loan_org.grant_credit_grid_outside_bal is '网格外授信金额(万元)';

comment on column pa_view_loan_org.grant_credit_outside_bal_ratio is '网格外授信金额占比';

comment on column pa_view_loan_org.grant_credit_outside_compare_month_bal is '网格外授信金额较上月(万元)';

comment on column pa_view_loan_org.grant_credit_outside_compare_year_bal is '网格外授信金额较年初(万元)';

comment on column pa_view_loan_org.use_credit_bal is '用信金额(万元)';

comment on column pa_view_loan_org.use_credit_compare_month_bal is '用信金额较上月(万元)';

comment on column pa_view_loan_org.use_credit_compare_year_bal is '用信金额较年初(万元)';

comment on column pa_view_loan_org.use_credit_grid_inside_bal is '网格内用信金额(万元)';

comment on column pa_view_loan_org.use_credit_inside_bal_ratio is '网格内用信金额占比';

comment on column pa_view_loan_org.use_credit_inside_compare_month_bal is '网格内用信金额较上月(万元)';

comment on column pa_view_loan_org.use_credit_inside_compare_year_bal is '网格内用信金额较年初(万元)';

comment on column pa_view_loan_org.use_credit_grid_outside_bal is '网格外用信金额(万元)';

comment on column pa_view_loan_org.use_credit_outside_bal_ratio is '网格外用信金额占比';

comment on column pa_view_loan_org.use_credit_outside_compare_month_bal is '网格外用信金额较上月(万元)';

comment on column pa_view_loan_org.use_credit_outside_compare_year_bal is '网格外用信金额较年初(万元)';

comment on column pa_view_loan_org.total_bad_bal is '总和-不良余额(万元)';

comment on column pa_view_loan_org.total_bad_compare_month_bal is '总和-不良余额较上月(万元)';

comment on column pa_view_loan_org.total_bad_compare_year_bal is '总和-不良余额较年初(万元)';

comment on column pa_view_loan_org.total_bad_ratio is '总和-不良率';

comment on column pa_view_loan_org.total_bad_grid_inside_ratio is '总和-网格内不良率';

comment on column pa_view_loan_org.total_bad_grid_outside_ratio is '总和-网格外不良率';

comment on column pa_view_loan_org.total_overdue_bal is '总和-逾欠余额(元)';

comment on column pa_view_loan_org.total_overdue_compare_month_bal is '总和-逾欠余额较上月(万元)';

comment on column pa_view_loan_org.total_overdue_compare_year_bal is '总和-逾欠余额较年初(万元)';

comment on column pa_view_loan_org.total_overdue_ratio is '总和-逾欠率';

comment on column pa_view_loan_org.total_overdue_grid_inside_ratio is '总和-网格内逾欠率';

comment on column pa_view_loan_org.total_overdue_grid_outside_ratio is '总和-网格外逾欠率';

comment on column pa_view_loan_org.total_interest_ratio is '总和-收息率';

comment on column pa_view_loan_org.total_interest_grid_inside_ratio is '总和-网格内收息率';

comment on column pa_view_loan_org.total_interest_grid_outside_ratio is '总和-网格外收息率';

comment on column pa_view_loan_org.credit_num is '信用-户数';

comment on column pa_view_loan_org.credit_bal is '信用-金额(万元)';

comment on column pa_view_loan_org.credit_bad_bal is '信用-不良余额(万元)';

comment on column pa_view_loan_org.credit_bad_ratio is '信用-不良率';

comment on column pa_view_loan_org.credit_overdue_bal is '信用-逾欠余额(万元)';

comment on column pa_view_loan_org.credit_overdue_ratio is '信用-逾欠率';

comment on column pa_view_loan_org.ensure_num is '保证-户数';

comment on column pa_view_loan_org.ensure_bal is '保证-金额(万元)';

comment on column pa_view_loan_org.ensure_bad_bal is '保证-不良余额(万元)';

comment on column pa_view_loan_org.ensure_bad_ratio is '保证-不良率';

comment on column pa_view_loan_org.ensure_overdue_bal is '保证-逾欠余额(万元)';

comment on column pa_view_loan_org.ensure_overdue_ratio is '保证-逾欠率';

comment on column pa_view_loan_org.mortgage_num is '抵押-户数';

comment on column pa_view_loan_org.mortgage_bal is '抵押-金额(万元)';

comment on column pa_view_loan_org.mortgage_bad_bal is '抵押-不良余额(万元)';

comment on column pa_view_loan_org.mortgage_bad_ratio is '抵押-不良率';

comment on column pa_view_loan_org.mortgage_overdue_bal is '抵押-逾欠余额(万元)';

comment on column pa_view_loan_org.mortgage_overdue_ratio is '抵押-逾欠率';

comment on column pa_view_loan_org.pledge_num is '质押-户数';

comment on column pa_view_loan_org.pledge_bal is '质押-金额(万元)';

comment on column pa_view_loan_org.pledge_bad_bal is '质押-不良余额(万元)';

comment on column pa_view_loan_org.pledge_bad_ratio is '质押-不良率';

comment on column pa_view_loan_org.pledge_overdue_bal is '质押-逾欠余额(万元)';

comment on column pa_view_loan_org.pledge_overdue_ratio is '质押-逾欠率';

comment on column pa_view_loan_org.create_time is '创建时间';

comment on column pa_view_loan_org.update_time is '更新时间';

create unique index INDEX33654377
	on pa_view_loan_org (loan_id);

alter table pa_view_loan_org
	add constraint CONS134252771
		primary key (loan_id);

create table pa_view_loan_org_grid
(
	loan_id BIGINT(19),
	loan_ftp VARCHAR(255),
	org_code VARCHAR(255),
	grid_code VARCHAR(255),
	customer_type VARCHAR(255),
	grant_credit_num INT(10),
	grant_credit_compare_month_num INT(10),
	grant_credit_compare_year_num INT(10),
	grant_credit_grid_inside_num INT(10),
	grant_credit_inside_num_ratio VARCHAR(255),
	grant_credit_inside_compare_month_num INT(10),
	grant_credit_inside_compare_year_num INT(10),
	grant_credit_grid_outside_num INT(10),
	grant_credit_outside_num_ratio VARCHAR(255),
	grant_credit_outside_compare_month_num INT(10),
	grant_credit_outside_compare_year_num INT(10),
	use_credit_num INT(10),
	use_credit_compare_month_num INT(10),
	use_credit_compare_year_num INT(10),
	use_credit_grid_inside_num INT(10),
	use_credit_inside_num_ratio VARCHAR(255),
	use_credit_inside_compare_month_num INT(10),
	use_credit_inside_compare_year_num INT(10),
	use_credit_grid_outside_num INT(10),
	use_credit_outside_num_ratio VARCHAR(255),
	use_credit_outside_compare_month_num INT(10),
	use_credit_outside_compare_year_num INT(10),
	grant_credit_bal VARCHAR(255),
	grant_credit_compare_month_bal VARCHAR(255),
	grant_credit_compare_year_bal VARCHAR(255),
	grant_credit_grid_inside_bal VARCHAR(255),
	grant_credit_inside_bal_ratio VARCHAR(255),
	grant_credit_inside_compare_month_bal VARCHAR(255),
	grant_credit_inside_compare_year_bal VARCHAR(255),
	grant_credit_grid_outside_bal VARCHAR(255),
	grant_credit_outside_bal_ratio VARCHAR(255),
	grant_credit_outside_compare_month_bal VARCHAR(255),
	grant_credit_outside_compare_year_bal VARCHAR(255),
	use_credit_bal VARCHAR(255),
	use_credit_compare_month_bal VARCHAR(255),
	use_credit_compare_year_bal VARCHAR(255),
	use_credit_grid_inside_bal VARCHAR(255),
	use_credit_inside_bal_ratio VARCHAR(255),
	use_credit_inside_compare_month_bal VARCHAR(255),
	use_credit_inside_compare_year_bal VARCHAR(255),
	use_credit_grid_outside_bal VARCHAR(255),
	use_credit_outside_bal_ratio VARCHAR(255),
	use_credit_outside_compare_month_bal VARCHAR(255),
	use_credit_outside_compare_year_bal VARCHAR(255),
	total_bad_bal VARCHAR(225),
	total_bad_compare_month_bal VARCHAR(225),
	total_bad_compare_year_bal VARCHAR(225),
	total_bad_ratio VARCHAR(225),
	total_bad_grid_inside_ratio VARCHAR(225),
	total_bad_grid_outside_ratio VARCHAR(225),
	total_overdue_bal VARCHAR(225),
	total_overdue_compare_month_bal VARCHAR(225),
	total_overdue_compare_year_bal VARCHAR(225),
	total_overdue_ratio VARCHAR(225),
	total_overdue_grid_inside_ratio VARCHAR(225),
	total_overdue_grid_outside_ratio VARCHAR(225),
	total_interest_ratio VARCHAR(225),
	total_interest_grid_inside_ratio VARCHAR(225),
	total_interest_grid_outside_ratio VARCHAR(225),
	credit_num INT(10),
	credit_bal VARCHAR(225),
	credit_bad_bal VARCHAR(225),
	credit_bad_ratio VARCHAR(225),
	credit_overdue_bal VARCHAR(225),
	credit_overdue_ratio VARCHAR(225),
	ensure_num INT(10),
	ensure_bal VARCHAR(225),
	ensure_bad_bal VARCHAR(225),
	ensure_bad_ratio VARCHAR(225),
	ensure_overdue_bal VARCHAR(225),
	ensure_overdue_ratio VARCHAR(225),
	mortgage_num INT(10),
	mortgage_bal VARCHAR(225),
	mortgage_bad_bal VARCHAR(225),
	mortgage_bad_ratio VARCHAR(225),
	mortgage_overdue_bal VARCHAR(225),
	mortgage_overdue_ratio VARCHAR(225),
	pledge_num INT(10),
	pledge_bal VARCHAR(225),
	pledge_bad_bal VARCHAR(225),
	pledge_bad_ratio VARCHAR(225),
	pledge_overdue_bal VARCHAR(225),
	pledge_overdue_ratio VARCHAR(225),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_loan_org_grid is '小微视图-贷款模块-网格、机构纬度';

comment on column pa_view_loan_org_grid.loan_id is '贷款视图id';

comment on column pa_view_loan_org_grid.loan_ftp is '贷款ftp(万元，保留两位小数)';

comment on column pa_view_loan_org_grid.org_code is '机构编号';

comment on column pa_view_loan_org_grid.grid_code is '网格编号';

comment on column pa_view_loan_org_grid.customer_type is '客户类型 1-绩效客户 2-授信客户  3-不含乡贷授信客户';

comment on column pa_view_loan_org_grid.grant_credit_num is '授信户数';

comment on column pa_view_loan_org_grid.grant_credit_compare_month_num is '授信户数较上月';

comment on column pa_view_loan_org_grid.grant_credit_compare_year_num is '授信户数较年初';

comment on column pa_view_loan_org_grid.grant_credit_grid_inside_num is '网格内授信户数';

comment on column pa_view_loan_org_grid.grant_credit_inside_num_ratio is '网格内授信户数占比';

comment on column pa_view_loan_org_grid.grant_credit_inside_compare_month_num is '网格内授信户数较上月';

comment on column pa_view_loan_org_grid.grant_credit_inside_compare_year_num is '网格内授信户数较年初';

comment on column pa_view_loan_org_grid.grant_credit_grid_outside_num is '网格外授信户数 0';

comment on column pa_view_loan_org_grid.grant_credit_outside_num_ratio is '网格外授信户数占比 0';

comment on column pa_view_loan_org_grid.grant_credit_outside_compare_month_num is '网格外授信户数较上月 0';

comment on column pa_view_loan_org_grid.grant_credit_outside_compare_year_num is '网格外授信户数较年初 0';

comment on column pa_view_loan_org_grid.use_credit_num is '用信户数';

comment on column pa_view_loan_org_grid.use_credit_compare_month_num is '用信户数较上月';

comment on column pa_view_loan_org_grid.use_credit_compare_year_num is '用信户数较年初';

comment on column pa_view_loan_org_grid.use_credit_grid_inside_num is '网格内用信户数';

comment on column pa_view_loan_org_grid.use_credit_inside_num_ratio is '网格内用信户数占比';

comment on column pa_view_loan_org_grid.use_credit_inside_compare_month_num is '网格内用信户数较上月';

comment on column pa_view_loan_org_grid.use_credit_inside_compare_year_num is '网格内用信户数较年初';

comment on column pa_view_loan_org_grid.use_credit_grid_outside_num is '网格外用信户数 0';

comment on column pa_view_loan_org_grid.use_credit_outside_num_ratio is '网格外用信户数占比 0';

comment on column pa_view_loan_org_grid.use_credit_outside_compare_month_num is '网格外用信户数较上月 0';

comment on column pa_view_loan_org_grid.use_credit_outside_compare_year_num is '网格外用信户数较年初 0';

comment on column pa_view_loan_org_grid.grant_credit_bal is '授信金额(万元，整数)';

comment on column pa_view_loan_org_grid.grant_credit_compare_month_bal is '授信金额较上月(万元，整数)';

comment on column pa_view_loan_org_grid.grant_credit_compare_year_bal is '授信金额较年初(万元，整数)';

comment on column pa_view_loan_org_grid.grant_credit_grid_inside_bal is '网格内授信金额(万元，整数)';

comment on column pa_view_loan_org_grid.grant_credit_inside_bal_ratio is '网格内授信金额占比';

comment on column pa_view_loan_org_grid.grant_credit_inside_compare_month_bal is '网格内授信金额较上月(万元，整数)';

comment on column pa_view_loan_org_grid.grant_credit_inside_compare_year_bal is '网格内授信金额较年初(万元，整数)';

comment on column pa_view_loan_org_grid.grant_credit_grid_outside_bal is '网格外授信金额(万元，整数) 0';

comment on column pa_view_loan_org_grid.grant_credit_outside_bal_ratio is '网格外授信金额占比 0';

comment on column pa_view_loan_org_grid.grant_credit_outside_compare_month_bal is '网格外授信金额较上月(万元，整数) 0';

comment on column pa_view_loan_org_grid.grant_credit_outside_compare_year_bal is '网格外授信金额较年初(万元，整数) 0';

comment on column pa_view_loan_org_grid.use_credit_bal is '用信金额(万元，整数)';

comment on column pa_view_loan_org_grid.use_credit_compare_month_bal is '用信金额较上月(万元，整数)';

comment on column pa_view_loan_org_grid.use_credit_compare_year_bal is '用信金额较年初(万元，整数)';

comment on column pa_view_loan_org_grid.use_credit_grid_inside_bal is '网格内用信金额(万元，整数)';

comment on column pa_view_loan_org_grid.use_credit_inside_bal_ratio is '网格内用信金额占比';

comment on column pa_view_loan_org_grid.use_credit_inside_compare_month_bal is '网格内用信金额较上月(万元，整数)';

comment on column pa_view_loan_org_grid.use_credit_inside_compare_year_bal is '网格内用信金额较年初(万元，整数)';

comment on column pa_view_loan_org_grid.use_credit_grid_outside_bal is '网格外用信金额(万元，整数) 0';

comment on column pa_view_loan_org_grid.use_credit_outside_bal_ratio is '网格外用信金额占比 0';

comment on column pa_view_loan_org_grid.use_credit_outside_compare_month_bal is '网格外用信金额较上月(万元，整数) 0';

comment on column pa_view_loan_org_grid.use_credit_outside_compare_year_bal is '网格外用信金额较年初(万元，整数) 0';

comment on column pa_view_loan_org_grid.total_bad_bal is '总和-不良余额(万元，整数)';

comment on column pa_view_loan_org_grid.total_bad_compare_month_bal is '总和-不良余额较上月(万元，整数)';

comment on column pa_view_loan_org_grid.total_bad_compare_year_bal is '总和-不良余额较年初(万元，整数)';

comment on column pa_view_loan_org_grid.total_bad_ratio is '总和-不良率';

comment on column pa_view_loan_org_grid.total_bad_grid_inside_ratio is '总和-网格内不良率';

comment on column pa_view_loan_org_grid.total_bad_grid_outside_ratio is '总和-网格外不良率 0';

comment on column pa_view_loan_org_grid.total_overdue_bal is '总和-逾欠余额(万元，整数)';

comment on column pa_view_loan_org_grid.total_overdue_compare_month_bal is '总和-逾欠余额较上月(万元，整数)';

comment on column pa_view_loan_org_grid.total_overdue_compare_year_bal is '总和-逾欠余额较年初(万元，整数)';

comment on column pa_view_loan_org_grid.total_overdue_ratio is '总和-逾欠率';

comment on column pa_view_loan_org_grid.total_overdue_grid_inside_ratio is '总和-网格内逾欠率';

comment on column pa_view_loan_org_grid.total_overdue_grid_outside_ratio is '总和-网格外逾欠率 0';

comment on column pa_view_loan_org_grid.total_interest_ratio is '总和-收息率';

comment on column pa_view_loan_org_grid.total_interest_grid_inside_ratio is '总和-网格内收息率';

comment on column pa_view_loan_org_grid.total_interest_grid_outside_ratio is '总和-网格外收息率 0';

comment on column pa_view_loan_org_grid.credit_num is '信用-户数';

comment on column pa_view_loan_org_grid.credit_bal is '信用-金额(万元，整数)';

comment on column pa_view_loan_org_grid.credit_bad_bal is '信用-不良余额(万元，整数)';

comment on column pa_view_loan_org_grid.credit_bad_ratio is '信用-不良率';

comment on column pa_view_loan_org_grid.credit_overdue_bal is '信用-逾欠余额(万元，整数)';

comment on column pa_view_loan_org_grid.credit_overdue_ratio is '信用-逾欠率';

comment on column pa_view_loan_org_grid.ensure_num is '保证-户数';

comment on column pa_view_loan_org_grid.ensure_bal is '保证-金额(万元，整数)';

comment on column pa_view_loan_org_grid.ensure_bad_bal is '保证-不良余额(万元，整数)';

comment on column pa_view_loan_org_grid.ensure_bad_ratio is '保证-不良率';

comment on column pa_view_loan_org_grid.ensure_overdue_bal is '保证-逾欠余额(万元，整数)';

comment on column pa_view_loan_org_grid.ensure_overdue_ratio is '保证-逾欠率';

comment on column pa_view_loan_org_grid.mortgage_num is '抵押-户数';

comment on column pa_view_loan_org_grid.mortgage_bal is '抵押-金额(万元，整数)';

comment on column pa_view_loan_org_grid.mortgage_bad_bal is '抵押-不良余额(万元，整数)';

comment on column pa_view_loan_org_grid.mortgage_bad_ratio is '抵押-不良率';

comment on column pa_view_loan_org_grid.mortgage_overdue_bal is '抵押-逾欠余额(万元，整数)';

comment on column pa_view_loan_org_grid.mortgage_overdue_ratio is '抵押-逾欠率';

comment on column pa_view_loan_org_grid.pledge_num is '质押-户数';

comment on column pa_view_loan_org_grid.pledge_bal is '质押-金额(万元，整数)';

comment on column pa_view_loan_org_grid.pledge_bad_bal is '质押-不良余额(万元，整数)';

comment on column pa_view_loan_org_grid.pledge_bad_ratio is '质押-不良率';

comment on column pa_view_loan_org_grid.pledge_overdue_bal is '质押-逾欠余额(万元，整数)';

comment on column pa_view_loan_org_grid.pledge_overdue_ratio is '质押-逾欠率';

comment on column pa_view_loan_org_grid.create_time is '创建时间';

comment on column pa_view_loan_org_grid.update_time is '更新时间';

create unique index INDEX33654379
	on pa_view_loan_org_grid (loan_id);

alter table pa_view_loan_org_grid
	add constraint CONS134252772
		primary key (loan_id);

create table pa_view_prosperous_loan_manager
(
	prosperous_loan_id BIGINT(19),
	loan_ftp VARCHAR(255),
	manager_code VARCHAR(255),
	product_code VARCHAR(255),
	customer_type VARCHAR(255),
	grant_credit_num INT(10),
	grant_credit_compare_month_num INT(10),
	grant_credit_compare_year_num INT(10),
	grant_credit_grid_inside_num INT(10),
	grant_credit_inside_num_ratio VARCHAR(255),
	grant_credit_inside_compare_month_num INT(10),
	grant_credit_inside_compare_year_num INT(10),
	grant_credit_grid_outside_num INT(10),
	grant_credit_outside_num_ratio VARCHAR(255),
	grant_credit_outside_compare_month_num INT(10),
	grant_credit_outside_compare_year_num INT(10),
	use_credit_num INT(10),
	use_credit_compare_month_num INT(10),
	use_credit_compare_year_num INT(10),
	use_credit_grid_inside_num INT(10),
	use_credit_inside_num_ratio VARCHAR(255),
	use_credit_inside_compare_month_num INT(10),
	use_credit_inside_compare_year_num INT(10),
	use_credit_grid_outside_num INT(10),
	use_credit_outside_num_ratio VARCHAR(255),
	use_credit_outside_compare_month_num INT(10),
	use_credit_outside_compare_year_num INT(10),
	grant_credit_bal VARCHAR(255),
	grant_credit_compare_month_bal VARCHAR(255),
	grant_credit_compare_year_bal VARCHAR(255),
	grant_credit_grid_inside_bal VARCHAR(255),
	grant_credit_inside_bal_ratio VARCHAR(255),
	grant_credit_inside_compare_month_bal VARCHAR(255),
	grant_credit_inside_compare_year_bal VARCHAR(255),
	grant_credit_grid_outside_bal VARCHAR(255),
	grant_credit_outside_bal_ratio VARCHAR(255),
	grant_credit_outside_compare_month_bal VARCHAR(255),
	grant_credit_outside_compare_year_bal VARCHAR(255),
	use_credit_bal VARCHAR(255),
	use_credit_compare_month_bal VARCHAR(255),
	use_credit_compare_year_bal VARCHAR(255),
	use_credit_grid_inside_bal VARCHAR(255),
	use_credit_inside_bal_ratio VARCHAR(255),
	use_credit_inside_compare_month_bal VARCHAR(255),
	use_credit_inside_compare_year_bal VARCHAR(255),
	use_credit_grid_outside_bal VARCHAR(255),
	use_credit_outside_bal_ratio VARCHAR(255),
	use_credit_outside_compare_month_bal VARCHAR(255),
	use_credit_outside_compare_year_bal VARCHAR(255),
	total_bad_bal VARCHAR(225),
	total_bad_compare_month_bal VARCHAR(225),
	total_bad_compare_year_bal VARCHAR(225),
	total_bad_ratio VARCHAR(225),
	total_bad_grid_inside_ratio VARCHAR(225),
	total_bad_grid_outside_ratio VARCHAR(225),
	total_overdue_bal VARCHAR(225),
	total_overdue_compare_month_bal VARCHAR(225),
	total_overdue_compare_year_bal VARCHAR(225),
	total_overdue_ratio VARCHAR(225),
	total_overdue_grid_inside_ratio VARCHAR(225),
	total_overdue_grid_outside_ratio VARCHAR(225),
	total_interest_ratio VARCHAR(225),
	total_interest_grid_inside_ratio VARCHAR(225),
	total_interest_grid_outside_ratio VARCHAR(225),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_prosperous_loan_manager is '小微视图-凡荣贷模块-客户经理纬度';

comment on column pa_view_prosperous_loan_manager.prosperous_loan_id is '贷款视图id';

comment on column pa_view_prosperous_loan_manager.loan_ftp is '贷款ftp(万元，保留两位小数)';

comment on column pa_view_prosperous_loan_manager.manager_code is '客户经理编号';

comment on column pa_view_prosperous_loan_manager.product_code is '产品编号';

comment on column pa_view_prosperous_loan_manager.customer_type is '客户类型 1-绩效客户 2-授信客户 3-不含乡贷授信客户';

comment on column pa_view_prosperous_loan_manager.grant_credit_num is '授信户数';

comment on column pa_view_prosperous_loan_manager.grant_credit_compare_month_num is '授信户数较上月';

comment on column pa_view_prosperous_loan_manager.grant_credit_compare_year_num is '授信户数较年初';

comment on column pa_view_prosperous_loan_manager.grant_credit_grid_inside_num is '网格内授信户数';

comment on column pa_view_prosperous_loan_manager.grant_credit_inside_num_ratio is '网格内授信户数占比';

comment on column pa_view_prosperous_loan_manager.grant_credit_inside_compare_month_num is '网格内授信户数较上月';

comment on column pa_view_prosperous_loan_manager.grant_credit_inside_compare_year_num is '网格内授信户数较年初';

comment on column pa_view_prosperous_loan_manager.grant_credit_grid_outside_num is '网格外授信户数';

comment on column pa_view_prosperous_loan_manager.grant_credit_outside_num_ratio is '网格外授信户数占比';

comment on column pa_view_prosperous_loan_manager.grant_credit_outside_compare_month_num is '网格外授信户数较上月';

comment on column pa_view_prosperous_loan_manager.grant_credit_outside_compare_year_num is '网格外授信户数较年初';

comment on column pa_view_prosperous_loan_manager.use_credit_num is '用信户数';

comment on column pa_view_prosperous_loan_manager.use_credit_compare_month_num is '用信户数较上月';

comment on column pa_view_prosperous_loan_manager.use_credit_compare_year_num is '用信户数较年初';

comment on column pa_view_prosperous_loan_manager.use_credit_grid_inside_num is '网格内用信户数';

comment on column pa_view_prosperous_loan_manager.use_credit_inside_num_ratio is '网格内用信户数占比';

comment on column pa_view_prosperous_loan_manager.use_credit_inside_compare_month_num is '网格内用信户数较上月';

comment on column pa_view_prosperous_loan_manager.use_credit_inside_compare_year_num is '网格内用信户数较年初';

comment on column pa_view_prosperous_loan_manager.use_credit_grid_outside_num is '网格外用信户数';

comment on column pa_view_prosperous_loan_manager.use_credit_outside_num_ratio is '网格外用信户数占比';

comment on column pa_view_prosperous_loan_manager.use_credit_outside_compare_month_num is '网格外用信户数较上月';

comment on column pa_view_prosperous_loan_manager.use_credit_outside_compare_year_num is '网格外用信户数较年初';

comment on column pa_view_prosperous_loan_manager.grant_credit_bal is '授信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager.grant_credit_compare_month_bal is '授信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager.grant_credit_compare_year_bal is '授信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager.grant_credit_grid_inside_bal is '网格内授信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager.grant_credit_inside_bal_ratio is '网格内授信金额占比';

comment on column pa_view_prosperous_loan_manager.grant_credit_inside_compare_month_bal is '网格内授信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager.grant_credit_inside_compare_year_bal is '网格内授信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager.grant_credit_grid_outside_bal is '网格外授信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager.grant_credit_outside_bal_ratio is '网格外授信金额占比';

comment on column pa_view_prosperous_loan_manager.grant_credit_outside_compare_month_bal is '网格外授信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager.grant_credit_outside_compare_year_bal is '网格外授信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_bal is '用信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_compare_month_bal is '用信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_compare_year_bal is '用信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_grid_inside_bal is '网格内用信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_inside_bal_ratio is '网格内用信金额占比';

comment on column pa_view_prosperous_loan_manager.use_credit_inside_compare_month_bal is '网格内用信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_inside_compare_year_bal is '网格内用信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_grid_outside_bal is '网格外用信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_outside_bal_ratio is '网格外用信金额占比';

comment on column pa_view_prosperous_loan_manager.use_credit_outside_compare_month_bal is '网格外用信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager.use_credit_outside_compare_year_bal is '网格外用信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager.total_bad_bal is '总和-不良余额(万元，整数)';

comment on column pa_view_prosperous_loan_manager.total_bad_compare_month_bal is '总和-不良余额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager.total_bad_compare_year_bal is '总和-不良余额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager.total_bad_ratio is '总和-不良率';

comment on column pa_view_prosperous_loan_manager.total_bad_grid_inside_ratio is '总和-网格内不良率';

comment on column pa_view_prosperous_loan_manager.total_bad_grid_outside_ratio is '总和-网格外不良率';

comment on column pa_view_prosperous_loan_manager.total_overdue_bal is '总和-逾欠余额(万元，整数)';

comment on column pa_view_prosperous_loan_manager.total_overdue_compare_month_bal is '总和-逾欠余额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager.total_overdue_compare_year_bal is '总和-逾欠余额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager.total_overdue_ratio is '总和-逾欠率';

comment on column pa_view_prosperous_loan_manager.total_overdue_grid_inside_ratio is '总和-网格内逾欠率';

comment on column pa_view_prosperous_loan_manager.total_overdue_grid_outside_ratio is '总和-网格外逾欠率';

comment on column pa_view_prosperous_loan_manager.total_interest_ratio is '总和-收息率';

comment on column pa_view_prosperous_loan_manager.total_interest_grid_inside_ratio is '总和-网格内收息率';

comment on column pa_view_prosperous_loan_manager.total_interest_grid_outside_ratio is '总和-网格外收息率';

comment on column pa_view_prosperous_loan_manager.create_time is '创建时间';

comment on column pa_view_prosperous_loan_manager.update_time is '更新时间';

create unique index INDEX33654381
	on pa_view_prosperous_loan_manager (prosperous_loan_id);

alter table pa_view_prosperous_loan_manager
	add constraint CONS134252773
		primary key (prosperous_loan_id);

create table pa_view_prosperous_loan_manager_grid
(
	prosperous_loan_id BIGINT(19),
	loan_ftp VARCHAR(255),
	manager_code VARCHAR(255),
	grid_code VARCHAR(255),
	product_code VARCHAR(255),
	customer_type VARCHAR(255),
	grant_credit_num INT(10),
	grant_credit_compare_month_num INT(10),
	grant_credit_compare_year_num INT(10),
	grant_credit_grid_inside_num INT(10),
	grant_credit_inside_num_ratio VARCHAR(255),
	grant_credit_inside_compare_month_num INT(10),
	grant_credit_inside_compare_year_num INT(10),
	grant_credit_grid_outside_num INT(10),
	grant_credit_outside_num_ratio VARCHAR(255),
	grant_credit_outside_compare_month_num INT(10),
	grant_credit_outside_compare_year_num INT(10),
	use_credit_num INT(10),
	use_credit_compare_month_num INT(10),
	use_credit_compare_year_num INT(10),
	use_credit_grid_inside_num INT(10),
	use_credit_inside_num_ratio VARCHAR(255),
	use_credit_inside_compare_month_num INT(10),
	use_credit_inside_compare_year_num INT(10),
	use_credit_grid_outside_num INT(10),
	use_credit_outside_num_ratio VARCHAR(255),
	use_credit_outside_compare_month_num INT(10),
	use_credit_outside_compare_year_num INT(10),
	grant_credit_bal VARCHAR(255),
	grant_credit_compare_month_bal VARCHAR(255),
	grant_credit_compare_year_bal VARCHAR(255),
	grant_credit_grid_inside_bal VARCHAR(255),
	grant_credit_inside_bal_ratio VARCHAR(255),
	grant_credit_inside_compare_month_bal VARCHAR(255),
	grant_credit_inside_compare_year_bal VARCHAR(255),
	grant_credit_grid_outside_bal VARCHAR(255),
	grant_credit_outside_bal_ratio VARCHAR(255),
	grant_credit_outside_compare_month_bal VARCHAR(255),
	grant_credit_outside_compare_year_bal VARCHAR(255),
	use_credit_bal VARCHAR(255),
	use_credit_compare_month_bal VARCHAR(255),
	use_credit_compare_year_bal VARCHAR(255),
	use_credit_grid_inside_bal VARCHAR(255),
	use_credit_inside_bal_ratio VARCHAR(255),
	use_credit_inside_compare_month_bal VARCHAR(255),
	use_credit_inside_compare_year_bal VARCHAR(255),
	use_credit_grid_outside_bal VARCHAR(255),
	use_credit_outside_bal_ratio VARCHAR(255),
	use_credit_outside_compare_month_bal VARCHAR(255),
	use_credit_outside_compare_year_bal VARCHAR(255),
	total_bad_bal VARCHAR(225),
	total_bad_compare_month_bal VARCHAR(225),
	total_bad_compare_year_bal VARCHAR(225),
	total_bad_ratio VARCHAR(225),
	total_bad_grid_inside_ratio VARCHAR(225),
	total_bad_grid_outside_ratio VARCHAR(225),
	total_overdue_bal VARCHAR(225),
	total_overdue_compare_month_bal VARCHAR(225),
	total_overdue_compare_year_bal VARCHAR(225),
	total_overdue_ratio VARCHAR(225),
	total_overdue_grid_inside_ratio VARCHAR(225),
	total_overdue_grid_outside_ratio VARCHAR(225),
	total_interest_ratio VARCHAR(225),
	total_interest_grid_inside_ratio VARCHAR(225),
	total_interest_grid_outside_ratio VARCHAR(225),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_prosperous_loan_manager_grid is '小微视图-凡荣贷模块-网格、客户经理纬度';

comment on column pa_view_prosperous_loan_manager_grid.prosperous_loan_id is '贷款视图id';

comment on column pa_view_prosperous_loan_manager_grid.loan_ftp is '贷款ftp(万元，保留两位小数)';

comment on column pa_view_prosperous_loan_manager_grid.manager_code is '客户经理编号';

comment on column pa_view_prosperous_loan_manager_grid.grid_code is '网格编号';

comment on column pa_view_prosperous_loan_manager_grid.product_code is '产品编号';

comment on column pa_view_prosperous_loan_manager_grid.customer_type is '客户类型 1-绩效客户 2-授信客户  3-不含乡贷授信客户';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_num is '授信户数';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_compare_month_num is '授信户数较上月';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_compare_year_num is '授信户数较年初';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_grid_inside_num is '网格内授信户数';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_inside_num_ratio is '网格内授信户数占比';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_inside_compare_month_num is '网格内授信户数较上月';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_inside_compare_year_num is '网格内授信户数较年初';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_grid_outside_num is '网格外授信户数 0';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_outside_num_ratio is '网格外授信户数占比 0';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_outside_compare_month_num is '网格外授信户数较上月 0';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_outside_compare_year_num is '网格外授信户数较年初 0';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_num is '用信户数';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_compare_month_num is '用信户数较上月';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_compare_year_num is '用信户数较年初';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_grid_inside_num is '网格内用信户数';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_inside_num_ratio is '网格内用信户数占比';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_inside_compare_month_num is '网格内用信户数较上月';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_inside_compare_year_num is '网格内用信户数较年初';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_grid_outside_num is '网格外用信户数 0';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_outside_num_ratio is '网格外用信户数占比 0';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_outside_compare_month_num is '网格外用信户数较上月 0';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_outside_compare_year_num is '网格外用信户数较年初 0';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_bal is '授信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_compare_month_bal is '授信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_compare_year_bal is '授信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_grid_inside_bal is '网格内授信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_inside_bal_ratio is '网格内授信金额占比';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_inside_compare_month_bal is '网格内授信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_inside_compare_year_bal is '网格内授信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_grid_outside_bal is '网格外授信金额(万元，整数) 0';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_outside_bal_ratio is '网格外授信金额占比 0';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_outside_compare_month_bal is '网格外授信金额较上月(万元，整数) 0';

comment on column pa_view_prosperous_loan_manager_grid.grant_credit_outside_compare_year_bal is '网格外授信金额较年初(万元，整数) 0';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_bal is '用信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_compare_month_bal is '用信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_compare_year_bal is '用信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_grid_inside_bal is '网格内用信金额(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_inside_bal_ratio is '网格内用信金额占比';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_inside_compare_month_bal is '网格内用信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_inside_compare_year_bal is '网格内用信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_grid_outside_bal is '网格外用信金额(万元，整数) 0';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_outside_bal_ratio is '网格外用信金额占比 0';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_outside_compare_month_bal is '网格外用信金额较上月(万元，整数) 0';

comment on column pa_view_prosperous_loan_manager_grid.use_credit_outside_compare_year_bal is '网格外用信金额较年初(万元，整数) 0';

comment on column pa_view_prosperous_loan_manager_grid.total_bad_bal is '总和-不良余额(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.total_bad_compare_month_bal is '总和-不良余额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.total_bad_compare_year_bal is '总和-不良余额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.total_bad_ratio is '总和-不良率';

comment on column pa_view_prosperous_loan_manager_grid.total_bad_grid_inside_ratio is '总和-网格内不良率';

comment on column pa_view_prosperous_loan_manager_grid.total_bad_grid_outside_ratio is '总和-网格外不良率 0';

comment on column pa_view_prosperous_loan_manager_grid.total_overdue_bal is '总和-逾欠余额(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.total_overdue_compare_month_bal is '总和-逾欠余额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.total_overdue_compare_year_bal is '总和-逾欠余额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_manager_grid.total_overdue_ratio is '总和-逾欠率';

comment on column pa_view_prosperous_loan_manager_grid.total_overdue_grid_inside_ratio is '总和-网格内逾欠率';

comment on column pa_view_prosperous_loan_manager_grid.total_overdue_grid_outside_ratio is '总和-网格外逾欠率 0';

comment on column pa_view_prosperous_loan_manager_grid.total_interest_ratio is '总和-收息率';

comment on column pa_view_prosperous_loan_manager_grid.total_interest_grid_inside_ratio is '总和-网格内收息率';

comment on column pa_view_prosperous_loan_manager_grid.total_interest_grid_outside_ratio is '总和-网格外收息率 0';

comment on column pa_view_prosperous_loan_manager_grid.create_time is '创建时间';

comment on column pa_view_prosperous_loan_manager_grid.update_time is '更新时间';

create unique index INDEX33654383
	on pa_view_prosperous_loan_manager_grid (prosperous_loan_id);

alter table pa_view_prosperous_loan_manager_grid
	add constraint CONS134252774
		primary key (prosperous_loan_id);

create table pa_view_prosperous_loan_org
(
	prosperous_loan_id BIGINT(19),
	loan_ftp VARCHAR(255),
	org_code VARCHAR(255),
	product_code VARCHAR(255),
	customer_type VARCHAR(255),
	grant_credit_num INT(10),
	grant_credit_compare_month_num INT(10),
	grant_credit_compare_year_num INT(10),
	grant_credit_grid_inside_num INT(10),
	grant_credit_inside_num_ratio VARCHAR(255),
	grant_credit_inside_compare_month_num INT(10),
	grant_credit_inside_compare_year_num INT(10),
	grant_credit_grid_outside_num INT(10),
	grant_credit_outside_num_ratio VARCHAR(255),
	grant_credit_outside_compare_month_num INT(10),
	grant_credit_outside_compare_year_num INT(10),
	use_credit_num INT(10),
	use_credit_compare_month_num INT(10),
	use_credit_compare_year_num INT(10),
	use_credit_grid_inside_num INT(10),
	use_credit_inside_num_ratio VARCHAR(255),
	use_credit_inside_compare_month_num INT(10),
	use_credit_inside_compare_year_num INT(10),
	use_credit_grid_outside_num INT(10),
	use_credit_outside_num_ratio VARCHAR(255),
	use_credit_outside_compare_month_num INT(10),
	use_credit_outside_compare_year_num INT(10),
	grant_credit_bal VARCHAR(255),
	grant_credit_compare_month_bal VARCHAR(255),
	grant_credit_compare_year_bal VARCHAR(255),
	grant_credit_grid_inside_bal VARCHAR(255),
	grant_credit_inside_bal_ratio VARCHAR(255),
	grant_credit_inside_compare_month_bal VARCHAR(255),
	grant_credit_inside_compare_year_bal VARCHAR(255),
	grant_credit_grid_outside_bal VARCHAR(255),
	grant_credit_outside_bal_ratio VARCHAR(255),
	grant_credit_outside_compare_month_bal VARCHAR(255),
	grant_credit_outside_compare_year_bal VARCHAR(255),
	use_credit_bal VARCHAR(255),
	use_credit_compare_month_bal VARCHAR(255),
	use_credit_compare_year_bal VARCHAR(255),
	use_credit_grid_inside_bal VARCHAR(255),
	use_credit_inside_bal_ratio VARCHAR(255),
	use_credit_inside_compare_month_bal VARCHAR(255),
	use_credit_inside_compare_year_bal VARCHAR(255),
	use_credit_grid_outside_bal VARCHAR(255),
	use_credit_outside_bal_ratio VARCHAR(255),
	use_credit_outside_compare_month_bal VARCHAR(255),
	use_credit_outside_compare_year_bal VARCHAR(255),
	total_bad_bal VARCHAR(225),
	total_bad_compare_month_bal VARCHAR(225),
	total_bad_compare_year_bal VARCHAR(225),
	total_bad_ratio VARCHAR(225),
	total_bad_grid_inside_ratio VARCHAR(225),
	total_bad_grid_outside_ratio VARCHAR(225),
	total_overdue_bal VARCHAR(225),
	total_overdue_compare_month_bal VARCHAR(225),
	total_overdue_compare_year_bal VARCHAR(225),
	total_overdue_ratio VARCHAR(225),
	total_overdue_grid_inside_ratio VARCHAR(225),
	total_overdue_grid_outside_ratio VARCHAR(225),
	total_interest_ratio VARCHAR(225),
	total_interest_grid_inside_ratio VARCHAR(225),
	total_interest_grid_outside_ratio VARCHAR(225),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_prosperous_loan_org is '小微视图-凡荣贷模块-机构纬度';

comment on column pa_view_prosperous_loan_org.prosperous_loan_id is '贷款视图id';

comment on column pa_view_prosperous_loan_org.loan_ftp is '贷款ftp(万元，保留两位小数)';

comment on column pa_view_prosperous_loan_org.org_code is '机构编号';

comment on column pa_view_prosperous_loan_org.product_code is '产品编号';

comment on column pa_view_prosperous_loan_org.customer_type is '客户类型 1-绩效客户 2-授信客户  3-不含乡贷授信客户';

comment on column pa_view_prosperous_loan_org.grant_credit_num is '授信户数';

comment on column pa_view_prosperous_loan_org.grant_credit_compare_month_num is '授信户数较上月';

comment on column pa_view_prosperous_loan_org.grant_credit_compare_year_num is '授信户数较年初';

comment on column pa_view_prosperous_loan_org.grant_credit_grid_inside_num is '网格内授信户数';

comment on column pa_view_prosperous_loan_org.grant_credit_inside_num_ratio is '网格内授信户数占比';

comment on column pa_view_prosperous_loan_org.grant_credit_inside_compare_month_num is '网格内授信户数较上月';

comment on column pa_view_prosperous_loan_org.grant_credit_inside_compare_year_num is '网格内授信户数较年初';

comment on column pa_view_prosperous_loan_org.grant_credit_grid_outside_num is '网格外授信户数';

comment on column pa_view_prosperous_loan_org.grant_credit_outside_num_ratio is '网格外授信户数占比';

comment on column pa_view_prosperous_loan_org.grant_credit_outside_compare_month_num is '网格外授信户数较上月';

comment on column pa_view_prosperous_loan_org.grant_credit_outside_compare_year_num is '网格外授信户数较年初';

comment on column pa_view_prosperous_loan_org.use_credit_num is '用信户数';

comment on column pa_view_prosperous_loan_org.use_credit_compare_month_num is '用信户数较上月';

comment on column pa_view_prosperous_loan_org.use_credit_compare_year_num is '用信户数较年初';

comment on column pa_view_prosperous_loan_org.use_credit_grid_inside_num is '网格内用信户数';

comment on column pa_view_prosperous_loan_org.use_credit_inside_num_ratio is '网格内用信户数占比';

comment on column pa_view_prosperous_loan_org.use_credit_inside_compare_month_num is '网格内用信户数较上月';

comment on column pa_view_prosperous_loan_org.use_credit_inside_compare_year_num is '网格内用信户数较年初';

comment on column pa_view_prosperous_loan_org.use_credit_grid_outside_num is '网格外用信户数';

comment on column pa_view_prosperous_loan_org.use_credit_outside_num_ratio is '网格外用信户数占比';

comment on column pa_view_prosperous_loan_org.use_credit_outside_compare_month_num is '网格外用信户数较上月';

comment on column pa_view_prosperous_loan_org.use_credit_outside_compare_year_num is '网格外用信户数较年初';

comment on column pa_view_prosperous_loan_org.grant_credit_bal is '授信金额(万元)';

comment on column pa_view_prosperous_loan_org.grant_credit_compare_month_bal is '授信金额较上月(万元)';

comment on column pa_view_prosperous_loan_org.grant_credit_compare_year_bal is '授信金额较年初(万元)';

comment on column pa_view_prosperous_loan_org.grant_credit_grid_inside_bal is '网格内授信金额(万元)';

comment on column pa_view_prosperous_loan_org.grant_credit_inside_bal_ratio is '网格内授信金额占比';

comment on column pa_view_prosperous_loan_org.grant_credit_inside_compare_month_bal is '网格内授信金额较上月(万元)';

comment on column pa_view_prosperous_loan_org.grant_credit_inside_compare_year_bal is '网格内授信金额较年初(万元)';

comment on column pa_view_prosperous_loan_org.grant_credit_grid_outside_bal is '网格外授信金额(万元)';

comment on column pa_view_prosperous_loan_org.grant_credit_outside_bal_ratio is '网格外授信金额占比';

comment on column pa_view_prosperous_loan_org.grant_credit_outside_compare_month_bal is '网格外授信金额较上月(万元)';

comment on column pa_view_prosperous_loan_org.grant_credit_outside_compare_year_bal is '网格外授信金额较年初(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_bal is '用信金额(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_compare_month_bal is '用信金额较上月(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_compare_year_bal is '用信金额较年初(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_grid_inside_bal is '网格内用信金额(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_inside_bal_ratio is '网格内用信金额占比';

comment on column pa_view_prosperous_loan_org.use_credit_inside_compare_month_bal is '网格内用信金额较上月(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_inside_compare_year_bal is '网格内用信金额较年初(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_grid_outside_bal is '网格外用信金额(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_outside_bal_ratio is '网格外用信金额占比';

comment on column pa_view_prosperous_loan_org.use_credit_outside_compare_month_bal is '网格外用信金额较上月(万元)';

comment on column pa_view_prosperous_loan_org.use_credit_outside_compare_year_bal is '网格外用信金额较年初(万元)';

comment on column pa_view_prosperous_loan_org.total_bad_bal is '总和-不良余额(万元)';

comment on column pa_view_prosperous_loan_org.total_bad_compare_month_bal is '总和-不良余额较上月(万元)';

comment on column pa_view_prosperous_loan_org.total_bad_compare_year_bal is '总和-不良余额较年初(万元)';

comment on column pa_view_prosperous_loan_org.total_bad_ratio is '总和-不良率';

comment on column pa_view_prosperous_loan_org.total_bad_grid_inside_ratio is '总和-网格内不良率';

comment on column pa_view_prosperous_loan_org.total_bad_grid_outside_ratio is '总和-网格外不良率';

comment on column pa_view_prosperous_loan_org.total_overdue_bal is '总和-逾欠余额(元)';

comment on column pa_view_prosperous_loan_org.total_overdue_compare_month_bal is '总和-逾欠余额较上月(万元)';

comment on column pa_view_prosperous_loan_org.total_overdue_compare_year_bal is '总和-逾欠余额较年初(万元)';

comment on column pa_view_prosperous_loan_org.total_overdue_ratio is '总和-逾欠率';

comment on column pa_view_prosperous_loan_org.total_overdue_grid_inside_ratio is '总和-网格内逾欠率';

comment on column pa_view_prosperous_loan_org.total_overdue_grid_outside_ratio is '总和-网格外逾欠率';

comment on column pa_view_prosperous_loan_org.total_interest_ratio is '总和-收息率';

comment on column pa_view_prosperous_loan_org.total_interest_grid_inside_ratio is '总和-网格内收息率';

comment on column pa_view_prosperous_loan_org.total_interest_grid_outside_ratio is '总和-网格外收息率';

comment on column pa_view_prosperous_loan_org.create_time is '创建时间';

comment on column pa_view_prosperous_loan_org.update_time is '更新时间';

create unique index INDEX33654385
	on pa_view_prosperous_loan_org (prosperous_loan_id);

alter table pa_view_prosperous_loan_org
	add constraint CONS134252775
		primary key (prosperous_loan_id);

create table pa_view_prosperous_loan_org_grid
(
	prosperous_loan_id BIGINT(19),
	loan_ftp VARCHAR(255),
	org_code VARCHAR(255),
	grid_code VARCHAR(255),
	product_code VARCHAR(255),
	customer_type VARCHAR(255),
	grant_credit_num INT(10),
	grant_credit_compare_month_num INT(10),
	grant_credit_compare_year_num INT(10),
	grant_credit_grid_inside_num INT(10),
	grant_credit_inside_num_ratio VARCHAR(255),
	grant_credit_inside_compare_month_num INT(10),
	grant_credit_inside_compare_year_num INT(10),
	grant_credit_grid_outside_num INT(10),
	grant_credit_outside_num_ratio VARCHAR(255),
	grant_credit_outside_compare_month_num INT(10),
	grant_credit_outside_compare_year_num INT(10),
	use_credit_num INT(10),
	use_credit_compare_month_num INT(10),
	use_credit_compare_year_num INT(10),
	use_credit_grid_inside_num INT(10),
	use_credit_inside_num_ratio VARCHAR(255),
	use_credit_inside_compare_month_num INT(10),
	use_credit_inside_compare_year_num INT(10),
	use_credit_grid_outside_num INT(10),
	use_credit_outside_num_ratio VARCHAR(255),
	use_credit_outside_compare_month_num INT(10),
	use_credit_outside_compare_year_num INT(10),
	grant_credit_bal VARCHAR(255),
	grant_credit_compare_month_bal VARCHAR(255),
	grant_credit_compare_year_bal VARCHAR(255),
	grant_credit_grid_inside_bal VARCHAR(255),
	grant_credit_inside_bal_ratio VARCHAR(255),
	grant_credit_inside_compare_month_bal VARCHAR(255),
	grant_credit_inside_compare_year_bal VARCHAR(255),
	grant_credit_grid_outside_bal VARCHAR(255),
	grant_credit_outside_bal_ratio VARCHAR(255),
	grant_credit_outside_compare_month_bal VARCHAR(255),
	grant_credit_outside_compare_year_bal VARCHAR(255),
	use_credit_bal VARCHAR(255),
	use_credit_compare_month_bal VARCHAR(255),
	use_credit_compare_year_bal VARCHAR(255),
	use_credit_grid_inside_bal VARCHAR(255),
	use_credit_inside_bal_ratio VARCHAR(255),
	use_credit_inside_compare_month_bal VARCHAR(255),
	use_credit_inside_compare_year_bal VARCHAR(255),
	use_credit_grid_outside_bal VARCHAR(255),
	use_credit_outside_bal_ratio VARCHAR(255),
	use_credit_outside_compare_month_bal VARCHAR(255),
	use_credit_outside_compare_year_bal VARCHAR(255),
	total_bad_bal VARCHAR(225),
	total_bad_compare_month_bal VARCHAR(225),
	total_bad_compare_year_bal VARCHAR(225),
	total_bad_ratio VARCHAR(225),
	total_bad_grid_inside_ratio VARCHAR(225),
	total_bad_grid_outside_ratio VARCHAR(225),
	total_overdue_bal VARCHAR(225),
	total_overdue_compare_month_bal VARCHAR(225),
	total_overdue_compare_year_bal VARCHAR(225),
	total_overdue_ratio VARCHAR(225),
	total_overdue_grid_inside_ratio VARCHAR(225),
	total_overdue_grid_outside_ratio VARCHAR(225),
	total_interest_ratio VARCHAR(225),
	total_interest_grid_inside_ratio VARCHAR(225),
	total_interest_grid_outside_ratio VARCHAR(225),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29)
);

comment on table pa_view_prosperous_loan_org_grid is '小微视图-凡荣贷模块-网格、机构纬度';

comment on column pa_view_prosperous_loan_org_grid.prosperous_loan_id is '贷款视图id';

comment on column pa_view_prosperous_loan_org_grid.loan_ftp is '贷款ftp(万元，保留两位小数)';

comment on column pa_view_prosperous_loan_org_grid.org_code is '机构编号';

comment on column pa_view_prosperous_loan_org_grid.grid_code is '网格编号';

comment on column pa_view_prosperous_loan_org_grid.product_code is '产品编号';

comment on column pa_view_prosperous_loan_org_grid.customer_type is '客户类型 1-绩效客户 2-授信客户  3-不含乡贷授信客户';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_num is '授信户数';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_compare_month_num is '授信户数较上月';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_compare_year_num is '授信户数较年初';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_grid_inside_num is '网格内授信户数';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_inside_num_ratio is '网格内授信户数占比';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_inside_compare_month_num is '网格内授信户数较上月';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_inside_compare_year_num is '网格内授信户数较年初';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_grid_outside_num is '网格外授信户数 0';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_outside_num_ratio is '网格外授信户数占比 0';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_outside_compare_month_num is '网格外授信户数较上月 0';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_outside_compare_year_num is '网格外授信户数较年初 0';

comment on column pa_view_prosperous_loan_org_grid.use_credit_num is '用信户数';

comment on column pa_view_prosperous_loan_org_grid.use_credit_compare_month_num is '用信户数较上月';

comment on column pa_view_prosperous_loan_org_grid.use_credit_compare_year_num is '用信户数较年初';

comment on column pa_view_prosperous_loan_org_grid.use_credit_grid_inside_num is '网格内用信户数';

comment on column pa_view_prosperous_loan_org_grid.use_credit_inside_num_ratio is '网格内用信户数占比';

comment on column pa_view_prosperous_loan_org_grid.use_credit_inside_compare_month_num is '网格内用信户数较上月';

comment on column pa_view_prosperous_loan_org_grid.use_credit_inside_compare_year_num is '网格内用信户数较年初';

comment on column pa_view_prosperous_loan_org_grid.use_credit_grid_outside_num is '网格外用信户数 0';

comment on column pa_view_prosperous_loan_org_grid.use_credit_outside_num_ratio is '网格外用信户数占比 0';

comment on column pa_view_prosperous_loan_org_grid.use_credit_outside_compare_month_num is '网格外用信户数较上月 0';

comment on column pa_view_prosperous_loan_org_grid.use_credit_outside_compare_year_num is '网格外用信户数较年初 0';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_bal is '授信金额(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_compare_month_bal is '授信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_compare_year_bal is '授信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_grid_inside_bal is '网格内授信金额(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_inside_bal_ratio is '网格内授信金额占比';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_inside_compare_month_bal is '网格内授信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_inside_compare_year_bal is '网格内授信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_grid_outside_bal is '网格外授信金额(万元，整数) 0';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_outside_bal_ratio is '网格外授信金额占比 0';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_outside_compare_month_bal is '网格外授信金额较上月(万元，整数) 0';

comment on column pa_view_prosperous_loan_org_grid.grant_credit_outside_compare_year_bal is '网格外授信金额较年初(万元，整数) 0';

comment on column pa_view_prosperous_loan_org_grid.use_credit_bal is '用信金额(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.use_credit_compare_month_bal is '用信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.use_credit_compare_year_bal is '用信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.use_credit_grid_inside_bal is '网格内用信金额(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.use_credit_inside_bal_ratio is '网格内用信金额占比';

comment on column pa_view_prosperous_loan_org_grid.use_credit_inside_compare_month_bal is '网格内用信金额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.use_credit_inside_compare_year_bal is '网格内用信金额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.use_credit_grid_outside_bal is '网格外用信金额(万元，整数) 0';

comment on column pa_view_prosperous_loan_org_grid.use_credit_outside_bal_ratio is '网格外用信金额占比 0';

comment on column pa_view_prosperous_loan_org_grid.use_credit_outside_compare_month_bal is '网格外用信金额较上月(万元，整数) 0';

comment on column pa_view_prosperous_loan_org_grid.use_credit_outside_compare_year_bal is '网格外用信金额较年初(万元，整数) 0';

comment on column pa_view_prosperous_loan_org_grid.total_bad_bal is '总和-不良余额(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.total_bad_compare_month_bal is '总和-不良余额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.total_bad_compare_year_bal is '总和-不良余额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.total_bad_ratio is '总和-不良率';

comment on column pa_view_prosperous_loan_org_grid.total_bad_grid_inside_ratio is '总和-网格内不良率';

comment on column pa_view_prosperous_loan_org_grid.total_bad_grid_outside_ratio is '总和-网格外不良率 0';

comment on column pa_view_prosperous_loan_org_grid.total_overdue_bal is '总和-逾欠余额(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.total_overdue_compare_month_bal is '总和-逾欠余额较上月(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.total_overdue_compare_year_bal is '总和-逾欠余额较年初(万元，整数)';

comment on column pa_view_prosperous_loan_org_grid.total_overdue_ratio is '总和-逾欠率';

comment on column pa_view_prosperous_loan_org_grid.total_overdue_grid_inside_ratio is '总和-网格内逾欠率';

comment on column pa_view_prosperous_loan_org_grid.total_overdue_grid_outside_ratio is '总和-网格外逾欠率 0';

comment on column pa_view_prosperous_loan_org_grid.total_interest_ratio is '总和-收息率';

comment on column pa_view_prosperous_loan_org_grid.total_interest_grid_inside_ratio is '总和-网格内收息率';

comment on column pa_view_prosperous_loan_org_grid.total_interest_grid_outside_ratio is '总和-网格外收息率 0';

comment on column pa_view_prosperous_loan_org_grid.create_time is '创建时间';

comment on column pa_view_prosperous_loan_org_grid.update_time is '更新时间';

create unique index INDEX33654387
	on pa_view_prosperous_loan_org_grid (prosperous_loan_id);

alter table pa_view_prosperous_loan_org_grid
	add constraint CONS134252776
		primary key (prosperous_loan_id);

create table performance_customer_info
(
	id BIGINT(19),
	customer_nm VARCHAR(8188),
	customer_name VARCHAR(8188),
	customer_phone VARCHAR(8188),
	customer_identity VARCHAR(8188),
	customer_type INT(10),
	customer_level INT(10),
	customer_address VARCHAR(8188),
	work_unit VARCHAR(8188),
	JOB VARCHAR(8188),
	deposit DEC(30,2),
	loan DEC(30,2),
	finance DEC(30,2),
	property DEC(30,2),
	belong_grid_code VARCHAR(8188),
	belong_grid_name VARCHAR(8188),
	grid_manager VARCHAR(8188),
	branch_bank VARCHAR(8188),
	separate_bank VARCHAR(8188),
	credit_manager VARCHAR(8188),
	credit_manager_job_number VARCHAR(8188),
	gender VARCHAR(8188),
	birthday VARCHAR(8188),
	age INT(10),
	marital_status VARCHAR(8188),
	legal_person_identity_num VARCHAR(8188),
	legal_person_name VARCHAR(8188),
	legal_person_phone VARCHAR(8188),
	business_address VARCHAR(8188),
	point VARCHAR(8188),
	create_time TIMESTAMP(36,6),
	separate_bank_id VARCHAR(8188),
	org_num VARCHAR(8188),
	dept_id BIGINT(19),
	addr_type VARCHAR(8188),
	new_legal_person VARCHAR(8188),
	update_time DATE(13)
);

create table performance_customer_info_bak20240601
(
	id BIGINT(19),
	customer_nm VARCHAR(8188),
	customer_name VARCHAR(8188),
	customer_phone VARCHAR(8188),
	customer_identity VARCHAR(8188),
	customer_type INT(10),
	customer_level INT(10),
	customer_address VARCHAR(8188),
	work_unit VARCHAR(8188),
	JOB VARCHAR(8188),
	deposit DEC(30,2),
	loan DEC(30,2),
	finance DEC(30,2),
	property DEC(30,2),
	belong_grid_code VARCHAR(8188),
	belong_grid_name VARCHAR(8188),
	grid_manager VARCHAR(8188),
	branch_bank VARCHAR(8188),
	separate_bank VARCHAR(8188),
	credit_manager VARCHAR(8188),
	credit_manager_job_number VARCHAR(8188),
	gender VARCHAR(8188),
	birthday VARCHAR(8188),
	age INT(10),
	marital_status VARCHAR(8188),
	legal_person_identity_num VARCHAR(8188),
	legal_person_name VARCHAR(8188),
	legal_person_phone VARCHAR(8188),
	business_address VARCHAR(8188),
	point VARCHAR(8188),
	create_time TIMESTAMP(36,6),
	separate_bank_id VARCHAR(8188),
	org_num VARCHAR(8188),
	dept_id BIGINT(19),
	addr_type VARCHAR(8188),
	new_legal_person VARCHAR(8188),
	update_time DATE(13)
);

create table performance_customer_info_new
(
	id BIGINT(19),
	customer_nm VARCHAR(1020),
	customer_name VARCHAR(1020),
	customer_phone VARCHAR(1020),
	customer_identity VARCHAR(1020),
	customer_type INT(10),
	customer_level INT(10),
	customer_address VARCHAR(1020),
	work_unit VARCHAR(1020),
	JOB VARCHAR(1020),
	deposit CLOB,
	loan CLOB,
	finance CLOB,
	property CLOB,
	belong_grid_code VARCHAR(1020),
	belong_grid_name VARCHAR(1020),
	grid_manager VARCHAR(1020),
	branch_bank VARCHAR(1020),
	separate_bank VARCHAR(1020),
	credit_manager VARCHAR(1020),
	credit_manager_job_number VARCHAR(1020),
	gender VARCHAR(1020),
	birthday VARCHAR(1020),
	age INT(10),
	marital_status VARCHAR(1020),
	legal_person_identity_num VARCHAR(1020),
	legal_person_name VARCHAR(1020),
	legal_person_phone VARCHAR(1020),
	business_address VARCHAR(1020),
	point VARCHAR(1020),
	create_time TIMESTAMP(36,6),
	separate_bank_id VARCHAR(32752),
	org_num VARCHAR(400),
	dept_id BIGINT(19),
	addr_type VARCHAR(32752)
);

create table performance_customer_info_notuse
(
	id BIGINT(19),
	customer_nm VARCHAR(1020),
	customer_name VARCHAR(1020),
	customer_phone VARCHAR(1020),
	customer_identity VARCHAR(1020),
	customer_type INT(10),
	customer_level INT(10),
	customer_address VARCHAR(1020),
	work_unit VARCHAR(1020),
	JOB VARCHAR(1020),
	deposit DEC(30,2),
	loan DEC(30,2),
	finance DEC(30,2),
	property DEC(30,2),
	belong_grid_code VARCHAR(1020),
	belong_grid_name VARCHAR(1020),
	grid_manager VARCHAR(1020),
	branch_bank VARCHAR(1020),
	separate_bank VARCHAR(1020),
	credit_manager VARCHAR(1020),
	credit_manager_job_number VARCHAR(1020),
	gender VARCHAR(1020),
	birthday VARCHAR(1020),
	age INT(10),
	marital_status VARCHAR(1020),
	legal_person_identity_num VARCHAR(1020),
	legal_person_name VARCHAR(1020),
	legal_person_phone VARCHAR(1020),
	business_address VARCHAR(1020),
	point VARCHAR(1020),
	create_time TIMESTAMP(36,6),
	separate_bank_id VARCHAR(32752),
	org_num VARCHAR(400),
	dept_id BIGINT(19),
	addr_type VARCHAR(32752)
);

create table performance_customer_info_old
(
	id BIGINT(19),
	customer_nm VARCHAR(1020),
	customer_name VARCHAR(1020),
	customer_phone VARCHAR(1020),
	customer_identity VARCHAR(1020),
	customer_type INT(10),
	customer_level INT(10),
	customer_address VARCHAR(1020),
	work_unit VARCHAR(1020),
	JOB VARCHAR(1020),
	deposit CLOB,
	loan CLOB,
	finance CLOB,
	property CLOB,
	belong_grid_code VARCHAR(1020),
	belong_grid_name VARCHAR(1020),
	grid_manager VARCHAR(1020),
	branch_bank VARCHAR(1020),
	separate_bank VARCHAR(1020),
	credit_manager VARCHAR(1020),
	credit_manager_job_number VARCHAR(1020),
	gender VARCHAR(1020),
	birthday VARCHAR(1020),
	age INT(10),
	marital_status VARCHAR(1020),
	legal_person_identity_num VARCHAR(1020),
	legal_person_name VARCHAR(1020),
	legal_person_phone VARCHAR(1020),
	business_address VARCHAR(1020),
	point VARCHAR(1020),
	create_time TIMESTAMP(36,6),
	separate_bank_id VARCHAR(32752),
	org_num VARCHAR(400),
	dept_id BIGINT(19),
	addr_type VARCHAR(32752)
);

create table permissions
(
	role VARCHAR(50) not null,
	resource VARCHAR(255) not null,
	action VARCHAR(8) not null,
	constraint INDEX33654392
		unique (role, resource, action)
);

create table qrtz_calendars
(
	sched_name VARCHAR(120) not null,
	calendar_name VARCHAR(200) not null,
	calendar BLOB not null
);

comment on table qrtz_calendars is '日历信息表';

comment on column qrtz_calendars.sched_name is '调度名称';

comment on column qrtz_calendars.calendar_name is '日历名称';

comment on column qrtz_calendars.calendar is '存放持久化calendar对象';

create unique index INDEX33654397
	on qrtz_calendars (sched_name, calendar_name);

alter table qrtz_calendars
	add constraint CONS134252780
		primary key (sched_name, calendar_name);

create table qrtz_fired_triggers
(
	sched_name VARCHAR(120) not null,
	entry_id VARCHAR(95) not null,
	trigger_name VARCHAR(200) not null,
	trigger_group VARCHAR(200) not null,
	instance_name VARCHAR(200) not null,
	fired_time BIGINT(19) not null,
	sched_time BIGINT(19) not null,
	priority INT(10) not null,
	state VARCHAR(16) not null,
	job_name VARCHAR(200),
	job_group VARCHAR(200),
	is_nonconcurrent VARCHAR(1),
	requests_recovery VARCHAR(1)
);

comment on table qrtz_fired_triggers is '已触发的触发器表';

comment on column qrtz_fired_triggers.sched_name is '调度名称';

comment on column qrtz_fired_triggers.entry_id is '调度器实例id';

comment on column qrtz_fired_triggers.trigger_name is 'qrtz_triggers表trigger_name的外键';

comment on column qrtz_fired_triggers.trigger_group is 'qrtz_triggers表trigger_group的外键';

comment on column qrtz_fired_triggers.instance_name is '调度器实例名';

comment on column qrtz_fired_triggers.fired_time is '触发的时间';

comment on column qrtz_fired_triggers.sched_time is '定时器制定的时间';

comment on column qrtz_fired_triggers.priority is '优先级';

comment on column qrtz_fired_triggers.state is '状态';

comment on column qrtz_fired_triggers.job_name is '任务名称';

comment on column qrtz_fired_triggers.job_group is '任务组名';

comment on column qrtz_fired_triggers.is_nonconcurrent is '是否并发';

comment on column qrtz_fired_triggers.requests_recovery is '是否接受恢复执行';

create unique index INDEX33654402
	on qrtz_fired_triggers (sched_name, entry_id);

alter table qrtz_fired_triggers
	add constraint CONS134252783
		primary key (sched_name, entry_id);

create table qrtz_job_details
(
	sched_name VARCHAR(120) not null,
	job_name VARCHAR(200) not null,
	job_group VARCHAR(200) not null,
	description VARCHAR(250),
	job_class_name VARCHAR(250) not null,
	is_durable VARCHAR(1) not null,
	is_nonconcurrent VARCHAR(1) not null,
	is_update_data VARCHAR(1) not null,
	requests_recovery VARCHAR(1) not null,
	job_data BLOB
);

comment on table qrtz_job_details is '任务详细信息表';

comment on column qrtz_job_details.sched_name is '调度名称';

comment on column qrtz_job_details.job_name is '任务名称';

comment on column qrtz_job_details.job_group is '任务组名';

comment on column qrtz_job_details.description is '相关介绍';

comment on column qrtz_job_details.job_class_name is '执行任务类名称';

comment on column qrtz_job_details.is_durable is '是否持久化';

comment on column qrtz_job_details.is_nonconcurrent is '是否并发';

comment on column qrtz_job_details.is_update_data is '是否更新数据';

comment on column qrtz_job_details.requests_recovery is '是否接受恢复执行';

comment on column qrtz_job_details.job_data is '存放持久化job对象';

create unique index INDEX33654214
	on qrtz_job_details (sched_name, job_name, job_group);

alter table qrtz_job_details
	add constraint CONS134252690
		primary key (sched_name, job_name, job_group);

create table qrtz_locks
(
	sched_name VARCHAR(120) not null,
	lock_name VARCHAR(40) not null
);

comment on table qrtz_locks is '存储的悲观锁信息表';

comment on column qrtz_locks.sched_name is '调度名称';

comment on column qrtz_locks.lock_name is '悲观锁名称';

create unique index INDEX33654216
	on qrtz_locks (sched_name, lock_name);

alter table qrtz_locks
	add constraint CONS134252691
		primary key (sched_name, lock_name);

create table qrtz_paused_trigger_grps
(
	sched_name VARCHAR(120) not null,
	trigger_group VARCHAR(200) not null
);

comment on table qrtz_paused_trigger_grps is '暂停的触发器表';

comment on column qrtz_paused_trigger_grps.sched_name is '调度名称';

comment on column qrtz_paused_trigger_grps.trigger_group is 'qrtz_triggers表trigger_group的外键';

create unique index INDEX33654404
	on qrtz_paused_trigger_grps (sched_name, trigger_group);

alter table qrtz_paused_trigger_grps
	add constraint CONS134252784
		primary key (sched_name, trigger_group);

create table qrtz_scheduler_state
(
	sched_name VARCHAR(120) not null,
	instance_name VARCHAR(200) not null,
	last_checkin_time BIGINT(19) not null,
	checkin_interval BIGINT(19) not null
);

comment on table qrtz_scheduler_state is '调度器状态表';

comment on column qrtz_scheduler_state.sched_name is '调度名称';

comment on column qrtz_scheduler_state.instance_name is '实例名称';

comment on column qrtz_scheduler_state.last_checkin_time is '上次检查时间';

comment on column qrtz_scheduler_state.checkin_interval is '检查间隔时间';

create unique index INDEX33654414
	on qrtz_scheduler_state (sched_name, instance_name);

alter table qrtz_scheduler_state
	add constraint CONS134252790
		primary key (sched_name, instance_name);

create table qrtz_triggers
(
	sched_name VARCHAR(120) not null,
	trigger_name VARCHAR(200) not null,
	trigger_group VARCHAR(200) not null,
	job_name VARCHAR(200) not null,
	job_group VARCHAR(200) not null,
	description VARCHAR(250),
	next_fire_time BIGINT(19),
	prev_fire_time BIGINT(19),
	priority INT(10),
	trigger_state VARCHAR(16) not null,
	trigger_type VARCHAR(8) not null,
	start_time BIGINT(19) not null,
	end_time BIGINT(19),
	calendar_name VARCHAR(200),
	misfire_instr SMALLINT(5),
	job_data BLOB,
	constraint INDEX33654219
		unique (sched_name, job_name, job_group),
	constraint CONS134252693
		foreign key (sched_name, job_name, job_group) references qrtz_job_details
			deferrable
);

comment on table qrtz_triggers is '触发器详细信息表';

comment on column qrtz_triggers.sched_name is '调度名称';

comment on column qrtz_triggers.trigger_name is '触发器的名字';

comment on column qrtz_triggers.trigger_group is '触发器所属组的名字';

comment on column qrtz_triggers.job_name is 'qrtz_job_details表job_name的外键';

comment on column qrtz_triggers.job_group is 'qrtz_job_details表job_group的外键';

comment on column qrtz_triggers.description is '相关介绍';

comment on column qrtz_triggers.next_fire_time is '上一次触发时间（毫秒）';

comment on column qrtz_triggers.prev_fire_time is '下一次触发时间（默认为-1表示不触发）';

comment on column qrtz_triggers.priority is '优先级';

comment on column qrtz_triggers.trigger_state is '触发器状态';

comment on column qrtz_triggers.trigger_type is '触发器的类型';

comment on column qrtz_triggers.start_time is '开始时间';

comment on column qrtz_triggers.end_time is '结束时间';

comment on column qrtz_triggers.calendar_name is '日程表名称';

comment on column qrtz_triggers.misfire_instr is '补偿执行的策略';

comment on column qrtz_triggers.job_data is '存放持久化job对象';

create unique index INDEX33654218
	on qrtz_triggers (sched_name, trigger_name, trigger_group);

create unique index sched_name
	on qrtz_triggers (sched_name, job_name, job_group);

alter table qrtz_triggers
	add constraint CONS134252692
		primary key (sched_name, trigger_name, trigger_group);

create table qrtz_blob_triggers
(
	sched_name VARCHAR(120) not null,
	trigger_name VARCHAR(200) not null,
	trigger_group VARCHAR(200) not null,
	blob_data BLOB,
	constraint CONS134252779
		foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers
			deferrable
);

comment on table qrtz_blob_triggers is 'Blob类型的触发器表';

comment on column qrtz_blob_triggers.sched_name is '调度名称';

comment on column qrtz_blob_triggers.trigger_name is 'qrtz_triggers表trigger_name的外键';

comment on column qrtz_blob_triggers.trigger_group is 'qrtz_triggers表trigger_group的外键';

comment on column qrtz_blob_triggers.blob_data is '存放持久化Trigger对象';

create unique index INDEX33654394
	on qrtz_blob_triggers (sched_name, trigger_name, trigger_group);

create unique index INDEX33654395
	on qrtz_blob_triggers (sched_name, trigger_name, trigger_group);

alter table qrtz_blob_triggers
	add constraint CONS134252778
		primary key (sched_name, trigger_name, trigger_group);

create table qrtz_cron_triggers
(
	sched_name VARCHAR(120) not null,
	trigger_name VARCHAR(200) not null,
	trigger_group VARCHAR(200) not null,
	cron_expression VARCHAR(200) not null,
	time_zone_id VARCHAR(80),
	constraint CONS134252782
		foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers
			deferrable
);

comment on table qrtz_cron_triggers is 'Cron类型的触发器表';

comment on column qrtz_cron_triggers.sched_name is '调度名称';

comment on column qrtz_cron_triggers.trigger_name is 'qrtz_triggers表trigger_name的外键';

comment on column qrtz_cron_triggers.trigger_group is 'qrtz_triggers表trigger_group的外键';

comment on column qrtz_cron_triggers.cron_expression is 'cron表达式';

comment on column qrtz_cron_triggers.time_zone_id is '时区';

create unique index INDEX33654399
	on qrtz_cron_triggers (sched_name, trigger_name, trigger_group);

create unique index INDEX33654400
	on qrtz_cron_triggers (sched_name, trigger_name, trigger_group);

alter table qrtz_cron_triggers
	add constraint CONS134252781
		primary key (sched_name, trigger_name, trigger_group);

create table qrtz_simple_triggers
(
	sched_name VARCHAR(120) not null,
	trigger_name VARCHAR(200) not null,
	trigger_group VARCHAR(200) not null,
	repeat_count BIGINT(19) not null,
	repeat_interval BIGINT(19) not null,
	times_triggered BIGINT(19) not null,
	constraint CONS134252786
		foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers
			deferrable
);

comment on table qrtz_simple_triggers is '简单触发器的信息表';

comment on column qrtz_simple_triggers.sched_name is '调度名称';

comment on column qrtz_simple_triggers.trigger_name is 'qrtz_triggers表trigger_name的外键';

comment on column qrtz_simple_triggers.trigger_group is 'qrtz_triggers表trigger_group的外键';

comment on column qrtz_simple_triggers.repeat_count is '重复的次数统计';

comment on column qrtz_simple_triggers.repeat_interval is '重复的间隔时间';

comment on column qrtz_simple_triggers.times_triggered is '已经触发的次数';

create unique index INDEX33654406
	on qrtz_simple_triggers (sched_name, trigger_name, trigger_group);

create unique index INDEX33654407
	on qrtz_simple_triggers (sched_name, trigger_name, trigger_group);

alter table qrtz_simple_triggers
	add constraint CONS134252785
		primary key (sched_name, trigger_name, trigger_group);

create table qrtz_simprop_triggers
(
	sched_name VARCHAR(120) not null,
	trigger_name VARCHAR(200) not null,
	trigger_group VARCHAR(200) not null,
	str_prop_1 VARCHAR(512),
	str_prop_2 VARCHAR(512),
	str_prop_3 VARCHAR(512),
	int_prop_1 INT(10),
	int_prop_2 INT(10),
	long_prop_1 BIGINT(19),
	long_prop_2 BIGINT(19),
	dec_prop_1 DEC(13,4),
	dec_prop_2 DEC(13,4),
	bool_prop_1 VARCHAR(1),
	bool_prop_2 VARCHAR(1),
	constraint CONS134252788
		foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers
			deferrable
);

comment on table qrtz_simprop_triggers is '同步机制的行锁表';

comment on column qrtz_simprop_triggers.sched_name is '调度名称';

comment on column qrtz_simprop_triggers.trigger_name is 'qrtz_triggers表trigger_name的外键';

comment on column qrtz_simprop_triggers.trigger_group is 'qrtz_triggers表trigger_group的外键';

comment on column qrtz_simprop_triggers.str_prop_1 is 'String类型的trigger的第一个参数';

comment on column qrtz_simprop_triggers.str_prop_2 is 'String类型的trigger的第二个参数';

comment on column qrtz_simprop_triggers.str_prop_3 is 'String类型的trigger的第三个参数';

comment on column qrtz_simprop_triggers.int_prop_1 is 'int类型的trigger的第一个参数';

comment on column qrtz_simprop_triggers.int_prop_2 is 'int类型的trigger的第二个参数';

comment on column qrtz_simprop_triggers.long_prop_1 is 'long类型的trigger的第一个参数';

comment on column qrtz_simprop_triggers.long_prop_2 is 'long类型的trigger的第二个参数';

comment on column qrtz_simprop_triggers.dec_prop_1 is 'decimal类型的trigger的第一个参数';

comment on column qrtz_simprop_triggers.dec_prop_2 is 'decimal类型的trigger的第二个参数';

comment on column qrtz_simprop_triggers.bool_prop_1 is 'Boolean类型的trigger的第一个参数';

comment on column qrtz_simprop_triggers.bool_prop_2 is 'Boolean类型的trigger的第二个参数';

create unique index INDEX33654409
	on qrtz_simprop_triggers (sched_name, trigger_name, trigger_group);

create unique index INDEX33654410
	on qrtz_simprop_triggers (sched_name, trigger_name, trigger_group);

alter table qrtz_simprop_triggers
	add constraint CONS134252787
		primary key (sched_name, trigger_name, trigger_group);

create table roles
(
	username VARCHAR(50) not null,
	role VARCHAR(50) not null,
	constraint INDEX33654412
		unique (username, role)
);

create table sys_batch_task_record
(
	id BIGINT(19),
	task_name VARCHAR(255),
	status TINYINT(3),
	auto_execute TINYINT(3),
	params VARCHAR(255),
	ip VARCHAR(16),
	LOG CLOB,
	begin_time TIMESTAMP(36,6),
	end_time TIMESTAMP(36,6),
	execute_time INT(10)
);

comment on table sys_batch_task_record is '系统跑批记录表';

comment on column sys_batch_task_record.id is 'id';

comment on column sys_batch_task_record.task_name is '任务名称';

comment on column sys_batch_task_record.status is '跑批状态';

comment on column sys_batch_task_record.auto_execute is '是否自动跑批';

comment on column sys_batch_task_record.params is '跑批参数';

comment on column sys_batch_task_record.ip is '跑批服务IP';

comment on column sys_batch_task_record.LOG is '跑批日志';

comment on column sys_batch_task_record.begin_time is '跑批开始时间';

comment on column sys_batch_task_record.end_time is '跑批结束时间';

comment on column sys_batch_task_record.execute_time is '跑批持续时间';

create unique index INDEX33660223
	on sys_batch_task_record (id);

alter table sys_batch_task_record
	add constraint CONS134253359
		primary key (id);

create table sys_config
(
	config_id INT(10),
	config_name VARCHAR(100) default '',
	config_key VARCHAR(100) default '',
	config_value VARCHAR(500) default '',
	config_type CHAR(1) default 'N',
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29),
	remark VARCHAR(500)
);

comment on table sys_config is '参数配置表';

comment on column sys_config.config_id is '参数主键';

comment on column sys_config.config_name is '参数名称';

comment on column sys_config.config_key is '参数键名';

comment on column sys_config.config_value is '参数键值';

comment on column sys_config.config_type is '系统内置（Y是 N否）';

comment on column sys_config.create_by is '创建者';

comment on column sys_config.create_time is '创建时间';

comment on column sys_config.update_by is '更新者';

comment on column sys_config.update_time is '更新时间';

comment on column sys_config.remark is '备注';

create unique index INDEX33654416
	on sys_config (config_id);

alter table sys_config
	add constraint CONS134252791
		primary key (config_id);

create table sys_deploy_form
(
	id BIGINT(19),
	form_id BIGINT(19),
	deploy_id VARCHAR(50)
);

comment on table sys_deploy_form is '流程实例关联表单';

comment on column sys_deploy_form.id is '主键';

comment on column sys_deploy_form.form_id is '表单主键';

comment on column sys_deploy_form.deploy_id is '流程实例主键';

create unique index INDEX33654418
	on sys_deploy_form (id);

alter table sys_deploy_form
	add constraint CONS134252792
		primary key (id);

create table sys_dept
(
	dept_id BIGINT(19),
	parent_id BIGINT(19) default 0,
	ancestors VARCHAR(50) default '',
	dept_name VARCHAR(30) default '',
	order_num INT(10) default 0,
	org_num VARCHAR(20),
	leader VARCHAR(20),
	phone VARCHAR(11),
	email VARCHAR(50),
	status CHAR(1) default '0',
	del_flag CHAR(1) default '0',
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(36,6),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(36,6)
);

create unique index INDEX33654420
	on sys_dept (dept_id);

alter table sys_dept
	add constraint CONS134252793
		primary key (dept_id);

create table sys_dict_data
(
	dict_code BIGINT(19),
	dict_sort INT(10) default 0,
	dict_label VARCHAR(100) default '',
	dict_value VARCHAR(100) default '',
	dict_type VARCHAR(100) default '',
	css_class VARCHAR(100),
	list_class VARCHAR(100),
	is_default CHAR(1) default 'N',
	status CHAR(1) default '0',
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29),
	remark VARCHAR(500),
	constraint idx_sys_dict_data_dict_type_value
		unique (dict_type, dict_value)
);

comment on table sys_dict_data is '字典数据表';

comment on column sys_dict_data.dict_code is '字典编码';

comment on column sys_dict_data.dict_sort is '字典排序';

comment on column sys_dict_data.dict_label is '字典标签';

comment on column sys_dict_data.dict_value is '字典键值';

comment on column sys_dict_data.dict_type is '字典类型';

comment on column sys_dict_data.css_class is '样式属性（其他样式扩展）';

comment on column sys_dict_data.list_class is '表格回显样式';

comment on column sys_dict_data.is_default is '是否默认（Y是 N否）';

comment on column sys_dict_data.status is '状态（0正常 1停用）';

comment on column sys_dict_data.create_by is '创建者';

comment on column sys_dict_data.create_time is '创建时间';

comment on column sys_dict_data.update_by is '更新者';

comment on column sys_dict_data.update_time is '更新时间';

comment on column sys_dict_data.remark is '备注';

create unique index INDEX33654249
	on sys_dict_data (dict_code);

alter table sys_dict_data
	add constraint CONS134252701
		primary key (dict_code);

create table sys_dict_type
(
	dict_id BIGINT(19),
	dict_name VARCHAR(100) default '',
	dict_type VARCHAR(100) default ''
		constraint INDEX33654423
			unique,
	status CHAR(1) default '0',
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29),
	remark VARCHAR(500)
);

comment on table sys_dict_type is '字典类型表';

comment on column sys_dict_type.dict_id is '字典主键';

comment on column sys_dict_type.dict_name is '字典名称';

comment on column sys_dict_type.dict_type is '字典类型';

comment on column sys_dict_type.status is '状态（0正常 1停用）';

comment on column sys_dict_type.create_by is '创建者';

comment on column sys_dict_type.create_time is '创建时间';

comment on column sys_dict_type.update_by is '更新者';

comment on column sys_dict_type.update_time is '更新时间';

comment on column sys_dict_type.remark is '备注';

create unique index INDEX33654422
	on sys_dict_type (dict_id);

alter table sys_dict_type
	add constraint CONS134252794
		primary key (dict_id);

create table sys_expression
(
	id BIGINT(19),
	name VARCHAR(50),
	expression VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	create_by BIGINT(19),
	update_by BIGINT(19),
	status TINYINT(3) default 0,
	remark VARCHAR(255)
);

comment on table sys_expression is '流程表达式';

comment on column sys_expression.id is '表单主键';

comment on column sys_expression.name is '表达式名称';

comment on column sys_expression.expression is '表达式内容';

comment on column sys_expression.create_time is '创建时间';

comment on column sys_expression.update_time is '更新时间';

comment on column sys_expression.create_by is '创建人员';

comment on column sys_expression.update_by is '更新人员';

comment on column sys_expression.status is '状态';

comment on column sys_expression.remark is '备注';

create unique index INDEX33654425
	on sys_expression (id);

alter table sys_expression
	add constraint CONS134252796
		primary key (id);

create table sys_form
(
	form_id BIGINT(19),
	form_name VARCHAR(50),
	form_content CLOB,
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	create_by BIGINT(19),
	update_by BIGINT(19),
	remark VARCHAR(255)
);

comment on table sys_form is '流程表单';

comment on column sys_form.form_id is '表单主键';

comment on column sys_form.form_name is '表单名称';

comment on column sys_form.form_content is '表单内容';

comment on column sys_form.create_time is '创建时间';

comment on column sys_form.update_time is '更新时间';

comment on column sys_form.create_by is '创建人员';

comment on column sys_form.update_by is '更新人员';

comment on column sys_form.remark is '备注';

create unique index INDEX33654427
	on sys_form (form_id);

alter table sys_form
	add constraint CONS134252797
		primary key (form_id);

create table sys_job
(
	job_id BIGINT(19),
	job_name VARCHAR(64) default '' not null,
	job_group VARCHAR(64) default 'DEFAULT' not null,
	invoke_target VARCHAR(500) not null,
	cron_expression VARCHAR(255) default '',
	misfire_policy VARCHAR(20) default '3',
	concurrent CHAR(1) default '1',
	status CHAR(1) default '0',
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29),
	remark VARCHAR(500) default ''
);

comment on table sys_job is '定时任务调度表';

comment on column sys_job.job_id is '任务ID';

comment on column sys_job.job_name is '任务名称';

comment on column sys_job.job_group is '任务组名';

comment on column sys_job.invoke_target is '调用目标字符串';

comment on column sys_job.cron_expression is 'cron执行表达式';

comment on column sys_job.misfire_policy is '计划执行错误策略（1立即执行 2执行一次 3放弃执行）';

comment on column sys_job.concurrent is '是否并发执行（0允许 1禁止）';

comment on column sys_job.status is '状态（0正常 1暂停）';

comment on column sys_job.create_by is '创建者';

comment on column sys_job.create_time is '创建时间';

comment on column sys_job.update_by is '更新者';

comment on column sys_job.update_time is '更新时间';

comment on column sys_job.remark is '备注信息';

create unique index INDEX33654429
	on sys_job (job_id, job_name, job_group);

alter table sys_job
	add constraint CONS134252798
		primary key (job_id, job_name, job_group);

create table sys_job_log
(
	job_log_id BIGINT(19),
	job_name VARCHAR(64) not null,
	job_group VARCHAR(64) not null,
	invoke_target VARCHAR(500) not null,
	job_message VARCHAR(500),
	status CHAR(1) default '0',
	exception_info VARCHAR(2000) default '',
	create_time TIMESTAMP(29)
);

comment on table sys_job_log is '定时任务调度日志表';

comment on column sys_job_log.job_log_id is '任务日志ID';

comment on column sys_job_log.job_name is '任务名称';

comment on column sys_job_log.job_group is '任务组名';

comment on column sys_job_log.invoke_target is '调用目标字符串';

comment on column sys_job_log.job_message is '日志信息';

comment on column sys_job_log.status is '执行状态（0正常 1失败）';

comment on column sys_job_log.exception_info is '异常信息';

comment on column sys_job_log.create_time is '创建时间';

create unique index INDEX33654431
	on sys_job_log (job_log_id);

alter table sys_job_log
	add constraint CONS134252799
		primary key (job_log_id);

create table sys_listener
(
	id BIGINT(19),
	name VARCHAR(128),
	type CHAR(2),
	event_type VARCHAR(32),
	value_type CHAR(2),
	value VARCHAR(255),
	create_time TIMESTAMP(29),
	update_time TIMESTAMP(29),
	create_by BIGINT(19),
	update_by BIGINT(19),
	status TINYINT(3) default 0,
	remark VARCHAR(255)
);

comment on table sys_listener is '流程监听';

comment on column sys_listener.id is '表单主键';

comment on column sys_listener.name is '名称';

comment on column sys_listener.type is '监听类型';

comment on column sys_listener.event_type is '事件类型';

comment on column sys_listener.value_type is '值类型';

comment on column sys_listener.value is '执行内容';

comment on column sys_listener.create_time is '创建时间';

comment on column sys_listener.update_time is '更新时间';

comment on column sys_listener.create_by is '创建人员';

comment on column sys_listener.update_by is '更新人员';

comment on column sys_listener.status is '状态';

comment on column sys_listener.remark is '备注';

create unique index INDEX33654433
	on sys_listener (id);

alter table sys_listener
	add constraint CONS134252800
		primary key (id);

create table sys_logininfor
(
	info_id BIGINT(19),
	user_name VARCHAR(50) default '',
	ipaddr VARCHAR(128) default '',
	login_location VARCHAR(255) default '',
	browser VARCHAR(50) default '',
	os VARCHAR(50) default '',
	status CHAR(1) default '0',
	msg VARCHAR(255) default '',
	login_time TIMESTAMP(29)
);

comment on table sys_logininfor is '系统访问记录';

comment on column sys_logininfor.info_id is '访问ID';

comment on column sys_logininfor.user_name is '用户账号';

comment on column sys_logininfor.ipaddr is '登录IP地址';

comment on column sys_logininfor.login_location is '登录地点';

comment on column sys_logininfor.browser is '浏览器类型';

comment on column sys_logininfor.os is '操作系统';

comment on column sys_logininfor.status is '登录状态（0成功 1失败）';

comment on column sys_logininfor.msg is '提示消息';

comment on column sys_logininfor.login_time is '访问时间';

create unique index INDEX33654435
	on sys_logininfor (info_id);

alter table sys_logininfor
	add constraint CONS134252801
		primary key (info_id);

create table sys_menu
(
	menu_id BIGINT(19),
	menu_name VARCHAR(50),
	parent_id BIGINT(19),
	order_num INT(10),
	path VARCHAR(200),
	component VARCHAR(255),
	query VARCHAR(255),
	is_frame INT(10),
	is_cache INT(10),
	menu_type CHAR(1),
	VISIBLE CHAR(1),
	status CHAR(1),
	perms VARCHAR(100),
	icon VARCHAR(100),
	create_by VARCHAR(64),
	create_time TIMESTAMP(36,6),
	update_by VARCHAR(64),
	update_time TIMESTAMP(36,6),
	remark VARCHAR(500)
);

create unique index INDEX33654437
	on sys_menu (menu_id);

alter table sys_menu
	add constraint CONS134252802
		primary key (menu_id);

create table sys_network
(
	network_id BIGINT(19),
	parent_id BIGINT(19) default 0,
	ancestors VARCHAR(50) default '',
	network_name VARCHAR(30) default '',
	network_code VARCHAR(255),
	order_num INT(10) default 0,
	leader VARCHAR(20),
	type INT(10),
	similar_rank INT(10),
	phone VARCHAR(11),
	email VARCHAR(50),
	address VARCHAR(200),
	lng VARCHAR(100),
	lat VARCHAR(100),
	status CHAR(1) default '0',
	del_flag CHAR(1) default '0',
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29),
	area_manage_update_time TIMESTAMP(29)
);

comment on table sys_network is '网点表';

comment on column sys_network.network_id is '机构id';

comment on column sys_network.parent_id is '父部门id';

comment on column sys_network.ancestors is '祖级列表';

comment on column sys_network.network_name is '网点名称';

comment on column sys_network.network_code is '网点编号';

comment on column sys_network.order_num is '显示顺序';

comment on column sys_network.leader is '负责人';

comment on column sys_network.type is '类型营业中心-1，商圈性-2，社区型-3，乡镇型-4，市场型-5，园区型-6';

comment on column sys_network.similar_rank is '同类排名';

comment on column sys_network.phone is '联系电话';

comment on column sys_network.email is '邮箱';

comment on column sys_network.address is '地址';

comment on column sys_network.lng is '经度';

comment on column sys_network.lat is '纬度';

comment on column sys_network.status is '网点状态（0正常 1停用）';

comment on column sys_network.del_flag is '删除标志（0代表存在 2代表删除）';

comment on column sys_network.create_by is '创建者';

comment on column sys_network.create_time is '创建时间';

comment on column sys_network.update_by is '更新者';

comment on column sys_network.update_time is '更新时间';

comment on column sys_network.area_manage_update_time is '所辖区域更新时间';

create unique index INDEX33654558
	on sys_network (network_id);

alter table sys_network
	add constraint CONS134252856
		primary key (network_id);

create table sys_notice
(
	notice_id INT(10),
	notice_title VARCHAR(50) not null,
	notice_type CHAR(1) not null,
	notice_content BLOB,
	status CHAR(1) default '0',
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29),
	remark VARCHAR(255)
);

comment on table sys_notice is '通知公告表';

comment on column sys_notice.notice_id is '公告ID';

comment on column sys_notice.notice_title is '公告标题';

comment on column sys_notice.notice_type is '公告类型（1通知 2公告）';

comment on column sys_notice.notice_content is '公告内容';

comment on column sys_notice.status is '公告状态（0正常 1关闭）';

comment on column sys_notice.create_by is '创建者';

comment on column sys_notice.create_time is '创建时间';

comment on column sys_notice.update_by is '更新者';

comment on column sys_notice.update_time is '更新时间';

comment on column sys_notice.remark is '备注';

create unique index INDEX33654439
	on sys_notice (notice_id);

alter table sys_notice
	add constraint CONS134252803
		primary key (notice_id);

create table sys_oper_log
(
	oper_id BIGINT(19),
	title VARCHAR(50) default '',
	business_type INT(10) default 0,
	method VARCHAR(100) default '',
	request_method VARCHAR(10) default '',
	operator_type INT(10) default 0,
	oper_name VARCHAR(50) default '',
	dept_name VARCHAR(50) default '',
	oper_url VARCHAR(255) default '',
	oper_ip VARCHAR(128) default '',
	oper_location VARCHAR(255) default '',
	oper_param VARCHAR(2000) default '',
	json_result VARCHAR(2000) default '',
	status INT(10) default 0,
	error_msg VARCHAR(2000) default '',
	oper_time TIMESTAMP(29),
	cost_time BIGINT(19) default 0
);

comment on table sys_oper_log is '操作日志记录';

comment on column sys_oper_log.oper_id is '日志主键';

comment on column sys_oper_log.title is '模块标题';

comment on column sys_oper_log.business_type is '业务类型（0其它 1新增 2修改 3删除）';

comment on column sys_oper_log.method is '方法名称';

comment on column sys_oper_log.request_method is '请求方式';

comment on column sys_oper_log.operator_type is '操作类别（0其它 1后台用户 2手机端用户）';

comment on column sys_oper_log.oper_name is '操作人员';

comment on column sys_oper_log.dept_name is '部门名称';

comment on column sys_oper_log.oper_url is '请求URL';

comment on column sys_oper_log.oper_ip is '主机地址';

comment on column sys_oper_log.oper_location is '操作地点';

comment on column sys_oper_log.oper_param is '请求参数';

comment on column sys_oper_log.json_result is '返回参数';

comment on column sys_oper_log.status is '操作状态（0正常 1异常）';

comment on column sys_oper_log.error_msg is '错误消息';

comment on column sys_oper_log.oper_time is '操作时间';

comment on column sys_oper_log.cost_time is '消耗时间';

create unique index INDEX33654441
	on sys_oper_log (oper_id);

alter table sys_oper_log
	add constraint CONS134252804
		primary key (oper_id);

create table sys_post
(
	post_id BIGINT(19),
	post_code VARCHAR(64) not null,
	post_name VARCHAR(50) not null,
	post_sort INT(10) not null,
	status CHAR(1) not null,
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(29),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(29),
	remark VARCHAR(500)
);

comment on table sys_post is '岗位信息表';

comment on column sys_post.post_id is '岗位ID';

comment on column sys_post.post_code is '岗位编码';

comment on column sys_post.post_name is '岗位名称';

comment on column sys_post.post_sort is '显示顺序';

comment on column sys_post.status is '状态（0正常 1停用）';

comment on column sys_post.create_by is '创建者';

comment on column sys_post.create_time is '创建时间';

comment on column sys_post.update_by is '更新者';

comment on column sys_post.update_time is '更新时间';

comment on column sys_post.remark is '备注';

create unique index INDEX33654443
	on sys_post (post_id);

alter table sys_post
	add constraint CONS134252805
		primary key (post_id);

create table sys_role
(
	role_id BIGINT(19),
	role_name VARCHAR(255),
	role_key VARCHAR(255),
	role_sort INT(10),
	data_scope VARCHAR(2),
	menu_check_strictly TINYINT(3),
	dept_check_strictly TINYINT(3),
	status CHAR(1),
	del_flag CHAR(1) default '0',
	create_by VARCHAR(255),
	create_time TIMESTAMP(36,6),
	update_by VARCHAR(255),
	update_time TIMESTAMP(36,6),
	remark VARCHAR(255)
);

create unique index INDEX33654508
	on sys_role (role_id);

alter table sys_role
	add constraint CONS134252840
		primary key (role_id);

create table sys_role_dept
(
	role_id BIGINT(19) not null,
	dept_id BIGINT(19) not null
);

comment on table sys_role_dept is '角色和部门关联表';

comment on column sys_role_dept.role_id is '角色ID';

comment on column sys_role_dept.dept_id is '部门ID';

create unique index INDEX33654445
	on sys_role_dept (role_id, dept_id);

alter table sys_role_dept
	add constraint CONS134252806
		primary key (role_id, dept_id);

create table sys_role_menu
(
	role_id BIGINT(19),
	menu_id BIGINT(19)
);

create table sys_task_form
(
	id BIGINT(19),
	form_id BIGINT(19),
	task_id VARCHAR(50)
);

comment on table sys_task_form is '流程任务关联表单';

comment on column sys_task_form.id is '主键';

comment on column sys_task_form.form_id is '表单主键';

comment on column sys_task_form.task_id is '所属任务';

create unique index INDEX33654448
	on sys_task_form (id);

alter table sys_task_form
	add constraint CONS134252807
		primary key (id);

create table sys_user
(
	user_id BIGINT(19),
	dept_id BIGINT(19),
	user_name VARCHAR(30) not null,
	manager_num VARCHAR(200),
	nick_name VARCHAR(30) not null,
	user_type VARCHAR(2) default '00',
	email VARCHAR(50) default '',
	phonenumber VARCHAR(11) default '',
	sex CHAR(1) default '0',
	avatar VARCHAR(100) default '',
	password VARCHAR(100) default '',
	status CHAR(1) default '0',
	del_flag CHAR(1) default '0',
	login_ip VARCHAR(128) default '',
	login_date TIMESTAMP(36,6),
	create_by VARCHAR(64) default '',
	create_time TIMESTAMP(36,6),
	update_by VARCHAR(64) default '',
	update_time TIMESTAMP(36,6),
	remark VARCHAR(500)
);

create unique index INDEX33654450
	on sys_user (user_id);

alter table sys_user
	add constraint CONS134252808
		primary key (user_id);

create table sys_user_post
(
	user_id BIGINT(19) not null,
	post_id BIGINT(19) not null
);

comment on table sys_user_post is '用户与岗位关联表';

comment on column sys_user_post.user_id is '用户ID';

comment on column sys_user_post.post_id is '岗位ID';

create unique index INDEX33654452
	on sys_user_post (user_id, post_id);

alter table sys_user_post
	add constraint CONS134252809
		primary key (user_id, post_id);

create table sys_user_role
(
	user_id BIGINT(19) not null,
	role_id BIGINT(19) not null
);

comment on table sys_user_role is '用户和角色关联表';

comment on column sys_user_role.user_id is '用户ID';

comment on column sys_user_role.role_id is '角色ID';

create unique index INDEX33654454
	on sys_user_role (user_id, role_id);

alter table sys_user_role
	add constraint CONS134252810
		primary key (user_id, role_id);

create table task_base_detail
(
	id BIGINT(19),
	task_id BIGINT(19),
	code VARCHAR(100)
		constraint idx_code
			unique,
	name VARCHAR(100),
	manager_id VARCHAR(100)
		constraint idx_manager_id
			unique,
	manager_name VARCHAR(100),
	network_code VARCHAR(100),
	network_name VARCHAR(100),
	status INT(10),
	complete_percent VARCHAR(100),
	is_deleted INT(10) default 0,
	create_by VARCHAR(100),
	update_by VARCHAR(100),
	create_date TIMESTAMP(29),
	update_date TIMESTAMP(29),
	positions BLOB,
	constraint idx_task_id
		unique (task_id, code)
);

comment on table task_base_detail is '任务下派明细表';

comment on column task_base_detail.id is 'id';

comment on column task_base_detail.task_id is '任务id';

comment on column task_base_detail.code is '客户内码';

comment on column task_base_detail.name is '客户姓名';

comment on column task_base_detail.manager_id is '客户经理号';

comment on column task_base_detail.manager_name is '客户经理名称';

comment on column task_base_detail.network_code is '网点code';

comment on column task_base_detail.network_name is '网点名称';

comment on column task_base_detail.status is '状态 0-未完成 1-已完成 2-结束';

comment on column task_base_detail.complete_percent is '完整度';

comment on column task_base_detail.is_deleted is '是否删除  0-未删除 1-已删除';

comment on column task_base_detail.create_by is '创建人';

comment on column task_base_detail.update_by is '更新人';

comment on column task_base_detail.create_date is '创建时间';

comment on column task_base_detail.update_date is '更新时间';

comment on column task_base_detail.positions is '点集';

create unique index INDEX33654225
	on task_base_detail (id);

alter table task_base_detail
	add constraint CONS134252695
		primary key (id);

create table task_base_info
(
	id BIGINT(19),
	task_name VARCHAR(100),
	task_type BIGINT(19),
	task_type_name VARCHAR(100),
	distribution_rule_parent BIGINT(19),
	distribution_rule_child VARCHAR(200),
	begin_date DATE(13),
	end_date DATE(13),
	is_cycle INT(10),
	cycle_type INT(10),
	cycle_time INT(10),
	task_describe VARCHAR(300),
	status INT(10) default 0,
	complete_process VARCHAR(10),
	priority INT(10) default 1,
	create_by VARCHAR(100),
	update_by VARCHAR(100),
	create_date TIMESTAMP(29),
	update_date TIMESTAMP(29),
	create_id BIGINT(19),
	create_org VARCHAR(100)
);

comment on table task_base_info is '任务基本信息表';

comment on column task_base_info.id is 'id';

comment on column task_base_info.task_name is '任务名称';

comment on column task_base_info.task_type is '任务类型id';

comment on column task_base_info.task_type_name is '任务类型名称';

comment on column task_base_info.distribution_rule_parent is '分配规则id';

comment on column task_base_info.distribution_rule_child is '分配规则--客群id';

comment on column task_base_info.begin_date is '开始时间';

comment on column task_base_info.end_date is '结束时间';

comment on column task_base_info.is_cycle is '是否周期任务 0-否 1-是';

comment on column task_base_info.cycle_type is '周期类型';

comment on column task_base_info.cycle_time is '周期长(天)';

comment on column task_base_info.task_describe is '任务描述';

comment on column task_base_info.status is '任务状态  0-未开始  1-进行中 2-已结束 3-手动结束';

comment on column task_base_info.complete_process is '完成进度';

comment on column task_base_info.priority is '优先级';

comment on column task_base_info.create_by is '创建人';

comment on column task_base_info.update_by is '更新人';

comment on column task_base_info.create_date is '创建时间';

comment on column task_base_info.update_date is '更新时间';

comment on column task_base_info.create_id is '创建用户id';

comment on column task_base_info.create_org is '创建用户机构code';

create unique index INDEX33654459
	on task_base_info (id);

alter table task_base_info
	add constraint CONS134252812
		primary key (id);

create table task_custom_object
(
	id BIGINT(19),
	task_id BIGINT(19),
	code VARCHAR(100),
	name VARCHAR(100),
	grid_manager_id VARCHAR(100),
	grid_manager_name VARCHAR(100),
	task_manager_id VARCHAR(100),
	task_manager_name VARCHAR(100),
	network_code VARCHAR(100),
	network_name VARCHAR(100),
	create_by VARCHAR(100),
	update_by VARCHAR(100),
	create_date TIMESTAMP(29),
	update_date TIMESTAMP(29),
	constraint INDEX4642280577837200
		unique (task_id, code)
);

comment on table task_custom_object is '任务客户对象清单表';

comment on column task_custom_object.id is 'id';

comment on column task_custom_object.task_id is '任务id';

comment on column task_custom_object.code is '客户内码';

comment on column task_custom_object.name is '客户姓名';

comment on column task_custom_object.grid_manager_id is '网格客户经理号';

comment on column task_custom_object.grid_manager_name is '网格客户经理名称';

comment on column task_custom_object.task_manager_id is '责任客户经理号';

comment on column task_custom_object.task_manager_name is '责任客户经理名称';

comment on column task_custom_object.network_code is '所属网点code';

comment on column task_custom_object.network_name is '网点名称';

comment on column task_custom_object.create_by is '创建人';

comment on column task_custom_object.update_by is '更新人';

comment on column task_custom_object.create_date is '创建时间';

comment on column task_custom_object.update_date is '更新时间';

create unique index INDEX33654456
	on task_custom_object (id);

alter table task_custom_object
	add constraint CONS134252811
		primary key (id);

create table task_detail_content
(
	id BIGINT(19),
	task_detail_id BIGINT(19),
	field_id BIGINT(19),
	detail_content VARCHAR(255)
);

comment on column task_detail_content.id is '主键';

comment on column task_detail_content.task_detail_id is '任务详情id';

comment on column task_detail_content.field_id is '子任务字段id';

comment on column task_detail_content.detail_content is '字段内容';

create unique index INDEX33654461
	on task_detail_content (id);

alter table task_detail_content
	add constraint CONS134252813
		primary key (id);

create table task_filter_list
(
	id BIGINT(19),
	custom_name VARCHAR(100)
		constraint idx_name
			unique,
	sex TINYINT(3),
	code VARCHAR(255)
		constraint INDEX4642281498069900
			unique,
	mobile_phone VARCHAR(20),
	manager_name VARCHAR(100),
	manager_code VARCHAR(50),
	network_code VARCHAR(50),
	task_id BIGINT(19),
	task_name VARCHAR(100)
		constraint idx_task_name
			unique,
	task_filter_id BIGINT(19),
	filter_reason VARCHAR(100),
	filter_date TIMESTAMP(29),
	status INT(10)
);

comment on table task_filter_list is '任务过滤清单表';

comment on column task_filter_list.id is 'id';

comment on column task_filter_list.custom_name is '客户姓名';

comment on column task_filter_list.sex is '性别  0-男  1-女   2-未知';

comment on column task_filter_list.code is '客户内码';

comment on column task_filter_list.mobile_phone is '联系电话';

comment on column task_filter_list.manager_name is '客户经理名称';

comment on column task_filter_list.manager_code is '客户经理号';

comment on column task_filter_list.network_code is '所属机构';

comment on column task_filter_list.task_id is '任务id';

comment on column task_filter_list.task_name is '任务名称';

comment on column task_filter_list.task_filter_id is '过滤规则id';

comment on column task_filter_list.filter_reason is '过滤规则名称';

comment on column task_filter_list.filter_date is '过滤时间';

comment on column task_filter_list.status is '当前状态 0-正常 1-过滤';

create unique index INDEX33654242
	on task_filter_list (id);

alter table task_filter_list
	add constraint CONS134252700
		primary key (id);

create table task_intercept_rules
(
	id BIGINT(19),
	task_type_id BIGINT(19),
	task_rules_name VARCHAR(100),
	is_open CHAR(1) default '0',
	days TINYINT(3) default 0,
	num TINYINT(3) default 0,
	create_by VARCHAR(100),
	create_date TIMESTAMP(29),
	update_by VARCHAR(100),
	update_date TIMESTAMP(29)
);

comment on table task_intercept_rules is '任务拦截规则表';

comment on column task_intercept_rules.id is 'id';

comment on column task_intercept_rules.task_type_id is '任务类型';

comment on column task_intercept_rules.task_rules_name is '拦截规则名称';

comment on column task_intercept_rules.is_open is '开关  0-开 1-关';

comment on column task_intercept_rules.days is '天数';

comment on column task_intercept_rules.num is '任务下发次数';

comment on column task_intercept_rules.create_by is '创建人';

comment on column task_intercept_rules.create_date is '创建时间';

comment on column task_intercept_rules.update_by is '更新人';

comment on column task_intercept_rules.update_date is '更新时间';

create unique index INDEX33654463
	on task_intercept_rules (id);

alter table task_intercept_rules
	add constraint CONS134252814
		primary key (id);

create table task_map_position
(
	id BIGINT(19),
	user_id BIGINT(19),
	position_type INT(10) default 1,
	start_date TIMESTAMP(29),
	end_date TIMESTAMP(29),
	positions BLOB,
	is_deleted INT(10),
	create_date TIMESTAMP(29),
	update_date TIMESTAMP(29),
	constraint idx_userid_type
		unique (user_id, position_type, start_date, end_date)
);

comment on table task_map_position is '点位信息表';

comment on column task_map_position.id is '表id';

comment on column task_map_position.user_id is '用户id';

comment on column task_map_position.position_type is '点位类型 1-任务轨迹';

comment on column task_map_position.start_date is '点位开始记录时间';

comment on column task_map_position.end_date is '点位结束记录时间';

comment on column task_map_position.positions is '点集';

comment on column task_map_position.is_deleted is '是否删除 0-未删除 1-已删除';

comment on column task_map_position.create_date is '创建时间';

comment on column task_map_position.update_date is '更新时间';

create unique index INDEX33654256
	on task_map_position (id);

alter table task_map_position
	add constraint CONS134252704
		primary key (id);

create table task_position
(
	id BIGINT(19),
	type INT(10) default 1,
	status INT(10) default 0,
	start_lot VARCHAR(30),
	start_lat VARCHAR(30),
	end_lot VARCHAR(30),
	end_lat VARCHAR(30),
	start_date TIMESTAMP(29),
	end_date TIMESTAMP(29),
	distance VARCHAR(50),
	user_id BIGINT(19),
	manager_id VARCHAR(100),
	manager_name VARCHAR(100),
	network_code VARCHAR(100),
	network_name VARCHAR(100),
	create_date TIMESTAMP(29),
	update_date TIMESTAMP(29)
);

comment on table task_position is '任务轨迹记录表';

comment on column task_position.id is '表id';

comment on column task_position.type is '类型  1-任务轨迹';

comment on column task_position.status is '状态  0-进行中  1-结束  2-超时结束';

comment on column task_position.start_lot is '开始经度';

comment on column task_position.start_lat is '开始纬度';

comment on column task_position.end_lot is '结束经度';

comment on column task_position.end_lat is '结束纬度';

comment on column task_position.start_date is '开始时间';

comment on column task_position.end_date is '结束时间';

comment on column task_position.distance is '路程';

comment on column task_position.user_id is '用户id';

comment on column task_position.manager_id is '客户经理号';

comment on column task_position.manager_name is '客户经理名称';

comment on column task_position.network_code is '网点code';

comment on column task_position.network_name is '网点名称';

comment on column task_position.create_date is '创建时间';

comment on column task_position.update_date is '更新时间';

create unique index INDEX33654465
	on task_position (id);

alter table task_position
	add constraint CONS134252815
		primary key (id);

create table task_position_record
(
	id BIGINT(19),
	task_id BIGINT(19),
	detail_id BIGINT(19),
	position_id BIGINT(19),
	lat VARCHAR(30),
	lot VARCHAR(30),
	sign_date TIMESTAMP(29),
	manager_id VARCHAR(100),
	manager_name VARCHAR(100),
	network_code VARCHAR(100),
	network_name VARCHAR(100),
	create_date TIMESTAMP(29),
	update_date TIMESTAMP(29),
	constraint INDEX4642282239094100
		unique (manager_id, detail_id, task_id)
);

comment on table task_position_record is '任务轨迹打卡记录表';

comment on column task_position_record.id is '轨迹打卡记录表id';

comment on column task_position_record.task_id is '任务id';

comment on column task_position_record.detail_id is '记录id';

comment on column task_position_record.position_id is '轨迹id';

comment on column task_position_record.lat is '纬度';

comment on column task_position_record.lot is '经度';

comment on column task_position_record.sign_date is '打卡时间';

comment on column task_position_record.manager_id is '客户经理号';

comment on column task_position_record.manager_name is '客户经理名称';

comment on column task_position_record.network_code is '网点code';

comment on column task_position_record.network_name is '网点名称';

comment on column task_position_record.create_date is '创建时间';

comment on column task_position_record.update_date is '更新时间';

create unique index INDEX33654467
	on task_position_record (id);

alter table task_position_record
	add constraint CONS134252816
		primary key (id);

create table task_statistics_detail
(
	id BIGINT(19),
	type INT(10),
	type_name VARCHAR(100),
	object_type INT(10) default 0,
	object_code VARCHAR(100),
	object_name VARCHAR(50),
	parent_code VARCHAR(100) default '0',
	parent_name VARCHAR(50),
	task_id BIGINT(19) default 0,
	task_name VARCHAR(50),
	child_complete_num INT(10),
	child_unfinish_num INT(10),
	parent_complete_num INT(10),
	parent_unfinish_num INT(10),
	score DEC(18,2),
	statistics_date DATE(13),
	create_date TIMESTAMP(29),
	update_date TIMESTAMP(29)
);

comment on table task_statistics_detail is '任务统计明细表';

comment on column task_statistics_detail.type is '统计类别 1-主任务完成率 2-子任务完成进度';

comment on column task_statistics_detail.type_name is '统计类别名称';

comment on column task_statistics_detail.object_type is '统计对象类型 0-客户经理 1-网点 2-机构';

comment on column task_statistics_detail.object_code is '对象唯一码(客户经理号,网点code,机构码)';

comment on column task_statistics_detail.object_name is '对象名称 机构名称/网点名称/客户经理名称';

comment on column task_statistics_detail.parent_code is '上一级唯一码(机构->网点->客户经理)';

comment on column task_statistics_detail.parent_name is '上一级名称';

comment on column task_statistics_detail.task_id is '任务id,统计整体时为 0';

comment on column task_statistics_detail.child_complete_num is '子任务完成数量';

comment on column task_statistics_detail.child_unfinish_num is '子任务未完成数量';

comment on column task_statistics_detail.parent_complete_num is '主任务完成数量';

comment on column task_statistics_detail.parent_unfinish_num is '主任务未完成数量';

comment on column task_statistics_detail.score is '百分比数值';

comment on column task_statistics_detail.statistics_date is '统计日期';

comment on column task_statistics_detail.create_date is '创建时间';

comment on column task_statistics_detail.update_date is '更新时间';

create unique index INDEX33654470
	on task_statistics_detail (id);

alter table task_statistics_detail
	add constraint CONS134252817
		primary key (id);

create table task_type_child
(
	id BIGINT(19),
	parent_id BIGINT(19) not null
		constraint INDEX4642282541166400
			unique,
	field_name VARCHAR(100),
	field_description VARCHAR(1000),
	is_required CHAR(1),
	filling_form VARCHAR(100),
	content TEXT(max)
);

comment on table task_type_child is '任务类型字段表';

comment on column task_type_child.id is 'id';

comment on column task_type_child.parent_id is '类型父表id';

comment on column task_type_child.field_name is '字段名称';

comment on column task_type_child.field_description is '字段描述';

comment on column task_type_child.is_required is '是否必填 0-否 1-是';

comment on column task_type_child.filling_form is '填写形式 1-文字 2-文本 3-是否选项 4-图片 5-标签选择 6-打卡签到';

comment on column task_type_child.content is '域值内容';

create unique index INDEX33654495
	on task_type_child (id);

alter table task_type_child
	add constraint CONS134252834
		primary key (id);

create table task_type_parent
(
	id BIGINT(19),
	group_type VARCHAR(100),
	task_type_name VARCHAR(100),
	task_object_id INT(10),
	task_object VARCHAR(100),
	remark VARCHAR(300),
	create_by VARCHAR(100),
	update_by VARCHAR(100),
	create_date TIMESTAMP(29),
	update_date TIMESTAMP(29),
	create_id BIGINT(19),
	create_org VARCHAR(100)
);

comment on table task_type_parent is '任务类型主表';

comment on column task_type_parent.id is 'id';

comment on column task_type_parent.group_type is '业务群体分类';

comment on column task_type_parent.task_type_name is '类型名称';

comment on column task_type_parent.task_object_id is '任务对象唯一标识';

comment on column task_type_parent.task_object is '任务对象';

comment on column task_type_parent.remark is '备注';

comment on column task_type_parent.create_by is '创建人(客户经理号)';

comment on column task_type_parent.update_by is '更新人';

comment on column task_type_parent.create_date is '创建时间';

comment on column task_type_parent.update_date is '更新时间';

comment on column task_type_parent.create_id is '创建用户id';

comment on column task_type_parent.create_org is '创建用户机构code';

create unique index INDEX33654493
	on task_type_parent (id);

alter table task_type_parent
	add constraint CONS134252833
		primary key (id);

create table tenant_capacity
(
	id BIGINT(19),
	tenant_id VARCHAR(128) default '' not null
		constraint INDEX33654473
			unique,
	quota BIGINT(19) default 0 not null,
	usage BIGINT(19) default 0 not null,
	max_size BIGINT(19) default 0 not null,
	max_aggr_count BIGINT(19) default 0 not null,
	max_aggr_size BIGINT(19) default 0 not null,
	max_history_count BIGINT(19) default 0 not null,
	gmt_create TIMESTAMP(29) default CURRENT_TIMESTAMP() not null,
	gmt_modified TIMESTAMP(29) default CURRENT_TIMESTAMP() not null
);

comment on table tenant_capacity is '租户容量信息表';

comment on column tenant_capacity.id is '主键ID';

comment on column tenant_capacity.tenant_id is 'Tenant ID';

comment on column tenant_capacity.quota is '配额，0表示使用默认值';

comment on column tenant_capacity.usage is '使用量';

comment on column tenant_capacity.max_size is '单个配置大小上限，单位为字节，0表示使用默认值';

comment on column tenant_capacity.max_aggr_count is '聚合子配置最大个数';

comment on column tenant_capacity.max_aggr_size is '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';

comment on column tenant_capacity.max_history_count is '最大变更历史数量';

comment on column tenant_capacity.gmt_create is '创建时间';

comment on column tenant_capacity.gmt_modified is '修改时间';

create unique index INDEX33654472
	on tenant_capacity (id);

alter table tenant_capacity
	add constraint CONS134252818
		primary key (id);

create table tenant_info
(
	id BIGINT(19),
	kp VARCHAR(128) not null,
	tenant_id VARCHAR(128) default ''
		constraint INDEX4645177463975200
			unique,
	tenant_name VARCHAR(128) default '',
	tenant_desc VARCHAR(256),
	create_source VARCHAR(32),
	gmt_create BIGINT(19) not null,
	gmt_modified BIGINT(19) not null,
	constraint INDEX33654476
		unique (kp, tenant_id)
);

comment on table tenant_info is 'tenant_info';

comment on column tenant_info.id is 'id';

comment on column tenant_info.kp is 'kp';

comment on column tenant_info.tenant_id is 'tenant_id';

comment on column tenant_info.tenant_name is 'tenant_name';

comment on column tenant_info.tenant_desc is 'tenant_desc';

comment on column tenant_info.create_source is 'create_source';

comment on column tenant_info.gmt_create is '创建时间';

comment on column tenant_info.gmt_modified is '修改时间';

create unique index INDEX33654475
	on tenant_info (id);

alter table tenant_info
	add constraint CONS134252826
		primary key (id);

create table users
(
	username VARCHAR(50) not null,
	password VARCHAR(500) not null,
	enabled TINYINT(3) not null
);

create unique index INDEX33654480
	on users (username);

alter table users
	add constraint CONS134252828
		primary key (username);

create table xsky_log
(
	url VARCHAR(32752),
	bus_type VARCHAR(32752),
	file_type VARCHAR(32752),
	create_time DATE(13),
	update_time DATE(13),
	bucket_name VARCHAR(32752),
	pic_key VARCHAR(32752),
	serial_number VARCHAR(32752)
);

