# Tarea-Discretas-AEstrella
Práctica Matemáticas Discretas II

Por Daniel Tabares Pavas con CC. 1.007.480.705 Y Miguel Angel Urueña Riobo con CC. 1.006.121.797

Para Matemáticas Discretas II - UdeA, Enero de 2021
Desarrollado en Java Usando el IDE Intellij 

IMPORTANTE:
debido a que el programa usa lectura de archivos se debe modificar una direccion en el código para ejecutarlo

si se descarga el proyecto en un computador para ejecutarlo se deberá buscar en la carpeta del proyecto una 
carpeta llamada src, la cual contiene todo relevante, o desarrollado por nosotros

dentro de esta se encuentra la carpeta controller y en esta un archivo con el mismo nombre, este archivo es el que 
realiza todo el proceso relacionado con la ejecucion del algoritmo A*.
en este archivo, se deberán ubicar las funciones llamadas llenarMatriz y llenarCoordenadas
(directamente buscar las lineas 29 y 58 del archivo Controller.java)
las cuales usan la lectura de archivos, en estas se debe ubicar la sigueinte sentencia.

      reader = new FileReader("C:/Users/HP/IdeaProjects/aplicacionAEstrella/src/resources/matriz.txt");      
      
      
que contienen la direccion en la cual se ubican los archivos .txt y el grafo del ejercicio
esta direccion debe ser cambiada a la ubicacion resultante en el equipo en que se descarga el aplicativo
dicha direccion llega hasta la carpeta src nuevamente, dentro de esta resources y por último el nombre del archivo .txt
(matriz.txt o coordenadas.txt) 

con este cambio realizado el programa debería ejecutarse sin problema alguno, en cualquier caso se puede contactar con nosotros
para resolver cualquier tipo de problema con la ejecucion del algoritmo

----------------------------------------------

A continuacion se explica la utilidad de los demás archivos y carpetas en el código (Si desea saltarse esta parte no hay problema, lo relevante está en controller)

la carpeta view contiene un archivo de interfaz gráfica, con librerías nativas de java y este es lanzado por el método main al iniciar la ejecucion

la carpeta Model tiene las clases (tipos de dato) Nodo, el cual tiene nombre y coordenadas (x,y) , 
y Paso, el cual tiene un nodo padre, un nodo destino y un costo

la carpeta resources unicamente contiene los archivos de lectura (matriz.txt y coordenadas.txt) además de la imagen del grafo (grafo.PNG)

La clase controller.java hace uso de todos estos archivos para ejecutar de una forma sencilla el algoritmo A* y así hacerlo menos confuso o dificil de entender
a su vez el método controller tiene comentarios que ayudan a la identificacion y entendimiento de cada proceso.
