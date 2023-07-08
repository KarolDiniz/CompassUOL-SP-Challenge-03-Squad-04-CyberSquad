package br.com.compassuol.pb.challenge.msproducts.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;
}
