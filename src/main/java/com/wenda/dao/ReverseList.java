package com.wenda.dao;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.awt.*;
import java.security.PublicKey;
import java.time.temporal.Temporal;

/**
 * Created by 49540 on 2017/7/6.
 */

public class ReverseList {
    static class LinkedNode{
        public int value;
        public LinkedNode next;
    }

    static class Node{
    private LinkedNode head = new LinkedNode();

    public void init()
    {
        LinkedNode tmp = head;
        for (int i = 1;i<6;i++)
        {
            LinkedNode node = new LinkedNode();
            node.value = i;
            tmp.next = node;
            tmp = node;
        }
    }
    public void reverse()
    {
        LinkedNode prev = head.next;
        while(prev.next!=null)
        {
            LinkedNode p = prev.next;
            prev.next = p.next;
            p.next = head.next;
            head.next = p;
        }

    }
    public void print()
    {
        LinkedNode temp = head.next;
        while(temp!=null)
        {
            System.out.print(temp.value);
            temp = temp.next;
        }
        System.out.println("/n");
    }
    }
    public static void main(String[] args)
    {
        Node node = new Node();
        node.init();
        node.print();
        node.reverse();
        node.print();

    }
}
