//package com.example.ordering_lecture.common;
//
//import com.example.ordering_lecture.member.domain.Gender;
//import com.example.ordering_lecture.member.domain.Member;
//import com.example.ordering_lecture.member.domain.Role;
//import com.example.ordering_lecture.member.dto.MemberResponseDto;
//import com.example.ordering_lecture.member.repository.MemberRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class InitialDataLoader implements CommandLineRunner {
//    @Autowired
//    private final MemberRepository memberRepository;
//    @Autowired
//    private final PasswordEncoder passwordEncoder;
//    public InitialDataLoader(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
//        this.memberRepository = memberRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    // CommandLineRunner을 통해 스프링 bean으로 등록되는 시점에 run 메서드 실행
//    @Override
//    public void run(String... args) throws Exception{
//        if(memberRepository.findByEmail("admin@test.com").isEmpty()){
//            Member member = Member.builder()
//                    .name("admin")
//                    .email("admin@test.com")
//                    .password(passwordEncoder.encode("1234"))
//                    .gender(Gender.MALE)
//                    .role(Role.ADMIN)
//                    .phoneNumber("010-0000-0000")
//                    .build();
//            memberRepository.save(member);
//        }
//    }
//
//}
