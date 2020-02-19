package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String readMessage(){
        String i;

        while (true) {
            try {
                i = reader.readLine();
                break;
            } catch (IOException e) {
                writeMessage("Сообщение не удалось отправить");
            }
        }
        return i;
    }

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static int readInt() {
        int i;

        while (true) {
            try {
                i = Integer.parseInt(reader.readLine());
                break;
            } catch (NumberFormatException e) {
                writeMessage("Не правильный формат числа, попробуйте еще раз");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

}
