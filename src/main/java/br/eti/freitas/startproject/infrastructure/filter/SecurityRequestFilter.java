package br.eti.freitas.startproject.infrastructure.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.eti.freitas.startproject.infrastructure.constant.SecurityConstant;
import br.eti.freitas.startproject.infrastructure.service.impl.SecurityUserDetailsServiceImpl;
import br.eti.freitas.startproject.infrastructure.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class SecurityRequestFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityRequestFilter.class);

	@Autowired
	private SecurityUserDetailsServiceImpl securityUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		try {

			String jwt = request.getHeader(SecurityConstant.SECURITY_JWT_HEADER_AUTHENTICATION);

			if (jwt != null && jwt.startsWith(SecurityConstant.SECURITY_JWT_TOKEN_PREFIX)) {

				jwt = jwt.replace(SecurityConstant.SECURITY_JWT_TOKEN_PREFIX, "");
				String username = jwtUtil.extractUsername(jwt);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					UserDetails userDetails = securityUserDetailsService.loadUserByUsername(username);

					if (jwtUtil.validateToken(jwt, userDetails)) {

						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtUtil
								.getAuthenticationToken(jwt, SecurityContextHolder.getContext().getAuthentication(),
										userDetails);

						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
						request.setAttribute(SecurityConstant.USER, username);
					}
				}
			}
		} catch (SignatureException e) {
			LOG.error("SecurityRequestFilter: Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			LOG.error("SecurityRequestFilter: Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			LOG.error("SecurityRequestFilter: JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			LOG.error("SecurityRequestFilter: JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			LOG.error("SecurityRequestFilter: JWT claims string is empty: {}", e.getMessage());
		} finally {
			chain.doFilter(request, response);
		}
	}

}
