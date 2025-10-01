package ssafy.ps.enjoytrip_be.service.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dao.UserDao;
import ssafy.ps.enjoytrip_be.dao.impl.BoardDaoImpl;
import ssafy.ps.enjoytrip_be.dao.impl.UserDaoImpl;
import ssafy.ps.enjoytrip_be.domain.User;
import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.dto.UserRequestDto;
import ssafy.ps.enjoytrip_be.service.UserService;

import java.sql.SQLException;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao =  UserDaoImpl.getInstance();
    private final BoardDaoImpl boardDao = BoardDaoImpl.getInstance();
    @Getter
    public static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl(){
    }

    @Override
    public void join(UserRequestDto requestDto) {
        User user = new User();
        user.setUserId(requestDto.getUserId());
        user.setUserName(requestDto.getUserName());
        user.setUserPassword(requestDto.getUserPassword());

        User userId = userDao.findByUserId(user.getUserId());// 중복 검사
        if (userId != null) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        } else {
            userDao.createUser(user);
        }
    }

    @Override
    public UserDto login(String userId, String userPassword) {
        log.info("UserService login() 호출됨. userId: {}", userId);
        User user = userDao.findByUserId(userId);
        if (user != null) {
            // --- 여기 로그 추가 ---
            boolean isPasswordCorrect = user.getUserPassword().equals(userPassword);
            log.info("DB 비밀번호와 입력된 비밀번호 일치 여부: {}", isPasswordCorrect);
            // --- 여기까지 ---

            if(isPasswordCorrect) {
                // 로그인 성공
                UserDto userDto = new UserDto();
                userDto.setUserNo(user.getUserNo());
                userDto.setUserId(user.getUserId());
                userDto.setUserName(user.getUserName());
                userDto.setJoinDate(user.getJoinDate());
                return userDto;
            } else {
                log.info("비밀번호가 일치하지 않습니다.");
            }
        }
        return null;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userDao.findByUserId(userId);
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setUserNo(user.getUserNo());
            userDto.setUserId(user.getUserId());
            userDto.setUserName(user.getUserName());
            userDto.setJoinDate(user.getJoinDate());
            return userDto;
        }
        return null;
    }

    @Override
    public UserDto getUserByNo(long userNo) {
        User user = userDao.findByUserNo(userNo);
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setUserNo(user.getUserNo());
            userDto.setUserId(user.getUserId());
            userDto.setUserName(user.getUserName());
            return userDto;
        }
        return null;
    }

    @Override
    public void updateUser(UserRequestDto userDto) {
        User user = userDao.findByUserNo(userDto.getUserNo());
        if (user != null) {
            user.setUserName(userDto.getUserName());
            if(userDto.getUserPassword() != null && !userDto.getUserPassword().isBlank()) {
                user.setUserPassword(userDto.getUserPassword());
            }
            userDao.updateUser(user);
        } else {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
    }

    @Override
    public void deleteUser(long userNo) {
        User user = userDao.findByUserNo(userNo);
        if (user != null) {
            try {
                boardDao.reassignPostsToUnknown(userNo); // 사용자가 작성했던 게시글 이름 "탈퇴한 이용자"으로 변경
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            userDao.deleteUser(user.getUserNo());
        } else {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
    }

}
