/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.FrameMain.foco;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Bernardo SJ
 */
public class algoritmoColasMultinivel implements Runnable {

    @Override
    public void run() {
        try {
            iniciarColasMultinivel();
        } catch (InterruptedException ex) {
            Logger.getLogger(algoritmoColasMultinivel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void iniciarColasMultinivel() throws InterruptedException  {
       Procesos p;
       ArrayList<Procesos> procesosPrioridad5=new ArrayList(); 
       ArrayList<Procesos> procesosPrioridad4=new ArrayList(); 
       ArrayList<Procesos> procesosPrioridad3=new ArrayList(); 
       ArrayList<Procesos> procesosPrioridad2=new ArrayList(); 
       ArrayList<Procesos> procesosPrioridad1=new ArrayList();
       while(!FrameMain.procesoslistos.isEmpty()){
           Thread.sleep(1000);
            for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                if (FrameMain.procesoslistos.get(i).prioridad == 5) {
                    procesosPrioridad5.add(FrameMain.procesoslistos.get(i));
                }
            }
            
            for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                if (FrameMain.procesoslistos.get(i).prioridad == 4) {
                    procesosPrioridad4.add(FrameMain.procesoslistos.get(i));
                }
            }
            
            for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                if (FrameMain.procesoslistos.get(i).prioridad == 3) {
                    procesosPrioridad3.add(FrameMain.procesoslistos.get(i));
                }
            }
            
            for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                if (FrameMain.procesoslistos.get(i).prioridad == 2) {
                    procesosPrioridad2.add(FrameMain.procesoslistos.get(i));
                }
            }
            
            for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                if (FrameMain.procesoslistos.get(i).prioridad == 1) {
                    procesosPrioridad1.add(FrameMain.procesoslistos.get(i));
                }
            }
            
            if(!procesosPrioridad5.isEmpty()){
              while(!procesosPrioridad5.isEmpty()){
                  Thread.sleep(1000);
                  p=procesosPrioridad5.get(0);
                  int posListos=0,posTab=0; 
                 for (int l = 0; l < FrameMain.procesoslistos.size(); l++) {
                        if (p.id == FrameMain.procesoslistos.get(l).id) {
                            posListos = l;
                        }
                    }
                 for (int t = 0; t < FrameMain.procesosEnTabla.size(); t++) {
                        if (p.id == FrameMain.procesosEnTabla.get(t).id) {
                            posTab = t;
                        }
                    }
                  if (p.es == false) {
                Thread.sleep(1000);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                FrameMain.procesoslistos.remove(posListos);
                FrameMain.model1.clear();
                for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                    FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                }
                FrameMain.lbl_ejecucion.setText("Ejecutando");
                FrameMain.lbl_ejecucion2.setText(p.nombre);
                Thread.sleep(1000);
                while (p.es == false) {
                    FrameMain.tiempo_cpu++;
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                  
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        procesosPrioridad5.remove(0);
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        Thread.sleep(1000);
                        break;
                    }else{
                          FrameMain.actualizartabla();
                    }
                }                
            } else {
                Random rnd = new Random();
                int camino = rnd.nextInt(2);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                if (camino == 1) {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosbloqueados.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosbloqueados.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        procesosPrioridad5.remove(0);
                        Thread.sleep(1000);
                    } else {
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");                        
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosbloqueados.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosbloqueados.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosbloqueados.remove(0);
                        procesosPrioridad5.remove(0);
                        procesosPrioridad5.add(p);
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                } else {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosusplistos.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosusplistos.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        procesosPrioridad5.remove(0);
                        Thread.sleep(1000);
                    } else {
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText(""); 
                        FrameMain.actualizartabla();
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosusplistos.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosusplistos.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosusplistos.remove(0);
                        procesosPrioridad5.remove(0);
                        procesosPrioridad5.add(p);
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                }
            }
              }
            }
            
            if(!procesosPrioridad4.isEmpty()){
               while(!procesosPrioridad4.isEmpty()){
                   Thread.sleep(1000);
                    int posPro=0;
            int rafagaMenor=procesosPrioridad4.get(0).rafaga;
            for(int i=0;i<procesosPrioridad4.size();i++){
                if(procesosPrioridad4.get(i).rafaga<rafagaMenor){
                    rafagaMenor=procesosPrioridad4.get(i).rafaga;
                    posPro=i;
                }
            }
            p=procesosPrioridad4.get(posPro);
            int posListos=0,posTab=0;
            for (int l = 0; l < FrameMain.procesoslistos.size(); l++) {
                        if (p.id == FrameMain.procesoslistos.get(l).id) {
                            posListos = l;
                        }
                    }
                    for (int t = 0; t < FrameMain.procesosEnTabla.size(); t++) {
                        if (p.id == FrameMain.procesosEnTabla.get(t).id) {
                            posTab = t;
                        }
               }
                  if (p.es == false) {
                Thread.sleep(1000);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                FrameMain.procesoslistos.remove(posListos);
                FrameMain.model1.clear();
                for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                    FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                }
                FrameMain.lbl_ejecucion.setText("Ejecutando");
                FrameMain.lbl_ejecucion2.setText(p.nombre);
                Thread.sleep(1000);
                while (p.es == false) {
                    FrameMain.tiempo_cpu++;
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                   
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        procesosPrioridad4.remove(posPro);
                        Thread.sleep(1000);
                        break;
                    }else{
                         FrameMain.actualizartabla();
                    }
                }
            } else {
                Random rnd = new Random();
                int camino = rnd.nextInt(2);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                if (camino == 1) {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosbloqueados.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                   
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosbloqueados.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        procesosPrioridad4.remove(posPro);
                        Thread.sleep(1000);
                    } else {
                         FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosbloqueados.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosbloqueados.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosbloqueados.remove(0);
                         procesosPrioridad4.remove(posPro);
                        procesosPrioridad4.add(p);
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                } else {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosusplistos.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                   
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosusplistos.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        Thread.sleep(1000);
                    } else {
                         FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosusplistos.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosusplistos.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosusplistos.remove(0);
                        procesosPrioridad4.remove(posPro);
                        procesosPrioridad4.add(p);
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                }
            }
            }
            }
            
            if(!procesosPrioridad3.isEmpty()){
               while(!procesosPrioridad3.isEmpty()){
                  p=procesosPrioridad3.get(0);
                  int posListos=0,posTab=0; 
                 for (int l = 0; l < FrameMain.procesoslistos.size(); l++) {
                        if (p.id == FrameMain.procesoslistos.get(l).id) {
                            posListos = l;
                        }
                    }
                 for (int t = 0; t < FrameMain.procesosEnTabla.size(); t++) {
                        if (p.id == FrameMain.procesosEnTabla.get(t).id) {
                            posTab = t;
                        }
                    }
                 if (p.es == false) {
                Thread.sleep(1000);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                FrameMain.procesoslistos.remove(posListos);
                FrameMain.model1.clear();
                for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                    FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                }
                FrameMain.lbl_ejecucion.setText("Ejecutando");
                FrameMain.lbl_ejecucion2.setText(p.nombre);
                Thread.sleep(1000);
                
                    FrameMain.tiempo_cpu++;
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                   
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        procesosPrioridad3.remove(0);
                        Thread.sleep(1000);
                    }else{
                         FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesoslistos.add(p);
                        procesosPrioridad3.remove(0);
                        procesosPrioridad3.add(p);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                             FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }               
            } else {
                Random rnd = new Random();
                int camino = rnd.nextInt(2);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                if (camino == 1) {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosbloqueados.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosbloqueados.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        procesosPrioridad3.remove(0);
                        Thread.sleep(1000);
                    } else {
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");                        
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosbloqueados.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosbloqueados.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosbloqueados.remove(0);
                        procesosPrioridad3.remove(0);
                        procesosPrioridad3.add(p);
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                } else {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosusplistos.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosusplistos.remove(posListos);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        procesosPrioridad3.remove(0);
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        Thread.sleep(1000);
                    } else {
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");                        
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosusplistos.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosusplistos.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosusplistos.remove(0);
                        procesosPrioridad3.remove(0);
                        procesosPrioridad3.add(p);
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                }
            }
               }
            }
            
            if(!procesosPrioridad2.isEmpty()){
              while(!procesosPrioridad2.isEmpty()){
                  int posPro=0;
            int prioridadMayor=procesosPrioridad2.get(0).prioridad;
            for(int i=0;i<procesosPrioridad2.size();i++){
                if(procesosPrioridad2.get(i).prioridad>prioridadMayor){
                    prioridadMayor=procesosPrioridad2.get(i).prioridad;
                    posPro=i;
                    break;
                }
            }
            p = procesosPrioridad2.get(posPro);
            int posListos=0,posTab=0; 
                 for (int l = 0; l < FrameMain.procesoslistos.size(); l++) {
                        if (p.id == FrameMain.procesoslistos.get(l).id) {
                            posListos = l;
                        }
                    }
                 for (int t = 0; t < FrameMain.procesosEnTabla.size(); t++) {
                        if (p.id == FrameMain.procesosEnTabla.get(t).id) {
                            posTab = t;
                        }
                    }
                  if (p.es == false) {
                Thread.sleep(1000);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                FrameMain.procesoslistos.remove(posListos);
                FrameMain.model1.clear();
                for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                    FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                }
                FrameMain.lbl_ejecucion.setText("Ejecutando");
                FrameMain.lbl_ejecucion2.setText(p.nombre);
                Thread.sleep(1000);
                while (p.es == false) {
                    FrameMain.tiempo_cpu++;
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        procesosPrioridad2.remove(posPro);
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        Thread.sleep(1000);
                        break;
                    }else{
                        FrameMain.actualizartabla();
                    }
                }
            } else {
                Random rnd = new Random();
                int camino = rnd.nextInt(2);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                if (camino == 1) {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosbloqueados.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosbloqueados.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        procesosPrioridad2.remove(posPro);
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        Thread.sleep(1000);
                    } else {
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosbloqueados.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosbloqueados.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosbloqueados.remove(0);
                        procesosPrioridad2.remove(posPro);
                        procesosPrioridad2.add(p);
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                } else {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosusplistos.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosusplistos.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        procesosPrioridad2.remove(posPro);
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        Thread.sleep(1000);
                    } else {
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosusplistos.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosusplistos.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosusplistos.remove(0);
                        procesosPrioridad2.remove(posPro);
                        procesosPrioridad2.add(p);
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                }
            }
              }
            }
             
            if(!procesosPrioridad1.isEmpty()){
              while(!procesosPrioridad1.isEmpty()){
                int posPro=0;
            int rafagaMenor=procesosPrioridad1.get(0).rafaga;
            for(int i=0;i<procesosPrioridad1.size();i++){
                if(procesosPrioridad1.get(i).rafaga<rafagaMenor){
                    rafagaMenor=procesosPrioridad1.get(i).rafaga;
                    posPro=i;
                }
            }
            p=procesosPrioridad1.get(posPro);
            int posListos=0,posTab=0;
            for (int l = 0; l < FrameMain.procesoslistos.size(); l++) {
                        if (p.id == FrameMain.procesoslistos.get(l).id) {
                            posListos = l;
                        }
                    }
                    for (int t = 0; t < FrameMain.procesosEnTabla.size(); t++) {
                        if (p.id == FrameMain.procesosEnTabla.get(t).id) {
                            posTab = t;
                        }
               }
                   if (p.es == false) {
                Thread.sleep(1000);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                FrameMain.procesoslistos.remove(posListos);
                FrameMain.model1.clear();
                for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                    FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                }
                FrameMain.lbl_ejecucion.setText("Ejecutando");
                FrameMain.lbl_ejecucion2.setText(p.nombre);
                Thread.sleep(1000);
                
                    FrameMain.tiempo_cpu++;
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        procesosPrioridad1.remove(0);
                        Thread.sleep(1000);
                    }else{
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesoslistos.add(p);
                        procesosPrioridad1.remove(0);
                        procesosPrioridad1.add(p);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                             FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }               
            } else {
                Random rnd = new Random();
                int camino = rnd.nextInt(2);
                if (p.rafaga == FrameMain.rafagas.get(posTab)) {
                    FrameMain.procesosEstadisticas.get(posTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                if (camino == 1) {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosbloqueados.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosbloqueados.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        procesosPrioridad1.remove(0);
                        Thread.sleep(1000);
                    } else {
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");                        
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosbloqueados.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosbloqueados.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosbloqueados.remove(0);
                        procesosPrioridad1.remove(0);
                        procesosPrioridad1.add(p);
                        FrameMain.model2.clear();
                        for (int i = 0; i < FrameMain.procesosbloqueados.size(); i++) {
                            FrameMain.model2.addElement(FrameMain.procesosbloqueados.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                } else {
                    FrameMain.procesoslistos.remove(posListos);
                    FrameMain.procesosusplistos.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga - FrameMain.q;
                   
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(posTab).rafaga <= 0) {
                        FrameMain.procesosusplistos.remove(0);
                        FrameMain.procesosEnTabla.get(posTab).rafaga = FrameMain.procesosEnTabla.get(posTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        procesosPrioridad1.remove(posPro);
                        FrameMain.procesosEstadisticas.get(posTab).instante_de_fin = FrameMain.tiempo_cpu;
                        Thread.sleep(1000);
                    } else {
                         FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");                        
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.procesosusplistos.get(0).es = false;
                        FrameMain.procesoslistos.add(FrameMain.procesosusplistos.get(0));
                        FrameMain.procesosEnTabla.get(posTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosusplistos.remove(0);
                        procesosPrioridad1.remove(0);
                        procesosPrioridad1.add(p);
                        FrameMain.model3.clear();
                        for (int i = 0; i < FrameMain.procesosusplistos.size(); i++) {
                            FrameMain.model3.addElement(FrameMain.procesosusplistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                            FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }
                }
            }   
              }  
            }
       }
         String path="/Main/foco a.png";
        URL url=this.getClass().getResource(path);
        ImageIcon icon=new ImageIcon(url);
        foco.setIcon(icon);
       FrameMain.algoritmoTerminado();
    }
}

