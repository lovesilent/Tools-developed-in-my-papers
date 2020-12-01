package com.lovesilent.GameAnalysis;

public class Constants2 {
    //This is the configuration file that contains all the basic information for node No. 135704499 in Twitter dataset
    private Constants2() {}

    public enum Actions_pro {
        CHANGE_ROLE_1,
        CHANGE_ROLE_2,
        CHANGE_ROLE_3,
        CHANGE_ROLE_4,
        CHANGE_ROLE_5,
        CHANGE_ROLE_6,
        CHANGE_ROLE_7;
    }

    public enum Actions_inv {
        CHANGE_ROLE_1,
        CHANGE_ROLE_2,
        CHANGE_ROLE_3,
        CHANGE_ROLE_4,
        CHANGE_ROLE_5,
        CHANGE_ROLE_6,
        CHANGE_ROLE_7;
    }

    public static final Integer K = 7;
    public static final Integer S = 16384;
    public static final Double BETA = 0.7;
    public static final Double PHI = 0.5;
    public static final Double GAMA = 0.5;

    public static final Double ZETA = 0.00001;

    public static final Double RPC[] = {
            0.2784,
            0.3522,
            0.1205,
            0.0764,
            0.0440,
            0.0576,
            0.0242
    };

    public static final Double PHI1[] = {
            0.3908,
            0.2184,
            0.1264,
            0.0460,
            0.0460,
            0.0345,
            0.0345
    };

    public static final Double PHI2[] = {
            0.1148,
            0.1452,
            0.0497,
            0.0315,
            0.0181,
            0.0237,
            0.0100
    };

    public static final Double GAMA1[] = {
            0.0220,
            0.0393,
            0.0679,
            0.1866,
            0.1866,
            0.2488,
            0.2488
    };

    public static final Double GAMA2[] = {
            0.2667,
            0.3374,
            0.1154,
            0.0732,
            0.0421,
            0.0551,
            0.0232
    };

}
