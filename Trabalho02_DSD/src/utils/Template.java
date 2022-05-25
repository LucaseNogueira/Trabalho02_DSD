package utils;

/**
 * Define template de interface visual para a aplicação.
 * Temas disponíveis: Nimbus / Windows / Metal /Mac OS X / CDE/Motif / GTK+
 * 
 * @author Lucas Nogueira
 * @author Fabio Frare
 * 
 * @since 2022
 */
public class Template {
    
    private final String template = "Nimbus";
    
    public void iniciarTema() {               
           try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (template.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(classe.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
}
