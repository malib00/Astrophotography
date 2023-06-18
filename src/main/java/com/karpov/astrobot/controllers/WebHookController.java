package com.karpov.astrobot.controllers;

import com.karpov.astrobot.bot.AstroBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

	private final AstroBot astroBot;

	public WebHookController(AstroBot astroBot) {
		this.astroBot = astroBot;
	}

	@PostMapping("/")
	public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
		return astroBot.onWebhookUpdateReceived(update);
	}
}
