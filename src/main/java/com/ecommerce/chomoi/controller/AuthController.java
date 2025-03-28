package com.ecommerce.chomoi.controller;

import com.ecommerce.chomoi.dto.api.ApiResponse;
import com.ecommerce.chomoi.dto.auth.*;
import com.ecommerce.chomoi.dto.shop.ShopResponse;
import com.ecommerce.chomoi.enums.Role;
import com.ecommerce.chomoi.security.SecurityUtil;
import com.ecommerce.chomoi.service.AuthService;
import com.ecommerce.chomoi.service.EmailService;
import com.ecommerce.chomoi.util.CodeUtil;
import com.ecommerce.chomoi.util.CommonUtil;
import com.ecommerce.chomoi.util.jwt.BaseJWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${app.clientReceiveTokensPath}")
    private String clientReceiveTokensPath;
    @Value("${app.codeUtil.timeToLive}")
    private int timeToLive;
    private final AuthService authService;
    private final EmailService emailService;
    private final CodeUtil<AuthRegisterRequest> codeUtil;
    private final CodeUtil<String> forgotPasswordCodeUtil;
    private final SecurityUtil securityUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid AuthRegisterRequest request) {
        authService.register(request);
        String verificationCode = UUID.randomUUID().toString();
        codeUtil.save(verificationCode, request, this.timeToLive);
        String pathEmailTemplateVerifyRegister = "src/main/resources/HTMLTemplates/email_template_verify_register.html";
        emailService.sendEmailToVerifyRegister(request.getEmail(), verificationCode, pathEmailTemplateVerifyRegister);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("auth-s-01")
                .message("Request register successfully, check your email")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/register/verify/{verificationCode}")
    public RedirectView verifyRegister(@PathVariable String verificationCode) {
        AuthRegisterRequest request = codeUtil.get(verificationCode);
        AuthResponse authResponse = authService.verifyRegister(request);
        codeUtil.remove(verificationCode);
        String pathEmailTemplateWelcome = "src/main/resources/HTMLTemplates/email_template_welcome.html";
        emailService.sendEmailToWelcome(request.getEmail(), pathEmailTemplateWelcome);
        String redirectUrl = UriComponentsBuilder.fromUriString(clientReceiveTokensPath)
                .queryParam("accessToken", authResponse.getAccessToken())
                .queryParam("refreshToken", authResponse.getRefreshToken())
                .toUriString();
        return new RedirectView(redirectUrl);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid AuthLoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .code("auth-s-03")
                .message("Login successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody @Valid AuthRefreshTokenRequest request) {
        AuthResponse authResponse = authService.refreshToken(request);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .code("auth-s-04")
                .message("Refresh new access token successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logOut(@RequestBody @Valid AuthLogOutRequest request) {
        authService.logOut(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("auth-s-05")
                .message("Log out successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> logOut(@RequestBody @Valid AuthChangePasswordRequest request) {
        String userId = BaseJWTUtil.getPayload(SecurityContextHolder.getContext()).getId();
        authService.changePassword(userId, request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("auth-s-06")
                .message("Password changed successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/login/oauth2/success")
    public RedirectView loginOAuth2Success(@AuthenticationPrincipal OAuth2User oAuth2User) {
        AuthResponse authResponse = authService.loginOAuth2Success(oAuth2User);
        String redirectUrl = UriComponentsBuilder.fromUriString(clientReceiveTokensPath)
                .queryParam("accessToken", authResponse.getAccessToken())
                .queryParam("refreshToken", authResponse.getRefreshToken())
                .toUriString();
        return new RedirectView(redirectUrl);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forGotPassword(@RequestBody AuthForgotPasswordRequest request) {
        authService.forgotPassword(request);
        String verificationCode = CommonUtil.generateVerificationCode();
        forgotPasswordCodeUtil.save(CommonUtil.getForgotPasswordKey(verificationCode), request.getEmail(), 1);
        String pathEmailTemplateVerifyForgotPasswordCode = "src/main/resources/HTMLTemplates/email_template_verify_forgot_password_code.html";
        emailService.sendEmailToVerifyForgotPassword(request.getEmail(), verificationCode, pathEmailTemplateVerifyForgotPasswordCode);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code("auth-s-08")
                .message("Request to get new password successfully, please check your email")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/forgot-password/verify")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyForgotPassword(@RequestBody @Valid AuthVerifyForgotPasswordRequest request) {
        String email = forgotPasswordCodeUtil.get(CommonUtil.getForgotPasswordKey(request.getCode()));
        AuthResponse authResponse = authService.verifyForgotPassword(email, request);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .data(authResponse)
                .code("auth-s-09")
                .message("Verify forgot password successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<AuthAccountInfoResponse>> getInfo() {
        String accountId = securityUtil.getAccountId();
        ApiResponse<AuthAccountInfoResponse> apiResponse = ApiResponse.<AuthAccountInfoResponse>builder()
                .data(authService.getAccountInfo(accountId))
                .code("auth-s-10")
                .message("Get user info successfully")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/upgrade-to-shop")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<ShopResponse>> upgradeToShop(@Valid @RequestBody AuthUpgradeToShop upgradeToShopDto) {
        ShopResponse shop = authService.upgradeShop(upgradeToShopDto);
        ApiResponse<ShopResponse> response = ApiResponse.<ShopResponse>builder()
                .code("auth-s-11")
                .message("Upgrade to shop successfully")
                .data(shop)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/roles")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Set<Role>>> getRoles() {
        Set<Role> roles = securityUtil.getAccount().getRoles();
        ApiResponse<Set<Role>> apiResponse = ApiResponse.<Set<Role>>builder()
                .code("auth-s-11")
                .message("Get roles successfully")
                .data(roles)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/new-tokens")
    public ResponseEntity<ApiResponse<AuthResponse>> newTokens() {
        AuthResponse authResponse = authService.newTokens();
        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .code("auth-s-12")
                .message("Get new tokens")
                .data(authResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}

