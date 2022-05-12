package springstudyjunior.realworld.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springstudyjunior.realworld.dto.NewUserRequestDto;
import springstudyjunior.realworld.dto.UserResponseDto;
import springstudyjunior.realworld.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<UserResponseDto> registerUser(NewUserRequestDto newUserRequestDto) {
        final var newUserData = newUserRequestDto.toEntity();
        String email = newUserData.getEmail();
        String username = newUserData.getUsername();

//        new User에 newUserData를 검증 후 예외처리를 진행하고 정상적으로 저장이 되면 저장
//        final var newUser =

        userRepository.save(newUserData);

//        정상적으로 저장되면 OK(201)(UserResponse( 새로 저장된 user와 jwtToken)) 을 반환 -> 안되면 422
        return null;
    }

    public void loginUser() {

    }

    public void getUser() {

    }

    public void updateUser() {
    }
}
