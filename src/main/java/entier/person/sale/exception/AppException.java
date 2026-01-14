package entier.person.sale.exception;

import lombok.Getter;

/**
 * Ngoại lệ tùy chỉnh cho ứng dụng.
 * Dùng để ném ra khi có lỗi nghiệp vụ (business logic) hoặc lỗi dữ liệu không hợp lệ.
 */
@Getter
public class AppException extends RuntimeException {
    private final String errorCode; // tùy chọn, để xác định loại lỗi

    public AppException(String message) {
        super(message);
        this.errorCode = null;
    }

    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
