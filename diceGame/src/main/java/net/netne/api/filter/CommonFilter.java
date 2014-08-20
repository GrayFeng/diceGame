package net.netne.api.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.AESEncrypter;
import net.netne.common.uitls.ResultUtil;

import org.apache.commons.lang.StringUtils;

public class CommonFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String uid = req.getParameter("uid");
		String path = req.getRequestURI();
		Result result = null;
		if(path != null){
			if(path.contains("api/login") || path.contains("api/reg")){
				if(StringUtils.isEmpty(uid) || !MemberCache.getInstance().isHave(uid)){
					result = new Result(EEchoCode.ERROR.getCode(),"缺少UID认证信息");
				}
			}else if(!path.contains("api/start") && !path.contains("api/img")){
				if(StringUtils.isEmpty(uid) || !MemberCache.getInstance().isLogin(uid)){
					result = new Result(EEchoCode.ERROR.getCode(),"用户未登录或缺少UID认证信息");
				}
			}
		}
		if(result != null){
			resp.setContentType("text/plain;charset=UTF-8");  
			OutputStream os = resp.getOutputStream();
			os.write(ResultUtil.getJsonString(result).getBytes());
			os.flush();
			os.close();
			resp.flushBuffer();
			return;
		}
		if(FilteredRequest.isDecryption){
			chain.doFilter(new FilteredRequest(request), response);
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}

class FilteredRequest extends HttpServletRequestWrapper {

    private static final String queryId = "params";
    
    public static final boolean isDecryption = false;

    public FilteredRequest(ServletRequest request) {
        super((HttpServletRequest) request);
    }

    public String getParameter(String paramName) {
        String value = super.getParameter(paramName);
        if (queryId.equals(paramName) && value != null) {
            if (isDecryption) {
                value = new AESEncrypter().decrypt(value);
            }
        }
        return value;
    }

    public String[] getParameterValues(String paramName) {
        String values[] = super.getParameterValues(paramName);
        if (queryId.equals(paramName) && values != null) {
            if (isDecryption) {
                for (int index = 0; index < values.length; index++) {
                    values[index] = new AESEncrypter().decrypt(values[index]);
                }
            }
        }
        return values;
    }
}
