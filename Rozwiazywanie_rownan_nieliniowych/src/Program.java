import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    static final Scanner scan;

    static {
        scan = new Scanner(System.in);
    }

    private static final double E = 0.001;
    private static int counterOfIteration = 0;

    public static void main(String[] args) {
        System.out.println("Witaj w programie wyznaczania przybliżonego rozwiązania metodą stycznych(Newtona)");
        int polynomialDegree = getPolynomialDegree();
        ArrayList<Double> polynomial = readPolynomial(polynomialDegree);
        writePolynomial(polynomial);
        double[] range = getRange(polynomial);
        ArrayList<Double> firstDerivative = getDerivative(polynomial);
        ArrayList<Double> secondDerivative = getDerivative(firstDerivative);
        double startElement = getStartElement(polynomial, secondDerivative, range);
        double result = calculatingTheApproximateSolution(startElement, polynomial, firstDerivative);
        System.out.println("----------------------------------------------");
        System.out.printf("Przyblizone rozwiazanie rownania wynosi: %.4f", result);
        System.out.println();
        System.out.println("Szukane przyblizenie zostalo znalezione z dokladnoscia do E = 0.001");
        System.out.println("Rozwiazanie zostalo znalezione po " + counterOfIteration + " iteracjach algorytmu.");
        System.out.println("----------------------------------------------");

    }

    //Obliczenia przyblizonego rozwiazania algorytmem stycznych
    public static double calculatingTheApproximateSolution(double startElement, ArrayList<Double> polynomial, ArrayList<Double> firstDerivative) {

        double x = 0;
        double valueOfX = 0;
        do {
            x = startElement - ((valueAtPoint(polynomial, startElement)) / (valueAtPoint(firstDerivative, startElement)));
            startElement = x;
            valueOfX = Math.abs(valueAtPoint(polynomial, x));
            counterOfIteration++;
        }
        while (valueOfX > E);

        return x;
    }

    //Ustalenie stopnia wielomianu
    public static int getPolynomialDegree() {
        double polynomialCoefficient;

        System.out.print("Podaj stopień wielomianu: ");
        String userData;

        userData = scan.nextLine();

        int degreeOfPolynomial = 0;

        try {
            degreeOfPolynomial = Integer.parseInt(userData);
        } catch (Exception ex) {
            System.out.println("Stopień wielomianu musi być liczbą!");
            return getPolynomialDegree();
        }
        return degreeOfPolynomial;
    }

    //Wczytanie wielomianu z klawiatury
    public static ArrayList<Double> readPolynomial(int degreeOfPolynomial) {
        ArrayList<Double> wielomian = new ArrayList();
        double polynomialCoefficient;

        String userData;

        System.out.println("Teraz dodaj wspolczynniki wielomianu, jesli brak danej potegi - wpisz 0");

        for (int i = degreeOfPolynomial; i >= 1; --i) {

            System.out.print("Podaj współczynnik dla stopnia " + i + ": ");
            userData = scan.nextLine();
            try {
                polynomialCoefficient = Double.parseDouble(userData);
            } catch (Exception ex) {
                System.out.println("Współczynnik wielomianu musi być liczbą!");
                return readPolynomial(degreeOfPolynomial);
            }
            wielomian.add(polynomialCoefficient);
        }

        System.out.print("Podaj wyraz wolny dla wielomianu: ");
        userData = scan.nextLine();
        try {
            polynomialCoefficient = Double.parseDouble(userData);
        } catch (Exception ex) {
            System.out.println("Wyraz wolny wielomianu musi być liczbą!");
            return readPolynomial(degreeOfPolynomial);
        }
        wielomian.add(polynomialCoefficient);

        return wielomian;
    }

    //Wypisanie wielomianu do konsoli po wczytaniu z klawiatury
    public static void writePolynomial(ArrayList<Double> polynomial) {
        int j = 0;
        int k = polynomial.size() - 1;
        System.out.print("Twój wielomian to: ");
        System.out.print("W(x) = ");
        for (int i = polynomial.size(); i > 0; i--) {
            if (k != 0) {
                if (polynomial.get(j) != 0) {
                    System.out.print(polynomial.get(j) + "x^" + k + " + ");
                }
                j++;
                k--;
            } else {
                System.out.print(polynomial.get(j));
            }
        }
    }

    //Ustalenie przedzialu dla zawierajacego pierwiastek rownania
    public static double[] getRange(ArrayList<Double> polynomial) {
        double range[] = new double[2];
        System.out.println();
        System.out.println("Określ przedział [a,b]");
        System.out.print("Podaj liczbę a: ");
        double a = scan.nextDouble();
        range[0] = a;
        System.out.print("Podaj liczbę b: ");
        double b = scan.nextDouble();
        ArrayList<Double> firstDerivative = getDerivative(polynomial);

        //TWIERDZENIE BOLZANO-CAUCHY'EGO I TWIERDZENIE O IZOLACJI PIERWIASTKA ROWNANIA
        if ((b > a)) {
            range[1] = b;
            return range;
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Podano zly przedzial!");
                return getRange(polynomial);
            }
        }
    }

    //Ustalenie elementu startowego do poczatku obliczen
    public static double getStartElement(ArrayList<Double> polynomial, ArrayList<Double> secondDerivative, double[] range) {

        double a = range[0];
        double b = range[1];

        double valueAtPointOfPolynomial = valueAtPoint(polynomial, a);
        double valueAtPointOfSecondDerivative = valueAtPoint(secondDerivative, a);

        if ((valueAtPointOfPolynomial * valueAtPointOfSecondDerivative) > 0) {
            System.out.println("Punktem startowym zostaje a = " + a);
            return a;
        } else {
            System.out.println("Punktem startowym zostaje b, poniewaz f(a) * f''(a) < 0, b =  " + b);
            return b;
        }
    }



    //Wyliczenie wartosci w punkcie w danym wielomianie
    public static double valueAtPoint(ArrayList<Double> polynomial, double x) {
        double result = polynomial.get(0);

        for (int i = 0; i <= polynomial.size() - 2; ++i) {
            result = result * x + polynomial.get(i + 1);
        }
        return result;
    }

    //Wyliczenie pochodnej z wielomianu
    public static ArrayList<Double> getDerivative(ArrayList<Double> polynomial) {
        ArrayList<Double> derivative = new ArrayList(polynomial.size() - 1);
        for (int i = 0; i < polynomial.size() - 1; i++)
            derivative.add(polynomial.get(i) * (polynomial.size() - i - 1));
        return derivative;
    }
}