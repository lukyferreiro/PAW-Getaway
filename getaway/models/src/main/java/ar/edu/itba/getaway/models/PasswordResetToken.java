package ar.edu.itba.getaway.models;

import java.time.LocalDateTime;

public class PasswordResetToken {

    private String value;
    private Long id;
    private Long userId;
    private LocalDateTime expirationDate;

    private static final Integer TOKEN_DURATION_DAYS = 1;

    public static LocalDateTime generateTokenExpirationDate() {
        return LocalDateTime.now().plusDays(TOKEN_DURATION_DAYS);
    }

    public PasswordResetToken(Long id, String value, Long userId, LocalDateTime expirationDate) {
        this.value = value;
        this.id = id;
        this.userId = userId;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
