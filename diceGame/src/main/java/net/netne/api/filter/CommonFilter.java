package net.netne.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import net.netne.common.uitls.AESEncrypter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonFilter implements Filter{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(AESEncrypter.isDecryption){
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
	
	private Logger logger = LoggerFactory.getLogger(FilteredRequest.class);

    private static final String queryId = "params";
    
    public FilteredRequest(ServletRequest request) {
        super((HttpServletRequest) request);
    }

    public String getParameter(String paramName) {
        String value = super.getParameter(paramName);
        if (queryId.equals(paramName) && value != null) {
            if (AESEncrypter.isDecryption) {
                value = new AESEncrypter().decrypt(value);
                logger.info("api-rev:" + value);
            }
        }
        return value;
    }

    public String[] getParameterValues(String paramName) {
        String values[] = super.getParameterValues(paramName);
        if (queryId.equals(paramName) && values != null) {
            if (AESEncrypter.isDecryption) {
                for (int index = 0; index < values.length; index++) {
                    values[index] = new AESEncrypter().decrypt(values[index]);
                    logger.info("api-rev:" + values[index]);
                }
            }
        }
        return values;
    }
}
