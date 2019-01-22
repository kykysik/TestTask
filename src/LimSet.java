import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Размер множества не может превышать 10 элементов.
 * Доступны операции добавления, удаления и проверка наличия элемента.
 * Если при добавлении очередного элемента размер множества превышает 10 - то удаляется
 * элемент, к которому было наименьшее количество обращений ( вызовов метода contains() )
 * Если таких элементов несколько - удаляется любой один из них.
 *
 * @param <E>
 */

public class LimSet<E> implements LimitedSet<E> {

    private HashMap<E, Integer> map;
    private static final Object PRESENT = new Object();
    private int size;

    public LimSet() {
        this.size = 10;
        map = new HashMap<>();
    }

    public LimSet(int size) {
        this.size = size;
        map = new HashMap<>();
    }

    @Override
    public void add(E e) {
        if (map.size() < size) {
            map.put(e, 0);

        } else {
            deleteKeyWithMinValue();
            map.put(e, 0);
        }
    }

    @Override
    public boolean remove(E e) {
        return map.remove(e) == PRESENT;
    }

    @Override
    public boolean contains(E e) {
        if (map.containsKey(e)) {
            int temp = map.get(e);
            map.put(e, ++temp);
        }

        return map.containsKey(e);
    }

    @Override
    public String toString() {
        return "src.LimSet" + map.keySet();
    }

    private void deleteKeyWithMinValue() {
        int temp = map
                .entrySet()
                .stream()
                .mapToInt(Map.Entry::getValue)
                .min()
                .orElseThrow(RuntimeException::new);

        Optional<E> first = map
                .entrySet()
                .stream()
                .filter(value -> value.getValue().equals(temp))
                .map(Map.Entry::getKey).findFirst();

        map.remove(first.orElseThrow(RuntimeException::new));
    }
}
