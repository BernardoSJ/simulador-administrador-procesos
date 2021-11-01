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

/**
 *
 * @author Bernardo SJ
 */
public class algoritmoColasMultinivelConRetroalimentacion implements Runnable {

    @Override
    public void run() {
        try {
            iniciarColasMultinivelConRetroalimentacion();
        } catch (InterruptedException ex) {
            Logger.getLogger(algoritmoColasMultinivelConRetroalimentacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarColasMultinivelConRetroalimentacion() throws InterruptedException {
        Procesos p;
        ArrayList<Procesos> procesosPrioridad = new ArrayList();
        int q = 1;
        int prioridad = 5;
        while (!FrameMain.procesoslistos.isEmpty()) {
            FrameMain.q = q;
            FrameMain.lbl_quantum.setText("" + FrameMain.q);
            Thread.sleep(1000);
            for (int i = 0; i < FrameMain.procesoslistos.size(); i++) {
                if (FrameMain.procesoslistos.get(i).prioridad == prioridad) {
                    procesosPrioridad.add(FrameMain.procesoslistos.get(i));
                }
            }
            if (procesosPrioridad.isEmpty()) {
                q = q * 2;
                prioridad--;
            } else {
                while (!procesosPrioridad.isEmpty()) {
                    int posTab = 0, posListos = 0, posPro = 0, rafaMenor = procesosPrioridad.get(0).rafaga;
                    for (int i = 0; i < procesosPrioridad.size(); i++) {
                        if (procesosPrioridad.get(i).rafaga < rafaMenor) {
                            rafaMenor = procesosPrioridad.get(i).rafaga;
                            posPro = i;
                        }
                    }
                    p = procesosPrioridad.get(posPro);
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
                            procesosPrioridad.remove(posPro);
                            Thread.sleep(1000);
                        } else {
                             FrameMain.actualizartabla();
                            FrameMain.lbl_ejecucion.setText("Ejecucion");
                            FrameMain.lbl_ejecucion2.setText("");
                            FrameMain.procesosEnTabla.get(posTab).prioridad--;
                            FrameMain.actualizartabla();
                            //p.prioridad--;
                            FrameMain.procesoslistos.add(p);
                            procesosPrioridad.remove(p);
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
                                procesosPrioridad.remove(posPro);
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
                                FrameMain.procesosEnTabla.get(posTab).es = false;
                                FrameMain.procesosEnTabla.get(posTab).prioridad--;
                                FrameMain.actualizartabla();
                                FrameMain.procesosbloqueados.get(0).es = false;
                                //FrameMain.procesosbloqueados.get(0).prioridad--;
                                FrameMain.procesoslistos.add(FrameMain.procesosbloqueados.get(0));
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
                                procesosPrioridad.remove(p);
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
                                procesosPrioridad.remove(posPro);
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
                                FrameMain.procesosEnTabla.get(posTab).es = false;
                                FrameMain.procesosEnTabla.get(posTab).prioridad--;
                                FrameMain.actualizartabla();
                                FrameMain.procesosusplistos.get(0).es = false;
                                //FrameMain.procesosusplistos.get(0).prioridad--;
                                FrameMain.procesoslistos.add(FrameMain.procesosusplistos.get(0));
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
                                procesosPrioridad.remove(p);
                                Thread.sleep(1000);
                            }
                        }
                    }
                    if (procesosPrioridad.isEmpty()) {
                        q = q * 2;
                        prioridad--;
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
