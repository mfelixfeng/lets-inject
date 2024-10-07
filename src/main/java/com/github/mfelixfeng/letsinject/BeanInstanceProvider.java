package com.github.mfelixfeng.letsinject;

public interface BeanInstanceProvider {
    <T> T getInstance(Class<T> beanClass);
}
