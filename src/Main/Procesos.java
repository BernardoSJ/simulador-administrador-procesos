
package Main;

public class Procesos {
    String nombre;
    int id=0;
    int rafaga;
    int prioridad;
    boolean es;
    
    //Atributos que serviran para las estadisticas para llenar algunos valores estos serán calculados
     int instante_de_llegada;
     int instante_de_fin;
     int tiempo_de_ejecucion;
     int tiempo_de_servicio;
     int tiempo_de_espera;
     float indiceServicio;
     
    void pedirNombre(String nombre){
       this.nombre=nombre;
    }
    
    void generarId(int cont){
        this.id=cont;
    }
    
    void pedirRafaga(int rafaga){
        this.rafaga=rafaga;
    }
    void prioridad(int prioridad){
        this.prioridad=prioridad;
    }

   
}
