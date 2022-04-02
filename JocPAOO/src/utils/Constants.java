package utils;

import main.Game;

public class Constants {

    public static class UI {

        public static class Buttons {

            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int DEAD = 2;
        public static final int ATTACK = 3;
        public static final int DOOR_IN = 4;
        public static final int DOOR_OUT = 5;
        public static final int FALL = 6;
        public static final int GROUND = 7;
        public static final int JUMP = 8;

        public static int GetSA(int playerAction) {

            return switch (playerAction) {
                case IDLE -> 10;
                case RUNNING -> 6;
                case DOOR_IN, DOOR_OUT -> 7;
                case DEAD -> 4;
                case ATTACK -> 3;
                case FALL, GROUND, JUMP -> 1;
                default -> 1;
            };
        }
    }
}
