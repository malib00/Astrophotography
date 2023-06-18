package com.karpov.astrobot.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class EchoService {

	public SendMessage echoText(Update update) {

		return new SendMessage(update.getMessage().getChatId().toString(), update.getMessage().getText());

	}
}
