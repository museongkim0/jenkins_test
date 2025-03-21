package com.example.backend.user.service;

import com.example.backend.global.exception.UserException;
import com.example.backend.global.response.responseStatus.UserResponseStatus;
import com.example.backend.instructor.model.Instructor;
import com.example.backend.instructor.model.dto.req.UpdateUserInstructorDto;
import com.example.backend.instructor.model.dto.res.InstructorResponseDto;
import com.example.backend.instructor.repository.InstructorRepository;
import com.example.backend.student.model.Dto.StudentResponseDto;
import com.example.backend.student.model.StudentDetail;
import com.example.backend.student.repository.StudentRepository;
import com.example.backend.user.model.Dto.UserInsSignUpDto;
import com.example.backend.user.model.Dto.UserResponseDto;
import com.example.backend.user.model.Dto.UserUpdateRequestDto;
import com.example.backend.user.model.User;

import com.example.backend.user.repository.UserRepository;
import com.example.backend.user.model.Dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    private static final List<String> VALID_ROLES = List.of("INSTRUCTOR", "MANAGER", "STUDENT");

    @Transactional
    public UserResponseDto.SignupResponse signup(UserRequestDto.SignupRequest dto, String role) {
         if (!pattern.matcher(dto.getEmail()).matches()) {
            throw new UserException(UserResponseStatus.INVALID_EMAIL_FORMAT);
        }

        // 이메일 중복 체크
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserException(UserResponseStatus.EMAIL_ALREADY_IN_USE);
        }

        User user = null;

        if ("MANAGER".equals(role)) {  // role.equals("MANAGER") 방식으로 비교
            user = managerSignup(dto);
        } else if ("STUDENT".equals(role)) {
            user = studentSignup(dto);
        } else if ("INSTRUCTOR".equals(role)) {
            user = instructorSignup(dto);
        } else {
            throw new UserException(UserResponseStatus.UNIDENTIFIED_ROLE);
        }
        // 이메일 형식 검증

        if (user != null) {
            return UserResponseDto.SignupResponse.from(user);
        }
        throw new UserException(UserResponseStatus.USER_SAVE_FAIL);
    }

    @Transactional
    public User instructorSignup(UserRequestDto.SignupRequest dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = userRepository.save(dto.toEntity(encodedPassword, "INSTRUCTOR"));

        return user;
    }


    //
    public void CustomInstructorSignup(UserInsSignUpDto dto, String role) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        userRepository.save(dto.toEntity(encodedPassword , "INSTRUCTOR"));
    }

    @Transactional
    public User studentSignup(UserRequestDto.SignupRequest dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = userRepository.save(dto.toEntity(encodedPassword, "STUDENT"));

        return user;
    }

    @Transactional
    public User managerSignup(UserRequestDto.SignupRequest dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = userRepository.save(dto.toEntity(encodedPassword, "MANAGER"));

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }


    public User getUserinfo(Long userIdx){
        return userRepository.findById(userIdx)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public void setUserInfo(User user){
        userRepository.save(user);
    }


    public List<User> findUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public void setInstructorInfo(Long instructorIdx, UpdateUserInstructorDto dto) {
        userRepository.save(dto.toInstructorEntity(instructorIdx));
    }


    public List<User> findUsersByRoleWithInstructor() {
        return userRepository.findUsersWithInstructorByRole("INSTRUCTOR");
    }

    @Transactional(readOnly = true)
    public Object getUserInfo(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("로그인한 사용자가 없습니다.");
        }
        String role = user.getRole();
        if ("MANAGER".equalsIgnoreCase(role)) {
            return UserResponseDto.BasicUserResponseDto.from(user);
        } else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
            Optional<Instructor> optInstructor = instructorRepository.findByUserIdx(user.getIdx());
            if (optInstructor.isPresent()) {
                return InstructorResponseDto.from(optInstructor.get());
            } else {
                return UserResponseDto.BasicUserResponseDto.from(user);
            }
        } else if ("STUDENT".equalsIgnoreCase(role)) {
            Optional<StudentDetail> optStudent = studentRepository.findByStudent(user.getIdx());
            if (optStudent.isPresent()) {
                return StudentResponseDto.from(optStudent.get());
            } else {
                return UserResponseDto.BasicUserResponseDto.from(user);
            }
        } else {
            return UserResponseDto.BasicUserResponseDto.from(user);
        }
    }

    @Transactional
    public Object updateUserInfo(User user, UserUpdateRequestDto dto) {
        boolean updated = false;

        if (dto.getBirth() != null) {
            user.setBirth(dto.getBirth());
            updated = true;
        }

        if(dto.getProfileUrl() != null) {
            user.setProfileUrl(dto.getProfileUrl());
            updated = true;
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encoded = passwordEncoder.encode(dto.getPassword());
            user.setPassword(encoded);
            updated = true;
        }

        String role = user.getRole();
        if ("STUDENT".equalsIgnoreCase(role)) {
            StudentDetail studentDetail = user.getStudentDetail();
            if (studentDetail != null && dto.getAddress() != null && !dto.getAddress().isEmpty()) {
                studentDetail.setAddress(dto.getAddress());
                studentRepository.save(studentDetail);
                updated = true;
            }
        } else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
            Instructor instructor = user.getInstructor();
            if (instructor != null && dto.getPortfolio() != null && !dto.getPortfolio().isEmpty()) {
                instructor.setPortfolio(dto.getPortfolio());
                instructorRepository.save(instructor);
                updated = true;
            }
            if (instructor != null && dto.getRecord() != null && !dto.getRecord().isEmpty()) {
                instructor.setRecord(dto.getRecord());
                instructorRepository.save(instructor);
                updated = true;
            }
        }

        if (updated) {
            userRepository.save(user);
        }

        return getUserInfo(user);
    }

}
