package lotto.domain;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class LottoStatics {
    // 3개 이상부터 통계
    public static final int MIN_STATIC_RANK = 3;
    public static final int[] RANK_PRIZE = new int[] {0,0,0,5_000,50_000,1_500_000,2_000_000_000};
    private static final String ENTER = "\n";


    private Map<Integer, Integer> rankStatics;
    private int totalGames;

    public LottoStatics() {
        init();
    }

    public Profit getProfit() {
        double total = 0;
        for (int rank : rankStatics.keySet()) {
            total += getRankCount(rank) * RANK_PRIZE[rank];
        }
        return new Profit(total,totalGames * LottoGame.PRICE);
    }

    private void init() {
        rankStatics = new HashMap<>();
        for( int rank = MIN_STATIC_RANK; rank <= LottoGame.LOTTO_NUMBER_COUNT; rank++) {
            rankStatics.computeIfAbsent(rank,k->0);
        }
    }

    public void addStatic(int rank) {
        totalGames++;
        if (rank >= MIN_STATIC_RANK) {
            rankStatics.computeIfPresent(rank,(k,v)->v+1);
        }
    }

    public int getRankCount(int rank) {
        return rankStatics.getOrDefault(rank,0);
    }

}
