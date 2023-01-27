//package ar.edu.itba.getaway.models;
//
//import javax.persistence.*;
//import java.security.SecureRandom;
//import java.time.LocalDateTime;
//import java.util.Base64;
//import java.util.Objects;
//
//@Entity
//@Table(name = "sessionRefreshToken")
//public class SessionRefreshToken {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sessionRefreshToken_sessionTokenId_seq")
//    @SequenceGenerator(sequenceName = "sessionRefreshToken_sessionTokenId_seq", name = "sessionRefreshToken_sessionTokenId_seq", allocationSize = 1)
//    @Column(name = "sessionTokenId")
//    private long id;
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "sessionTokenUserId")
//    private UserModel user;
//    @Column(name = "sessionToken", nullable = false)
//    private String value;
//    @Column(name = "sessionTokenExpirationDate", nullable = false)
//    private LocalDateTime expirationDate;
//
//    private static final Integer TOKEN_DURATION_DAYS = 5;
//    private static final SecureRandom secureRandom = new SecureRandom();
//    private static final Base64.Encoder base64Encoder = Base64.getEncoder();
//
//    public static LocalDateTime generateTokenExpirationDate() {
//        return LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS);
//    }
//
//    public static String generateSessionToken() {
//        final byte[] bytes = new byte[64];
//        secureRandom.nextBytes(bytes);
//        return base64Encoder.encodeToString(bytes);
//    }
//
//    /* default */
//    protected SessionRefreshToken() {
//        // Just for Hibernate
//    }
//
//    public SessionRefreshToken(String value, UserModel user, LocalDateTime expirationDate) {
//        this.value = value;
//        this.user = user;
//        this.expirationDate = expirationDate;
//    }
//
//    public SessionRefreshToken(long id, String value, UserModel user, LocalDateTime expirationDate) {
//        this.id = id;
//        this.value = value;
//        this.user = user;
//        this.expirationDate = expirationDate;
//    }
//
//    public boolean isValid() {
//        return expirationDate.compareTo(LocalDateTime.now()) > 0;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public UserModel getUser() {
//        return user;
//    }
//
//    public void setUser(UserModel user) {
//        this.user = user;
//    }
//
//    public LocalDateTime getExpirationDate() {
//        return expirationDate;
//    }
//
//    public void setExpirationDate(LocalDateTime expirationDate) {
//        this.expirationDate = expirationDate;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof SessionRefreshToken)) {
//            return false;
//        }
//        SessionRefreshToken sessionRefreshToken = (SessionRefreshToken) o;
//        return this.getId() == sessionRefreshToken.getId();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//
//    public void refresh() {
//        setValue(generateSessionToken());
//        resetExpirationDate();
//    }
//
//    public void resetExpirationDate() {
//        expirationDate = generateTokenExpirationDate();
//    }
//
//}
