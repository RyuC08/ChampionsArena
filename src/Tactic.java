/**
 * A Tactic is a special type of BattleModifier that that a player adds to their
 * loadout. It is automatically applied and provides temporary buffs or effects
 * during battle. Tactics are not activated by the player, but are always in effect
 * when equipped. Examples would be a "Shield Wall" or "Battle Cry" that
 * temporarily increase defense or attack power, respectively.
 */
public abstract class Tactic extends BattleModifier {

    /**
     * Create a new Tactic with the given name, description, and maximum uses.
     * @param name The name of the tactic.
     * @param description The description of the tactic.
     * @param maxUses The maximum number of uses for this tactic.
     */
    public Tactic(String name, String description, int maxUses) {
        super(name, description, maxUses);
    }

    /**
     * Modify the attack power of a champion. This method is called when the
     * champion attacks. The base damage is passed in, and the method should
     * return the modified damage (if any). The default implementation does 
     * not modify the damage.
     * @param baseDamage The base damage being dealt.
     * @param context The context of the battle, including the attacker and defender.
     * @return The modified damage.
     */
    @Override
    public int modifyAttack(int baseDamage, BattleContext context) {
        // Default: no offensive effect
        return baseDamage;
    }

    /**
     * Modify the defense power of a champion. This method is called when the
     * champion defends. The base damage is passed in, and the method should
     * return the modified damage (if any). The default implementation does
     * not modify the damage.
     * @param baseDamage The base damage being dealt.
     * @param context The context of the battle, including the attacker and defender.
     * @return The modified damage.
     */
    @Override
    public int modifyDefense(int baseDamage, BattleContext context) {
        // Default: no defensive effect
        return baseDamage;
    }
}
