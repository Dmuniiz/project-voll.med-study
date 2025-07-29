package med.voll.api.config.exception;

import org.springframework.validation.FieldError;

public record DataValidation(String campo, String mensagem) {

    public DataValidation(FieldError e){
        this(e.getField(), e.getDefaultMessage());
    }

}
