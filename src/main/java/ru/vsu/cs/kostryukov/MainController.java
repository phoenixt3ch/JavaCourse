package ru.vsu.cs.kostryukov;

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
    private static final String APP_VERSION = "v1.1";
    private static final String APP_BASEPATH = "/api/" + APP_VERSION;
    private static final String APP_SERVICE = "microcalc " + APP_VERSION;

    private static final StatusResponse okResponse = new StatusResponse("OK");
    private static final ErrorResponse errorResponse = new ErrorResponse("Invalid Input");

    @GetMapping(APP_BASEPATH + "/status")
    public StatusResponse getStatus() {
        return okResponse;
    }

    @PostMapping(APP_BASEPATH + "/add")
    public ServiceResponse postAdd(
            @Valid @RequestBody CalcRequest calcRequest, @RequestHeader HttpHeaders headers) {
        final double op1 = calcRequest.getOperands().get(0);
        final double op2 = calcRequest.getOperands().get(1);

        return new ServiceResponse(ICalculator.add(op1, op2), calcRequest.getOperands(), APP_SERVICE);
    }

    @PostMapping(APP_BASEPATH + "/sub")
    public ServiceResponse postSub(
            @Valid @RequestBody CalcRequest calcRequest, @RequestHeader HttpHeaders headers) {
        final double op1 = calcRequest.getOperands().get(0);
        final double op2 = calcRequest.getOperands().get(1);

        return new ServiceResponse(ICalculator.subtract(op1, op2), calcRequest.getOperands(), APP_SERVICE);
    }

    @PostMapping(APP_BASEPATH + "/div")
    public ServiceResponse postDiv(
            @Valid @RequestBody CalcRequest calcRequest, @RequestHeader HttpHeaders headers) {
        final double op1 = calcRequest.getOperands().get(0);
        final double op2 = calcRequest.getOperands().get(1);
        final double res;

        try {
            res = ICalculator.divide(op1, op2);
        } catch (ArithmeticException e) {
            throw new BadOperandsException();
        }

        return new ServiceResponse(res, calcRequest.getOperands(), APP_SERVICE);
    }

    @PostMapping(APP_BASEPATH + "/mult")
    public ServiceResponse postMult(
            @Valid @RequestBody CalcRequest calcRequest, @RequestHeader HttpHeaders headers) {
        final double op1 = calcRequest.getOperands().get(0);
        final double op2 = calcRequest.getOperands().get(1);

        return new ServiceResponse(ICalculator.multiply(op1, op2), calcRequest.getOperands(), APP_SERVICE);
    }


    @ExceptionHandler({BadOperandsException.class, WebExchangeBindException.class})
    ResponseEntity handleBadOperands() {
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ServiceResponse {
        private double result;
        private List<Double> operands;
        private String service;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class CalcRequest {
        @NotNull
        @Size(min = 2, max = 2)
        public List<Double> operands;
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
