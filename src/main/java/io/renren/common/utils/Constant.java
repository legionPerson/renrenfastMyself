/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

    /**
     *  同步项目数据uri
     */
    public static final String SYNC_PROJECT_DATA_URI = "/webcommon/getSSLx";

    /**
     *  推送项目审批数据uri
     */
    public static final String PUSH_PROJECT_APPV_URI = "/commitapproval/GzCommitApproval";


    /**
     * 同步项目审批状态数据
     */
    public static final String  SYNC_CHECK_STATUS ="/oa/getSpStatusInfoList";

    /**
     * 同步机构数据
     */
    public static final String  SYNC_ORG_DATA_URI ="/user/getDptInfo?offset=";



    /**
     * 同步用户数据
     */
    public static final String SYNC_USER_DATA="/user/getEmpInfo";


    /**
     * 测试获取审批状态
     */
    public  static final String TEST_CHECK_DATA="/test/testb";
    /**
     *  redis-key 机构树
     */
    public static final String REDIS_KEY_ORG_TREE = "org:tree";

    /**
     * redis-key 机构树（过滤被隔离的机构）
     */
    public static final String REDIS_KEY_ORG_TREE_WHIT_INSULATED = "org:tree:insulated";


    // ---------------项目阶段，1：售前，2：实施，3：完成交付,4:运维，5：其他----------------
    /**
     *  售前
     */
    public static final String PHASE_SALE = "售前";

    /**
     *  售前
     */
    public static final String PHASE_IMPL = "实施";

    /**
     *  售前
     */
    public static final String PHASE_DONE = "完成交付";

    /**
     *  售前
     */
    public static final String PHASE_OPS = "运维";

    /**
     *  售前
     */
    public static final String PHASE_OTHERS = "其他";



    // ---------------审批状态,1：提交审批，2：审批通过，3：驳回，4：已撤销,5:提交失败----------------
    /**
     *  提交审批
     */
    public static final String APPV_STATUS_COMMIT = "1";

    /**
     *  审批通过
     */
    public static final String APPV_STATUS_PASS = "2";

    /**
     *  驳回
     */
    public static final String APPV_STATUS_BACK = "3";

    /**
     *  已撤销
     */
    public static final String APPV_STATUS_CANCEL = "4";

    /**
     *  提交失败
     */
    public static final String APPV_STATUS_COMMIT_FAIL = "5";

    /**
     *  取第一行
     */
    public static final String LIMIT_1 = "limit 1";


	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final String APPID=" wxe976ce04277d58ea";

    public static final String SEND_REPORT_URL="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";

}
