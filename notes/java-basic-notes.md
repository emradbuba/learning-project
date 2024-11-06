https://www.pluralsight.com/courses/understanding-java-vm-memory-management
https://app.pluralsight.com/library/courses/java-understanding-solving-memory-problems/table-of-contents

# Java Basics

## Memory management

### Stack, Heap & GarbageCollection

#### Stack

<details>
<summary>What do we have on stack?</summary>

> * Static memory allocation and the execution of a thread
> * It contains **method's** primitive values
> * It contains references **method's** objects (which are stored on a heap)
</details>

<details>
<summary>How values are accessed?</summary>

> It is a LIFO approach
</details>

<details>
<summary>When a new item on stack is created?</summary>

> * When we invoke a new method, a new item containing primitive values and references to heap's objects used by this method is created
> * When method is finished, the method's "block" is removed from the stack and next item is available for, probably, next (calling) method
</details>

<details>
<summary>So what does the <code>StackOverflow</code> means?</summary>

> Method calls a method, which calls a method etc... so there stack memory is full an cannot create new "blocks" for new methods
</details>

<details>
<summary>Is it possible that stack is accessed by multiple threads?</summary>
 
> Nope. Each thread has it's own stack.
</details>

<details>
<summary>What is the allowed size of a stack? </summary>

> Depends on VM and OS, but we are talking about 256KB to 1MB in standard cases. It can be customized by `-Xss`
</details>

#### Heap

<details>
<summary>So what can we find on a heap?</summary>

> Instances of classes, references to which are present on stack area
</details>

<details>
<summary>How is heap area divided?</summary>

> * Young generation area - newly created objects land there
> * Old generation area - new object which got old after some time (GC has some thresholds to indicate when to move from Young to Old generation area)
</details>


#### Comparison Stack vs Heap

<details>
<summary>Difference in terms of management</summary>

> * Stack is managed automatically in a LIFO manner
> * Heap is managed in a more complicated manner by GC
</details>

<details>
<summary>Difference in terms of size</summary>

> * Stack's memory size is fixed and cannot grow and grow without the end.
> * Heap memory is allocated dynamically - it can be limited by user settings which even allow to use entire available physical area
</details>

<details>
<summary>Difference in terms of scope</summary>

> * Stack -> method / thread
> * Heap -> global access
</details>

<details>
<summary>Difference in terms of speed</summary>

> Stack is faster than heap
> > <details>
> > <summary>Why is stack faster?</summary>
> > 
> > * Easier access - LIFO
> > * Has limited method's scope
> > * Has fixed size
> >
> </details>
</details>

#### Example of Stack and Heap: 
```java
public class Application {
    public static void main() {
        int primitiveInt = 3;
        MyClass obj = new MyClass();
        Application app = new Application();
        app.start(obj);
    }
    
    private void start(MyClass myClassObject) {
        String s = myClass.getDefaultName();
        myClassObject = null; // <-- ?
    }
}
```
Basically easy stuff (similar description [here](https://www.digitalocean.com/community/tutorials/java-heap-space-vs-stack-memory#heap-and-stack-memory-in-java-program)), but: 
<details>
<summary>When happens when we pass the parameter to start() method?</summary>

> * A new reference type is created in the new `start()`'s method stack-block and it point to the same object in memo as in the `main()`. 
> * It means I can assign a `null` to `myClassObject` reference, but it does not impact the reference in main.
</details>

#### About Garbage Collection

<details>
<summary>Minor and Major GC</summary>

> * YoungGen memory space (EDEN) - minor GC is invoked there, it is cheap and not distinctive.
> * OldGen memory space - major GC
</details>

<details>
<summary>When the Major GC is invoked?</summary>

> Usually when OldGen is full.
</details>

<details>
<summary>What is the survivor space? How many of such do we have and where?</summary>

> * There are **two** such spaces in HEAP's **young generation (EDEN)**. Objects which survive the minorGC (YoungGen / Eden) garbage collections land there. After some time the so to OldGen.
</details>
 
<details>
<summary>Why Major GC is not so nice? </summary>

> It is nice, as it's necessary, but it takes longer, much longer than MinorGC and freezes the application when invoked many times. 
> That is why it is worth to tune the GC process (advanced stuff) to avoid many MajorGCs.
</details>

<details>
<summary>Examples of customization using <code>-XX...</code> options</summary>

> * `-Xmx` / `-Xms` - Heap which means Young-And-Old Gen together 
> * `-XX:MaxNewSize`, `-XX:NewSize` -- about NewGen
> * `-XX:MaxPermSize` / `-XX:PermSize` -- PermanentGen
</details>

### Other memory related topics

#### String Pool

<details>
<summary>What does the `intern()` method ensures?</summary>

> If String pool contains a String which is `equal` to the one we created, the `"String".intern()` will return a reference to an already existing String from pool.
</details>

<details>
<summary><b>CODE EXAMPLE</b> with String equality: </summary>

> ```java
> public class StringPoolExample {
>     public static void main(String[] args) {
>         String radekA = "Radek";
>         String radekB = "Radek";
>         System.out.println(radekA == radekB); // <-- result 1
> 
>         String kasiaA = "Kasia";
>         String kasiaB = new String("Kasia");
>         System.out.println(kasiaA == kasiaB);  // <-- result 2
> 
>         String franekA = new String("Franek");
>         String franekB = new String("Franek").intern();
>         System.out.println(franekA == franekB);  // <-- result 3
> 
>         String pabloA = "Pablo";
>         String pabloB = "Pab" + "lo";
>         System.out.println(pabloA == pabloB); // <-- result 4
> 
>         String annA = "Ann";
>         String an = "An";
>         String n = "n";
>         String annB = an + n;
>         System.out.println(annA == annB); // <-- result 4b
> 
>         String nickA = "Nick";
>         String nickB = new StringBuilder("N").append("ick").toString();
>         System.out.println(nickA == nickB); // <-- result 5
> 
>         String monicaA = "Monica";
>         String monicaB = monicaA.toString();
>         System.out.println(monicaA == monicaB); // <== result 6
>     }
> }
> ```
> <details>
> <summary>Result 1</summary>
> 
> > result 1: TRUE -- literals are by default 'interned'
> </details>
> 
> <details>
> <summary>Result 2</summary>
> 
> > result 2: FALSE -- the `new` enforced the new object creation
> </details>
> 
> <details>
> <summary>Result 3</summary>
> 
> > result 3: FALSE! -- `intern()` ensures an already existing reference is returned, but it point to the `"A"` which was created when `new String("A")` was being created ;)
> > Since == compares references (i.e., the memory addresses of the objects), and franekA and franekB point to different objects (one in the heap and one in the string pool), the comparison franekA == franekB yields false.
> </details>                    
> 
> <details>
> <summary>Result 4</summary>
> 
> > result 4: TRUE -- concatenation results are not interned, but `"Ra" + "dek"` was probably changed by compiler to `"Radek"` as combining two known literals;
> </details>
> 
> <details>
> <summary>Result 4b</summary>
> 
> > result 4b: FALSE -- here same as 4. but probably compiler did not optimize the `String` so there were to `new String` created at the end
> </details>
> 
> <details>
> <summary>Result 5</summary>
> 
> > result 5: FALSE -- `StringBuilder`/`StringBuffer` results are not interned
> </details>
> 
> <details>
> <summary>Result 6</summary>
> 
> > result 6: TRUE -- `toString` of `String` returns just the `this` reference
> </details>
</details>

#### And...

<details>
<summary>Passing by Value or Reference?</summary>

> * In Java everything is passed by value - even if we send a reference variable pass a value of the address.
> * In other words, if I pass an `object` a method parameter and assign null to it, the original ref variable will not be affected.
> * So we do not pass address but a reference to this address...
</details>



