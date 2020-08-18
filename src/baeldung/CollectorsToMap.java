package baeldung;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ron
 * @date 2020/8/17 上午 09:21
 */
public class CollectorsToMap {
    private List<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        List<Book> bookList = buildList();
        Map<String, String> map = listToMap(bookList);


    }

    private static List<Book> buildList() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("The Fellowship of the Ring", 1954, "0395489318"));
        bookList.add(new Book("The Two Towers", 1954, "0345339711"));
        bookList.add(new Book("The Return of the King", 1955, "0618129111"));
        return bookList;
    }


    /*  For this scenario we'll use the following overload of the toMap() method:
        Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper)
     */

    public static Map<String, String> listToMap(List<Book> books) {
        return books.stream().collect(Collectors.toMap(Book::getIsbn, Book::getName));
    }

    public static Map<Integer, Book> listToMapWithDupKeyError(List<Book> books) {
        return books.stream().collect(
                Collectors.toMap(Book::getReleaseYear, Function.identity()));
    }

    @Test
    public void whenConvertFromListToMap() {
        assertEquals(3, CollectorsToMap.listToMap(buildList()).size());
    }

    // (expected = IllegalStateException.class)
    @Test
    public void whenMapHasDuplicateKey_without_merge_function_then_runtime_exception() {

        CollectorsToMap.listToMapWithDupKeyError(buildList());
    }

    /*    To resolve it, we need to use a different method with an additional parameter, the mergeFunction:

    *     Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
          Function<? super T, ? extends U> valueMapper,
          BinaryOperator<U> mergeFunction)
    *
    * */

    public Map<Integer, Book> listToMapWithDupKey(List<Book> books) {
        return books.stream().collect(Collectors.toMap(Book::getReleaseYear, Function.identity(),
                (existing, replacement) -> existing));
    }

    @Test
    public void whenMapHasDuplicateKeyThenMergeFunctionHandlesCollision() {
        Map<Integer, Book> booksByYear = listToMapWithDupKey(buildList());
        assertEquals(2, booksByYear.size());
        assertEquals("0395489318", booksByYear.get(1954).getIsbn());
    }

    /*  Other Map Types
    *  By default, a toMap() method will return a HashMap.
       But can we return different Map implementations? The answer is yes:

    Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                             Function<? super T, ? extends U> valueMapper,
                             BinaryOperator<U> mergeFunction,
                             Supplier<M> mapSupplier)
     */

    /*
     *   List to ConcurrentMap
     *   Let's take the same example as above and add a mapSupplier function to return a ConcurrentHashMap:
     *
     *
     *
     * */

    public Map<Integer, Book> listToConcurrentMap(List<Book> books) {
        return books.stream().collect(Collectors.toMap(Book::getReleaseYear, Function.identity(),
                (o1, o2) -> o1, ConcurrentHashMap::new));
    }

    @Test
    public void whenCreateConcurrentHashMap() {
        assertTrue(listToConcurrentMap(buildList()) instanceof ConcurrentHashMap);
    }

    /* Sorted Map
    *
    *
    * */

    public TreeMap<String, Book> listToSortedMap(List<Book> books) {
        return books.stream()
                .collect(
                        Collectors.toMap(Book::getName, Function.identity(), (o1, o2) -> o1, TreeMap::new));
    }

    @Test
    public void whenMapisSorted() {
        assertTrue(listToSortedMap(buildList()).firstKey().equals(
                "The Fellowship of the Ring"));
    }


}


