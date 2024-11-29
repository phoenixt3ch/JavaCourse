package ru.vsu.cs.kostryukov.microcalc.add;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@RestController
public class MainController {
    private static final String APP_VERSION = "v1";
    private static final String APP_BASEPATH = "/api/" + APP_VERSION;
    private static final String APP_SERVICE = "add " + APP_VERSION;

    private static final StatusResponse okResponse = new StatusResponse("OK");
    private static final ErrorResponse errorResponse = new ErrorResponse("Invalid Input");

    @GetMapping(APP_BASEPATH + "/status")
    public StatusResponse getStatus() {
        return okResponse;
    }

    @PostMapping(APP_BASEPATH + "/add")
    public ServiceResponse postAdd(
            @Valid @RequestBody CalcRequest calcRequest, @RequestHeader HttpHeaders headers) {
        final int op1 = calcRequest.getOperands().get(0);
        final int op2 = calcRequest.getOperands().get(1);

        return new ServiceResponse(op1 + op2, calcRequest.getOperands(), APP_SERVICE);
    }

    @ExceptionHandler({BadOperandsException.class, WebExchangeBindException.class})
    ResponseEntity handleBadOperands() {
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ServiceResponse {
        private int result;
        private List<Integer> operands;
        private String service;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CalcRequest {
        @NotNull
        @Size(min = 2, max = 2)
        public List<Integer> operands;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadOperandsException extends RuntimeException {}

    @Data
    @NoArgsConstructor
    static class StatusResponse {
        private String data;

        public StatusResponse(String data) {
            this.data = data;
        }
    }

    @Data
    @NoArgsConstructor
    static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
