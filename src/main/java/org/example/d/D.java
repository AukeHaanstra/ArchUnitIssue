package org.example.d;

import lombok.extern.slf4j.Slf4j;
import org.example.a.Ex; // dependency NOT detected
import org.example.b.B;

@Slf4j
public class D {

    public void doD() {
        B b = new B();
        try {
            b.doB();
        } catch (Ex ex) { // dependency NOT detected
            log.info("Exception from ex", ex);
        }
    }
}
