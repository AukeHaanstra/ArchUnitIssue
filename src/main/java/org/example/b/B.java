package org.example.b;

import org.example.a.A;
import org.example.a.Ex;

public class B {

    public void doB() throws Ex {
        throw new Ex();
    }

    public A giveA() {
        return new A();
    }

    public Ex giveEx() {
        return new Ex();
    }


}
