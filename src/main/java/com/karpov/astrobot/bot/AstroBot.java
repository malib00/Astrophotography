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

	final private MainUpdateHandler mainUpdateHandler;

	/*constructor without a token is deprecated, so need to use @Value instead of token from botConfig*/
	public AstroBot(@Value("${bot.token}") String botToken, BotConfig botConfig, MainUpdateHandler mainMessageHandler) {
		super(botToken);
		this.botConfig = botConfig;
		this.mainUpdateHandler = mainMessageHandler;
	}

	@Override
	public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
			return mainUpdateHandler.handleUpdate(update);
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
