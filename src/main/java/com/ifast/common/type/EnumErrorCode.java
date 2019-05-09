package com.ifast.common.type;

/**
 * <pre>
 * 全局异常码
 * </pre>
 * <small> 2018年4月5日 | Aron</small>
 */
public enum EnumErrorCode {


    ok(200, "请求成功")
    , unknowFail(500, "未知错误")
    , illegalArgument(400, "参数校验异常")
    , notAuthorization(401, "未授权")
    , pageNotFound(404, "页面不存在")


    , duplicateKeyExist(40000, "记录已存在")

    , genReadConfigError(40100, "代码生成器获取配置文件失败")
    , genWriteConfigError(40101, "代码生成器修改配置文件失败")
    , genRenderTemplateError(40102, "代码生成器渲染模板失败")

    , FileUploadGetBytesError(40200, "文件上传错误")
    , FileUploadError(40201, "文件上传错误")

    , deptUpdateErrorExistChilds(40300, "包含下级部门,不允许修改")
    , deptDeleteErrorExistUsers(40301, "包含用户,不允许删除")

    , userLoginFail(40401, "用户或密码错误")
    , userUpdatePwd4adminNotAllowed(40402, "超级管理员的账号不允许直接重置！")
    , wxmpMenuSaveMainError(40500, "微信主菜单不能超过3个")
    , wxmpMenuSaveSubError(40501, "微信子菜单不能超过5个")
    , wxmpMediaImageSyncError(40600, "图片素材同步错误")
    , wxmpMediaGroupSendSyncError(40610, "素材群发失败")
    , wxmpFansSyncError(40700, "粉丝同步失败")


    // API
    , apiAuthorizationLoginFailed(44000, "用户名或密码错误")
    , apiAuthorizationSignFailed(44000, "token生成失败")
    , apiAuthorizationInvalid(44001, "token不合法")
    , apiAuthorizationLoggedout(44002, "token已注销")
    , apiAuthorizationExpired(44003, "token已过期")
    , apiAuthorizationFailed(44004, "token认证失败")

    , apiSmsSendFailed(44100, "短信发送失败")
    , apiSmsSendFailed4ContentNull(44101, "短信发送失败")
    , apiSmsCodeInvalid(44110, "短信验证码错误")

    , apiWxMpAppIdError(44200, "appId不合法")

    , apiCardUserNotFound(45000, "用户不存在")
    , apiCardNotPermission(45001, "没有权限")

    ;

    private int code;
    private String msg;

    EnumErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getCodeStr() {
        return code + "";
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public EnumErrorCode setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public static String getMsgByCode(int code) {
        EnumErrorCode[] values = EnumErrorCode.values();
        for (EnumErrorCode ec : values) {
            if (ec.code == code) {
                return ec.msg;
            }
        }
        return "";
    }

    /**
     *
     * <pre>
     * </pre>
     *
     * <small> Aron | 2017-09-05</small>
     *
     * @param args
     */
    public static void main(String[] args) {
        String msg = EnumErrorCode.getMsgByCode(EnumErrorCode.unknowFail.code);
        System.out.println(msg);
    }



}
