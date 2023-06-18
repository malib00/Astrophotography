package com.karpov.astrobot.bot;

import com.karpov.astrobot.config.BotConfig;
import com.karpov.astrobot.handlers.MainUpdateHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AstroBot extends TelegramWebhookBot {

	private final BotConfig botConfig;

	final private MainUpdateHandler mainMessageHandler;

	/*constructor without a token is deprecated, so i need to use @Value instead of token from botConfig*/
	public AstroBot(@Value("${bot.token}") String botToken, BotConfig botConfig, MainUpdateHandler mainMessageHandler) {
		super(botToken);
		this.botConfig = botConfig;
		this.mainMessageHandler = mainMessageHandler;
	}

	@Override
	public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
		return mainMessageHandler.handleUpdate(update);
	}

	@Override
	public String getBotPath() {
		return botConfig.getBotWebHookPath();
	}

	@Override
	public String getBotUsername() {
		return botConfig.getBotUsername();
	}

}
