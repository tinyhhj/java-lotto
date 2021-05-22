package lotto.view;

import lotto.domain.LottoGame;
import lotto.domain.LottoGames;
import lotto.domain.LottoStatics;
import lotto.domain.Profit;

import java.io.PrintStream;

public class ResultView {
    PrintStream view = new PrintStream(System.out);
    private String ENTER = "\n";
    public void enter() {
        view.println("");
    }

    public void println(Object obj) {
        view.println(obj);
    }

    public void showLottoGames(LottoGames lottoGames) {
        for (LottoGame game : lottoGames) {
            println(game);
        }
        enter();
    }

    public void showLottoStatics(LottoStatics statics) {

        StringBuffer buffer = new StringBuffer();
        Profit profit = statics.getProfit();
        buffer.append("당첨 통계").append(ENTER)
                .append("---------").append(ENTER);
        for( int rank = LottoStatics.MIN_STATIC_RANK; rank <= LottoGame.LOTTO_NUMBER_COUNT; rank++) {
            buffer.append(rank)
                    .append("개 일치 (")
                    .append(LottoStatics.RANK_PRIZE[rank])
                    .append(")- ")
                    .append(statics.getRankCount(rank))
                    .append("개").append(ENTER);
        }
        buffer.append("총 수익률은 ").append(profit).append("입니다.");
        view.println(buffer);
    }
}
