import commands.ACommands;
import commands.CommandSaver;
import console.Console;
import exceptions.EmptyInputException;
import interaction.Request;
import interaction.Response;
import json.JsonConverter;
import utils.RouteInfo;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReaderSender {

    protected void readAndSend(List<String> input, Request request, SocketChannel socketChannel, Console console) throws
            IOException {
        boolean flag = true;
        if (CommandSaver.checkCommand(input)) {

            ACommands command = CommandSaver.getCommand(input);
            if (command.isIdAsker()) {
                if (input.size() != 2 || Integer.parseInt(input.get(1)) < 0 || input.get(1).contains(".") || input.get(1).contains(",")) {
                    System.err.println("введи нормальный айди");
                    flag = false;

                }
            }
            if (flag) {
                if (command.isAsker()) {
                    RouteInfo info = console.info();
                    request.setInfo(info);
                }

                socketChannel.write(StandardCharsets.UTF_8.encode(JsonConverter.ser(request)));
                System.out.println("sending to the server...");
            } else System.out.println("ну значит не отправлю на сервер твою команду. заново вводи");

        } else
            throw new NullPointerException("Введённой вами команды не существует. Попробуйте ввести другую команду.");

    }

}
