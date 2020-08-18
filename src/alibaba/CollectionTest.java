package alibaba;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ron
 * @date 2020/8/13 下午 04:59
 */
public class CollectionTest {
    /*  集合处理 */

    public static void main(String[] args) {

        /*
         *   2.判断所有集合内部的元素是否为空，使用isEmpty()方法，而不是size()==0的方式
         *
         *  在某些集合中，前者的时间复杂度为O(1)，而且可读性更好
         *
         *  */
        Map<String, Object> map = new HashMap<>(16);
        if (map.isEmpty()) {
            System.out.println("no element in this map.");
        }

        /*
         *   3.在使用java.util.stream.Collectors类的toMap()方法转为Map集合时，
         *     一定要使用含有参数类型为BinaryOperator，参数名为mergeFunction的方法，
         *     否则当出现相同key值时会抛出IllegalStateException异常
         *
         *     说明：参数mergeFunction的作用是当出现key重复时，自定义对value的处理策略
         *
         * */
        // 正例
        List<Pair<String, Double>> pairArrayList = new ArrayList<>(3);
        pairArrayList.add(new Pair<>("version", 12.10));
        pairArrayList.add(new Pair<>("version", 12.19));
        pairArrayList.add(new Pair<>("version", 6.28));
        Map<String, Double> pairMap = pairArrayList.stream().collect(
                // 生成的map集合中只有一个键值对：{version=6.28}
                Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2));

        // 反例
        String[] departments = new String[]{"iERP", "iERP", "EIBU"};
        // 抛出IllegalStateException异常
//        Map<Integer, String> departMap = Arrays.stream(departments)
//                .collect(Collectors.toMap(String::hashCode, str -> str));

//        Map<Integer, String> departMap = Arrays.stream(departments)
//                .collect(Collectors.toMap(String::length, String::toString, (oldValue, newValue) -> newValue));

        /*
         *       4.在使用java.util.stream.Collectors类的toMap()方法转为Map集合时，一定要注意当value为null时会抛NPE异常
         *       说明：在java.util.HashMap的merge方法里会进行如下的判断：
         *
         *       if (value == null || remappingFunction == null)
         *           throw new NullPointerException();
         * */

        // 反例：
        List<Pair<String, Double>> pairArrayList4 = new ArrayList<>(2);
        pairArrayList.add(new Pair<>("version1", 8.3));
        pairArrayList.add(new Pair<>("version2", null));
//        Map<String, Double> map4 = pairArrayList.stream().collect(
//                // 抛出NullPointerException异常
//                Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2));


        /*
         *   使用集合转数组的方法，必须使用集合的toArray(T[] array)，传入的是类型完全一致、长度为0的空数组
         *   直接使用toArray无参方法存在问题，此方法返回值只能是Object[]类，若强转其它类型数组将出现ClassCastException错误
         *
         * */

        /*
         * 说明：使用toArray带参方法，数组空间大小的length：
         *   1） 等于0，动态创建与size相同的数组，性能最好。
         *   2） 大于0但小于size，重新创建大小等于size的数组，增加GC负担。
         *   3） 等于size，在高并发情况下，数组创建完成之后，size正在变大的情况下，负面影响与2相同。
         *   4） 大于size，空间浪费，且在size处插入null值，存在NPE隐患。
         *
         * */

        // 正例：
        List<String> list = new ArrayList<>(2);
        list.add("guan");
        list.add("bao");
        String[] array = list.toArray(new String[0]);

        /*
         *   10. 在使用Collection接口任何实现类的addAll()方法时，都要对输入的集合参数进行NPE判断
         *   在ArrayList#addAll方法的第一行代码即Object[] a = c.toArray(); 其中c为输入集合参数，如果为null，则直接抛出异常
         *
         * */

        /*
         *   11. 使用工具类Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方法，
         *       它的add/remove/clear方法会抛出UnsupportedOperationException异常
         *
         *       说明：asList的返回对象是一个Arrays内部类，并没有实现集合的修改方法。
         *       Arrays.asList体现的是适配器模式，只是转换接口，后台的数据仍是数组
         * */

        String[] str = new String[]{"chen", "yang", "hao"};
        List<String> list11 = Arrays.asList(str);
//        list11.add("yangguanbao");
//        str[0] = "change";


        /*
         *   12.泛型通配符<? extends T>来接收返回的数据，此写法的泛型集合不能使用add方法，
         *       而<? super T>不能使用get方法，两者在接口调用赋值的场景中容易出错
         *
         *       说明：扩展说一下PECS(Producer Extends Consumer Super)原则：
         *           第一、频繁往外读取内容的，适合用<? extends T>。
         *           第二、经常往里插入的，适合用<? super T>
         * */

        /*
         *   14. 不要在foreach循环里进行元素的remove/add操作。
         *       remove元素请使用Iterator方式，如果并发操作，需要对Iterator对象加锁
         *
         *   说明：以上代码的执行结果肯定会出乎大家的意料，那么试一下把“1”换成“2”，会是同样的结果吗？
         *
         * */

//        正例：
        List<String> list14 = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.removeIf("1"::equals);

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if ("2".equals(item)) {
                iterator.remove();
            }
        }

//        反例：
//        for (String item : list) {
//            if ("2".equals(item)) {
//                list.remove(item);
//            }
//        }

        /*
         *     15.在JDK7版本及以上，Comparator实现类要满足如下三个条件，
         *     不然Arrays.sort，Collections.sort会抛IllegalArgumentException异常
         *
         *
         *       说明：三个条件如下
         *               1） x，y的比较结果和y，x的比较结果相反。
         *               2） x>y，y>z，则x>z。
         *               3） x=y，则x，z比较结果和y，z比较结果相同
         *
         * */

//        反例：下例中没有处理相等的情况，交换两个对象判断结果并不互反，
//              不符合第一个条件，在实际使用中可能会出现异常。
        List<Student> listStudent = new ArrayList<>();
        listStudent.add(new Student(2));
        listStudent.add(new Student(2));
        listStudent.add(new Student(3));
        listStudent.add(new Student(1));
        listStudent.add(new Student(3));
        listStudent.sort((o1, o2) -> o1.getId() > o2.getId() ? 1 : -1);
        new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getId() > o2.getId() ? 1 : -1;
            }
        };


    }


}
