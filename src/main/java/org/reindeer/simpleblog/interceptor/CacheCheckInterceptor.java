package org.reindeer.simpleblog.interceptor;

import org.reindeer.simpleblog.core.repositories.BlogRepository;
import org.reindeer.simpleblog.core.util.SpringUtil;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fzy on 2014/7/12.
 */
public class CacheCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        WebRequest webRequest = new ServletWebRequest(request, response);
        long lastModified = SpringUtil.getBean(BlogRepository.class).getLastModified();
        boolean result = webRequest.checkNotModified(lastModified);
        return !result;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
