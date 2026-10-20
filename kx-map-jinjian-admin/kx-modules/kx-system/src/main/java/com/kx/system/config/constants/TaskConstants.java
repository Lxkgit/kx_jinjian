package com.kx.system.config.constants;

public class TaskConstants {
    /**周期任务自定义*/
    public static final Integer  IS_CYCLE = 4;
    /**excle导入*/
    public static final String  IS_EXCLE = "excle导入";

    /**主任务状态  0-未开始  1-进行中 2-已结束 */
    public static final Integer  TASK_STATUS_INIT = 0;
    public static final Integer  TASK_STATUS_RUNNING= 1;
    public static final Integer  TASK_STATUS_STOP = 2;
    public static final Integer  TASK_STATUS_STOP_UNEND = 3;

    /**任务状态  0-未开始  1-进行中 2-已结束 */
    public static final Integer  CHILD_TASK_STATUS_UNFINISH = 0;
    public static final Integer  CHILD_TASK_STATUS_COMPLETE= 1;
    public static final Integer  CHILD_TASK_STATUS_OVER = 2;

    /**任务类型  0-主任务  1-子任务 */
    public static final Integer  TASK_PARENT = 0;
    public static final Integer  TASK_CHILD= 1;

    /**下派记录状态 0-未删除 1-已删除*/
    public static final Integer  TASK_NOT_DELETED = 0;
    public static final Integer  TASK_IS_DELETED= 1;

    /**下派任务操作 0-新增 1-删除 2-变更*/
    public static final Integer  TASK_ASSIGN_ADD = 0;
    public static final Integer  TASK_ASSIGN_DELETE= 1;
    public static final Integer  TASK_ASSIGN_UPDATE= 2;
    public static final Integer  TASK_ASSIGN_UNKNOWN= 3;

    /**客户导入状态*/
    public static final String  CUSTOM_ADD ="新增";
    public static final String  CUSTOM_DELETE="删除";
    public static final String  CUSTOM_UPDATE="变更";
    public static final String  CUSTOM_UNKNOWN="未知";

    /**任务移交状态  0-保存  1-提交 2-撤回 */
    public static final Integer  TASK_TRANSFER_SAVE = 0;
    public static final Integer  TASK_TRANSFER_COMMIT= 1;
    public static final Integer  TASK_TRANSFER_RETURN = 2;

    /**下派客群*/
    public static final String  CUSTOM_TABLE_NAME ="tm_customer_group";

    /**任务过滤客户状态  0-正常 1-过滤 */
    public static final Integer  FILTER_STATUS_NORMAL= 0;
    public static final Integer  FILTER_STATUS= 1;

    /**任务周期  0-否 1-是 */
    public static final Integer  IS_NOT_CYCLE_TASK= 0;
    public static final Integer  IS_CYCLE_TASK= 1;

    /**任务轨迹状态 0-进行中 1-结束 2-超时结束*/
    public static final Integer  IS_RUNNING= 0;
    public static final Integer  IS_END= 1;
    public static final Integer  IS_OVERTIME_END= 2;

    /**轨迹类型 1-任务轨迹*/
    public static final Integer  IS_TASK= 1;

    /**打卡签到 范围20米*/
    public static final Double  SIGN_DISTANCE= 2000000.0d;

    /**
     * 必填
     */
    public static final Integer IS_REQUIRED = 1;

    /**
     * 非必填
     */
    public static final Integer NOT_REQUIRED = 0;

}
