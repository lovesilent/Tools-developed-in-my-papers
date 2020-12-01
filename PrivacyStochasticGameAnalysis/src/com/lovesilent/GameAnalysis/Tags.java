package com.lovesilent.GameAnalysis;

import java.util.ArrayList;
import java.util.List;
import static com.lovesilent.GameAnalysis.Constants2.K;

public class Tags {
    private Integer id;
    private String binary;
    private List<Integer> tag_pro = new ArrayList<>();
    private List<Integer> tag_inv = new ArrayList<>();

    public Tags(Integer id, String binary) {
        // initial tags characterization
        this.id = id;
        this.binary = binary;
        for (int i = 0; i < K; ++i) {
            tag_pro.add(binary.charAt(i)-'0');
            tag_inv.add(binary.charAt(i+K)-'0');
        }
    }

    public String changeTagsAction(int pro_index, int inv_index){
        String nex_binary = "";
        for (int i = 0; i < 2*K; ++i) {
            if (i < K) {
                if (i == pro_index) {
                    nex_binary += 1-tag_pro.get(i);
                } else {
                    nex_binary += tag_pro.get(i);
                }
            } else {
                if (i == inv_index + K) {
                    nex_binary += 1-tag_inv.get(i-K);
                } else {
                    nex_binary += tag_inv.get(i-K);
                }
            }
        }
        return nex_binary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getTag_pro() {
        return tag_pro;
    }

    public void setTag_pro(List<Integer> tag_pro) {
        this.tag_pro = tag_pro;
    }

    public List<Integer> getTag_inv() {
        return tag_inv;
    }

    public void setTag_inv(List<Integer> tag_inv) {
        this.tag_inv = tag_inv;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }
}
