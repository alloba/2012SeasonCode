package team3329.subsystems.drive;

import team3329.util.vector.*;

//this class is a trapezoidal motion profiler for the drive
public class MotionProfiler
{

    private double[] lastTimeMap;

    //returns an array of polar vectors that models a trapezoidal profile
    //and the coordinate to run
    public PolarVector[] getSetPointTable(PolarVector start, PolarVector end,
            double startTime, double endTime)
    {
        double dx = end.getDistance() - start.getDistance();
        double da = end.getDirection() - start.getDirection();
        double dt = endTime - startTime;
        double x1 = start.getDistance();
        double a1 = start.getDirection();
        //the time should be in seconds. Because we don't want to many data points make
        //the data flow with every driver station call (.2 seconds)
        int steps = (int) Math.ceil(dt / .2);

        PolarVector[] table = new PolarVector[steps];
        lastTimeMap = new double[steps];

        for (int i = 0; i < steps; i++)
        {

            double u = i * .2 / dt;
            double v = (-2 * (u * u * u) + 3 * (u * u));
            double distance = x1 + dx * (v);
            double angle = a1 + da * (v);
            table[i] = new PolarVector(distance, angle);
            lastTimeMap[i] = i * .2 + startTime;
        }

        return table;
    }

    //returns the last time map created from the last setpoint calculation.
    public double[] getLastTimeMap()
    {
        return lastTimeMap;
    }

    public static void main(String[] args)
    {
          MotionProfiler profiler = new MotionProfiler();
          PolarVector a = new PolarVector(new CartesianVector(0,0));
          PolarVector b = new PolarVector(new CartesianVector(3,3));

          double speed = 24;
          double time = b.getDistance() / speed;

          PolarVector[] setpoints = profiler.getSetPointTable(a, b, 0, time);
          for(int i=0;i<setpoints.length;i++)
          {
              System.out.println(setpoints[i].getDistance());
          }
    }
}
