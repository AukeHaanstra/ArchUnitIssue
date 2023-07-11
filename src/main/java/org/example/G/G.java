package org.example.G;

import org.example.b.B; // dependency detected

public class G {

    public void doG() {
        new B().giveA(); // dependency detected
    }
}
