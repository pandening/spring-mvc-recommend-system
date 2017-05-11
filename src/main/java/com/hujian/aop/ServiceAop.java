package com.hujian.aop;

import com.hujian.star.log.impl.SampleLocalLogServiceImpl;
import com.hujian.star.log.utils.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by hujian on 2017/5/11.
 */
@Component
@Aspect
public class ServiceAop implements Serializable {

    @Autowired
    private SampleLocalLogServiceImpl sampleLocalLogHandler = null;

    /**
     * the star
     */
    @Pointcut("execution(* com.hujian.*.services.*..*(..))")
    public void aspect(){
    }

    /**
     * before calling the services
     * for the methods call-time statistic info.
     */
    @Before("aspect()")
    public void BeforeCallServices(JoinPoint joinPoint){
        String dec = joinPoint.getSignature().getDeclaringType().getName();
        String method = joinPoint.getSignature().getName();
        String args = "";
        for( Object arg : joinPoint.getArgs() ){
            args += arg.toString() + " ";
        }
        //do the log,then we can read the record from log to do some statistics
        //such as the call count of the methods
        String log = "[" + LogUtils.getTime() + "]\t" + method + "\t" + args + "\t" + dec + "\n";
        sampleLocalLogHandler.writeLog(log,"aop_info");
    }

}
