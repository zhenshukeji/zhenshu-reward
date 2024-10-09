package com.zhenshu.reward.common.utils.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zhenshu.reward.common.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json 工具类
 */
public class JacksonUtil {

    private static final Log logger = LogFactory.getLog(JacksonUtil.class);

    public static String parseString(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asText();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public static List<String> parseStringList(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);

            if (leaf != null) {
                return mapper.convertValue(leaf, new TypeReference<List<String>>() {
                });
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Integer parseInteger(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asInt();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static List<Integer> parseIntegerList(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);

            if (leaf != null) {
                return mapper.convertValue(leaf, new TypeReference<List<Integer>>() {
                });
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Long parseLong(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asLong();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static List<Long> parseLongList(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);

            if (leaf != null) {
                return mapper.convertValue(leaf, new TypeReference<List<Long>>() {
                });
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Boolean parseBoolean(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asBoolean();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Short parseShort(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                Integer value = leaf.asInt();
                return value.shortValue();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Byte parseByte(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null) {
                Integer value = leaf.asInt();
                return value.byteValue();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T parseObject(String body, String field, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            node = node.get(field);
            return mapper.treeToValue(node, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static JsonNode toNode(String json) {
        if (json == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {

            return mapper.readTree(json);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static Map<String, String> toMap(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(data, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toJson(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            JavaTimeModule timeModule = new JavaTimeModule();
            timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            objectMapper.registerModule(timeModule);
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * json 转对象
     *
     * @param json  json
     * @param clazz 对象
     * @param <T>   对象
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            JavaTimeModule timeModule = new JavaTimeModule();
            timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            mapper.registerModule(timeModule);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return null;
    }


    /**
     * 获取List集合
     *
     * @param json json字符串
     * @param t    实体类
     * @return 集合
     * @throws JsonParseException 解析异常
     */
    public static <T> List<T> toObjectList(String json, Class<T> t) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            List<T> result = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            JavaTimeModule timeModule = new JavaTimeModule();
            timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            mapper.registerModule(timeModule);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            List list = mapper.readValue(json, List.class);
            for (Object o : list) {
                String jsonString = toJson(o);
                result.add(toObject(jsonString, t));
            }
            return result;
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return null;
    }

    public static void main(String[] args) {
        String data = "{\"skey\":\"nSNfMQlOQJ2rCDmVKPA4Vcd/UWsjl1erEsfF/XZxLDyIjynnIgzEZT/nyYNQwAQ7+ooGk1cXSSAzVDsUGCrh1z4CPJ0xMDaf1/ytHfCpHSWMSezlgpmOAGk4NMM/uMdLdfH76/FNwU5vtXy8u+JdonJw7EeOfg4xAFCWj5z+J2s=\",\"body\":\"KlthbnVYG8NfoVi4+Kcl31gPc9ZCwpEt78SXvLZ+vkfNAf7rP6BpGBYk9XCCau6sFHKu1NUjjpaJhnHMWn3sWDr9rXSc7Nm00+2Zff35L9GpPpP25TODpkQW7N0aMQdCPRSvCHIBZHiU8dZf09VuIae1A0mJLf54IY9H419kca8fjiHk4qRDrU3jse0jrMa6ekxoUa5dWbTkIBVs/3YWQINMyaDo2YX9RHxeVBLrhiNVNAQjAyDu/slatMlqko6DqcKAGvNbQaVuL7pBxHFlp7PNjIbGrXcXcasyDK9HAUEY9LsU19FGxwrNOy/SPH4r7od2Hi8nRbdy0sDw76AgoHtvu7Ob9W7vpoc/2dorafY/at7+4+qLRJi/0Bvbw1i5Aa8AA/RbOdOQ/uOTSRpzGg==\"}";
        JsonNode node = JacksonUtil.toNode(data);
    }
}
