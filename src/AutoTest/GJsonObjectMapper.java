package AutoTest;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *  Json数据处理
 */
public class GJsonObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    /**
     *  Json配置
     */
	@SuppressWarnings("deprecation")
	public GJsonObjectMapper() {
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.setSerializationInclusion(Include.NON_NULL);
    }

	/**
	 *  Json读取数据结构
	 */
    public <T> T readValue(String content, Class<T> valueType) {
        try {
            return super.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Json写入数据处理
     */
    public String writeValueAsString(Object object) {
        try {
            return super.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
