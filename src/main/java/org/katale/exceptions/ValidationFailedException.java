package org.katale.exceptions;

public class ValidationFailedException extends Throwable{

    public ValidationFailedException() {
        super("Failed to validate against rule");
    }


}
