package com.stone.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.junit.Test;

public class ProxyTest {

	@Test
	public void test() {
		Calculator target = new CalculatorImpl();
		Calculator proxy = (Calculator) Proxy.newProxyInstance(
				target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("The method " + method.getName() + " begins with " + Arrays.asList(args));
						
						Object result = method.invoke(target, args);
						
						System.out.println("The method " + method.getName() + " ends with " + result);
						return result;
					}
				});
		System.out.println(proxy.add(1, 3));
		System.out.println(proxy.sub(3, 1));
	}
}