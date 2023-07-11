package org.example.c;

import org.example.a.A; // dependency detected

public class C {

    public void doC() {
        A a = new A(); // dependency detected
    }

}
