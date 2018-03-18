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
import java.util.Collections;
//import java.util.Comparator;
import java.util.StringTokenizer;
//import org.apache.commons.net.*;

/**
 *
 * @author Λεωνίδας
 */
public class HashCodeApp {

    static int r,c,f,n,b,t;
    static ArrayList<Ride> rides;
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




            tk = new StringTokenizer(in.readLine());
            r = parseInt(tk.nextToken());
            c = parseInt(tk.nextToken());
            f = parseInt(tk.nextToken());
            n = parseInt(tk.nextToken());
            b = parseInt(tk.nextToken());
            t = parseInt(tk.nextToken());

            rides = new ArrayList<>();
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
                rides.add(new Ride(start, end, st, f, index));
            }
            for (int i = 0; i < f; i++)
            {
                vehicles[i] = new Vehicle();
            }

            //MyMergeSort mms = new MyMergeSort();
            //mms.sort(rides);

            Collections.sort(rides, (o1, o2) ->
            {
                //Compare o1 with o2
                if (o1.f < o2.f)
                {
                    return -1;
                }
                else if (o1.f == o2.f)
                {
                    return 0;
                }
                else
                {
                    return 1;
                }
            });

            for (int i = 0; i < t; i++) //Every step of the simulation
            {
                for (int j = 0; j < f; j++) //Every vehicle
                {
                    if (/*vehicles[j].getRideEnd() == i*/ vehicles[j].assigned && (i == vehicles[j].assignedRideStartStep + vehicles[j].getDistanceToRideStart() + vehicles[j].getRideDistance())) //If the Ride-distance steps have passed since the Ride was assigned
                    {
                        vehicles[j].unassign();
                    }
                    if (!vehicles[j].assigned)
                    {
                        for (int k = 0; k < rides.size(); k++) //Every ride
                        {
                            //if (!rides.get(k).assigned)
                            //{
                            if (HashCodeApp.check(rides.get(k), vehicles[j], i))
                            {
                                if (i + Math.abs(vehicles[j].location.a - rides.get(k).start.a) + Math.abs(vehicles[j].location.b - rides.get(k).start.b) >= rides.get(k).s)
                                {
                                    vehicles[j].assign(rides.get(k),i);
                                    //rides.get(k).assigned = true;
                                    rides.remove(k);
                                    break;
                                }
                            }
                            //}
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
            PrintWriter pw = new PrintWriter(new File(outfiles[file]));
            pw.print(out);
            pw.close();
        }
    }


    private static boolean check(Ride ride, Vehicle vehicle, int steps) {
        return Math.abs(ride.start.a - vehicle.location.a) + Math.abs(ride.start.b - vehicle.location.b) + steps + ride.getDistance() <= ride.f;
    }
    
    
    
}

/*class MyMergeSort {
     
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
    }*
     
    public void sort(ArrayList<Ride> inputArr) {
        this.array = inputArr;
        this.length = inputArr.size();
        this.tempMergArr = new ArrayList<>(length);
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
            tempMergArr.add(i,array.get(i));
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr.get(i).f <= tempMergArr.get(i).f) {
                array.set(k,tempMergArr.get(i));
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
}*/

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
    //public boolean assigned;
    
    public Ride(Pair start, Pair end, int s, int f,int i) {
        this.start = start;
        this.end = end;
        this.f = f;
        this.s = s;
        index = i;
        //assigned = false;
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
            return assignedRide.getDistance();
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

    public int getDistanceToRideStart()
    {
        return Math.abs(location.a - assignedRide.start.a) + Math.abs(location.b - assignedRide.start.b);
    }
}