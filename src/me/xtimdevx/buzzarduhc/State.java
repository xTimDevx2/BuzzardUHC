package me.xtimdevx.buzzarduhc;

/**
 * Created by xTimDevx on 19/05/2017.
 */
public enum State {


    ENDING, INGAME, SCATTERING, LOBBY, CLOSED, PREGAME;

    private static State currentState;

    public static void setState(State state) {
        currentState = state;
    }

    public static boolean isState(State state) {
        return currentState == state;
    }

    public static State getState() {
        return currentState;
    }
}
