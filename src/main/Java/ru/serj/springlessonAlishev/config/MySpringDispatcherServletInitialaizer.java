package ru.serj.springlessonAlishev.config;
import org.springframework.web.filter.CharacterEncodingFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//AbstractAnnotationConfigDispatcherServletInitializer реализует интерфейс WebApplicationInitializer поэтому можем им заменить web.xml
// это класс который исполняет роль Spring  web.xml
public class MySpringDispatcherServletInitialaizer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerCharacterEncodingFilter(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }

    private void registerCharacterEncodingFilter(ServletContext aContext) {
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = aContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }

    @Override
    //использовать небудем, возвращаем null
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    //в этих классах должны передать какую то конфигурацию, конкретно в этот класс должны передать класс java замену applicationContextMVC.xml(spring config)
    protected Class<?>[] getServletConfigClasses() {
        //подставляем наш Spring config класс
        return new Class[] {SpringConfig.class}; // создаем массив типа Class и инициализируем его одни нашим Spring config классом
    }

    @Override
    //эквивалентно что мы в файле web.xml все http запросы пользователя посылаем на диспетчер сервлет
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
//и добавим зависимость javax Servlet API в pom эта зависимость используется AbstractAnnotationConfigDispatcherServletInitializer