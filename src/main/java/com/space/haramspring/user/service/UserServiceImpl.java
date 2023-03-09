package com.space.haramspring.user.service;

import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.jwt.JwtUtils;
import com.space.haramspring.user.dto.UserJwtDto;
import com.space.haramspring.user.dto.UserSignupDto;
import com.space.haramspring.user.dto.UserLoginDto;
import com.space.haramspring.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    /**
     * getUserInfo - Service ㅣ
     * @param userId 사용자의 아이디를 받는다.
     * @return 해당 아이디로 userSeqNum 을 찾아 저장소에서 회원 정보를 map 형태로 반환한다.
     */
    @Override
    public Map<String, String> getUserInfo(String userId) {

        Long userSeqNum = userRepository.getSeqNumFindById(userId);

        if (userSeqNum.equals(-1L)) {
            // 유저가 id를 "fail"로 요청했을 때, 예외처리 필요(다른 형식 사용 요망)
            return createResponseBody("read", "fail");
        }

        Optional<User> optionalUser = userRepository.findByUserSeq(userSeqNum);

        return createSuccessMsgFromUserInfo(optionalUser.get());
    }

    /**
     * addUser - Service ㅣ
     * 회원가입을 위한 정보를 저장한다.
     * @param userSignupDto 데이터 베이스에 저장될 정보를 해당 객체로 받아온다.
     *                      같은 아이디로 저장되어 있는 유저가 있는지 체크한 후,
     *                      회원 정보를 데이터 베이스에 저장한다.
     * @return 저장된 회원 정보를 반환한다.
     */
    @Override
    public Map<String, String> addUser(UserSignupDto userSignupDto) {

        String userId = userSignupDto.getUserId();
        log.info("[service] - 유저 추가 로직 실행. 받아온 유저의 아이디는 {}", userId);
        Long userSeqNum = userRepository.getSeqNumFindById(userId);

        if (!userSeqNum.equals(-1L)) {
            // 유저가 id를 "fail"로 요청했을 때, 예외처리 필요(다른 형식 사용 요망)
            return createResponseBody("create", "fail");
        }

        User user = userRepository.saveUser(userSignupDto);

        return createSuccessMsgFromUserInfo(user);

    }


//    @Override
//    public Map<String, String> updateUser(UserSignupDto user) {
//
//        String userId = user.getId();
//        Long userSeqNum = userRepository.getSeqNumFindById(userId);
//
//        if (userSeqNum.equals(-1L)) {
//
//            return createResponseBody("update", "fail");
//        }
//
//        UserSignupDto newUser = userRepository.updateUser(userSeqNum, user);
//
//        return createResponseBody("update", "success");
//    }

//    @Override
//    public Map<String, String> deleteUser(String userId) {
//
//        Long userSeqNum = userRepository.getSeqNumFindById(userId);
//
//        if (userSeqNum.equals(-1L)) {
//            return createResponseBody("delete", "fail");
//        }
//
//        userRepository.removeUser(userSeqNum, userId);
//        return createResponseBody("delete", "success");
//    }

    /**
     * loginUser - Service ㅣ
     * @param userLoginDto 유저가 로그인 요청시 입력한 아이디 패스워드를 불러온다.
     *                     이를 통해 userSeqNum 을 찾아 유저 정보를 UserJwtDto 에 담아 토큰을 생성하는
     *                     generate 메서드에게 호출하며 이 정보를 넘긴다.
     *                     받아온 해쉬 값의 로그인 시간을 loginDate 에 저장한다.
     * @return 토큰 값을 Map 에 담아 반환한다.
     */
    @Override
    public Map<String, String> loginUser(UserLoginDto userLoginDto) {

        Long userSeqNum = userRepository.getSeqNumFindById(userLoginDto.getUserId());
        log.debug("[UserServiceImpl] - userSeqNum : {}", userSeqNum);

        if (userSeqNum.equals(-1L)) {
            return createResponseBody("login", "fail");
        }

        Optional<User> optionalUser = userRepository.findByUserSeq(userSeqNum);

        User user = optionalUser.get();

        UserJwtDto userJwtDto = UserJwtDto.builder()
                .userId(user.getUserId())
                .build();

        String token = jwtUtils.generateToken(userJwtDto);

        log.debug("[UserServiceImpl] - token : {}", token);

        Map<String, String> accessToken = new HashMap<>();
        accessToken.put("accessToken", "Bearer " + token);

        user.setLoginDate(jwtUtils.extractAllClaims(token).getIssuedAt().toString());

        log.debug("[UserServiceImpl] - User Login Complete: {}", "OK");
        return accessToken;
    }

    /**
     * createSuccessMsgFromUserInfo - Service ㅣ
     * 성공 메세지를 만들기 위한 메서드이다.
     * @param user 유저 객체를 불러온다.
     * @return 성공 메세지(유저 정보)를 Map 형태로 반환한다.
     */
    private static Map<String, String> createSuccessMsgFromUserInfo(User user) {
        Map<String, String> successMsg = new HashMap<>();

        successMsg.put("joinDate", user.getJoinDate());
        successMsg.put("nickname", user.getNickname());
        successMsg.put("email", user.getEmail());
        successMsg.put("userId", user.getUserId());

        return successMsg;
    }

    /**
     * createResponseBody - Service ㅣ
     * 성공 메세지를 반환하기 위한 메서드이다.
     * @param key 해당 키 값으로
     * @param value 밸류를 넣어
     * @return 맵 형태로 반환한다.
     * @param <T> 어떤 타입으로 반환할지 모르기에 타입 매개변수를 사용했다.
     */
    private static <T> Map<String, T> createResponseBody(String key, T value) {
        Map<String, T> response;
        response = new HashMap<>();
        response.put(key, value);
        return response;
    }
}