package com.ruoyi.common.utils;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.reflect.ReflectUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字典工具类
 * 
 * @author ruoyi
 */
@Component
public class DictUtils
{
    /**
     * 分隔符
     */
    public static final String SEPARATOR = ",";
    public static final String DICT_LABEL = "dictLabel";
    public static final String DICT_VALUE = "dictValue";
    /**
     * 设置字典缓存
     * 
     * @param key 参数键
     * @param dictDatas 字典数据列表
     */
    public static void setDictCache(String key, List<SysDictData> dictDatas)
    {
        CacheUtils.put(getCacheName(), getCacheKey(key), dictDatas);
    }

    /**
     * 获取字典缓存
     * 
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    public static List<SysDictData> getDictCache(String key)
    {
        Object cacheObj = CacheUtils.get(getCacheName(), getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj))
        {
            return StringUtils.cast(cacheObj);
        }
        return null;
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * 
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue)
    {
        if (StringUtils.isEmpty(dictValue))
        {
            return StringUtils.EMPTY;
        }
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * 
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel)
    {
        if (StringUtils.isEmpty(dictLabel))
        {
            return StringUtils.EMPTY;
        }
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue, String separator)
    {
       return getDictElement(dictType, dictValue, separator, DICT_LABEL);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * 
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel, String separator)
    {
        return getDictElement(dictType, dictLabel, separator,DICT_VALUE);
    }


    /**
     * 根据字典类型和字典条件获取字典内容
     *
     * @param dictType 字典类型
     * @param dictCondition 字典条件（字典标签或字典值）
     * @param separator 分隔符
     * @return 字典内容
     */
    public static String getDictElement(String dictType, String dictCondition
            , String separator, String filedAttr) {
        // 获取字典缓存
        List<SysDictData> dictData = getDictCache(dictType);
        if (StringUtils.isNull(dictData)) {
            return StringUtils.EMPTY;
        }
		// 处理单个标签的情况
        if (!StringUtils.containsAny(dictCondition, separator)) {
            return getSingleDictElement(dictData, dictCondition, filedAttr);
        }
        // 处理多个标签的情况
        return getMultipleDictElements(dictData, dictCondition, separator, filedAttr);
    }
    /**
     * 获取字典元素
     *
     * @param dictData    字典数据列表
     * @param dictCondition 条件值
     * @param dictAttr     字典属性
     * @return 符合条件的字典属性值，未找到返回空字符串
     */
    private static String getDictElement(List<SysDictData> dictData, String dictCondition, String dictAttr) {
        // 遍历字典数据
        for (SysDictData dict : dictData) {
            // 判断需要转换的字典属性是DICT_LABEL还是DICT_VALUE
            String dictTransV = Objects.equals(DICT_LABEL, dictAttr) ? DICT_VALUE : DICT_LABEL;
            // 获取转换后的字典属性值
            String retValue = ReflectUtils.invokeGetter(dict, dictTransV);
            // 如果转换后的值和条件值相等，则返回所需的字典属性值
            if (Objects.equals(dictCondition, retValue)) {
                return ReflectUtils.invokeGetter(dict, dictAttr);
            }
        }
        return StringUtils.EMPTY; // 没有找到匹配的标签，返回空字符串
    }

    /**
     * 获取单个字典元素
     *
     * @param dictData     字典数据列表
     * @param dictCondition 条件值
     * @param dictAttr      字典属性
     * @return 符合条件的字典属性值
     */
    private static String getSingleDictElement(List<SysDictData> dictData, String dictCondition, String dictAttr) {
        // 如果字典属性是DICT_VALUE并且条件是数字，则直接返回条件
        if (Objects.equals(DICT_VALUE, dictAttr) && StringUtils.isNumeric(dictCondition)) {
            return dictCondition;
        }
        return getDictElement(dictData, dictCondition, dictAttr);
    }

    /**
     * 处理多个标签的情况
     *
     * @param dictData     字典数据列表
     * @param dictCondition 标签条件
     * @param separator    分隔符
     * @param dictAttr     字典属性
     * @return 拼接的字典值
     */
    private static String getMultipleDictElements(List<SysDictData> dictData, String dictCondition, String separator, String dictAttr) {
        // 分割条件值，得到多个条件
        String[] conditions = dictCondition.split(separator);

        // 使用Stream处理多个条件
        return Stream.of(conditions)
                .map(condition -> getDictElement(dictData, condition, dictAttr))
                .filter(StringUtils::isNotEmpty) // 过滤掉空值
                .collect(Collectors.joining(separator)); // 拼接结果
    }

    /**
     * 根据字典类型获取字典所有值
     *
     * @param dictType 字典类型
     * @return 字典值
     */
    public static String getDictValues(String dictType)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas))
        {
            return StringUtils.EMPTY;
        }
        for (SysDictData dict : datas)
        {
            propertyString.append(dict.getDictValue()).append(SEPARATOR);
        }
        return StringUtils.stripEnd(propertyString.toString(), SEPARATOR);
    }

    /**
     * 根据字典类型获取字典所有标签
     *
     * @param dictType 字典类型
     * @return 字典值
     */
    public static String getDictLabels(String dictType)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas))
        {
            return StringUtils.EMPTY;
        }
        for (SysDictData dict : datas)
        {
            propertyString.append(dict.getDictLabel()).append(SEPARATOR);
        }
        return StringUtils.stripEnd(propertyString.toString(), SEPARATOR);
    }

    /**
     * 删除指定字典缓存
     * 
     * @param key 字典键
     */
    public static void removeDictCache(String key)
    {
        CacheUtils.remove(getCacheName(), getCacheKey(key));
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache()
    {
        CacheUtils.removeAll(getCacheName());
    }

    /**
     * 获取cache name
     * 
     * @return 缓存名
     */
    public static String getCacheName()
    {
        return Constants.SYS_DICT_CACHE;
    }

    /**
     * 设置cache key
     * 
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey)
    {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
