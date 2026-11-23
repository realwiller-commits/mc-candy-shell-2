package com.candyshell.command;

import com.candyshell.battle.RapBattle;
import com.candyshell.battle.RoundBank;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Chat version of the rap battle (prototype).
 *   /candyshell start      -> begins a battle
 *   /candyshell pick 1-4   -> choose a rhyme
 * Later this gets replaced by a boss entity + a real menu screen.
 */
public class CandyShellCommand {

	// One battle per player
	private static final Map<UUID, RapBattle> BATTLES = new HashMap<>();

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("candyshell")
			.then(Commands.literal("start")
				.executes(ctx -> start(ctx.getSource().getPlayerOrException())))
			.then(Commands.literal("pick")
				.then(Commands.argument("number", IntegerArgumentType.integer(1, 4))
					.executes(ctx -> pick(
						ctx.getSource().getPlayerOrException(),
						IntegerArgumentType.getInteger(ctx, "number"))))));
	}

	private static int start(ServerPlayer player) {
		RapBattle battle = new RapBattle(RoundBank.createRounds());
		BATTLES.put(player.getUUID(), battle);
		say(player, "=== RAP BATTLE: MC Candy Shell ===", ChatFormatting.GREEN);
		showRound(player, battle);
		return 1;
	}

	private static int pick(ServerPlayer player, int number) {
		RapBattle battle = BATTLES.get(player.getUUID());
		if (battle == null) {
			say(player, "No battle running. Type /candyshell start", ChatFormatting.RED);
			return 0;
		}

		String chosenText = battle.currentRound().options().get(number - 1).text();
		say(player, "You: " + chosenText, ChatFormatting.AQUA);

		RapBattle.PickResult result = battle.pick(number - 1);

		if (result.damage() >= 15) {
			say(player, "FIRE! The crowd goes wild!", ChatFormatting.GOLD);
		} else if (result.damage() >= 10) {
			say(player, "Nice rhyme!", ChatFormatting.YELLOW);
		} else if (result.damage() >= 5) {
			say(player, "Meh... a few people clap.", ChatFormatting.GRAY);
		} else {
			say(player, "That fell flat. Crickets.", ChatFormatting.DARK_GRAY);
		}
		say(player, "MC Candy Shell " + healthBar(result.bossHealth()), ChatFormatting.GREEN);

		if (result.battleOver()) {
			BATTLES.remove(player.getUUID());
			say(player, "MC Candy Shell: \"Time's up, little Steve!\"", ChatFormatting.GREEN);
			say(player, "*rainbow music note beam*  You lost the battle!", ChatFormatting.LIGHT_PURPLE);
			say(player, "Type /candyshell start to try again.", ChatFormatting.GRAY);
		} else {
			showRound(player, battle);
		}
		return 1;
	}

	private static void showRound(ServerPlayer player, RapBattle battle) {
		RapBattle.BattleRound round = battle.currentRound();
		say(player, "MC Candy Shell: \"" + round.bossLine() + "\"", ChatFormatting.GREEN);
		for (int i = 0; i < round.options().size(); i++) {
			say(player, "  " + (i + 1) + ") " + round.options().get(i).text(), ChatFormatting.WHITE);
		}
		say(player, "Choose with /candyshell pick 1  (or 2, 3, 4)", ChatFormatting.GRAY);
	}

	private static String healthBar(int health) {
		int filled = health / 5; // 20 blocks total
		return "[" + "█".repeat(filled) + "░".repeat(20 - filled) + "] " + health + "/" + RapBattle.MAX_HEALTH;
	}

	private static void say(ServerPlayer player, String text, ChatFormatting color) {
		player.sendSystemMessage(Component.literal(text).withStyle(color));
	}
}
