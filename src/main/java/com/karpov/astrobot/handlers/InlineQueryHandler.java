package com.karpov.astrobot.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InlineQueryHandler {
	public BotApiMethod<?> handleInlineQuery(Update update) {
		CallbackQuery callbackQuery = update.getCallbackQuery();
		String callbackQueryData = callbackQuery.getData();
		if (callbackQueryData.equals("ExitButtonPressed")) {
			return new DeleteMessage(callbackQuery.getFrom().getId().toString(), callbackQuery.getMessage().getMessageId());
		} else {
			return new SendMessage(update.getCallbackQuery().getFrom().getId().toString(), "Update has query, but not implemented");
		}
	}
}
