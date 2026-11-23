package com.candyshell.battle;

import java.util.List;

/**
 * The "brain" of the rap battle. No Minecraft code in here on purpose:
 * the boss entity and the menu screen (part 2) just call these methods.
 */
public class RapBattle {

    public record RhymeOption(String text, int power) {} // power: 0 (weak) to 3 (fire)
    public record BattleRound(String bossLine, List<RhymeOption> options) {}
    public record PickResult(int damage, int bossHealth, boolean battleOver, boolean playerLost) {}

    public static final int MAX_HEALTH = 100;
    private static final int DAMAGE_PER_POWER = 5;   // fire rhyme = 15 damage
    private static final int HEALTH_FLOOR = 10;      // MC Candy Shell can never reach 0 (rigged!)

    private final List<BattleRound> rounds;
    private int roundIndex = 0;
    private int bossHealth = MAX_HEALTH;

    public RapBattle(List<BattleRound> rounds) {
        this.rounds = rounds;
    }

    public BattleRound currentRound() {
        return rounds.get(roundIndex);
    }

    public int bossHealth() {
        return bossHealth;
    }

    /** Player picks option 0-3. Returns what happened. */
    public PickResult pick(int optionIndex) {
        RhymeOption chosen = currentRound().options().get(optionIndex);
        int damage = chosen.power() * DAMAGE_PER_POWER;
        bossHealth = Math.max(HEALTH_FLOOR, bossHealth - damage);

        roundIndex++;
        boolean over = roundIndex >= rounds.size();
        // The battle is rigged: when the rounds run out, the player always loses.
        return new PickResult(damage, bossHealth, over, over);
    }
}
