import console.ConsoleOutputer;

public class SecondClient {
    public static void main(String[] args) {


        ClientApp client = new ClientApp();

        ASCIIArt.greetings(new ConsoleOutputer());
        client.runClient();
    }
}
