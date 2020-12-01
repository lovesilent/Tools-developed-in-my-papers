package com.lovesilent.GameAnalysis;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

import static com.lovesilent.GameAnalysis.Constants2.*;

public class Main {

    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();   //获取开始时间

        double decayCn = 0.9999;
        double decayBn = 0.9995;
        List<State> AllStates = new ArrayList<>();
        UtilityFunctions utils = new UtilityFunctions();
        for (int n = 0; n < S; ++n) {
            AllStates.add(new State(n, utils.DecimalToKBinaryString(n)));
        }
        Algorithm algorithm = new Algorithm(AllStates);
        double bn = 0.1;
        double cn = 0.1;
        boolean flag = true;
        int iteration = 0;
        int MAX = 290000;
        int MIN = 1;
        int his_ite = 0;
        while (flag){
            iteration++;
            try
            {
                algorithm.updateWeights(bn, cn);
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e);
                return;
            }
            if (iteration > 32 && iteration % 32 == 0) {
                bn *= decayBn;
                cn *= decayCn;
            }
//            bn *= decayBn;
//            cn *= decayCn;


            if (iteration >= 1000 && iteration % 1000 == 0) {
//            if ((iteration - his_ite) >= MIN && (iteration - his_ite) % MIN == 0) {
                double result = algorithm.computeObjective();
                double re_sum = 0.0;
                int count = 0;
                for (int n = 0; n < S; ++n) {
                    for (int a = 0; a < K; ++a) {
                        for (int b = 0; b < K; ++b) {
                            Double berror_pro = algorithm.computeBErrorPro(n, a);
                            Double berror_inv = algorithm.computeBErrorInv(n, b);
                            State oldState = AllStates.get(n);
                            List<Double> fpro = oldState.getF_pro();
                            List<Double> finv = oldState.getF_inv();
                            Double temp_pro = berror_pro * fpro.get(a);
                            Double temp_inv = berror_inv * finv.get(b);
                            if (temp_pro > ZETA || temp_pro < -1 * ZETA) {
                                count++;
                            }
                            if (temp_inv > ZETA || temp_inv < -1 * ZETA) {
                                count++;
                            }
                            if (berror_pro > ZETA) {
                                re_sum += temp_pro;
                            }
                            if (berror_inv > ZETA) {
                                re_sum += temp_inv;
                            }

                        }
                    }
                }
//            System.out.println("迭代次数：" + iteration + "\t错误约束条件数：" + count + "\t 目标函数值：" + result);
//                if (count > 80000) {
//                    System.out.println(count + "\t" + result + "\t" + re_sum);
//                }else {
                System.out.println(count + "\t" + result + "\t" + re_sum + "\t\t" + bn + "\t" + cn);
//                }

//                if (count < MAX) {
//                    System.out.println(MAX + "\t" + iteration);
//                    if (MAX <= 75000) {
//                        MAX -= 1000;
//                    } else {
//                        MAX -= 5000;
//                    }
//                    MIN = iteration - his_ite;
//                    if (MIN > 1000) {
//                        MIN = 1000;
//                    }
//                    his_ite = iteration;
//                }

                //System.out.println(count + "\t" + result);

                if (count < 500000) {
                    System.out.println(iteration);
//                    if (result < ZETA && result > -1 * ZETA) {
                        utils.WirteToExcel(AllStates);
                        flag = false;
//                    }
                }

            }


        }

        long endTime=System.currentTimeMillis(); //获取结束时间
        String time = utils.getGapTime(endTime - startTime);
        System.out.println("程序运行时间： " + time);

        return;

//        Integer n = 1232;
//        Tags cur_tag = new Tags(n,utils.DecimalToKBinaryString(n));
//        State cur_state = new State(n, utils.DecimalToKBinaryString(n));
//        System.out.println(cur_state.getCur_tag().getTag_pro());
//        System.out.println(cur_state.getCur_tag().getTag_inv());
//        System.out.println(cur_state.getPayoff_protector());
//        System.out.println(cur_state.getPayoff_invader());


//        String nex_binary = cur_tag.changeTagsAction(0,5);
//        System.out.println(nex_binary);
//        System.out.println(Integer.parseInt(nex_binary,2));
//        System.out.println(utils.KBinaryStringToDecimal(nex_binary));

    }
}
