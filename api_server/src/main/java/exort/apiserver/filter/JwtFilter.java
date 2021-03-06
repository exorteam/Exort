package exort.apiserver.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exort.api.http.common.errorhandler.ApiError;
import org.springframework.web.filter.GenericFilterBean;

import exort.apiserver.component.JwtResolver;
import exort.apiserver.service.AuthService.AuthResponse;

public class JwtFilter extends GenericFilterBean {

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");

        // If the Http request is OPTIONS then just return the status code 200
        // which is HttpServletResponse.SC_OK in this code
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        // Except OPTIONS, other request should be checked by JWT
        else {
            // Check the authorization, check if the token is started by "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                // throw new ServletException("Missing or invalid Authorization header");
                request.setAttribute("id", null);
            } else {

                // Then get the JWT token from authorization
                final String token = authHeader.substring(7);
                final AuthResponse authRes = JwtResolver.parseToken(token);

                if (authRes == null) {
                    throw new ApiError(401, "badToken", "Token invalid or expired.");
                }

                request.setAttribute("id", authRes.getId());
            }
        }
        chain.doFilter(req, res);
    }
}

