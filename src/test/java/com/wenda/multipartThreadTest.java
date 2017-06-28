package com.wenda;

import com.sun.deploy.panel.ITreeNode;
import org.junit.Test;

import javax.management.relation.InvalidRelationTypeException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * Created by 49540 on 2017/6/28.
 */

public class multipartThreadTest {

    class Customer implements Runnable
    {
        private BlockingQueue<String> queue;
        public Customer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + queue.take());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    class Producure implements Runnable
    {
        private BlockingQueue<String> queue;
        public Producure(BlockingQueue queue)
        {
            this.queue = queue;
        }
        @Override
        public void run() {
            for(int i = 0;i<100;i++)
            {
                try {
                    queue.put(String.valueOf(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    public void BlockingQueueTest()
    {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        new Thread(new Customer(queue)).start();
        new Thread(new Customer(queue)).start();
        new Thread(new Producure(queue)).start();
    }
    public static void main(String[] args)
    {


    }
}
