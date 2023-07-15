package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "verificationToken")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verificationToken_verifId_seq")
    @SequenceGenerator(sequenceName = "verificationToken_verifId_seq", name = "verificationToken_verifId_seq", allocationSize = 1)
    @Column(name = "verifId")
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verifUserId")
    private UserModel user;
    @Column(name = "verifToken", nullable = false)
    private String value;
    @Column(name = "verifExpirationDate", nullable = false)
    private LocalDateTime expirationDate;
    private static final Integer TOKEN_DURATION_DAYS = 1;

    public static LocalDateTime generateTokenExpirationDate() {
        return LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS);
    }

    /* default */
    protected VerificationToken() {
        // Just for Hibernate
    }

    public VerificationToken(String value, UserModel user, LocalDateTime expirationDate) {
        this.value = value;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public VerificationToken(long id, String value, UserModel user, LocalDateTime expirationDate) {
        this.id = id;
        this.value = value;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    
    public long getUserId() {
        return user.getUserId();
    }

    public boolean isValid() {
        return expirationDate.compareTo(LocalDateTime.now()) > 0;
    }

    public boolean isValidToken() {
        return LocalDateTime.now().isBefore(expirationDate);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof VerificationToken)) {
            return false;
        }
        VerificationToken verificationToken = (VerificationToken) o;
        return this.getId() == verificationToken.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
