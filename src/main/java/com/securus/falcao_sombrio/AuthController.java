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

    public AuthController(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    // --- ENDPOINT 1: LOGIN COM MULTIFATOR (MFA GLOBAL) ---
    @PostMapping("/mfa-login")
    public ResponseEntity<String> loginComMFA(@RequestBody LoginRequest request) {
        
        Optional<Operador> operadorOpt = operadorRepository.findByLogin(request.getUsername());
        
        if (operadorOpt.isEmpty() || !operadorOpt.get().getSenha().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso Negado: Credenciais Inválidas.");
        }

        Operador operador = operadorOpt.get();

        if (request.getHardwareKey() == null || !request.getHardwareKey().equals(operador.getHardwareKey())) {
            System.out.println("ALERTA DE SEGURANÇA: Falha de MFA para o usuário: " + request.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("MFA FALHOU: Assinatura de Hardware Inválida.");
        }

        System.out.println("SISTEMA: Sessão criada via Pendrive para o operador: " + operador.getLogin());
        return ResponseEntity.ok(operador.getLogin());
    }

    // --- ENDPOINT 2: LOGOUT COM ROTAÇÃO UNIFICADA EM LOTE ---
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String username) {
        
        String novaChaveHardware = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        List<Operador> todosOperadores = operadorRepository.findAll();
        
        for (Operador op : todosOperadores) {
            op.setHardwareKey(novaChaveHardware);
        }
        
        operadorRepository.saveAll(todosOperadores);
        
        System.out.println("SISTEMA: Chave GLOBAL rotacionada por: " + username + ". Sincronizada para todos os operadores.");
        return ResponseEntity.ok(novaChaveHardware);
    }

    // CORREÇÃO: Transformada em static inner class para o compilador do Java resolver o tipo sem dar erro
    public static class LoginRequest {
        private String username;
        private String password;
        private String hardwareKey;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getHardwareKey() { return hardwareKey; }
        public void setHardwareKey(String hardwareKey) { this.hardwareKey = hardwareKey; }
    }
}