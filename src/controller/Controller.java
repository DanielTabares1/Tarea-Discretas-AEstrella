package controller;

import model.Nodo;
import model.Paso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
                    matrizCoordenadas[j][i] = datos[j];
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
        resolver();
        System.out.print("W");
    }

    public void resolver() {
        Nodo[] nodos = new Nodo[28];
        for (int i = 0; i < 28; i++) {
            String nombre = matrizCoordenadas[1][i];
            int x = Integer.parseInt(matrizCoordenadas[2][i]);
            int y = Integer.parseInt(matrizCoordenadas[3][i]);
            nodos[i] = new Nodo(nombre, x, y);
        } // se crean todos los nodos

        ArrayList<Paso> cerrado = new ArrayList<>(); // se crea una lista para los nodos por los que paso
        ArrayList<Paso> abierto = new ArrayList<>(); // se crea una lista para los nodos vecinos
        double pesoAcumulado=0;

        Nodo posicionActual = nodos[0];              // se agrega el nodo A como primer elemento
        cerrado.add(new Paso(posicionActual, null, 0));

        while (!posicionActual.getNombre().equals(nodos[22].getNombre())) {
            System.out.println("analizando con "+posicionActual.getNombre());
            int x = 0;      //determino el indice del vertice que estoy analizando
            for (int i = 0; i < 28; i++) {
                if (matrizCoordenadas[1][i].equals(posicionActual.getNombre())) {
                    x = i;
                    break;
                }
            }

            //se agregan los vecinos
            for (int i = 0; i < 28; i++) {
                if (matrizAdyacencia[x][i] != 0) {
                    boolean tomado = false;
                    int auxN=0;
                    for (int j = 0; j < cerrado.stream().count(); j++) {
                        if (cerrado.get(j).getDestino().getNombre().equals(matrizCoordenadas[1][i])) {
                            tomado = true;
                            System.out.println("ya se había tomado"+cerrado.get(j).getDestino().getNombre());
                            auxN=i;
                        }
                    }
                    // vericamos que no se haya pasado ya por el nodo y se agregan
                    if(!tomado){
                        Nodo d = new Nodo(matrizCoordenadas[1][i], Integer.parseInt(matrizCoordenadas[2][i]), Integer.parseInt(matrizCoordenadas[3][i]));
                        for (int j = 0; j < abierto.stream().count() ; j++) {
                            if(abierto.get(j).getDestino().getNombre().equals(matrizCoordenadas[1][i])){
                                tomado=true;
                                System.out.println("ya existía "+abierto.get(j).getDestino().getNombre()+"con "+abierto.get(j).getPeso());
                                if(abierto.get(j).getPeso()-heuristica(i)+matrizAdyacencia[x][i]<pesoAcumulado){
                                    //todo verificar caso, cómo arreglar
                                    System.out.println("en teoría me debería devolver hasta la d");
                                }
                                else if(abierto.get(j).getPeso()>(matrizAdyacencia[x][i]+pesoAcumulado+d.heuristica())){
                                    System.out.println("en el juego de la vidaaaaaaaa");
                                    abierto.remove(abierto.get(j));
                                    abierto.add(new Paso(d, posicionActual, matrizAdyacencia[x][i]+pesoAcumulado+d.heuristica()));
                                    System.out.print(d.getNombre()+"("+(matrizAdyacencia[x][i]+pesoAcumulado+d.heuristica())+")  ");
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                        }
                        if(!tomado){
                            System.out.print("  nt");
                            abierto.add(new Paso(d, posicionActual, matrizAdyacencia[x][i]+pesoAcumulado+d.heuristica()));
                            System.out.print(d.getNombre()+"("+(matrizAdyacencia[x][i]+pesoAcumulado+d.heuristica())+")  ");
                        }
                    }

                }
            }

            //encontramos el mejor vecino
            int mejorVecino=0;
            for (int i = 0; i < abierto.stream().count(); i++) {
                if(abierto.get(i).getPeso() < abierto.get(mejorVecino).getPeso()){
                    mejorVecino=i;
                }
            }

            //cambio de lista al vecino
            Paso aux = abierto.remove(mejorVecino);
            aux.setPeso(aux.getPeso()-aux.getDestino().heuristica());
            cerrado.add(aux);
            pesoAcumulado=aux.getPeso();
            posicionActual=aux.getDestino();
            System.out.println(pesoAcumulado);
        }
    }


    private double heuristica(int i) {
        int x = Integer.parseInt(matrizCoordenadas[2][i]);
        int y = Integer.parseInt(matrizCoordenadas[3][i]);
        return Math.sqrt(Math.pow(Math.abs(x - 335), 2) + Math.pow(Math.abs(y - 30), 2));
    }

    private int indiceLetra(String letra) {
        int x = 0;
        for (int i = 0; i < 28; i++) {
            if (letra.equals(matrizCoordenadas[1][i])) {
                x = i;
            }
        }
        return x;
    }


}
