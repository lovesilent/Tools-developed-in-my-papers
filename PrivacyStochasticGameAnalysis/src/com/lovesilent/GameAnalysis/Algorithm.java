package com.lovesilent.GameAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static com.lovesilent.GameAnalysis.Constants2.*;

public class Algorithm {

    private List<State> AllStates;

    public Algorithm(List<State> allStates) {
        // initial all states
        AllStates = allStates;
    }

    public void updateWeights(double bn, double cn) {
        List<State> updateStates = new ArrayList<>();
        for (State cur_state : AllStates) {
            int state_id = cur_state.getCur_tag().getId();
            Random r0 = new Random();
            // update Protector
            List<Double> update_Fpro = new ArrayList<>();
            double update_Vpro = cur_state.getValue_pro();
            double temp_pro_Fsum = 0.0;
            double temp_pro_Vsum = 0.0;
            for (int a = 0; a < K; ++a) {
                double cur_Fpro = cur_state.getF_pro().get(a);
                double bErrorPro = computeBErrorPro(state_id, a);
                double pdUPro = computePartialDifferentialUPro(state_id, a);
                double nex_Fpro = cur_Fpro - bn * Math.sqrt(cur_Fpro) * (-bErrorPro) * SGNfunc(pdUPro);
                if (nex_Fpro < 0.0) {
                    nex_Fpro = ZETA;
                }
                if (nex_Fpro > 1.0) {
//                    throw new IllegalArgumentException("nex_Fpro出错！");
                    nex_Fpro = 1.0 - ZETA;
                }
                temp_pro_Fsum += nex_Fpro;
                temp_pro_Vsum += cur_Fpro * bErrorPro;
                update_Fpro.add(nex_Fpro);
            }
            if (temp_pro_Fsum < 0.0) {
                throw new IllegalArgumentException("temp_pro_Fsum出错！");
            }

            int index_pro = r0.nextInt(K);
            double temp0_pro = (temp_pro_Fsum - 1.0);
            update_Fpro.set(index_pro, update_Fpro.get(index_pro) - temp0_pro);
            if (update_Fpro.get(index_pro) < 0.0) {
//                throw new IllegalArgumentException("temp_pro_Fsum出错！");
                update_Fpro.set(index_pro, ZETA);
            }


            update_Vpro += cn * temp_pro_Vsum;

            // update Invader
            List<Double> update_Finv = new ArrayList<>();
            double update_Vinv = cur_state.getValue_inv();
            double temp_inv_Fsum = 0.0;
            double temp_inv_Vsum = 0.0;
            for (int b = 0; b < K; ++b) {
                double cur_Finv = cur_state.getF_inv().get(b);
                double bErrorInv = computeBErrorInv(state_id, b);
                double pdUInv = computePartialDifferentialUInv(state_id, b);
                double nex_Finv = cur_Finv - bn * Math.sqrt(cur_Finv) * (-bErrorInv) * SGNfunc(pdUInv);
                if (nex_Finv < 0.0) {
                    nex_Finv = ZETA;
                }
                if (nex_Finv > 1.0) {
//                    throw new IllegalArgumentException("nex_Finv出错！");
                    nex_Finv = 1.0 - ZETA;
                }
                temp_inv_Fsum += nex_Finv;
                temp_inv_Vsum += cur_Finv * bErrorInv;
                update_Finv.add(nex_Finv);
            }
            if (temp_inv_Fsum < 0.0 ) {
                throw new IllegalArgumentException("temp_inv_Fsum出错！");
            }

            double temp0_inv = (temp_inv_Fsum - 1.0);
            int index_inv = r0.nextInt(K);
            update_Finv.set(index_inv, update_Finv.get(index_inv) - temp0_inv);
            if (update_Finv.get(index_inv) < 0.0) {
//                throw new IllegalArgumentException("temp_inv_Fsum出错！");
                update_Finv.set(index_inv, ZETA);
            }


            update_Vinv += cn * temp_inv_Vsum;

            State update_state = new State(update_Fpro,update_Finv,update_Vpro,update_Vinv);
//            cur_state.setF_pro(update_Fpro);
//            cur_state.setF_inv(update_Finv);
//            cur_state.setValue_pro(update_Vpro);
//            cur_state.setValue_inv(update_Vinv);
            updateStates.add(update_state);
        }

        for (int n = 0; n < S; ++n) {
            State oldState = AllStates.get(n);
            State newState = updateStates.get(n);
            oldState.setF_pro(newState.getF_pro());
            oldState.setF_inv(newState.getF_inv());
            oldState.setValue_pro(newState.getValue_pro());
            oldState.setValue_inv(newState.getValue_inv());
            AllStates.set(n, oldState);
        }


    }

    public Double computeBErrorPro(int state_id, int a_index) {
        return computeQPro(state_id, a_index) - AllStates.get(state_id).getValue_pro();
    }

    public Double computeBErrorInv(int state_id, int b_index) {
        return computeQInv(state_id, b_index) - AllStates.get(state_id).getValue_inv();
    }

    public Double computeQPro(int state_id, int a_index) {
        State cur_state = AllStates.get(state_id);
        List<String> nex_tags = cur_state.getNex_tags();
        double result = cur_state.getPayoff_protector();
        double temp = 0.0;
        for (int b = 0; b < K; ++b) {
            State nex_state = AllStates.get(Integer.parseInt(nex_tags.get(a_index * K + b), 2));
            temp += nex_state.getValue_pro() * cur_state.getF_inv().get(b);
        }
        result += BETA * temp;
        return result;
    }

    public Double computeQInv(int state_id, int b_index) {
        State cur_state = AllStates.get(state_id);
        List<String> nex_tags = cur_state.getNex_tags();
        double result = cur_state.getPayoff_invader();
        double temp = 0.0;
        for (int a = 0; a < K; ++a) {
            State nex_state = AllStates.get(Integer.parseInt(nex_tags.get(a * K + b_index), 2));
            temp += nex_state.getValue_inv() * cur_state.getF_pro().get(a);
        }
        result += BETA * temp;
        return result;
    }

    public Double computePartialDifferentialUPro(int state_id, int a_index) {
        State cur_state = AllStates.get(state_id);
        List<String> nex_tags = cur_state.getNex_tags();
        double result = cur_state.getPayoff_protector() + cur_state.getPayoff_invader();
        double temp = 0.0;
        for (int b = 0; b < K; ++b) {
            State nex_state = AllStates.get(Integer.parseInt(nex_tags.get(a_index * K + b), 2));
            temp += (nex_state.getValue_pro() + nex_state.getValue_inv()) * cur_state.getF_inv().get(b);
        }
        result += BETA * temp;
        return -result;
    }

    public Double computePartialDifferentialUInv(int state_id, int b_index) {
        State cur_state = AllStates.get(state_id);
        List<String> nex_tags = cur_state.getNex_tags();
        double result = cur_state.getPayoff_protector() + cur_state.getPayoff_invader();
        double temp = 0.0;
        for (int a = 0; a < K; ++a) {
            State nex_state = AllStates.get(Integer.parseInt(nex_tags.get(a * K + b_index), 2));
            temp += (nex_state.getValue_pro() + nex_state.getValue_inv()) * cur_state.getF_pro().get(a);
        }
        result += BETA * temp;
        return -result;
    }

    public Double SGNfunc(Double result) {
        if (result > ZETA) {
            return 1.0;
        } else if (result < (-1.0 * ZETA)) {
            return -1.0;
        } else {
            return result;
        }
    }

    public Double computeObjective() {
        double result = 0.0;
        for (State cur_state : AllStates) {
            List<String> nex_tags = cur_state.getNex_tags();
            double temp1 = cur_state.getValue_pro() + cur_state.getValue_inv();
            double temp2 = cur_state.getPayoff_protector() + cur_state.getPayoff_invader();
            double temp3 = 0.0;
            for (int a = 0; a < K; ++a) {
                for (int b = 0; b < K; ++b) {
                    State nex_state = AllStates.get(Integer.parseInt(nex_tags.get(a * K + b), 2));
                    temp3 += (nex_state.getValue_pro() + nex_state.getValue_inv()) * cur_state.getF_pro().get(a) * cur_state.getF_inv().get(b);
                }
            }
            result += temp1 - temp2 - BETA * temp3;
        }

        return result;
    }

}
