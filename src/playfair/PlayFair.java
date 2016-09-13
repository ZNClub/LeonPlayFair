/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playfair;

/**
 *
 * @author Leon
 */
import java.util.Scanner;

public class PlayFair {

    private String KeyWord = new String();
    private String Key = new String();
    private final char table[][] = new char[5][5];

    public void setKey(String k) {
        String adjust = new String();
        boolean flag = false;
        adjust = adjust + k.charAt(0);
        for (int i = 1; i < k.length(); i++) {
            for (int j = 0; j < adjust.length(); j++) {
                if (k.charAt(i) == adjust.charAt(j)) {
                    flag = true;
                }
            }
            if (flag == false) {
                adjust = adjust + k.charAt(i);
            }
            flag = false;
        }
        KeyWord = adjust;
    }

    public void KeyGen() {
        boolean flag = true;
        char current;
        Key = KeyWord;
        for (int i = 0; i < 26; i++) {
            current = (char) (i + 97);
            if (current == 'j') {
                continue;
            }
            for (int j = 0; j < KeyWord.length(); j++) {
                if (current == KeyWord.charAt(j)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                Key = Key + current;
            }
            flag = true;
        }
        System.out.println(Key);
        matrix();
    }

    private void matrix() {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                table[i][j] = Key.charAt(counter);
                System.out.print(table[i][j] + " ");
                counter++;
            }
            System.out.println();
        }
    }

    private String format(String oldText) {
        int i = 0;
        int len = 0;
        String text = new String();
        len = oldText.length();
        for (int tmp = 0; tmp < len; tmp++) {
            if (oldText.charAt(tmp) == 'j') {
                text = text + 'i';
            } else {
                text = text + oldText.charAt(tmp);
            }
        }
        len = text.length();
        for (i = 0; i < len; i = i + 2) {
            if (text.charAt(i + 1) == text.charAt(i)) {
                text = text.substring(0, i + 1) + 'x' + text.substring(i + 1);
            }
        }
        return text;
    }

    private String[] Divid2Pairs(String new_string) {
        String Original = format(new_string);
        int size = Original.length();
        if (size % 2 != 0) {
            size++;
            Original = Original + 'x';
        }
        String x[] = new String[size / 2];
        int counter = 0;
        for (int i = 0; i < size / 2; i++) {
            x[i] = Original.substring(counter, counter + 2);
            counter = counter + 2;
        }
        return x;
    }

    public int[] GetDiminsions(char letter) {
        int[] key = new int[2];
        if (letter == 'j') {
            letter = 'i';
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (table[i][j] == letter) {
                    key[0] = i;
                    key[1] = j;
                    break;
                }
            }
        }
        return key;
    }

    public String encryptMessage(String Source) {
        String srcArray[] = Divid2Pairs(Source);
        String Code = new String();
        char one;
        char two;
        int part1[] = new int[2];
        int part2[] = new int[2];
        for (String srcArray1 : srcArray) {
            one = srcArray1.charAt(0);
            two = srcArray1.charAt(1);
            part1 = GetDiminsions(one);
            part2 = GetDiminsions(two);
            if (part1[0] == part2[0]) {
                if (part1[1] < 4) {
                    part1[1]++;
                } else {
                    part1[1] = 0;
                }
                if (part2[1] < 4) {
                    part2[1]++;
                } else {
                    part2[1] = 0;
                }
            } else if (part1[1] == part2[1]) {
                if (part1[0] < 4) {
                    part1[0]++;
                } else {
                    part1[0] = 0;
                }
                if (part2[0] < 4) {
                    part2[0]++;
                } else {
                    part2[0] = 0;
                }
            } else {
                int temp = part1[1];
                part1[1] = part2[1];
                part2[1] = temp;
            }
            Code = Code + table[part1[0]][part1[1]]
                    + table[part2[0]][part2[1]];
        }
        return Code;
    }

    public static void main(String[] args) {
        PlayFair x = new PlayFair();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a keyword:");
        String keyword = sc.nextLine();
        String keyset = "";
        if (keyword.contains(" ")) {
            String[] splitter = keyword.split(" ");

            for (int i = 0; i < splitter.length; i++) {
                //alternqate comment
                keyset = keyset.concat(splitter[i]);
            }
        }
        System.out.println(keyset);
        x.setKey(keyset);
        x.KeyGen();
        System.out.println("Enter word to encrypt:");
        String key_input = sc.nextLine();
        key_input = key_input.replaceAll("\\s", "");
        System.out.println(key_input);
        for(int i=0;i<key_input.length()-1;i++){
            if(key_input.charAt(i) == key_input.charAt(i+1)){
                String s = key_input.substring(0,(i+1))+"x";
                key_input = s + key_input.substring(i+1,key_input.length());
            }
        }
        System.out.println(key_input);
        if (key_input.length() % 2 == 0) {
            System.out.println("Encryption: " + x.encryptMessage(key_input));
        } else {
            key_input = key_input + "x";
            System.out.println("Encryption: " + x.encryptMessage(key_input));
        }

    }
}

/*
Output : 
Enter a keyword:
playfair example
playfairexample
playfirexmbcdghknoqstuvwz
p l a y f 
i r e x m 
b c d g h 
k n o q s 
t u v w z 
Enter word to encrypt:
hide the gold in the tree stump
hidethegoldinthetreestump
hidethegoldinthetrexestump
Encryption: bmodzbxdnabekudmuixmmouvif
*/