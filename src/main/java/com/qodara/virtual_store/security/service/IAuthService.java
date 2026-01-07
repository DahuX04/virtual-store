package com.qodara.virtual_store.security.service;

import com.qodara.virtual_store.security.model.request.LoginRequestDto;
import com.qodara.virtual_store.security.model.request.PasswordRequestDto;
import com.qodara.virtual_store.security.model.request.RegisterRequestDto;
import com.qodara.virtual_store.security.model.response.RegisteredUserResponseDto;
import com.qodara.virtual_store.security.model.response.TokenResponseDto;
import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;

public interface IAuthService {
    ApiResponse<RegisteredUserResponseDto> registerUser(RegisterRequestDto request);

    ApiResponse<RegisteredUserResponseDto> updatePassword(int id, PasswordRequestDto request);

    ApiResponse<TokenResponseDto> login(LoginRequestDto request);

}
