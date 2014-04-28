/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial;

import jade.core.Agent;

public class HelloWorldAgent extends Agent {

    protected void setup() {
        System.out.println("Hello World. My name is " + this.getLocalName());
    }
}
