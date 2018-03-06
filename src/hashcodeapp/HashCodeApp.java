/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcodeapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Λεωνίδας
 */
public class HashCodeApp {

    static int r,c,f,n,b,t;
    static Ride[] rides;
    static Vehicle[] vehicles;
    static ArrayList<ArrayList<Ride>> rideBook;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader in=new BufferedReader(new FileReader("a_example.in"));
        StringBuilder out=new StringBuilder();
        StringTokenizer tk;
        
        
        PrintWriter pw=new PrintWriter(new File("output_a_example.txt"));
        
        tk = new StringTokenizer(in.readLine());
        r=parseInt(tk.nextToken());
        c=parseInt(tk.nextToken());
        f=parseInt(tk.nextToken());
        n=parseInt(tk.nextToken());
        b=parseInt(tk.nextToken());
        t=parseInt(tk.nextToken());
        
        rides = new Ride[n];
        vehicles = new Vehicle[f];
        rideBook = new ArrayList<>(r);
        System.out.println(r);
        System.out.println(rideBook.size());
        for (int i = 0; i < r; i++) {
            rideBook.set(i,new ArrayList<>());
        }
        /*String line;
        while ((line = in.readLine()) != null)
        {
            tk = new StringTokenizer(line);

            String []list = new String[tk.countTokens()];

            int i = 0;

            while (tk.hasMoreTokens())
            {
                list[i] = tk.nextToken();
                i++;
                rides[i].start = new Pair(parseInt(tk.nextToken()),parseInt(tk.nextToken()));
                System.out.println(i);
                rides[i].end = new Pair(parseInt(tk.nextToken()),parseInt(tk.nextToken()));
                rides[i].s = parseInt(tk.nextToken());
                rides[i].f = parseInt(tk.nextToken());
                rides[i].index = i;
            }
        }*/
        for (int i = 0; i < n; i ++) {
            String s = in.readLine();
            String [] str = s.split(" ");
            Pair start = new Pair(Integer.parseInt(str[0]),Integer.parseInt(str[1]));
            Pair end = new Pair(Integer.parseInt(str[2]),Integer.parseInt(str[3]));
            int st = Integer.parseInt(str[4]);
            int f = Integer.parseInt(str[5]);
            int index = i;
            rides[i] = new Ride(start,end,st,f,index);
        }
        
        for (int i = 0; i < n; i++) {
            rideBook.get(rides[i].start.a).add(rides[i]);
        }
        
        MyMergeSort mms = new MyMergeSort();
        for (int i = 0; i < r; i++) {
            mms.sort(rideBook.get(i));
        }
        
        int v = r / f;
        
        for (int i = 0; i < f; i++) {
            int steps = 0;
            for (int j = v*i; j < v*(i+1); j++)
            {
                for (int k = 0; k < rideBook.get(j).size(); k++)
                {
                    Pair loc = new Pair(0,0);
                    loc.a = vehicles[i].location.a;
                    loc.b = vehicles[i].location.b;
                    if (check(rideBook.get(j).get(k), loc, steps, i)) {
                        vehicles[i].location = rideBook.get(j).get(k).start;
                        steps += Math.abs(loc.a - vehicles[i].location.a) + Math.abs(loc.b - vehicles[i].location.b);
                        if (steps>t) {
                            break;
                        }
                        else {
                            loc.a = vehicles[i].location.a;
                            loc.b = vehicles[i].location.b;
                            vehicles[i].location = rideBook.get(j).get(k).end;
                            steps += Math.abs(loc.a - vehicles[i].location.a) + Math.abs(loc.b - vehicles[i].location.b);
                        }
                        if (steps > t) {
                            break;
                        }
                        vehicles[i].ridesNum++;
                        vehicles[i].rides.add(rideBook.get(j).get(k));
                    }
                }
            }
            out.append(vehicles[i].ridesNum).append(" ");
            for (int j = 0; j < vehicles[i].ridesNum; j++) {
                out.append(vehicles[i].rides.get(j).index).append(" ");
            }
            out.append("\n");
        }
    }
    public static boolean check(Ride ride, Pair loc, int steps, int i) {
        if (Math.abs(loc.a - vehicles[i].location.a) + Math.abs(loc.b - vehicles[i].location.b) + steps <= ride.f) {
            return true;
        }
        return false;
    }
    
    
    
}

class MyMergeSort {
     
    private ArrayList<Ride> array;
    private ArrayList<Ride> tempMergArr;
    private int length;
 
    /*public static void main(String a[]){
         
        int[] inputArr = {45,23,11,89,77,98,4,28,65,43};
        MyMergeSort mms = new MyMergeSort();
        mms.sort(inputArr);
        for(int i:inputArr){
            System.out.print(i);
            System.out.print(" ");
        }
    }*/
     
    public void sort(ArrayList<Ride> inputArr) {
        this.array = inputArr;
        this.length = inputArr.size();
        this.tempMergArr = new ArrayList<>();
        doMergeSort(0, length - 1);
    }
 
    private void doMergeSort(int lowerIndex, int higherIndex) {
         
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doMergeSort(lowerIndex, middle);
            // Below step sorts the right side of the array
            doMergeSort(middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }
 
    private void mergeParts(int lowerIndex, int middle, int higherIndex) {
 
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr.set(i, array.get(i));
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr.get(i).f <= tempMergArr.get(i).f) {
                array.set(k, tempMergArr.get(i));
                i++;
            } else {
                array.set(k, tempMergArr.get(j));
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array.set(k, tempMergArr.get(i));
            k++;
            i++;
        }
 
    }
}

class Pair {
    public int a,b;
    
    public Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }
}

class Ride {
    public int s,f,index;
    public Pair start,end;
    public boolean assigned;
    
    public Ride(Pair start, Pair end, int s, int f,int i) {
        this.start = start;
        this.end = end;
        this.f = f;
        this.s = s;
        index = i;
        assigned = false;
    }
    
    public int getDistance() {
        return Math.abs(start.a - end.a) + Math.abs(start.b - end.b);
    }
}

class Vehicle {
    public Pair location;
    public int ridesNum;
    public boolean assigned;
    public ArrayList<Ride> rides;
    
    public Vehicle() {
        location = new Pair(0,0);
        ridesNum = 0;
        assigned = false;
        rides = new ArrayList<>();
    }
}