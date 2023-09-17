package open.filelink.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    private T data;

    private LocalDateTime responseTIme;

    private int code;

    private String description;


    @Builder
    public ApiResponse(T data, LocalDateTime responseTIme, int code, String description) {
        this.data = data;
        this.responseTIme = responseTIme;
        this.code = code;
        this.description = description;
    }


    public static <T> ApiResponse<T> success(T data) {
        return (ApiResponse<T>) ApiResponse.builder()
                .code(StatusCode.SUCCESS.getCode())
                .responseTIme(LocalDateTime.now())
                .data(data)
                .description("success")
                .build();
    }


    public static <T> ApiResponse<T> fail(String description) {
        return (ApiResponse<T>) ApiResponse.builder()
                .code(StatusCode.FAIL.getCode())
                .responseTIme(LocalDateTime.now())
                .description(description)
                .build();
    }


}
