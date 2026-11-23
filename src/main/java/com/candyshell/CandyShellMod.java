package com.candyshell;

import com.candyshell.command.CandyShellCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CandyShellMod implements ModInitializer {
	public static final String MOD_ID = "candyshell";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Registers the /candyshell command when the game loads
		CommandRegistrationCallback.EVENT.register(
			(dispatcher, registryAccess, environment) -> CandyShellCommand.register(dispatcher));

		LOGGER.info("MC Candy Shell is ready to rap!");
	}
}
