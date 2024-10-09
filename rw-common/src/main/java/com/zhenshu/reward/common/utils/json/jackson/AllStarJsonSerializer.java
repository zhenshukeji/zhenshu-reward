package com.zhenshu.reward.common.utils.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zhenshu.reward.common.constant.Constants;

import java.io.IOException;

/**
 * @author hong
 * @version 1.0
 * @date 2024/6/12 17:02
 * @desc 全字符替换为*
 */
public class AllStarJsonSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        StringBuilder starBuilder = new StringBuilder();
        for (int i = Constants.ZERO; i < s.length(); i++) {
            starBuilder.append('*');
        }
        jsonGenerator.writeString(starBuilder.toString());
    }
}
