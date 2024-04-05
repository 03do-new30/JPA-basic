package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;
        System.out.println("a == b : " + (a == b)); // true

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");
        System.out.println("address1 == address2 : " + (address1 == address2)); // false

        // 값타입은 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야 한다
        // 값타입은 equals method를 사용해야 한다
        // ==는 인스턴스의 참조값을 비교
        // .eqauls()는 인스턴스의 값을 비교
        System.out.println("address1.equals(address2) : " + address1.equals(address2)); // true
        
    }
}
