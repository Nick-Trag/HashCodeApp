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
    //static ArrayList<ArrayList<Ride>> rideBook;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String[] files = {"a_example.in", "b_should_be_easy.in", "c_no_hurry.in", "d_metropolis.in", "e_high_bonus.in"};
        String[] outfiles = {"output_a_example.txt", "output_b.txt", "output_c.txt", "output_d.txt", "output_e.txt"};
        for (int file = 0; file < 5; file++)
        {
            System.out.println("Working on file " + files[file]);
            BufferedReader in = new BufferedReader(new FileReader(files[file]));
            StringBuilder out = new StringBuilder();
            StringTokenizer tk;


            PrintWriter pw = new PrintWriter(new File(outfiles[file]));

            tk = new StringTokenizer(in.readLine());
            r = parseInt(tk.nextToken());
            c = parseInt(tk.nextToken());
            f = parseInt(tk.nextToken());
            n = parseInt(tk.nextToken());
            b = parseInt(tk.nextToken());
            t = parseInt(tk.nextToken());

            rides = new Ride[n];
            vehicles = new Vehicle[f];

            for (int i = 0; i < n; i++)
            {
                String s = in.readLine();
                String[] str = s.split(" ");
                Pair start = new Pair(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
                Pair end = new Pair(Integer.parseInt(str[2]), Integer.parseInt(str[3]));
                int st = Integer.parseInt(str[4]);
                int f = Integer.parseInt(str[5]);
                int index = i;
                rides[i] = new Ride(start, end, st, f, index);
            }
            for (int i = 0; i < f; i++)
            {
                vehicles[i] = new Vehicle();
            }

            MyMergeSort mms = new MyMergeSort();
            mms.sort(rides);

            for (int i = 0; i < t; i++) //Every step of the simulation
            {
                for (int j = 0; j < f; j++) //Every vehicle
                {
                    if (/*vehicles[j].getRideEnd() == i*/ vehicles[j].assigned && (i - vehicles[j].assignedRideStartStep == vehicles[j].getRideDistance())) //If the Ride-distance steps have passed since the Ride was assigned
                    {
                        vehicles[j].unassign();
                    }
                    if (!vehicles[j].assigned)
                    {
                        for (int k = 0; k < n; k++) //Every ride
                        {
                            if (!rides[k].assigned)
                            {
                                if (check(rides[k], vehicles[j], i))
                                {
                                    if (i + Math.abs(vehicles[j].location.a - rides[k].start.a) + Math.abs(vehicles[j].location.b - rides[k].start.b) >= rides[k].s)
                                    {
                                        vehicles[j].assign(rides[k],i);
                                        rides[k].assigned = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (Vehicle v : vehicles)
            {
                if (v.ridesNum > 0)
                {
                    out.append(v.ridesNum);
                    for (int i = 0; i < v.rides.size(); i++)
                    {
                        out.append(" ").append(v.rides.get(i));
                    }
                    out.append("\n");
                }
            }
            pw.print(out);
            pw.close();
        }
    }


    private static boolean check(Ride ride, Vehicle vehicle, int steps) {
        return Math.abs(ride.start.a - vehicle.location.a) + Math.abs(ride.start.b - vehicle.location.b) + steps + ride.getDistance() <= ride.f;
    }
    
    
    
}

class MyMergeSort {
     
    private Ride[] array;
    private Ride[] tempMergArr;
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
     
    public void sort(Ride[] inputArr) {
        this.array = inputArr;
        this.length = inputArr.length;
        this.tempMergArr = new Ride[length];
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
            tempMergArr[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i].f <= tempMergArr[i].f) {
                array[k] = tempMergArr[i];
                i++;
            } else {
                array[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array[k] = tempMergArr[i];
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
    public Ride assignedRide;
    public int assignedRideStartStep;
    public ArrayList<Integer> rides;
    
    public Vehicle()
    {
        location = new Pair(0,0);
        ridesNum = 0;
        assigned = false;
        rides = new ArrayList<>();
        assignedRide = null;
        assignedRideStartStep = -1;
    }

    public void assign(Ride ride, int step)
    {
        assignedRide = new Ride(ride.start,ride.end,ride.s,ride.f,ride.index);
        assigned = true;
        ridesNum++;
        assignedRideStartStep = step;
        rides.add(ride.index);
    }

    public int getRideDistance()
    {
        if (assignedRide != null)
        {
            return Math.abs(assignedRide.start.a - assignedRide.end.a) + Math.abs(assignedRide.start.b - assignedRide.end.b);
        }
        else
        {
            return -1;
        }
    }

    public int getRideEnd()
    {
        if (assignedRide != null)
        {
            return assignedRide.f;
        }
        else
        {
            return -1;
        }
    }

    public void unassign()
    {
        location = new Pair(assignedRide.end.a,assignedRide.end.b);
        assignedRide = null;
        assignedRideStartStep = -1;
        assigned = false;
    }
}