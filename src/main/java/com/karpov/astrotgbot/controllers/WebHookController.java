package com.karpov.astrotgbot.controllers;

import com.karpov.astrotgbot.config.AstroTGBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

	private final AstroTGBot astroTGBot;

	public WebHookController(AstroTGBot astroTGBot) {
		this.astroTGBot = astroTGBot;
	}

	@PostMapping("/")
	public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
		return astroTGBot.onWebhookUpdateReceived(update);
	}
}
