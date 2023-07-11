package org.example.f;

import org.example.a.A;  // dependency NOT detected

public class F {

    void doF() {
        A a = null; // dependency NOT detected
    }
}
