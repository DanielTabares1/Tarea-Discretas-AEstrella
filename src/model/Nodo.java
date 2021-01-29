package model;

public class Nodo {
    //atributos de la clase
    private String nombre;  //nombre del nodo, letra o conjunto de letras
    private int x;          //coordenada x
    private int y;          //coordenada y
    //constructor
    public Nodo(String nombre, int x, int y) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
    }
    // getters y setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    //métodos de la clase

    public double heuristica(){
        int diferenciaX = Math.abs(x-335);
        int diferenciaY = Math.abs(y-30);
        double resultado = Math.sqrt(Math.pow(diferenciaX,2)+Math.pow(diferenciaY,2));
        return resultado;
    }
}
