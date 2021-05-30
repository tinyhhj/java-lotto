package lotto.domain;

import lotto.exception.InvalidLottoGame;

import java.util.*;

public class Lotto {
    public static final int PRICE = 1_000;
    public static final int NUMBER_COUNT = 6;

    private Set<LottoNumber> lottoNumbers = new HashSet<>();

    public Lotto(Set<LottoNumber> lotto) {
        lottoNumbers = lotto;
        verifyLottoNumbers();
    }



    public Lotto(LottoNumberFactory factory) {
        for (int i = 0; i < NUMBER_COUNT; i++) {
            lottoNumbers.add(factory.generateNumber());
        }
        verifyLottoNumbers();
    }

    public Lotto(List<Integer> numbers) {
        for (int number : numbers) {
            lottoNumbers.add(new LottoNumber(number));
        }
        verifyLottoNumbers();
    }

    private void verifyLottoNumbers() {
        if (lottoNumbers.size() != NUMBER_COUNT) {
            throw new InvalidLottoGame(String.format("%s %s",InvalidLottoGame.INVALID_LOTTO_GAME, lottoNumbers.size()));
        }
    }

    private static void addLottoNumber(LottoNumberFactory factory, Set<LottoNumber> lottoNumbers) {
        LottoNumber number = factory.generateNumber();
        while (lottoNumbers.contains(number)) {
            number = factory.generateNumber();
        }
        lottoNumbers.add(number);
    }

    public Rank matchCount(Lotto other) {
        int count = 0;
        for (LottoNumber number : lottoNumbers) {
            count = other.increaseCountIfContains(number,count);
        }
        return Rank.of(count);
    }

    private int increaseCountIfContains(LottoNumber number, int count) {
        if (containsNumber(number)) {
            return count + 1;
        }
        return count;
    }

    private boolean containsNumber(LottoNumber number) {
        return lottoNumbers.contains(number);
    }

    @Override
    public String toString() {
        List<LottoNumber> numbers = new ArrayList<LottoNumber>(lottoNumbers);
        Collections.sort(numbers);
        return numbers.toString();
    }
}
