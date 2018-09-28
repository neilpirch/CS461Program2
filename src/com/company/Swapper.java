package com.company;

import java.util.Random;

public class Swapper {
    private int itr = 0;
    private int att = 0;
    private int swaps = 0;
    private int attGoal = 20000;
    private int swapGoal = 2000;
    private double temperature = 400.0;
    private double minCompat = 100.0;
    private double maxCompat = 0.0;
    private double meanCompat = 0.0;

    public void calcResults(Rooms a) {
        a.calcRoomCompatArray();
        double dataArray[] = a.getRoomCompatData();
        double tempA = 0.0;
        double tempB = 0.0;
        int numRooms = a.getNumRooms();

        for (int i = 0; i < numRooms; i++) {
            tempB = dataArray[i];
            if (tempB < minCompat) {
                minCompat = dataArray[i];
            }
            if (tempB > maxCompat) {
                maxCompat = dataArray[i];
            }
            tempA += tempB;
        }
        meanCompat = tempA / numRooms;
    }

    public void outputResults() {
        System.out.format("Itr = %4s Temp = %7.3f  Att = %5s Swaps = %4s Min = %6.4f Max = %6.4f Mean = %6.4f %n", itr, temperature,
                att, swaps, minCompat,maxCompat, meanCompat );
    }

    public void runIterations(Rooms a) {
        runSwaps(a);
        itr++;
        calcResults(a);
        outputResults();
        temperature = .95 * temperature;
        while (swaps > 0) {
            runSwaps(a);
            itr++;
            calcResults(a);
            outputResults();
            temperature = .95 * temperature;
        }
    }
    public void runSwaps(Rooms a) {
        att = 0;
        swaps = 0;
        while (att < attGoal && swaps < swapGoal) {
            // randomly select 2 people from different rooms
            Random ran = new Random();
            int roomOne = ran.nextInt(a.getNumRooms());
            int roomTwo = ran.nextInt(a.getNumRooms());
            int personOne = ran.nextInt(a.getNumRoommates());
            int personTwo = ran.nextInt(a.getNumRoommates());
            while (roomOne == roomTwo) roomTwo = ran.nextInt(a.getNumRooms());

            // hold the roommate IDs in local arrays
            int arrOne[] = a.getRoomAssign(roomOne).clone();
            int arrTwo[] = a.getRoomAssign(roomTwo).clone();

            // calculate the average compatibility score before proposed swap

            double oldScore = (a.calcRoomScore(arrOne) + a.calcRoomScore(arrTwo)) / 2;

            // swap people in the local arrays
            int temp = arrOne[personOne];
            arrOne[personOne] = arrTwo[personTwo];
            arrTwo[personTwo] = temp;
            double newScore = (a.calcRoomScore(arrOne) + a.calcRoomScore(arrTwo)) / 2;

            // calculate probability swap
            double delta = newScore - oldScore;
            double prob = Math.exp(delta / temperature);
            double random = ran.nextDouble();
            if (delta > 0) {
                a.newRoomAssign(arrOne, roomTwo);
                a.newRoomAssign(arrTwo, roomOne);
                swaps++;
                att++;
            } else if (prob > random) {
                // probability swap
                a.newRoomAssign(arrOne, roomTwo);
                a.newRoomAssign(arrTwo, roomOne);
                swaps++;
                att++;
            } else {
                att++;
            }
        }
    }





}
