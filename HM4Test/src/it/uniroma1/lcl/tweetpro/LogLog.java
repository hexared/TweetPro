package it.uniroma1.lcl.tweetpro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LogLog implements UniqueUsersCount {

    private final Double[] array = { 0.0, 0.44567926005415, 1.2480639342271, 2.8391255240079, 6.0165231584809,
            12.369319965552, 25.073991603111, 50.482891762408, 101.30047482584, 202.93553338100, 406.20559696699,
            812.74569744189, 1625.8258850594, 3251.9862536323, 6504.3069874480, 13008.948453415, 26018.231384516,
            52036.797246302, 104073.92896967, 208148.19241629, 416296.71930949, 832593.77309585, 1665187.8806686,
            3330376.0958140, 6660752.5261049, 13321505.386687, 26643011.107850, 53286022.550177, 106572045.43483,
            213144091.20414, 426288182.74275, 852576365.81999 };

    public int calculate(List<Long> usersIdList) {
        return logLog(usersIdList);
    }

    private int logLog(List<Long> usersIdList) {
        List<String> hashedUserId = usersIdList.stream()
                                            .map(Long::toBinaryString)
                                            .map(s -> s.substring(s.length() - 8))
                                            .filter(s -> s.contains("1"))
                                            .collect(Collectors.toList());

        int k = hashedUserId.stream()
                            .map(s -> s.substring(s.lastIndexOf("1"))
                            .length()).max(Integer::compareTo).get();

        int m = (int) Math.pow(2.0, k);
        int[] multi = new int[m];
        Random rand = new Random();
        for (String binUser : hashedUserId) {
            int j = rand.nextInt(m);
            multi[j] = Integer.max(multi[j], binUser.substring(binUser.lastIndexOf("1")).length());
        }
        Double sol = array[k]* Math.pow(2, (double) multisetToList(multi).stream().reduce((a, b) -> a + b).get() * 1 / m);
        System.out.println(Math.abs(6592.0 - sol) / 6592.0);
        return sol.intValue();
    }

    private List<Integer> multisetToList(int[] multiset) {
        List<Integer> multiList = new ArrayList<>();
        for (int i = 0; i < multiset.length; i++) {
            multiList.add(multiset[i]);
        }
        return multiList;
    }
}