package toy.mywordle.auth;
// login 진행 완료 후 session을 만들어 넣는 역할 (Security ContextHolder)
// Authentication 객체만 Object로 갖는다.
// Authentication 안에 User 정보가 있어야 함.

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import toy.mywordle.domain.user;

import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private user user;

    public PrincipalDetails(user user) {
        this.user = user;
    }

    // 해당 User의 권한 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
