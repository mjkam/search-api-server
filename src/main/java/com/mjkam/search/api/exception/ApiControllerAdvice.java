package com.mjkam.search.api.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException ex) {
        log.info("Request Parameter Constraint Exception: {}", ex.getMessage(), ex);
        return  ExceptionResponse.of(ExceptionType.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionResponse handleConstraintViolationException(MethodArgumentTypeMismatchException ex) {
        log.info("Request Parameter Constraint Exception: {}", ex.getMessage(), ex);
        return  ExceptionResponse.of(ExceptionType.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return  ExceptionResponse.of(ExceptionType.BAD_REQUEST);
    }














//    private final MessageSource messageSource;
//    private final ViolationMessageResolver violationMessageResolver;

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Object> handle(ConstraintViolationException e) {
//        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//        for (ConstraintViolation<?> c: constraintViolations) {
//            System.out.println(message(c));
//        }
//        for (ConstraintViolation<?> c: constraintViolations) {
//            System.out.println(c.getConstraintDescriptor().getAnnotation());
//            System.out.println(c.getRootBeanClass().getSimpleName());
//            String rootBeanName = c.getRootBeanClass().getSimpleName();
//            rootBeanName = rootBeanName.substring(0, 1).toLowerCase() + rootBeanName.substring(1);
//            System.out.println(rootBeanName);
//            System.out.println(c.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
////            System.out.println(c.getConstraintDescriptor().getAnnotation().annotationType().getAnnotations());
//        }
//        System.out.println(e.getMessage());
//        return ResponseEntity.ok().build();
//    }

//    public String message(ConstraintViolation<?> violation) {
//        ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
//        Map<String, Object> attributes = descriptor.getAttributes();
//
//        String annotationName = descriptor.getAnnotation().annotationType().getSimpleName();
//        String rootBeanName = violation.getRootBeanClass().getSimpleName();
//        rootBeanName = rootBeanName.substring(0, 1).toLowerCase() + rootBeanName.substring(1);
//        String path = violation.getPropertyPath().toString();
//
//        DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
//        // 애노테이션, 클래스, 필드 조합으로 코드 생성
//        String[] codes = codesResolver.resolveMessageCodes(
//                annotationName, rootBeanName, path, null);
//
//        String result = null;
//        for (String code : codes) {
//            System.out.println("code: " + code);
//            try {
//                // 코드로 메시지 조회
//                result = messageSource.getMessage(code, null, Locale.getDefault());
//                for (Map.Entry<String, Object> es : attributes.entrySet()) {
//                    // 애노테이션 attribute 를 기준으로 {...} 형태의 메시지 치환.
//                    result = result.replace(
//                            "{" + es.getKey() + "}", es.getValue().toString());
//                }
//
//                // 검증 대상값 치환
//                result = result.replace("{validatedValue}",
//                        violation.getInvalidValue().toString());
//            } catch (NoSuchMessageException ignored) {
//                System.out.println("~~~~~");
//            }
//        }
//        // code 로 메시지를 찾지 못한 경우 기본값 사용
//        if (result == null) {
//            result = violation.getMessage();
//        }
//        return result;
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<Object> handle(MethodArgumentTypeMismatchException e) {
//        System.out.println("===");
//        System.out.println(e.getParameter().getParameter().getName());
//        System.out.println(e.getParameter().getParameterName());
//        System.out.println(e.getParameter().getContainingClass().getSimpleName());
//        System.out.println(e.getParameter().getContainingClass().getName());
//        System.out.println(e.getParameter().getAnnotatedElement().getAnnotations()[0].annotationType());
//        System.out.println(e.get);
//        violationMessageResolver.message(e.get());
//        return ResponseEntity.ok().build();
//    }
//    MethodArgumentTypeMismatchException

//    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConstraintViolationException.class})
//    public ResponseEntity<Object> handleBadRequest(Exception e) {
////        violationMessageResolver.message(e.)
//
//        log.error("BAD REQUEST : {}", e.getMessage(), e);
//        return ResponseEntity.ok().build();
//    }


}