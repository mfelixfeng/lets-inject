package com.github.mfelixfeng.letsinject;

import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.CreationException;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.DefinitionException;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class SimpleBean<T> implements Bean<T> {
    private final Class<T> beanType;
    private final BeanInstanceProvider beanInstanceProvider;

    public SimpleBean(Class<T> beanType, BeanInstanceProvider beanInstanceProvider) {
        this.beanType = beanType;
        this.beanInstanceProvider = beanInstanceProvider;
    }

    @Override
    public Class<T> getBeanClass() {
        return beanType;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return Collections.emptySet();
    }

    @Override
    public T create(CreationalContext<T> creationalContext) {
        try {
            Constructor<?> constructor = resolveInjectableConstructor();

            return injectField(instantiateBean(constructor, resolveConstructorParams(constructor)));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new CreationException(e);
        }
    }

    private T injectField(T instance) throws IllegalAccessException {
        for (Field field : getBeanClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object value = beanInstanceProvider.getInstance(field.getType());
                field.setAccessible(true);
                field.set(instance, value);
            }
        }

        return instance;
    }

    private T instantiateBean(Constructor<?> constructor, Object[] params) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return getBeanClass().cast(constructor.newInstance(params));
    }

    private Object[] resolveConstructorParams(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameters()).map(p -> beanInstanceProvider.getInstance(p.getType())).toArray();
    }

    private Constructor<?> resolveInjectableConstructor() {
        Constructor<?>[] constructors = findConstructorsWithInInjectAnnotation();

        if (constructors.length > 1) {
            throw new DefinitionException("Multiple constructor found in " + getBeanClass().getName());
        }

        if (constructors.length == 1) {
            return constructors[0];
        }

        return resolveDefaultConstructor();
    }

    private Constructor<T> resolveDefaultConstructor() {
        try {
            return getBeanClass().getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new DefinitionException("No default constructor found in " + getBeanClass().getName(), e);
        }
    }

    private Constructor<?>[] findConstructorsWithInInjectAnnotation() {
        return Arrays.stream(getBeanClass().getDeclaredConstructors())
            .filter(c -> c.isAnnotationPresent(Inject.class)).toArray(Constructor[]::new);
    }

    @Override
    public void destroy(T t, CreationalContext<T> creationalContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Type> getTypes() {
        return Collections.emptySet();
    }

    @Override
    public Set<Annotation> getQualifiers() {
        return Collections.emptySet();
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return Collections.emptySet();
    }

    @Override
    public boolean isAlternative() {
        return false;
    }
}
