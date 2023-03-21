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
            throw new IllegalArgumentException("Invalid operator :" + s);
         }

         data.removeLast();
         data.removeLast();
         data.addLast(result);

      } else throw new NoSuchElementException("Not enough elements in stack to perform (" + s + ") operation (<2)");
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

      if (pol == null){
         throw new IllegalArgumentException("Argument is null");
      }
      if (pol.trim().isEmpty()){
         throw new IllegalArgumentException("Pol is empty");
      }

      String[] elements = pol.trim().split("\\s+");
      for (int i = 0; i < elements.length; i++) {
         String element = elements[i];
         if (element.matches("-?\\d+")) {
            if (new_stack.data.size() >= 2 && elements.length - i < 2) {
               // checks if there are still enough elements to perform expression
               //System.out.println("Not enough numbers: " + pol);
               throw new IllegalArgumentException("Not enough numbers: " + pol);
            }
            new_stack.push(Long.parseLong(element.trim()));
         } else if (element.matches("[+\\-*/]")) {
            new_stack.op(element);
         } else {
            // Checks if regex is not digit nor arithmetical operation
            //System.out.println("Invalid symbol  (" + element + ") in " + pol);
            throw new RuntimeException("Invalid symbol  (" + element + ") in " + pol);
         }
      }
      if (new_stack.data.size() != 1) {
         throw new RuntimeException("Too many numbers to perform expression: " + pol);
      }
      return new_stack.tos();
   }
}
