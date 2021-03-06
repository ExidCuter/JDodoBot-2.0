package xyz.the_dodo.bot.utils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import xyz.the_dodo.REST.service.DeletedMessageServiceImpl;
import xyz.the_dodo.database.types.DeletedMessage;

import java.util.List;
import java.util.stream.Collectors;

public class DeletedMessageUtils {
    public static DeletedMessageServiceImpl deletedMessageService = BeanUtils.getBean(DeletedMessageServiceImpl.class);

    public static void addDeletedMessage(DeletedMessage deletedMessage) {
        deletedMessageService.save(deletedMessage);
    }

    public static List<DeletedMessage> getDeletedInGuild(User user, Guild guild) {
        List<DeletedMessage> messages;

        messages = deletedMessageService.findAllByServerDiscordId(guild.getId());

        return messages.stream()
                .filter(deletedMessage -> deletedMessage.getUser().getDiscordId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
