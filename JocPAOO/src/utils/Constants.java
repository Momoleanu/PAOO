package utils;

public class Constants {

    public static class Directions{
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;
        public static final int DOWN=3;
    }
    public static class PlayerConstants{

        public static final int IDLE =0;
        public static final int RUNNING=1;
        public static final int DEAD=2;
        public static final int ATTACK=3;
        public static final int DOOR_IN=4;
        public static final int DOOR_OUT=5;
        public static final int FALL=6;
        public static final int GROUND=7;
        public static final int JUMP=8;

        public static int GetSA(int playerAction) {

            switch(playerAction){

                case IDLE:
                    return 10;
                case RUNNING:
                    return 6;
                case DOOR_IN:
                case DOOR_OUT:
                    return 7;
                case DEAD:
                    return 4;
                case ATTACK:
                    return 3;
                case FALL:
                case GROUND:
                case JUMP:
                    return 1;
                default:
                    return 1;

            }
        }
    }
}
