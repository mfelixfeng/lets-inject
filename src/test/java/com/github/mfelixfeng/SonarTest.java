package com.github.mfelixfeng;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SonarTest {

    @Test
    void should_run() {
        Assertions.assertEquals("hello sonar", new Sonar().test());
    }
}
