package com.securus.falcao_sombrio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final OperadorRepository operadorRepository;

    // Injeção de dependência por construtor
    public AuthController(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    // --- ENDPOINT 1: LOGIN COM MULTIFATOR (MFA GLOBAL) ---
    @PostMapping("/mfa-login")
    public ResponseEntity<String> loginComMFA(@RequestBody LoginRequest request) {
        
        // 1. Busca o operador específico que está tentando logar
        Optional<Operador> operadorOpt = operadorRepository.findByLogin(request.getUsername());
        
        // 2. Valida as credenciais normais (Login e Senha)
        if (operadorOpt.isEmpty() || !operadorOpt.get().getSenha().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso Negado: Credenciais Inválidas.");
        }

        Operador operador = operadorOpt.get();

        // 3. VALIDAÇÃO DA CHAVE: Compara o arquivo com a chave atualizada do banco
        if (request.getHardwareKey() == null || !request.getHardwareKey().equals(operador.getHardwareKey())) {
            System.out.println("ALERTA DE SEGURANÇA: Falha de MFA para o usuário: " + request.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("MFA FALHOU: Assinatura de Hardware Inválida ou Ausente.");
        }

        // 4. Acesso liberado
        return ResponseEntity.ok(operador.getLogin());
    }

    // --- ENDPOINT 2: LOGOUT COM ROTAÇÃO UNIFICADA PARA TODOS ---
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String username) {
        
        // 1. Gera UMA única nova chave aleatória de 32 caracteres para o sistema inteiro
        String novaChaveHardware = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        
        // 2. Busca TODOS os operadores cadastrados na tabela do Supabase
        List<Operador> todosOperadores = operadorRepository.findAll();
        
        // 3. Laço de repetição (Loop) para injetar a MESMA chave em todas as contas ao mesmo tempo
        for (Operador op : todosOperadores) {
            op.setHardwareKey(novaChaveHardware);
        }
        
        // 4. Salva a lista inteira atualizada de uma vez só no banco
        operadorRepository.saveAll(todosOperadores);
        
        System.out.println("SISTEMA: Chave GLOBAL rotacionada por: " + username + ". Sincronizada para todos os operadores.");
        
        // 5. Devolve o novo token para o front-end disparar o download
        return ResponseEntity.ok(novaChaveHardware);
    }
}

// --- CLASSE AUXILIAR DTO PARA CAPTURA DO PAYLOAD DE ENTRADA ---
class LoginRequest {
    private String username;
    private String password;
    private String hardwareKey;

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getHardwareKey() { return hardwareKey; }
    public void setHardwareKey(String hardwareKey) { this.hardwareKey = hardwareKey; }
}