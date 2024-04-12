package com.example.ordering_lecture.common;


import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.domain.Role;
import com.example.ordering_lecture.member.repository.MemberRepository;
import com.example.ordering_lecture.securities.JwtTokenProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class InitialDataLoader implements CommandLineRunner {
//      commandLineRunner를 통해 스프링빈으로 등록되는 시점에 run 메소드 실행
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public InitialDataLoader(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        if(memberRepository.findByEmail("admin@test.com").isEmpty()){
            Member adminMember = Member.builder()
                    .name("admin")
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .build();
            jwtTokenProvider.createRecommandToken(adminMember.getEmail(), adminMember.getRole().toString());
            memberRepository.save(adminMember);
        }else {
            Member adminMember = memberRepository.findByEmail("admin@test.com").orElseThrow();
          jwtTokenProvider.createRecommandToken(adminMember.getEmail(), adminMember.getRole().toString());
        }

    }
}
