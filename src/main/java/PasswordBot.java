import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "passgen_bot";
    }

    @Override
    public String getBotToken() {
        return "7842141988:AAH5awuh9toPVT87otlwxIGYF3XpqvMneU4";
    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);

        Message message = update.getMessage();
        String messageText = message.getText();
        Long chatID = message.getFrom().getId();

        if (messageText.equals("/start")) {
            sendText(chatID, "Привет, стартуем! Используй /pwgen для генерации пароля");
        } else if (messageText.equals("/pwgen")) {
            // Генерация пароля
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[8]; // Массив для хранения байтов (например, 16 байт = 128 бит)
            random.nextBytes(bytes);
            String randomString = Base64.getEncoder().encodeToString(bytes); // Преобразование в строку

            sendText(chatID, randomString);
            sendText(chatID, "Это просто демонстрация функционала, не рекомендую использовать этот пароль!");
        }
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new PasswordBot());
    }
}
