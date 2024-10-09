package com.zhenshu.reward.common.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hong
 * @version 1.0
 * @date 2023/4/28 10:43
 * @desc json序列化Bean
 */
@Slf4j
@Component
public class JsonUtil {
    private static ObjectMapper objectMapper;

    @Autowired
    public JsonUtil(ApplicationContext applicationContext, List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
        // 创建构造器
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder()
                .applicationContext(applicationContext);
        // 系统定制化
        for (Jackson2ObjectMapperBuilderCustomizer customizer : customizers) {
            customizer.customize(jackson2ObjectMapperBuilder);
        }
        // 自定义定制化
        Jackson2ObjectMapperBuilderCustomizer customizer = builder -> {
            // 关闭缩进输出
            builder.indentOutput(false);
        };
        customizer.customize(jackson2ObjectMapperBuilder);
        // 构建 ObjectMapper
        JsonUtil.objectMapper = jackson2ObjectMapperBuilder.createXmlMapper(false).build();
    }

    /**
     * 对象转JSON字符串
     *
     * @param obj 对象
     * @return 结果
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Json序列化异常; 原因:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON字符串转对象
     *
     * @param json   数据
     * @param tClass class对象
     * @return 结果
     */
    public static <T> T toObject(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("Json反序列化异常; 原因:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON字符串转对象
     *
     * @param map    数据
     * @param tClass class对象
     * @return 结果
     */
    public static <T> T toObject(Map<String, Object> map, Class<T> tClass) {
        return objectMapper.convertValue(map, tClass);
    }

    /**
     * JSON字符串转对象
     *
     * @param json          数据
     * @param typeReference class对象
     * @return 结果
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Json反序列化异常; 原因:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * map  转JavaBean
     */
    public static <T> T toObject(Object obj, Class<T> clazz) {
        return objectMapper.convertValue(obj, clazz);
    }

    /**
     * JSON字符串转对象
     *
     * @param json   数据
     * @param tClass class对象
     * @return 结果
     */
    public static <T> Set<T> toSet(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(Set.class, tClass));
        } catch (JsonProcessingException e) {
            log.error("Json反序列化异常; 原因:{}", e.getMessage());
            return null;
        }
    }

    /**
     * JSON字符串转对象
     *
     * @param json   数据
     * @param tClass class对象
     * @return 结果
     */
    public static <T> List<T> toArray(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(List.class, tClass));
        } catch (JsonProcessingException e) {
            log.error("Json反序列化异常; 原因:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转Map
     *
     * @param obj 原始对象
     * @return 结果
     */
    public static Map<String, Object> toMap(Object obj) {
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * 获取json字符串中指定参数的值
     *
     * @param json  json字符串
     * @param field 参数名称
     * @return 结果
     */
    public static String parseField(String json, String field) {
        try {
            JsonNode node = objectMapper.readTree(json);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                if (leaf.isObject() || leaf.isArray()) {
                    return leaf.toString();
                } else {
                    // 非复杂类型, 字段转字符串返回
                    return leaf.asText();
                }
            }
            return null;
        } catch (JsonProcessingException e) {
            log.error("Json解析属性值失败; 原因:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
