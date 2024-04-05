package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        // 자바의 기본 타입은 절대 공유되지 않는다
        // Side Effect가 없다
        int a = 10;
        int b = a;

        a = 20;

        System.out.println("a = " + a); // 20
        System.out.println("b = " + b); // 10

        // 래퍼 클래스는 reference를 공유는 가능하지만
        // 이 값을 변경할 방법이 없다
        Integer aa = new Integer(10);
        Integer bb = aa;
//        aa.setValue(20); 같은 방법이 없다
        // -> Side Effect가 없다
        System.out.println("aa = " + aa); // 10
        System.out.println("bb = " + bb); // 10
    }
}
