package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }
    private T returnMax_with_C(Comparator<T> c) {
        if (isEmpty()){
            return null;
        }
        T max = get(0);
        for (int i = 1;i<size();i++){
            T item = get(i);
            int compareresult = c.compare(max,item);
            if (compareresult < 0) {
                max = item;
            }
        }
        return max;

    }
    public T max(){
        return returnMax_with_C(comparator);
    }

    public T max(Comparator<T> c) {
        return returnMax_with_C(c);
    }
}
