int PIN_1 = 23;
int PIN_2 = 25;
int PIN_3 = 27;
int PIN_4 = 29;

int counter = 0;

void setup() {
  pinMode(PIN_1, OUTPUT);
  pinMode(PIN_2, OUTPUT);
  pinMode(PIN_3, OUTPUT);
  pinMode(PIN_4, OUTPUT);
}

void loop() {

  ++counter;

  int remainder = counter;

  if (remainder >= 8) {
    digitalWrite(PIN_1, HIGH);
    remainder = remainder - 8;
  } else {
    digitalWrite(PIN_1, LOW);
  }

  if (remainder >= 4) {
    digitalWrite(PIN_2, HIGH);
    remainder = remainder - 4;
  } else {
    digitalWrite(PIN_2, LOW);
  }

  if (remainder >= 2) {
    digitalWrite(PIN_3, HIGH);
    remainder = remainder - 2;
  } else {
    digitalWrite(PIN_3, LOW);
  }

  if (remainder >= 1) {
    digitalWrite(PIN_4, HIGH);
    remainder = remainder - 1;
  } else {
    digitalWrite(PIN_4, LOW);
  }

  delay(1000);

  digitalWrite(PIN_1, LOW);
  digitalWrite(PIN_2, LOW);
  digitalWrite(PIN_3, LOW);
  digitalWrite(PIN_4, LOW);

  delay(1000);

  if (counter == 15) {
    counter = 0;
  }
}
