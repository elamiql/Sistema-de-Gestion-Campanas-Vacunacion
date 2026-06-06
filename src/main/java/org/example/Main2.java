package org.example;

import org.example.builder.CampanaBuilder;
import org.example.model.*;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CampanaBuilder cb = new CampanaBuilder();
        OutputOptions o = new OutputOptions();
        boolean sistemaFuncionando = true;

        System.out.println("=== Prueba de getHistorialVacunacion() con 2 Personas ===\n");

        while (sistemaFuncionando) {
            o.initialOptions();
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    System.out.print("Nombre de la campaña: ");
                    cb.setNombre(sc.nextLine());
                    System.out.print("Descripción de la campaña: ");
                    cb.setDescripcion(sc.nextLine());
                    System.out.print("Fecha de inicio: ");
                    cb.setFechaInicio(sc.nextLine());
                    System.out.print("Fecha de termino: ");
                    cb.setFechaTermino(sc.nextLine());
                    System.out.println();
                    Campana c1 = cb.construir();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                     break;
                case 6:
                    break;
                case 7:
                    sistemaFuncionando = false;
                    break;
                default:
                    break;
            }
        }

    }
}

class OutputOptions {
    public void initialOptions() {
        System.out.println("1 - Registrar Campaña");
        System.out.println("2 - Registrar Centro de Vacunación");
        System.out.println("3 - Registrar Paciente");
        System.out.println("4 - Agendar Cita");
        System.out.println("5 - Registrar Vacunación");
        System.out.println("6 - Mostrar Historial Paciente");
        System.out.println("7 - Salir\n");
        System.out.print("Opción: ");
    }
}

