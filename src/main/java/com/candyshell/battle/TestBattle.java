package com.candyshell.battle;

import java.util.Scanner;

/** Console test: run this to try the battle without Minecraft. */
public class TestBattle {
    public static void main(String[] args) {
        RapBattle battle = new RapBattle(RoundBank.createRounds());
        Scanner in = new Scanner(System.in);
        RapBattle.PickResult result;
        do {
            var round = battle.currentRound();
            System.out.println("\nMC Candy Shell: " + round.bossLine());
            for (int i = 0; i < 4; i++) {
                System.out.println("  " + (i + 1) + ") " + round.options().get(i).text());
            }
            System.out.print("Pick 1-4: ");
            int pick = Integer.parseInt(in.nextLine().trim()) - 1;
            result = battle.pick(pick);
            System.out.println("Damage: " + result.damage() + " | Boss health: " + result.bossHealth());
        } while (!result.battleOver());
        System.out.println(result.playerLost() ? "\nYou lost! *rainbow music note beam*" : "You won!");
    }
}
