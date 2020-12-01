package com.lovesilent.GameAnalysis;

import java.util.ArrayList;
import java.util.List;

import static com.lovesilent.GameAnalysis.Constants2.*;

public class State {

    private Tags cur_tag;

    private List<String> nex_tags = new ArrayList<>();

    private List<Double> f_pro = new ArrayList<>();
    private List<Double> f_inv = new ArrayList<>();
    private Double value_pro;
    private Double value_inv;

    private Double payoff_protector;
    private Double payoff_invader;

    public State(State state) {
        f_pro = state.getF_pro();
        f_inv = state.getF_inv();
        value_pro = state.getValue_pro();
        value_inv = state.getValue_inv();
    }

    public State(List<Double> update_fpro, List<Double> update_finv, double update_vpro, double update_vinv) {
        f_pro = update_fpro;
        f_inv = update_finv;
        value_pro = update_vpro;
        value_inv = update_vinv;
    }


    public State(Integer id, String binary) {
        // initial the state
        cur_tag = new Tags(id, binary);

        // initial the weight
        for (int i = 0; i < K; ++i) {
            f_pro.add(1.0 / K);
            f_inv.add(1.0 / K);
        }

        // initial the v
        value_pro = 3.0;
        value_inv = 3.0;

        // compute payoff u
        double benefit_pro = getSocialBenefits();
        double cost_inv = getInvaderCost();
        double entropy = getInformationPropagationEntropy();
        payoff_protector = benefit_pro - entropy;
        payoff_invader = entropy - cost_inv;

        // derive next states
        for (int i = 0; i < K; ++i) {
            for (int j = 0; j < K; ++j) {
                nex_tags.add(cur_tag.changeTagsAction(i, j));
            }
        }

    }


    private Double getSocialBenefits() {
        double result = 0.0;
        List<Integer> tag_pro = cur_tag.getTag_pro();
        for (int i = 0; i < K; ++i) {
            if (tag_pro.get(i) == 1) {
                result += PHI * PHI1[i] + (1 - PHI) * PHI2[i];
            }
        }
        return result;
    }

    private Double getInvaderCost() {
        double result = 0.0;
        List<Integer> tag_inv = cur_tag.getTag_inv();
        for (int i = 0; i < K; ++i) {
            if (tag_inv.get(i) == 1) {
                result += GAMA * GAMA1[i] + (1 - GAMA) * GAMA2[i];
            }
        }
        return result;
    }

    private Double getInformationPropagationEntropy() {
        double result = 0.0;
        List<Integer> tag_pro = cur_tag.getTag_pro();
        List<Integer> tag_inv = cur_tag.getTag_inv();
        int count = 1;
        double invader_psum = 0.0;
        for (int i = 0; i < K; ++i) {
            if (tag_pro.get(i) == 1) {
                count++;
                if (tag_inv.get(i) == 1) {
                    invader_psum += RPC[i];
                }
            }
        }

        //privacy no leak
        if (count == 1) {
            return 0.0;
        }

        //privacy must leak
        if (invader_psum >= 1) {
            return 1.0;
        }

        double temp1 = (count - (count - 1) * (1 - invader_psum)) / count;
        double temp2 = (1 - invader_psum) / count;
        result = (temp1 * Math.log(1 / temp1) + (count - 1) * temp2 * Math.log(1 / temp2)) / Math.log(2);

        double origin_entropy = Math.log(count) / Math.log(2);

        return 1 - (result / origin_entropy);
    }

    public Tags getCur_tag() {
        return cur_tag;
    }

    public void setCur_tag(Tags cur_tag) {
        this.cur_tag = cur_tag;
    }

    public Double getPayoff_protector() {
        return payoff_protector;
    }

    public void setPayoff_protector(Double payoff_protector) {
        this.payoff_protector = payoff_protector;
    }

    public Double getPayoff_invader() {
        return payoff_invader;
    }

    public void setPayoff_invader(Double payoff_invader) {
        this.payoff_invader = payoff_invader;
    }

    public List<Double> getF_pro() {
        return f_pro;
    }

    public void setF_pro(List<Double> f_pro) {
        this.f_pro = f_pro;
    }

    public List<Double> getF_inv() {
        return f_inv;
    }

    public void setF_inv(List<Double> f_inv) {
        this.f_inv = f_inv;
    }

    public Double getValue_pro() {
        return value_pro;
    }

    public void setValue_pro(Double value_pro) {
        this.value_pro = value_pro;
    }

    public Double getValue_inv() {
        return value_inv;
    }

    public void setValue_inv(Double value_inv) {
        this.value_inv = value_inv;
    }

    public List<String> getNex_tags() {
        return nex_tags;
    }

    public void setNex_tags(List<String> nex_tags) {
        this.nex_tags = nex_tags;
    }
}
