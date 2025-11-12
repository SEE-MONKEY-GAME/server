package com.seemonkey.bananajump.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.seemonkey.bananajump.auth.AuthService;
import com.seemonkey.bananajump.common.exception.CustomException;
import com.seemonkey.bananajump.common.exception.ErrorType;
import com.seemonkey.bananajump.member.domain.Member;
import com.seemonkey.bananajump.member.repository.MemberRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class HashAuthFilter extends OncePerRequestFilter {

	private static final String SCHEME = "Hash ";
	private static final AntPathMatcher PATH = new AntPathMatcher();
	private final MemberRepository memberRepository;

	// 인증 제외 경로
	private static final List<String> EXCLUDE = List.of(
	);
	private final AuthService authService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String uri = request.getRequestURI();
		for (String p : EXCLUDE) {
			if (PATH.match(p, uri)) return true;
		}
		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain)
		throws ServletException, IOException {

		String authz = request.getHeader(HttpHeaders.AUTHORIZATION);

		// Authorization 없거나 다른 스킴이면 통과(익명)
		if (authz == null || !authz.startsWith(SCHEME)) {
			chain.doFilter(request, response);
			return;
		}

		String token = authz.substring(SCHEME.length()).trim();
		if (token.isEmpty()) {
			unauthorized(response, "Empty token");
			return;
		}

		try {
			// 1) 토큰 검증 + 사용자 로드
			Member member = authService.getOrCreateMemberBySocialId(token);

			Long memberId = member.getMemberId();

			// 예시) Authorization: Hash <token> -> token으로 memberId 조회 후 SecurityContext에 저장
			Authentication auth = new UsernamePasswordAuthenticationToken(memberId, null, Collections.emptyList());
			SecurityContextHolder.getContext().setAuthentication(auth);


			chain.doFilter(request, response);
		} catch (Exception e) {
			log.error("[AUTH] unexpected", e);
			unauthorized(response, "Unauthorized");
		}
	}

	private void unauthorized(HttpServletResponse res, String msg) throws IOException {
		SecurityContextHolder.clearContext();
		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		res.setContentType("application/json;charset=UTF-8");
		res.getWriter().write("{\"code\":\"AUTH-401\",\"message\":\"" + msg.replace("\"","\\\"") + "\"}");
	}
}
