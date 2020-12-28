package cl.tenpo.desafio.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Este Aspecto loggea información acerca de cada request.
 * Es últil para implementar observabilidad sobre la aplicación.
 *
 * Created by martin.saporiti
 * on 27/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Around("execution(* cl.tenpo.desafio.web.controller.*.*(..))")
    public Object log(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = null;
        Object value;
        try {

            request = ((ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes())
                    .getRequest();

            value = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(
                    "{} {} from {} - Error: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    throwable.getMessage());
            throw throwable;
        } finally {
            log.info(
                    "{} {} from {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr());
        }
        return value;
    }

}
