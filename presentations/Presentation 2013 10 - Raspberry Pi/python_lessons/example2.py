#calculation(2,4);

def calculation(x,y):
   z = add(x,y);
   z *= z;
   return z;

def add(x,y):
   return x + y;

x = calculation(2,4);
print (x);












# If you move definition of "add" to the end, it fails.
