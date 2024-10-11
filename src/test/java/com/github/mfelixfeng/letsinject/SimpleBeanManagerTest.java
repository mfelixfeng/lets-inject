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
        void should_inject_dependence() {
            simpleBeanManager.register(BeanWithDependence.class);
            simpleBeanManager.register(DependentBean.class);

            BeanWithDependence instance = simpleBeanManager.getInstance(BeanWithDependence.class);
            assertNotNull(instance);
            assertNotNull(instance.dependence);
        }

        @Test
        void should_inject_multiple_dependencies() {
            simpleBeanManager.register(BeanWithMultipleDependencies.class);
            simpleBeanManager.register(DependentBean.class);
            simpleBeanManager.register(AnotherDependentBean.class);

            BeanWithMultipleDependencies instance = simpleBeanManager.getInstance(BeanWithMultipleDependencies.class);
            assertNotNull(instance);
            assertNotNull(instance.dependence);
            assertNotNull(instance.anotherDependentBean);
        }

        @Test
        void should_throws_exception_for_not_found_dependence() {
            simpleBeanManager.register(BeanWithDependence.class);

            assertThrows(UnsatisfiedResolutionException.class, () -> simpleBeanManager.getInstance(BeanWithDependence.class));
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
                //for test
            }

            @Inject
            public BeanWithMultipleConstructor(String value) {
                //for test
            }
        }

        static class BeanWithDependence {
            private DependentBean dependence;

            @Inject
            public BeanWithDependence(DependentBean dependence) {
                this.dependence = dependence;
            }
        }

        static class BeanWithMultipleDependencies {
            private DependentBean dependence;
            private AnotherDependentBean anotherDependentBean;

            @Inject
            public BeanWithMultipleDependencies(DependentBean dependence, AnotherDependentBean anotherDependentBean) {
                this.dependence = dependence;
                this.anotherDependentBean = anotherDependentBean;
            }
        }
    }

    @Nested
    class FieldInjectTest {
        @Test
        void should_inject_dependence() {
            simpleBeanManager.register(BeanWithFieldDependence.class);
            simpleBeanManager.register(DependentBean.class);

            BeanWithFieldDependence instance = simpleBeanManager.getInstance(BeanWithFieldDependence.class);

            assertNotNull(instance);
            assertNotNull(instance.dependence);
        }

        @Test
        void should_inject_multiple_dependencies() {
            simpleBeanManager.register(BeanWithFieldDependencies.class);
            simpleBeanManager.register(DependentBean.class);
            simpleBeanManager.register(AnotherDependentBean.class);

            BeanWithFieldDependencies instance = simpleBeanManager.getInstance(BeanWithFieldDependencies.class);

            assertNotNull(instance);
            assertNotNull(instance.dependence);
            assertNotNull(instance.anotherDependentBean);

        }

        @Test
        void should_inject_private_dependence() {
            simpleBeanManager.register(BeanWithPrivateFieldDependence.class);
            simpleBeanManager.register(DependentBean.class);

            BeanWithPrivateFieldDependence instance = simpleBeanManager.getInstance(BeanWithPrivateFieldDependence.class);

            assertNotNull(instance);
            assertNotNull(instance.dependence);
        }

        @Test
        void should_inject_protected_dependence() {
            simpleBeanManager.register(BeanWithProtectedFieldDependence.class);
            simpleBeanManager.register(DependentBean.class);

            BeanWithProtectedFieldDependence instance = simpleBeanManager.getInstance(BeanWithProtectedFieldDependence.class);

            assertNotNull(instance);
            assertNotNull(instance.dependence);
        }

        @Test
        void should_throws_exception_for_not_found_dependence() {
            simpleBeanManager.register(BeanWithFieldDependence.class);

            assertThrows(UnsatisfiedResolutionException.class, () -> simpleBeanManager.getInstance(BeanWithFieldDependence.class));
        }


        static class BeanWithPrivateFieldDependence {
            @Inject
            private DependentBean dependence;
        }

        static class BeanWithProtectedFieldDependence {
            @Inject
            protected DependentBean dependence;
        }

        static class BeanWithFieldDependence {
            @Inject
            DependentBean dependence;
        }

        static class BeanWithFieldDependencies {
            @Inject
            DependentBean dependence;

            @Inject
            AnotherDependentBean anotherDependentBean;
        }
    }

    @Nested
    class InitializerMethodInjectTest {

        @Test
        void should_inject_dependence() {
            simpleBeanManager.register(BeanWithInitializerMethod.class);
            simpleBeanManager.register(DependentBean.class);

            BeanWithInitializerMethod instance = simpleBeanManager.getInstance(BeanWithInitializerMethod.class);
            assertNotNull(instance);
            assertNotNull(instance.dependence);
        }

        @Test
        void should_inject_multiple_dependence() {
            simpleBeanManager.register(BeanWithMultipleInitializerMethod.class);
            simpleBeanManager.register(DependentBean.class);
            simpleBeanManager.register(AnotherDependentBean.class);

            BeanWithMultipleInitializerMethod instance = simpleBeanManager.getInstance(BeanWithMultipleInitializerMethod.class);
            assertNotNull(instance);
            assertNotNull(instance.dependence);
            assertNotNull(instance.anotherDependentBean);
        }

        @Test
        void should_inject_with_multiple_parameters() {
            simpleBeanManager.register(BeanWithInitializerMethodAndMultipleParameters.class);
            simpleBeanManager.register(DependentBean.class);
            simpleBeanManager.register(AnotherDependentBean.class);

            BeanWithInitializerMethodAndMultipleParameters instance = simpleBeanManager.getInstance(BeanWithInitializerMethodAndMultipleParameters.class);
            assertNotNull(instance);
            assertNotNull(instance.dependence);
            assertNotNull(instance.anotherDependentBean);
        }

        @Test
        void should_throws_exception_for_not_found_dependence() {
            simpleBeanManager.register(BeanWithInPrivateInitializerMethod.class);

            assertThrows(UnsatisfiedResolutionException.class, () -> simpleBeanManager.getInstance(BeanWithInPrivateInitializerMethod.class));
        }

        @Test
        void should_inject_private_dependence() {
            simpleBeanManager.register(BeanWithInPrivateInitializerMethod.class);
            simpleBeanManager.register(DependentBean.class);

            BeanWithInPrivateInitializerMethod instance = simpleBeanManager.getInstance(BeanWithInPrivateInitializerMethod.class);
            assertNotNull(instance);
            assertNotNull(instance.dependence);
        }

        @Test
        void should_throws_exception_for_failing_initializer_method() {
            simpleBeanManager.register(BeanWithFailingInitializerMethod.class);
            simpleBeanManager.register(DependentBean.class);

            assertThrows(CreationException.class, () -> simpleBeanManager.getInstance(BeanWithFailingInitializerMethod.class));
        }

        static class BeanWithFailingInitializerMethod {
            @Inject
            private void setDependence(DependentBean dependence) {
                throw new RuntimeException();
            }
        }

        static class BeanWithInPrivateInitializerMethod {
            private DependentBean dependence;

            @Inject
            private void setDependence(DependentBean dependence) {
                this.dependence = dependence;
            }
        }

        static class BeanWithInitializerMethod {
            private DependentBean dependence;

            @Inject
            public void setDependence(DependentBean dependence) {
                this.dependence = dependence;
            }
        }

        static class BeanWithInitializerMethodAndMultipleParameters {
            private DependentBean dependence;
            private AnotherDependentBean anotherDependentBean;

            @Inject
            public void setDependence(DependentBean dependence, AnotherDependentBean anotherDependentBean) {
                this.dependence = dependence;
                this.anotherDependentBean = anotherDependentBean;
            }
        }

        static class BeanWithMultipleInitializerMethod {
            private DependentBean dependence;
            private AnotherDependentBean anotherDependentBean;

            @Inject
            public void setDependence(DependentBean dependence) {
                this.dependence = dependence;
            }

            @Inject
            public void setAnotherDependence(AnotherDependentBean anotherDependentBean) {
                this.anotherDependentBean = anotherDependentBean;
            }
        }
    }

    static class DependentBean {

    }

    static class AnotherDependentBean {

    }
}
