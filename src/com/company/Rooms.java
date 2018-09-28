package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Rooms {
    int numPeople = 200;
    private int numRooms = 50;
    private int numRoommates = 4;

    private int[][] roomAssign = new int[numRooms][numRoommates];
    private double [][] compatData = new double[numPeople][numPeople];
    private double[] roomCompatData = new double[numRooms];


    // read compatibility scores from text file to 2d array
    public void readData() throws Exception {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("scores.txt")));
        while(sc.hasNextLine()) {
            for (int i=0; i<compatData.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j=0; j<line.length; j++) {
                    compatData[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
    }

    // assign individuals into rooms
    public void setRoomAssign(){
        int p = 0;
        for (int r = 0; r < numRooms; r++){
            for (int m = 0; m < numRoommates; m++){
                roomAssign[r][m] = p;
                p++;
            }
        }
    }

    // calculate individual compatibility numbers
    public double calcIndScore(int[] room, int roommateID) {
        double totalCompat = 0;
        double indScore;
        for (int i = 0; i < numRoommates; i++) {
            totalCompat += compatData[room[roommateID]][room[i]];
        }
        indScore = totalCompat / (numRoommates - 1);
        return indScore;
    }

    // calculate average compatibility for the room
    public double calcRoomScore(int[] room) {
        double totalCompat = 0;
        double roomScore;

        for (int i = 0; i < 4; i++) {
            totalCompat += calcIndScore(room, i);
        }
        roomScore = totalCompat / numRoommates;
        return roomScore;
    }

    // place room compatibility scores in an array
    public void calcRoomCompatArray() {
        for (int i = 0; i < numRooms; i++) {
            roomCompatData[i] = calcRoomScore(roomAssign[i]);
        }
    }

    public int getNumRooms() {
        return numRooms;
    }
    public int getNumRoommates() {
        return numRoommates;
    }

    public int[] getRoomAssign(int room) {
        return roomAssign[room];
    }

    public void newRoomAssign(int[] newRoomAssign, int room) {
        this.roomAssign[room] = newRoomAssign.clone();
    }

    public double[] getRoomCompatData() {
        return roomCompatData;
    }
    public int[][] getAllRoomAssign() {
        return roomAssign;
    }
}
