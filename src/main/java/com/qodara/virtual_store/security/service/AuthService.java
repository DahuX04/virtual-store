package com.qodara.virtual_store.security.service;


import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Account;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.AccountRepository;
import com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories.RoleRepository;
import com.qodara.virtual_store.security.jwt.provider.JwtTokenProvider;
import com.qodara.virtual_store.security.model.request.LoginRequestDto;
import com.qodara.virtual_store.security.model.request.PasswordRequestDto;
import com.qodara.virtual_store.security.model.request.RegisterRequestDto;
import com.qodara.virtual_store.security.model.response.RegisteredUserResponseDto;
import com.qodara.virtual_store.security.model.response.TokenResponseDto;
import com.qodara.virtual_store.shared.exception.CustomException;
import com.qodara.virtual_store.shared.exception.ValidationException;
import com.qodara.virtual_store.shared.model.dto.response.ApiResponse;
import com.qodara.virtual_store.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    public AuthService(AuthenticationManager authenticationManager, AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public ApiResponse<RegisteredUserResponseDto> registerUser(RegisterRequestDto request) {
        //validación de la petición
        validateRegisterRequest(request);

        //si no existe, lo registra
        var account = Account.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.getRoleById(request.getRole()))
                .build();

        //guarda el usuario
        var newUser = accountRepository.save(account);

        //mapea de la entidad al dto
        var responseData = modelMapper.map(newUser, RegisteredUserResponseDto.class);

        return new ApiResponse<>("Register Success", Estatus.SUCCESS, responseData);
    }

    @Override
    public ApiResponse<RegisteredUserResponseDto> updatePassword(int id, PasswordRequestDto request) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) {
            return new ApiResponse<>("Account not found", Estatus.ERROR, null);
        } else {
            Account account = accountOptional.get();
            validateUpdatePassword(request);
            modelMapper.map(request, account);
            account.setPassword(passwordEncoder.encode(request.getPassword()));
            account.setRole(roleRepository.getRoleById(account.getRole().getId()));
            accountRepository.save(account);
            RegisteredUserResponseDto response = modelMapper.map(account, RegisteredUserResponseDto.class);
            return new ApiResponse<>("Password updated successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<TokenResponseDto> login(LoginRequestDto request) {
        //se validan las credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //establece la seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //se obtiene el token
        String token = jwtTokenProvider.generateToken(authentication);

        Optional<Account> account = accountRepository.findByEmail(request.getEmail());

        var responseData = new TokenResponseDto(account.get().getId(), token, account.get().getRole().getName());
        return new ApiResponse<>("Authentication Success", Estatus.SUCCESS, responseData);
    }


    private void validateRegisterRequest(RegisterRequestDto request) {
        if (isEmailAlreadyRegistered(request.getEmail())) {
            throw new CustomException(HttpStatus.CONFLICT, "The email '" + request.getEmail() + "' is already registered, please try another one");
        }
        if (isNotValidatePassword(request.getPassword())) {
            throw new ValidationException("Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one number and one special character");
        }
    }

    private void validateUpdatePassword(PasswordRequestDto request) {
        if (isNotValidatePassword(request.getPassword())) {
            throw new ValidationException("Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one number and one special character");
        }
    }


    private boolean isEmailAlreadyRegistered(String email) {
        return accountRepository.existsByEmail(email);
    }


    private boolean isNotValidatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return !password.matches(regex);
    }
}
