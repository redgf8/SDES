import java.util.Scanner;

public class Driver {

    public static void main(String args[]) {

        SDES sdes = new SDES();
        boolean[] test = {false, true, true, false, true, true, false, false};
        boolean[] test2 = {false, false, false};
        boolean[] test3 = {true, true, false};
        byte testByte = 0;
        byte[] byteArray = {84, 104, 105, 115, 32, 105, 115, 32, 97, 32, 116, 101, 115, 116, 46};
        int[] epv = {5, 4, 3, 2, 1, 0};
        System.out.println(sdes.byteArrayToString(byteArray));
        testByte = sdes.bitArrayToByte(test);
        System.out.println(testByte);
        test = sdes.byteToBitArray(testByte, 8);
        //test = sdes.lh(test);
        //test = sdes.rh(test);
        //test = sdes.concat(test, test2);
        //test = sdes.concat(test2, test);
        //test = sdes.xor(test2, test3);
        for (int i = 0; i < test.length; i++) {

            System.out.println(test[i]);

        }
        Scanner input = new Scanner(System.in);
        sdes.getKey10(input);

    }

}
