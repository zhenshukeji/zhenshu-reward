package com.zhenshu.reward.common.config.mybatis.meta;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhenshu.reward.common.constant.Constants;
import com.zhenshu.reward.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author hong
 * @version 1.0
 * @date 2023/6/20 15:16
 * @desc 自动填充
 */
@Slf4j
@Component
public class FillMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getUsername());
            this.strictInsertFill(metaObject, "createBy", Long.class, SecurityUtils.getUserId());
        }
        this.strictInsertFill(metaObject, "delFlag", Boolean.class, false);
        this.strictInsertFill(metaObject, "version", Integer.class, Constants.ONE);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
