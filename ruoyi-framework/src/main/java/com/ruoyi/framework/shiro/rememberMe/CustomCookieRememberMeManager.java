package com.ruoyi.framework.shiro.rememberMe;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.shiro.service.SysLoginService;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义CookieRememberMeManager，干预序列化cookie[rememberMe]时的流程
 *
 * @author XuYang
 */
public class CustomCookieRememberMeManager extends CookieRememberMeManager
{

    private final SysLoginService sysLoginService = SpringUtils.getBean(SysLoginService.class);

    /**
     * 序列化 cookie[rememberMe]的逻辑
     * 序列化的时候去掉角色的Permissions权限字符串
     * 因为accountPrincipals中的Principals对象和Subject中的Principals对象是同一个，
     * 如果只清除权限不恢复权限会导致正常登录后的subject中的role也没有权限字符，所以还得恢复一下。
     */
    @Override
    protected void rememberIdentity(Subject subject, PrincipalCollection accountPrincipals)
    {
        // 创建一个临时对象来存储原始的权限信息
        Map<SysRole, Set<String>> originalPermissions = new HashMap<>();
        // 清除权限信息
        for (Object principal : accountPrincipals)
        {
            if (principal instanceof SysUser)
            {
                List<SysRole> roles = ((SysUser) principal).getRoles();
                for (SysRole role : roles)
                {
                    originalPermissions.put(role, role.getPermissions());
                    role.setPermissions(null);
                }
            }
        }
        // 将accountPrincipals转换为字节数组，后续流程会再转base64
        byte[] bytes = convertPrincipalsToBytes(accountPrincipals);
        // 恢复权限信息
        for (Object principal : accountPrincipals)
        {
            if (principal instanceof SysUser)
            {
                List<SysRole> roles = ((SysUser) principal).getRoles();
                for (SysRole role : roles)
                {
                    role.setPermissions(originalPermissions.get(role));
                }
            }
        }
        rememberSerializedIdentity(subject, bytes);
    }

    /**
     * 反序列化 cookie[rememberMe]的逻辑
     * 本方法只会在【cookie中只携带了rememberMe，没有携带JSESSIONID】的情况下执行一次，
     * 执行后就会生成新的JSESSIONID返回给客户端，后续就不会再执行本方法。
     */
    @Override
    public PrincipalCollection getRememberedPrincipals(SubjectContext subjectContext)
    {
        PrincipalCollection principals = super.getRememberedPrincipals(subjectContext);
        if (principals == null || principals.isEmpty())
        {
            return principals;
        }
        // 序列化的时候把权限字符串去掉了，这里存上
        for (Object principal : principals)
        {
            if (principal instanceof SysUser)
            {
                sysLoginService.setRolePermission((SysUser) principal);
            }
        }
        return principals;
    }
}
