/**
 * Created by yangmengfei on 2017/12/21.
 */

//package concurrency.waxomatic;

import java.io.*;

import java.util.concurrent.*;



//import static net.mindview.util.Print.*;



class Car {

    private boolean waxOn = false;



    public synchronized void waxed() {

        waxOn = true; // Ready to buff

        notifyAll();

    }



    public synchronized void buffed() {

        waxOn = false; // Ready for another coat of wax

        notifyAll();

    }



    public synchronized void waitForWaxing()

            throws InterruptedException {

        while (waxOn == false)

            wait();

    }



    public synchronized void waitForBuffing()

            throws InterruptedException {

        while (waxOn == true)

            wait();

    }

}



class WaxOn implements Runnable {

    private Car car;
    private String name;


    public WaxOn(Car c, String name) {

        car = c;
        this.name = name;

    }



    public void run() {

        try {

            while (!Thread.interrupted()) {

                System.out.print(name+": ");
                System.out.print("Wax On! ");

                TimeUnit.MILLISECONDS.sleep(200);

                car.waxed();

                car.waitForBuffing();

            }

        } catch (InterruptedException e) {

            System.out.print("Exiting via interrupt");

        }

        System.out.print("Ending Wax On task");

    }

}



class WaxOff implements Runnable {

    private Car car;



    public WaxOff(Car c ) {

        car = c;


    }



    public void run() {

        try {

            while (!Thread.interrupted()) {

                car.waitForWaxing();

                System.out.print("Wax Off! ");

                TimeUnit.MILLISECONDS.sleep(200);

                car.buffed();

            }

        } catch (InterruptedException e) {

            System.out.print("Exiting via interrupt");

        }

        System.out.print("Ending Wax Off task");

    }

}



public class WaxOMatic {

    public static void main(String[] args) throws Exception {

        Car car = new Car();

        ExecutorService exec = Executors.newCachedThreadPool();

        exec.execute(new WaxOff(car));

        exec.execute(new WaxOn(car,  "WaxOn1"));
        exec.execute(new WaxOn(car,  "WaxOn2"));


        TimeUnit.SECONDS.sleep(5); // Run for a while...

        exec.shutdownNow(); // Interrupt all tasks

    }

}