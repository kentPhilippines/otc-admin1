package com.ruoyi.common.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * 数据源切换处理
 * 
 * @author ruoyi
 */
public class DynamicDataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<DataSourceTypeStruct> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源的变量
     */
    public static void setDataSourceType(String dsType)
    {
        DataSourceTypeStruct dataSourceTypeStruct = Optional.ofNullable(CONTEXT_HOLDER.get()).orElse(new DataSourceTypeStruct(DataSourceType.MASTER.name()));//最顶层默认为 MASTER 数据源
        dataSourceTypeStruct.setDsType(dsType);
        log.info("切换到{}数据源,当前:{}", dsType,dataSourceTypeStruct);
        CONTEXT_HOLDER.set(dataSourceTypeStruct);
    }

    /**
     * 获得数据源的变量
     */
    public static String getDataSourceType()
    {
        DataSourceTypeStruct dataSourceTypeStruct = Optional.ofNullable(CONTEXT_HOLDER.get()).orElse(new DataSourceTypeStruct(DataSourceType.MASTER.name()));//最顶层默认为 MASTER 数据源
        return dataSourceTypeStruct.getDsType();
    }

    /**
     * 清空数据源变量
     */
    public static void clearDataSourceType()
    {
        DataSourceTypeStruct dataSourceTypeStruct = Optional.ofNullable(CONTEXT_HOLDER.get()).orElse(new DataSourceTypeStruct(DataSourceType.MASTER.name()));
        dataSourceTypeStruct.clearDsType();
        log.info("清理一层数据源,当前:{}", dataSourceTypeStruct);
    }
    /**
    * 创建层级结构 保存数据源
    */
    static class DataSourceTypeStruct{
        private final String dsType;
        private DataSourceTypeStruct next;
        protected DataSourceTypeStruct(String dsType){
            this.dsType = dsType;
        }
        public String getDsType(){//获得最底层DataSourceTypeStruct的dsType
            if(null!=next){
                return next.getDsType();
            }
            return dsType;
        }
        public void setDsType(String dsType){//设置最底层DataSourceTypeStruct的dsType
            if(null!=next){
                next.setDsType(dsType);
                return;
            }
            next = new DataSourceTypeStruct(dsType);
        }
        public void clearDsType(){//清理最底层的DataSourceTypeStruct 层级-1
            if(null!=next){
                if(null==next.next){
                    next=null;
                }else{
                    next.clearDsType();
                }
            }
        }
        @Override
        public String toString(){
            return dsType+(null==next?"":("->"+next));
        }
    }
}
