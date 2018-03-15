package hashcodeapp;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

public class JudgeSystem
{
    static int r,c,f,n,b,t;
    static Ride[] rides;
    static Vehicle[] vehicles;

    public static void main(String[] args) throws IOException
    {
        String[] files = {"a_example.in", "b_should_be_easy.in", "c_no_hurry.in", "d_metropolis.in", "e_high_bonus.in"};
        String[] outfiles = {"output_a_example.txt", "output_b.txt", "output_c.txt", "output_d.txt", "output_e.txt"};
        for (int file = 0; file < 5; file++)
        {
            BufferedReader in = new BufferedReader(new FileReader(files[file]));
            //StringBuilder out=new StringBuilder();
            StringTokenizer tk;


            //PrintWriter pw=new PrintWriter(new File("output_a_example.txt"));

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
                boolean temp = true; //IntelliJ kept bugging me about duplicate code, so I added this useless command
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

            BufferedReader in2 = new BufferedReader(new FileReader(outfiles[file]));
            //StringTokenizer stk;
            int points = 0;
            for (int i = 0; i < f; i++) //Every vehicle
            {
                String s = in2.readLine();
                //stk = new StringTokenizer(in2.readLine());
                String[] str;
                try
                {
                    str = s.split(" ");
                }
                catch (Exception e) //This only happens when not every vehicle has been assigned a ride (Only in b_should_be_easy.in)
                {
                    System.out.println("Exception caught");
                    break;
                }
                int ridesNum = parseInt(str[0]);
                int steps = 0;
                ArrayList<Ride> vRides = new ArrayList<>();
                for (int j = 0; j < ridesNum; j++) //Every ride the vehicle has completed
                {
                    int rideNum = parseInt(str[j + 1]);
                    vRides.add(rides[rideNum]);
                }

                for (int j = 0; j < ridesNum; j++)
                {
                    if (Math.abs(vRides.get(j).start.a - vehicles[i].location.a) + Math.abs(vRides.get(j).start.b - vehicles[i].location.b) + steps + vRides.get(j).getDistance() > vRides.get(j).f)
                    {
                        System.out.println("You fucked up on file " + files[file]);
                    }
                    steps += Math.abs(vRides.get(j).start.a - vehicles[i].location.a) + Math.abs(vRides.get(j).start.b - vehicles[i].location.b);// + vRides.get(j).getDistance();
                    if (steps < vRides.get(j).s)
                    {
                        steps = vRides.get(j).s + vRides.get(j).getDistance();
                    }
                    points += vRides.get(j).getDistance();
                    if (vRides.get(j).s >= Math.abs(vehicles[i].location.a - vRides.get(j).start.a) + Math.abs(vehicles[i].location.b - vRides.get(j).start.b)) //Check if this ride gets bonus points
                    {
                        points += b;
                    }
                    vehicles[i].location = new Pair(vRides.get(j).end.a, vRides.get(j).end.b);
                }
            }
            System.out.println("Points: " + points + " for " + files[file]);
        }
    }
}
