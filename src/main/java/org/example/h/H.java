package org.example.h;

import org.example.a.Ex;
import org.example.b.B; // dependency NOT detected

public class H {

    public void doH() {
        new B().giveEx(); // dependency NOT detected
        Ex ex = new B().giveEx(); // dependency NOT detected
    }
}
