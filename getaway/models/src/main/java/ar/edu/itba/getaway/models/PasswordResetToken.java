package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "passwordResetToken")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passwordResetToken_passTokenId_seq")
    @SequenceGenerator(sequenceName = "passwordResetToken_passTokenId_seq", name = "passwordResetToken_passTokenId_seq", allocationSize = 1)
    @Column(name = "passTokenId")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passTokenUserId")
    private UserModel user;
    @Column(name = "passToken", nullable = false)
    private String value;
    @Column(name = "passTokenExpirationDate", nullable = false)
    private LocalDateTime expirationDate;

    private static final Integer TOKEN_DURATION_DAYS = 1;

    public static LocalDateTime generateTokenExpirationDate() {
        return LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS);
    }

    /* default */
    protected PasswordResetToken() {
        // Just for Hibernate
    }

    public PasswordResetToken(String value, UserModel user, LocalDateTime expirationDate) {
        this.value = value;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public boolean isValid() {
        return expirationDate.compareTo(LocalDateTime.now()) > 0;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof PasswordResetToken)){
            return false;
        }
        PasswordResetToken passwordResetToken = (PasswordResetToken) o;
        return this.id.equals(passwordResetToken.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
