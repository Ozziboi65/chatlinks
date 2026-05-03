package com.chatlinks.client.mixin;

import com.chatlinks.client.ChatlinksClient;
import com.chatlinks.client.Config;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.net.URI;
import java.util.regex.Matcher;

@Mixin(ChatComponent.class)
public class MainMixin {
    @ModifyVariable(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V", at = @At("HEAD"), argsOnly = true)
    private Component injectClickableLinks(Component message) {
        String fullText = message.getString();
        Matcher matcher = ChatlinksClient.PATTERN.matcher(fullText);
        if (!matcher.find()) {
            return message;
        }
        MutableComponent result = Component.empty();
        int lastEnd = 0;
        matcher.reset();
        while (matcher.find()) {
            String url = matcher.group();
            int start = matcher.start();
            int end = matcher.end();

            if (start > lastEnd) {
                String before = fullText.substring(lastEnd, start);
                result.append(Component.literal(before));
            }

            Component hoverComponent = Component.literal(Config.hoverLinkText);

            Style urlStyle = Style.EMPTY
                    .withClickEvent(new ClickEvent.OpenUrl(URI.create(url)))
                    .withHoverEvent(new HoverEvent.ShowText(hoverComponent))
                    .withUnderlined(true)
                    .withColor(Config.linkColor);
            result.append(Component.literal(url).setStyle(urlStyle));
            lastEnd = end;
        }
        if (lastEnd < fullText.length()) {
            String after = fullText.substring(lastEnd);
            result.append(Component.literal(after));
        }
        return result;
    }
}
