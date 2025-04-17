public abstract class BattleModifier {
    private final String name;
    private final String description;

    private int duration;
    private final int maxDuration;

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
    }

    /**
     * @return The name of this modifier.
     */
    public final String getName() {
        return name;
    }

    /**
     * @return The description of this modifier.
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @return The number of uses remaining for this modifier.
     */
    public final int getDuration() {
        return duration;
    }

    /**
     * @return The maximum number of uses for this modifier.
     */
    public final int getMaxDuration() {
        return maxDuration;
    }


    /**
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
    public final void effectUsed() {
        duration--;
    }

    /**
     * Called when an attack is made. This method should modify the base damage
     * based on the Modifier's effect and return the modified damage. When this
     * method is called and the Modifier is used, the duration should be decreased
     * by calling effectUsed().
     * @param baseDamage The damage being dealt
     * @param context The context of the battle, including the attacker and defender
     * @return The modified damage
     */
    public int modifyAttack(int baseDamage, BattleContext context) {
        return baseDamage;
    };

    /**
     * Called when a defense is made. This method should modify the base damage
     * based on the Modifier's effect and return the modified damage. When this
     * method is called and the Modifier is used, the duration should be decreased
     * by calling effectUsed().
     * @param baseDamage The damage being dealt
     * @param context The context of the battle, including the attacker and defender
     * @return The modified damage
     */
    public int modifyDefense(int baseDamage, BattleContext context) {
        return baseDamage;
    };
}
