package nl.underkoen.underbot;

/**
 * Created by Under_Koen on 10-05-17.
 */
public enum Roles {
    EVERYONE(-1), NIGHTBOT(0), SUPPORTER(1), SUPER_SUPPORTER(2), MOD(3), YOUTUBER(4), ADMIN(5);

    public int role;

    Roles(int role) {
        this.role = role;
    }
}
