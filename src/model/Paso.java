package model;

public class Paso {
    private Nodo destino;
    private Nodo padre;
    private double peso;

    public Paso(Nodo destino, Nodo padre, double peso) {
        this.destino = destino;
        this.padre = padre;
        this.peso = peso;
    }

    public Nodo getDestino() {
        return destino;
    }
    public void setDestino(Nodo destino) {
        this.destino = destino;
    }
    public Nodo getPadre() {
        return padre;
    }
    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
}
