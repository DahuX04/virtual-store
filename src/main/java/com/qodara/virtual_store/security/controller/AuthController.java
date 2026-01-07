package com.qodara.virtual_store.security.controller;

import com.qodara.virtual_store.security.model.request.LoginRequestDto;
import com.qodara.virtual_store.security.model.request.PasswordRequestDto;
import com.qodara.virtual_store.security.model.request.RegisterRequestDto;
import com.qodara.virtual_store.security.model.response.RegisteredUserResponseDto;
import com.qodara.virtual_store.security.model.response.TokenResponseDto;
import com.qodara.virtual_store.security.service.IAuthService;
import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth")
@RequestMapping("/api/v1/auth")
@RestController
@SecurityRequirements
public class AuthController {
    private final IAuthService service;


    public AuthController(IAuthService service) {
        this.service = service;
    }

    //@SecurityRequirements //desactiva la seguridad para este controlador (swagger)
    @Operation(summary = "Inicia sesión")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        var res = service.login(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @Operation(summary = "Registra un nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisteredUserResponseDto>> registerUser(@Valid @RequestBody RegisterRequestDto request) {
        var res = service.registerUser(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Cambia la contraseña de un nuevo usuario")
    @PutMapping("/change-password/{id}")
    public ResponseEntity<ApiResponse<RegisteredUserResponseDto>> updatePassword(@PathVariable int id, @Valid @RequestBody PasswordRequestDto request) {
        var res = service.updatePassword(id, request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
