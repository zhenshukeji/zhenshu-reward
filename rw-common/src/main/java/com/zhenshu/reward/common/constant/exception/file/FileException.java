package com.zhenshu.reward.common.constant.exception.file;

import com.zhenshu.reward.common.constant.exception.base.BaseException;

/**
 * 文件信息异常类
 *
 * @author zhenshu
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
