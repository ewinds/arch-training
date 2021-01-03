import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class User {
    private PasswordEncoder passwordEncoder;
    private String name;
    private String hash;

    User(String name, String password) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.name = name;
        this.hash = this.passwordEncoder.encode(password);
    }

    boolean checkPW(String name, String password) {

        return this.name.equals(name) && this.passwordEncoder.matches(password, this.hash);
    }
}
