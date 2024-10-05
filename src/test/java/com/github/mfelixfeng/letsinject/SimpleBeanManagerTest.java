package com.github.mfelixfeng.letsinject;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.mfelixfeng.letsinject.SimpleBeanManager;
import jakarta.enterprise.inject.UnsatisfiedResolutionException;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.DefinitionException;
import jakarta.inject.Inject;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleBeanManagerTest {

    private SimpleBeanManager simpleBeanManager;

    @BeforeEach
    void setup() {
        simpleBeanManager = new SimpleBeanManager();
    }

    @Test
    void should_register_and_get_bean() {
        simpleBeanManager.register(TestBean.class);

        Set<Bean<?>> beans = simpleBeanManager.getBeans(TestBean.class);
        assertEquals(1, beans.size());

        Bean<?> retriveBean = beans.iterator().next();
        assertEquals(TestBean.class, retriveBean.getBeanClass());
    }

    @Test
    void should_return_nothing_given_unknown_type() {
        Set<Bean<?>> beans = simpleBeanManager.getBeans(TestBean.class);
        assertTrue(beans.isEmpty());
    }

    @Test
    void should_get_reference() {
        simpleBeanManager.register(TestBean.class);
        Bean<?> bean = simpleBeanManager.getBeans(TestBean.class).iterator().next();

        Object reference = simpleBeanManager.getReference(bean, TestBean.class, simpleBeanManager.createCreationalContext(bean));

        assertNotNull(reference);
        assertTrue(reference instanceof TestBean);
    }

    @Test
    void should_get_instance() {
        simpleBeanManager.register(TestBean.class);

        Object instance = simpleBeanManager.getInstance(TestBean.class);

        assertNotNull(instance);
        assertTrue(instance instanceof TestBean);
    }

    @Test
    void should_throws_exception_for_unregistered_bean() {
        assertThrows(UnsatisfiedResolutionException.class, () -> simpleBeanManager.getInstance(TestBean.class));
    }

    @Test
    void should_inject_dependence_via_constructor() {
        simpleBeanManager.register(BeanWithDependencies.class);
        simpleBeanManager.register(DependentBean.class);

        BeanWithDependencies instance = simpleBeanManager.getInstance(BeanWithDependencies.class);
        assertNotNull(instance);
        assertTrue(instance.dependence instanceof DependentBean);
    }

    @Test
    void should_throws_exception_for_not_found_dependence() {
        simpleBeanManager.register(BeanWithDependencies.class);

        assertThrows(UnsatisfiedResolutionException.class, ()-> simpleBeanManager.getInstance(BeanWithDependencies.class));
    }

    @Test
    void should_throws_exception_for_multiple_constructor() {
        simpleBeanManager.register(BeanWithMultipleConstructor.class);

        assertThrows(DefinitionException.class, ()-> simpleBeanManager.getInstance(BeanWithMultipleConstructor.class));
    }



    static class TestBean {

    }

    static class BeanWithMultipleConstructor {
        @Inject
        public BeanWithMultipleConstructor(DependentBean dependence) {
        }

        @Inject
        public BeanWithMultipleConstructor(String value) {
        }
    }

    static class BeanWithDependencies {
        private DependentBean dependence;

        @Inject
        public BeanWithDependencies(DependentBean dependence) {
            this.dependence = dependence;
        }
    }

    static class DependentBean {

    }
}
