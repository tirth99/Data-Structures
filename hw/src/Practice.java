import jdk.nashorn.internal.parser.JSONParser;

import java.util.Arrays;

public class Practice {

    public static void main(String[] args) {
        String str1= "'around-the-world? nice ";
        String[] words = str1.split("\\W");
        System.out.println(Arrays.toString(words));

        for(String a : words)
            System.out.println(a);
    }
}
