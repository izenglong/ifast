package com.ifast.api.shiro;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ifast.api.exception.IFastApiException;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月22日 | Aron</small>
 */
public class JWTAuthenticationFilter extends BasicHttpAuthenticationFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 判断是否为登录请求
     */
    @Override
    protected boolean isLoginAttempt(String authzHeader) {
    	return authzHeader!=null && authzHeader.length()>0;
    }
    
    @Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		return false;//已经在isAccessAllowed登录过了，不执行父类的登录操作（token不同）
	}

	/**
     * 这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 注解 @RequiresAuthentication
     * 缺点：不能够对GET,POST等请求进行分别过滤鉴权
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
        	String error = "访问非法";
            try {
            	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                String authorization = httpServletRequest.getHeader("Authorization");
                JWTAuthenticationTokenToken token = new JWTAuthenticationTokenToken(authorization);
                getSubject(request, response).login(token);
            	return true;
            } catch (IFastApiException | IncorrectCredentialsException | ExpiredCredentialsException e) {
            	error = e.getMessage();
            } catch (Exception e) {
            	log.info(e.getMessage());
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try(PrintWriter writer = response.getWriter()){
            	writer.write("{\"error\":\""+error+"\"}");
            }catch (Exception ex) {
            	log.warn(ex.getMessage());
            }
            log.info(error);
            return false;
        }
        return true;
    }

    /** 请求结束时登出，每次请求都重新鉴权登录 */
    @Override
	protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
    	getSubject(request, response).logout();
		super.cleanup(request, response, existing);
	}

	/**
     * 跨域处理
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

}
