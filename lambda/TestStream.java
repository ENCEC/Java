import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {
    public static void main(String[] args) throws Exception{
        //去重、限制个数、计数
        Long count = Stream.of("1","2","3","1").distinct().limit(2).count();
        System.out.println(count);
        System.out.println("------------------------");
        //排序
        List<Integer> list1 = Arrays.asList(3,2,4);
        List<Integer> list2 =list1.stream().sorted((i1,i2)->i1-i2).collect(Collectors.toList());
        list2.forEach((i)->{
            System.out.print(i);
        });
        System.out.println("------------------------");
        //过滤
        List<Integer> list3 =list1.stream().filter(i->i>2).collect(Collectors.toList());
        list3.forEach(System.out::print);
        System.out.println("------------------------");
        //获取map的值
        Map <String,String> map1 = new HashMap<>();
        IntStream.rangeClosed(1,10).forEach(i->map1.put(String.valueOf(i),"str"+i));
        List<String> list4 = map1.entrySet().stream().map(m->m.getValue()).collect(Collectors.toList());
        list4.forEach(System.out::print);
        System.out.println("------------------------");
        //去除逗号
        List<String> list5 = Arrays.asList("String","f,g,h");
        Stream<String> stringStream = list5.stream().map(s->s.replaceAll(",",""));
        stringStream.forEach(System.out::print);
        System.out.println("------------------------");
        //分割字符串
        Stream<String> stringStream2 = list5.stream().flatMap(s->{
            String[] split = s.split(",");
            Stream<String> testStream = Arrays.stream(split);
            return testStream;
        });
        stringStream2.forEach(System.out::print);
        System.out.println("------------------------");

        //比如对给定单词列表 [“Hello”,“World”], 现在想返回列表[“H”,“e”,“l”,“o”,“W”,“r”,“d”]
        //.map 需要返回值 若不要返回值则只用.peek方法
        String[] words = new String[]{"Hello","World"};
        List<String[]> list6 = Arrays.stream(words).map(
                word->word.split(""))
                .distinct().collect(Collectors.toList());
        list6.forEach(System.out::print);
        System.out.println("------------------------");

        //flatMap方法的效果：各个数组并不是分别映射成一个流，而是映射成流的内容
        List<String> list7 = Arrays.stream(words)
                .map(word->word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        list7.forEach(System.out::print);
        System.out.println("------------------------");

        //匹配聚合操作
        List<Integer> list8 = Arrays.asList(1, 2, 3, 4, 5);

        boolean allMatch = list8.stream().allMatch(e -> e > 10);
        boolean noneMatch = list8.stream().noneMatch(e -> e > 10);
        boolean anyMatch = list8.stream().anyMatch(e -> e > 4);

        Integer findFirst = list8.stream().findFirst().get();
        Integer findAny = list8.stream().findAny().get();

        long count1 = list8.stream().count();
        Integer max = list8.stream().max(Integer::compareTo).get();
        Integer min = list8.stream().min(Integer::compareTo).get();

        System.out.println(allMatch+"---"+noneMatch+"---"+anyMatch+"---"
                +findFirst+"---"+findAny+"---"+count1+"---"+max+"---"+min);
        System.out.println("------------------------");
        //lambda表达式做运算操作reduce使用
        Integer sum1 = list8.stream().reduce((a,b)->a+b).get();
        System.out.println(sum1);
        Stream<String> stream1 = Stream.of("test", "t1", "t2", "teeeee", "aaaa", "taaa");
        //两个参数，第一个为初始参数
        System.out.println(stream1.reduce("[value]", (s1, s2) -> s1.concat(s2)));
        System.out.println("------------------------");
        //非并行时
        Stream<String> stream3 = Stream.of("aa", "ab", "c", "ad");
        System.out.println(stream3.reduce(new ArrayList<String>(), (r, t) -> {r.add(t); return r; }, (r1, r2) -> r1));
        Stream<String> stream4 = Stream.of("aa", "ab", "c", "ad");
        stream4.reduce(new ArrayList<String>(),
                (r, t) -> {if (t.contains("a")) {r.add(t);}  return r;},
                (r1, r2) -> r1)
                .stream().forEach(System.out::println);
        System.out.println("------------------------");
        //并行时 当Stream是并行时，第三个参数就有意义了，它会将不同线程计算的结果调用combiner做汇总后返回。
        System.out.println(Stream.of(1, 2, 3).parallel().reduce(4, (s1, s2) -> s1 + s2
                , (s1, s2) -> s1 + s2));
        System.out.println("------------------------");
        //如果是非并行时，初始值为4,然后执行累加，应该是10。那为什么并行时为18，因为多线程并行时，多个线程分别取执行参数2的操作，
        //就是：4+1=5 4+2=6 4+3=7得到了5,6,7这三个数，最后就是把各个线程并行处理的结果再执行第三个参数规定的操作即累加。
        Stream<String> s1 = Stream.of("aa", "ab", "c", "ad");
        s1.parallel().reduce(Collections.synchronizedList(new ArrayList<>()),
                (r, t) -> {if (t.contains("a")) {r.add(t);}  return r; },
                (r1, r2) -> r1)
                .stream().forEach(System.out::println);
        System.out.println("------------------------");
        //累加操作
        Integer num1 = IntStream.rangeClosed(1,100).parallel().boxed().reduce((i1,i2)->i1+i2).get();
        System.out.println(num1);
    }
    //详见：https://blog.csdn.net/weixin_41938314/article/details/119979830
}
