package com.chatlinks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.ChatFormatting;

public class ChatlinksClient implements ClientModInitializer {

	// ts class is basically for globals real logic in mixins :P
	public final static String REGEX = "\\bhttps?://(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*[-a-zA-Z0-9@:%_\\+~#?&\\/=])?";
	public final static Pattern PATTERN = Pattern.compile(REGEX);

	public static final String MOD_ID = "chatlinks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("at start of init client :P");
	}

}