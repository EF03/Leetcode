package alibaba;

/**
 * @author Ron
 * @date 2020/8/13 下午 04:17
 */
public class MethodTest {

    public static void main(String[] args) {
        /*
         * 23.循环体内，字符串的连接方式，使用StringBuilder的append方法进行扩展
         *
         *   下例中，反编译出的字节码文件显示每次循环都会new出一个StringBuilder对象，然后进行append
         *   操作，最后通过toString方法返回String对象，造成内存资源浪费
         *   反例：
         *
         * */
        String str = "start";
        for (int i = 0; i < 100; i++) {
            str = str + "hello";
        }

        /*
         * 26.类成员与方法访问控制从严
         *
         *   1） 如果不允许外部直接通过new来创建对象，那么构造方法必须是private。
         *   2） 工具类不允许有public或default构造方法。
         *   3） 类非static成员变量并且与子类共享，必须是protected
         *   4） 类非static成员变量并且仅在本类使用，必须是private
         *   5） 类static成员变量如果仅在本类使用，必须是private
         *   6） 若是static成员变量，考虑是否为final
         *   7） 类成员方法只供类内部调用，必须是private
         *   8） 类成员方法只对继承类公开，那么限制为protected
         *
         *   任何类、方法、参数、变量，严控访问范围。
         *   过于宽泛的访问范围，不利于模块解耦。
         *   思考：如果是一个private的方法，想删除就删除，可是一个public的service成员方法或成员变量，
         *   删除一下，不得手心冒点汗吗？
         *   变量像自己的小孩，尽量在自己的视线内，变量作用域太大，无限制的到处跑，那么你会担心的
         *
         * */


    }






    private int data;
    public MethodTest(int data) {
        this.data = data;
    }
    public Integer getData() {
        /*
         *  22 setter方法中，参数名称与类成员变量名称一致，this.成员名 = 参数名。
         *  在getter/setter方法中，不要增加业务逻辑，增加排查问题的难度
         *  反例：
         * */
        boolean condition = false;
        if (condition) {
            return this.data + 100;
        } else {
            return this.data - 100;
        }
    }
}
