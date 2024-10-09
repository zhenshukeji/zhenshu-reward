/**
 * @author hong
 * @date 2024/5/29 18:53
 * @version 1.0
 * @desc 简单玩法
 * <p>
 * 简单玩法: 从接口角度来看无特殊要求就能进行结算的玩法
 *  例如: 浏览咨询、分享...
 *  特点: 接口无法进行判断玩法是否真实完成, 只能由调用方进行判断
 * </p>
 * <p>
 * 接入步骤:
 *  1.实现 SimpleActivityInfo 接口定义一个玩法活动信息, 为每个字段标注好新增和修改时的JSR303校验枚举, 或实现 verify 方法完成参数校验
 *  2.在 SimpleActivityType 中新增一个玩法对应的枚举
 *  3.接入任务结算功能模块, 详见 README.md 中的【玩法结算接入】小节
 *  4.SimpleActivityProvider#supper 方法中放行【玩法结算接入】小节添加的 TaskType 任务类型
 * </p>
 */
package com.zhenshu.reward.game.simple;
