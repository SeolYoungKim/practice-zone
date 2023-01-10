package com.study.tobyspringpractice.learning_test;

import static org.assertj.core.api.Assertions.*;

import com.study.tobyspringpractice.dependency.constructor.MemberRepositoryImpl;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class JUnitTest {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MemberRepositoryImpl autowiredMemberRepositoryImpl;

    static Set<JUnitTest> testObjects = new HashSet<>();
    static ApplicationContext context = null;


    @Test
    void objTest1() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(context == null || context == this.applicationContext).isTrue();
        context = this.applicationContext;
        System.out.println(context);
    }

    @Test
    void objTest2() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(context == null || context == this.applicationContext).isTrue();
        context = this.applicationContext;
        System.out.println(context);
    }

    @Test
    void objTest3() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(context == null || context == this.applicationContext).isTrue();
        context = this.applicationContext;
        System.out.println(context);
    }

    @Test
    void getBeanFail() {
        assertThatThrownBy(() -> applicationContext.getBean(DataSource.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Test
    void getBeanSuccess() {
        MemberRepositoryImpl bean = applicationContext.getBean(MemberRepositoryImpl.class);
        assertThat(bean).extracting("name").isEqualTo("thisIsMemberRepositoryBean");
    }

    @Test
    void getBeanEqualTo() {
        MemberRepositoryImpl beanByGetBean = applicationContext.getBean(MemberRepositoryImpl.class);
        assertThat(beanByGetBean).isSameAs(autowiredMemberRepositoryImpl);
    }
}
