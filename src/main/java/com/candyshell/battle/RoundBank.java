package com.candyshell.battle;

import com.candyshell.battle.RapBattle.BattleRound;
import com.candyshell.battle.RapBattle.RhymeOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** All the (original!) rhymes. Add more rounds here any time. */
public class RoundBank {

    private static RhymeOption o(String text, int power) {
        return new RhymeOption(text, power);
    }

    private static BattleRound round(String bossLine, RhymeOption... opts) {
        List<RhymeOption> list = new ArrayList<>(List.of(opts));
        Collections.shuffle(list); // so the best answer isn't always in the same slot
        return new BattleRound(bossLine, list);
    }

    public static List<BattleRound> createRounds() {
        return List.of(
            round("I'm MC Candy Shell, smooth and sweet, and you're about to melt!",
                o("Your shell is cracking up, that's the worst rap I've felt!", 3),
                o("Sprinkles are for cupcakes, not for rappers, I've dealt.", 2),
                o("I like cake.", 0),
                o("Melt melt melt melt.", 1)),
            round("I'm crunchy on the outside, I'm chocolate in the core!",
                o("Then why are you hiding, I'm knocking on your door!", 2),
                o("You're a gumdrop with a mic who can't even score!", 3),
                o("Core core core.", 0),
                o("My sword is made of iron.", 1)),
            round("Creepers fear my flow, even Endermen stop to stare!",
                o("Your flow's a stale lollipop, left out in the air!", 3),
                o("Creepers go boom, they do not care.", 1),
                o("I have a pickaxe and a chair.", 0),
                o("They only stare 'cause you've got candy wrapper hair!", 2)),
            round("Mine all you want, diamonds can't buy you a beat!",
                o("I'll trade you my diamonds for a better cheat!", 1),
                o("Your beat is so weak, it can't even skip a seat!", 2),
                o("Beats beats beats.", 0),
                o("You're sugar-coated sad, and I'm hot like the heat!", 3)),
            round("I've got a crowd of villagers chanting my name!",
                o("They're only here for trades, you're a one-hit-wonder flame!", 3),
                o("Hrmm hrmm hrmm, the villagers say the same.", 1),
                o("Names names names.", 0),
                o("Your fans all left, that's a crying shame!", 2)),
            round("Last verse, little Steve, time to taste defeat!",
                o("Defeat is a candy that I'm too tough to eat!", 2),
                o("I'm bringing the fire, now feel the heat, sweet and neat!", 3),
                o("Eat eat eat.", 0),
                o("I'll just respawn and run it back, no retreat!", 1))
        );
    }
}
