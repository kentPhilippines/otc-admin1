-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司人员管理', '121', '1', '/busi/person/{0}', 'C', '0', 'busi:person:view', '#', 'admin', sysdate(), '', null, '客户公司人员管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司人员管理查询', @parentId, '1',  '#',  'F', '0', 'busi:person:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司人员管理新增', @parentId, '2',  '#',  'F', '0', 'busi:person:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司人员管理修改', @parentId, '3',  '#',  'F', '0', 'busi:person:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司人员管理删除', @parentId, '4',  '#',  'F', '0', 'busi:person:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司人员管理导出', @parentId, '5',  '#',  'F', '0', 'busi:person:export',       '#', 'admin', sysdate(), '', null, '');



-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司管理', '121', '1', '/busi/company', 'C', '0', 'busi:company:view', '#', 'admin', sysdate(), '', null, '客户公司管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司管理查询', @parentId, '1',  '#',  'F', '0', 'busi:company:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司管理新增', @parentId, '2',  '#',  'F', '0', 'busi:company:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司管理修改', @parentId, '3',  '#',  'F', '0', 'busi:company:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司管理删除', @parentId, '4',  '#',  'F', '0', 'busi:company:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户公司管理导出', @parentId, '5',  '#',  'F', '0', 'busi:company:export',       '#', 'admin', sysdate(), '', null, '');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('监区产线', '117', '1', '/busi/prisonLine', 'C', '0', 'busi:prisonLine:view', '#', 'admin', sysdate(), '', null, '监区产线菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('监区产线查询', @parentId, '1',  '#',  'F', '0', 'busi:prisonLine:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('监区产线新增', @parentId, '2',  '#',  'F', '0', 'busi:prisonLine:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('监区产线修改', @parentId, '3',  '#',  'F', '0', 'busi:prisonLine:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('监区产线删除', @parentId, '4',  '#',  'F', '0', 'busi:prisonLine:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('监区产线导出', @parentId, '5',  '#',  'F', '0', 'busi:prisonLine:export',       '#', 'admin', sysdate(), '', null, '');



-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订单维护', '119', '1', '/busi/order', 'C', '0', 'busi:order:view', '#', 'admin', sysdate(), '', null, '订单菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订单查询', @parentId, '1',  '#',  'F', '0', 'busi:order:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订单新增', @parentId, '2',  '#',  'F', '0', 'busi:order:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订单修改', @parentId, '3',  '#',  'F', '0', 'busi:order:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订单删除', @parentId, '4',  '#',  'F', '0', 'busi:order:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('订单导出', @parentId, '5',  '#',  'F', '0', 'busi:order:export',       '#', 'admin', sysdate(), '', null, '');



-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品需求', '119', '1', '/busi/productRequire', 'C', '0', 'busi:productRequire:view', '#', 'admin', sysdate(), '', null, '产品需求菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品需求查询', @parentId, '1',  '#',  'F', '0', 'busi:productRequire:list',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品需求新增', @parentId, '2',  '#',  'F', '0', 'busi:productRequire:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品需求修改', @parentId, '3',  '#',  'F', '0', 'busi:productRequire:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品需求删除', @parentId, '4',  '#',  'F', '0', 'busi:productRequire:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品需求导出', @parentId, '5',  '#',  'F', '0', 'busi:productRequire:export',       '#', 'admin', sysdate(), '', null, '');