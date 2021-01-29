package controller;

import javafx.scene.control.Label;
import model.Nodo;
import model.Paso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    public Label label_vista;

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

    //algoritmo que detecta la accion del botón y ejecuta el algoritmo
    public void btn_ejecutar_algoritmo_action() {
        //lee y carga las matrices desde los archivos de texto
        llenarMatriz();
        llenarCoordenadas();
        //ejecuta el algoritmo que da la solucion
        resolver();
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

        //un ciclo se repite hasta llegar al destino "W"
        while (!posicionActual.getNombre().equals(nodos[22].getNombre())) {
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
                    // vericamos  si ya se ha pasado antes por el nodo
                    for (int j = 0; j < cerrado.stream().count(); j++) {
                        if (cerrado.get(j).getDestino().getNombre().equals(matrizCoordenadas[1][i])) {
                            tomado = true;
                            auxN=i;
                        }
                    }
                    //si no se ha pasado antes
                    if(!tomado){
                        //se crea un nuevo nodo con los datos del destino
                        Nodo d = new Nodo(matrizCoordenadas[1][i], Integer.parseInt(matrizCoordenadas[2][i]), Integer.parseInt(matrizCoordenadas[3][i]));
                        //se recorre la lista de los posibles vecinos con un ciclo
                        for (int j = 0; j < abierto.stream().count() ; j++) {
                            //si el nodo estino ya estaba entre los posibles vecinos
                            if(abierto.get(j).getDestino().getNombre().equals(matrizCoordenadas[1][i])){
                                tomado=true;
                                //se comprueba si hay un mejor camino para llegar a nuestra ubicacion actual y en dicho caso se cambia
                                if(abierto.get(j).getPeso()-heuristica(i)+matrizAdyacencia[x][i]<pesoAcumulado){
                                    Paso eliminar = cerrado.get((int)cerrado.stream().count()-1);
                                    while (!eliminar.getPadre().getNombre().equals(abierto.get(j).getPadre().getNombre())){
                                        eliminar = cerrado.remove((int)cerrado.stream().count()-1);
                                    }
                                    eliminar=abierto.remove(j);
                                    eliminar.setPeso(eliminar.getPeso()-eliminar.getDestino().heuristica());
                                    cerrado.add(eliminar);
                                    pesoAcumulado = eliminar.getPeso();
                                    Paso agregar = new Paso(nodos[x], eliminar.getDestino(), pesoAcumulado+matrizAdyacencia[x][i]);
                                    cerrado.add(agregar);
                                    pesoAcumulado+=matrizAdyacencia[x][i];
                                    break;
                                }
                                // se verifica si el nuevo camino es más corto que el anterior y se actualiza
                                else if(abierto.get(j).getPeso()>(matrizAdyacencia[x][i]+pesoAcumulado+d.heuristica())){
                                    abierto.remove(abierto.get(j));
                                    abierto.add(new Paso(d, posicionActual, matrizAdyacencia[x][i]+pesoAcumulado+d.heuristica()));
                                    break;
                                }
                            }
                        }
                        //si el destino no había sido revisado antes, se agrega a la lista de abiertos
                        if(!tomado){
                            abierto.add(new Paso(d, posicionActual, matrizAdyacencia[x][i]+pesoAcumulado+d.heuristica()));
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

            //cambio de lista al vecino encontrado
            Paso aux = abierto.remove(mejorVecino);
            aux.setPeso(aux.getPeso()-aux.getDestino().heuristica());
            cerrado.add(aux);
            pesoAcumulado=aux.getPeso();
            posicionActual=aux.getDestino();
        }

        //Se revisa la lista cerrado que contiene los pasos definitivos
        String resultado = "";
        for (Paso p: cerrado) {
            resultado+=p.getDestino().getNombre()+"   ";
        }
        //se muestra el resultado en pantalla
        label_vista.setText("El mejor camino es\n"+resultado);
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
