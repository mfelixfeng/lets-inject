package com.github.mfelixfeng.letsinject;

import jakarta.el.ELResolver;
import jakarta.el.ExpressionFactory;
import jakarta.enterprise.context.spi.Context;
import jakarta.enterprise.context.spi.Contextual;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.UnsatisfiedResolutionException;
import jakarta.enterprise.inject.spi.AnnotatedField;
import jakarta.enterprise.inject.spi.AnnotatedMember;
import jakarta.enterprise.inject.spi.AnnotatedMethod;
import jakarta.enterprise.inject.spi.AnnotatedParameter;
import jakarta.enterprise.inject.spi.AnnotatedType;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanAttributes;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.Decorator;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.inject.spi.InjectionTargetFactory;
import jakarta.enterprise.inject.spi.InterceptionFactory;
import jakarta.enterprise.inject.spi.InterceptionType;
import jakarta.enterprise.inject.spi.Interceptor;
import jakarta.enterprise.inject.spi.ObserverMethod;
import jakarta.enterprise.inject.spi.ProducerFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleBeanManager implements BeanManager, BeanInstanceProvider {
    @Override
    public Object getInjectableReference(InjectionPoint injectionPoint, CreationalContext<?> creationalContext) {
        return null;
    }

    @Override
    public Bean<?> getPassivationCapableBean(String s) {
        return null;
    }

    @Override
    public void validate(InjectionPoint injectionPoint) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Decorator<?>> resolveDecorators(Set<Type> set, Annotation... annotations) {
        return Collections.emptyList();
    }

    @Override
    public boolean isPassivatingScope(Class<? extends Annotation> aClass) {
        return false;
    }

    @Override
    public Set<Annotation> getInterceptorBindingDefinition(Class<? extends Annotation> aClass) {
        return Collections.emptySet();
    }

    @Override
    public Set<Annotation> getStereotypeDefinition(Class<? extends Annotation> aClass) {
        return Collections.emptySet();
    }

    @Override
    public boolean areQualifiersEquivalent(Annotation annotation, Annotation annotation1) {
        return false;
    }

    @Override
    public boolean areInterceptorBindingsEquivalent(Annotation annotation, Annotation annotation1) {
        return false;
    }

    @Override
    public int getQualifierHashCode(Annotation annotation) {
        return 0;
    }

    @Override
    public int getInterceptorBindingHashCode(Annotation annotation) {
        return 0;
    }

    @Deprecated(since="4.1", forRemoval=true)
    @Override
    public ELResolver getELResolver() {
        return null;
    }

    @Deprecated(forRemoval=true)
    @Override
    public ExpressionFactory wrapExpressionFactory(ExpressionFactory expressionFactory) {
        return null;
    }

    @Override
    public <T> AnnotatedType<T> createAnnotatedType(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> InjectionTargetFactory<T> getInjectionTargetFactory(AnnotatedType<T> annotatedType) {
        return null;
    }

    @Override
    public <X> ProducerFactory<X> getProducerFactory(AnnotatedField<? super X> annotatedField, Bean<X> bean) {
        return null;
    }

    @Override
    public <X> ProducerFactory<X> getProducerFactory(AnnotatedMethod<? super X> annotatedMethod, Bean<X> bean) {
        return null;
    }

    @Override
    public <T> BeanAttributes<T> createBeanAttributes(AnnotatedType<T> annotatedType) {
        return null;
    }

    @Override
    public BeanAttributes<?> createBeanAttributes(AnnotatedMember<?> annotatedMember) {
        return null;
    }

    @Override
    public <T> Bean<T> createBean(BeanAttributes<T> beanAttributes, Class<T> aClass, InjectionTargetFactory<T> injectionTargetFactory) {
        return null;
    }

    @Override
    public <T, X> Bean<T> createBean(BeanAttributes<T> beanAttributes, Class<X> aClass, ProducerFactory<X> producerFactory) {
        return null;
    }

    @Override
    public InjectionPoint createInjectionPoint(AnnotatedField<?> annotatedField) {
        return null;
    }

    @Override
    public InjectionPoint createInjectionPoint(AnnotatedParameter<?> annotatedParameter) {
        return null;
    }

    @Override
    public <T extends Extension> T getExtension(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> InterceptionFactory<T> createInterceptionFactory(CreationalContext<T> creationalContext, Class<T> aClass) {
        return null;
    }

    @Override
    public Object getReference(Bean<?> bean, Type type, CreationalContext<?> creationalContext) {
        return ((Bean<Object>) bean).create((CreationalContext<Object>) creationalContext);
    }

    @Override
    public <T> CreationalContext<T> createCreationalContext(Contextual<T> contextual) {
        return null;
    }

    @Override
    public Set<Bean<?>> getBeans(Type type, Annotation... annotations) {
        if (!beans.containsKey(type)) {
            return Collections.emptySet();
        }

        Bean<?> bean = beans.get(type);
        return Set.of(bean);
    }

    @Override
    public Set<Bean<?>> getBeans(String s) {
        return Collections.emptySet();
    }

    @Override
    public <X> Bean<? extends X> resolve(Set<Bean<? extends X>> set) {
        return null;
    }

    @Override
    public <T> Set<ObserverMethod<? super T>> resolveObserverMethods(T t, Annotation... annotations) {
        return Collections.emptySet();
    }

    @Override
    public List<Interceptor<?>> resolveInterceptors(InterceptionType interceptionType, Annotation... annotations) {
        return Collections.emptyList();
    }

    @Override
    public boolean isScope(Class<? extends Annotation> aClass) {
        return false;
    }

    @Override
    public boolean isNormalScope(Class<? extends Annotation> aClass) {
        return false;
    }

    @Override
    public boolean isQualifier(Class<? extends Annotation> aClass) {
        return false;
    }

    @Override
    public boolean isStereotype(Class<? extends Annotation> aClass) {
        return false;
    }

    @Override
    public boolean isInterceptorBinding(Class<? extends Annotation> aClass) {
        return false;
    }

    @Override
    public Context getContext(Class<? extends Annotation> aClass) {
        return null;
    }

    @Override
    public Collection<Context> getContexts(Class<? extends Annotation> aClass) {
        return Collections.emptyList();
    }

    @Override
    public Event<Object> getEvent() {
        return null;
    }

    @Override
    public Instance<Object> createInstance() {
        return null;
    }

    @Override
    public boolean isMatchingBean(Set<Type> set, Set<Annotation> set1, Type type, Set<Annotation> set2) {
        return false;
    }

    @Override
    public boolean isMatchingEvent(Type type, Set<Annotation> set, Type type1, Set<Annotation> set1) {
        return false;
    }

    Map<Type, Bean<?>> beans = new HashMap<>();

    public void register(Class<?> beanClass) {
        beans.put(beanClass, new SimpleBean<>(beanClass, this));
    }

    @Override
    public <T> T getInstance(Class<T> beanClass) {
        Bean<?> bean = beans.get(beanClass);
        if (bean == null) {
            throw new UnsatisfiedResolutionException("No bean registered for :" + beanClass.getName());
        }
        return beanClass.cast(getReference(bean, beanClass, createCreationalContext(bean)));
    }
}
