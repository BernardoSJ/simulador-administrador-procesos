/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.FrameMain.foco;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Bernardo SJ
 */
public class algoritmoRR implements Runnable {

    @Override
    public void run() {
        try {
            iniciarRR();
        } catch (InterruptedException ex) {
            Logger.getLogger(algoritmoRR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void iniciarRR() throws InterruptedException{
      Procesos p;
        while (!FrameMain.procesoslistos.isEmpty()) {
            Thread.sleep(1000);
            p = FrameMain.procesoslistos.get(0);
            int pEnTab = 0;
            for (int i = 0; i < FrameMain.procesosEnTabla.size(); i++) {
                if (p.id == FrameMain.procesosEnTabla.get(i).id) {
                    pEnTab = i;
                }
            }
            if (p.es == false) {
                Thread.sleep(1000);
                if (p.rafaga == FrameMain.rafagas.get(pEnTab)) {
                    FrameMain.procesosEstadisticas.get(pEnTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                FrameMain.procesoslistos.remove(0);
                FrameMain.model1.clear();
                for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                    FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                }
                FrameMain.lbl_ejecucion.setText("Ejecutando");
                FrameMain.lbl_ejecucion2.setText(p.nombre);
                Thread.sleep(1000);
                
                    FrameMain.tiempo_cpu++;
                    FrameMain.procesosEnTabla.get(pEnTab).rafaga = FrameMain.procesosEnTabla.get(pEnTab).rafaga - FrameMain.q;
                   
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(pEnTab).rafaga <= 0) {
                        FrameMain.procesosEnTabla.get(pEnTab).rafaga = FrameMain.procesosEnTabla.get(pEnTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(pEnTab).instante_de_fin = FrameMain.tiempo_cpu;
                        Thread.sleep(1000);
                    }else{
                         FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesoslistos.add(p);
                        FrameMain.model1.clear();
                        for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                             FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                        }
                        Thread.sleep(1000);
                    }               
            } else {
                Random rnd = new Random();
                int camino = rnd.nextInt(2);
                if (p.rafaga == FrameMain.rafagas.get(pEnTab)) {
                    FrameMain.procesosEstadisticas.get(pEnTab).instante_de_llegada = FrameMain.tiempo_cpu;
                }
                if (camino == 1) {
                    FrameMain.procesoslistos.remove(0);
                    FrameMain.procesosbloqueados.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(pEnTab).rafaga = FrameMain.procesosEnTabla.get(pEnTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(pEnTab).rafaga <= 0) {
                        FrameMain.procesosbloqueados.remove(0);
                        FrameMain.procesosEnTabla.get(pEnTab).rafaga = FrameMain.procesosEnTabla.get(pEnTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(pEnTab).instante_de_fin = FrameMain.tiempo_cpu;
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
                        FrameMain.procesosEnTabla.get(pEnTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosbloqueados.remove(0);
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
                    FrameMain.procesoslistos.remove(0);
                    FrameMain.procesosusplistos.add(p);
                    FrameMain.tiempo_cpu++;
                    FrameMain.model1.clear();
                    for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                        FrameMain.model1.addElement(FrameMain.procesoslistos.get(i).nombre);
                    }
                    FrameMain.lbl_ejecucion.setText("Ejecutando");
                    FrameMain.lbl_ejecucion2.setText(p.nombre);
                    Thread.sleep(1000);
                    FrameMain.procesosEnTabla.get(pEnTab).rafaga = FrameMain.procesosEnTabla.get(pEnTab).rafaga - FrameMain.q;
                    
                    Thread.sleep(1000);
                    if (FrameMain.procesosEnTabla.get(pEnTab).rafaga <= 0) {
                        FrameMain.procesosusplistos.remove(0);
                        FrameMain.procesosEnTabla.get(pEnTab).rafaga = FrameMain.procesosEnTabla.get(pEnTab).rafaga * 0;
                        FrameMain.actualizartabla();
                        FrameMain.lbl_ejecucion.setText("Ejecucion");
                        FrameMain.lbl_ejecucion2.setText("");
                        FrameMain.procesosEstadisticas.get(pEnTab).instante_de_fin = FrameMain.tiempo_cpu;
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
                        FrameMain.procesosEnTabla.get(pEnTab).es = false;
                        FrameMain.actualizartabla();
                        FrameMain.procesosusplistos.remove(0);
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
        String path="/Main/foco a.png";
        URL url=this.getClass().getResource(path);
        ImageIcon icon=new ImageIcon(url);
        foco.setIcon(icon);
        FrameMain.algoritmoTerminado();
    }
}
