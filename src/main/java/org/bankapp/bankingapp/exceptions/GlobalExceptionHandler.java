package org.bankapp.bankingapp.exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.security.SignatureException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
//        logger.error("Constrain error: ", ex);
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("Constrain error: ", ex);
        return new ErrorResponse(ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                System.currentTimeMillis());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ErrorResponse handleAccountNotFoundException(AccountNotFoundException ex) {
        logger.error("Account not found: ", ex);
        return new ErrorResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis());
    }
    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ErrorResponse handleInsufficientFundsException(InsufficientFundsException ex) {
        logger.error(ex.getMessage());
        return new ErrorResponse(ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis());
    }

//
//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(JpaSystemException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleJpaSystemException(JpaSystemException ex) {
//        logger.error("JpaSystemException error: ", ex);
//        String errorMessage;
//
//        if (ex.getCause() != null && ex.getCause().getMessage().contains("Data truncated for column")) {
//            errorMessage = "One of the input fields exceeds the allowed length. Please review your inputs and try again.";
//        } else {
//            errorMessage = "There was an issue saving your data. Please ensure all inputs are valid and try again.";
//        }
//
//        return new ErrorResponse(errorMessage,
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(ExpiredJwtException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleExpiredJwtException(ExpiredJwtException ex) {
//        logger.error("Invalid JWT Signature: ", ex);
//        return new ErrorResponse("Expired JWT signature. The token cannot be trusted.",
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(InvalidJwtAuthenticationException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException ex) {
//        logger.error("Invalid JWT Signature: ", ex);
//        return new ErrorResponse("Expired JWT signature. The token cannot be trusted.",
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(SignatureException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleSignatureException(SignatureException ex) {
//        logger.error("Invalid JWT Signature: ", ex);
//        return new ErrorResponse("Invalid JWT signature. The token cannot be trusted.",
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
//        String message = String.format("The HTTP method '%s' is not supported for this endpoint. Supported methods are: %s",
//                ex.getMethod(), ex.getSupportedHttpMethods());
//        logger.error("Method Not Allowed: ", ex);
//        return new ErrorResponse(message, HttpStatus.METHOD_NOT_ALLOWED.value(), System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
//        logger.error("Access Denied: ", ex);
//        return new ErrorResponse("You do not have permission to access this resource.",
//                HttpStatus.FORBIDDEN.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
//    public ErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
//        logger.error("File upload size exceeded: ", ex);
//        return new ErrorResponse("The uploaded file exceeds the maximum allowed size.",
//                HttpStatus.PAYLOAD_TOO_LARGE.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleAuthenticationException(AuthenticationException ex) {
//        logger.error("Authentication Error: ", ex);
//        return new ErrorResponse("Authentication failed. Please check your credentials.",
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleBadCredentialsException(BadCredentialsException ex) {
//        logger.error("Invalid Credentials: ", ex);
//        return new ErrorResponse("Invalid username or password.",
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//
//    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
//        logger.error("Authentication Credentials Not Found: ", ex);
//        return new ErrorResponse("No authentication credentials provided.",
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(UnAuthorizedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleAuthorizedFilterException(UnAuthorizedException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(CustomBadRequestException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleBadRequestException(CustomBadRequestException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.NOT_FOUND.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(FileExistsException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleFileExistsException(FileExistsException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(MissingServletRequestPartException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(ConflictException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ErrorResponse handleConflictException(ConflictException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.CONFLICT.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(UserForbiddenException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ErrorResponse handleForbiddenException(UserForbiddenException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.FORBIDDEN.value(),
//                System.currentTimeMillis());
//    }
//
////    @ExceptionHandler(ConstraintViolationException.class)
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
////    public ErrorResponse handleEntityNotValidConstraints(ConstraintViolationException ex) {
////        String firstError = ex.getConstraintViolations().iterator().next().getMessage();
////        return new ErrorResponse(firstError,
////                HttpStatus.BAD_REQUEST.value(),
////                System.currentTimeMillis());
////    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//        String firstError = exception.getBindingResult().getFieldErrors().stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .findFirst()
//                .orElse("Validation error occurred.");
//        return new ErrorResponse(firstError,
//                HttpStatus.BAD_REQUEST.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(TokenNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleTokenNotFoundException(TokenNotFoundException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(PreconditionFailedException.class)
//    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
//    public ErrorResponse handlePreconditionFailedException(PreconditionFailedException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.PRECONDITION_FAILED.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(TokenExpiredException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse handleTokenExpiredException(TokenExpiredException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.UNAUTHORIZED.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
//        String detail = String.format("Parameter '%s' should be of type '%s'", ex.getName(), ex.getRequiredType().getSimpleName());
//        return new ErrorResponse(detail,
//                HttpStatus.BAD_REQUEST.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(NoResourceFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleNoResourceFoundException(NoResourceFoundException ex) {
//        return new ErrorResponse(ex.getMessage(),
//                HttpStatus.NOT_FOUND.value(),
//                System.currentTimeMillis());
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleInternalServerError(Exception ex) {
//        logger.error("Internal Server Error: ", ex);
//     return new ErrorResponse(
//             "An unexpected error occurred: " + ex.getMessage(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                System.currentTimeMillis());
//    }
}