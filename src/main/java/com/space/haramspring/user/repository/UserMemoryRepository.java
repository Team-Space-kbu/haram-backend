package com.space.haramspring.user.repository;

import com.space.haramspring.core.entity.users.Role;
import com.space.haramspring.core.entity.users.User;
import com.space.haramspring.user.dto.UserSignupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class UserMemoryRepository implements UserRepository {

    static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 멀티스레드 참조시, 중복 접근을 방지하기 위함.
    private static final Map<Long, User> store = new ConcurrentHashMap<>();
    private static final Map<String, Long> idList = new ConcurrentHashMap<>();
    private static Long userSeqNum = 0L;

    /**
     * saveUser - Repository ㅣ
     * 데이터 베이스에 유저 정보를 저장한다.
     * @param userSignupDto 저장할 유저 정보를 받아온다.
     * @return 만들어진 유저 정보를 반환한다.
     */
    @Override
    public User saveUser(UserSignupDto userSignupDto) {

        CharSequence password = java.nio.CharBuffer.wrap(userSignupDto.getPassword());

        User user = User.builder()
                .userSeqNum(++userSeqNum)
                .userId(userSignupDto.getUserId())
                .email(userSignupDto.getEmail())
                .password(passwordEncoder.encode(password))
                .nickname(userSignupDto.getNickname())
                .role(Role.USER)
                .build();

        Long userSeqNum = user.getUserSeqNum();
        String userId = user.getUserId();

        user.setJoinDate(new Date(System.currentTimeMillis()).toString());

        store.put(userSeqNum, user); // 유저 객체 저장 (store)
        idList.put(userId, userSeqNum); // 유저 고유 번호 저장 (idList)

        return user;
    }

    /**
     * getSeqNumFindById - Repository ㅣ
     * @param loginId 유저의 아이디를 받아온다.
     * @return 유저의 아이디로 저장된 Sequence Number 를 반환한다.
     */
    @Override
    public Long getSeqNumFindById(String loginId) {

        if(idList.containsKey(loginId)){
            return idList.get(loginId);
        }
        return -1L;
    }

    /**
     * findByUserSeq - Repository ㅣ
     * @param userSeqNum 유저의 고유 번호를 받아온다.
     * @return 고유번호를 통해 찾은 유저 객체를 반환한다.
     */
    @Override
    public Optional<User> findByUserSeq(Long userSeqNum) {

        return findAllUser().stream()
                .filter(m -> m.getUserSeqNum().equals(userSeqNum))
                .findFirst();
    }

    /**
     * findAllUser - Repository ㅣ
     * @return 모든 유저 객체를 찾아 반환한다.
     */
    @Override
    public User addUserByUserObject(User user) {

        log.info("[UserMemoryRepository - addUserByUserObject] 유스케이스에게 {} 유저 정보 저장 요청", user);
        User saveUser = addUserUseCase.addUser(user);
        return saveUser;
    }

//    @Override
//    public User updateUser(Long userSeqNum, UserSignupDto updateUser) {
//
//        // update 향후 testing 필요
//        updateUser.setUserSeqNum(userSeqNum);
//        store.put(userSeqNum, updateUser);
//
//        return store.get(userSeqNum);
//    }

    /**
     * removeUser - Repository ㅣ
     * 유저의 정보를 삭제한다.
     * @param userSeqNum 유저의 고유 번호를 받아온다.
     * @param userId 유저의 아이디를 받아온다.
     */
    @Override
    public void removeUser(Long userSeqNum, String userId) {

        store.remove(userSeqNum);
        idList.remove(userId);
    }
}
