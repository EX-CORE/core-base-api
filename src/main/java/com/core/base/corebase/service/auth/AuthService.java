package com.core.base.corebase.service.auth;

import com.core.base.corebase.client.GoogleAuthClient;
import com.core.base.corebase.client.GoogleInfoClient;
import com.core.base.corebase.client.dto.AuthDto;
import com.core.base.corebase.client.dto.GoogleDto;
import com.core.base.corebase.common.code.ErrorCode;
import com.core.base.corebase.common.exception.BaseException;
import com.core.base.corebase.config.GoogleProperties;
import com.core.base.corebase.domain.user.Account;
import com.core.base.corebase.domain.user.User;
import com.core.base.corebase.domain.user.code.UserState;
import com.core.base.corebase.repository.AccountRepository;
import com.core.base.corebase.repository.MemberRepository;
import com.core.base.corebase.repository.UserRepository;
import com.core.base.corebase.support.JwtProvider;
import io.jsonwebtoken.Claims;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final GoogleAuthClient googleAuthClient;
    private final GoogleInfoClient googleInfoClient;
    private final JwtProvider jwtProvider;
    private final GoogleProperties googleProperties;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public AuthService(GoogleAuthClient googleAuthClient,
                     GoogleInfoClient googleInfoClient,
                     JwtProvider jwtProvider,
                     GoogleProperties googleProperties,
                     AccountRepository accountRepository,
                     UserRepository userRepository,
                     MemberRepository memberRepository) {
        this.googleAuthClient = googleAuthClient;
        this.googleInfoClient = googleInfoClient;
        this.jwtProvider = jwtProvider;
        this.googleProperties = googleProperties;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }

    public String getUserGoogleCode() {
        return "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + googleProperties.getClientId() +
                "&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile" +
                "&response_type=code&access_type=offline" +
                "&state=state_parameter_passthrough_value&include_granted_scopes=true" +
                "&redirect_uri=" + googleProperties.getRedirectUrl() +
                "&prompt=consent";
    }

    @Transactional
    public AuthDto.LoginRes login(String code, String type) {
        String resultRedirectUrl = googleProperties.getRedirectUrl();
        if ("LOCAL".equals(type)) {
            resultRedirectUrl = "http://localhost:3000/login";
        }

        GoogleDto.GoogleTokenReq tokenReq = new GoogleDto.GoogleTokenReq(
                code,
                googleProperties.getClientId(),
                googleProperties.getClientSecret(),
                resultRedirectUrl,
                "authorization_code"
        );

        GoogleDto.GoogleTokenRes accessTokenResponse = googleAuthClient.getTokenByCode(tokenReq);
        GoogleDto.GoogleInfoRes googleInfoResponse = googleInfoClient.getInfo("Bearer " + accessTokenResponse.getAccessToken());

        User user = userRepository.findByEmail(googleInfoResponse.getEmail())
                .orElseGet(() -> {
                    User newUser = userRepository.save(new User(
                            googleInfoResponse.getName(),
                            googleInfoResponse.getEmail(),
                            googleInfoResponse.getPicture()
                    ));
                    accountRepository.save(new Account(
                            accessTokenResponse.getRefreshToken(),
                            UserState.ACTIVE,
                            newUser
                    ));

                    memberRepository.findByEmailAndUserIsNull(newUser.getEmail())
                            .forEach(member -> member.updateUser(newUser));

                    return newUser;
                });

        return new AuthDto.LoginRes(
                jwtProvider.generateAccessToken(user.getUid()),
                jwtProvider.generateRefreshToken(user.getUid())
        );
    }

    @Transactional(readOnly = true)
    public AuthDto.TokenRefreshRes tokenRefresh(AuthDto.TokenRefreshReq req) {
        Claims tokenInfo = jwtProvider.getBody(req.getRefreshToken());
        if (tokenInfo == null || !jwtProvider.isRefresh(tokenInfo)) {
            throw new BaseException(ErrorCode.INVALID_TOKEN);
        }

        UUID userId = jwtProvider.getId(tokenInfo);
        if (!accountRepository.existsByUid(userId)) {
            throw new BaseException(ErrorCode.INVALID_TOKEN);
        }

        return new AuthDto.TokenRefreshRes(jwtProvider.generateAccessToken(userId));
    }
}
