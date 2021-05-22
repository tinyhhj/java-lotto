package lotto.domain;

import lotto.exception.InvalidLottoGame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LottoGameTest {

    @Test
    public void 로또게임_생성() {
        LottoGame game = new LottoGame();
        assertThat(game.isValid()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideLottoNumbers")
    public void 당첨번호와_로또게임이_일치하는_숫자갯수(int[] lastWeekNumbers, int[] myLottoNumbers, int matchCount) {
        LottoGame winLotto = new LottoGame(lastWeekNumbers);
        LottoGame myLotto = new LottoGame(myLottoNumbers);
        assertThat(winLotto.matchCount(myLotto)).isEqualTo(matchCount);
    }

    private static Stream<Arguments> provideLottoNumbers() {
        return Stream.of(
                Arguments.of(new int[]{1,2,3,4,5,6},new int[] {1,2,3,41,42,43},3),
                Arguments.of(new int[]{1,2,3,4,5,6},new int[] {11,12,13,14,15,16},0),
                Arguments.of(new int[]{1,2,3,4,5,6},new int[] {1,2,3,4,5,6},6)
        );
    }


    @Test
    @DisplayName("당첨번호 갯수가 6개 이상일 때 InvalidLottoGame 에러발생")
    public void 당첨번호가_6개이상입력() {
        assertThatThrownBy(()->new LottoGame(1,2,3,4,5,6,7))
                .isInstanceOf(InvalidLottoGame.class)
                .hasMessageContaining(InvalidLottoGame.INVALID_LOTTO_GAME);
    }

    @Test
    public void 당첨번호_출력() {
        assertThat(new LottoGame(1,2,3,4,5,6).toString())
                .isEqualTo("[1, 2, 3, 4, 5, 6]");
        assertThat(new LottoGame(6,5,4,3,2,1).toString())
                .isEqualTo("[1, 2, 3, 4, 5, 6]");
    }

    public static class IntArrayConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            if (source instanceof String && int[].class.isAssignableFrom(targetType)) {
                String[] stringArray = ((String) source).split("\\s*,\\s*");
                int[] intArray = new int[stringArray.length];
                int idx = 0;
                for( String number : stringArray) {
                    intArray[idx++] = Integer.parseInt(number);
                }
                return intArray;
            } else {
                throw new IllegalArgumentException("Conversion from " + source.getClass() + " to "
                        + targetType + " not supported.");
            }
        }

    }
}
