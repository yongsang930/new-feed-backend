package com.newfeed.backend.domain.authorization;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1) username(=email)로 store_user 조회 (Optional 반환)
        var storeUserEntity = storeUserService.getRegisterUser(username);

        // 2) 유저가 소속된 store를 store_id + REGISTERED 상태로 조회
        var storeEntity = storeRepository.findFirstByIdAndStatusOrderByIdDesc(
            storeUserEntity.get().getStoreId(),
            StoreStatus.REGISTERED
        );

        // 3) store_user 엔티티가 존재하면 UserSession 객체 생성
        return storeUserEntity.map(it ->{

            // UserSession: Spring Security에서 사용할 인증 정보 객체
            var userSession = UserSession.builder()
                .userId(it.getId())
                .email(it.getEmail())
                .password(it.getPassword())
                .status(it.getStatus())
                .role(it.getRole())
                .registeredAt(it.getRegisteredAt())
                .lastLoginAt(it.getLastLoginAt())
                .unregisteredAt(it.getUnregisteredAt())

                 // Store 정보 (store_user는 store에 소속되므로 store도 필요)
                .storeId(storeEntity.get().getId())
                .storeName(storeEntity.get().getName())
                .build();

            // UserDetails 타입으로 리턴됨 (UserSession이 UserDetails 구현체)
            return userSession;
        })
        // 4) Optional이 비어있으면 Spring Security 표준 예외 발생
        .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}