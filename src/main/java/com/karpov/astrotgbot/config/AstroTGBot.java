package com.karpov.astrotgbot.config;

import com.karpov.astrotgbot.handlers.MainUpdateHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AstroTGBot extends TelegramWebhookBot {

	@Value("${bot.username}")
	private String botUsername;

	@Value("${bot.webHookPath}")
	private String webHookPath;

	final private MainUpdateHandler mainMessageHandler;

	public AstroTGBot(@Value("${bot.token}") String botToken, MainUpdateHandler mainMessageHandler) {
		super(botToken);
		this.mainMessageHandler = mainMessageHandler;
	}

	@Override
	public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
		return mainMessageHandler.handleUpdate(update);
	}

	@Override
	public String getBotPath() {
		return webHookPath;
	}

	@Override
	public String getBotUsername() {
		return botUsername;
	}

}
