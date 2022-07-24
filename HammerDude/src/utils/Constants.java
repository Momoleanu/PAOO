package utils;

import main.Game;

public class Constants {

    public static class EnemyConstants {
        public static final int PIG = 0;


        //constante setata in functie de actiunea inamicului de pe sprite sheet

        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int ATTACK = 2;
        public static final int DEAD = 3;
        public static final int HIT = 4;
        public static final int JUMP = 5;
        public static final int FALL = 6;
        public static final int GROUND = 7;

        public static final int PIG_WIDTH_DEFAULT = 34;
        public static final int PIG_HEIGHT_DEFAULT = 28;

        public static final int PIG_WIDTH = (int) (PIG_WIDTH_DEFAULT * Game.SCALE);
        public static final int PIG_HEIGHT = (int) (PIG_HEIGHT_DEFAULT * Game.SCALE);

        public static final int PIG_DRAW_OFFSET_X = (int) (2 * Game.SCALE);
        public static final int PIG_DRAW_OFFSET_Y = (int) (4 * Game.SCALE);

        public static int getSpriteAmount(int enemyType, int enemyState) {

            //numar de animatii per linie

            switch (enemyType) {
                case PIG:
                    switch (enemyState) {
                        case IDLE:
                            return 9;
                        case RUN:
                            return 6;
                        case ATTACK:
                            return 5;
                        case DEAD:
                            return 3;
                        case HIT:
                            return 2;
                        case JUMP:
                        case FALL:
                        case GROUND:
                            return 1;
                    }
            }
            return 0;
        }

        public static int getMaxHealth(int enemyType) {

            //damage in cazul porcului

            switch (enemyType) {
                case PIG:
                    return 10;
                default:
                    return 1;
            }

        }

        public static int enemyDmg(int enemyType) {
            switch (enemyType) {
                case PIG:
                    return 10;
                default:
                    return 0;
            }

        }

    }

    public static class Env {
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;

        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);

        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);

    }

    public static class UI {

        public static class Buttons {

            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {

            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);

        }

        public static class ActButtons {
            public static final int ACT_DEFAULT_SIZE = 56;
            public static final int ACT_SIZE = (int) (ACT_DEFAULT_SIZE * Game.SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
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
        public static final int HIT = 4;
        public static final int FALL = 5;
        public static final int GROUND = 6;
        public static final int JUMP = 7;

        public static int GetSA(int playerAction) {

            return switch (playerAction) {
                case IDLE -> 10;
                case RUNNING -> 6;
                case DEAD -> 4;
                case HIT -> 2;
                case ATTACK -> 3;
                case FALL, GROUND, JUMP -> 1;
                default -> 1;
            };
        }
    }
}
