package ru.serj.springlessonAlishev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.sql.DriverManager;

// SpringConfig заменяет нам applicationContextMVC.xml
@Configuration
@ComponentScan("ru.serj.springlessonAlishev")// сканирует в указанной папке и в подпапках
@EnableWebMvc // т.к. у нас Spring MVC приложение, которое поддерживает веб функции, эта аннотация равноценна
// тэгу <mvc:annotation-driven/> <!--включает необходимые аннотации для Spring MVC приложения-->
public class SpringConfig implements WebMvcConfigurer {
// implements WebMvcConfigurer с этим интерфейсом мы реализуем метод configureViewResolvers этот интерфейс реализуется
// в том случаем когда мы хотим под себя настроить Spring VMC,
// в данном случае мы хотим вместо стандартного шаблонизатора использовать шаблонизатор Thymeleaf
    private final ApplicationContext applicationContext;
// внедряем applicationContext с помощью @Autowired
    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        //используем тут бин контекста чтобы настроить Thymeleaf
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");// папка с нашими представлениями
        templateResolver.setSuffix(".html");//их расширения
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {//бин где производим конфигурацию наших представлений
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
//передаем инфу спринг мвс что хотим использовать шаблонизатор Thymeleaf
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }
    //Создадим bean jdbcTemplate который будем внедрять в классы работающие с БД
    //для этого нам нужен еще один bean DataSource, он нужен jdbcTemplate чтобы указать к какой БД подключаться
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //здесь укажем конфигурацию нашей БД для dataSource
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/first_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("PostgreSQL");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}
