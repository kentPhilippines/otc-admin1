package com.ruoyi.bps.service.impl;

import com.ruoyi.bps.mapper.TopgpDdlMapper;
import com.ruoyi.bps.service.TopgpDdlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@DataSource(value = DataSourceType.SLAVE)
//@DataSource(value = DataSourceType.TOPTESTDSREPORT)
public class TopgpDdlServiceImpl implements TopgpDdlService {
    @Autowired
    private TopgpDdlMapper topgpDdlMapper;

    //修改表名
    public  int alterTableName(String originalTableName, String newTableName)
    {
        return topgpDdlMapper.alterTableName(originalTableName,newTableName);
    }

    // truncate指定数据库表的数据
    public int truncateTable(String tableName){
        return topgpDdlMapper.truncateTable(tableName);
    }

    //drop 指定定数据库表
    public int dropTable(String tableName){
        return topgpDdlMapper.dropTable(tableName);
    }


    //根据传入的表明，创建新的表并且将原表的数据插入到新的Occur表中
    public void copyTable(String newTableName,String originalTableName){
        return ;
    }

    //统计某张表中的总数据条数
    public int getRecordCount(String tableName){
        return topgpDdlMapper.getRecordCount(tableName);
    }

    //从指定数据库中，查询是否存在某张表
    public int isTableInDb(String dataBaseName, String tableName){
        return topgpDdlMapper.isTableInDb(dataBaseName,tableName);
    }
}