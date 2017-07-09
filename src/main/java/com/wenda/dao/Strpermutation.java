package com.wenda.dao;

import javax.sound.midi.Soundbank;
import java.util.Arrays;

/**
 * Created by 49540 on 2017/7/6.
 */
public class Strpermutation {

    public static void swap(char[] array, int i,int j)
    {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static  void permutation(char[] array, int from,int to)
    {
        if(from == to)
        {
            for(int i = 0;i<=to;i++)
            {
                System.out.print(array[i]);
            }
            System.out.print("\n");
            return ;
        }
        for(int i = from ;i<=to;i++)
        {
            swap(array,i,from);
            permutation(array, from+1, to);
            swap(array, i,from);
        }
    }
    public static void main(String[] args)
    {
        char[] array = "12345".toCharArray();
        permutation(array,0,"12345".length()-1);
    }
}
