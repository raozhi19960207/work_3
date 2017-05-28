package com.smallsoho.test.aspectj;
import android.provider.Settings;
import android.util.Log;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * Created by raozhi on 2017/5/28.
 */

@Aspect
public class TraceAspect
{
    private String TAG = "TAG ";
    //定义切入点，截获所有方法的执行
    private static final String POINT_METHOD = "execution(* com.hail_hydra.time..*.*(..))";
    private static final String POINT_CALLMETHOD = "call(* com.hail_hydra.time..*.*(..))";
    @Pointcut(POINT_METHOD)
    public void methodAnnotated()
    {
    }
    @Pointcut(POINT_CALLMETHOD)
    public void methodCallAnnotated(){}
    @Around("methodAnnotated()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{

        long start = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        try//打印日志，包括调用时间和调用函数
        {
            Object result = joinPoint.proceed();
            long end= System.currentTimeMillis();
            Log.e(TAG,df.format(new Date())+"\taround:\tUse function:" + joinPoint.toShortString() + "\tUse time : " + (end - start) + " ms!");
            return result;
        }
        catch (Throwable e)
        {
            long end = System.currentTimeMillis();
            Log.e(TAG,df.format(new Date())+"\taround:\tUse function:" + joinPoint.toShortString() + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            throw e;
        }
    }
//    @Before("methodCallAnnotated()")
//    public void beforecall(JoinPoint joinPoint){
//        Log.e(TAG,joinPoint.toShortString());
//    }



}