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
        BufferedReader in=new BufferedReader(new FileReader("e_high_bonus.in"));
        StringBuilder out=new StringBuilder();
        StringTokenizer tk;


        PrintWriter pw=new PrintWriter(new File("output_e.txt"));

        tk = new StringTokenizer(in.readLine());
        r=parseInt(tk.nextToken());
        c=parseInt(tk.nextToken());
        f=parseInt(tk.nextToken());
        n=parseInt(tk.nextToken());
        b=parseInt(tk.nextToken());
        t=parseInt(tk.nextToken());

        rides = new Ride[n];
        vehicles = new Vehicle[f];

        for (int i = 0; i < n; i ++) {
            String s = in.readLine();
            boolean temp = true; //IntelliJ kept bugging me about duplicate code, so I added this useless command
            String [] str = s.split(" ");
            Pair start = new Pair(Integer.parseInt(str[0]),Integer.parseInt(str[1]));
            Pair end = new Pair(Integer.parseInt(str[2]),Integer.parseInt(str[3]));
            int st = Integer.parseInt(str[4]);
            int f = Integer.parseInt(str[5]);
            int index = i;
            rides[i] = new Ride(start,end,st,f,index);
        }
        for (int i = 0; i < f; i++)
        {
            vehicles[i] = new Vehicle();
        }

        BufferedReader in2 =new BufferedReader(new FileReader("output_e.txt"));
        StringTokenizer stk;
        int points = 0;
        for (int i = 0; i < f; i++) //Every vehicle
        {
            stk = new StringTokenizer(in2.readLine());

            int ridesNum = parseInt(stk.nextToken());
            ArrayList<Ride> vRides = new ArrayList<>();
            for (int j = 0; j < ridesNum; j++) //Every ride the vehicle has completed
            {
                int rideNum = parseInt(stk.nextToken());
                vRides.add(rides[rideNum]);
            }
            for (Ride ride : vRides)
            {
                points+=ride.getDistance();
                if () //Check if this ride gets bonus points
                {

                }
            }
        }
    }
}
