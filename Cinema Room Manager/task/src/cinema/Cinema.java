package cinema;

import java.util.Scanner;

public class Cinema {
    private static String[][] seatingArrangement;
    private static int numberOfRows;
    private static int numberOfSeatsPerRow;
    private static int numberOfSoldSeats = 0;
    private static int currentIncome = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        numberOfRows = scanner.nextInt() + 1;
        System.out.println("Enter the number of seats in each row:");
        numberOfSeatsPerRow = scanner.nextInt() + 1;
        initializeSeatingArrangement();

        while (true) {
            switch (chooseOptionFromMenu()) {
                case 1:
                    printSeating();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    printStatistics();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void printStatistics() {
        System.out.printf("Number of purchased tickets: %d\r\n", numberOfSoldSeats);
        System.out.printf("Percentage: %.2f%%\r\n", (double) numberOfSoldSeats * 100 / ((numberOfRows-1) * (numberOfSeatsPerRow - 1)));
        System.out.printf("Current income: $%d\r\n", currentIncome);
        System.out.printf("Total income: $%d\r\n", calculateTotalProfit());
    }

    private static int chooseOptionFromMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static void buyTicket() {
        boolean correctTicket = false;

        while (!correctTicket) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a row number:");
            int inputRowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int inputSeatNumber = scanner.nextInt();

            if (inputRowNumber > numberOfRows - 1 || inputSeatNumber > numberOfSeatsPerRow - 1) {
                System.out.println("Wrong input!");
                continue;
            }

            if (seatingArrangement[inputRowNumber][inputSeatNumber].equals("B")) {
                System.out.println("That ticket has already been purchased!");
                continue;
            }

            System.out.printf("Ticket price: $%s\r\n", Integer.toString(ticketPrice(inputRowNumber)));
            seatingArrangement[inputRowNumber][inputSeatNumber] = "B";
            numberOfSoldSeats++;
            currentIncome += ticketPrice(inputRowNumber);
            correctTicket = true;
        }
    }

    private static int ticketPrice(int row) {
        if (isBigTheater()) {
            if (row >= (numberOfRows / 2)) return 8;
            else return 10;
        } else return 10;
    }

    private static int calculateTotalProfit() {
        return isBigTheater() ? calculateProfitBigTheater() : calculateProfitSmallTheater();
    }

    private static boolean isBigTheater() {
        return (numberOfRows - 1) * (numberOfSeatsPerRow - 1) > 60;
    }

    private static int calculateProfitBigTheater() {
        return ((numberOfRows - 1) / 2) * (numberOfSeatsPerRow - 1) * 10 + (((numberOfRows - 1) - ((numberOfRows - 1) / 2)) * (numberOfSeatsPerRow - 1) * 8);
    }

    private static int calculateProfitSmallTheater() {
        return numberOfRows * numberOfSeatsPerRow * 10;
    }

    private static void initializeSeatingArrangement() {
        seatingArrangement = new String[numberOfRows][numberOfSeatsPerRow];
        seatingArrangement[0][0] = " ";
        for (int i = 1; i < numberOfSeatsPerRow; i++) seatingArrangement[0][i] = Integer.toString(i);

        for (int i = 1; i < numberOfRows; i++) {
            seatingArrangement[i][0] = Integer.toString(i);
            for (int k = 1; k < numberOfSeatsPerRow; k++) seatingArrangement[i][k] = "S";
        }
    }

    private static void printSeating() {
        System.out.println("Cinema:");
        for (int i = 0; i < numberOfRows; i++) {
            for (int k = 0; k < numberOfSeatsPerRow; k++) {
                System.out.printf("%s ",seatingArrangement[i][k]);
            }
            System.out.println();
        }
    }
}