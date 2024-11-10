package org.annill.linguabot.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramMessage {
    private String chatId;
    private String text;
    private ReplyMarkup replyMarkup;
    private String method;

    @JsonProperty("chat_id")
    public String getChatId() {
        return chatId;
    }

    @JsonProperty("chat_id")
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @JsonProperty("reply_markup")
    public ReplyMarkup getReplyMarkup() {
        return replyMarkup;
    }

    @JsonProperty("reply_markup")
    public void setReplyMarkup(ReplyMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }

    public static class ReplyMarkup {
        private List<List<InlineKeyboardButton>> inlineKeyboard;

        @JsonProperty("inline_keyboard")
        public List<List<InlineKeyboardButton>> getInlineKeyboard() {
            return inlineKeyboard;
        }

        @JsonProperty("inline_keyboard")
        public void setInlineKeyboard(List<List<InlineKeyboardButton>> inlineKeyboard) {
            this.inlineKeyboard = inlineKeyboard;
        }
    }

    public static class InlineKeyboardButton {
        @Setter
        @Getter
        private String text;
        private String callbackData;

        @JsonProperty("callback_data")
        public String getCallbackData() {
            return callbackData;
        }

        @JsonProperty("callback_data")
        public void setCallbackData(String callbackData) {
            this.callbackData = callbackData;
        }
    }

}
