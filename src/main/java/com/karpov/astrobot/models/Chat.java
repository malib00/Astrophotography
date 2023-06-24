package com.karpov.astrobot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

	@Id
	@NotNull
	private long id;

	@NotNull
	private boolean blockedByUser;

	private Instant timestamp;

	@Enumerated(EnumType.STRING)
	private BotState botState;

	private Double latitude;
	private Double longitude;

	public Chat(long id, boolean blockedByUser, Instant timestamp, BotState botState) {
		this.id = id;
		this.blockedByUser = blockedByUser;
		this.timestamp = timestamp;
		this.botState = botState;
	}
}
