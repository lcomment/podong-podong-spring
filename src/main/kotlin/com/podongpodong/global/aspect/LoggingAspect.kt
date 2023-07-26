package com.podongpodong.global.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import java.lang.reflect.Method

@Aspect
@Component
class LoggingAspect(
    private val log: Logger = LoggerFactory.getLogger("AOP LOGGER")
) {

    @Pointcut("execution(* com.podongpodong.domain..*Controller.*(..)) || execution(* com.podongpodong.global.oauth2.handler.*.*(..))")
    fun cut(): Unit {
    }

    @Before("cut()")
    fun beforeParameterLog(joinPoint: JoinPoint) {
        val method: Method = getMethod(joinPoint)
        log.info("======= method name = {} =======", method.name)

        val args: Array<Any>? = joinPoint.args

        args?.run {
            for (arg in this) {
                log.info("parameter type = {}", arg.javaClass.simpleName)
                log.info("parameter value = {}", arg)
            }
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    fun afterReturnLog(joinPoint: JoinPoint, returnObj: Any?) {
        val method = getMethod(joinPoint)

        returnObj?.run {
            log.info("======= method name = {} =======", method.name)
            log.info("return type = {}", this.javaClass.simpleName)
            log.info("return value = {}", this)
        }
    }

    private fun getMethod(joinPoint: JoinPoint): Method {
        val signature: MethodSignature = joinPoint.signature as MethodSignature
        return signature.method
    }
}