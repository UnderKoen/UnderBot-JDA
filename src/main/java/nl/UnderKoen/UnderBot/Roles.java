package nl.UnderKoen.UnderBot;

/**
 * Created by Under_Koen on 10-05-17.
 */
public enum Roles {
    EVERYONE(-1), NIGHTBOT(0), SUPPORTER(1), MOD(2), YOUTUBER(3), ADMIN(4);

    public int role;

    Roles(int role) {
        this.role = role;
    }
}
