package nl.underkoen.underbot;

/**
 * Created by Under_Koen on 10-05-17.
 */
public enum Roles {
    EVERYONE(-1), TIMEOUTED(0), UNDERBOT(1), NIGHTBOT(2), SUPPORTER(3), SUPER_SUPPORTER(4), MOD(5), YOUTUBER(6), ADMIN(7);

    public int role;

    Roles(int role) {
        this.role = role;
    }
}
