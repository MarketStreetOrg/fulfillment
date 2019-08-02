package org.katale.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.katale.aop.annotations.Validator;
import org.katale.exceptions.ValidationFailedException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Aspect
public class CustomValidator {

    @Pointcut(value = "@annotation(validator)", argNames = "validator")
    protected void CustomValidator(Validator validator) { }

    @Before("CustomValidator(validator)")
    public void validate(JoinPoint joinPoint, Validator validator) throws ValidationFailedException {

        try {

            if (validator.params().length != joinPoint.getArgs().length) throw new ValidationFailedException();

            for (int index = 0; index < validator.params().length; index++) {

                if (!Pattern.matches(validator.params()[index], joinPoint.getArgs()[index].toString())) {
//                    System.err.println("Error: Failed to match pattern with: \"" + joinPoint.getArgs()[index] + "\"");
//                    joinPoint.getArgs()[0] = "illegal order number";
                    throw new ValidationFailedException();
                }
            }

        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }

        System.out.println("Validation Success");
    }

}
