package com.zhouchuanxiang.inputlab.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 请求消息体
 */
@Setter
@Getter
public class RequestMsgVO implements Serializable {

    /**
     * 用户名
     */
    public String userName;

    /**
     * 用户名
     */
    public String userId;


    /**
     * 角色编码
     */
    public List<String> roleList;

    /**
     * 用户类型
     */
    public String userType;
    /**
     * 所属系统 业务系统：business   管理平台：manager
     */
    public String systemType;
    /**
     * 管理平台鉴权使用： 4A配置的component-菜单标签
     */
    public Set<String> componentSet;

    /**
     * URL后缀带的机构ID 分润的企业ID
     */
    public String shareOrgId;

    /**
     * 自己的所属机构/企业ID
     */
    public String orgId;

    /**
     * 部门名称
     */
    public String department;

}
