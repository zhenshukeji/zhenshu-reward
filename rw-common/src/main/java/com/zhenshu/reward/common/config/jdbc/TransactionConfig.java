package com.zhenshu.reward.common.config.jdbc;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author xyh
 * @version 1.0
 * @date 2022/5/25 15:27
 * @desc
 */
@SpringBootConfiguration
public class TransactionConfig {

    @Bean
    public TransactionTemplate transactionTemplate(DataSourceTransactionManager dataSourceTransactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSourceTransactionManager);
        // 可以设置事务隔离级别, 默认使用数据库默认隔离级别
        // transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 可以设置事务传播方式
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 可以设置事务超时时间
        // transactionTemplate.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
        return transactionTemplate;
    }


    @Bean
    public TransactionTemplate newTransactionTemplate(DataSourceTransactionManager dataSourceTransactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSourceTransactionManager);
        // 可以设置事务隔离级别, 默认使用数据库默认隔离级别
        // transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 可以设置事务传播方式
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 可以设置事务超时时间
        // transactionTemplate.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
        return transactionTemplate;
    }

}
