package duikt.practice.otb.service;

import static org.springframework.data.domain.Sort.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class SortAdvice {

    public static Direction getDirectionForSort(String sortDirection) {
        if (sortDirection.equals("+")) {
            return ASC;
        }

        return DESC;
    }

}
