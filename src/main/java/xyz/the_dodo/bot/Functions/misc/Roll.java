package xyz.the_dodo.bot.Functions.misc;

import net.dv8tion.jda.core.entities.MessageChannel;
import xyz.the_dodo.bot.Functions.IFunction;
import xyz.the_dodo.bot.types.MessageParams;
import xyz.the_dodo.bot.utils.RandomGen;

public class Roll extends IFunction
{
	public Roll(String command, String description, String usage) {
		super(command, description, usage);
	}

	@Override
	public void trigger(MessageParams p_messageParams) {
		int value, max;
		MessageChannel channel;

		channel = p_messageParams.getTextChannel();

		try {
			if (p_messageParams.getParameters()== null || p_messageParams.getParameters().length == 0) {
				value = RandomGen.rndNm(7);
				while (value == 0) {
					value = RandomGen.rndNm(7);
				}
				channel.sendMessage(String.valueOf(value)).complete();
			} else if (p_messageParams.getParameters().length > 0) {
				max = Integer.parseInt(p_messageParams.getParameters()[0]) + 1;
				value = RandomGen.rndNm(max);
				while (value == 0) {
					value = RandomGen.rndNm(max);
				}
				channel.sendMessage(String.valueOf(value)).complete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
