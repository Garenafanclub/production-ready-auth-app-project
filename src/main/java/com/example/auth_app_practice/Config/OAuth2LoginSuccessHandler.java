package com.example.auth_app_practice.Config;

import com.example.auth_app_practice.Model.Provider;
import com.example.auth_app_practice.Model.User;
import com.example.auth_app_practice.Repository.UserRepo;
import com.example.auth_app_practice.Services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1. Extract the Google User profile data
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userRepo.findByEmail(email).orElse(null);

        if(user == null)
        {
            // 3. User is brand new! Create them.
            // Since your User model requires a password, we generate a random secure UUID and hash it.
            // They will never use this to log in, because their Provider is explicitly set to GOOGLE.
            user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .provider(Provider.GOOGLE)
                    .enabled(true)
                    .build();
            user = userRepo.save(user);
        }

        // 4. Generate your custom JWT...
        String jwtToken = jwtService.generateToken(user);

//        // 5. Redirect the user back to your Frontend (e.g., React) with the token attached to the URL
//        String frontendUrl = "http://localhost:3000/oauth2/redirect?token=" + jwtToken;
//        getRedirectStrategy().sendRedirect(request,response,frontendUrl);
        // 5. SINCE WE HAVE NO FRONTEND: Just print the token directly to the web browser!
        response.setContentType("application/json");
        response.getWriter().write("{\n" +
                "  \"message\": \"Successfully logged in with Google!\",\n" +
                "  \"token\": \"" + jwtToken + "\"\n" +
                "}");
    }
}
