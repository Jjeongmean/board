package com.board.entity;

import com.board.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        //패스워드 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = new Member();

        //사용자가 입력한 회원가입 정보를 member엔티티로 변환
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(password); //DB에는 최종적으로 암호화된 패스워드가 저장되도록 한다.

        //개발자가 지정해줘야 하는 정보
        //member.setRole(Role.USER); //일반 사용자로 가입할때
        //member.setRole(Role.ADMIN); //관리자로 가입할때

        return member;
    }
}
