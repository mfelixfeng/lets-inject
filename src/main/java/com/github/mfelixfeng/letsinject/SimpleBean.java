package com.github.mfelixfeng.letsinject;

import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.CreationException;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.DefinitionException;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;

public class SimpleBean<T> implements Bean<T> {
    private Class<T> beanType;
    SimpleBeanManager simpleBeanManager;

    public SimpleBean(Class<T> beanType, SimpleBeanManager simpleBeanManager) {
        this.beanType = beanType;
        this.simpleBeanManager = simpleBeanManager;
    }

    @Override
    public Class<T> getBeanClass() {
        return beanType;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return null;
    }

    @Override
    public T create(CreationalContext<T> creationalContext) {
        try {
            Constructor[] constructors = Arrays.stream(getBeanClass().getDeclaredConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class)).toArray(Constructor[]::new);
            if(constructors.length > 1) {
                throw new DefinitionException("Multiple constructor found in " + getBeanClass().getName());
            }

            Constructor<?> constructor = Arrays.stream(getBeanClass().getDeclaredConstructors())
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst()
                .orElseGet(() -> {
                    try {
                        return getBeanClass().getDeclaredConstructor();
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });

            Object[] params = Arrays.stream(constructor.getParameters()).map(p -> simpleBeanManager.getInstance(p.getType())).toArray();

            return getBeanClass().cast(constructor.newInstance(params));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new CreationException(e);
        }
    }

    @Override
    public void destroy(T t, CreationalContext<T> creationalContext) {

    }

    @Override
    public Set<Type> getTypes() {
        return null;
    }

    @Override
    public Set<Annotation> getQualifiers() {
        return null;
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
        return null;
    }

    @Override
    public boolean isAlternative() {
        return false;
    }
}
