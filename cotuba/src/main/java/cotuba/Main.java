package cotuba;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Path diretorioDosMD;
        String formato;
        Path arquivoDeSaida;
        boolean modoVerboso = false;

        try {

            var opcoesCLI = new LeitorOpcoesCLI(args);
            diretorioDosMD = opcoesCLI.getDiretorioDosMD();
            formato = opcoesCLI.getFormato();
            arquivoDeSaida = opcoesCLI.getArquivoDeSaida();
            modoVerboso = opcoesCLI.isModoVerboso();

            var renderizador = new RenderizadorMDParaHTML();
            List<Capitulo> capitulos = renderizador.renderiza(diretorioDosMD);

            Ebook ebook = new Ebook();
            ebook.setFormato(formato);
            ebook.setArquivoDeSaida(arquivoDeSaida);
            ebook.setCapitulos(capitulos);

            if ("pdf".equals(formato)) {
                var geradorPDF = new GeradorPDF();
                geradorPDF.gera(ebook);

            } else if ("epub".equals(formato)) {
                var geradorEPUB = new GeradorEPUB();
                geradorEPUB.gera(ebook);
            } else {
                throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
            }

            System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (modoVerboso) {
                ex.printStackTrace();
            }
            System.exit(1);
        }
    }

}
