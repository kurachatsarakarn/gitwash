package ac.th.ku.soa.washsystem.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ac.th.ku.soa.washsystem.repository.EmployeeRepository;
import ac.th.ku.soa.washsystem.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private EmployeeRepository empRep;
    
    @Autowired
    private UserRepository userRep;
    
    private String usernamereq;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        
        String path = request.getRequestURI(); // ดึง Path ของ Request

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	Collection<GrantedAuthority> authorities = new ArrayList<>();	
        	if(path.startsWith("/emp")) {
        			 usernamereq = empRep.findByuserName(username).getUserName();
        			 authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        		}

			if (jwtUtil.validateToken(token,usernamereq)) {}
				
				
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                		usernamereq, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        filterChain.doFilter(request, response);    
    }
}


