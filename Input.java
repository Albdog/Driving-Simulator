/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package driving.simulator.physics;

import java.util.Scanner;

/**
 *
 * @author Joaquin
 */
class Input implements Runnable {
    Scanner scanner = new Scanner(System.in);
    public Input() {
        
    }

    @Override
    public void run() {
        while(true) {
            int speed = scanner.nextInt();
            //continue here 8===D
        }
    }
    
}
