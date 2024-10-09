package com.zhenshu.reward.common.utils;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author hong
 * @version 1.0
 * @date 2024/5/14 17:50
 * @desc 事务生命周期工具类
 */
public class TransactionLifeCycleUtils {
    /**
     * 事务提交后执行
     *
     * @param runnable 执行方法
     */
    public static void afterCommit(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            // 在事务中, 事务提交后执行
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            // 不在事务中, 立刻执行
            runnable.run();
        }
    }
}
