import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LongStack {
   private LinkedList<Long> data;

   public static void main (String[] argum) {

   }

   LongStack() {
      data = new LinkedList<Long>();
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      LongStack cloned = new LongStack();
      cloned.data = new LinkedList<>(this.data);

      return  cloned;
   }

   public boolean stEmpty() {
      return data.isEmpty();
   }

   public void push(long a) {
      data.addLast(a);
   }

   public long pop() {
      if (data.isEmpty()) {
         throw new NoSuchElementException();
      }
      return data.removeLast();
   } // pop

   public void op (String s) {
      if (data.size() > 1){
         long topmost_first = data.getLast();
         long topmost_second = data.get(data.size() - 2);

         long result;

         if (s.equals("+")) {
            result = topmost_first + topmost_second;
         } else if (s.equals("-")) {
            result = topmost_second - topmost_first;
         } else if (s.equals("*")) {
            result = topmost_first * topmost_second;
         } else if (s.equals("/")) {
            result = topmost_second / topmost_first;
         } else {
            throw new IllegalArgumentException("Invalid operator");
         }

         data.removeLast();
         data.removeLast();
         data.addLast(result);

      } else throw new NoSuchElementException();
   }
  
   public long tos() {
      if (data.size() >= 1){
         return data.getLast();
      } else throw new NoSuchElementException();
   }

   @Override
   public boolean equals (Object o) {
      LongStack test_stack = (LongStack) o;
      return data.equals(test_stack.data);
   }

   @Override
   public String toString() {
      StringBuilder string_builder = new StringBuilder();
      for (int i = 0; i < data.size(); i++) {
         string_builder.append(data.get(i));
         string_builder.append(" ");
      }
      return  string_builder.toString();
   }

   public static long interpret(String pol) {
      LongStack new_stack = new LongStack();

      String[] elements = pol.trim().split("\\s+");
      for (int i = 0; i < elements.length; i++) {
         String element = elements[i];
         if (element.matches("-?\\d+")) {
            new_stack.push(Long.parseLong(element.trim()));
         } else if (element.matches("[+\\-*/]")) {
            new_stack.op(element);
         } else {
            throw new RuntimeException();
         }
      }
      if (new_stack.data.size() != 1) {
         throw new RuntimeException();
      }
      return new_stack.tos();
   }
}
