package com.securus.falcao_sombrio;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // CHAVE REAL GERADA PELO SEU PENDRIVE D:
    private final String CHAVE_HARDWARE_MESTRE = "x+mre6eyF3r0MvBjxpsSxx45pqzsRuin0H5ppzg3E4r9uBfCpPQz6ZNbci3TGEHShy9Gbg7+IETUPaWMpOAhSw=="; 

    private final OperadorRepository operadorRepository;

    public AuthController(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }

    @PostMapping("/mfa-login")
    public ResponseEntity<String> loginComMFA(@RequestBody LoginRequest request) {
        
        // 1. Verifica se o operador existe e se a senha está correta no banco
        Optional<Operador> operadorOpt = operadorRepository.findByLogin(request.getUsername());
        
        if (operadorOpt.isEmpty() || !operadorOpt.get().getSenha().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso Negado: Credenciais Inválidas.");
        }

        // 2. PROTOCOLO OBRIGATÓRIO PARA TODOS: Exige validação do hardware (Pendrive)
        if (request.getHardwareKey() == null || !request.getHardwareKey().equals(CHAVE_HARDWARE_MESTRE)) {
            System.out.println("ALERTA DE SEGURANÇA: Tentativa de login sem a Chave Física por: " + request.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("MFA FALHOU: Assinatura de Hardware Inválida ou Ausente.");
        }

        // 3. Se passou pela senha E pelo pendrive, acesso liberado!
        return ResponseEntity.ok(operadorOpt.get().getLogin());
    }
}

class LoginRequest {
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