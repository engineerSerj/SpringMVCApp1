package ru.serj.springlessonAlishev.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//AbstractAnnotationConfigDispatcherServletInitializer реализует интерфейс WebApplicationInitializer поэтому можем им заменить web.xml
public class MySpringDispatcherServletInitialaizer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    //в этих классах должны передать какую то конфигурацию, конкретно в этот класс должны передать класс java замену applicationContextMVC.xml(spring config)
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }
}
