package org.reindeer.simpleblog.interceptor;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * Created by fzy on 2014/6/29.
 */
public class ProcessTimeInterceptor implements HandlerInterceptor {

    private NamedThreadLocal<Long> preTimeThreadLocal = new NamedThreadLocal<>("processTime-preTime");

    private NamedThreadLocal<Long> postTimeThreadLocal = new NamedThreadLocal<>("processTime-postTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long beginTime = System.nanoTime();
        preTimeThreadLocal.set(beginTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long beginTime = System.nanoTime();
        postTimeThreadLocal.set(beginTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long completeTime = System.nanoTime();
        Long preTime = preTimeThreadLocal.get();
        Long postTime = postTimeThreadLocal.get();
        if (preTime == null || postTime == null) {
            return;
        }
        preTimeThreadLocal.remove();
        postTimeThreadLocal.remove();
        Writer writer = response.getWriter();
        writer.write("<!--controllerTime:" + (float) (postTime - preTime) / 1000000 + "ms -->");
        writer.write("<!--viewTime:" + (float) (completeTime - postTime) / 1000000 + "ms -->");
        writer.write("<!--totalTime:" + (float) (completeTime - preTime) / 1000000 + "ms -->");
        writer.flush();
    }
}
