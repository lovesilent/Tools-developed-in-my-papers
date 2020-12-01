package com.lovesilent.GameAnalysis;

public final class Constants {
    //This is the configuration file that contains all the basic information for node No. 698 in Facebook dataset
    private Constants() {}

     public enum Actions_pro {
        CHANGE_ROLE_1,
        CHANGE_ROLE_2,
        CHANGE_ROLE_3,
        CHANGE_ROLE_4,
        CHANGE_ROLE_5,
        CHANGE_ROLE_6;
    }

    public enum Actions_inv {
        CHANGE_ROLE_1,
        CHANGE_ROLE_2,
        CHANGE_ROLE_3,
        CHANGE_ROLE_4,
        CHANGE_ROLE_5,
        CHANGE_ROLE_6;
    }

    public static final Integer K = 6;
    public static final Integer S = 4096;
    public static final Double BETA = 0.7;
    public static final Double PHI = 0.5;
    public static final Double GAMA = 0.5;

    public static final Double ZETA = 0.00001;

    public static final Double RPC[] = {
            0.2051,
            0.1858,
            0.1143,
            0.2751,
            0.0837,
            0.1497
    };

    public static final Double PHI1[] = {
            0.2424,
            0.1818,
            0.1667,
            0.1364,
            0.1364,
            0.0606
    };

    public static final Double PHI2[] = {
            0.1242,
            0.1125,
            0.0692,
            0.1665,
            0.0507,
            0.0906
    };

    public static final Double GAMA1[] = {
            0.0882,
            0.1175,
            0.1282,
            0.1567,
            0.1567,
            0.3526
    };

    public static final Double GAMA2[] = {
            0.1991,
            0.1803,
            0.1110,
            0.2670,
            0.0812,
            0.1453
    };


}
