package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Controller {

    private double[][] matrizAdyacencia = new double[28][28];
    private String[][] matrizCoordenadas = new String[4][28];

    public void llenarMatriz() {
        //se abre un flujo de datos entre el aplicativo y el archivo "matriz"
        FileReader reader;
        BufferedReader reader1;

        try {
            //se establece el nombre y direccion del archivo
            reader = new FileReader("C:/Users/HP/IdeaProjects/aplicacionAEstrella/src/resources/matriz.txt");
            reader1 = new BufferedReader(reader);

            //dos cilos anidados recorren el archivo y guardan  los datos en una matriz
            for (int i = 0; i < 28; i++) {
                String fila = reader1.readLine();
                String[] datos = fila.split("\t");
                for (int j = 0; j < 28; j++) {
                    matrizAdyacencia[i][j] = Double.parseDouble(datos[j]);
                }
            }
            reader1.close();
            reader.close();

        } catch (IOException e) {
        }
    }

    public void llenarCoordenadas() {
        //se abre un flujo de datos entre el aplicativo y el archivo "coordenadas
        FileReader reader;
        BufferedReader reader1;

        try {
            //se establece el nombre y direccion del archivo
            reader = new FileReader("C:/Users/HP/IdeaProjects/aplicacionAEstrella/src/resources/coordenadas.txt");
            reader1 = new BufferedReader(reader);

            //dos cilos anidados recorren el archivo y guardan  los datos en una matriz
            for (int i = 0; i < 28; i++) {
                String fila = reader1.readLine();
                String[] datos = fila.split("\t");
                for (int j = 0; j < 4; j++) {
                    matrizCoordenadas[i][j] = datos[j];
                }
            }
            reader1.close();
            reader.close();

        } catch (IOException e) {
            System.out.println("no archivo");
        }
    }

    public void btn_ejecutar_algoritmo_action() {
        llenarMatriz();
        llenarCoordenadas();
        //todo ejecucion del algoritmo :P estoy mamao de programar
    }


}
