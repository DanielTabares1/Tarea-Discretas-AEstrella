package controller;

import javafx.scene.control.Label;
import model.Nodo;
import model.Paso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    public Label label_vista; // coneccion entre el algoritmo y el texto que se va a mostrar en pantalla

    private double[][] matrizAdyacencia = new double[28][28];  //matriz de adyacencia
    private String[][] matrizCoordenadas = new String[4][28];  //matriz de coordenadas


    /*
    método que lee el archivo matriz.txt e ingresa sus datos en la matriz de adyacencia
     */
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


    /*
    método que lee el archivo coordenadas.txt e ingresa sus datos en la matriz de coordenadas
     */
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


    /*
    método que detecta la accion del click en el botón de la interfaz gráfica
     */
    public void btn_ejecutar_algoritmo_action() {
        //lee y carga las matrices desde los archivos de texto
        llenarMatriz();
        llenarCoordenadas();
        //ejecuta el algoritmo que da la solucion
        resolver();
    }


    /*
    método que toma todos los datos cargados y ejecuta la solución
     */
    public void resolver() {
        //se crea un arreglo de Nodos en el cual se guardarán todos los posibles nodos a visitar
        //con sus datos correspondientes (nombre y coordenadas)
        Nodo[] nodos = new Nodo[28];
        //este ciclo llena el anterior arreglo
        for (int i = 0; i < 28; i++) {
            String nombre = matrizCoordenadas[1][i];
            int x = Integer.parseInt(matrizCoordenadas[2][i]);
            int y = Integer.parseInt(matrizCoordenadas[3][i]);
            nodos[i] = new Nodo(nombre, x, y);
        }

        ArrayList<Paso> cerrado = new ArrayList<>(); // se crea una lista para los nodos por los que se ha pasado
        ArrayList<Paso> abierto = new ArrayList<>(); // se crea una lista para los nodos vecinos
        double pesoAcumulado = 0;                      //se establece el peso acumulado inicial como 0
        Nodo posicionActual = nodos[0];              // se crea el nodo A como primer elemento y posicion actual
        cerrado.add(new Paso(posicionActual, null, 0)); // se agreaga el mmismo nodo a la lista cerrado

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
                //este condicional determina si los nodos analizados tienen una arista en común
                //determinando si su cruce en la matriz de adyacencia es diferente de cero
                if (matrizAdyacencia[x][i] != 0) {
                    boolean tomado = false;   //se crea la variable tomado para controlar el no repetir nodos
                    int auxN = 0;
                    // vericamos  si ya se ha pasado antes por el nodo
                    for (int j = 0; j < cerrado.stream().count(); j++) {
                        if (cerrado.get(j).getDestino().getNombre().equals(matrizCoordenadas[1][i])) {
                            tomado = true;
                            auxN = i;
                        }
                    }
                    //si no se ha pasado antes
                    if (!tomado) {
                        //se crea un nuevo nodo con los datos del destino
                        Nodo d = new Nodo(matrizCoordenadas[1][i], Integer.parseInt(matrizCoordenadas[2][i]), Integer.parseInt(matrizCoordenadas[3][i]));
                        //se recorre la lista de los posibles vecinos con un ciclo
                        for (int j = 0; j < abierto.stream().count(); j++) {
                            //si el nodo estino ya estaba entre los posibles vecinos
                            if (abierto.get(j).getDestino().getNombre().equals(matrizCoordenadas[1][i])) {
                                tomado = true;
                                //se comprueba si hay un mejor camino para llegar a nuestra ubicacion actual y en dicho caso se cambia
                                if (abierto.get(j).getPeso() - heuristica(i) + matrizAdyacencia[x][i] < pesoAcumulado) {
                                    Paso eliminar = cerrado.get((int) cerrado.stream().count() - 1);
                                    //este ciclo elimina los pasos dados hasta la salida que encontramos y era mejor
                                    while (!eliminar.getPadre().getNombre().equals(abierto.get(j).getPadre().getNombre())) {
                                        eliminar = cerrado.remove((int) cerrado.stream().count() - 1);
                                    }
                                    eliminar = abierto.remove(j);
                                    eliminar.setPeso(eliminar.getPeso() - eliminar.getDestino().heuristica());
                                    cerrado.add(eliminar);
                                    pesoAcumulado = eliminar.getPeso();
                                    Paso agregar = new Paso(nodos[x], eliminar.getDestino(), pesoAcumulado + matrizAdyacencia[x][i]);
                                    cerrado.add(agregar);
                                    pesoAcumulado += matrizAdyacencia[x][i];
                                    break;
                                }
                                // se verifica si el nuevo camino es más corto que el anterior y se actualiza
                                else if (abierto.get(j).getPeso() > (matrizAdyacencia[x][i] + pesoAcumulado + d.heuristica())) {
                                    abierto.remove(abierto.get(j));
                                    abierto.add(new Paso(d, posicionActual, matrizAdyacencia[x][i] + pesoAcumulado + d.heuristica()));
                                    break;
                                }
                            }
                        }
                        //si el destino no había sido revisado antes, se agrega a la lista de abiertos
                        if (!tomado) {
                            abierto.add(new Paso(d, posicionActual, matrizAdyacencia[x][i] + pesoAcumulado + d.heuristica()));
                        }
                    }
                }
            }

            //encontramos el mejor vecino
            int mejorVecino = 0;
            for (int i = 0; i < abierto.stream().count(); i++) {
                if (abierto.get(i).getPeso() < abierto.get(mejorVecino).getPeso()) {
                    mejorVecino = i;
                }
            }

            //cambio de lista al vecino encontrado, se elimina de los vecinos y se agrega a los pasos dados
            Paso aux = abierto.remove(mejorVecino);
            aux.setPeso(aux.getPeso() - aux.getDestino().heuristica());
            cerrado.add(aux);
            pesoAcumulado = aux.getPeso();
            posicionActual = aux.getDestino();
        }

        //Se revisa la lista cerrado que contiene los pasos definitivos
        //Y se guardan los nombres de los nodos visitados en un string
        String resultado = "";
        for (Paso p : cerrado) {
            resultado += p.getDestino().getNombre() + "   ";
        }
        //se muestra el string resultado en pantalla
        label_vista.setText("El mejor camino es\n" + resultado);
    }


    /*
    método que calcula la eurística al pasarle un dato entero
    correspondiente al índice del nodo en la tabla de coordenadas
    (A:0, B:1, C:2, D:3, E:4, F:5, ...)
     */
    private double heuristica(int i) {
        int x = Integer.parseInt(matrizCoordenadas[2][i]);
        int y = Integer.parseInt(matrizCoordenadas[3][i]);
        return Math.sqrt(Math.pow(Math.abs(x - 335), 2) + Math.pow(Math.abs(y - 30), 2));
    }

    /*
    método encargado de obtener el indice correspondiente al nodo
    cuyo nombre es el parámetro de dicho método
    (A:0, B:1, C:2, D:3, E:4, F:5, ...)
     */
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
