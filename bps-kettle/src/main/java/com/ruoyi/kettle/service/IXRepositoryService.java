package com.ruoyi.kettle.service;

import java.util.List;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.kettle.domain.XRepository;
import com.ruoyi.kettle.repo.RepoTree;

/**
 * 资源库Service接口
 * 
 * @author kone
 * @date 2021-07-12
 */
public interface IXRepositoryService 
{
    /**
     * 查询资源库
     * 
     * @param id 资源库ID
     * @return 资源库
     */
    public XRepository selectXRepositoryById(Long id);

    /**
     * 查询资源库列表
     * 
     * @param xRepository 资源库
     * @return 资源库集合
     */
    public List<XRepository> selectXRepositoryList(XRepository xRepository);

    /**
     * 新增资源库
     * 
     * @param xRepository 资源库
     * @return 结果
     */
    public int insertXRepository(XRepository xRepository);

    /**
     * 修改资源库
     * 
     * @param xRepository 资源库
     * @return 结果
     */
    public int updateXRepository(XRepository xRepository);

    /**
     * 批量删除资源库
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteXRepositoryByIds(String ids);

    /**
     * 删除资源库信息
     * 
     * @param id 资源库ID
     * @return 结果
     */
    public int deleteXRepositoryById(Long id);

    List<RepoTree> selectRepoTree(Long id,String type);

    List<RepoTree> selectRepoRoot(XRepository repository);
}