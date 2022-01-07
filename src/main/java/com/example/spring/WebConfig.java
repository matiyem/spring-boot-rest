package com.example.spring;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/10/2021
 * Time: 2:48 PM
 */
//اگر از SpringBootApliation@ استفاده میکنیم و کتابخانه های spring-web-mvc در clathpath وجود دارد،EnableWebMvc@ بصورت خودکار به auto configuration اضافه میشود
//همچنین میتوانیم با پیاده سازی اینترفیس webMvcConfigurer عملکرد mvc را اضافه کنیم
@Configuration
public class WebConfig implements WebMvcConfigurer {


    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //برای تنظیمات rset باید یا از mvc استفاده کنیم و یا از http message converter استفاده میشود
        //تنظیمات mvc مدل قدیمی است و دارای تنظیمات زیاد و سنگین است در صورتی مدل convertor سبک است و پیاده سازی آن ساده است
        converters.add(new MappingJackson2CborHttpMessageConverter());
        converters.add(createXmlHttpMessageConverter());
    }

    private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xmlConverter.setMarshaller(xStreamMarshaller);
        xmlConverter.setUnmarshaller(xStreamMarshaller);
        return xmlConverter;
    }

    // Etags

    // If we're not using Spring Boot we can make use of
    // AbstractAnnotationConfigDispatcherServletInitializer#getServletFilters
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter(){
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean=
                new FilterRegistrationBean<>(new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("/foo/*");
        filterRegistrationBean.setName("etagFilter");
        return filterRegistrationBean;
    }

    // We can also just declare the filter directly
    // @Bean
    // public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
    // return new ShallowEtagHeaderFilter();
    // }
}
