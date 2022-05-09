
package backupData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.SwingWorker;
import sistemacheranaguapotable.bd.Ruta;
/**
 *
 * @author jafeth888
 */
public class ProcesoRespaldoBd extends SwingWorker<Void, Void>{
    private String ruta;
    public ProcesoRespaldoBd(String ruta) {
            // TODO Auto-generated constructor stub
            this.setProgress(0);
            this.ruta=ruta;
    }
    
    	public void crearRespaldoBd(String ruta) throws IOException {

            Process p = Runtime.getRuntime().exec("mysqldump -u root " + Ruta.database + "");

            //new HiloLector(p.getErrorStream()).start();

            InputStream is = p.getInputStream();// Pedimos la entrada

            FileOutputStream fos = new FileOutputStream(ruta + ".sql"); // creamos el archivo para le respaldo
            byte[] buffer = new byte[1000]; // Creamos una variable de tipo byte para el buffer

            int leido = is.read(buffer); // Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.

            while (leido > 0) {
                fos.write(buffer, 0, leido);// Buffer de caracteres, Desplazamiento de partida para empezar a escribir
                                            // caracteres, Número de caracteres para escribir
                leido = is.read(buffer);
            }

            fos.close();// Cierra respaldo
	}
	
	public void procesarRespaldpBd() throws InterruptedException {
		this.setProgress(0);
		for (int i = 0; i <=100; i++) {
                    if(i==70) {
                        try {
                            this.crearRespaldoBd(ruta);
                            System.out.println("backupData.ProcesoRespaldoBd.procesarRespaldpBd()");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    this.setProgress(i);
                    Thread.sleep(20);
		}
		Thread.sleep(1200);
		System.exit(0);
		
	}
    
    @Override
    protected Void doInBackground() throws Exception {
        this.setProgress(0);
        this.procesarRespaldpBd();
        return null;
    }
}
