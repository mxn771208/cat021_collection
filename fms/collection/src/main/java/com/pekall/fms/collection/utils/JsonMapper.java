package com.pekall.fms.collection.utils;
import java.io.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonParseException;

import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by maxl on 15-10-10.
 */
public class JsonMapper {
  private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);
  private static ObjectMapper m = new ObjectMapper();
  private static JsonFactory jf = new JsonFactory();
  static {
    //JsonEncoding encoding = JsonEncoding.UTF8;
    //不序列化null值和empty值
    m.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static <T> Object fromJson(String jsonAsString, Class<T> pojoClass)
    throws JsonMappingException, JsonParseException, IOException {
    return m.readValue(jsonAsString, pojoClass);
  }

  public static <T> Object fromInputStream(InputStream is, Class<T> pojoClass)
    throws JsonMappingException, JsonParseException, IOException {
    return m.readValue(is, pojoClass);
  }

  public static <T> Object fromJson(FileReader fr, Class<T> pojoClass)
    throws JsonParseException, IOException
  {
    return m.readValue(fr, pojoClass);
  }
  public JsonNode fromJson(String jsonAsString)
    throws JsonParseException, IOException
  {
    return m.readTree(jsonAsString);
  }

  public static String toJson(Object pojo) {
    try {
      return toJson(pojo,true);
    }
    catch(Exception ex) {
      logger.error("反序列化json数据:"+ex.toString());
      return "";
    }
  }

  public static String toJson(Object pojo, boolean prettyPrint)
    throws JsonMappingException, JsonGenerationException, IOException {
    StringWriter sw = new StringWriter();
    JsonGenerator jg = jf.createGenerator(sw);
    if (prettyPrint) {
      jg.useDefaultPrettyPrinter();
    }
    //不序列化null值和empty值
    //m.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    //m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    m.writeValue(jg, pojo);
    return sw.toString();
  }

  public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint)
    throws JsonMappingException, JsonGenerationException, IOException {
    JsonGenerator jg = jf.createGenerator(fw);
    if (prettyPrint) {
      jg.useDefaultPrettyPrinter();
    }
    //不序列化null值和empty值
    //m.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    //m.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    m.writeValue(jg, pojo);
  }
}
