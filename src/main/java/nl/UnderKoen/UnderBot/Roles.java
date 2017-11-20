package nl.underkoen.underbot;

/**
 * Created by Under_Koen on 10-05-17.
 */
public enum Roles {
    EVERYONE(-1), MUTED(0), UNDERBOT(1), NIGHTBOT(2), SUPPORTER(3), SUPER_SUPPORTER(4), SUPER_SUPPORTER_HITBOX(5), MOD(6), YOUTUBER(7), ADMIN(8);

    public int role;

    Roles(int role) {
        this.role = role;
    }
}