# Interpreted

# Dynamic Typing
x = 5
print(x)
print(type(x))
x = 5.5
print(x)
print(type(x))
x = (1 > 0)
print (x)
print(type(x))

# " or ' for strings where " is interpolation
x = "Hello " + 'world'
print(x)

# Syntactic support for arrays
x = ["wild", "attack", "monkeys"]
print(x)
print(x[0]) # Zero indexed

# Array concatenation, but types must match!
x += ["with"]
print(x)

# What does this do?
x += "lasers"
print(x)
