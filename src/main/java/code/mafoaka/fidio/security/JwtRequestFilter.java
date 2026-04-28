package code.mafoaka.fidio.security;

import code.mafoaka.fidio.repository.ElectionRepository;
import code.mafoaka.fidio.repository.entity.ElectionEntity;
import code.mafoaka.fidio.service.BlindSignatureService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final BlindSignatureService blindSignatureService;
  private final ElectionRepository electionRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null) {
      if (requestTokenHeader.startsWith("Bearer ")) {
        jwtToken = requestTokenHeader.substring(7);
        try {
          username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (Exception e) {
          logger.error("Unable to get JWT Token or JWT Token has expired");
        }
      } else if (requestTokenHeader.startsWith("Blind ")) {
        String blindAuth = requestTokenHeader.substring(6);
        String[] parts = blindAuth.split(":");
        if (parts.length == 3) {
          String electionId = parts[0];
          String message = parts[1];
          String signature = parts[2];

          try {
            ElectionEntity election =
                electionRepository.findById(UUID.fromString(electionId)).orElseThrow();
            if (blindSignatureService.verifySignature(election, message, signature)) {
              BlindAuthenticationToken auth =
                  new BlindAuthenticationToken(
                      electionId,
                      message,
                      signature,
                      List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS_VOTER")));
              SecurityContextHolder.getContext().setAuthentication(auth);
            }
          } catch (Exception e) {
            logger.error("Invalid Blind signature or electionId");
          }
        }
      }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      if (jwtTokenUtil.validateToken(jwtToken, username)) {
        String role = jwtTokenUtil.getRoleFromToken(jwtToken);
        List<SimpleGrantedAuthority> authorities =
            role != null ? List.of(new SimpleGrantedAuthority("ROLE_" + role)) : List.of();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(username, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);
  }
}
