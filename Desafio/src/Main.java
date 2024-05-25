import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Ingrese el nombre del directorio: ");
        String directorio = scanner.nextLine();

        System.out.print("Ingrese el nombre del archivo (sin extensión): ");
        String archivo = scanner.nextLine();

        ArrayList<String> lista = new ArrayList<>();
        lista.add("Perro");
        lista.add("Gato");
        lista.add("Juan");
        lista.add("Daniel");
        lista.add("Juan");
        lista.add("Gato");
        lista.add("Perro");
        lista.add("Camila");
        lista.add("Daniel");
        lista.add("Camila");

        crearArchivo(directorio, archivo, lista);


        System.out.print("Ingrese la palabra a buscar: ");
        String palabraABuscar = scanner.nextLine();


        String nombreArchivo = "src/" + directorio + "/" + archivo + ".txt";

        int repeticiones = buscarTexto(nombreArchivo, palabraABuscar);
        if (repeticiones >= 0) {
            System.out.println("Cantidad de repeticiones de '" + palabraABuscar + "' -> " + repeticiones);
        }

        scanner.close();
    }

    public static void crearArchivo(String directorio, String archivo, ArrayList<String> lista) {
        if (!archivo.endsWith(".txt")) {
            archivo += ".txt";
        }

        File dir = new File("src/" + directorio);

        if (!dir.exists()) {
            try {
                if (dir.mkdirs()) {
                    System.out.println("Directorio '" + directorio + "' creado exitosamente.");
                } else {
                    System.out.println("Error al crear directorio.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error al crear directorio: " + e.getMessage());
                return;
            }
        } else {
            System.out.println("El directorio '" + directorio + "' ya existe.");
        }

        File arch = new File(dir, archivo);
        try {
            if (arch.createNewFile()) {
                System.out.println("Archivo '" + archivo + "' creado exitosamente en el directorio '" + directorio + "'.");
            } else {
                System.out.println("El archivo '" + archivo + "' ya existe en el directorio '" + directorio + "'.");
            }
        } catch (IOException e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(arch)) {
            Iterator<String> iterator = lista.iterator();
            while (iterator.hasNext()) {
                writer.write(iterator.next() + System.lineSeparator());
            }
            System.out.println("Contenido escrito en el archivo '" + archivo + "'.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public static int buscarTexto(String nombreArchivo, String palabraABuscar) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists() || !archivo.isFile()) {
            System.out.println("El fichero ingresado no existe");
            return -1;
        }

        int contador = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {

                int index = 0;
                while ((index = linea.indexOf(palabraABuscar, index)) != -1) {
                    contador++;
                    index += palabraABuscar.length();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return contador;
    }
}
