package ssafy.ps.enjoytrip_be.service.impl;

import lombok.extern.slf4j.Slf4j;
import ssafy.ps.enjoytrip_be.dao.UserDao;
import ssafy.ps.enjoytrip_be.dao.impl.UserDaoImpl;
import ssafy.ps.enjoytrip_be.domain.User;
import ssafy.ps.enjoytrip_be.dto.UserDto;
import ssafy.ps.enjoytrip_be.dto.UserRegisterRequestDto;
import ssafy.ps.enjoytrip_be.service.UserService;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao =  UserDaoImpl.getInstance();
    public static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl(){
    }

    public static UserServiceImpl getInstance(){
        return instance;
    }

    @Override
    public void join(UserRegisterRequestDto requestDto) {
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
    public UserDto getUser(String userId) {
        return null;
    }

    @Override
    public void updateUser(UserDto userDto) {

    }

    @Override
    public void deleteUser(String userId) {

    }
}
