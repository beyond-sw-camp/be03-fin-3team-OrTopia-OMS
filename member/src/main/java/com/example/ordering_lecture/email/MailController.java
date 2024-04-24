package com.example.ordering_lecture.email;

import com.example.ordering_lecture.member.domain.Member;
import com.example.ordering_lecture.member.dto.Buyer.MemberNewpasswordRequestDto;
import com.example.ordering_lecture.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @PostMapping("/find-id")
    public ResponseEntity<String> findUserIdByEmail(@RequestParam String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾지 못했습니다. " + email));

        mailService.sendMail(email, "귀하의 계정 정보", "귀하의 아이디는 " + member.getEmail() + " 입니다.");
        return ResponseEntity.ok("등록하신 이메일로 아이디 정보를 보냈습니다. 메일함을 확인해 주세요.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾지 못했습니다. " + email));

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setMember(member);
        token.setExpiryDate(30);  // Set the token's expiry time to 30 minutes
        passwordResetTokenRepository.save(token);

        String url = "http://localhost:8081/NewPasswordComponent?token=" + token.getToken();
        mailService.sendMail(email, "비밀번호 재설정 요청", "아래 링크를 클릭하여 비밀번호를 재설정하세요: " + url);

        return ResponseEntity.ok("비밀번호 재설정 링크를 이메일로 보냈습니다. 링크는 30분 동안 유효합니다.");
    }

    @GetMapping("/change-password")
    public ResponseEntity<String> showChangePasswordForm(@RequestParam("token") String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token is invalid or expired"));

        if (resetToken.getExpiryDate().before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is invalid or expired");
        }

        return ResponseEntity.ok("Please enter your new password");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("token") String token, @RequestParam("password") String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token is invalid or expired"));

        if (resetToken.getExpiryDate().before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is invalid or expired");
        }

        Member member = resetToken.getMember();
        member.setPassword(passwordEncoder.encode(newPassword)); // 새 비밀번호를 암호화하여 저장
        memberRepository.save(member);

        passwordResetTokenRepository.delete(resetToken); // 토큰 삭제

        return ResponseEntity.ok("Your password has been successfully reset.");
    }

}
