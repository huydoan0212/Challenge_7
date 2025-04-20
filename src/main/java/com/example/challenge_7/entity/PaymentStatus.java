package com.example.challenge_7.entity;

public enum PaymentStatus {
    THANH_TOAN_THANH_CONG("Thanh toán thành công"),
    CHUA_THANH_TOAN("Chưa thanh toán"),
    THANH_TOAN_THAT_BAI("Thanh toán thất bại");
    private String message;

    PaymentStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
