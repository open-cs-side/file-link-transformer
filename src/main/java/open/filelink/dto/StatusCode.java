package open.filelink.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatusCode {

    SUCCESS(200),
    FAIL(400);

    private final int code;


}
