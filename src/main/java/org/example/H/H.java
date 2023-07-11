package org.example.H;

import org.example.b.B; // dependency detected

public class H {

    public void doH() {
        new B().giveEx(); // dependency detected
    }
}
