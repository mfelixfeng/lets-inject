package com.github.mfelixfeng.letsinject;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.enterprise.inject.CreationException;
import jakarta.enterprise.inject.UnsatisfiedResolutionException;
import jakarta.enterprise.inject.spi.DefinitionException;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SimpleBeanManagerTest {

    private SimpleBeanManager simpleBeanManager;

    @BeforeEach
    void setup() {
        simpleBeanManager = new SimpleBeanManager();
    }

    @Nested
    class ConstructorInjectTest {
        @Test
        void should_inject_dependence_via_constructor() {
            simpleBeanManager.register(BeanWithDependencies.class);
            simpleBeanManager.register(DependentBean.class);

            BeanWithDependencies instance = simpleBeanManager.getInstance(BeanWithDependencies.class);
            assertNotNull(instance);
        }

        @Test
        void should_throws_exception_for_not_found_dependence() {
            simpleBeanManager.register(BeanWithDependencies.class);

            assertThrows(UnsatisfiedResolutionException.class, () -> simpleBeanManager.getInstance(BeanWithDependencies.class));
        }

        @Test
        void should_throws_exception_for_multiple_with_in_inject_annotation_constructor() {
            simpleBeanManager.register(BeanWithMultipleConstructor.class);

            assertThrows(DefinitionException.class, () -> simpleBeanManager.getInstance(BeanWithMultipleConstructor.class));
        }

        @Test
        void should_throws_exception_for_no_default_constructor_bean() {
            simpleBeanManager.register(NoDefaultConstructorBean.class);

            assertThrows(DefinitionException.class, () -> simpleBeanManager.getInstance(NoDefaultConstructorBean.class));
        }

        @Test
        void should_throws_exception_for_private_default_constructor_bean() {
            simpleBeanManager.register(PrivateConstructorBean.class);

            assertThrows(CreationException.class, () -> simpleBeanManager.getInstance(PrivateConstructorBean.class));
        }

        static class PrivateConstructorBean {
            private PrivateConstructorBean() {
            }

        }

        static class NoDefaultConstructorBean {
            public NoDefaultConstructorBean(String value) {

            }
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
    }

    static class TestBean {

    }

    static class DependentBean {

    }
}
