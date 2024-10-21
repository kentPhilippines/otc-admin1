package com.ruoyi.common.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据源
 * 
 * @author ruoyi
 */
public enum DataSourceType
{
    /**
     * 主库
     */
    MASTER,

    /**
     * 从库
     */
    SLAVE;

    private static final Set<String> DATA_SOURCE_TYPE_SET;

    static {
        DATA_SOURCE_TYPE_SET = Arrays.stream(DataSourceType.values())
                .map(DataSourceType::name).collect(Collectors.toSet());
    }

    public static boolean contains(String sourceType){
        return DATA_SOURCE_TYPE_SET.contains(sourceType);
    }
}
