package ch.heigvd;

import ch.heigvd.data.*;

class Pair<T, U>
{
    private T first;
    private U second;
    public Pair(T first, U second) {
        this.first = first; this.second = second;
    }
    public T first() { return first; }
    public U second() { return second; }
    public String toString() {
        return "(" + first + ", " + second + ")"; // toString()
    }
}


enum op{
    plus{
        @Override
        int eval(int a, int b) {
            return a+b;
        }
    },
    minus{
        @Override
        int eval(int a, int b) {
            return a-b;
        }
    },
    mult{
        @Override
        int eval(int a, int b) {
            return a*b;
        }
    };
    abstract int eval(int a, int b);
}


record testRecod(int a, int b, String c){

}



public class POO {

    public static void main(String[] args) {
        int a,b;
        a=2;b=3;

        for (op o : op.values()){
            System.out.printf("%d %s %d = %d\n",a,o,b,o.eval(a,b));
        }

        testRecod t = new testRecod(1,2,"test");
        System.out.println(t);
    }
}