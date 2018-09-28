package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // create class instances
        Rooms rooms = new Rooms();
        Swapper swapper = new Swapper();

        // read data from scores.txt to 2d array
        try {
            rooms.readData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // place individuals into rooms
        rooms.setRoomAssign();

        // calculate room compatibility scores
        rooms.calcRoomCompatArray();

        // calculate and output min, max, mean, and temp
        swapper.calcResults(rooms);
        swapper.outputResults();

        // Run simulated annealing swaps
        swapper.runIterations(rooms);
        //swapper.calcResults(rooms);
        //swapper.outputResults();

        System.out.println(Arrays.deepToString(rooms.getAllRoomAssign()));

    }
}
