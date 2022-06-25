package com.epam.spring.homework2.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.epam.spring.homework2.beans.BeanB;
import com.epam.spring.homework2.beans.BeanC;
import com.epam.spring.homework2.beans.BeanD;
import com.epam.spring.homework2.beans.MyBeanFactoryPostProcessor;
import com.epam.spring.homework2.beans.MyBeanPostProcessor;

@Configuration
@Import(OtherConfig.class)
public class BeansConfig {
	
	@Bean
	public BeanFactoryPostProcessor getMyBeanFactoryPostProcessor() {
		return new MyBeanFactoryPostProcessor();
	}
	
	@Bean
	public BeanPostProcessor getMyBeanPostProcessor() {
		return new MyBeanPostProcessor();
	}
	
	@Bean(initMethod = "customInitMethod", destroyMethod = "customDestroyMethod")
	@ConfigurationProperties(prefix = "bean-b")
	@DependsOn("getBeanD")
	public BeanB getBeanB() {
		return new BeanB();
	}
	
	@Bean(initMethod = "customInitMethod", destroyMethod = "customDestroyMethod")
	@ConfigurationProperties(prefix = "bean-c")
	@DependsOn("getBeanB")
	public BeanC getBeanC() {
		return new BeanC();
	}
	
	@Bean(initMethod = "customInitMethod", destroyMethod = "customDestroyMethod")
	@ConfigurationProperties(prefix = "bean-d")
	public BeanD getBeanD() {
		return new BeanD();
	}

}
