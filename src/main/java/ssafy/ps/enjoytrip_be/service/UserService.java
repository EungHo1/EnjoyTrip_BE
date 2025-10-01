package ssafy.ps.enjoytrip_be.service;

import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.dto.UserRegisterRequestDto;

public interface UserService {
    void join(UserRegisterRequestDto requestDto); // 파라미터 타입 변경
    UserDto login(String userId, String userPassword); // 로그인
    UserDto getUser(String userId); // 회원 정보 조회
    void updateUser(UserDto userDto); // 회원 정보 수정
    void deleteUser(String userId); // 회원 탈퇴
}