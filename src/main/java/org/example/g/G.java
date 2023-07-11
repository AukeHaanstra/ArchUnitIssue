package org.example.g;

import org.example.b.B; // dependency detected

public class G {

    public void doG() {
        new B().giveA(); // (NOT detected)
        new B().giveA().something(); // dependency detected
    }
}
