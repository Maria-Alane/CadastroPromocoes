package org.example.controller;

import org.example.model.Usuario;
import org.example.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listarUsuarios());
        return "usuarios";
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "form";
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute Usuario usuario) {
        service.criarUsuario(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/buscar")
    public String buscarUsuarioPorId(@RequestParam Long id, Model model) {
        Usuario usuario = service.buscarPorId(id);
        model.addAttribute("usuarios", java.util.List.of(usuario));
        return "usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = service.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Usuario usuario) {
        Usuario usuarioExistente = service.buscarPorId(id);
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        service.atualizarUsuario(usuarioExistente);
        return "redirect:/usuarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.removerUsuario(id);
        return "redirect:/usuarios";
    }
}