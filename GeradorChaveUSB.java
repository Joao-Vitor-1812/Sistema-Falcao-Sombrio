import java.io.File;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class GeradorChaveUSB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println(" FORJA DE CHAVE MFA - SECURUS DYNAMICS ");
        System.out.println("=========================================");
        System.out.print("Digite a letra da unidade do seu Pendrive (Ex: D:\\): ");
        String unidade = scanner.nextLine().trim();

        // Garante que a barra da pasta esteja correta
        if (!unidade.endsWith("\\") && !unidade.endsWith("/")) {
            unidade += "\\";
        }

        File pendrive = new File(unidade);
        if (!pendrive.exists() || !pendrive.isDirectory()) {
            System.out.println("ERRO: Unidade não encontrada. Verifique se o pendrive está conectado.");
            return;
        }

        try {
            // 1. Gera uma sequência de bytes incrivelmente aleatória e segura
            SecureRandom secureRandom = new SecureRandom();
            byte[] chaveBytes = new byte[64]; // 64 bytes = 512 bits de entropia
            secureRandom.nextBytes(chaveBytes);

            // 2. Converte para uma string legível (Base64)
            String chaveCriptografica = Base64.getEncoder().encodeToString(chaveBytes);

            // 3. Define o nome do arquivo-chave
            File arquivoChave = new File(pendrive, "securus_hardware_token.key");

            // 4. Grava a chave no pendrive
            try (FileOutputStream fos = new FileOutputStream(arquivoChave)) {
                fos.write(chaveCriptografica.getBytes());
            }

            // Oculta o arquivo (funciona no Windows)
            try {
                Process p = Runtime.getRuntime().exec("attrib +H " + arquivoChave.getAbsolutePath());
                p.waitFor();
            } catch (Exception ignore) {}

            System.out.println("\n[SUCESSO] Chave de Hardware gerada e gravada!");
            System.out.println("Local: " + arquivoChave.getAbsolutePath());
            System.out.println("\nCOPIE A LINHA ABAIXO E COLE NO SEU AuthController.java:");
            System.out.println("--------------------------------------------------");
            System.out.println(chaveCriptografica);
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Erro ao forjar a chave: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}