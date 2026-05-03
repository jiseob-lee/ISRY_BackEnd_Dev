package isry.itgcms.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.BooleanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.rte.fdl.string.EgovStringUtil;

/**  
 * @Class Name : ConvertUtils.java
 * @Description : 변환처리 , VO(List) -> MAP(List) , MAP(List) -> VO(List) 
 * @Modification Information  
 * @
 * @  수정일      	    수정자          		수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2022.05.19    HAN      	최초생성
 * 
 * @author 공통팀
 * @since 2022.05.19
 * @version 1.0
 */
public class ConvertUtils {
    private ConvertUtils() {}

    // 객체 => map
    public static Map<String, Object> convertToMap(Object obj) {
        try {
            if (Objects.isNull(obj)) {
                return Collections.emptyMap();
            }
            Map<String, Object> convertMap = new HashMap<>();

            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                convertMap.put(field.getName(), field.get(obj));
            }
            return convertMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // map => 객체
    public static <T> T convertToValueObject(Map<String, Object> map, Class<T> type) {
        try {
            Objects.requireNonNull(type, "Class cannot be null");
            T instance = type.getConstructor().newInstance();

            if (map == null || map.isEmpty()) {
                return instance;
            }

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Field[] fields = type.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = field.getName();

                    boolean isSameType = entry.getValue().getClass().equals(field.getType());
                    boolean isSameName = entry.getKey().equals(name);

                    if (isSameType && isSameName) {
                        field.set(instance, map.get(name));
                        break;
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 목록 : 객체(vo) => map
    public static List<Map<String, Object>> convertToMaps(List<?> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> convertList = new ArrayList<>(list.size());

        for (Object obj : list) {
            convertList.add(ConvertUtils.convertToMap(obj));
        }
        return convertList;
    }

    // 목록 : map => 객체(vo)
    public static <T> List<T> convertToValueObjects(List<Map<String, Object>> list, Class<T> type) {
        Objects.requireNonNull(type, "Class cannot be null");

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> convertList = new ArrayList<>(list.size());

        for (Map<String, Object> map : list) {
            convertList.add(ConvertUtils.convertToValueObject(map, type));
        }
        return convertList;
    }
    
    /**
	 * 객체 => DataMap 변환
	 * 
	 * @param obj	변환할 object 객체
	 * @return	생성된 DataMap 객체
	 */
    public static <T> Map<String, Object> convertToDataMap(T obj) {
		if (Objects.isNull(obj)) {
            return Collections.emptyMap();
        }
		
		// 데이터를 담을 Map 객체 생성
		Map<String, Object> convertMap = new LinkedHashMap<>();
		
		// 자바 Reflect 를 이용한 해당 object의 클래스 접근
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			// private 변수 접근
			field.setAccessible(true);
			
			// field 명 카멜케이스에서 언더스코어로 변환
			String fieldName = field.getName();
			
			String key = EgovStringUtil.convertToUnderScore(fieldName);
			// key 명 대문자 언더스코어로 변환
			key = key.toUpperCase();
			
			// Serialize 변수는 무시
			if (key.startsWith("SERIAL_VERSION_")) {
				continue;
			}
			
			Object value = new Object();
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// do ignore
			}
			
			convertMap.put(key, value);
		}
		
		return convertMap;
	}
    
    /**
	 * 객체 목록 => DataSet 변환
	 * 
	 * @param objects	변환할 object 객체 목록
	 * @return	생성된 DataSet 객체
	 */
    public static <T> List<Map<String, Object>> convertToDataSet(List<T> objects) {
		if (objects == null || objects.isEmpty()) {
            return Collections.emptyList();
        }
		
		List<Map<String, Object>> convertList = new ArrayList<>();
		
		for (T obj : objects) {
			convertList.add(convertToDataMap(obj));
		}
		
		return convertList;
	}
    
    /**
     * DataMap => VO 변환
     * 
     * @param <R>		변환할 타입
     * @param map		변환할 DataMap 데이터
     * @param clazz		변환할 타입 클래스
     * @return	생성된 VO 객체
     */
    public static <R, K, V> R convertValueObjectAtDataMap(Map<K, V> map, Class<R> clazz) {
    	Objects.requireNonNull(clazz, "Class cannot be null");
    	
    	// VO 객체 생성
    	R object = null;
    	
    	try {
    		object = clazz.getConstructor().newInstance();
    		
    		if (map == null || map.isEmpty()) {
                return object;
            }
    		
    		// 자바 Reflect 를 이용한 해당 object의 클래스 접근
        	Field[] fields = clazz.getDeclaredFields();

            for (Map.Entry<? super K, ? super V> entry : map.entrySet()) {
                for (Field field : fields) {
                	// private 변수 접근
        			field.setAccessible(true);
        			
        			// Map key 언더스코어에서 카멜케이스로 변환
        			String key = "";
        			Object value = null;
        			
                	if (entry.getKey() instanceof String) {
                		key = EgovStringUtil.convertToCamelCase((String) entry.getKey());
                	} else {
                		key = EgovStringUtil.convertToCamelCase(entry.getKey().toString());
                	}
                	
                	boolean isSameType = false;
                	boolean isSameName = key.equals(field.getName());
                	
                	if (entry.getValue() != null) {
                		value = entry.getValue();
//                		String typeName = field.getType().getName();		// < JDK 1.8 
                		String typeName = field.getGenericType().getTypeName();	// JDK 1.8 >=
                		// DB 칼럼중 Number인 경우 아래의 타입으로 설정될 수 있기 때문에
                    	// VO field 타입중 Integer 나 Long 등으로 체크.
                		String valueType = value.getClass().getName();
                    	if (valueType.equals("java.math.BigInteger") || valueType.equals("java.math.BigDecimal")) {
                    		isSameType = true;
                    		// 해당 key 와 field 명이 일치
                    		if (isSameName) {
                    			if (typeName.equals("java.lang.Integer")) {
                        			value = Integer.valueOf(value.toString());
                            	} else if (typeName.equals("int")) {
                            		value = Integer.valueOf(value.toString()).intValue();
                        		} else if (typeName.equals("java.lang.Long")) {
                        			value = Long.valueOf(value.toString());
                            	} else if (typeName.equals("long")) {
                            		value = Long.valueOf(value.toString()).longValue();
                            	} else {
                            		// 기타유형은 value 값을 String 형으로 변환
                            		value = value.toString();
                            	}
                    		}
                    	} else {
                    		if (!valueType.equals("java.lang.String")) {
                    			isSameType = value.getClass().equals(field.getType());
                    		} else {
                    			isSameType = true;
                    		}
                    		
                    		// 해당 key 와 field 명이 일치
                    		if (isSameName) {
                    			value = convertValueByTypeName(value, typeName);
                    		}
                    	}
                	}
                	
                    // 일치하는 Type 과 Key 가 발견되면 VO field 에 setting.
                    if (isSameType && isSameName) {
                        field.set(object, value);
                        break;
                    }
                }
            }
            
            return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    /**
     * DataSet => VO 목록 변환
     * 
     * @param <R>		변환할 타입
     * @param list		변환할 DataSet 데이터
     * @param clazz		변환할 타입 클래스
     * @return 생성된 VO 목록 객체
     */
    public static <R, K, V> List<R> convertValueObjectsAtDataSet(List<Map<K, V>> list, Class<R> clazz) {
    	Objects.requireNonNull(clazz, "Class cannot be null");
    	
    	if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
    	
        List<R> convertList = new ArrayList<>(list.size());

        for (Map<? super K, ? super V> map : list) {
            convertList.add(ConvertUtils.convertValueObjectAtDataMap(map, clazz));
        }
        
        return convertList;
    }
    
    /**
     * Type 명에 따른 Value 변환
     * 
     * @param value		원본 Object
     * @param typeName	Type 명 (Class or Primitive)
     * @return
     */
    private static Object convertValueByTypeName(Object value, String typeName) {
    	Objects.requireNonNull(typeName, "TypeName cannot be null");
    	
    	if (value == null) return null;
    	
    	if (typeName.equals("java.lang.String")) {
    		value = value.toString();
    	} else if (typeName.equals("java.lang.Integer")) {
    		try {
    			value = Integer.valueOf(value.toString());
    		} catch (NumberFormatException ex) {
    			value = Integer.valueOf(0);
    		}
    	} else if (typeName.equals("int")) {
    		int nVal = 0;
    		try {
    			nVal = Integer.valueOf(value.toString()).intValue();
    		} catch (NumberFormatException ex) {
    			nVal = Integer.valueOf(0).intValue();
    		}
    		value = nVal;
		} else if (typeName.equals("java.lang.Long")) {
			try {
    			value = Long.valueOf(value.toString());
    		} catch (NumberFormatException ex) {
    			value = Long.valueOf(0L);
    		}
    	} else if (typeName.equals("long")) {
    		long nVal = 0;
    		try {
    			nVal = Long.valueOf(value.toString()).longValue();
    		} catch (NumberFormatException ex) {
    			nVal = Long.valueOf(0).longValue();
    		}
    		value = nVal;
    	} else if (typeName.equals("java.lang.Float")) {
			try {
    			value = Float.valueOf(value.toString());
    		} catch (NumberFormatException ex) {
    			value = Float.valueOf(0);
    		}
    	} else if (typeName.equals("float")) {
    		float fVal = 0;
    		try {
    			fVal = Float.valueOf(value.toString()).floatValue();
    		} catch (NumberFormatException ex) {
    			fVal = Float.valueOf(0).floatValue();
    		}
    		value = fVal;
    	} else if (typeName.equals("java.lang.Double")) {
    		try {
    			value = Double.valueOf(value.toString());
    		} catch (NumberFormatException ex) {
    			value = Double.valueOf(0);
    		}
    	} else if (typeName.equals("double")) {
    		double dVal = 0;
    		try {
    			dVal = Double.valueOf(value.toString()).doubleValue();
    		} catch (NumberFormatException ex) {
    			dVal = Double.valueOf(0).doubleValue();
    		}
    		value = dVal;
    	} else if (typeName.equals("java.lang.Byte")) {
    		try {
    			value = Byte.valueOf(value.toString());
    		} catch (NumberFormatException ex) {
    			value = Byte.valueOf((byte) 0);
    		}
    	} else if (typeName.equals("byte")) {
    		byte bVal = 0;
    		try {
    			bVal = Byte.valueOf(value.toString()).byteValue();
    		} catch (NumberFormatException ex) {
    			bVal = Byte.valueOf((byte) 0).byteValue();
    		}
    		value = bVal;
    	} else if (typeName.equals("java.lang.Short")) {
    		try {
    			value = Short.valueOf(value.toString());
    		} catch (NumberFormatException ex) {
    			value = Short.valueOf((short) 0);
    		}
    	} else if (typeName.equals("short")) {
    		short sVal= 0;
    		try {
    			value = Short.valueOf(value.toString()).shortValue();
    		} catch (NumberFormatException ex) {
    			value = Short.valueOf((short) 0).shortValue();
    		}
    		value = sVal;
    	} else if (typeName.equals("java.lang.Boolean")) {
    		value = BooleanUtils.toBooleanObject(value.toString());
    	} else if (typeName.equals("boolean")) {
    		value = BooleanUtils.toBoolean(value.toString());
    	}
    	
    	return value;
    }
    
    /**
     * JSON 문자열 => Map 변환
     * 
     * @Method명   : jsonToMap
     * @param jsonString
     * @return 변환된 Map 객체
     */
    public static Map<String, Object> convertJsonStringToMap(String jsonString) {
    	ObjectMapper objectMapper = new ObjectMapper();
		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
		
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap = objectMapper.readValue(jsonString, typeRef);
		} catch (IOException e) {
			// do ignore
		}
		return resultMap;
    }
    
    /**
     * Map => JSON 문자열 변환
     * 
     * @param map
     * @return 변환된 JSON 문자열
     */
    public static <K, V> String convertMapToJsonString(Map<? super K, ? super V> map) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	String resultStr = new String();
    	try {
    		resultStr = objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// do ignore
		}
    	return resultStr;
    }
}
