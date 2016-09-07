package com.piksel.secure;

import com.piksel.annotations.PublicResource;
import org.reflections.Reflections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    private static Set<String> nonAuthenticationWhiteListSet = new TreeSet<>();

    static {
        Reflections reflections = new Reflections("com.piksel.resources");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(PublicResource.class, false);
        for (Class<?> annotatedClass : annotated) {
            if (!annotatedClass.isAnnotationPresent(Path.class)) {
                continue;
            }
            String classPath = annotatedClass.getAnnotation(Path.class).value();

            for (Method method : annotatedClass.getDeclaredMethods()) {
                String fullPath = classPath;
                if (method.isAnnotationPresent(GET.class)
                        || method.isAnnotationPresent(POST.class)
                        || method.isAnnotationPresent(PUT.class)
                        || method.isAnnotationPresent(DELETE.class)) {
                    if (method.isAnnotationPresent(Path.class)) {
                        fullPath += method.getDeclaredAnnotation(Path.class).value();
                    }

                    fullPath = fullPath.replaceAll("\\{.*\\}", ".+");
                    nonAuthenticationWhiteListSet.add(fullPath);
                }
            }
        }
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getPathInfo();

        if (isPathWhiteListedForAuthenticationPassThrough(path)) {
            chain.doFilter(request, response);
            return;
        }

        @SuppressWarnings("unchecked")
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();

        for (Cookie cookie :
                cookies) {
            if (cookie.getName().equals("token")) {

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ADMIN"));

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test", "test");
                token.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
                Authentication authentication = new UsernamePasswordAuthenticationToken("test", "test", authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                chain.doFilter(request, response);

            } else {
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

    }


    private boolean isPathWhiteListedForAuthenticationPassThrough(String path) {
        for (String regex : nonAuthenticationWhiteListSet) {
            if (path.matches(regex)) {
                return true;
            }
        }

        return false;
    }


}
