import java.util.Scanner;

public class SafeInput
{
        //  SafeInput file holding all the Methods

        /**
         * Ensures the user enters a non-empty string.
         */
        public static String getNonZeroLenString(Scanner pipe, String prompt) {
            String RestString = " ";
            do {

                System.out.print("\n" + prompt + ": ");
                RestString = pipe.nextLine().trim();
                if (RestString.isEmpty()) {
                    System.out.println(" Error: This input cannot be empty. Please can you try again.");
                }
            } while (RestString.isEmpty());
            return RestString;
        }

        /**
         * Prompts the user for any integer.
         */
        public static int getInt(Scanner pipe, String prompt) {
            int result = 0;
            boolean done = false;
            String trash;

            do {
                System.out.print(prompt + ": ");
                if (pipe.hasNextInt()) {
                    result = pipe.nextInt();
                    pipe.nextLine(); // clear input buffer
                    done = true;
                } else {
                    trash = pipe.nextLine();
                    System.out.println(" Error: '" + trash + "' is not a valid integer.");
                }
            } while (!done);

            return result;
        }

        /**
         * Prompts the user for any double.
         */
        public static double getDouble(Scanner pipe, String prompt) {
            double result = 0;
            boolean done = false;
            String trash;

            do {
                System.out.print(prompt + ": ");
                if (pipe.hasNextDouble()) {
                    result = pipe.nextDouble();
                    pipe.nextLine(); // clear buffer
                    done = true;
                } else {
                    trash = pipe.nextLine();
                    System.out.println(" Error: '" + trash + "' is not a valid double.");
                }
            } while (!done);

            return result;
        }

        /**
         * Prompts the user for an integer within a given range.
         */
        public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
            int result = 0;
            boolean done = false;
            String trash;

            do {
                System.out.print(prompt + " [" + low + " - " + high + "]: ");
                if (pipe.hasNextInt()) {
                    result = pipe.nextInt();
                    pipe.nextLine();
                    if (result >= low && result <= high) {
                        done = true;
                    } else {
                        System.out.println(" Error: Input not in range [" + low + " - " + high + "]." + result);
                    }
                } else {
                    trash = pipe.nextLine();
                    System.out.println(" Error: Invalid input. Please enter an integer." + trash);
                }
            } while (!done);

            return result;
        }

        /**
         * Prompts the user for a double within a given range.
         */
        public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
            double result = 0;
            boolean done = false;
            String trash;

            do {
                System.out.print(prompt + " [" + low + " - " + high + "]: ");
                if (pipe.hasNextDouble()) {
                    result = pipe.nextDouble();
                    pipe.nextLine();
                    if (result >= low && result <= high) {
                        done = true;
                    } else {
                        System.out.println(" Error: Value not in range [" + low + " - " + high + "]." + result);
                    }
                } else {
                    trash = pipe.nextLine();
                    System.out.println(" Error: Invalid number. Please enter a double value." + trash);
                }
            } while (!done);

            return result;
        }

        /**
         * Prompts the user for a Yes or No response [Y/N].
         * Returns true for yes, false for no.
         */
        public static boolean getYNConfirm(Scanner pipe, String prompt) {
            String response = "";
            boolean valid = true;
            boolean result = false;

            do {
                System.out.print(prompt + " [Y/N]: ");
                response = pipe.nextLine().trim();

                if (response.equalsIgnoreCase("Y")) {
                    result = true;
                    valid = true;
                } else if (response.equalsIgnoreCase("N")) {
                    result = true;
                    valid = false;
                } else {
                    System.out.println(" Error: Please enter 'Y' or 'N'." + response);
                }

            } while (!result);

            return valid;
        }

        /**
         * Prompts the user to enter a string that matches a given RegEx pattern.
         */
        public static String getRegExString(Scanner pipe, String prompt, String regExPattern) {
            String input = "";
            boolean valid = false;

            do {
                System.out.print("\n" + prompt + ": ");
                input = pipe.nextLine();

                if (input.matches(regExPattern)) {
                    valid = true;
                } else {
                    System.out.println(" Error: Input does not match the required pattern." + regExPattern);
                }

            } while (!valid);

            return input;
        }


        /**
         * Prints a "pretty" header with a centered message surrounded by asterisks.
         * <p>
         * Example:
         * **********************************************************
         * ***               Message Centered Here                ***
         * **********************************************************
         *
         * @param msg The message to display in the header
         */
        public static void prettyHeader(String msg) {
            final int TOTAL_WIDTH = 60;  // Total line width
            final int SIDE_STARS = 3;    // Stars at each side
            final char STAR = '*';

            //  Top border
            for (int i = 0; i < TOTAL_WIDTH; i++) {
                System.out.print(STAR);
            }
            System.out.println();

            //  Calculate padding
            int textSpace = TOTAL_WIDTH - (SIDE_STARS * 2);
            int msgLength = msg.length();
            int padding = (textSpace - msgLength) / 2;
            int extra = (textSpace - msgLength) % 2; // Handle odd-length messages

            //  Middle line
            System.out.print("***");
            for (int i = 0; i < padding; i++) {
                System.out.print(" ");
            }
            System.out.print(msg);
            for (int i = 0; i < padding + extra; i++) {
                System.out.print(" ");
            }
            System.out.println("***");

            //  Bottom border
            for (int i = 0; i < TOTAL_WIDTH; i++) {
                System.out.print(STAR);
            }
            System.out.println();

        }

    /**
     * Converts a Celsius temperature to Fahrenheit.
     *
     * @param Celsius The temperature in Celsius (double).
     * @return The equivalent temperature in Fahrenheit (double).
     */
    public static double CtoF(double Celsius) {
        return (Celsius * 9.0 / 5.0) + 32;
    }

}


