package user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nickname;

  private String email;

  private String password;

  // md5 salt for password
  private String salt;

  private String hostname;

  private int port;

  private boolean isLogin;

  public User(String nickname, String email, String password, String salt) {
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.salt = salt;
  }
}

