/**
 * An abstract class representing a battle modifier that can be applied to
 * a character in a battle. This class provides a structure for defining
 * various modifiers, including their name, description, duration, and
 * effects on attack, defense, and healing.
 * Each modifier has a name, description, and a number of uses. The
 * duration of the modifier decreases each time it is used. The modifier
 * can modify the attack, defense, and healing values based on its
 * specific effects. The modifier can also be applied to the wielder or
 * the enemy, depending on the context of the battle.
 * The modifier can be expired when its duration reaches zero.
 * 
 * The onTurnEnd method can be overridden by subclasses to define
 * specific behavior at the end of each turn.
 * 
 * The effectUsedThisTurn method should be called when the modifier is
 * applied to indicate that it has been used this turn.
 * 
 * The endRound method should be called at the end of each round to
 * decrease the duration of the modifier if it has been used.
 * 
 * The modifyAttack, modifyDefense, and modifyHealing methods can be
 * overridden by subclasses to define specific behavior for modifying
 * attack, defense, and healing values.
 */
public abstract class BattleModifier {
    private final String name;
    private final String description;

    private int duration;
    private final int maxDuration;
    private boolean wasUsed = false;

    /**
     * Constructor for BattleModifier with a name, description, and a number of uses.
     * @param name The name of the modifier
     * @param description The description of the modifier
     * @param duration The number of uses for this modifier
     */
    public BattleModifier(String name, String description, int duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.maxDuration = duration;
        this.wasUsed = false;
    }

    /**
     * Get the name of this modifier.
     * @return The name of this modifier.
     */
    public final String getName() {
        return name;
    }

    /**
     * Get the description of this modifier.
     * @return The description of this modifier.
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Get the number of uses remaining for this modifier.
     * @return The number of uses remaining for this modifier.
     */
    public final int getDuration() {
        return duration;
    }

    /**
     * Get the maximum number of uses for this modifier.
     * @return The maximum number of uses for this modifier.
     */
    public final int getMaxDuration() {
        return maxDuration;
    }


    /**
     * Check if the modifier has worn out and no longer has any effect.
     * @return true if the modifier has worn out and no longer has any effect.
     */
    public final boolean isExpired() {
        return duration <= 0;
    }


    /**
     * Decrease the modifier's duration when it has been used. This method should
     * be called when the effect is applied by the modifyAttack or modifyDefense
     * methods.
     */
    public final void effectUsedThisTurn() {
        wasUsed = true;
    }

    /**
     * Decrease the modifier's duration when it has been used. This method will
     * be called when the effect is applied by the modifyAttack, modifyDefense,
     * modifyHealing, or onTurnEnd methods.
     */
    public final void endRound() {
        if (wasUsed) {
            duration--;
            wasUsed = false;
        }
    }

    /**
     * Called when an attack is made. This method should modify the base damage
     * based on the Modifier's effect and return the modified damage. 
     * <b>Note:</b> This method should call effectUsedThisTurn() if it affects
     * either the wielder or enemy.
     * @param baseDamage The damage being dealt
     * @param context The context of the battle, including the wielder and opponent
     * @return The modified damage
     */
    public int modifyAttack(int baseDamage, BattleContext context) {
        return baseDamage;
    };

    /**
     * Called when a defense is made. This method should modify the base damage
     * based on the Modifier's effect and return the modified damage. 
     * <b>Note:</b> This method should call effectUsedThisTurn() if it affects
     * either the wielder or enemy.
     * @param baseDamage The damage being dealt
     * @param context The context of the battle, including the wielder and opponent
     * @return The modified damage
     */
    public int modifyDefense(int baseDamage, BattleContext context) {
        return baseDamage;
    };

    /**
     * Called when a healing action is made. This method should modify the base
     * healing based on the Modifier's effect and return the modified healing.
     * <b>Note:</b> This method should call effectUsedThisTurn() if it affects
     * either the wielder or enemy.
     * @param baseHeal The healing being dealt
     * @param context The context of the battle, including the wielder and opponent
     * @return The modified healing
     */
    public int modifyHealing(int baseHeal, BattleContext context) {
        return baseHeal;
    }

    /**
     * Called at the end of each turn. This method should be overridden by
     * subclasses to define the specific behavior of the modifier at the end of
     * each turn.
     * <b>Note:</b> This method should call effectUsedThisTurn() if it affects
     * either the wielder or enemy.
     * @param context The context of the battle, including the wielder and opponent
     */
    public void onTurnEnd(BattleContext context) {
        // Default implementation does nothing
    }
}
