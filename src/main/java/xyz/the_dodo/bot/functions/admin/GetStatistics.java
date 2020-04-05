package xyz.the_dodo.bot.functions.admin;

import xyz.the_dodo.bot.anotations.BotService;
import xyz.the_dodo.bot.functions.IFunction;
import xyz.the_dodo.bot.listeners.StatsListener;
import xyz.the_dodo.bot.types.CommandCategoryEnum;
import xyz.the_dodo.bot.types.MessageParams;
import xyz.the_dodo.bot.utils.AdminUtils;
import xyz.the_dodo.bot.utils.StringUtils;

@BotService(command = "getBotStats", category = CommandCategoryEnum.ADMIN)
public class GetStatistics extends IFunction {
    public GetStatistics(String command, String description, String usage, boolean isService, CommandCategoryEnum commandCategoryEnum) {
        super(command, description, usage, isService, commandCategoryEnum);
    }

    @Override
    public void trigger(MessageParams messageParams) {
        StringBuilder stringBuilder;

        if (AdminUtils.isUserBotOwner(messageParams.getUser())) {
            stringBuilder = new StringBuilder();

            StatsListener.userInteractions.forEach((p_key, p_value) -> stringBuilder.append("`" + p_key + "`: " + p_value));

            StringUtils.splitIntoMessages(stringBuilder.toString(), '\n').forEach(p_message -> messageParams.getTextChannel().sendMessage(p_message).queue());
        }
    }
}
