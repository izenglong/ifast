package com.ifast.common.base;

import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.utils.HttpContextUtils;
import com.ifast.common.utils.Result;
import com.ifast.common.utils.ShiroUtils;
import com.ifast.sys.domain.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * </pre>
 * 
 * <small> 2018年2月25日 | Aron</small>
 */
@Slf4j
public abstract class BaseController {

    // 用户信息相关

    public UserDO getUser() {
        return ShiroUtils.getSysUser();
    }

    public Long getUserId() {
        return getUser().getId();
    }

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    // 参数请求、分页

    /**
     * <pre>
     * 自动获取分页参数，返回分页对象page
     * </pre>
     * 
     * <small> 2018年4月15日 | Aron</small>
     * 
     * @param e
     * @return
     */
    public <E> Page<E> getPage(Class<E> e) {
        int pageNumber = getParaToInt("pageNumber", 1);
        int pageSize = getParaToInt("pageSize", 10);
        Page<E> page = new Page<>(pageNumber, pageSize);
        //支持sort、order参数
        HttpServletRequest request = getRequest();
        String sort = request.getParameter("sort");
        if(StringUtils.isNotBlank(sort)) {
        	page.setOrderByField(sort);
        	String order = request.getParameter("order");
        	if(StringUtils.isNotBlank(order)){
        	    page.setAsc("asc".equalsIgnoreCase(order));
            }
        }
        return page;
    }

    private int getParaToInt(String key, int defaultValue) {
        String pageNumber = HttpContextUtils.getHttpServletRequest().getParameter(key);
        if (StringUtils.isBlank(pageNumber)) {
            return defaultValue;
        }
        return Integer.parseInt(pageNumber);
    }

    // Servlet

    protected HttpServletRequest getRequest(){
        return HttpContextUtils.getHttpServletRequest();
    }

    protected HttpServletResponse getResponse(){
        return HttpContextUtils.getHttpServletResponse();
    }

    // 响应结果

    protected Result<String> fail(){
        return Result.fail();
    }

    protected Result<String> success(){
        return success(null);
    }

    protected <T> Result<T> success(T t){
        return Result.ok(t);
    }


}