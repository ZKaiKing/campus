package com.graduation.compusinfo.display.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class JsonUtils {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";


    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.registerModule(new JavaTimeModule());

    }

    private JsonUtils() {}

    /**
     * 实体转为json字符串
     *
     * @param o
     * @return
     */
    @SneakyThrows
    public static String toJson(@NonNull Object o) {
        return objectMapper.writeValueAsString(o);
    }

    /**
     * 实体转换为格式化的json字符串
     *
     * @param o
     * @return
     */
    @SneakyThrows
    public static String toPrettyJson(@NonNull Object o) {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    /**
     * json 字符串转为实体
     *
     * @param json json 字符串
     * @param clazz 实体class
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T toBean(@NonNull String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * json 字符串转为 map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(@NonNull String json) {
        return to(json, new TypeReference<Map<String, Object>>(){});
    }

    /**
     * json 数组字符串转为 list 集合
     *
     * @param json
     * @param elementsClass
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> List<T> toList(@NonNull String json, Class<T> elementsClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, elementsClass);
        return objectMapper.readValue(json, javaType);

    }

    /**
     * json 字符串转为 typeReference 中声明的泛型
     *
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T to(@NonNull String json, TypeReference<T> typeReference) {
        return objectMapper.readValue(json, typeReference);
    }


}
