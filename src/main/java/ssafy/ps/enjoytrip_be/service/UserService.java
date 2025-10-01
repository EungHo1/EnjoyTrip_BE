package ssafy.ps.enjoytrip_be.service;

import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.dto.UserRequestDto;

public interface UserService {
    void join(UserRequestDto requestDto); // 파라미터 타입 변경
    UserDto login(String userId, String userPassword); // 로그인
    UserDto getUserById(String userId); // 회원 정보 조회
    UserDto getUserByNo(long userNo);
    void updateUser(UserRequestDto userDto); // 회원 정보 수정
    void deleteUser(long userNo); // 회원 탈퇴
}