package com.zhenshu.reward.common.constant.enums;

import com.zhenshu.reward.common.constant.HttpStatus;
import com.zhenshu.reward.common.constant.exception.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/8/5 11:15
 * @desc 通用异常
 */
@Getter
@AllArgsConstructor
public enum ErrorEnums implements Error {
    /**
     * 返回数据
     */
    SUCCESS(HttpStatus.SUCCESS, "成功"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "请重新登录"),
    INNER_ERROR(HttpStatus.ERROR, "参与人数过多，请稍后重试"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "参数错误或者格式不正确"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "权限不足，禁止访问"),

    BIZ_ERROR_CODE(30300, "操作错误"),
    FREQUENT_OPERATION(30302, "有其他操作正在进行，暂无法执行此操作，请稍后再试"),
    VERSION_ERROR(30303, "操作失败，请稍后重试"),

    NOT_EXPORT_DATA(38001, "没有可导出的数据"),
    IMPORT_ERROR(38002, "导入失败, 格式不正确"),
    IMPORT_ERROR_CHECK_DATA(38003, "导入失败, 请检查数据是否填写完整"),
    IMPORT_NOT_DATA(38004, "导入失败, 请填写数据"),
    DATA_NOT_EXIST(38005, "数据不存在"),
    SEND_SMS_ERROR(38006, "发送验证码异常，请稍后重试"),
    EXPORT_ERROR(38007, "导出失败"),
    NO_THAN_ONE_MINUTE(38008, "短信验证码未超过1分钟"),
    CODE_ERROR(38009, "验证码不正确"),
    ;


    private final Integer code;

    private final String msg;
}
