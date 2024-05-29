/*
Program sprawdza poprawność wpisywanego imienia. W przypadku wystąpienia spacji w imieniu, funkcja wyrzuca zdefiniowany wyjątek WrongStudentName, który jest wyłapywany w pętli głównej Commit6_0.
Poniższe zadania będą się sprowadzały do modyfikacji bazowego kodu. Proces modyfikacji ogólnie może wyglądać następująco:
• Ustalenie jaki błąd chcę się sprawdzić i wyłapać.
• Decyzja, czy użyje się własnej klasy wyjątku, czy wykorzysta już istniejące (np. Exception, IOException).
• Napisanie kodu sprawdzającego daną funkcjonalność. W przypadku warunku błędu wyrzucany będzie wyjątek: throw new WrongStudentName().
• W definicji funkcji, która zawiera kod wyrzucania wyjątku dopisuje się daną nazwę wyjątku, np. public static String ReadName() throws WrongStudentName.
• We wszystkich funkcjach, które wywołują powyższą funkcję także należy dopisać, że one wyrzucają ten wyjątek – inaczej program się nie skompiluje.
• W pętli głównej, w main’ie, w zdefiniowanym już try-catch dopisuje się Nazwę wyjątku i go obsługuje, np. wypisuje w konsoli co się stało.
*/

//Commit6_1. Na podstawie analogii do wyjątku WrongStudentName utwórz i obsłuż wyjątki WrongAge oraz WrongDateOfBirth. 
//Niepoprawny wiek – gdy jest mniejszy od 0 lub większy niż 100. Niepoprawna data urodzenia – gdy nie jest zapisana w formacie DD-MM-YYYY, np. 28-02-2023.

import java.io.IOException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class WrongStudentName extends Exception { }

class WrongAge extends Exception {
    public WrongAge(String message) {
        super(message);
    }
}

class WrongDateOfBirth extends Exception {
    public WrongDateOfBirth(String message) {
        super(message);
    }
}

class InvalidMenuOption extends Exception {
    public InvalidMenuOption(String message) {
        super(message);
    }
}

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                int ex = menu();
                switch (ex) {
                    case 1:
                        exercise1();
                        break;
                    case 2:
                        exercise2();
                        break;
                    case 3:
                        exercise3();
                        break;
                    case 0:
                        return;
                    default:
                        throw new InvalidMenuOption("Niepoprawna opcja menu: " + ex);
                }
            } catch (IOException e) {
                // Obsługa innych wyjątków IOException.
            } catch (WrongStudentName e) {
                System.out.println("Błędne imie studenta!");
            } catch (WrongAge e) {
                System.out.println(e.getMessage());
            } catch (WrongDateOfBirth e) {
                System.out.println(e.getMessage());
            } catch (InvalidMenuOption e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Niepoprawny wybór. Wprowadź liczbę od 0 do 3.");
            }
        }
    }

    public static int menu() throws InvalidMenuOption {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");

        if (!scan.hasNextInt()) {
            scan.next(); // Pobranie niepoprawnej wartości aby móc wyświetlić komunikat o błędzie
            throw new InvalidMenuOption("Niepoprawna opcja menu. Proszę wprowadzić liczbę od 0 do 3.");
        }

        int choice = scan.nextInt();
        return choice;
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine();
        System.out.println("Podaj imie: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();
        return name;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        var name = ReadName();
        System.out.println("Podaj wiek: ");
        var age = scan.nextInt();
        if (age < 0 || age > 100) {
            throw new WrongAge("Niepoprawny wiek: Wiek powinien być między 0 a 100.");
        }
        scan.nextLine();
        System.out.println("Podaj datę urodzenia DD-MM-YYYY");
        var date = scan.nextLine();
        if (!isValidDate(date)) {
            throw new WrongDateOfBirth("Niepoprawna data urodzenia: Data powinna być w formacie DD-MM-YYYY.");
        }
        (new Service()).addStudent(new Student(name, age, date));
    }

    private static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for (Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.println("Podaj imie: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if (wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}