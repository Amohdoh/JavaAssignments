public class WaterLevelProgram {
    public static void main(String[] args) {
        //Vars
        int subsided = 0;
        int userInput1, userInput2, userInput3;
        java.util.Scanner input = new java.util.Scanner(System.in);

        while (subsided == 0){
            System.out.print("What is the water level at now (in mm):");
            userInput1 = input.nextInt();
            System.out.print("What is the water level at now (in mm):");
            userInput2 = input.nextInt();

            if (userInput1 <= userInput2){
                continue;
            }
            else{
                System.out.print("What is the water level at now (in mm):");
                userInput3 = input.nextInt();
            }

            if (userInput1 > userInput2 && userInput2 > userInput3){
                subsided = 1;
            }

        }
        System.out.println("It appears that the flood is subsiding.");
    }
}